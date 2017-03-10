package com.ml.evaluation;

import com.ml.datastructure.ILinearModel;
import com.ml.datastructure.NumberVector;
import javafx.util.Pair;

import java.util.List;

/**
 * Created by longuy on 3/10/2017.
 */
public class Evaluator<TVector extends NumberVector> implements IEvaluator<TVector> {
    @Override
    public double getAccuracyRatio(List<Pair<TVector, Boolean>> dataPoints, ILinearModel<TVector> model) {
        if (dataPoints.isEmpty()) return 1;

        int correct = 0;
        for (Pair<TVector, Boolean> d : dataPoints) {
            boolean target = d.getValue();
            boolean y = model.classify(d.getKey());
            if (target == y) correct++;
        }
        return correct/dataPoints.size();
    }
}
