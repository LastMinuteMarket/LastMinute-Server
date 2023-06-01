package com.lastminute.lastminuteserver.user.dto;

import com.lastminute.lastminuteserver.user.domain.User;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record UserProfileDto (
    @NotNull
    Long userId,

    @NotNull(message = "이름을 입력해주세요.")
    @Size(min = 2, max = 12, message = "2 ~ 12자의 이름을 입력해주세요.")
    String nickname,

    @Nullable
    @Size(max = 255, message = "255자를 초과한 이메일 주소는 입력할 수 없습니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    String email
) {
    public static UserProfileDto of(User entity) {
        return UserProfileDto.builder()
                .userId(entity.getId())
                .nickname(entity.getNickname())
                .email(entity.getEmail())
                .build();
    }
}