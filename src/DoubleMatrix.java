//********************************************************************************************
//  DoubleMatrix.java
//
//  Author: Eric Kitt
//********************************************************************************************

import java.util.Arrays;

/**
 * DoubleMatrix is a class that represents a matrix of floating point numbers.  It includes a
 * variety of methods for common matrix operations such as matrix multiplication, matrix
 * inverse, reduced row echelon form, determinant, etc.
 *
 * @author Eric Kitt
 * @version JDK 21.0.2
 */

public class DoubleMatrix
{
    //  The dimensions of the matrix
    public final int rows, columns;

    //  Despite representing 2D matrices, DoubleMatrix objects store entries in a 1D array
    protected final double[] matrix;

    //  The number of decimals shown when printing a matrix
    public static final int DISPLAY_DECIMALS = 4;


    /**
     * Generates a text form of the matrix's entries, shown in rows with square brackets on
     * the ends.  DISPLAY_DECIMALS determines the amount of decimals shown in the entries.
     *
     * @return A string containing the matrix's entries.
     */
    public String toString()
    {
        MatrixIterator iterator = new MatrixIterator(rows, columns);

        //  Every line of the string will begin with "[" and end with "]", and every entry
        //  will be delimited by spaces.
        String display = "[ ";

        //  The lowest amount of spaces an entry can take is the number of decimals + 1 (for
        //  the decimal point ".") + 1 (for a digit to the left of the decimal point).
        int maxDigits = DISPLAY_DECIMALS + 2;


        //  Determine the number of spaces taken up by the entry with the most digits
        while (iterator.hasNext())
        {
            int currentDigits =
                    String.format("%."+DISPLAY_DECIMALS+"f", get(iterator)).length();

            if (maxDigits < currentDigits)
                maxDigits = currentDigits;

            iterator.next();
        }

        //  Formats entries to take up a consistent amount of space.  Delimits them with
        //  spaces.  Starts rows with "[", and ends them with "]".
        iterator.reset();
        while (iterator.hasNext())
        {
            display += String.format("%" + maxDigits + "." + DISPLAY_DECIMALS + "f ",
                    get(iterator));

            iterator.next();

            display += (iterator.getColumn() == 0 && iterator.hasNext() ? "]\n[ " : "");
        }
        display += "]";


        return display;
    }


    /**
     * Determines if a particular index (values for a row and a column) are not within the
     * bounds of the matrix.
     *
     * @param row The row that will be checked to be out of bounds.
     * @param column The column that will be checked to be out of bounds.
     * @return true if the row or column parameters are invalid; false, otherwise.
     */
    public boolean invalidIndex(int row, int column)
    {
        return (column >= columns || row >= rows || column < 0 || row < 0);
    }

    /**
     * Determines if a MatrixIterator object stores instance variables that can cause
     * improper or out-of-bounds indexing in the Matrix.
     *
     * @param iterator The iterator that will be checked.
     * @return true if iterator has invalid values for indices or columns; false, otherwise.
     */
    public boolean invalidIndex(MatrixIterator iterator)
    {
        return (iterator.indices != matrix.length || iterator.columns != columns);
    }


    /**
     * Accessor for an entry at a specific row and column.
     *
     * @throws IllegalArgumentException if the provided index is invalid.
     * @param row The row of the entry that will be accessed.
     * @param column The column of the entry that will be accessed.
     * @return The entry stored at that row and column.
     */
    public double get(int row, int column)
    {
        if (invalidIndex(row, column))
            throw new IllegalArgumentException();

        return matrix[row * columns + column];
    }

    /**
     * Accessor for an entry at a position specified by the cursor of an iterator.
     *
     * @throws IllegalArgumentException if the provided index is invalid.
     * @param iterator The MatrixIterator object whose cursor points to the entry that will be
     *                 accessed.
     * @return The entry that is pointed to by the MatrixIterator object's cursor.
     */
    public double get(MatrixIterator iterator)
    {
        if (invalidIndex(iterator))
            throw new IllegalArgumentException();

        return matrix[iterator.getIndex()];
    }


