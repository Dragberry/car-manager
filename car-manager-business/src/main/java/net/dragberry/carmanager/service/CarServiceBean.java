package net.dragberry.carmanager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.dragberry.carmanager.dao.CarDao;
import net.dragberry.carmanager.domain.Car;
import net.dragberry.carmanager.to.CarTO;
import net.dragberry.carmanager.to.ResultList;

@Service
public class CarServiceBean implements CarService {
	
	@Autowired
	private CarDao carDao;

	@Override
	public ResultList<CarTO> fetchCarsForCustomer(Long customerKey) {
		List<Car> cars = carDao.fetchListForCustomer(customerKey);
		ResultList<CarTO> carList = new ResultList<>();
		cars.forEach(car -> {
			CarTO carTO = new CarTO();
			carTO.setCarKey(car.getEntityKey());
			carTO.setModel(car.getModel());
			carTO.setBrand(car.getBrand());
			carTO.setPurchaseDate(car.getPurchaseDate());
			carTO.setSaleDate(car.getSaleDate());
			carList.addItem(carTO);
		});
		return carList;
	}

	@Override
	public ResultList<CarTO> fetchCarList(Long customerKey) {
		return null;
	}

}
