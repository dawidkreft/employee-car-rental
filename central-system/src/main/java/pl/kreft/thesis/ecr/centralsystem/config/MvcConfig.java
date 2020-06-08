package pl.kreft.thesis.ecr.centralsystem.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static pl.kreft.thesis.ecr.centralsystem.common.RequestPageMappingInfo.HOME_PAGE_REQUEST;
import static pl.kreft.thesis.ecr.centralsystem.common.RequestPageMappingInfo.LOGIN_PAGE_REQUEST;
import static pl.kreft.thesis.ecr.centralsystem.common.ResponsePageMappingInfo.HOME_PAGE;
import static pl.kreft.thesis.ecr.centralsystem.common.ResponsePageMappingInfo.LOGIN_PAGE;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController(HOME_PAGE_REQUEST).setViewName(HOME_PAGE);
        registry.addViewController("/").setViewName(HOME_PAGE);
        registry.addViewController(LOGIN_PAGE_REQUEST).setViewName(LOGIN_PAGE);
    }
}