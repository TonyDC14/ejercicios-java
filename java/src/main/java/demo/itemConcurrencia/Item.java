package demo.itemConcurrencia;

import java.util.Iterator;

/**
 * Implementar la interfaz: ConcurrentMemoryStore
 * La implementación debe ser thread-safe y lo más eficiente posible. El iterador
 * retornado por valueIterator() debe ser recorrible sin riesgo de una
 * ConcurrentModificationException.
 * **/
class Item{

    private int value1, value2;

    public void setValue1(int v) {
        value1 = v;
    }

    public void setValue2(int v) {
        value2 = v;
    }

    public int getValue1() {
        return value1;
    }

    public int getValue2() {
        return value2;
    }
}