package net.dragberry.carmanager.dao;

import java.util.List;

import net.dragberry.carmanager.domain.Car;

public interface CarDao extends DataAccessObject<Car, Long> {

	List<Car> fetchListForCustomer(Long customerKey);
}
