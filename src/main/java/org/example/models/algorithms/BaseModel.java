package org.example.models.algorithms;

import lombok.Getter;
import lombok.Setter;
import org.example.models.enums.AgentSelection;
import org.example.models.enums.UpdatingStrategy;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Abstract base class for different models in the simulation.
 * This class provides common properties and methods for models
 * that simulate opinion dynamics on a network.
 */
@Setter
@Getter
abstract sealed public class BaseModel permits MajorityModel, VoterModel, SznajdModel {
    /**
     * The network graph representing the connections between agents.
     */
    protected Graph<Integer, DefaultEdge> network;

    /**
     * A map storing the opinions of agents, where the key is the agent's ID
     * and the value is the agent's opinion (true or false).
     */
    protected Map<Integer, Boolean> opinions;

    /**
     * The number of agents in the network.
     */
    protected int N;

    /**
     * The strategy used for selecting agents.
     */
    protected AgentSelection agentSelection;

    /**
     * The strategy used for updating opinions.
     */
    protected UpdatingStrategy updatingStrategy;

    /**
     * Random number generator used for various random operations.
     */
    private Random randomGenerator = new Random();

    /**
     * Updates the model by one time step.
     * This method should be implemented by subclasses to define
     * how the model evolves over time.
     */
    public abstract void updateOneTimeStampFurther();

    /**
     * Updates the opinions of agents in the model.
     * This method should be implemented by subclasses to define
     * how opinions are updated.
     */
    public abstract void updateOpinion();

    /**
     * Updates the opinion of a specific agent.
     * This method calls the abstract updateOpinion method.
     *
     * @param agent the ID of the agent whose opinion is to be updated
     */
    public void updateOpinion(Integer agent) {
        updateOpinion();
    }

    /**
     * Returns a random agent ID from the network.
     *
     * @return a random agent ID
     */
    protected int getRandomAgent() {
        return getRandomAgentFromSize(N);
    }

    /**
     * Returns a random agent ID from a specified size.
     *
     * @param size the size from which to select a random agent ID
     * @return a random agent ID
     */
    protected int getRandomAgentFromSize(int size) {
        return randomGenerator.nextInt(size);
    }

    /**
     * Randomizes the opinions of all agents in the network based on a given coefficient.
     * The coefficient determines the probability of an agent having a false opinion.
     *
     * @param blueCoefficient the probability threshold for assigning false opinions
     */
    public void randomizeOpinions(double blueCoefficient) {
        opinions.clear();
        for (int i = 0; i < N; i++) {
            opinions.put(i, randomGenerator.nextDouble() > blueCoefficient);
        }
    }

    /**
     * Sets the percentage coefficient for the MajorityModel.
     * This method is intended to be overridden by subclasses if needed.
     *
     * @param coeff the coefficient to set
     */
    public void setMajorityModelPercentageCoeff(double coeff){

    }
}
