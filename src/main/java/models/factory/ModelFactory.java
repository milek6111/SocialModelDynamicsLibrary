package models.factory;

import models.algorithms.BaseModel;
import models.algorithms.MajorityModel;
import models.algorithms.SznajdModel;
import models.algorithms.VoterModel;
import models.enums.ModelType;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

import java.util.EnumMap;

public class ModelFactory {

    public static EnumMap<ModelType, BaseModel> mapper= new EnumMap<>(ModelType.class);

    static {
        mapper.put(ModelType.VOTER, new VoterModel());
        mapper.put(ModelType.MAJORITY, new MajorityModel());
        mapper.put(ModelType.SZNAJD, new SznajdModel());
    }

    public static BaseModel createModel(ModelType type, Graph<Integer, DefaultEdge> network, int iterations){
        if(mapper.containsKey(type)){
            BaseModel model = mapper.get(type);
            model.setIterations(iterations);
            model.setNetwork(network);
            return model;
        }
        else
            throw new IllegalArgumentException("No model found for: " + type);
    }
}
