package com.lastminute.lastminuteserver.user.dto;

import com.lastminute.lastminuteserver.user.domain.ForbiddenName;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record ForbiddenNameDto (
    @NotNull
    @Size(max = 12, message = "12자 내외의 이름을 입력해주세요.")
    String name,

    @NotNull
    @Size(max = 30, message = "30자 내외의 사유를 입력해주세요.")
    String reason
) {
    public ForbiddenName toEntity() {
        return ForbiddenName.builder()
                .name(this.name)
                .reason(this.reason)
                .build();
    }

    public static ForbiddenNameDto of(ForbiddenName entity) {
        return ForbiddenNameDto.builder()
                .name(entity.getName())
                .reason(entity.getReason())
                .build();
    }
}
