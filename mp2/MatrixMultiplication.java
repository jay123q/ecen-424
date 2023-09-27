import java.util.Random;

public class MatrixMultiplication {
    private static final int matrixSize = 20;
    private static final int threadNum = 5;

    private static int[][] matrixA = new int[matrixSize][matrixSize];
    private static int[][] matrixB = new int[matrixSize][matrixSize];
    private static int[][] matrixFinal = new int[matrixSize][matrixSize];

    public static void matrixMultiplication(int startRow, int endRow) {
        for (int i = startRow; i < endRow; i++) {
            for (int j = 0; j < matrixSize; j++) {
                for (int k = 0; k < matrixSize; k++) {
                    // i j for the final proper, then ik shows normal and ib is the proper mult
                    matrixFinal[i][j] += matrixA[i][k] * matrixB[k][j];
                }
            }
        }
    }

    public static void main(String[] args) {
        // RANDOMIZE THIS
        Random random = new Random();

        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {

                matrixA[i][j] = random.nextInt(11); // 0-10
                matrixB[i][j] = random.nextInt(11);
            }
        }
        System.out.print(" PRINT MATRIX A \n");
        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                if (j < 19) {
                    System.out.print(matrixA[i][j] + ",");
                } else {
                    System.out.print(matrixA[i][j]);

                }
            }
            System.out.println();
        }

        System.out.print(" PRINT MATRIX B \n ");
        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                if (j < 19) {
                    System.out.print(matrixB[i][j] + ",");
                } else {
                    System.out.print(matrixB[i][j]);

                }
            }
            System.out.println();
        }

        Thread[] threads = new Thread[threadNum];

        for (int i = 0; i < threadNum; i++) {

            int startRow = i * (matrixSize / threadNum); // i * 4 = 0
            int endRow = (i + 1) * (matrixSize / threadNum); // (i+1)*4 = 4
            threads[i] = new Thread(() -> matrixMultiplication(startRow, endRow));
            threads[i].start();
        }

        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.print(" PRINT FINAL MATRIX \n");
        for (int i = 0; i < matrixSize; i++) {
            for (int j = 0; j < matrixSize; j++) {
                System.out.print(matrixFinal[i][j] + " ");
            }
            System.out.println();
        }
    }
}
