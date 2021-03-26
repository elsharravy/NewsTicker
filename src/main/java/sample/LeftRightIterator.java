package sample;

import java.util.ArrayList;
import java.util.ListIterator;

class LeftRightIterator<T> implements ListIterator
{
    private ArrayList<T> container;
    private ListIterator<T> current;

    Directions lastDirection;

    LeftRightIterator(ArrayList<T> c ) {
        this.container = c;
        this.current = container.listIterator(  );
    }

    LeftRightIterator(ArrayList<T> c , int pos) {
        this.container = c;
        this.current = container.listIterator( pos );
    }

    public boolean hasNext() {
        return current.hasNext();
    }

    public T next() {
        if( lastDirection == Directions.LEFT )
        {
           current.next();
        }
        lastDirection = Directions.RIGHT;
        return current.next();
    }

    public boolean hasPrevious() {
        return current.hasNext();
    }

    public T previous() {
        if( lastDirection == Directions.RIGHT )
        {
            current.previous();
        }
        lastDirection = Directions.LEFT;
        return current.previous();
    }

    public int nextIndex() {
        return current.nextIndex();
    }

    public int previousIndex() {
        return current.previousIndex();
    }

    public void remove() {

    }

    public void set(Object o) {

    }

    public void add(Object o) {

    }


}
