package com.lastminute.lastminuteserver.user.domain;

import com.lastminute.lastminuteserver.exceptions.RequestException;
import com.lastminute.lastminuteserver.exceptions.RequestExceptionCode;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
public class User {

    public static final String WITH_DRAWN_NICKNAME = "탈퇴한 사용자";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(length = 12)
    private String nickname;

    @Column(length = 255)
    private String email;

    @NotNull
    @Column(length = 12)
    private String password;

    @Setter
    @NotNull
    @Column(length = 5)
    @Enumerated(EnumType.STRING)
    AccountRole accountRole = AccountRole.USER;

    @Setter
    @NotNull
    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    AccountState accountState = AccountState.NORMAL;

//    @NotNull
//    @Column(length = 10)
//    @Enumerated(EnumType.STRING)
//    ProviderType providerType;

    @Setter
    @NotNull
    private Boolean authenticated = false;

    public void withdraw() {
        this.updateProfile(WITH_DRAWN_NICKNAME, null);
        this.accountState = AccountState.WITHDRAWN;
    }

    public void updateProfile(String nickname) {
        updateProfile(nickname, this.email);
    }

    public void updateProfile(String nickname, String email) {
        if (!this.accountState.isAccessible()) {
            throw RequestException.of(RequestExceptionCode.USER_ILLEGAL_STATE);
        }

        this.nickname = nickname;
        this.email = email;
    }


    @Builder
    public User(String nickname, String email, String password) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
//        this.accountRole = accountRole;
//        this.accountState = accountState;
//        this.providerType = providerType;
    }
}
