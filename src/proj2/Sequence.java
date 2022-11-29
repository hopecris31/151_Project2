package proj2;

/**
 * Hope Crisafi
 * CS 151 Project 2
 * Oct 4, 2022
 *
 *  A class that represents a sequence ADT. Holds items of the same type.
 *  Items in sequence are accessed via a "current" marker, and not by index
 *
 *  INVARIANTS:
 *
 *  If size>0:
 *      The contents are stored in sequence at indexes 0 through size-1
 *      If there's no current index, current = -1
 *      the contents at Indexes >= items are irrelevant
 *      if size = 0, any items become irrelevant
 *      currentIndex can never be greater than or equal to size
 *      Items can be accessed via the "current" marker
 *      If items are added to a full sequence, the capacity expands to accommodate the added item
 *
 *  INSTANCE VARIABLES:
 *      holder -- the String array that holds the items
 *      items -- the number of elements currently in the Sequence
 *      currentIndex -- the current index of the holder
 */

public class Sequence {
    private String[] holder;
    private int items;
    private int currentIndex;
    private final int DEFAULT_SIZE = 10;
    private final int NO_INDEX = -1;
    private final int EMPTY = 0;

    /**
     * Creates a new sequence with initial capacity 10.
     */
    public Sequence() {
         this.holder = new String[DEFAULT_SIZE];
         this.currentIndex = NO_INDEX;
         this.items = EMPTY;
    }

    /**
     * Creates a new sequence. (non-default constructor)
     *
     * @param initialCapacity the initial capacity of the sequence.
     */
    public Sequence(int initialCapacity){
        this.holder = new String[initialCapacity];
        this.currentIndex = NO_INDEX;
        this.items = 0;
    }

    /**
     * Adds a string to the sequence in the location before the
     * current element. If the sequence has no current element, the
     * string is added to the beginning of the sequence.
     *
     * The added element becomes the current element.
     *
     * If the sequences's capacity has been reached, the sequence will
     * expand to twice its current capacity plus 1.
     *
     * @param value the string to add.
     */
    public void addBefore(String value) {
        if(!this.isCurrent()){
            this.setCurrentIndex(0);
        }
        else{
            this.capacityReached();
        }
        this.shiftIncludingCurrent(value);
        this.setIndexValue(this.currentIndex, value);
        items++;
    }

    /**
     * Adds a string to the sequence in the location after the current
     * element. If the sequence has no current element, the string is
     * added to the end of the sequence.
     *
     * The added element becomes the current element.
     *
     * If the sequences's capacity has been reached, the sequence will
     * expand to twice its current capacity plus 1.
     *
     * @param value the string to add.
     */
    public void addAfter(String value) {
        if(!this.isCurrent()){
            this.setCurrentIndex(this.size());
            this.setIndexValue(this.currentIndex, value);
        }
        else{
            this.capacityReached();
            this.shiftExcludingCurrent(value);
            this.setIndexValue(this.currentIndex+1, value);
            this.currentIndex ++;
        }
        items++;
    }

    /**
     * @return true if and only if the sequence has a current element.
     */
    public boolean isCurrent() {
        return this.currentIndex > NO_INDEX;
    }

    /**
     * @return the capacity of the sequence.
     */
    public int getCapacity() {
        return this.holder.length;
    }

    /**
     * @return the element at the current location in the sequence, or
     * null if there is no current element.
     */
    public String getCurrent() {
        if (isCurrent()){
            return this.holder[this.currentIndex];
        }
        else{
            return null;
        }
    }

    /**
     * Increase the sequence's capacity to be
     * at least minCapacity.  Does nothing
     * if current capacity is already >= minCapacity.
     *
     * @param minCapacity the minimum capacity that the sequence
     * should now have.
     */
    public void ensureCapacity(int minCapacity) {
        if(this.getCapacity() < minCapacity){
            String[] newHolder = new String[minCapacity];
            for(int i = 0; i < this.size(); i++){
                newHolder[i] = this.holder[i];
            }
            this.holder = newHolder;
        }
    }

