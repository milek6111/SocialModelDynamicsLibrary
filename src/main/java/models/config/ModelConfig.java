package models.config;

import lombok.*;
import models.algorithms.BaseModel;
import models.enums.AgentSelection;
import models.enums.ModelType;
import models.enums.UpdatingStrategy;
import org.jgrapht.Graph;
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
