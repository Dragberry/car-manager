package net.dragberry.carmanager.service.transfer;

import java.io.InputStream;

/**
 * Data importer interface
 * 
 * @author Maksim Drahun
 *
 */
public interface DataImporter {

	/**
	 * Do import
	 * 
	 * @param is
	 * @throws Exception
	 */
	void doImport(InputStream is) throws Exception;
}
