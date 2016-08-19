package net.dragberry.carmanager.menu.service;

import java.io.Serializable;
import java.util.Set;

import net.dragberry.carmanager.menu.MainMenu;

public interface MenuService extends Serializable {

	MainMenu fetchMenuForCustomer(Set<String> customerRoles);
}
