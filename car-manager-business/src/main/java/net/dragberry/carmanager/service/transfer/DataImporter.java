package net.dragberry.carmanager.service.transfer;

import java.io.InputStream;

import net.dragberry.carmanager.to.ResultTO;
import net.dragberry.carmanager.to.UploadTransactionResult;

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
	 * @return
	 */
	ResultTO<UploadTransactionResult> doImport(InputStream is) throws Exception;
}
