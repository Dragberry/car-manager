package net.dragberry.carmanager.web.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

@Component
public class CustomCsrfHeaderFilter extends OncePerRequestFilter {

	private static final String XSRF_TOKEN = "XSRF-TOKEN";

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain fc)
			throws ServletException, IOException {
		CsrfToken csrf = (CsrfToken) req.getAttribute(CsrfToken.class.getName());
		if (csrf != null) {
			Cookie cookie = WebUtils.getCookie(req, XSRF_TOKEN);
			String token = csrf.getToken();
			if (cookie == null || token != null && !token.equals(cookie.getValue())) {
				cookie = new Cookie(XSRF_TOKEN, token);
				cookie.setPath("/");
				res.addCookie(cookie);
			}
		}
		fc.doFilter(req, res);
	}

}
