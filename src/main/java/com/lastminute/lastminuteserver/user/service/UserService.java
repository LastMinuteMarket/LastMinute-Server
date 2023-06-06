package com.lastminute.lastminuteserver.user.service;

import com.lastminute.lastminuteserver.exceptions.RequestExceptionCode;
import com.lastminute.lastminuteserver.exceptions.RequestException;
import com.lastminute.lastminuteserver.security.CustomUserDetailsService;
import com.lastminute.lastminuteserver.security.JwtTokenProvider;
import com.lastminute.lastminuteserver.user.domain.AccountState;
import com.lastminute.lastminuteserver.user.domain.User;
import com.lastminute.lastminuteserver.user.dto.UserCreateDto;
import com.lastminute.lastminuteserver.user.dto.UserLoginDto;
import com.lastminute.lastminuteserver.user.dto.UserProfileDto;
import com.lastminute.lastminuteserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    // TODO : 캐싱 처리
    private final ForbiddenNameService forbiddenNameService;

    @Transactional
    public UserProfileDto createNormalUser(UserCreateDto userCreate) {
        validateUserName(userCreate.nickname());

        User user = User.builder()
                .email(userCreate.email())
                .nickname(userCreate.nickname())
                .password(passwordEncoder.encode(userCreate.password()))
                .providerType(userCreate.providerType())
                .build();

        user = userRepository.save(user);
        return UserProfileDto.of(user);
    }

    @Transactional
    public String login(UserLoginDto userLoginDto){
        Authentication authentication = authentication(userLoginDto.getNickname(),
                userLoginDto.getPassword());
        return jwtTokenProvider.generateAccessToken(authentication);
    }

    private Authentication authentication(String nickName, String password){
        try {
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(nickName, password)
            );
        } catch (Exception e){
            throw new RuntimeException("인증 실패");
        }
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

    public boolean isActivateUser(Long userId) {
        User user = findUserInternal(userId);
        return !user.getAccountState().equals(AccountState.NORMAL);
    }

    private User findUserInternal(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> RequestException.of(RequestExceptionCode.USER_NOT_FOUND));
    }

}
