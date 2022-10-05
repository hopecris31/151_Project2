package proj2;

/**
 * JUnit test class.  Use these tests as models for your own.
 */
import org.junit.*;
import org.junit.rules.Timeout;
import static org.junit.Assert.*;

public class SequenceTest {

    //@Rule // a test will fail if it takes longer than 1/10 of a second to run
    //public Timeout timeout = Timeout.millis(100);

    String[] emptySequence = new String[] {};

    /**
     * creates a new sequence object with default constructor
     * the last element added is the current element
     * @param items the items to be added to the sequence
     * @return the created sequence
     */
    private Sequence makeSequence(String[] items){
        Sequence newSequence = new Sequence();
        for (int i = 0; i < items.length; i++){
            newSequence.addBefore(items[i]);
        }
        return newSequence;
    }

    /**
     * creates a new sequence object
     * the last element added is the current element
     * @param capacity the capacity of the sequence
     * @param items the items to be added to the sequence
     * @return the created sequence
     */
    private Sequence makeSequenceAddBefore(int capacity, String[] items){
        Sequence newSequence = new Sequence(capacity);
        for (int i = 0; i < items.length; i++){
            newSequence.addBefore(items[i]);
        }
        return newSequence;
    }

    private Sequence makeSequenceAddAfter(int capacity, String[] items){
        Sequence newSequence = new Sequence(capacity);
        for (int i = 0; i < items.length; i++){
            newSequence.addAfter(items[i]);
        }
        return newSequence;
    }

    @Test //Tests constructor, makes an empty sequence with default capacity of 10
    public void testDefaultConstructor(){
        Sequence sequence = new Sequence();
        assertEquals(10, sequence.getCapacity()); //capacity should be 10
        assertEquals(true, sequence.isEmpty()); //sequence should contain no elements
        assertEquals(null, sequence.getCurrent()); //current index should be -1
    }


    @Test //Test non-default constructor, make empty sequence with capacity of 15
    public void nonDefaultConstructor(){
        Sequence sequence = new Sequence(15);
        assertEquals(15, sequence.getCapacity()); //capacity should be 10
        assertEquals(true, sequence.isEmpty()); //sequence should contain no elements
        assertEquals(null, sequence.getCurrent()); //current index should be -1
    }

    @Test //Tests addBefore; adds an element when the capacity has been reached
    public void addBeforeCapacityReached(){
        String[] items = new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"};
        Sequence sequence = new Sequence(); //creates sequence with 10 spots
        for (int i = 0; i < items.length; i++){
            sequence.addBefore(items[i]);//update item count here, otherwise sequence.items = 0 after adding all elements
        }
        assertEquals("11", sequence.getCurrent()); //current element should be "10" at index 0, the most recently added element
        assertEquals(21, sequence.getCapacity()); //capacity should be 21
    }

    @Test //Tests addBefore; adds an element to an empty sequence
    public void addBeforeEmptySequence(){
        String[] items = new String[] {};
        Sequence sequence = new Sequence();
        System.out.println(sequence.toString());
        sequence.addBefore("1");
        assertEquals(true, sequence.isCurrent());
        assertEquals("1", sequence.getCurrent());
        assertEquals(1, sequence.size());
        System.out.println(sequence.toString());
    }

    @Test //Tests addBefore; adds an element to a sequence with room
    public void addBeforeSequenceWithRoom(){
        String[] items = new String[] {"1", "2", "3"};
        Sequence sequence = new Sequence();
        for (int i = 0; i < items.length; i++){
            sequence.addBefore(items[i]);//update item count here, otherwise sequence.items = 0 after adding all elements
        }
        System.out.println(sequence.toString());
        sequence.addBefore("X");
        assertEquals(true, sequence.isCurrent());
        assertEquals("X", sequence.getCurrent());
        assertEquals(4, sequence.size());
        System.out.println(sequence.toString());
    }

    @Test //Tests addBefore; adds an element to a sequence with no current element
    public void addBeforeNoCurrent(){
        String[] items = new String[] {"1", "2", "3"};
        Sequence sequence = new Sequence(7); //creates sequence with 10 spots
        for (int i = 0; i < items.length; i++){
            sequence.addBefore(items[i]);//update item count here, otherwise sequence.items = 0 after adding all elements
        }
        sequence.advance(); //advance 3 times to set current element = -1
        sequence.advance();
        sequence.advance();
        sequence.addBefore("X");
        assertEquals("X", sequence.getCurrent());
        System.out.println(sequence.toString());
    }

    @Test //Tests addAll; add all when there is not enough space so capacity has to be increased
    public void adAllIncreaseCapacity(){
        String[] items1 = new String[] {"1", "2"};
        String[] items2 = new String[] {"C", "B", "A"};
        Sequence seq = new Sequence(10);
        Sequence sequence1 = new Sequence(2); //creates sequence with 10 spots
        Sequence sequence2 = new Sequence(3);
        for (int i = 0; i < items1.length; i++){
            sequence1.addBefore(items1[i]);//update item count here, otherwise sequence.items = 0 after adding all elements
        }
        for (int i = 0; i < items2.length; i++){
            sequence2.addBefore(items2[i]);//update item count here, otherwise sequence.items = 0 after adding all elements
        }
        sequence1.addAll(sequence2);
        System.out.println(sequence1.toString());
    }



}
