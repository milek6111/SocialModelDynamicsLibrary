package models.algorithms;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

final public class MajorityModel extends BaseModel {
    private final double PERCENTAGE = 0.3;
    @Override
    public void updateOpinions() {
        int r = (int) Math.floor(PERCENTAGE * N);
        ArrayList<Integer> vertices = new ArrayList<>(opinions.keySet());
        Collections.shuffle(vertices);
        ArrayList<Integer> chosenGroup = (ArrayList<Integer>) vertices.subList(0,r);



    }
}
