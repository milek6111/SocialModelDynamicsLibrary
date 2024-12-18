package org.example.models.config;

import lombok.*;
import org.example.models.enums.AgentSelection;
import org.example.models.enums.ModelType;
import org.example.models.enums.UpdatingStrategy;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Configuration class for the model.
 * This class holds the configuration settings for the model, including the type of model,
 * the network graph, the opinions of agents, the agent selection strategy, and the updating strategy.
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ModelConfig {
    /**
     * The type of the model.
     */
    ModelType type = ModelType.VOTER;

    /**
     * The network graph representing the connections between agents.
     */
    SimpleGraph<Integer, DefaultEdge> network;

    /**
     * A map storing the opinions of agents, where the key is the agent's ID
     * and the value is the agent's opinion (true or false).
     */
    Map<Integer, Boolean> opinions;

    /**
     * The strategy used for selecting agents.
     */
    AgentSelection agentSelection;

    /**
     * The strategy used for updating opinions.
     */
    UpdatingStrategy updatingStrategy = UpdatingStrategy.N_TIMES;

    /**
     * Generates a graph from a given neighbor matrix.
     *
     * @param matrix the neighbor matrix
     * @throws IllegalArgumentException if the matrix is not square
     */
    public void graphFromNeighbourMatrix(int[][] matrix) {
        if (!checkIsSquare(matrix)) {
            throw new IllegalArgumentException("Matrix must be NxN size");
        }

        network = new SimpleGraph<>(DefaultEdge.class);
        int size = matrix.length;

        for (int i = 0; i < size; i++) {
            network.addVertex(i);
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == 1) {
                    network.addEdge(i, j);
                }
            }
        }
    }

    /**
     * Generates a graph from a given neighbor matrix.
     *
     * @param matrix the neighbor matrix
     * @throws IllegalArgumentException if the matrix is not square
     */
    public void graphFromNeighbourMatrix(Integer[][] matrix) {
        if (!checkIsSquare(matrix)) {
            throw new IllegalArgumentException("Matrix must be NxN size");
        }

        network = new SimpleGraph<>(DefaultEdge.class);

        int size = matrix.length;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == 1) {
                    network.addEdge(i, j);
                }
            }
        }
    }

    /**
     * Generates a graph from a neighbor matrix read from a file.
     *
     * @param filePath the path to the file containing the neighbor matrix
     */
    public void generateMatrixFromFile(String filePath) {
        int[][] matrix = readFromFile(filePath);
        graphFromNeighbourMatrix(matrix);
    }

    /**
     * Generates a fully connected graph with a given number of vertices.
     *
     * @param verticesNum the number of vertices in the graph
     */
    public void generateFullGraph(int verticesNum) {
        network = new SimpleGraph<>(DefaultEdge.class);
        for (int i = 0; i < verticesNum; i++) {
            network.addVertex(i);
        }

        for (int i = 0; i < verticesNum; i++) {
            for (int j = 0; j < verticesNum; j++) {
                if (i != j) {
                    network.addEdge(i, j);
                }
            }
        }
    }

    /**
     * Generates a square grid graph with a given side length.
     *
     * @param sideNum the side length of the square grid
     */
    public void generateSquareGraph(int sideNum) {
        network = new SimpleGraph<>(DefaultEdge.class);
        for (int i = 0; i < Math.pow(sideNum, 2); i++) {
            network.addVertex(i);
        }

        for (int i = 0; i < sideNum; i++) {
            for (int j = 0; j < sideNum; j++) {
                int currentVertex = i * sideNum + j;

                if (j > 0) {
                    int leftNeighbor = i * sideNum + (j - 1);
                    network.addEdge(currentVertex, leftNeighbor);
                }

                if (j < sideNum - 1) {
                    int rightNeighbor = i * sideNum + (j + 1);
                    network.addEdge(currentVertex, rightNeighbor);
                }

                if (i > 0) {
                    int topNeighbor = (i - 1) * sideNum + j;
                    network.addEdge(currentVertex, topNeighbor);
                }

                if (i < sideNum - 1) {
                    int bottomNeighbor = (i + 1) * sideNum + j;
                    network.addEdge(currentVertex, bottomNeighbor);
                }
            }
        }
    }

    /**
     * Randomizes the opinions of all agents in the network.
     */
    public void randomizeOpinions() {
        Random random = new Random();

        if (network == null) {
            return;
        }

        int size = network.vertexSet().size();

        opinions = new HashMap<>();
        for (int i : network.vertexSet()) {
            opinions.put(i, random.nextBoolean());
        }
    }

    /**
     * Reads a neighbor matrix from a file.
     *
     * @param filePath the path to the file containing the neighbor matrix
     * @return the neighbor matrix
     * @throws RuntimeException if the file cannot be read
     * @throws IllegalArgumentException if the file contains invalid number format
     */
    private int[][] readFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            return br.lines()
                    .map(line -> line.trim().split("\\s+"))
                    .map(arr -> {
                        int[] row = new int[arr.length];
                        for (int i = 0; i < arr.length; i++) {
                            row[i] = Integer.parseInt(arr[i]);
                        }
                        return row;
                    })
                    .toArray(int[][]::new);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read matrix from file", e);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Matrix file contains invalid number format", e);
        }
    }

    /**
     * Checks if a given matrix is square.
     *
     * @param matrix the matrix to check
     * @return true if the matrix is square, false otherwise
     */
    private boolean checkIsSquare(int[][] matrix) {
        try {
            int n = matrix.length;
            for (int i = 0; i < n; i++) {
                if (n != matrix[i].length) return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks if a given matrix is square.
     *
     * @param matrix the matrix to check
     * @return true if the matrix is square, false otherwise
     */
    private boolean checkIsSquare(Integer[][] matrix) {
        try {
            int n = matrix.length;
            for (int i = 0; i < n; i++) {
                if (n != matrix[i].length) return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}