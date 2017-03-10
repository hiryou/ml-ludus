package com.ml.learning;

import com.ml.datastructure.ILinearDiscriminantLearner;
import com.ml.datastructure.ILinearModel;
import com.ml.datastructure.NeuronLinearDiscriminantLearner;
import com.ml.datastructure.NumberVector;
import com.ml.evaluation.Evaluator;
import com.ml.evaluation.IEvaluator;
import javafx.util.Pair;

import java.util.Arrays;
import java.util.List;

/**
 * Created by longuy on 3/9/2017.
 */
public class OrFunction {

    /**
     * Train a 1-single neuron network to solve OR function
     * @param args
     */
    public static void main(String[] args) {
        List<Pair<FeatureVector, Boolean>> data = Arrays.asList(
                new Pair(new FeatureVector(0, 0), false),
                new Pair(new FeatureVector(0, 1), true),
                new Pair(new FeatureVector(1, 0), true),
                new Pair(new FeatureVector(1, 1), true)
        );
        ILinearDiscriminantLearner learner = new NeuronLinearDiscriminantLearner(25, 0.05d);
        IEvaluator<FeatureVector> eval = new Evaluator();

        for (int t=0; t<8; t++) {
            learner.resetData();
            learner.addDataPoints(data);

            ILinearModel model = learner.train();
            System.out.println(String.format("Accuracy = %f", eval.getAccuracyRatio(data, model)));
        }
    }

    public static class FeatureVector extends NumberVector {

        public FeatureVector(int x1, int x2) {
            super(Arrays.asList(x1, x2));
        }
    }
}
