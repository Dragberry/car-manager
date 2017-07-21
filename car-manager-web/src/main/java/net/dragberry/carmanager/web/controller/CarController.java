package net.dragberry.carmanager.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.dragberry.carmanager.service.CarService;
import net.dragberry.carmanager.to.CarTO;
import net.dragberry.carmanager.web.security.CMSecurityContext;

@Controller
public class CarController {
	
	public static final String SERVICE_URL = "/service/car";
	public static final String LIST_URL = SERVICE_URL + "/list";
	
	
	@Autowired
	private CarService carService;

	
	@RequestMapping(CarController.LIST_URL)
	@ResponseBody
	public List<CarTO> fetchCarList() {
		return carService.fetchCarsForCustomer(CMSecurityContext.getLoggedCustomerKey()).getResult();
	}
	
	
}
