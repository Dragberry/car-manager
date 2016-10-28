package net.dragberry.carmanager.web.security;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class CustomerDetails extends User {

	private static final long serialVersionUID = -2611246446268300417L;
	
	private Long customerKey;
	
	private Set<String> roles;

	public CustomerDetails(Long customerKey, String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities, Set<String> roles) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.customerKey = customerKey;
		this.roles = roles;
	}
	
	public CustomerDetails(Long customerKey, String customerName, String password, List<GrantedAuthority> authorities, Set<String> roles) {
		this(customerKey, customerName, password, true, true, true, true, authorities, roles);
	}

	public Long getCustomerKey() {
		return customerKey;
	}
	
	public Set<String> getRoles() {
		return roles;
	}

}