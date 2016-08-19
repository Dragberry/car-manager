package net.dragberry.carmanager.web.controller;

import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.dragberry.carmanager.web.common.Constants;

@Controller
public class LoginController implements Serializable {

	private static final long serialVersionUID = 6310435142165380682L;

	private static Logger LOG = LogManager.getLogger(LoginController.class.getName());

	@RequestMapping(value = Constants.Path.LOGOUT)
	public String logout(HttpServletRequest requert) {
		try {
			requert.logout();
		} catch (ServletException e) {
			LOG.info("Error while logout action", e);
		}
		return Constants.View.HOME_REDIRECT;
	}

	@RequestMapping(value = Constants.Path.LOGIN, method = RequestMethod.GET)
	public String login() {
		return Constants.View.LOGIN;
	}

}
