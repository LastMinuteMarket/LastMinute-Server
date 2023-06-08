package com.lastminute.lastminuteserver.user.presentation;

import com.lastminute.lastminuteserver.user.dto.UserCreateDto;
import com.lastminute.lastminuteserver.user.dto.UserLoginDto;
import com.lastminute.lastminuteserver.user.dto.UserProfileDto;
import com.lastminute.lastminuteserver.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/openapi/users")
public class UserApiController {

    private final UserService userService;

    @PostMapping(value = "/signup")
    public ResponseEntity<UserProfileDto> signUp(@RequestBody @Valid UserCreateDto userCreateDto){
        UserProfileDto userProfileDto = userService.createNormalUser(userCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userProfileDto);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<UserProfileDto> login(@RequestBody @Valid UserLoginDto userLoginDto){
        UserProfileDto userProfileDto = userService.login(userLoginDto);
        return ResponseEntity.status(HttpStatus.OK).body(userProfileDto);
    }
}
