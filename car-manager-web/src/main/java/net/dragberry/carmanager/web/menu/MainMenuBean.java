package net.dragberry.carmanager.web.menu;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Controller;

import net.dragberry.carmanager.menu.MainMenu;
import net.dragberry.carmanager.menu.service.MenuService;
import net.dragberry.carmanager.web.security.CMSecurityContext;


@Controller
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MainMenuBean implements Serializable {

	private static final long serialVersionUID = 5082686956970424579L;
	
	@Autowired
	private MenuService menuService;
	
	private Long customerKey;
	
	private MainMenu mainMenu;
	
	public void reload(Long customerKey) {
		if (this.customerKey == null || !this.customerKey.equals(customerKey) || mainMenu == null) {
			this.customerKey = customerKey;
			mainMenu = menuService.fetchMenuForCustomer(CMSecurityContext.getCustomerRoles());
		}
	}
	
	public MainMenu getMainMenu() {
		return mainMenu;
	}
	
}
