package org.example.models.factory;

import org.example.models.algorithms.BaseModel;
import org.example.models.algorithms.MajorityModel;
import org.example.models.algorithms.SznajdModel;
import org.example.models.algorithms.VoterModel;
import org.example.models.config.ModelConfig;
import org.example.models.enums.ModelType;

import java.util.EnumMap;

/**
 * Factory class for creating model instances based on the configuration.
 */
public class ModelFactory {

    /**
     * A map that associates each ModelType with its corresponding BaseModel instance.
     */
    public static EnumMap<ModelType, BaseModel> mapper = new EnumMap<>(ModelType.class);

    static {
        mapper.put(ModelType.VOTER, new VoterModel());
        mapper.put(ModelType.MAJORITY, new MajorityModel());
        mapper.put(ModelType.SZNAJD, new SznajdModel());
    }

    /**
     * Creates a model instance based on the provided configuration.
     *
     * @param config the configuration for the model
     * @return the created model instance
     * @throws IllegalArgumentException if no model is found for the specified type
     */
    public static BaseModel createModel(ModelConfig config) {
        if (mapper.containsKey(config.getType())) {
            BaseModel model = mapper.get(config.getType());
            model.setAgentSelection(config.getAgentSelection());
            model.setUpdatingStrategy(config.getUpdatingStrategy());
            model.setNetwork(config.getNetwork());
            model.setOpinions(config.getOpinions());
            model.setN(config.getNetwork().vertexSet().size());
            return model;
        } else {
            throw new IllegalArgumentException("No model found for: " + config.getType());
        }
    }
}