package net.dragberry.carmanager.service;

import net.dragberry.carmanager.to.CarTO;
import net.dragberry.carmanager.to.ResultList;
import net.dragberry.carmanager.to.ResultTO;

public interface CarService {
	
	ResultList<CarTO> fetchCarsForCustomer(Long customerKey);
	
	ResultList<CarTO> fetchCarList(Long customerKey);

	ResultTO<CarTO> createCar(CarTO car);

}
