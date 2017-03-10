package com.ml.learning;

import com.ml.datastructure.ILinearDiscriminantLearner;
import com.ml.datastructure.ILinearModel;
import com.ml.datastructure.NeuronLinearDiscriminantLearner;
import com.ml.datastructure.NumberVector;

import java.util.Arrays;

/**
 * Created by longuy on 3/9/2017.
 */
public class OrFunction {

    /**
     * Train a 1-single neuron network to solve OR function
     * @param args
     */
    public static void main(String[] args) {
        ILinearDiscriminantLearner learner = new NeuronLinearDiscriminantLearner(10, 0.02d);
        learner.resetData();
        learner.addDataPoint(new FeatureVector(0, 0), false);
        learner.addDataPoint(new FeatureVector(0, 1), true);
        learner.addDataPoint(new FeatureVector(1, 0), true);
        learner.addDataPoint(new FeatureVector(1, 1), true);

        ILinearModel model = learner.train();
        System.out.println(toString(0, 0, model));
        System.out.println(toString(0, 1, model));
        System.out.println(toString(1, 0, model));
        System.out.println(toString(1, 1, model));
    }

    private static String toString(int x1, int x2, ILinearModel model) {
        return String.format("%d or %d = %d", x1, x2, model.classify(new FeatureVector(0, 0)));
    }

    public static class FeatureVector extends NumberVector {

        public FeatureVector(int x1, int x2) {
            super(Arrays.asList(x1, x2));
        }
    }
}
