package com.lastminute.lastminuteserver.user.presentation;

import com.lastminute.lastminuteserver.user.dto.UserCreateDto;
import com.lastminute.lastminuteserver.user.dto.UserLoginDto;
import com.lastminute.lastminuteserver.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/openapi/users")
public class UserApiController {

    private final UserService userService;

    @PostMapping(value = "/signUp")
    public ResponseEntity<Object> signUp(@Valid UserCreateDto userCreateDto){
        userService.createNormalUser(userCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping(value = "/login")
    public ResponseEntity<String> login(@Valid UserLoginDto userLoginDto){
        String accessToken = userService.login(userLoginDto);
        return ResponseEntity.status(HttpStatus.OK).body(accessToken);
    }
}
