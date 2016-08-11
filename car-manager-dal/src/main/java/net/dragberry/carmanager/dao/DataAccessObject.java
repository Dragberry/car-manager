package net.dragberry.carmanager.dao;

import java.io.Serializable;
import java.util.List;

import net.dragberry.carmanager.domain.AbstractEntity;

/**
 * 
 * @author Maksim Drahun
 *
 */
public interface DataAccessObject<E extends AbstractEntity, ID extends Serializable> {

	E findOne(ID entityKey);
	
	List<E> fetchList();
	
	E create(E entity);
	
	E update(E entity);
	
	E delete(ID entityKey);
	
}
