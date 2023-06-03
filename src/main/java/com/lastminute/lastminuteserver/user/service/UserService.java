package com.lastminute.lastminuteserver.user.service;

import com.lastminute.lastminuteserver.exceptions.RequestExceptionCode;
import com.lastminute.lastminuteserver.exceptions.RequestException;
import com.lastminute.lastminuteserver.user.domain.AccountState;
import com.lastminute.lastminuteserver.user.domain.User;
import com.lastminute.lastminuteserver.user.dto.UserCreateDto;
import com.lastminute.lastminuteserver.user.dto.UserProfileDto;
import com.lastminute.lastminuteserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // TODO : 캐싱 처리
    private final ForbiddenNameService forbiddenNameService;

    public UserProfileDto createNormalUser(UserCreateDto userCreate) {
        validateUserName(userCreate.nickname());

        User user = User.builder()
                .email(userCreate.email())
                .nickname(userCreate.nickname())
                .providerType(userCreate.providerType())
                .build();

        user = userRepository.save(user);
        return UserProfileDto.of(user);
    }

    private void validateUserName(String nickname) {
        final boolean isForbidden = forbiddenNameService.isForbiddenName(nickname);
        if (isForbidden) {
            throw RequestException.of(RequestExceptionCode.USER_NAME_NOT_ALLOWED);
        }
    }

    public UserProfileDto findUser(Long userId) {
        User user = findUserInternal(userId);
        return UserProfileDto.of(user);
    }

    public UserProfileDto updateUser(UserProfileDto request) {
        User user = findUserInternal(request.userId());

        validateUserName(request.nickname());

        user.updateProfile(request.nickname());
        user = userRepository.save(user);

        return UserProfileDto.of(user);
    }

    public UserProfileDto withdrawUser(Long userId) {
        User user = findUserInternal(userId);

        if (user.getAccountState().equals(AccountState.WITHDRAWN)) {
            throw RequestException.of(RequestExceptionCode.USER_ALREADY_WITHDRAWN);
        }

        user.withdraw();
        user = userRepository.save(user);

        return UserProfileDto.of(user);
    }

    private User findUserInternal(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> RequestException.of(RequestExceptionCode.USER_NOT_FOUND));
    }

}
