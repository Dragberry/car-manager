package net.dragberry.carmanager.dao.impl;

import org.springframework.stereotype.Repository;

import net.dragberry.carmanager.dao.FuelDao;
import net.dragberry.carmanager.domain.Fuel;

@Repository
public class FuelDaoImpl extends AbstractDao<Fuel> implements FuelDao {

	public FuelDaoImpl() {
		super(Fuel.class);
	}

}
