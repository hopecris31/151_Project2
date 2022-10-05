package proj2;

/**
 * JUnit test class.  Use these tests as models for your own.
 */
import org.junit.*;
import org.junit.rules.Timeout;
import static org.junit.Assert.*;

public class SequenceTest {

    @Rule // a test will fail if it takes longer than 1/10 of a second to run
    public Timeout timeout = Timeout.millis(100);

    String[] emptySequence = new String[] {};

    /**
     * creates a new sequence object with default constructor
     * the last element added is the current element
     * @param items the items to be added to the sequence
     * @return the created sequence
     */
    private Sequence makeSequence(String[] items){
        Sequence newSequence = new Sequence();
        for (String item : items) {
            newSequence.addBefore(item);
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
        for (String item : items) {
            newSequence.addBefore(item);
        }
        return newSequence;
    }


    @Test //Tests constructor, makes an empty sequence with default capacity of 10
    public void testDefaultConstructor(){
        Sequence sequence = new Sequence();
        assertEquals(10, sequence.getCapacity()); //capacity should be 10
        assertTrue(sequence.isEmpty()); //sequence should contain no elements
        assertNull(sequence.getCurrent()); //current index should be -1
    }


    @Test //Test non-default constructor, make empty sequence with capacity of 15
    public void nonDefaultConstructor(){
        Sequence sequence = new Sequence(15);
        assertEquals(15, sequence.getCapacity()); //capacity should be 10
        assertTrue(sequence.isEmpty()); //sequence should contain no elements
        assertNull(sequence.getCurrent()); //current index should be -1
    }

    @Test //Test addBefore; no current element(added to beginning, new currIndex)
    public void addBeforeNoCurrent(){
        String[] items = new String[] {"3", "2", "1"};
        Sequence sequence = makeSequence(items);
        sequence.addBefore("X");

        assertTrue(sequence.isCurrent());
        assertEquals("X", sequence.getCurrent());
        assertEquals(4, sequence.size());
        assertEquals(10, sequence.getCapacity());
    }

    @Test //Tests addBefore; capacity reached (capacity should expand)
    public void addBeforeCapacityReached(){
        String[] items = new String[] {"3", "2", "1"};
        Sequence sequence = makeSequence(items, 3);
        sequence.addBefore("X");

        assertTrue(sequence.isCurrent());
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

        assertTrue(sequence.isCurrent());
        assertEquals("X", sequence.getCurrent());
        assertEquals(4, sequence.size());
        assertEquals(4, sequence.getCapacity());
    }

    @Test //Tests addBefore; adds an element to an empty sequence
    public void addBeforeEmpty() {
        String[] items = new String[]{};
        Sequence sequence = makeSequence(items);
        sequence.addBefore("X");

        assertTrue(sequence.isCurrent());
        assertEquals("X", sequence.getCurrent());
        assertEquals(1, sequence.size());
        assertEquals(10, sequence.getCapacity());
    }

    @Test //Tests addAfter; adds an element when there is no current element (adds to end of sequence)
    public void addAfterNoCurrent() {
        String[] items = new String[]{};
        Sequence sequence = makeSequence(items);
        sequence.addBefore("X");

        assertTrue(sequence.isCurrent());
        assertEquals("X", sequence.getCurrent());
        assertEquals(1, sequence.size());
        assertEquals(10, sequence.getCapacity());
    }

    @Test //Tests addAfter; adds an element when the capacity is reached
    public void addAfterCapacityReached() {
        String[] items = new String[]{"3", "2", "1"};
        Sequence sequence = makeSequence(items, 3);
        sequence.addBefore("X");

        assertTrue(sequence.isCurrent());
        assertEquals("X", sequence.getCurrent());
        assertEquals(4, sequence.size());
        assertEquals(7, sequence.getCapacity());
    }

    @Test //Tests addAfter; adds an element when there is enough capacity
    public void addAfterEnoughCapacity() {
        String[] items = new String[]{"3", "2", "1"};
        Sequence sequence = makeSequence(items);
        sequence.addBefore("X");

        assertTrue(sequence.isCurrent());
        assertEquals("X", sequence.getCurrent());
        assertEquals(4, sequence.size());
        assertEquals(10, sequence.getCapacity());
    }

    @Test //Tests isCurrent; tests on an empty sequence with no current
    public void isCurrentNoCurrent() {
        String[] items = new String[]{};
        Sequence sequence = makeSequence(items);

        assertFalse(sequence.isCurrent());
    }

    @Test //Tests isCurrent; sequence has items with current
    public void isCurrentWithCurrent() {
        String[] items = new String[]{"3", "2", "1"};
        Sequence sequence = makeSequence(items);
        sequence.advance();

        assertTrue(sequence.isCurrent());
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

        assertNull(sequence.getCurrent());
    }

    @Test //Tests getCurrent; sequence with current
    public void getCurrentWithCurrent() {
        String[] items = new String[]{"3", "2", "1"};
        Sequence sequence = makeSequence(items);

        assertEquals("1", sequence.getCurrent());
    }

    @Test //Tests ensureCapacity; not enough space so it has to expand sequence
    public void ensureCapacityNotEnoughSpace() {
        String[] items = new String[]{"3", "2", "1"};
        Sequence sequence = makeSequence(items, 3);
        sequence.ensureCapacity(5);

        assertEquals(5, sequence.getCapacity());
        assertEquals(3, sequence.size());
    }

    @Test //Tests ensureCapacity; there is enough space, so ensureCapacity should do nothing
    public void ensureCapacityEnoughSpace() {
        String[] items = new String[]{"3", "2", "1"};
        Sequence sequence = makeSequence(items, 3);
        sequence.ensureCapacity(2);

        assertEquals(3, sequence.getCapacity());
        assertEquals(3, sequence.size());
    }

    @Test //Tests addAll; if there is enough space for items to be added
    public void addAllEnoughSpace() {
        String[] items1 = new String[]{"3", "2", "1"};
        String[] items2 = new String[]{"C", "B", "A"};
        Sequence sequence1 = makeSequence(items1);
        Sequence sequence2 = makeSequence(items2);
        sequence1.start(); //currentIndex should be 1

        sequence1.addAll(sequence2);

        assertEquals(6, sequence1.size());
        assertEquals(10, sequence1.getCapacity());
        assertEquals("1", sequence1.getCurrent());
    }

    @Test //Tests addAll; if there is not enough space for items to be added, capacity increases
    public void addAllNotEnoughSpace() {
        String[] items1 = new String[]{"3", "2", "1"};
        String[] items2 = new String[]{"C", "B", "A"};
        Sequence sequence1 = makeSequence(items1, 3);
        Sequence sequence2 = makeSequence(items2);
        sequence1.start(); //currentIndex should be 1

        sequence1.addAll(sequence2);

        assertEquals(6, sequence1.size());
        assertEquals(6, sequence1.getCapacity());
        assertEquals("1", sequence1.getCurrent());
    }

    @Test //Tests addAll; if there is enough space for items to be added
    public void addAllNoCurrentIndex() {
        String[] items1 = new String[]{};
        String[] items2 = new String[]{"C", "B", "A"};
        Sequence sequence1 = makeSequence(items1);
        Sequence sequence2 = makeSequence(items2);

        sequence1.addAll(sequence2);

        assertEquals(3, sequence1.size());
        assertEquals(10, sequence1.getCapacity());
        assertNull(sequence1.getCurrent());
    }

    @Test //Tests advance; at end of size so makes current = -1
    public void advanceAtEndOfSize() {
        String[] items = new String[]{"3", "2", "1"};
        Sequence sequence = makeSequence(items);

        sequence.advance(); //advance 3 times to get advance to equal no index
        sequence.advance();
        sequence.advance();

        assertNull(sequence.getCurrent());
    }

    @Test //Tests advance; there is a current index with room to advance to a next one
    public void advanceWithCurrent() {
        String[] items = new String[]{"3", "2", "1"};
        Sequence sequence = makeSequence(items);

        System.out.println(sequence);
        sequence.advance(); //advance to get current = 2
        sequence.advance();
        System.out.println(sequence);

        assertEquals("3", sequence.getCurrent());
    }

    @Test //Tests advance; no current index
    public void advanceNoCurrent() {
        String[] items = new String[]{};
        Sequence sequence = makeSequence(items);

        sequence.advance();

        assertNull(sequence.getCurrent());
    }

    @Test //Tests clone; clone empty sequence
    public void cloneEmpty() {
        String[] items = new String[]{};
        Sequence sequence = makeSequence(items);

        Sequence cloneSequence = sequence.clone();

        assertTrue(sequence.equals(cloneSequence)); //tests below are to ensure clone worked in case equals is not working properly

        assertNull(sequence.getCurrent());
        assertEquals(10, sequence.getCapacity());
        assertEquals(0, sequence.size());
        assertNull(cloneSequence.getCurrent());
        assertEquals(10, cloneSequence.getCapacity());
        assertEquals(0, cloneSequence.size());
    }

    @Test //Tests clone; clone sequence with elements
    public void cloneWithElements() {
        String[] items = new String[]{"3", "2", "1"};
        Sequence sequence = makeSequence(items);
        sequence.advance();

        Sequence cloneSequence = sequence.clone();

        assertTrue(sequence.equals(cloneSequence)); //tests below are to ensure clone worked in case equals is not working properly

        assertEquals("2", sequence.getCurrent());
        assertEquals(10, sequence.getCapacity());
        assertEquals(3, sequence.size());
        assertEquals("2", cloneSequence.getCurrent());
        assertEquals(10, cloneSequence.getCapacity());
        assertEquals(3, cloneSequence.size());
    }

    @Test //Tests removeCurrent; removes the current element in a sequence that has a current element
    public void removeCurrentWithCurrent() {
        String[] items = new String[]{"3", "2", "1"};
        Sequence sequence = makeSequence(items);
        sequence.advance();

        sequence.removeCurrent();

        assertEquals("3", sequence.getCurrent());
        assertEquals(2, sequence.size());
    }

    @Test //Tests removeCurrent; called on sequence with no current, so does nothing
    public void removeCurrentNoCurrent() {
        String[] items = new String[]{"3", "2", "1"};
        Sequence sequence = makeSequence(items);
        sequence.advance(); //advance 3 times to get currentIndex to equal -1
        sequence.advance();
        sequence.advance();

        sequence.removeCurrent();

        assertNull(sequence.getCurrent());
        assertEquals(3, sequence.size());
    }

    @Test //Tests removeCurrent; called on sequence where current is at end of sequence, removes element and sets currentIndex = -1
    public void removeCurrentEndOfSequence() {
        String[] items = new String[]{"3", "2", "1"};
        Sequence sequence = makeSequence(items);
        sequence.advance(); //advance 2 times to get currentIndex at end of sequence
        sequence.advance();

        sequence.removeCurrent();

        assertNull(sequence.getCurrent());
        assertEquals(2, sequence.size());
    }

    @Test //Tests size; called on an empty sequence
    public void sizeEmptySequence() {
        String[] items = new String[]{};
        Sequence sequence = makeSequence(items);

        assertEquals(0, sequence.size());
    }

    @Test //Tests start; called upon an empty sequence
    public void startEmptySequence() {
        String[] items = new String[]{};
        Sequence sequence = makeSequence(items);

        sequence.start();

        assertNull(sequence.getCurrent());
    }

    @Test //Tests start; called upon a sequence with items, but no current index
    public void startSequenceWithItems() {
        String[] items = new String[]{"3", "2", "1"};
        Sequence sequence = makeSequence(items);
        sequence.advance();
        sequence.advance();
        sequence.advance();

        sequence.start();

        assertEquals("1", sequence.getCurrent());
    }

    @Test //Tests trimToSize; given a sequence with remaining capacity, the capacity should be reduced to the size
    public void trimToSizeRemainingCapacity() {
        String[] items = new String[]{"3", "2", "1"};
        Sequence sequence = makeSequence(items);

        sequence.trimToSize();

        assertEquals(3, sequence.getCapacity());
        assertEquals(3, sequence.size());
    }

    @Test //Tests trimToSize; given a sequence where capacity is already equal to size, should do nothing
    public void trimToSizeCapacityEqualsSize() {
        String[] items = new String[]{"3", "2", "1"};
        Sequence sequence = makeSequence(items, 3);

        sequence.trimToSize();

        assertEquals(3, sequence.getCapacity());
        assertEquals(3, sequence.size());
    }

    @Test //Tests trimToSize; given an empty sequence with capacity 10, should reduce capacity to nothing
    public void trimToSizeEmptySequence() {
        String[] items = new String[]{};
        Sequence sequence = makeSequence(items);

        sequence.trimToSize();

        assertEquals(0, sequence.getCapacity());
        assertEquals(0, sequence.size());
    }
}
