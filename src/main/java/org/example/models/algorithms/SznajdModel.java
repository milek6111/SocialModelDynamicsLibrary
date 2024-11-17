package org.example.models.algorithms;

import org.example.models.enums.AgentSelection;
import org.example.models.enums.UpdatingStrategy;
import org.jgrapht.graph.DefaultEdge;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

final public class SznajdModel extends BaseModel {
    private int currentNode = 0;

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

    @Override
    public void updateOpinion() {
        if (N == 0) return;
        updateOpinion(getRandomAgent());
    }

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

    private int findNeighbour(int agent) {

        ArrayList<DefaultEdge> defaultEdges = new ArrayList<DefaultEdge>(network.outgoingEdgesOf(agent));

        if (defaultEdges.isEmpty()) return -1;

        DefaultEdge edge = defaultEdges.get(getRandomAgentFromSize(defaultEdges.size()));

        return !network.getEdgeTarget(edge).equals(agent) ? network.getEdgeTarget(edge) : network.getEdgeSource(edge);

    }
}
