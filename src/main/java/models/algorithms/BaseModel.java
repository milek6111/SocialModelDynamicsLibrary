package models.algorithms;

import lombok.Getter;
import lombok.Setter;
import models.enums.AgentSelection;
import models.enums.UpdatingStrategy;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Setter
@Getter
abstract sealed public class BaseModel permits MajorityModel, VoterModel, SznajdModel {
    protected Graph<Integer, DefaultEdge> network;
    protected Map<Integer, Boolean> opinions;
    protected int N;
    protected AgentSelection agentSelection;
    protected UpdatingStrategy updatingStrategy;
    private Random randomGenerator = new Random();


    public abstract void updateOneTimeStampFurther();

    public abstract void updateOpinion();

    public void updateOpinion(Integer agent) {
        updateOpinion();
    }

    protected int getRandomAgent() {
        return getRandomAgentFromSize(N);
    }

    protected int getRandomAgentFromSize(int size) {
        return randomGenerator.nextInt(size);
    }

    protected void randomizeOpinions(double trueCoefficient) {
        opinions = new HashMap<>();
        for (int i = 0; i < N; i++) {
            opinions.put(i, randomGenerator.nextDouble() < trueCoefficient);
        }
    }
}
