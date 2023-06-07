package com.lastminute.lastminuteserver.user.service;

import com.lastminute.lastminuteserver.exceptions.RequestExceptionCode;
import com.lastminute.lastminuteserver.exceptions.RequestException;
import com.lastminute.lastminuteserver.user.domain.AccountState;
import com.lastminute.lastminuteserver.user.domain.User;
import com.lastminute.lastminuteserver.user.dto.UserCreateDto;
import com.lastminute.lastminuteserver.user.dto.UserLoginDto;
import com.lastminute.lastminuteserver.user.dto.UserProfileDto;
import com.lastminute.lastminuteserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // TODO : 캐싱 처리
    private final ForbiddenNameService forbiddenNameService;

    public UserProfileDto createNormalUser(UserCreateDto userCreate) {
        validateUserName(userCreate.nickname());

        User user = userCreate.toEntity();

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

    public UserProfileDto login(UserLoginDto userLoginDto) {
        User user = getUserByNickname(userLoginDto.getNickname());
        if (!user.getPassword().equals(userLoginDto.getPassword())){
            throw RequestException.of(RequestExceptionCode.PASSWORD_NOT_CORRECT);
        }
        user.setAuthenticated(true);
        userRepository.saveAndFlush(user);

        return UserProfileDto.of(user);
    }

    @Scheduled(cron = "0 */5 * * * *") // 매 5분마다 로그아웃. (테스트는 매 5초마다로 진행)
    public void logout(){
        List<User> userList = userRepository.findAllByAuthenticated(true);
        for (User user : userList){
            user.setAuthenticated(false);
        }
        userRepository.saveAllAndFlush(userList);
    }

    public boolean isActivateUser(Long userId) {
        User user = findUserInternal(userId);
        return user.getAccountState().equals(AccountState.NORMAL);
    }

    public User authenticate(Long userId){
        User user =  userRepository.findById(userId)
                .orElseThrow(() -> RequestException.of(RequestExceptionCode.USER_NOT_FOUND));
        if (!user.getAuthenticated()){
            throw RequestException.of(RequestExceptionCode.LOGIN_FIRST);
        }
        return user;
    }

    private User findUserInternal(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> RequestException.of(RequestExceptionCode.USER_NOT_FOUND));
    }

    private User getUserByNickname(String nickname){
        return userRepository.findByNickname(nickname)
                .orElseThrow(() -> RequestException.of(RequestExceptionCode.USER_NOT_FOUND));
    }
}
