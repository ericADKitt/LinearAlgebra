//********************************************************************************************
//  MatrixIterator.java
//
//  Author: Eric Kitt
//********************************************************************************************

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * MatrixIterator is a subclass of Iterator that allows for efficient iteration through all
 * entries of a DoubleMatrix object.
 *
 * @author Eric Kitt
 * @version JDK 21.0.2
 */

public class MatrixIterator implements Iterator<Integer>
{
    //  Despite representing 2D matrices, DoubleMatrix objects store entries in a 1D array.
    //  index represents a cursor that iterates through a DoubleMatrix object.
    private int index = 0;

    //  Given a DoubleMatrix object, columns and indices represent its total number of columns
    //  and the total number of entries respectively.
    public final int columns, indices;

    /**
     * Constructor for a MatrixIterator.
     *
     * @param rows The number of rows of the DoubleMatrix object through which this
     *             MatrixIterator object will iterate.
     * @param columns The number of columns of the DoubleMatrix object through which this
     *                MatrixIterator object will iterate.
     */
    public MatrixIterator(int rows, int columns)
    {
        this.columns = columns;
        this.indices = rows * columns;
    }

    /**
     * Increments the cursor and returns it.
     *
     * @throws NoSuchElementException If the iterator has already reached the final entry.
     * @return The index of the DoubleMatrix object's next entry.
     */
    @Override
    public Integer next()
    {
        if (!hasNext())
            throw new NoSuchElementException();

        index++;

        return index;
    }

    /**
     * Sets the cursor to the index of the first entry of the DoubleMatrix
     * object's next row.
     *
     * @throws NoSuchElementException If the iterator has already reached the final row.
     * @return The index of the first entry of the DoubleMatrix object's next row.
     */
    public Integer nextRow()
    {
        if (!hasNextRow())
            throw new NoSuchElementException();

        index = (getRow() + 1) * columns;

        return index;
    }

    /**
     * Sets the cursor to the index of the DoubleMatrix object's first entry (at index 0).
     */
    public void reset() { index = 0; }

    /**
     * Determines if the cursor is not at the DoubleMatrix object's final entry.
     *
     * @return true if the cursor is NOT at the final entry; false, otherwise.
     */
    @Override
    public boolean hasNext() { return (index < indices); }

    /**
     * Determines if the cursor is not on the DoubleMatrix object's final row.
     *
     * @return true if the cursor is NOT on the final row; false, otherwise.
     */
    public boolean hasNextRow() { return (index + columns < indices); }

    /**
     * Calculates the current row of the cursor's position given the DoubleMatrix
     * object's number of columns
     *
     * @return The current row of the cursor.
     */
    public int getRow() { return index / columns; }

    /**
     * Calculates the current column of the cursor's position given the DoubleMatrix
     * object's number of columns
     *
     * @return The current column of the cursor.
     */
    public int getColumn() { return index % columns; }

    /**
     * Accessor for the cursor (the index instance variable).
     *
     * @return value of the cursor (index instance variable).
     */
    public int getIndex() { return index; }
}
