package org.sparta.jenview.jwt_security.jwt;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Component
public class BlacklistFilter extends GenericFilterBean {

    private static final Set<String> BLACKLIST = new HashSet<>();

    static {
        // 예시로 블랙리스트에 추가된 사용자 ID
        BLACKLIST.add("blacklistedUser");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && BLACKLIST.contains(authentication.getName())) {
            response.getWriter().write("You are blacklisted");
            return;
        }
        chain.doFilter(request, response);
    }
}
