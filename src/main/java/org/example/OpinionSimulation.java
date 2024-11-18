package org.example;

import org.example.models.algorithms.BaseModel;
import org.example.models.algorithms.MajorityModel;
import org.example.models.algorithms.SznajdModel;
import org.example.models.algorithms.VoterModel;
import org.example.models.config.ModelConfig;
import org.example.models.enums.AgentSelection;
import org.example.models.enums.ModelType;

import org.example.models.enums.UpdatingStrategy;
import org.example.models.factory.ModelFactory;
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


    public void simulate() {
        model.updateOneTimeStampFurther();
    }

    public void printStatus() {
        int N = modelConfig.getOpinions().keySet().size();

        if (N == 0){
            System.out.println("[]");
            return;
        }

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

    public void randomizeWithBlueCoefficient(double blueCoefficient){
        if(0.0 <= blueCoefficient && blueCoefficient <= 1.0){
            model.randomizeOpinions(blueCoefficient);
        }
        else {
            throw new IllegalArgumentException("coefficient must be in range [0, 1], but given was: " + blueCoefficient);
        }
    }

    public void generateNewModel(){
        model = ModelFactory.createModel(modelConfig);
    }

    public void setCurrentNodeForSequential(int val){
        if(model instanceof SznajdModel){
            ((SznajdModel) model).setCurrentNode(val);
        }
        if(model instanceof VoterModel){
            ((VoterModel) model).setCurrentNode(val);
        }
    }
}
