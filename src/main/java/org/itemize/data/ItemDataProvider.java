package org.itemize.data;

import java.util.Map;

public interface ItemDataProvider {
	public Map<String, ItemData> getItemData();

	public ItemData get(String ID);
}
