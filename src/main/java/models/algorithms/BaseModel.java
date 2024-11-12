package models.algorithms;

import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

import java.util.Map;
import java.util.Random;

@Setter
@Getter
abstract sealed public class BaseModel permits MajorityModel, VoterModel, SznajdModel {
    protected Graph<Integer, DefaultEdge> network;
    protected Map<Integer,Boolean> opinions;
    protected int N;
    protected int iterations;
    protected Random randomGenerator =  new Random();


    public abstract void updateOpinions();
}
