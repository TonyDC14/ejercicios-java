package demo.sequenceIterator;

import static org.junit.Assert.assertTrue;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.*;

/**
 * Unit test for simple App.
 */
public class SequenceIteratorTest {

    @BeforeClass
    public static void basicConfigurator(){
        BasicConfigurator.configure();
    }

    private static final Logger logger = LogManager.getLogger(SequenceIteratorTest.class);

    /**
     * Rigorous Test :-)
     */

    Collection<Iterator<Comparable>> inputs;

    @Test
    public void sequenceIteratorSobreMultiplesSecuenciasOrdenadas() throws Exception {
        inputs = new ArrayList<Iterator<Comparable>>(){{
            add(new ArrayList<Comparable>(Arrays.asList(1, 3, 5, 7, 9)).iterator());
            add(new ArrayList<Comparable>(Arrays.asList(2, 4, 6, 8)).iterator());
            add(new ArrayList<Comparable>(Arrays.asList(0, 10, 20, 30)).iterator());
        }};
        SequenceIterator ordenador = new SequenceIterator(inputs);

        //Ordenamos
        while (ordenador.hasNext())
            if(ordenador.sort())
                ordenador.next();


        assertTrue(compareIterator(
                new ArrayList<Comparable>(Arrays.asList(0, 1 , 2, 3, 4, 5, 6, 7, 8, 9, 10, 20, 30)).iterator(),
                ordenador.toIterator())
        );
    }

    @Test
    public void sequenceIteratorSobreMultiplesSecuenciasDesordenadas() throws Exception {
        inputs = new ArrayList<Iterator<Comparable>>(){{
            add(new ArrayList<Comparable>(Arrays.asList(9, 7, 5, 3, 1)).iterator());
            add(new ArrayList<Comparable>(Arrays.asList(4, 2, 8, 6)).iterator());
            add(new ArrayList<Comparable>(Arrays.asList(20, 10, 0, 30)).iterator());
        }};
        SequenceIterator ordenador = new SequenceIterator(inputs);

        //Ordenamos
        while (ordenador.hasNext())
            if(ordenador.sort())
                ordenador.next();


        assertTrue(compareIterator(
                new ArrayList<Comparable>(Arrays.asList(0, 1 , 2, 3, 4, 5, 6, 7, 8, 9, 10, 20, 30)).iterator(),
                ordenador.toIterator())
        );
    }

    public boolean compareIterator(Iterator it1, Iterator it2){
        while(it1.hasNext())
            if(!it2.hasNext() || !it1.next().equals(it2.next()))
                return false;
        return true;
    }

    @After
    public void initializeCollectionIteratorComparable(){
        inputs = null;
    }

}

