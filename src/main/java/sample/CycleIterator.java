package sample;

import java.util.*;

class CycleIterator<T> implements ListIterator
{
    private ArrayList<T> container;
    private ListIterator<T> current;

    Directions lastDirection;

    CycleIterator(ArrayList<T> c)
    {
        this.container = c;
        this.current = container.listIterator();
    }

    //   @Override
    public boolean hasNext() {
        return true;
    }

    //   @Override
    public T next() {
            if( lastDirection == Directions.LEFT )
            {
                getNext();
            }
            lastDirection = Directions.RIGHT;
            return getNext();
    }

    public boolean hasPrevious() {
        return true;
    }


    public T previous() {
        if( lastDirection == Directions.RIGHT )
        {
            getPrevious();
        }
        lastDirection = Directions.LEFT;
        return getPrevious();
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

    public int getContainerSize()
    {
        return container.size();
    }

   private T getNext()
    {
        try
        {
            return current.next();
        }
        catch (NoSuchElementException e)
        {
            current = container.listIterator();
            return current.next();
        }
    }

    private T getPrevious()
    {
        try
        {
            return current.previous();
        }
        catch (NoSuchElementException e)
        {
            current = container.listIterator(container.size());
            return current.previous();
        }
    }

}

enum Directions
        {
            LEFT,
            RIGHT,
        }