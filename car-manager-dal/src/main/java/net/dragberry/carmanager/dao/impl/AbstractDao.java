package net.dragberry.carmanager.dao.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.transaction.annotation.Transactional;

import net.dragberry.carmanager.dao.DataAccessObject;
import net.dragberry.carmanager.domain.AbstractEntity;
import net.dragberry.carmanager.transferobject.QueryListTO;

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
	
	protected <T> TypedQuery<T> prepateFetchQuery(String hql, Map<String, Object> params, Class<T> entityType) {
		TypedQuery<T> typedQuery = getEntityManager().createQuery(hql, entityType);
		params.entrySet().forEach(entry -> {
			typedQuery.setParameter(entry.getKey(), entry.getValue());
		});
		return typedQuery;
	}
	
	protected <T> TypedQuery<T> preparePageableQuery(String hql, QueryListTO query, Map<String, Object> params, Class<T> entityType) {
		return prepateFetchQuery(hql, params, entityType)
				.setFirstResult(query.getPageNumber() * query.getPageSize())
				.setMaxResults(query.getPageSize());
	}
	
	
}
