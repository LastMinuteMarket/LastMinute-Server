package com.lastminute.lastminuteserver.user.domain;

import com.lastminute.lastminuteserver.exceptions.RequestException;
import com.lastminute.lastminuteserver.exceptions.RequestExceptionCode;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
public class User implements UserDetails {

    public static final String WITH_DRAWN_NICKNAME = "탈퇴한 사용자";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(length = 12, unique = true)
    private String nickname;

    @Column(length = 255)
    private String email;

    @NotNull
    @Column(length = 12)
    private String password;

    @NotNull
    @Column(length = 5)
    @Enumerated(EnumType.STRING)
    AccountRole accountRole = AccountRole.USER;

    @NotNull
    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    AccountState accountState = AccountState.NORMAL;

    @NotNull
    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    ProviderType providerType;

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
    public User(String nickname, String email, String password,
                AccountRole accountRole, AccountState accountState, ProviderType providerType) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.accountRole = accountRole;
        this.accountState = accountState;
        this.providerType = providerType;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> roles = new HashSet<>();
        roles.add(new SimpleGrantedAuthority(accountRole.getValue()));
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return nickname;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
