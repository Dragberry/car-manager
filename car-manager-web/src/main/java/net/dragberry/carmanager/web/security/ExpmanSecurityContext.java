package net.dragberry.carmanager.web.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class ExpmanSecurityContext {

	private static final long ANONYMOUS_CUSTOMER_KEY = 2L;
	
	private static final String ANONYMOUS_CUSTOMER_NAME = "Guest";
	
	private static final String ANONYMOUS_ROLE = "ANONYMOUS";

	public static Long getCustomerKey() {
		CustomerDetails details = getCustomerDetails();
		return details == null ? ANONYMOUS_CUSTOMER_KEY : details.getCustomerKey();
	}

	public static String getCustomerName() {
		CustomerDetails details = getCustomerDetails();
		return details == null ? ANONYMOUS_CUSTOMER_NAME : details.getUsername();
	}
	
	public static Set<String> getCustomerRoles() {
		CustomerDetails details = getCustomerDetails();
		Set<String> roles = new HashSet<>();
		roles.add(ANONYMOUS_ROLE);
		return details == null ? roles : details.getRoles();
	}
	
	private static Authentication getAuthentification() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
	
	private static CustomerDetails getCustomerDetails() {
		Authentication authentication = getAuthentification();
		if (authentication != null) {
			Object principal =  getAuthentification().getPrincipal();
			if (principal instanceof CustomerDetails) {
				return (CustomerDetails) principal;
			}
		}
		return null;
	}
}
