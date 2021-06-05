package org.itemize;

import java.util.UUID;
import java.util.function.Consumer;

import org.itemize.data.ItemData;
import org.itemize.data.ItemDataProvider;

public abstract class ItemStackProvider<T extends Object, B extends Object> {
	protected final ItemDataProvider dataProvider;

	ItemStackProvider(ItemDataProvider dataProvider) {
		this.dataProvider = dataProvider;
	}

	public ItemDataProvider getDataProvider() {
		return dataProvider;
	}

	public T create(String ID, UUID origin) {
		return create(ID, origin, _m -> {});
	};

	public abstract T create(String ID, UUID origin, Consumer<B> builderConsumer);
}
