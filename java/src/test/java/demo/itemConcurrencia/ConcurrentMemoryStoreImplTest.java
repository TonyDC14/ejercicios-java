package demo.itemConcurrencia;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
public class ConcurrentMemoryStoreImplTest {

    Item cosa1 = new Item(), cosa2 = new Item(), cosa3 = new Item();

    @BeforeClass
    public static void basicConfigurator(){
        BasicConfigurator.configure();
    }

    private static final Logger logger = LogManager.getLogger(ConcurrentMemoryStoreImplTest.class);

    @Before
    public void setCosas(){
        cosa1.setValue1(4);cosa1.setValue1(530);
        cosa2.setValue1(9);cosa2.setValue1(10);
        cosa3.setValue1(87);cosa3.setValue1(80);
    }

    @Test(expected = ConcurrentModificationException.class)
    public void executorServiceItemThreadsConsumeNo() throws InterruptedException {

        NoConcurrentMemoryStoreImpl memoryStore = new NoConcurrentMemoryStoreImpl();

        memoryStore.store("1", cosa1);
        memoryStore.store("3", cosa3);
        memoryStore.store("2", cosa2);

        Iterator it = memoryStore.valueIterator();
        while (it.hasNext()) {
            Item item = (Item) it.next();
            memoryStore.remove("2");
        }

    }

    @Test
    public void executorServiceItemThreadsConsume() throws InterruptedException {

        ConcurrentMemoryStoreImpl memoryStore = new ConcurrentMemoryStoreImpl();

        memoryStore.store("1", cosa1);
        memoryStore.store("3", cosa3);
        memoryStore.store("2", cosa2);

        Iterator it = memoryStore.valueIterator();
        while (it.hasNext()) {
            Item item = (Item) it.next();
            memoryStore.remove("2");
        }

    }

}
