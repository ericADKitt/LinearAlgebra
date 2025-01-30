//********************************************************************************************
//  DoubleVector.java
//
//  Author: Eric Kitt
//********************************************************************************************

/**
 * DoubleVector is a class that represents a vector of floating point numbers.  It includes
 * common vector operations such as dot product, cross product, etc.  As well, it extends
 * DoubleMatrix, inheriting all its relevant methods and attributes.
 *
 * @author Eric Kitt
 * @version JDK 21.0.2
 */

public class DoubleVector extends DoubleMatrix
{
    /**
     * Accessor for a component of a vector at a specified index.
     *
     * @throws IllegalArgumentException if the index of the component is invalid.
     * @param index The index of the component that will be accessed.
     * @return The component at the specified index.
     */
    public double get(int index)
    {
        if (index > rows || index < 0)
            throw new IllegalArgumentException();

        return matrix[index];
    }

    /**
     * Mutator for a component of a vector at a specified index.
     *
     * @throws IllegalArgumentException if the index of the component is invalid.
     * @param index The index of the component that will be mutated.
     * @param entry The new value for the component at the specified index.
     */
    public void set(int index, double entry)
    {
        if (index > rows || index < 0)
            throw new IllegalArgumentException();

        matrix[index] = entry;
    }


    /**
     * DoubleVector constructor.  Creates a zero vector with a specified number of components.
     *
     * @param length The number of components in the new vector.
     */
    public DoubleVector(int length) { super(length, 1); }

    /**
     * DoubleVector constructor.  Creates a vector with a specified number of components, all
     * of which contain the same value.
     *
     * @param length The number of components in the new vector.
     * @param entry The value of each of the components.
     */
    public DoubleVector(int length, double entry) { super(length, 1, entry); }

    /**
     * DoubleVector constructor.  Creates a DoubleVector object given an array of doubles.
     *
     * @param vector The array of doubles used to create the DoubleVector.
     */
    public DoubleVector(double ... vector) { super(vector.length, 1, vector); }


    /**
     * Creates a standard-basis vector of a specific length for a specified coordinate.
     * i.e., this method creates a zero vector with one component set to 1.
     *
     * @throws IllegalArgumentException if coordinate is invalid given the vector's length.
     * @param length The number of components in the vector.
     * @param coordinate The index of the component that will be set to 1.
     * @return The standard basis vector.
     */
    public static DoubleVector basis(int length, int coordinate)
    {
        if (coordinate < 0 || coordinate >= length)
            throw new IllegalArgumentException();

        DoubleVector basisVector = new DoubleVector(length);
        basisVector.set(coordinate, 1.0);

        return basisVector;
    }


    /**
     * Calculates the dot product with another vector.
     *
     * @throws IllegalArgumentException if the vector have differing numbers of components.
     * @param other The other vector in the dot product calculation.
     * @return The value of the dot product.
     */
    public double dotProduct(DoubleVector other)
    {
        if (rows != other.rows)
            throw new IllegalArgumentException();

        double product = 0;

        for (int i = 0; i < rows; i++)
            product += get(i) * other.get(i);

        return product;
    }

    /**
     * Calculates the cross product with another vector.  Both the calling and parameter
     * vectors must have THREE COMPONENTS.
     *
     * @throws IllegalArgumentException if either the calling or parameter vector does not\
     * have THREE COMPONENTS
     * @param other The RIGHT vector in the cross product calculation.
     * @return A DoubleVector object equal to the cross product of the calling (LEFT) and
     * parameter (RIGHT) vectors.
     */
    public DoubleVector crossProduct(DoubleVector other)
    {
        if (rows != 3 || other.rows != 3)
            throw new IllegalArgumentException();

        DoubleVector product = new DoubleVector(3);

        for (int i = 0; i < 3; i++)
        {
            int next = (i + 1) % 3;
            int prev = (i + 2) % 3;

            product.set(i, get(next) * other.get(prev) - get(prev) * other.get(next));
        }

        return product;
    }

