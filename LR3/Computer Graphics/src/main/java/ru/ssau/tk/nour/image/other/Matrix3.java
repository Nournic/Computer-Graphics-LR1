package ru.ssau.tk.nour.image.other;

public class Matrix3 {
    private double[][] matrix;

    public Matrix3(double[][] matrix) {
        if(matrix.length != 3 || matrix[0].length != 3)
            throw new ArithmeticException("Matrix is not 3x3");
        this.matrix = matrix;
    }

    public Vector3 multVector(Vector3 vec){
        return new Vector3(
                matrix[0][0] * vec.getX() + matrix[1][0] * vec.getY() + matrix[2][0],
                matrix[0][1] * vec.getX() + matrix[1][1] * vec.getY() + matrix[2][1],
                matrix[0][2] * vec.getX() + matrix[1][2] * vec.getY() + matrix[2][2]
        );
    }
}
