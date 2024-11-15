package models.factory;

import lombok.Builder;
import models.algorithms.BaseModel;
import models.algorithms.MajorityModel;
import models.algorithms.SznajdModel;
import models.algorithms.VoterModel;
import models.config.ModelConfig;
import models.enums.ModelType;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

import java.util.EnumMap;
import java.util.Map;


public class ModelFactory {

    public static EnumMap<ModelType, BaseModel> mapper= new EnumMap<>(ModelType.class);

    static {
        mapper.put(ModelType.VOTER, new VoterModel());
        mapper.put(ModelType.MAJORITY, new MajorityModel());
        mapper.put(ModelType.SZNAJD, new SznajdModel());
    }

    public static BaseModel createModel(ModelConfig config){
        if(mapper.containsKey(config.getType())){
            BaseModel model = mapper.get(config.getType());
            model.setAgentSelection(config.getAgentSelection());
            model.setUpdatingStrategy(config.getUpdatingStrategy());
            model.setNetwork(config.getNetwork());
            model.setOpinions(config.getOpinions());
            model.setN(config.getNetwork().vertexSet().size());
            return model;
        }
        else
            throw new IllegalArgumentException("No model found for: " + config.getType());
    }
}
