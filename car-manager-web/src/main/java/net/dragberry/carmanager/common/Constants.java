package net.dragberry.carmanager.common;

public interface Constants {
	
	interface Path {
		// home
		String HOME = "/";
		String LOGIN = "/login";
		String LOGOUT = "/logout";
		String ACCESS_DENIED = "/access-denied";
		String REGISTRATION = "/registration";
	}
	
	static public interface View {
		// home
		String HOME = "home/index";
		String LOGIN = "home/login";
		String HOME_REDIRECT = "redirect:" + Path.HOME;
		String REGISTRATION = "home/registration";
	}
	
}
