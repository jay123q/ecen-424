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
                matrixA[i][j] = random.nextInt(10) + 1;
                matrixB[i][j] = random.nextInt(10) + 1;
            }
        }
        System.out.print(" PRINT MATRIX A \n");
        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                System.out.print(matrixA[i][j] + " ");
            }
            System.out.println();
        }

        System.out.print(" PRINT MATRIX B \n ");
        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                System.out.print(matrixB[i][j] + " ");
            }
            System.out.println();
        }

        Thread[] threads = new Thread[NUM_THREADS];

        // Create threads for matrix multiplication
        for (int i = 0; i < NUM_THREADS; i++) {

            int startRow = i * (MATRIX_SIZE / NUM_THREADS);
            int endRow = (i + 1) * (MATRIX_SIZE / NUM_THREADS);
            threads[i] = new Thread(() -> matrixMultiplication(startRow, endRow));
            threads[i].start();
        }

        // Wait for all threads to complete
        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Print the result matrix
        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                System.out.print(resultMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}
