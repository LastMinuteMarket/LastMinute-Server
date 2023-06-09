//package com.lastminute.lastminuteserver.config;
//
//import com.lastminute.lastminuteserver.security.CustomUserDetailsService;
//import com.lastminute.lastminuteserver.security.JwtRequestFilter;
//import com.lastminute.lastminuteserver.security.JwtTokenProvider;
//import com.lastminute.lastminuteserver.user.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@RequiredArgsConstructor
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//@Configuration
//public class SecurityConfig implements WebMvcConfigurer {
//    private final JwtTokenProvider jwtTokenProvider;
//    private final UserRepository userRepository;
//
//    @Bean
//    public UserDetailsService userDetailsService(){
//        return new CustomUserDetailsService(userRepository);
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
//            throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity
//                .csrf((csrf) -> csrf.disable())
//                .sessionManagement((sessionManagement) -> {
//                    sessionManagement.sessionCreationPolicy(SessionCreationPolicy.NEVER);
//                })
//                .formLogin((formLogin) -> formLogin.disable())
//                .httpBasic((httpBasic) -> httpBasic.disable())
//                .authorizeRequests().requestMatchers("/v1/openapi/users/**").permitAll()
//                .and()
//                .authorizeRequests().anyRequest().authenticated()
//                .and()
//                .addFilterBefore(new JwtRequestFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
//                .authorizeRequests().anyRequest().permitAll();
//
//        return httpSecurity.build();
//    }
//}
