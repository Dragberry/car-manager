package net.dragberry.carmanager.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.dragberry.carmanager.service.CarService;
import net.dragberry.carmanager.to.CarTO;
import net.dragberry.carmanager.to.ResultTO;
import net.dragberry.carmanager.web.security.CMSecurityContext;

@Controller
public class CarController {
	
	public static final String SERVICE_URL = "/service/car";
	public static final String LIST_URL = SERVICE_URL + "/list";
	public static final String SUBMIT_URL = SERVICE_URL + "/submit";
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private CarService carService;
	
	@Autowired
    private MessageSource messageSource;
	
	@RequestMapping(CarController.LIST_URL)
	@ResponseBody
	public List<CarTO> fetchCarList() {
		return carService.fetchCarsForCustomer(CMSecurityContext.getLoggedCustomerKey()).getResult();
	}
	
	@RequestMapping(CarController.SUBMIT_URL)
	@ResponseBody
	public ResultTO<CarTO> submitCar(@RequestBody CarTO car) {
		car.setCustomerKey(CMSecurityContext.getLoggedCustomerKey());
		ResultTO<CarTO> result = carService.createCar(car);
		if (result.hasIssues()) {
			result.getIssues().forEach(issue -> {
				issue.setMessage(messageSource.getMessage(issue.getMsgCode(), issue.getParams(), request.getLocale()));
			});
		}
		return result;
	}
}
