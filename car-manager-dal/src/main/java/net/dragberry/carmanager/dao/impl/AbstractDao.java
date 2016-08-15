package net.dragberry.carmanager.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import net.dragberry.carmanager.dao.DataAccessObject;
import net.dragberry.carmanager.domain.AbstractEntity;

public abstract class AbstractDao<E extends AbstractEntity> implements DataAccessObject<E, Long> {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	private final Class<E> entityType;
	
	public AbstractDao(Class<E> entityType) {
		this.entityType = entityType;
	}
	
	@Override
	public E findOne(Long entityKey) {
		return entityManager.find(entityType, entityKey);
	}
	
	@Override
	public List<E> fetchList() {
		return getEntityManager().createQuery("FROM " + getEntityName(), entityType).getResultList();
	}
	
	@Override
	public Long count() {
		return getEntityManager().createQuery("SELECT COUNT(e) FROM " + getEntityName() + " e", Long.class).getSingleResult();
	}
	
	@Override
	@Transactional
	public E create(E entity) {
		entityManager.persist(entity);
		return entity; 
	}
	
	@Override
	@Transactional
	public E update(E entity) {
		return entityManager.merge(entity);
	}
	
	@Override
	@Transactional
	public E delete(Long entityKey) {
		E entity = entityManager.find(entityType, entityKey);
		entityManager.remove(entity);
		return entity;
	}
	
	protected String getEntityName() {
		return entityType.getName();
	}
	
	protected Class<E> getEntityType() {
		return entityType;
	}
	
	protected EntityManager getEntityManager() {
		return entityManager;
	}
	
//	CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//	CriteriaQuery<E> cq = cb.createQuery(entityType);
//	Root<E> root = cq.from(entityType);
//	cq.select(root);
//	TypedQuery<E> typedQuery = entityManager.createQuery(cq);
//	return typedQuery.getResultList();
	
}
