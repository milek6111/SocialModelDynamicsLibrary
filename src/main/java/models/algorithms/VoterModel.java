package models.algorithms;

import org.jgrapht.graph.DefaultEdge;

import java.util.ArrayList;

final public class VoterModel extends BaseModel {
    @Override
    public void updateOpinions() {
        int nextVertex;
        for (int i = 0; i < N; i++) {
            nextVertex = randomGenerator.nextInt(N);
            //map connections of chosen voter to list
            ArrayList<DefaultEdge> neighbors = new ArrayList<>(network.outgoingEdgesOf(nextVertex));

            if(neighbors.isEmpty()){
                continue;
            }
            //choose random connected agent
            DefaultEdge chosenRelation = neighbors.get(randomGenerator.nextInt(neighbors.size()));
            Integer chosenNeighbor = network.getEdgeTarget(chosenRelation);

            //copy voter's opinion from chosen agent
            opinions.put(nextVertex, opinions.get(chosenNeighbor));

        }
    }
}