    /**
     * Adds two vectors.
     *
     * @param other The addend vector.
     * @return The sum of the calling and parameter vectors.
     */
    public DoubleVector add(DoubleVector other)
    {
        if (rows != other.rows)
            throw new IllegalArgumentException();

        DoubleVector sum = new DoubleVector(rows);

        for (int i = 0; i < rows; i++)
            sum.set(i, get(i) + other.get(i));

        return sum;
    }

    /**
     * Multiplies the vector by a scalar.
     *
     * @param scalar The scalar by which the vector will be multiplied.
     * @return The calling vector multiplied by the parameter scalar.
     */
    public DoubleVector scale(double scalar)
    {
        DoubleVector scaled = new DoubleVector(rows);

        for (int i = 0; i < rows; i++)
            scaled.set(i, get(i) * scalar);

        return scaled;
    }

    /**
     * Subtracts the parameter (subtrahend) vector from the calling (minuend) vector.
     *
     * @param subtrahend The vector that will be subtracted from the calling (minuend) vector.
     * @return The difference between the calling (minuend) and parameter (subtrahend) vectors
     */
    public DoubleVector subtract(DoubleVector subtrahend)
    {
        return add(subtrahend.scale(-1));
    }


    /**
     * Performs a dot product between the calling vector and itself.  Note that this is
     * equivalent to the squared length of the vector.
     *
     * @return The squared length of the vector.
     */
    public double squareLength()
    {
        double squareSum = 0;

        for (int i = 0; i < rows; i++)
        {
            double component = get(i);
            squareSum += component * component;
        }

        return squareSum;
    }

    /**
     * Provides the length of the vector.  Note that this is simply the square root of the
     * dot product between the vector and itself.
     *
     * @return The length of the vector.
     */
    public double length() { return Math.sqrt(squareLength()); }

    /**
     * Obtains the distance between the calling and parameter vectors.
     *
     * @param vectorTo The vector that is compared to the calling vector in terms of distance.
     * @return The absolute (scalar) distance between the calling and parameter vectors.
     */
    public double distance(DoubleVector vectorTo)
    {
        return vectorTo.subtract(this).length();
    }

    /**
     * Obtains the unit vector of a calling vector.  This will throw an exception if one
     * attempts to normalize a zero vector due to division by zero.
     *
     * @throws IllegalArgumentException if the vector is a zero vector.
     * @return The unit vector of the calling vector.
     */
    public DoubleVector normalize()
    {
        double length = length();

        if (FloatMath.isZero(length))
            throw new IllegalArgumentException();

        return scale(1.0 / length);
    }

    /**
     * Obtains the unit vector of the vector difference from the calling vector to the
     * parameter vector.  Note that, due to division by zero, this method will propagate an
     * IllegalArgumentException if the calling and parameter vectors are roughly equal.
     *
     * @param vectorTo The vector toward which the returned vector points from the calling
     *                 vector.
     * @return The unit vector of the vector difference from the calling vector to the
     * parameter vector
     */
    public DoubleVector direction(DoubleVector vectorTo)
    {
        return vectorTo.subtract(this).normalize();
    }

    /**
     * Projects the calling vector onto the parameter vector.  Please note the equation for
     * vector projection is as follows: such that a is the calling vector, b is the parameter
     * vector, / represents division, and * represents dot product or scalar multiplication,
     * the projection of a onto b is obtained with ( ( a * b ) / ( b * b ) ) * b.
     *
     * @throws IllegalArgumentException if the parameter vector is a zero vector (due to
     * division by zero).
     * @param vectorOnto The vector onto which the calling vector will be projected.
     * @return The vector projection of the calling vector onto the parameter vector.
     */
    public DoubleVector project(DoubleVector vectorOnto)
    {
        double ontoSquareLength = vectorOnto.squareLength();

        if (FloatMath.isZero(ontoSquareLength))
            throw new IllegalArgumentException();

        return vectorOnto.scale(dotProduct(vectorOnto) / ontoSquareLength);
    }
}
