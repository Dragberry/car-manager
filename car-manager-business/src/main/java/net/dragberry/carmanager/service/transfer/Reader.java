package net.dragberry.carmanager.service.transfer;

import java.io.IOException;
import java.io.InputStream;
import java.time.ZoneId;
import java.util.concurrent.BlockingQueue;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import net.dragberry.carmanager.transferobject.Record;

@Component
class Reader implements Runnable {
	
	private InputStream is;
	
	public InputStream getIs() {
		return is;
	}

	public void setIs(InputStream is) {
		this.is = is;
	}

	public BlockingQueue<Record> getTransactionQueue() {
		return transactionQueue;
	}

	public void setTransactionQueue(BlockingQueue<Record> transactionQueue) {
		this.transactionQueue = transactionQueue;
	}

	private StringBuilder sb;
	
	private BlockingQueue<Record> transactionQueue;
	
	
	@Override
	public void run() {
		try (XSSFWorkbook wb = new XSSFWorkbook(is)) {
			Sheet sheet = wb.getSheetAt(0);
			sheet.forEach((row) -> {
				if (row.getRowNum() >= 4) {
					sb = new StringBuilder("Row #").append(row.getRowNum()).append("\t");
					Record record = getRecord(row);
					try {
						transactionQueue.put(record);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				System.out.println(sb);
			});
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Get the record from row
	 * 
	 * @param row
	 * @return
	 */
	private Record getRecord(Row row) {
		Record record = new Record();
		row.forEach(cell -> {
			addCellInfo(sb, cell, cell.getColumnIndex());
			processCell(cell, record);
		});
		return record;
	}
	
	/**
	 * Processes single cell
	 * 
	 * @param cell
	 * @param record
	 */
	private void processCell(Cell cell, Record record) {
		switch (cell.getColumnIndex()) {
		case 0:
			record.setIndex((long) cell.getNumericCellValue());
			break;
		case 1:
			record.setDate(cell.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
			break;
		case 2:
			record.setDescription(cell.getStringCellValue());
			break;
		case 3:
			record.setType(cell.getStringCellValue());
			break;
		case 5:
			record.setCostBYR(cell.getNumericCellValue());
			break;
		case 6:
			record.setCostUSD(cell.getNumericCellValue());
			break;
		case 7:
			record.setCostBYRDad(cell.getNumericCellValue());
			break;
		case 8:
			record.setCostUSDDad(cell.getNumericCellValue());
			break;
		case 9:
			record.setTax(cell.getNumericCellValue());
			break;
		case 10:
			record.setExchangeRate(cell.getNumericCellValue());
			break;
		case 11:
			record.setExchangeRateReal(cell.getNumericCellValue());
			break;
		}
	}

	private void addCellInfo(StringBuilder sb, Cell cell, int cellNum) {
		if (cell == null) {
			return;
		}
		sb.append("Cell #").append(cellNum).append("[");
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_BLANK:
			sb.append("BLANK]").append(cell.getStringCellValue());
			break;
		case Cell.CELL_TYPE_NUMERIC:
			sb.append("NUMERIC]").append(cell.getNumericCellValue());
			break;
		case Cell.CELL_TYPE_STRING:
			sb.append("STRING]").append(cell.getStringCellValue());
			break;
		case Cell.CELL_TYPE_FORMULA:
			sb.append("FORMULA]").append(cell.getCellFormula());
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			sb.append("BOOLEAN]").append(cell.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_ERROR:
			sb.append("ERROR]").append(cell.getErrorCellValue());
			break;
		}
		sb.append(" ");
	}
}