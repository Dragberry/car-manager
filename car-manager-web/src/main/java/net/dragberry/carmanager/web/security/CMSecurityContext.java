package net.dragberry.carmanager.web.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public final class CMSecurityContext {

	private CMSecurityContext() {}
	
	public static CustomerDetails getLoggedCustomer() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.isAuthenticated()) {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (principal instanceof CustomerDetails) {
				return (CustomerDetails) principal;
			}
		}
		return null;
	}
	
	public static Long getLoggedCustomerKey() {
		CustomerDetails details = getLoggedCustomer();
		return details == null ? CustomerDetails.ANONYMOUS_KEY : details.getCustomerKey();
	}
}
