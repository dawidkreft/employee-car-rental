package pl.kreft.thesis.ecr.centralsystem.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;
import pl.kreft.thesis.ecr.centralsystem.user.service.UserDetailsProvider;

import static pl.kreft.thesis.ecr.centralsystem.common.RequestPageMappingInfo.HOME_PAGE_REQUEST;
import static pl.kreft.thesis.ecr.centralsystem.common.RequestPageMappingInfo.LOGIN_PAGE_REQUEST;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsProvider userDetailsProvider;
    private final AccessDeniedHandler accessDeniedHandler;

    @Autowired
    public WebSecurityConfig(UserDetailsProvider userDetailsProvider,
            AccessDeniedHandler accessDeniedHandler) {
        this.userDetailsProvider = userDetailsProvider;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/", HOME_PAGE_REQUEST)
            .permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .formLogin()
            .loginPage(LOGIN_PAGE_REQUEST)
            .permitAll()
            .and()
            .exceptionHandling()
            .accessDeniedHandler(accessDeniedHandler)
            .and()
            .logout()
            .permitAll();

    }
}

