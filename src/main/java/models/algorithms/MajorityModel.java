package models.algorithms;

import lombok.Getter;
import lombok.Setter;
import models.enums.UpdatingStrategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
final public class MajorityModel extends BaseModel {
    private double percentage = 0.3;

    @Override
    public void updateOneTimeStampFurther() {
        if (updatingStrategy.equals(UpdatingStrategy.N_TIMES)) {
            for (int i = 0; i < N; i++) {
                updateOpinion();
            }
        } else {
            updateOpinion();
        }
    }

    @Override
    public void updateOpinion() {
        List<Integer> chosenGroup = getGroup();


        int majorityOpinion = checkOpinons(chosenGroup);

        if (majorityOpinion == 0) return;

        boolean majority = majorityOpinion > 0;

        unifyOpinions(chosenGroup, majority);

    }

    @Override
    public void updateOpinion(Integer agent) {
        updateOpinion();
    }

    private List<Integer> getGroup() {
        int r = (int) Math.floor(percentage * N);
        ArrayList<Integer> vertices = new ArrayList<>(opinions.keySet());
        Collections.shuffle(vertices);
        return vertices.subList(0, r);
    }

    private int checkOpinons(List<Integer> group) {
        int falseCount = 0;
        int trueCount = 0;
        for (int agent : group) {
            if (opinions.get(agent)) trueCount++;
            else falseCount++;
        }

        return trueCount - falseCount;
    }

    private void unifyOpinions(List<Integer> agents, boolean opinion) {
        for (int agent : agents) {
            opinions.put(agent, opinion);
        }
    }
}
