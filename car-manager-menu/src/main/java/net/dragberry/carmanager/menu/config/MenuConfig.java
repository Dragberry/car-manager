package net.dragberry.carmanager.menu.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import net.dragberry.carmanager.menu.service.MenuService;

@Configuration
@ComponentScan(basePackageClasses = { MenuService.class })
public class MenuConfig {

}
