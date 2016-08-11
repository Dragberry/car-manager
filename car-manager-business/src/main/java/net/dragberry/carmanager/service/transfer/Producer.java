package net.dragberry.carmanager.service.transfer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import net.dragberry.carmanager.repository.CustomerRepo;
import net.dragberry.carmanager.transferobject.Record;

@Component
@Scope("prototype")
public class Producer extends Thread {
	
	private String name;

	@Autowired
	private BlockingQueue<Record> queue;
	@Autowired
	private CustomerRepo customerRepo;
	
	public Producer(String name) {
		this.name = name;
	}
	
	@Override
	public void run() {
		for (int i = 0; i < 8000; i++) {
			try {
				queue.put(new Record());
				System.out.println(Thread.currentThread().getName()+ ": " + i + " " + name);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			customerRepo.findOne(1L);
		}
	}

}
