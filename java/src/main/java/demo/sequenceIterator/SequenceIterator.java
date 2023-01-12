package demo.sequenceIterator;

import java.util.*;
import java.util.stream.Stream;

/*
* Crear una clase SequenceIterator que permita obtener de forma ordenada los
* elementos de un conjunto de secuencias de entrada iterables:
* */
public class SequenceIterator{

    //Entrada: { [1,3,5,7,9,....] , [2,4,6,8,...], [0,10,20,30,...], ....}
    EHCacheManager sequenceStorage = EHCacheManager.getManejador();
    int sequenceCursor = 0;

    /*
    * Las secuencias de entrada se encuentran ordenadas y las mismas pueden ser
    * arbitrariamente grandes (no es posible cargarlas enteras en memoria).
    */
    public SequenceIterator(Collection<Iterator<Comparable>> inputs) throws Exception{

        Iterator<Iterator<Comparable>> iterator = inputs.iterator();
        for(int i = 0; iterator.hasNext(); i++){
            List<Comparable> list = new ArrayList<>();
            iterator.next().forEachRemaining(list::add);
            sequenceStorage.ehCache.put(i, new CollectionSerializable(list));
        }

    }

    public boolean hasNext() {
        return sequenceStorage.ehCache.containsKey(this.sequenceCursor);
    }

    public Collection next() {
        CollectionSerializable toReturn = sequenceStorage.ehCache.get(this.sequenceCursor);
        this.sequenceCursor++;
        return toReturn.getObject();
    }

    /* Salida: Un iterador sobre la secuencia 0,1,2,3,4,5,6,7,8,9,10.. */
    public boolean sort(){
        try{

            CollectionSerializable toReturn = this.sequenceStorage.ehCache.get(this.sequenceCursor);
            List orderedList = new ArrayList(toReturn.getObject());
            Collections.sort(orderedList);

            //Actualizar el registro en cache
            sequenceStorage.ehCache.put(this.sequenceCursor, new CollectionSerializable(orderedList));

            return true;
        }catch (Exception e){ return false; }
    }

    public Iterator toIterator(){
        List collection = new ArrayList<Comparable>();
        for(int i = 0; sequenceStorage.ehCache.containsKey(i); i++){
            collection = Arrays.asList(Stream.concat(
                    collection.stream(),
                    sequenceStorage.ehCache.get(i).getObject().stream()).toArray());
        }
        Collections.sort(collection);
        return collection.iterator();
    }
}
