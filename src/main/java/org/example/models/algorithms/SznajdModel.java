package org.example.models.algorithms;

import org.example.models.enums.AgentSelection;
import org.example.models.enums.UpdatingStrategy;
import org.jgrapht.graph.DefaultEdge;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * SznajdModel class that extends the BaseModel.
 * This class implements the Sznajd model for opinion dynamics.
 */
final public class SznajdModel extends BaseModel {
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
                currentNode = (++currentNode >= N) ? 0 : currentNode;
            }
        }
    }

    /**
     * Updates the opinions of agents based on the Sznajd model.
     * It selects a random agent and updates its opinion.
     */
    @Override
    public void updateOpinion() {
        if (N == 0) return;
        updateOpinion(getRandomAgent());
    }

    /**
     * Updates the opinion of a specific agent.
     * It finds a neighbor of the agent and checks if they agree.
     * If they agree, it updates the opinions of all their neighbors.
     *
     * @param agent the ID of the agent whose opinion is to be updated
     */
    @Override
    public void updateOpinion(Integer agent) {
        int firstNeighbour = agent;
        int secondNeighbour = findNeighbour(firstNeighbour);

        if (secondNeighbour == -1) {
            return;
        }

        boolean aggreement = opinions.get(firstNeighbour) == opinions.get(secondNeighbour);

        if (!aggreement) {
            return;
        }

        boolean agreedOpinion = opinions.get(firstNeighbour);

        Set<Integer> allNeighbours = getAllNeighbours(firstNeighbour, secondNeighbour);

        for (Integer vertex : allNeighbours) {
            opinions.put(vertex, agreedOpinion);
        }
    }

    /**
     * Gets all the neighbors of two agents.
     *
     * @param firstAgent the ID of the first agent
     * @param secondAgent the ID of the second agent
     * @return a set of IDs representing all the neighbors of the two agents
     */
    private Set<Integer> getAllNeighbours(int firstAgent, int secondAgent) {
        Set<DefaultEdge> firstAgentNeighbours = network.outgoingEdgesOf(firstAgent);
        Set<DefaultEdge> secondAgentNeighbours = network.outgoingEdgesOf(secondAgent);

        Set<Integer> allNeighbours = firstAgentNeighbours.stream()
                .map(x -> {
                    return !network.getEdgeTarget(x).equals(firstAgent) ? network.getEdgeTarget(x) : network.getEdgeSource(x);
                })
                .collect(Collectors.toSet());

        Set<Integer> temp = secondAgentNeighbours.stream()
                .map(x -> {
                    return !network.getEdgeTarget(x).equals(secondAgent) ? network.getEdgeTarget(x) : network.getEdgeSource(x);
                })
                .collect(Collectors.toSet());

        allNeighbours.addAll(temp);
        allNeighbours.remove(firstAgent);
        allNeighbours.remove(secondAgent);

        return allNeighbours;
    }

    /**
     * Finds a neighbor of a given agent.
     *
     * @param agent the ID of the agent
     * @return the ID of a neighbor, or -1 if no neighbors are found
     */
    private int findNeighbour(int agent) {
        ArrayList<DefaultEdge> defaultEdges = new ArrayList<>(network.outgoingEdgesOf(agent));

        if (defaultEdges.isEmpty()) return -1;

        DefaultEdge edge = defaultEdges.get(getRandomAgentFromSize(defaultEdges.size()));

        return !network.getEdgeTarget(edge).equals(agent) ? network.getEdgeTarget(edge) : network.getEdgeSource(edge);
    }

    /**
     * Sets the current node being processed.
     *
     * @param val the value to set as the current node
     */
    public void setCurrentNode(int val) {
        currentNode = val;
    }
}