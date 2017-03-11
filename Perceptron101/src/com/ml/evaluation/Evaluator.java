package com.ml.evaluation;

import com.ml.datastructure.ILinearDiscriminantNeuron;
import com.ml.datastructure.ILinearModel;
import com.ml.datastructure.NumberVector;
import javafx.util.Pair;

import java.util.List;

/**
 * Created by longuy on 3/10/2017.
 */
public class Evaluator {

    static public <TVector> double getAccuracyRatio(
            List<Pair<TVector, Boolean>> dataPoints, ILinearDiscriminantNeuron<TVector> neuron) {
        if (dataPoints.isEmpty()) return 1;

        int correct = 0;
        for (Pair<TVector, Boolean> d : dataPoints) {
            boolean target = d.getValue();
            boolean y = neuron.classify(d.getKey());
            if (target == y) correct++;
        }
        return correct/dataPoints.size();
    }
}
