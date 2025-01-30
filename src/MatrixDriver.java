public class MatrixDriver
{
    public static void main(String[] args)
    {
        /*DoubleMatrix translationMat = new DoubleMatrix(3, 3,
                1, 0, 2, 0, 1, 3, 0, 0, 1);
        DoubleMatrix scaleMat = new DoubleMatrix(3, 3,
                5, 0, 0, 0, 7, 0, 0, 0, 1);


        System.out.println(translationMat);
        System.out.println(translationMat.inverse());
        System.out.println(translationMat.inverse().multiply(scaleMat).multiply(translationMat));*/

        DoubleMatrix mat2 = new DoubleMatrix(3, 3,
                3, -2, 4, -1, 5, 2, -9, -2, 3);

        System.out.println(mat2.determinant());
    }
}