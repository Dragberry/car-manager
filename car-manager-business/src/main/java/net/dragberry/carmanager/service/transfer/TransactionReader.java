package net.dragberry.carmanager.service.transfer;

import java.io.InputStream;
import java.util.concurrent.BlockingQueue;

import net.dragberry.carmanager.transferobject.Record;

public interface TransactionReader {

	void read(InputStream inputStream, BlockingQueue<Record> recordQueue);
}
