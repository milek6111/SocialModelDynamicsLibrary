package models.algorithms;

import org.jgrapht.graph.DefaultEdge;

import java.util.ArrayList;

final public class SznajdModel extends BaseModel {
    @Override
    public void updateOpinions() {

        for (int i = 0; i < N; i++) {
            int firstNeighbour = randomGenerator.nextInt(N);
            int secondNeighbour = findNeighbour(firstNeighbour);




        }
    }

    private int findNeighbour(int agent){

        ArrayList<DefaultEdge> defaultEdges = new ArrayList<DefaultEdge>(network.outgoingEdgesOf(agent));

        if(defaultEdges.isEmpty()) return -1;

        DefaultEdge edge = defaultEdges.get(randomGenerator.nextInt(defaultEdges.size()));

        return network.getEdgeTarget(edge);

    }
}
