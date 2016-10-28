package net.dragberry.carmanager.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import net.dragberry.carmanager.dao.CarDao;
import net.dragberry.carmanager.domain.Car;

@Repository
public class CarDaoImpl extends AbstractDao<Car> implements CarDao {

	public CarDaoImpl() {
		super(Car.class);
	}

	@Override
	public List<Car> fetchListForCustomer(Long customerKey) {
		return getEntityManager().createNamedQuery(Car.CARS_FOR_CUSTOMER_QUERY, getEntityType())
			.setParameter("owner", customerKey)
			.getResultList();
	}

}
