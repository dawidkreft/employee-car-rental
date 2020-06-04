package pl.kreft.thesis.ecr.centralsystem.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static pl.kreft.thesis.ecr.centralsystem.common.ResponsePageMappingInfo.ERROR_403;

@Slf4j
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse, AccessDeniedException e)
            throws IOException, ServletException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            log.info(String.format("User '%s' attempted to access the protected URL: %s",
                    auth.getName(), httpServletRequest.getRequestURI()));
        }

        httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + ERROR_403);
    }
}
