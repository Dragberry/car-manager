package net.dragberry.carmanager.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.dragberry.carmanager.service.CarService;
import net.dragberry.carmanager.to.CarTO;

@Controller
public class CarController {
	
	@Autowired
	private CarService carService;

	@RequestMapping("/service/car/list")
	@ResponseBody
	public List<CarTO> fetchCarList(@RequestParam("customerKey") Long customerKey) {
		return carService.fetchCarsForCustomer(customerKey).getResult();
	}
}
