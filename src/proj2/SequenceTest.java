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
    private Sequence makeSequence(String[] items, int capacity){
        Sequence newSequence = new Sequence(capacity);
        for (int i = 0; i < items.length; i++){
            newSequence.addBefore(items[i]);
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

    @Test //Test addBefore; no current element(added to beginning, new currIndex)
    public void addBeforeNoCurrent(){
        String[] items = new String[] {"3", "2", "1"};
        Sequence sequence = makeSequence(items);
        sequence.addBefore("X");

        assertEquals(true, sequence.isCurrent());
        assertEquals("X", sequence.getCurrent());
        assertEquals(4, sequence.size());
        assertEquals(10, sequence.getCapacity());
    }

    @Test //Tests addBefore; capacity reached (capacity should expand)
    public void addBeforeCapacityReached(){
        String[] items = new String[] {"3", "2", "1"};
        Sequence sequence = makeSequence(items, 3);
        sequence.addBefore("X");

        assertEquals(true, sequence.isCurrent());
        assertEquals("X", sequence.getCurrent());
        assertEquals(4, sequence.size());
        assertEquals(7, sequence.getCapacity());
    }

    @Test //Test addBefore; add an element with enough capacity
    public void addBeforeEnoughCapacity() {
        String[] items = new String[]{"3", "2", "1"};
        Sequence sequence = makeSequence(items, 4);
        sequence.advance();
        sequence.addBefore("X");

        assertEquals(true, sequence.isCurrent());
        assertEquals("X", sequence.getCurrent());
        assertEquals(4, sequence.size());
        assertEquals(4, sequence.getCapacity());
    }

    @Test //Tests addBefore; adds an element to an empty sequence
    public void addBeforeEmpty() {
        String[] items = new String[]{};
        Sequence sequence = makeSequence(items);
        sequence.addBefore("X");

        assertEquals(true, sequence.isCurrent());
        assertEquals("X", sequence.getCurrent());
        assertEquals(1, sequence.size());
        assertEquals(10, sequence.getCapacity());
    }

    @Test //Tests addAfter; adds an element when there is no current element (adds to end of sequence)
    public void addAfterNoCurrent() {
        String[] items = new String[]{};
        Sequence sequence = makeSequence(items);
        sequence.addBefore("X");

        assertEquals(true, sequence.isCurrent());
        assertEquals("X", sequence.getCurrent());
        assertEquals(1, sequence.size());
        assertEquals(10, sequence.getCapacity());
    }

    @Test //Tests addAfter; adds an element when the capacity is reached
    public void addAfterCapacityReached() {
        String[] items = new String[]{"3", "2", "1"};
        Sequence sequence = makeSequence(items, 3);
        sequence.addBefore("X");

        assertEquals(true, sequence.isCurrent());
        assertEquals("X", sequence.getCurrent());
        assertEquals(4, sequence.size());
        assertEquals(7, sequence.getCapacity());
    }

    @Test //Tests addAfter; adds an element when there is enough capacity
    public void addAfterEnoughCapacity() {
        String[] items = new String[]{"3", "2", "1"};
        Sequence sequence = makeSequence(items);
        sequence.addBefore("X");

        assertEquals(true, sequence.isCurrent());
        assertEquals("X", sequence.getCurrent());
        assertEquals(4, sequence.size());
        assertEquals(10, sequence.getCapacity());
    }

    @Test //Tests isCurrent; tests on an empty sequence with no current
    public void isCurrentNoCurrent() {
        String[] items = new String[]{};
        Sequence sequence = makeSequence(items);

        assertEquals(false, sequence.isCurrent());
    }

    @Test //Tests isCurrent; sequence has items with current
    public void isCurrentWithCurrent() {
        String[] items = new String[]{"3", "2", "1"};
        Sequence sequence = makeSequence(items);
        sequence.advance();

        assertEquals(true, sequence.isCurrent());
    }

    @Test //Tests getCapacity;
    public void getCapacity() {
        String[] items = new String[]{"3", "2", "1"};
        Sequence sequence = makeSequence(items, 3);

        assertEquals(3, sequence.getCapacity());
    }

    @Test //Tests getCurrent; empty sequence with no current
    public void getCurrentNoCurrent() {
        String[] items = new String[]{};
        Sequence sequence = makeSequence(items);

        assertEquals(null, sequence.getCurrent());
    }

    @Test //Tests getCurrent; sequence with current
    public void getCurrentWithCurrent() {
        String[] items = new String[]{"3", "2", "1"};
        Sequence sequence = makeSequence(items);

        assertEquals("1", sequence.getCurrent());
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
