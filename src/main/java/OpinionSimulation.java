import lombok.Getter;
import models.algorithms.BaseModel;
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


public class OpinionSimulation {
    private ModelConfig modelConfig;
    private BaseModel model;

    public OpinionSimulation(ModelType type, SimpleGraph<Integer, DefaultEdge> network, AgentSelection agentSelection, UpdatingStrategy updatingStrategy) {
        modelConfig = ModelConfig.builder().type(type).network(network).agentSelection(agentSelection).updatingStrategy(updatingStrategy).build();
        model = ModelFactory.createModel(modelConfig);
    }

    public OpinionSimulation(ModelConfig config) {
        modelConfig = config;
        model = ModelFactory.createModel(modelConfig);
    }


    void simulate() {
        model.updateOneTimeStampFurther();
    }

    public void printStatus() {
        int N = modelConfig.getOpinions().keySet().size();
        System.out.print("[");
        for (int i = 0; i < N; i++) {
            System.out.print(modelConfig.getOpinions().get(i) + ", ");
        }
        System.out.println("\b\b]");
    }

    public Map<Integer, Boolean> getOpinionsMap() {
        return new HashMap<>(modelConfig.getOpinions());
    }

    public SimpleGraph<Integer, DefaultEdge> getConnectionsGraph() {
        return modelConfig.getNetwork();
    }

    public ModelConfig getModelConfig() {
        return modelConfig;
    }
}