    /**
     * Mutator for an entry at a specific row and column.
     *
     * @throws IllegalArgumentException if the provided index is invalid.
     * @param row The row of the entry that will be mutated.
     * @param column The column of the entry that will be mutated.
     * @param entry The new value for the entry.
     */
    public void set(int row, int column, double entry)
    {
        if (invalidIndex(row, column))
            throw new IllegalArgumentException();

        matrix[row * columns + column] = entry;
    }

    /**
     * Mutator for an entry whose position is specified by the cursor of an iterator
     *
     * @throws IllegalArgumentException if the provided index is invalid.
     * @param iterator The MatrixIterator object whose cursor points to the entry that will be
     *                 mutated.
     * @param entry The new value for the entry.
     */
    public void set(MatrixIterator iterator, double entry)
    {
        if (invalidIndex(iterator))
            throw new IllegalArgumentException();

        matrix[iterator.getIndex()] = entry;
    }


    /**
     * DoubleMatrix constructor.  Creates a matrix with specified dimensions (rows and
     * columns) and sets all its entries to the same value.
     *
     * @param rows The number of rows (the height) of the created matrix.
     * @param columns The number of columns (the width) of the created matrix.
     * @param entry The value of all entries in the created matrix.
     */
    public DoubleMatrix(int rows, int columns, double entry)
    {
        this.rows = rows;
        this.columns = columns;
        matrix = new double[rows * columns];
        Arrays.fill(matrix, entry);
    }

    /**
     * DoubleMatrix constructor.  Creates a matrix with specified dimensions (rows and
     * columns) and sets all entries to 0.
     *
     * @param rows The number of rows (the height) of the created matrix.
     * @param columns The number of columns (the width) of the created matrix.
     */
    public DoubleMatrix(int rows, int columns)
    {
        this.rows = rows;
        this.columns = columns;
        matrix = new double[rows * columns];
        Arrays.fill(matrix, 0.0);
    }

    /**
     * DoubleMatrix constructor.  Creates a matrix with specified dimensions (rows and
     * columns) and takes in the entries as a parameter (an array of doubles).
     *
     * @param rows The number of rows (the height) of the created matrix.
     * @param columns The number of columns (the width) of the created matrix.
     * @param matrix The entries of the matrix from left to right, top to bottom.
     */
    public DoubleMatrix(int rows, int columns, double ... matrix)
    {
        this.rows = rows;
        this.columns = columns;
        this.matrix = Arrays.copyOf(matrix, rows * columns);
    }

