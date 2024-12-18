package org.example.models.algorithms;

import org.example.models.enums.AgentSelection;
import org.example.models.enums.UpdatingStrategy;
import org.jgrapht.graph.DefaultEdge;

import java.util.ArrayList;

/**
 * VoterModel class that extends the BaseModel.
 * This class implements the voter model for opinion dynamics.
 */
final public class VoterModel extends BaseModel {
    /**
     * The current node being processed.
     */
    private int currentNode = 0;

    /**
     * Updates the model by one time step.
     * If the updating strategy is N_TIMES, it updates the opinions N times.
     * Otherwise, it updates the opinions once.
     */
    @Override
    public void updateOneTimeStampFurther() {
        if (updatingStrategy.equals(UpdatingStrategy.N_TIMES)) {
            if (agentSelection.equals(AgentSelection.RANDOM)) {
                for (int i = 0; i < N; i++) {
                    updateOpinion();
                }
            } else {
                for (int i = 0; i < N; i++) {
                    updateOpinion(i);
                }
            }
        } else {
            if (agentSelection.equals(AgentSelection.RANDOM)) {
                updateOpinion();
            } else {
                updateOpinion(currentNode);
                currentNode = (currentNode++ >= N) ? 0 : currentNode;
            }
        }
    }

    /**
     * Updates the opinions of agents based on the voter model.
     * It selects a random agent and updates its opinion.
     */
    @Override
    public void updateOpinion() {
        if (N == 0) return;
        updateOpinion(getRandomAgent());
    }

    /**
     * Updates the opinion of a specific agent.
     * It finds a neighbor of the agent and adopts the neighbor's opinion.
     *
     * @param agent the ID of the agent whose opinion is to be updated
     */
    @Override
    public void updateOpinion(Integer agent) {
        int chosenNeighbour = findNeighbour(agent);

        if (chosenNeighbour == -1) return;

        opinions.put(agent, opinions.get(chosenNeighbour));
    }

    /**
     * Finds a neighbor of a given agent.
     *
     * @param agent the ID of the agent
     * @return the ID of a neighbor, or -1 if no neighbors are found
     */
    private int findNeighbour(int agent) {
        ArrayList<DefaultEdge> neighbors = new ArrayList<>(network.outgoingEdgesOf(agent));

        if (neighbors.isEmpty()) {
            return -1;
        }

        DefaultEdge chosenRelation = neighbors.get(getRandomAgentFromSize(neighbors.size()));
        return !network.getEdgeTarget(chosenRelation).equals(agent) ? network.getEdgeTarget(chosenRelation) : network.getEdgeSource(chosenRelation);
    }

    /**
     * Sets the current node being processed.
     *
     * @param val the value to set as the current node
     */
    public void setCurrentNode(int val){
        currentNode = val;
    }
}