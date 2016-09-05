package net.dragberry.carmanager.web.common;

public interface Constants {
	
	interface Path {
		static String redirect(String path) {
			return REDIRECT + path;
		}
		String REDIRECT = "redirect:";
		// home
		String HOME = "/";
		String LOGIN = "/login";
		String LOGOUT = "/logout";
		String ACCESS_DENIED = "/access-denied";
		String REGISTRATION = "/registration";
		
		String TRANSACTION_LIST = "/transaction/list";
		String TRANSACTION_CREATE = "/transaction/create";
	}
	
	static public interface View {
		// home
		String HOME = "home/index";
		String LOGIN = "home/login";
		String REGISTRATION = "home/registration";
		
		String TRANSACTION_LIST = "transaction/transaction-list";
		String TRANSACTION_CREATE = "transaction/transaction-create";
	}
	
}
