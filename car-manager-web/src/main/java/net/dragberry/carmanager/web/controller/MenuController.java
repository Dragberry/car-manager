package net.dragberry.carmanager.web.controller;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.dragberry.carmanager.menu.MainMenu;
import net.dragberry.carmanager.menu.MenuItem;
import net.dragberry.carmanager.menu.service.MenuService;
import net.dragberry.carmanager.web.security.CMSecurityContext;
import net.dragberry.carmanager.web.security.CustomerDetails;

@Controller
public class MenuController {
	
	@Autowired
	private MenuService menuService;
	
	@RequestMapping(value = "/service/menu", method = RequestMethod.GET)
	@ResponseBody 
	public MainMenu getMenu() {
		CustomerDetails details = CMSecurityContext.getLoggedCustomer();
		Set<String> roles = null;
		if (details != null) {
			roles = details.getRoles();
		} else {
			roles = new HashSet<>();
			roles.add(CustomerDetails.ANONYMOUS_ROLE);
		}
		MainMenu mainMenu = menuService.fetchMenuForCustomer(roles);
		MainMenu reducedMainMenu = new MainMenu();
		mainMenu.getItems().forEach(menuItem -> {
			if (!menuItem.isDisabled() && menuItem.isVisibled()) {
				MenuItem newItem = copyItem(menuItem);
				if (menuItem.isHeader()) {
					menuItem.getItems().forEach(subItem -> {
						if (!subItem.isDisabled() && menuItem.isVisibled()) {
							newItem.addItem(copyItem(subItem));
						}
					});
				}
				reducedMainMenu.addItem(newItem);
			}
		});
		return reducedMainMenu;
	}
	
	private static MenuItem copyItem(MenuItem menuItem) {
		MenuItem newItem = new MenuItem();
		newItem.setAction(menuItem.getAction());
		newItem.setKey(menuItem.getKey());
		newItem.setId(menuItem.getId());
		return newItem;
	}

}
