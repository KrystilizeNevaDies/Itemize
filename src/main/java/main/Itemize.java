package main;

import javax.annotation.Nullable;

import main.data.DefaultItemDataProvider;
import main.data.ItemData;
import main.data.ItemDataProvider;
import main.tags.ItemizeItemProvider;

public class Itemize {

	private static boolean isInitialized = false;

	private static ItemStackProvider itemStackProvider = new ItemizeItemProvider();

	/**
	 * This function initializes the item system
	 * @param provider
	 */
	public static void init(@Nullable ItemDataProvider provider) {
		if (Itemize.isInitialized) {
			return;
		}

		Itemize.isInitialized = true;

		if (provider == null) {
			ItemData.register(new DefaultItemDataProvider());
		} else {
			ItemData.register(provider);
		}
	}

	public static ItemStackProvider getProvider() {
		return Itemize.itemStackProvider;
	}
}
