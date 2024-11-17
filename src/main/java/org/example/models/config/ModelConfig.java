package org.example.models.config;

import lombok.*;
import org.example.models.enums.AgentSelection;
import org.example.models.enums.ModelType;
import org.example.models.enums.UpdatingStrategy;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ModelConfig {
    ModelType type = ModelType.VOTER;
    SimpleGraph<Integer, DefaultEdge> network;
    Map<Integer,Boolean> opinions;
    AgentSelection agentSelection;
    UpdatingStrategy updatingStrategy = UpdatingStrategy.N_TIMES;
}
