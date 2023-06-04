package com.lastminute.lastminuteserver.user.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserLoginDto {
    @NotNull(message = "이름을 입력해주세요.")
    @Size(min = 2, max = 12, message = "2 ~ 12자의 이름을 입력해주세요.")
    String nickname;
    @NotNull(message = "비밀번호를 입력해주세요.")
    @Size(min = 2, max = 12, message = "2 ~ 12자의 이름을 입력해주세요.")
    String password;
}
