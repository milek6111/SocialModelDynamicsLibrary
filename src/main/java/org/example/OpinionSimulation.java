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

/**
 * Class representing the opinion simulation.
 * This class is responsible for setting up the model configuration and running the simulation.
 */
public class OpinionSimulation {
    private ModelConfig modelConfig;
    private BaseModel model;

    /**
     * Constructor to initialize the opinion simulation with specific parameters.
     *
     * @param type the type of the model
     * @param network the network graph representing the connections between agents
     * @param agentSelection the strategy used for selecting agents
     * @param updatingStrategy the strategy used for updating opinions
     */
    public OpinionSimulation(ModelType type, SimpleGraph<Integer, DefaultEdge> network, AgentSelection agentSelection, UpdatingStrategy updatingStrategy) {
        modelConfig = ModelConfig.builder().type(type).network(network).agentSelection(agentSelection).updatingStrategy(updatingStrategy).build();
        model = ModelFactory.createModel(modelConfig);
    }

    /**
     * Constructor to initialize the opinion simulation with a given configuration.
     *
     * @param config the configuration for the model
     */
    public OpinionSimulation(ModelConfig config) {
        modelConfig = config;
        model = ModelFactory.createModel(modelConfig);
    }

    /**
     * Runs the simulation by updating the model one time step further.
     */
    public void simulate() {
        model.updateOneTimeStampFurther();
    }

    /**
     * Prints the current status of the opinions in the model.
     */
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

    /**
     * Gets the map of opinions of the agents.
     *
     * @return a map where the key is the agent's ID and the value is the agent's opinion
     */
    public Map<Integer, Boolean> getOpinionsMap() {
        return new HashMap<>(modelConfig.getOpinions());
    }

    /**
     * Gets the network graph representing the connections between agents.
     *
     * @return the network graph
     */
    public SimpleGraph<Integer, DefaultEdge> getConnectionsGraph() {
        return modelConfig.getNetwork();
    }

    /**
     * Gets the model configuration.
     *
     * @return the model configuration
     */
    public ModelConfig getModelConfig() {
        return modelConfig;
    }

    /**
     * Randomizes the opinions of the agents with a given blue coefficient.
     *
     * @param blueCoefficient the coefficient for randomizing opinions, must be in the range [0, 1]
     * @throws IllegalArgumentException if the coefficient is not in the range [0, 1]
     */
    public void randomizeWithBlueCoefficient(double blueCoefficient){
        if(0.0 <= blueCoefficient && blueCoefficient <= 1.0){
            model.randomizeOpinions(blueCoefficient);
        }
        else {
            throw new IllegalArgumentException("coefficient must be in range [0, 1], but given was: " + blueCoefficient);
        }
    }

    /**
     * Generates a new model based on the current configuration.
     */
    public void generateNewModel(){
        model = ModelFactory.createModel(modelConfig);
    }

    /**
     * Sets the current node for sequential selection in Sznajd or Voter models.
     *
     * @param val the value of the current node
     */
    public void setCurrentNodeForSequential(int val){
        if(model instanceof SznajdModel){
            ((SznajdModel) model).setCurrentNode(val);
        }
        if(model instanceof VoterModel){
            ((VoterModel) model).setCurrentNode(val);
        }
    }

    /**
     * Sets the coefficient for the Majority model.
     *
     * @param coeff the coefficient for the Majority model
     */
    public void setMajorityModelCoeff(double coeff){
        model.setMajorityModelPercentageCoeff(coeff);
    }
}