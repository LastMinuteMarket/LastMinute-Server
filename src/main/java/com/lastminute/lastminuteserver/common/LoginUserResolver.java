package com.lastminute.lastminuteserver.common;

import com.lastminute.lastminuteserver.exceptions.RequestException;
import com.lastminute.lastminuteserver.exceptions.RequestExceptionCode;
import com.lastminute.lastminuteserver.user.domain.User;
import com.lastminute.lastminuteserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
@RequiredArgsConstructor
public class LoginUserResolver implements HandlerMethodArgumentResolver {
    private final UserRepository userRepository;
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(Long.class)
                && parameter.hasParameterAnnotation(LoginUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {
        LoginUser loginUser = parameter.getParameterAnnotation(LoginUser.class);
        Long userId = Long.parseLong(loginUser.userId());
        User user =  userRepository.findById(userId)
                .orElseThrow(() -> RequestException.of(RequestExceptionCode.USER_NOT_FOUND));
        if (!user.getAuthenticated()){
            throw RequestException.of(RequestExceptionCode.LOGIN_FIRST);
        }
        log.info(user.getId()+"는 결과임");
        return user.getId();
    }
}