    /**
     * DoubleMatrix constructor.  Creates a matrix given a 2D array of doubles.
     *
     * @param matrix The 2D array of doubles from which a matrix will be constructed
     */
    public DoubleMatrix(double[][] matrix)
    {
        rows = matrix.length;
        columns = matrix[0].length;
        this.matrix = new double[rows * columns];

        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++)
                set(i, j, matrix[i][j]);
    }


    /**
     * Calculates the sum of the calling and parameter matrices.
     *
     * @param other The addend matrix.
     * @return A DoubleMatrix object that is the sum of the calling and parameter matrices.
     */
    public DoubleMatrix add(DoubleMatrix other)
    {
        if (rows != other.rows || columns != other.columns)
            throw new IllegalArgumentException();

        DoubleMatrix sum = new DoubleMatrix(rows, columns);
        MatrixIterator iterator = new MatrixIterator(rows, columns);

        while (iterator.hasNext())
        {
            sum.set(iterator, get(iterator) + other.get(iterator));
            iterator.next();
        }

        return sum;
    }

    /**
     * Calculates a matrix that has been multiplied with a scalar
     *
     * @param scalar The scalar by which the matrix will be multiplied.
     * @return A DoubleMatrix equal to the matrix multiplied by the scalar..
     */
    public DoubleMatrix scale(double scalar)
    {
        DoubleMatrix scaled = new DoubleMatrix(rows, columns);
        MatrixIterator iterator = new MatrixIterator(rows, columns);

        while (iterator.hasNext())
        {
            scaled.set(iterator, scalar * get(iterator));
            iterator.next();
        }

        return scaled;
    }

    /**
     * Calculates the transpose of a matrix.
     *
     * @return A DoubleMatrix equal to the transpose of the calling matrix.
     */
    public DoubleMatrix transpose()
    {
        DoubleMatrix transposed = new DoubleMatrix(columns, rows);
        MatrixIterator iterator = new MatrixIterator(rows, columns);

        while (iterator.hasNext())
        {
            transposed.set(iterator.getColumn(), iterator.getRow(), get(iterator));
            iterator.next();
        }

        return transposed;
    }

    /**
     * Calculates matrix multiplication.
     *
     * @throws IllegalArgumentException if the calling (LEFT) matrix's number of columns is
     * NOT equal to the parameter (RIGHT) matrix's number of rows.
     * @param right The multiplier matrix, visualized as being to the RIGHT of the calling
     *              matrix.
     * @return The product of tbe matrix multiplication given that the calling matrix is to
     * the LEFT and the parameter matrix is to the RIGHT.
     */
    public DoubleMatrix multiply(DoubleMatrix right)
    {
        if (columns != right.rows)
            throw new IllegalArgumentException();

        //  The product matrix should have the LEFT (calling) matrix's number of rows and the
        //  RIGHT (parameter) matrix's number of columns.
        double[] product = new double[rows * right.columns];

        //  Given any row and column in the product matrix, the entry at that row and column
        //  is the dot product of the transpose of that row in the LEFT multiplicand matrix
        //  and that column in the RIGHT multiplicand matrix (not transposed).
        MatrixIterator iterator = new MatrixIterator(rows, right.columns);
        while (iterator.hasNext())
        {
            product[iterator.getIndex()] = 0.0;

            for (int i = 0; i < columns; i++)
                product[iterator.getIndex()]
                        += get(iterator.getRow(), i) * right.get(i, iterator.getColumn());

            iterator.next();
        }

        return new DoubleMatrix(rows, right.columns, product);
    }

    /**
     * Calculates matrix multiplication with a parameter vector (that is on the RIGHT).
     *
     * @throws IllegalArgumentException if the calling matrix's number of columns is
     * NOT equal to the number of components (the HEIGHT) of the vector.
     * @param vector The vector that will be on the RIGHT when multiplied with the matrix.
     * @return The vector product of tbe matrix multiplication given that the calling matrix
     * is to the LEFT and the vector is to the RIGHT.
     */
    public DoubleVector multiply(DoubleVector vector)
    {
        if (columns != vector.rows)
            throw new IllegalArgumentException();

        DoubleVector product = new DoubleVector(vector.rows);

        //  Each component in the resulting product is the dot product of this vector and
        //  the transpose of the row in the calling matrix.
        for (int i = 0; i < rows; i++)
        {
            double sum = 0;

            for (int j = 0; j < columns; j++)
                sum += vector.get(j) * get(i, j);

            product.set(i, sum);
        }

        return product;
    }

    /**
     * Creates an identity matrix of a specified size.
     *
     * @throws IllegalArgumentException if size is a non-positive integer.
     * @param size The size of the created identity matrix.
     * @return An identity matrix of the specified size.
     */
    public static DoubleMatrix identity(int size)
    {
        if (size <= 0)
            throw new IllegalArgumentException();

        //  Initialize a matrix full of 0's
        DoubleMatrix iMatrix = new DoubleMatrix(size, size);

        //  Line the main diagonal with 1's
        for (int i = 0; i < size; i++)
            iMatrix.set(i, i, 1.0);

        return iMatrix;
    }



    public DoubleMatrix copy() { return new DoubleMatrix(rows, columns, matrix); }


    /**
     * Swaps two rows within a matrix.  This is an elementary row operation used to
     * bring a matrix into reduced row echelon form.
     *
     * @param row1 The row that will be swapped with row2.
     * @param row2 The row that will be swapped with row1.
     */
    public void rowSwap(int row1, int row2)
    {
        double temp;

        for (int i = 0; i < columns; i++)
        {
            temp = get(row1, i);
            set(row1, i, get(row2, i));
            set(row2, i, temp);
        }
    }

    /**
     * Multiplies a row within a matrix by a scalar value.  This is an elementary row
     * operation used to bring a matrix into reduced row echelon form.
     *
     * @param scalar The scalar by which a row will be multiplied.
     * @param row The row that will be multiplied by a scalar.
     */
    public void rowScale(double scalar, int row)
    {
        for (int i = row * columns; i < (row + 1) * columns; i++)
            matrix[i] *= scalar;
    }

    /**
     * Adds a multiple of one row to another row within a matrix.  This is an elementary row
     * operation used to bring a matrix into reduced row echelon form.
     *
     * @param rowTo The augend row; it will have a multiple of another row added to it.
     * @param scalar The scalar by which the addend row will be multiplied before being added
     *               to the augend row.
     * @param rowFrom The addend row; a multiple of it will be added to another row.
     */
    public void rowAdd(int rowTo, double scalar, int rowFrom)
    {
        for (int i = 0; i < columns; i++)
            matrix[rowTo * columns + i] += matrix[rowFrom * columns + i] * scalar;
    }


    /**
     * Support method.  Determines the column of the leading (nonzero) entry of a given row
     * within a matrix.
     *
     * @throws IllegalArgumentException if the row is not within the matrix's bounds.
     * @param row The row whose leading (nonzero) entry's column will be found.
     * @return The column of the leading (nonzero) entry within the specified row.
     */
    private int leadingIndex(int row)
    {
        if (row >= rows || row < 0)
            throw new IllegalArgumentException();


        int i = 0;

        while(i < columns && FloatMath.isZero(get(row, i)))
            i++;

        return i;
    }

    /**
     * Support method.  Returns the leading (nonzero) entry of a specified row within a matrix
     *
     * @throws IllegalArgumentException if the row is not within the matrix's bounds.
     * @param row The row whose leading (nonzero) entry will be found.
     * @return The leading (nonzero) entry of the specified row.
     */
    private double leadingEntry(int row)
    {
        if (row >= rows || row < 0)
            throw new IllegalArgumentException();


        double leadEntry = 0.0;

        for (int i = 0; i < columns; i++)
            if (!FloatMath.isZero(get(row, i)))
            {
                leadEntry = get(row, i);
                break;
            }

        return leadEntry;
    }

    /**
     * Support method.  Determines if a row within a matrix consists only of zeroes.
     *
     *
     * @throws IllegalArgumentException if the row is not within the matrix's bounds.
     * @param row The row that will be checked to be a zero row.
     * @return true if the row is a zero row; false, otherwise.
     */
    private boolean isZeroRow(int row)
    {
        if (row >= rows || row < 0)
            throw new IllegalArgumentException();


        boolean zeroRow = true;

        for (int i = 0; i < columns; i++)
            if (!FloatMath.isZero(get(row, i)))
            {
                zeroRow = false;
                break;
            }

        return zeroRow;
    }


    /**
     * Support method.  Sorts the rows of a matrix based on the indices of its row's leading
     * entries.  This ensures that rows are swapped such that leading zeroes are pushed to
     * the bottom left of the matrix.  Uses BUBBLE SORT.
     *
     * @param startIndex The row after which the matrix will be sorted (inclusive of the
     *                   startIndex row).
     * @return The number of row swaps that occurred during sorting.
     */
    private int sortRows(int startIndex)
    {
        int swaps = 0;
        boolean unsorted;

        //  This sorts rows based on their leading zeroes using BUBBLE SORT.
        do
        {
            unsorted = false;

            int previousRowZeroes = leadingIndex(startIndex);
            int currentRowZeroes;

            for (int i = startIndex + 1; i < rows; i++)
            {
                currentRowZeroes = leadingIndex(i);

                if (previousRowZeroes > currentRowZeroes)
                {
                    unsorted = true;

                    rowSwap(i, i - 1);
                    swaps++;
                    break;
                }

                previousRowZeroes = currentRowZeroes;
            }
        }
        while (unsorted);

        return swaps;
    }


    /**
     * Converts a matrix into its reduced row echelon form.
     */
    public void rref()
    {
        //  The column that will be reduced to zeroes in every row where it is NOT the column
        //  of the leading entry.
        int eliminateColumn;

        //  For every row within a matrix (from TOP to BOTTOM),
        for (int i = 0; i < rows; i++)
        {
            //  Sort the row and all rows after it based on their number of leading zeroes
            //  in DESCENDING ORDER.  This should emulate a lower triangular.
            sortRows(i);

            //  Because the rows are sorted by leading zeroes, if current row is a zero row,
            //  all the following rows will be zero rows, and sorting is finished.
            if (isZeroRow(i))
                break;

            eliminateColumn = leadingIndex(i);

            //  Divide a row by its leading entry to give it a leading entry of 1.
            rowScale(1.0 / leadingEntry(i), i);

            //  Reduce all the entries in the same column as this row's leading entry to 0 by
            //  means of subtracting multiples of this row from the rows BELOW.
            for (int j = i + 1; j < rows; j++)
                rowAdd(j, -get(j, eliminateColumn), i);
        }

        //  For every row in this matrix (from BOTTOM to TOP),
        //  Reduce all the entries in the same column as this row's leading entry to 0 by
        //  means of subtracting multiples of this row from the rows ABOVE.
        for (int i = rows - 1; i >= 1; i--)
            if (!isZeroRow(i))
            {
                eliminateColumn = leadingIndex(i);

                for (int j = i - 1; j >= 0; j--)
                {
                    rowAdd(j, -get(j, eliminateColumn), i);
                    set(j, eliminateColumn, 0);
                }
            }
    }


    /**
     * Determines if a matrix is an identity matrix.
     *
     * @return true if a matrix is an identity matrix; false if it is NOT SQUARE or not an
     * identity matrix.
     */
    public boolean isIdentity()
    {
        //  All identity matrices are square; therefore, they should have the same amount of
        //  rows as columns.
        if (rows != columns)
            return false;

        boolean isIdentityMatrix = true;
        MatrixIterator iterator = new MatrixIterator(rows, columns);

        //  For all entries in a matrix, this assesses whether the entries along the main
        //  diagonal are equal to 1 and all other entries are equal to 0.
        while (iterator.hasNext())
        {
            if (iterator.getRow() == iterator.getColumn()
                    ? !FloatMath.areEqual(get(iterator), 1.0)
                    : !FloatMath.isZero(get(iterator)))
            {
                isIdentityMatrix = false;
                break;
            }

            iterator.next();
        }

        return isIdentityMatrix;
    }

    /**
     * Augments the calling (LEFT) matrix by appending the parameter (RIGHT) matrix.
     *
     * @throws IllegalArgumentException if the calling and parameter matrices have differing
     * numbers of rows.
     * @param right The matrix that will be appended to the calling matrix's RIGHT.
     * @return An augmented matrix with the calling matrix on the LEFT and the parameter
     * matrix on the RIGHT.
     */
    public DoubleMatrix augment(DoubleMatrix right)
    {
        if (rows != right.rows)
            throw new IllegalArgumentException();

        //  Since entries in DoubleMatrix are stored in a one-dimensional array, the
        //  resulting augmented matrix will contain an array equal in length to the sum of the
        //  lengths of the arrays of the calling and parameter matrices.
        double[] augmented = new double[matrix.length + rows * right.columns];
        MatrixIterator iterator = new MatrixIterator(rows, columns + right.columns);
        int i, j;

        //  This arranges the entries of the calling and parameter matrices into the augmented
        //  matrix such that, BEFORE the width of the calling (LEFT) matrix has been exceeded,
        //  its entries are added, but AFTER that width has been exceeded, the entries of the
        //  parameter (RIGHT) matrix are added.
        while (iterator.hasNext())
        {
            i = iterator.getRow();
            j = iterator.getColumn();

            augmented[iterator.getIndex()]
                    = (j >= columns ? right.get(i, j - columns) : get(i, j));

            iterator.next();
        }

        return new DoubleMatrix(rows, columns + right.columns, augmented);
    }

    /**
     * Provides the right portion of a matrix to a specified width (number of columns).
     * This is useful when separating apart augmented matrices.
     *
     * @throws IllegalArgumentException if columns is less than 1 or greater than the number
     * of columns in the calling matrix.
     * @param columns The number of columns in the resulting sub-matrix.
     * @return A sub-matrix consisting of the specified number of columns from the right side
     * of the calling matrix.
     */
    public DoubleMatrix getRightColumns(int columns)
    {
        if (columns > this.columns || columns <= 0)
            throw new IllegalArgumentException();

        double[] rightMatrix = new double[rows * columns];
        int leftColumns = this.columns - columns;
        MatrixIterator iterator = new MatrixIterator(rows, columns);

        while (iterator.hasNext())
        {
            rightMatrix[iterator.getIndex()]
                    = get(iterator.getRow(), leftColumns + iterator.getColumn());

            iterator.next();
        }

        return new DoubleMatrix(rows, columns, rightMatrix);
    }

    /**
     * Provides the left portion of a matrix to a specified width (number of columns).
     * This is useful when separating apart augmented matrices.
     *
     * @throws IllegalArgumentException if columns is less than 1 or greater than the number
     * of columns in the calling matrix.
     * @param columns The number of columns in the resulting sub-matrix.
     * @return A sub-matrix consisting of the specified number of columns from the left side
     * of the calling matrix.
     */
    public DoubleMatrix getLeftColumns(int columns)
    {
        if (columns > this.columns || columns <= 0)
            throw new IllegalArgumentException();

        double[] leftMatrix = new double[rows * columns];
        MatrixIterator iterator = new MatrixIterator(rows, columns);

        while (iterator.hasNext())
        {
            leftMatrix[iterator.getIndex()] = get(iterator.getRow(), iterator.getColumn());

            iterator.next();
        }

        return new DoubleMatrix(rows, columns, leftMatrix);
    }


    /**
     * Provides the inverse of the calling matrix.
     *
     * @throws IllegalArgumentException if the matrix is NOT square or NOT invertible.
     * @return The calling matrix's inverse.
     */
    public DoubleMatrix inverse()
    {
        if (rows != columns)
            throw new IllegalArgumentException("Non-square matrices have no inverse.");

        //  Augment the matrix with an identity matrix of the same size.
        DoubleMatrix augmented = augment(identity(columns));

        //  Convert the augmented matrix into reduced row echelon form (RREF).
        augmented.rref();

        //  If the left half of the augmented matrix is not the identity matrix
        //  after being converted to RREF, the original matrix was not invertible.
        if (!augmented.getLeftColumns(columns).isIdentity())
            throw new IllegalArgumentException();

        //  Return the RIGHT half of the RREF augmented matrix.
        return augmented.getRightColumns(columns);
    }


    /**
     * Alters a SQUARE matrix using elementary row operations so that the elements above and
     * to the right of te main diagonal are all zero.  This is especially useful for
     * obtaining the determinant of a matrix.
     * Given any square matrix, if it is reduced to its upper or lower triangular using
     * elementary row operations, its determinant is equal to the product of all entries along
     * the main diagonal which, if an ODD amount of row swaps occurred, is MULTIPLIED by -1.
     * Therefore, the return value of this method is 1 if an EVEN amount of row swaps occurred
     * or -1 if an ODD amount of row swaps occurred.
     *
     * @throws IllegalArgumentException if the calling matrix is NOT SQUARE.
     * @return 1 if an EVEN amount of row swaps occurred; -1 if an ODD amount of row swaps
     * occurred.
     */
    public double upperTriangular()
    {
        if (rows != columns)
            throw new IllegalArgumentException();

        double sign = 1;

        int leadIndex;
        double leadEntry;

        for (int i = 0; i < rows; i++)
        {
            //  Multiplies the sign by -1 if sorting after a given row resulted in an ODD
            //  amount of row swaps.
            sign *= (sortRows(i) % 2 == 0 ? 1 : -1);

            if (isZeroRow(i))
                break;

            leadIndex = leadingIndex(i);
            leadEntry = leadingEntry(i);

            //  Adds multiples of rows to the rows above them such that all entries above or
            //  to the left of the main diagonal are zero.
            for (int j = i + 1; j < rows; j++)
                rowAdd(j, -get(j, leadIndex) / leadEntry, i);
        }

        return sign;
    }


    /**
     * Provides the determinant of a SQUARE matrix.  This will propagate an
     * IllegalArgumentException if the matrix is not square.
     *
     * @return The determinant of the matrix.
     */
    public double determinant()
    {
        //  Create a copy of the matrix
        DoubleMatrix test = this.copy();

        //  Determine the coefficient for the determinant based on the number of row swap
        //  operations required to alter the matrix to its upper triangular form.
        double sign = test.upperTriangular();

        double determinant = 1;

        //  The determinant is the product of all entries along the main diagonal
        for (int i = 0; i < rows; i++)
            determinant *= test.get(i, i);

        //  Note that, if an ODD number of row swaps occurred while obtaining the
        //  upper triangular form, the determinant must be multiplied by -1.
        return determinant * sign;
    }
}
