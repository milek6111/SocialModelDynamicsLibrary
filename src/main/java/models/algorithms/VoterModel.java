package models.algorithms;

import models.enums.AgentSelection;
import models.enums.UpdatingStrategy;
import org.jgrapht.graph.DefaultEdge;

import java.util.ArrayList;

final public class VoterModel extends BaseModel {
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
                currentNode = (currentNode++ >= N) ? 0 : currentNode;
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
        int chosenNeighbour = findNeighbour(agent);

        if (chosenNeighbour == -1) return;

        opinions.put(agent, opinions.get(chosenNeighbour));
    }

    private int findNeighbour(int agent) {
        ArrayList<DefaultEdge> neighbors = new ArrayList<>(network.outgoingEdgesOf(agent));

        if (neighbors.isEmpty()) {
            return -1;
        }

        DefaultEdge chosenRelation = neighbors.get(getRandomAgentFromSize(neighbors.size()));
        return network.getEdgeTarget(chosenRelation);
    }
}
