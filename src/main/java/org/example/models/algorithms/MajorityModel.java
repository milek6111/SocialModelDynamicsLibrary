package org.example.models.algorithms;

import lombok.Getter;
import lombok.Setter;
import org.example.models.enums.UpdatingStrategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * MajorityModel class that extends the BaseModel.
 * This class implements the majority rule for opinion dynamics.
 */
@Getter
@Setter
final public class MajorityModel extends BaseModel {
    /**
     * The percentage of agents to consider in the majority rule.
     */
    private double percentage = 0.05;

    /**
     * Updates the model by one time step.
     * If the updating strategy is N_TIMES, it updates the opinions N times.
     * Otherwise, it updates the opinions once.
     */
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

    /**
     * Updates the opinions of agents based on the majority rule.
     * It selects a group of agents, checks the majority opinion,
     * and unifies the opinions of the group to the majority opinion.
     */
    @Override
    public void updateOpinion() {
        List<Integer> chosenGroup = getGroup();

        int majorityOpinion = checkOpinons(chosenGroup);

        if (majorityOpinion == 0) return;

        boolean majority = majorityOpinion > 0;

        unifyOpinions(chosenGroup, majority);
    }

    /**
     * Updates the opinion of a specific agent.
     * This method calls the updateOpinion method.
     *
     * @param agent the ID of the agent whose opinion is to be updated
     */
    @Override
    public void updateOpinion(Integer agent) {
        updateOpinion();
    }

    /**
     * Selects a group of agents based on the percentage.
     *
     * @return a list of agent IDs representing the selected group
     */
    private List<Integer> getGroup() {
        int r = (int) Math.floor(percentage * N);
        ArrayList<Integer> vertices = new ArrayList<>(opinions.keySet());
        Collections.shuffle(vertices);
        return vertices.subList(0, r);
    }

    /**
     * Checks the opinions of a group of agents and returns the majority opinion.
     *
     * @param group the list of agent IDs representing the group
     * @return the difference between the number of true and false opinions
     */
    private int checkOpinons(List<Integer> group) {
        int falseCount = 0;
        int trueCount = 0;
        for (int agent : group) {
            if (opinions.get(agent)) trueCount++;
            else falseCount++;
        }

        return trueCount - falseCount;
    }

    /**
     * Unifies the opinions of a group of agents to the specified opinion.
     *
     * @param agents the list of agent IDs representing the group
     * @param opinion the opinion to set for the group
     */
    private void unifyOpinions(List<Integer> agents, boolean opinion) {
        for (int agent : agents) {
            opinions.put(agent, opinion);
        }
    }

    /**
     * Sets the percentage coefficient for the MajorityModel.
     * The coefficient must be between 0 and 1.
     *
     * @param coeff the coefficient to set
     * @throws IllegalArgumentException if the coefficient is not between 0 and 1
     */
    @Override
    public void setMajorityModelPercentageCoeff(double coeff) {
        if(coeff <= 0 || coeff >= 1){
            throw new IllegalArgumentException("Majority Model coefficient must be between 0 and 1");
        }
        percentage = coeff;
    }
}