    /**
     * Places the contents of another sequence at the end of this sequence.
     *
     * If adding all elements of the other sequence would exceed the
     * capacity of this sequence, the capacity is changed to make room for
     * all of the elements to be added.
     *
     * Postcondition: NO SIDE EFFECTS!  the other sequence should be left
     * unchanged.  The current element of both sequences should remain
     * where they are. (When this method ends, the current element
     * should refer to the same element that it did at the time this method
     * started.)
     *
     * @param another the sequence whose contents should be added.
     */
    public void addAll(Sequence another) {
        int storedCurrentIndex = this.currentIndex;
        if(!isCurrent()){
            storedCurrentIndex = NO_INDEX;
        }
        int totalItems = this.size() + another.size();
        if(totalItems > this.getCapacity()){
            this.ensureCapacity(totalItems);
        }
        this.setCurrentIndex(this.size()-1);
        this.addItems(another);
        this.setCurrentIndex(storedCurrentIndex);
    }

    /**
     * Move forward in the sequence so that the current element is now
     * the next element in the sequence.
     *
     * If the current element was already the end of the sequence,
     * then advancing causes there to be no current element.
     *
     * If there is no current element to begin with, do nothing.
     */
    public void advance() {
        if(isCurrent()) {
            if (endOfSequenceReached()) {
                this.setCurrentIndex(NO_INDEX);
            }
            else{this.currentIndex +=1;
            }
        }
    }

    /**
     * Make a copy of this sequence.  Subsequence changes to the copy
     * do not affect the current sequence, and vice versa.
     *
     * Postcondition: NO SIDE EFFECTS!  This sequence's current
     * element should remain unchanged.  The clone's current
     * element will correspond to the same place as in the original.
     *
     * @return the copy of this sequence.
     */
    public Sequence clone() {
        Sequence newSequence = new Sequence(this.getCapacity());
        newSequence.currentIndex = this.currentIndex;
        newSequence.items = this.size();
        for (int i = 0; i < this.size(); i++){
            newSequence.holder[i] = this.holder[i];
        }
        return newSequence;
    }

    /**
     * Remove the current element from this sequence.  The following
     * element, if there was one, becomes the current element.  If
     * there was no following element (current was at the end of the
     * sequence), the sequence now has no current element.
     *
     * If there is no current element, does nothing.
     */
    public void removeCurrent() {
        if(this.isCurrent()){
            this.remove();
            if(this.endOfSequenceReached()){
                this.setCurrentIndex(NO_INDEX);
            }
            this.items--;
        }
    }

    /**
     * @return the number of elements stored in the sequence.
     */
    public int size() {
        return this.items;
    }

    /**
     * Sets the current element to the start of the sequence.  If the
     * sequence is empty, the sequence has no current element.
     */
    public void start() {
        if(this.isEmpty()){
            this.setCurrentIndex(NO_INDEX);
        }
        else{
            this.setCurrentIndex(0);
        }
    }


    /**
     * Reduce the current capacity to its actual size, so that it has
     * capacity to store only the elements currently stored.
     */
    public void trimToSize() {
        if(this.getCapacity() != this.size()){
            String[] newHolder = new String[this.size()];
            for(int i = 0; i < this.size(); i++){
                newHolder[i] = this.holder[i];
            }
            this.holder = newHolder;
        }
    }

    /**
     * Produce a string representation of this sequence.  The current
     * location is indicated by a >.  For example, a sequence with "A"
     * followed by "B", where "B" is the current element, and the
     * capacity is 5, would print as:
     *
     *    {A, >B} (capacity = 5)
     *
     * The string you create should be formatted like the above example,
     * with a comma following each element, no comma following the
     * last element, and all on a single line.  An empty sequence
     * should give back "{}" followed by its capacity.
     *
     * @return a string representation of this sequence.
     */
    public String toString() {
        String sequenceString = "{";
        if(!this.isEmpty()){
            for(int i = 0; i < this.size(); i++){
                if(i == this.currentIndex) {
                    sequenceString += ">";
                }
                sequenceString += this.holder[i];
                if(i+1 != this.size()){
                    sequenceString += ", ";
                }
            }
        }
        return sequenceString += "} (capacity = " + this.getCapacity() + ")";
    }

