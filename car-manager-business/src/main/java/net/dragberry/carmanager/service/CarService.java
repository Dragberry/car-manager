package net.dragberry.carmanager.service;

import net.dragberry.carmanager.to.CarTO;
import net.dragberry.carmanager.to.ResultList;

public interface CarService {
	
	ResultList<CarTO> fetchCarsForCustomer(Long customerKey);

}
