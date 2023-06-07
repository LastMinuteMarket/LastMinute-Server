package com.lastminute.lastminuteserver.user.dto;

import com.lastminute.lastminuteserver.common.EnumValid;
import com.lastminute.lastminuteserver.user.domain.ProviderType;
import com.lastminute.lastminuteserver.user.domain.User;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record UserCreateDto (
    @NotNull(message = "이름을 입력해주세요.")
    @Size(min = 2, max = 12, message = "2 ~ 12자의 이름을 입력해주세요.")
    String nickname,

    @Nullable
    @Size(max = 255, message = "255자를 초과한 이메일 주소는 입력할 수 없습니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    String email,

    @NotNull(message = "비밀번호를 입력해주세요.")
    @Size(min = 2, max = 12, message = "2 ~ 12자의 비밀번를호 입력해주세요.")
    String password,

    @NotNull
    @EnumValid(enumClass = ProviderType.class, message = "지원하지 않는 provider 입니다.")
    ProviderType providerType
) {

    public User toEntity() {
        return User.builder()
                .nickname(this.nickname)
                .email(this.email)
                .password(this.password)
                .providerType(this.providerType)
                .build();
    }
}