    /**
     * Checks whether another sequence is equal to this one.  To be
     * considered equal, the other sequence must have the same size
     * as this sequence, have the same elements, in the same
     * order, and with the same element marked
     * current.  The capacity can differ.
     *
     * Postcondition: NO SIDE EFFECTS!  this sequence and the
     * other sequence should remain unchanged, including the
     * current element.
     *
     * @param other the other Sequence with which to compare
     * @return true iff the other sequence is equal to this one.
     */
    public boolean equals(Sequence other) {
        if(this.size() != other.size()) {
            return false;
        }
        if (this.currentIndex != other.currentIndex){
            return false;
        }
        for(int i=0; i<this.size(); i++){
            if(!this.holder[i].equals(other.holder[i])){
                return false;
            }
        }
        return true;
    }


    /**
     *
     * @return true if Sequence empty, else false
     */
    public boolean isEmpty() {
        return this.size() == EMPTY;
    }

    /**
     *  empty the sequence.  There should be no current element.
     */
    public void clear() {
        this.items = EMPTY;
        this.currentIndex = NO_INDEX;
    }


    /** ------------------------------
     *      PRIVATE HELPER METHODS
     * _______________________________
     */


    /**
     * shifts all elements from currentIndex and after one to the right (addBefore)
     * adds a value in the holder in the spot of currentIndex, before previous currentIndex
     * @param value a value to be added
     */
    private void shiftIncludingCurrent(String value){
        for(int i = this.size()-1; i >= this.currentIndex; i--){
            this.holder[i+1] = this.holder[i];
        }
        this.setIndexValue(this.currentIndex, value);
    }

    /**
     * checks if the items in the sequence have reached max capacity
     * if max capacity is reached, capacity is set do double its current plus one
     */
    private void capacityReached(){
        if(this.size() == this.getCapacity()){
            this.ensureCapacity((this.getCapacity()*2)+1);
        }
    }

    /**
     * shifts all elements after currentIndex over one to the right
     * adds a value in the holder after currentIndex
     * @param value a value to be added
     */
    private void shiftExcludingCurrent(String value) {
        for (int i = this.size(); i > this.currentIndex; i--) {
            this.holder[i] = this.holder[i-1]; //shifting elements to the right 1
        }
    }

    /**
     * sets the current index to the index specified
     * @param newIndex a new current index
     */
    private void setCurrentIndex (int newIndex){
        this.currentIndex = newIndex;
    }

    /**
     *Sets a value to a specified index
     * @param index index to be set
     * @param value value to take place of the index
     */
    private void setIndexValue(int index, String value){
        this.holder[index] = value;
    }


    /**
     * gets the last index in a seqence
     * @return the last index of a sequence
     */
    private int getLastIndex(){
        return getCapacity()-1;
    }

    /**
     * @return True if the end of the sequence has been reached, False if not
     */
    private boolean endOfSequenceReached(){
        return this.currentIndex == this.size()-1;
    }

    /**
     * adds items from another Sequence to this sequence.
     * @param another the other sequence to be added to original
     */
    private void addItems(Sequence another){
        int anotherIndex = 0;
        int totalSize = another.size() + this.size();

        for(int i = this.size(); i < totalSize; i++) {
            this.addAfter(another.holder[anotherIndex]);
            anotherIndex++;
        }
    }

    /**
     * removes the current element in a sequence by shifting all elements from the current index to the left
     */
    private void remove() {
        for (int i = this.currentIndex; i < this.size(); i++) {
            this.holder[i] = this.holder[i + 1];
        }
    }

}
