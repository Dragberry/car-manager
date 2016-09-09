package net.dragberry.carmanager.web.security;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import net.dragberry.carmanager.service.CustomerService;
import net.dragberry.carmanager.to.CustomerSettingsTO;
import net.dragberry.carmanager.web.common.Constants;

@Service
public class AuthSuccessHandler implements AuthenticationSuccessHandler {
	
	private static final String CUSTOMER_SETTINGS = "customerSettings";
	
	@Autowired
	private CustomerService customerService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res, Authentication auth)
			throws IOException, ServletException {
		CustomerSettingsTO settings = customerService.fetchCustomerSettings(CMSecurityContext.getCustomerKey());
		req.getSession().setAttribute(CUSTOMER_SETTINGS, settings);
		res.setStatus(HttpStatus.OK.value());
		res.sendRedirect(req.getContextPath() + Constants.Path.HOME);
		
	}


}
