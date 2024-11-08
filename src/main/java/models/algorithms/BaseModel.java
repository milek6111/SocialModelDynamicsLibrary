package models.algorithms;

import lombok.Setter;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

@Setter
abstract sealed public class BaseModel permits MajorityModel, VoterModel, SznajdModel {
    Graph<Integer, DefaultEdge> network;
    int iterations;


    public abstract void updateOpinions();
}
