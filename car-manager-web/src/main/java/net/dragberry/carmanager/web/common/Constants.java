package net.dragberry.carmanager.web.common;

public interface Constants {
	
	interface Path {
		// home
		String HOME = "/";
		String LOGIN = "/login";
		String LOGOUT = "/logout";
		String ACCESS_DENIED = "/access-denied";
		String REGISTRATION = "/registration";
		
		String TRANSACTION_LIST = "/transaction/list";
	}
	
	static public interface View {
		// home
		String HOME = "home/index";
		String LOGIN = "home/login";
		String HOME_REDIRECT = "redirect:" + Path.HOME;
		String REGISTRATION = "home/registration";
		
		String TRANSACTION_LIST = "transaction/transaction-list";
	}
	
}
