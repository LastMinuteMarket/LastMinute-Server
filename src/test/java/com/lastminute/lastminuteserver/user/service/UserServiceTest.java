package com.lastminute.lastminuteserver.user.service;

import com.lastminute.lastminuteserver.exceptions.RequestException;
import com.lastminute.lastminuteserver.user.domain.AccountRole;
import com.lastminute.lastminuteserver.user.domain.AccountState;
import com.lastminute.lastminuteserver.user.domain.ProviderType;
import com.lastminute.lastminuteserver.user.domain.User;
import com.lastminute.lastminuteserver.user.dto.UserCreateDto;
import com.lastminute.lastminuteserver.user.dto.UserProfileDto;
import com.lastminute.lastminuteserver.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    
    @Mock
    private ForbiddenNameService forbiddenNameService;

    @InjectMocks
    private UserService userService;

    @Nested
    @DisplayName("사용자 생성")
    class createUser {

        @BeforeEach
        public void setupEmptyForbiddenNames() {
            given(forbiddenNameService.isForbiddenName(anyString())).willReturn(false);
        }

        @Test
        @DisplayName("모든 값이 있고 정상적일 때 성공")
        public void createByFullInfo() {
            // given
            UserCreateDto request = UserCreateDto.builder()
                    .email("myemail@gmail.com")
                    .nickname("james")
                    .providerType(ProviderType.KAKAO)
                    .build();

            User createdUser = request.toEntity();
            Long userId = 412L;
            ReflectionTestUtils.setField(createdUser, "id", userId);

            given(userRepository.save(any())).willReturn(createdUser);

            // when
            UserProfileDto response = userService.createNormalUser(request);

            // then
            assertThat(response.userId()).isEqualTo(userId);
            assertThat(response.nickname()).isEqualTo(request.nickname());
        }

        @Test
        @DisplayName("필수 값만 있고 정상적일 때 성공")
        public void createByRequiredInfo() {
            // given
            UserCreateDto request = UserCreateDto.builder()
                    .nickname("james")
                    .providerType(ProviderType.KAKAO)
                    .build();
            User createdUser = request.toEntity();
            Long userId = 412L;
            ReflectionTestUtils.setField(createdUser, "id", userId);

            given(userRepository.save(any())).willReturn(createdUser);

            // when
            UserProfileDto response = userService.createNormalUser(request);

            // then
            assertThat(response.userId()).isEqualTo(userId);

// 사용자 이메일을 응답에서 제외하였으므로 테스트에서 제거하였습니다.
// since 23.06.03
//            assertThat(response.email()).isNull();
            assertThat(response.nickname()).isEqualTo(request.nickname());
        }
        
        @Test
        @DisplayName("금지된 이름으로 생성 시 예외")
        public void notAllowedUserName() {
            // given
            final String name = "탈퇴한 사용자";
            UserCreateDto request = UserCreateDto.builder()
                    .nickname(name)
                    .providerType(ProviderType.KAKAO)
                    .build();
            User createdUser = request.toEntity();
            Long userId = 412L;
            ReflectionTestUtils.setField(createdUser, "id", userId);
            
            given(forbiddenNameService.isForbiddenName(name)).willReturn(true);

            // when, then
            assertThatThrownBy(() -> userService.createNormalUser(request))
                    .isInstanceOf(RequestException.class)
                    .hasMessageContaining("사용할 수 없는 닉네임");
        }

    }

    @Nested
    @DisplayName("사용자 조회")
    class getUser {

        @Test
        @DisplayName("이용 중인 사용자 조회 성공")
        public void successGet() {
            // given
            User user = User.builder()
                    .email("myemail@gmail.com")
                    .nickname("james")
                    .providerType(ProviderType.KAKAO)
                    .accountRole(AccountRole.USER)
                    .accountState(AccountState.NORMAL)
                    .build();
            Long userId = 5326L;
            ReflectionTestUtils.setField(user, "id", userId);

            given(userRepository.findById(userId)).willReturn(Optional.of(user));

            // when
            UserProfileDto response = userService.findUser(userId);

            // then
            assertThat(response.userId()).isEqualTo(userId);
//            assertThat(response.email()).isEqualTo(user.getEmail());
            assertThat(response.nickname()).isEqualTo(user.getNickname());
        }

        @Test
        @DisplayName("해당하는 ID 없는 경우 예외")
        public void notFoundUser() {
            // given
            given(userRepository.findById(any())).willReturn(Optional.empty());

            // when, then
            assertThatThrownBy(() -> userService.findUser(14L))
                    .isInstanceOf(RequestException.class);
        }

        @Test
        @DisplayName("닉네임이 '탈퇴한 사용자'로 조회")
        public void withdrawnUser() {
            // given
            User user = User.builder()
                    .email("myemail@gmail.com")
                    .nickname("james")
                    .providerType(ProviderType.KAKAO)
                    .accountRole(AccountRole.USER)
                    .accountState(AccountState.NORMAL)
                    .build();
            Long userId = 5326L;
            ReflectionTestUtils.setField(user, "id", userId);

            user.withdraw();
            given(userRepository.findById(userId)).willReturn(Optional.of(user));

            // when
            UserProfileDto response = userService.findUser(userId);

            // then
            assertThat(response.userId()).isEqualTo(userId);
            assertThat(response.nickname()).isEqualTo("탈퇴한 사용자");
//            assertThat(response.email()).isNull();
        }
    }

    @Nested
    @DisplayName("사용자 탈퇴")
    class withdrawUser {

        @Test
        @DisplayName("사용자 존재하면 정상적으로 탈퇴")
        public void successWithdraw() {
            // given
            User user = User.builder()
                    .email("myemail@gmail.com")
                    .nickname("james")
                    .providerType(ProviderType.KAKAO)
                    .accountRole(AccountRole.USER)
                    .accountState(AccountState.NORMAL)
                    .build();
            Long userId = 5326L;
            ReflectionTestUtils.setField(user, "id", userId);

            given(userRepository.findById(userId)).willReturn(Optional.of(user));
            given(userRepository.save(any())).willReturn(user);

            // when
            UserProfileDto withdrawnUser = userService.withdrawUser(userId);

            // then
            assertThat(withdrawnUser.userId()).isEqualTo(userId);
            assertThat(withdrawnUser.nickname()).isEqualTo(User.WITH_DRAWN_NICKNAME);
        }

        @Test
        @DisplayName("이미 탈퇴한 사용자 예외")
        public void alreadyWithdrawn() {
            // given
            User user = User.builder()
                    .email("myemail@gmail.com")
                    .nickname("james")
                    .providerType(ProviderType.KAKAO)
                    .accountRole(AccountRole.USER)
                    .accountState(AccountState.NORMAL)
                    .build();
            Long userId = 5326L;
            ReflectionTestUtils.setField(user, "id", userId);

            user.withdraw();
            given(userRepository.findById(userId)).willReturn(Optional.of(user));

            // when, then
            assertThatThrownBy(() -> userService.withdrawUser(userId))
                    .isInstanceOf(RequestException.class);
        }
    }

    @Nested
    @DisplayName("사용자 정보 수정")
    class updateUser {

        @Test
        @DisplayName("정상적으로 수정 완료")
        public void updateSuccess() {
            // given
            User user = User.builder()
                    .email("myemail@gmail.com")
                    .nickname("james")
                    .providerType(ProviderType.KAKAO)
                    .accountRole(AccountRole.USER)
                    .accountState(AccountState.NORMAL)
                    .build();
            Long userId = 1512L;
            ReflectionTestUtils.setField(user, "id", userId);

            UserProfileDto request = UserProfileDto.builder()
                    .userId(userId)
//                    .email("different@gmail.com")
                    .nickname("nick")
                    .build();

            given(forbiddenNameService.isForbiddenName(anyString())).willReturn(false);
            given(userRepository.findById(userId)).willReturn(Optional.of(user));
            given(userRepository.save(any())).willReturn(entityFromUpdateDto(request));

            // when
            UserProfileDto response = userService.updateUser(request);

            // then
            assertThat(response.userId()).isEqualTo(request.userId());
//            assertThat(response.email()).isEqualTo(request.email());
            assertThat(response.nickname()).isEqualTo(request.nickname());
        }

        @Test
        @DisplayName("해당 ID의 사용자가 없을 때 예외")
        public void notFoundUser() {
            // given
            Long userId = 1512L;

            given(userRepository.findById(userId)).willReturn(Optional.empty());

            // when, then
            UserProfileDto request = UserProfileDto.builder()
                    .userId(userId)
//                    .email("different@gmail.com")
                    .nickname("nick")
                    .build();

            assertThatThrownBy(() -> userService.updateUser(request))
                    .isInstanceOf(RequestException.class);
        }

        @Test
        @DisplayName("금지된 이름으로 변경 시도 시 예외")
        public void forbiddenName() {
            // given
            User user = User.builder()
                    .email("myemail@gmail.com")
                    .nickname("james")
                    .providerType(ProviderType.KAKAO)
                    .accountRole(AccountRole.USER)
                    .accountState(AccountState.NORMAL)
                    .build();
            Long userId = 1512L;
            String tryName = "root";
            ReflectionTestUtils.setField(user, "id", userId);

            given(forbiddenNameService.isForbiddenName(tryName)).willReturn(true);
            given(userRepository.findById(userId)).willReturn(Optional.of(user));

            // when, then
            UserProfileDto request = UserProfileDto.builder()
                    .userId(userId)
//                    .email("different@gmail.com")
                    .nickname(tryName)
                    .build();

            assertThatThrownBy(() -> userService.updateUser(request))
                    .isInstanceOf(RequestException.class)
                    .hasMessageContaining("닉네임");
        }

        private static User entityFromUpdateDto(UserProfileDto dto) {
            User user = User.builder()
//                    .email(dto.email())
                    .nickname(dto.nickname())
                    .providerType(ProviderType.KAKAO)
                    .build();

            ReflectionTestUtils.setField(user, "id", dto.userId());
            return user;
        }

    }
}
