import java.util.Random;

public class MatrixMultiplication {
    private static final int MATRIX_SIZE = 20;
    private static final int NUM_THREADS = 5;

    private static int[][] matrixA = new int[MATRIX_SIZE][MATRIX_SIZE];
    private static int[][] matrixB = new int[MATRIX_SIZE][MATRIX_SIZE];
    private static int[][] resultMatrix = new int[MATRIX_SIZE][MATRIX_SIZE];

    public static void matrixMultiplication(int startRow, int endRow) {
        for (int i = startRow; i < endRow; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                for (int k = 0; k < MATRIX_SIZE; k++) {
                    // i j for the final proper, then ik shows normal and ib is the proper mult
                    resultMatrix[i][j] += matrixA[i][k] * matrixB[k][j];
                }
            }
        }
    }

    public static void main(String[] args) {
        // RANDOMIZE THIS
        Random random = new Random();

        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                // need some bound, I picked 10
                matrixA[i][j] = random.nextInt(10) + 1;
                matrixB[i][j] = random.nextInt(10) + 1;
            }
        }
        System.out.print(" PRINT MATRIX A \n");
        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                if (j < 19) {
                    System.out.print(matrixA[i][j] + ",");
                } else {
                    System.out.print(matrixA[i][j]);

                }
            }
            System.out.println();
        }

        System.out.print(" PRINT MATRIX B \n ");
        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                if (j < 19) {
                    System.out.print(matrixB[i][j] + ",");
                } else {
                    System.out.print(matrixB[i][j]);

                }
            }
            System.out.println();
        }

        Thread[] threads = new Thread[NUM_THREADS];

        for (int i = 0; i < NUM_THREADS; i++) {

            int startRow = i * (MATRIX_SIZE / NUM_THREADS); // i * 4 = 0
            int endRow = (i + 1) * (MATRIX_SIZE / NUM_THREADS); // (i+1)*4 = 4
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
        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                System.out.print(resultMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}
