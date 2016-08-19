package net.dragberry.carmanager.menu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MenuItem implements Serializable {

	private static final long serialVersionUID = 7173585549909297638L;
	
	private String key;
	
	private String id;
	
	private String action;
	
	private boolean visibled;
	
	private boolean disabled;
	
	private boolean header;
	
	private List<MenuItem> items = new ArrayList<>();

	public List<MenuItem> getItems() {
		return Collections.unmodifiableList(items);
	}

	public void addItem(MenuItem item) {
		items.add(item);
	}
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder("[");
		s.append("key=");
		s.append(key);
		s.append(";");
		s.append("]");
		return s.toString();
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public boolean isVisibled() {
		return visibled;
	}

	public void setVisibled(boolean visibled) {
		this.visibled = visibled;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public boolean isHeader() {
		return header || !items.isEmpty();
	}

	public void setHeader(boolean header) {
		this.header = header;
	}
	
}
