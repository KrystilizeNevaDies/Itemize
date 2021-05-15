package main;

import main.data.DefaultItemDataProvider;
import main.data.ItemData;

public class Itemize {
	
	private static boolean isInitialized = false;
	
	public static void init() {
		if (isInitialized)
			return;
		
		isInitialized = true;
		
		ItemData.register(new DefaultItemDataProvider());
	}
}
