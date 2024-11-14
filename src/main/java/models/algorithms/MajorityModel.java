package models.algorithms;

import java.util.ArrayList;
import java.util.Collections;

final public class MajorityModel extends BaseModel {
    private final double PERCENTAGE = 0.3;
    @Override
    public void updateOpinions() {
        int r = (int) Math.floor(PERCENTAGE * N);
        ArrayList<Integer> vertices = new ArrayList<>(opinions.keySet());

        for (int i = 0; i < N; i++) {
            Collections.shuffle(vertices);
            ArrayList<Integer> chosenGroup = (ArrayList<Integer>) vertices.subList(0,r);

            int majorityOpinion = checkOpinons(chosenGroup);

            if (majorityOpinion == 0) continue;

            boolean majority = majorityOpinion > 0;

            unifyOpinions(chosenGroup, majority);

        }


    }

    private int checkOpinons(ArrayList<Integer> group){
        int falseCount = 0;
        int trueCount = 0;
        for (int agent : group){
            if (opinions.get(agent)) trueCount++;
            else falseCount ++;
        }

        return trueCount - falseCount;
    }

    private void unifyOpinions(ArrayList<Integer> agents, boolean opinion){
        for (int agent : agents){
            opinions.put(agent, opinion);
        }
    }
}
