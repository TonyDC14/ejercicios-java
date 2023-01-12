package demo.itemConcurrencia;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class NoConcurrentMemoryStoreImpl implements ConcurrentMemoryStore {

    Map<String, Item> itemMap = new HashMap<>();

    @Override
    public void store(String key, Item item) throws IllegalArgumentException {
        itemMap.put(key, item);
    }

    @Override
    public void update(String key, int value1, int value2) {
        Item localItem = new Item();
        localItem.setValue1(value1);
        localItem.setValue2(value2);
            itemMap.put(key, localItem);
    }

    @Override
    public Iterator<Item> valueIterator() {
        return itemMap.values().iterator();
    }

    @Override
    public void remove(String key) {
        if(key != null)
            itemMap.remove(key);
    }
}
