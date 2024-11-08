package models.config;

import lombok.Builder;
import models.algorithms.BaseModel;
import models.enums.ModelType;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

@Builder
public class ModelConfig {
    ModelType type;
    int iterations;
    Graph<Integer, DefaultEdge> network;
}
