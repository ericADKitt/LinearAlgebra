//********************************************************************************************
//  FloatMath.java
//
//  Author: Eric Kitt
//********************************************************************************************

/**
 * FloatMath defines common functions necessary for floating point arithmetic, such as
 * determining floating point equality within a specified tolerance.  By default, this
 * tolerance is set to 0.0001.
 *
 * @author Eric Kitt
 * @version JDK 21.0.2
 */

public class FloatMath
{
    //  The maximum difference between floating point numbers when determining equality
    private static double tolerance = 0.0001;

    /**
     * Determines if a floating point number is close to zero within a specified tolerance
     *
     * @param num The floating point number that compared to 0.
     * @return true if the absolute difference between num and 0 is less than tolerance;
     * false, otherwise.
     */
    public static boolean isZero(double num) { return (Math.abs(num) < tolerance); }

    /**
     * Compares all elements in an array of floating point numbers to the first element.
     * If the difference between the first element and any other element is greater
     * than tolerance, returns false; otherwise, true is returned.
     *
     * @param numbers The array of floating point numbers that are compared.
     * @return true if all elements in numbers are equal within a tolerance; false, otherwise.
     */
    public static boolean areEqual(double ... numbers)
    {
        boolean equal = true;

        for (int i = 1; i < numbers.length; i++)
            if (!isZero(numbers[i] - numbers[0]))
            {
                equal = false;
                break;
            }

        return equal;
    }

    /**
     * Mutator for the tolerance variable; tolerance is the maximum tolerable difference
     * between floating point numbers when determining equality.  Its default value is
     * 0.0001.
     *
     * @param tolerance Maximum floating point difference when determining equality.
     */
    public static void setTolerance(double tolerance) { FloatMath.tolerance = tolerance; }
}
