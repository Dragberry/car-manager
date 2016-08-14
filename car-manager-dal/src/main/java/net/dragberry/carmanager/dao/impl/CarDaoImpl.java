package net.dragberry.carmanager.dao.impl;

import org.springframework.stereotype.Repository;

import net.dragberry.carmanager.dao.CarDao;
import net.dragberry.carmanager.domain.Car;

@Repository
public class CarDaoImpl extends AbstractDao<Car> implements CarDao {

	public CarDaoImpl() {
		super(Car.class);
	}

}
