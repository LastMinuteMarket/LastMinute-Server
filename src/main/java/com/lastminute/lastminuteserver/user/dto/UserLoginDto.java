package com.lastminute.lastminuteserver.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserLoginDto {
    @NotBlank(message = "이름을 입력해주세요.")
    @Size(min = 2, max = 12, message = "2 ~ 12자의 이름을 입력해주세요.")
    String nickname;
    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 2, max = 12, message = "2 ~ 12자의 비밀번호 입력해주세요.")
    String password;
}