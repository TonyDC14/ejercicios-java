package demo.itemConcurrencia;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentMemoryStoreImpl implements ConcurrentMemoryStore {

    // ConcurrentMap gives guidelines for valid implementations to provide thread-safety and memory-consistent atomic operations.}
    // ConcurrentMap guarantees memory consistency on key/value operations in a multi-threading environment.
    Map<String, Item> itemMap = new ConcurrentHashMap<String, Item>(); // default initial capacity (16), load factor (0.75) and concurrencyLevel (16)

    /*
    *  It’s a hash table implementation, which supports concurrent retrieval and updates.
    *  It’s used in a multi-threaded environment to avoid ConcurrentModificationException.
    *  fuente: digital ocean doc https://www.digitalocean.com/community/tutorials/concurrenthashmap-in-java
    * */

    @Override
    public void store(String key, Item item) throws IllegalArgumentException {
        if(this.checkNotNullMapParams(key, item))
            itemMap.putIfAbsent(key, item);
    }

    @Override
    public void update(String key, int value1, int value2) {
        // ConcurrentMap disabled the null key/value support
        Item localItem = new Item();
        localItem.setValue1(value1);
        localItem.setValue2(value2);
        if(this.checkNotNullMapParams(key, localItem))
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

    public Map<String, Item> get(){
        return itemMap;
    }

    public Boolean checkNotNullMapParams(String key, Item item){
        return (key != null && item != null) ? true : false;
    }
}
