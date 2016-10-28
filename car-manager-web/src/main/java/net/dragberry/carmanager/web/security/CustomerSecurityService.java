package net.dragberry.carmanager.web.security;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import net.dragberry.carmanager.service.CustomerService;
import net.dragberry.carmanager.to.CustomerTO;

public class CustomerSecurityService implements UserDetailsService {
	
	private static final String ROLE_PREFIX = "ROLE_";
	
	private final CustomerService customerSevice;
	
	@Autowired
	public CustomerSecurityService(CustomerService customerSevice) {
		this.customerSevice = customerSevice;
	}

	@Override
	public UserDetails loadUserByUsername(String customerName) throws UsernameNotFoundException {
		CustomerTO customerTO = customerSevice.findByCustomerName(customerName);
		if (customerTO != null) {
			List<GrantedAuthority> authorities = new ArrayList<>();
			for (String role : customerTO.getRoles()) {
				authorities.add(new SimpleGrantedAuthority(createSpringRole(role)));
			}
			return new CustomerDetails(customerTO.getCustomerKey(), customerTO.getCustomerName(), customerTO.getPassword(), authorities, customerTO.getRoles());
		}
		throw new UsernameNotFoundException(MessageFormat.format("The customer '%s' is not found", customerName));
	}

	private String createSpringRole(String role) {
		return ROLE_PREFIX + role;
	}

}