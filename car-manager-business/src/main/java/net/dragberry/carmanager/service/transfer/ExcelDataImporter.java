package net.dragberry.carmanager.service.transfer;

import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import net.dragberry.carmanager.to.ResultTO;
import net.dragberry.carmanager.to.UploadTransactionResult;

/**
 * Excel data importer
 * 
 * @author Maksim Drahun
 *
 */
@Service("ExcelDataImporter")
public class ExcelDataImporter implements DataImporter {
	
	private static final Logger LOG = LogManager.getLogger(ExcelDataImporter.class);
	
	@Autowired
	private ApplicationContext appContext;
	
	@Override
	public ResultTO<UploadTransactionResult> doImport(InputStream is) throws Exception {
		LOG.info("ExcelDataImporter.doImport started...");
		
		UploadTransactionResult result = new UploadTransactionResult();
		
		List<Future<UploadTransactionResult>> resultList = new ArrayList<>();
		try {
			int availableProcessors = Runtime.getRuntime().availableProcessors();
			ExecutorService executor = Executors.newFixedThreadPool(availableProcessors);
			Producer producer = appContext.getBean(Producer.class);
			producer.setInputStream(is);
			executor.submit(producer);
			
			for (int i = 0; i < availableProcessors - 1; i++) {
				Consumer consumer = appContext.getBean(Consumer.class);
				resultList.add(executor.submit(consumer));
			}
			while (!resultList.isEmpty()) {
				Iterator<Future<UploadTransactionResult>> iter = resultList.iterator();
				while (iter.hasNext()) {
					Future<UploadTransactionResult> future = iter.next();
					if (future.isDone()) {
						try {
							UploadTransactionResult threadResult = future.get();
							LOG.info(MessageFormat.format("Data importer task completed: successful={0}; failed={1}", threadResult.getSuccessfulTransactions(), threadResult.getFailedTransactions()));
							result.add(threadResult);
							iter.remove();
						} catch (Exception exc) {
							LOG.error("An error has occured!", exc);
						}
					}
				}
			}
			
			executor.shutdown();
			executor.awaitTermination(10, TimeUnit.MINUTES);
			LOG.info("ExcelDataImporter.doImport finished...");
			return new ResultTO<UploadTransactionResult>(result);
		} catch (Exception exc) {
			LOG.error("An error has occured!", exc);
			throw new RuntimeException(exc);
		}
		
	}
}
