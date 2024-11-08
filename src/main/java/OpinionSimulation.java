import models.config.ModelConfig;
import models.enums.ModelType;
import models.factory.ModelFactory;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

public class OpinionSimulation {
    private ModelConfig modelConfig;

    public OpinionSimulation(ModelType type, int iterations, Graph<Integer, DefaultEdge> network){
        modelConfig = ModelConfig.builder().type(type).iterations(iterations).network(network).build();

    }

    Graph<Integer, DefaultEdge> simulate(){

        return null;
    }
}
