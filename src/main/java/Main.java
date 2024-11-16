import models.config.ModelConfig;
import models.enums.AgentSelection;
import models.enums.ModelType;
import models.enums.UpdatingStrategy;
import models.factory.ModelFactory;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        ModelConfig config = new ModelConfig();
        config.setAgentSelection(AgentSelection.RANDOM);
        config.setType(ModelType.VOTER);
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

        config.setNetwork(gridGraph);
        config.setOpinions(opinions);

        System.out.println("Wierzchołki: " + gridGraph.vertexSet());
        System.out.println("Krawędzie: " + gridGraph.edgeSet());


        OpinionSimulation simulation = new OpinionSimulation(config);

        simulation.printStatus();
        for (int i = 0; i < 5; i++) {
            simulation.simulate();
            simulation.printStatus();
        }
    }
}
