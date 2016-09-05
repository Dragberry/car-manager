package net.dragberry.carmanager.dao.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

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
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Car> cq = cb.createQuery(Car.class);
		Root<Car> cRoot = cq.from(Car.class);
		cq.where(cb.equal(cRoot.get("owner"), customerKey));
		cq.select(cRoot);
		return getEntityManager().createQuery(cq).getResultList();
	}

}
