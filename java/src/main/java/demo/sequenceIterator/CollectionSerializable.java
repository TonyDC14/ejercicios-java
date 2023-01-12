package demo.sequenceIterator;

import java.io.Serializable;
import java.util.Collection;

public class CollectionSerializable implements Serializable {

    public CollectionSerializable(Collection collection){
        this.object = collection;
    }

    public Collection getObject() {
        return object;
    }

    public void setObject(Collection object) {
        this.object = object;
    }

    private Collection object;

}
