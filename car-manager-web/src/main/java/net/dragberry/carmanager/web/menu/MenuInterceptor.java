package net.dragberry.carmanager.web.menu;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class MenuInterceptor extends HandlerInterceptorAdapter {
	
	@Autowired
	private MainMenuBean mainMenuBean;
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
		mainMenuBean.reload(net.dragberry.carmanager.web.security.CMSecurityContext.getCustomerKey());
		modelAndView.addObject("menuBean", mainMenuBean);
	}

}
