package com.example.InventoryManagementSystem.config;

import com.example.InventoryManagementSystem.security.CustomerDetailsImpl;
import com.example.InventoryManagementSystem.service.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity//from spring security 6 have to use this annotation for authorization
public class SecurityConfig {
//    @Autowired
//    private CustomerDetailsImpl customerServiceImpl;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
//
//    @Bean
//    public DaoAuthenticationProvider authenticationProvider(){//configuration of UserDetailsService(I) style spring data jpa environment AuthenticationProvider
//        DaoAuthenticationProvider authProvider=new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(this.customerServiceImpl);
//        authProvider.setPasswordEncoder(passwordEncoder());
//        return  authProvider;
//    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
             http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()

                .authorizeHttpRequests(auth->{
                    auth.anyRequest().authenticated();
                })
                .httpBasic()
                        .and().exceptionHandling().accessDeniedPage("/denied");

                //http.authenticationProvider(authenticationProvider());
        return http.build();
    }
}
