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

        DoubleMatrix mat = new DoubleMatrix(3, 3,-3, -2, -4, 1, 1, 4, 4, 4, -4);
        DoubleVector vec = new DoubleVector(-2, -3, 3);
        System.out.println(mat.multiply(vec));

        DoubleMatrix mat1 = new DoubleMatrix(4, 4, 1, 0, 0, 0, 3, 1, 0, 0, 4, 6, 1, 0, -9, 9, 3, 1);
        DoubleMatrix mat2 = new DoubleMatrix(4, 3, 8, 6, -3, 0, 5, 3, 0, 0, 2, 0, 0, 0);
        DoubleVector vec1 = new DoubleVector(-17, -44, -28, 210);
        System.out.println("\n" + mat1.multiply(mat2).augment(vec1).rref());
    }
}