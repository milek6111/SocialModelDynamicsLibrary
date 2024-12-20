package org.example;

import org.example.models.config.ModelConfig;
import org.example.models.enums.AgentSelection;
import org.example.models.enums.ModelType;
import org.example.models.enums.UpdatingStrategy;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        ModelConfig config = new ModelConfig();
        config.setAgentSelection(AgentSelection.RANDOM);
        config.setType(ModelType.SZNAJD);
        config.setUpdatingStrategy(UpdatingStrategy.ONE_STEP);

        SimpleGraph<Integer, DefaultEdge> gridGraph = new SimpleGraph<>(DefaultEdge.class);

        int rows = 5;
        int cols = 5;

        for (int i = 0; i < rows * cols; i++) {
            gridGraph.addVertex(i);
        }

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int current = row * cols + col;

                if (col < cols - 1) {
                    int right = current + 1;
                    gridGraph.addEdge(current, right);
                }

                if (row < rows - 1) {
                    int down = current + cols;
                    gridGraph.addEdge(current, down);
                }
            }
        }

        Map<Integer, Boolean> opinions = new HashMap<>();
        for (int i = 0; i < rows*cols; i++) {
            opinions.put(i,new Random().nextBoolean());
        }

        //config.setNetwork(gridGraph);
//        config.graphFromNeighbourMatrix(new int[][]{{0,1}, {1,0}});
        //config.setOpinions(opinions);
        //config.generateFullGraph(100);
        config.generateSquareGraph(5);
        config.randomizeOpinions();

        System.out.println("Wierzchołki: " + config.getNetwork().vertexSet());
        System.out.println("Krawędzie: " + config.getNetwork().edgeSet());

//        gridGraph.removeEdge(0,1);
//        System.out.println("Krawędzie: " + gridGraph.edgeSet());


        OpinionSimulation simulation = new OpinionSimulation(config);

        simulation.printStatus();
        for (int i = 0; i < 5; i++) {
            simulation.simulate();
            simulation.printStatus();
        }
    }
}
