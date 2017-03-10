package com.ml.datastructure;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by longuy on 3/10/2017.
 */
public class NeuronLinearDiscriminantLearnerTest {

    @Test
    public void updateWeightsTest() {
        NeuronLinearDiscriminantLearner learner = new NeuronLinearDiscriminantLearner(10, 0.25);

        NumberVector wv0 = numberVector(-0.05d, -0.02d, 0.02d);
        NumberVector wv1 = learner.updateWeights(wv0, 0, 1, numberVector(-1, 0, 0));
        Assert.assertTrue(approxEquals(numberVector(0.2, -0.02, 0.02), wv1, 0.001));

        NumberVector wv2 = learner.updateWeights(wv1, 1, 0, numberVector(-1, 0, 1));
        Assert.assertTrue(approxEquals(numberVector(-0.05, -0.02, 0.27), wv2, 0.001));
    }

    private static boolean approxEquals(NumberVector expected, NumberVector actual, double error) {
        Assert.assertTrue(expected.size() == actual.size());
        for (int j=0; j<expected.size(); j++) {
            Assert.assertTrue(
                    Math.abs(expected.get(j).doubleValue() - actual.get(j).doubleValue()) <= Math.abs(error) );
        }
        return true;
    }

    private static NumberVector numberVector(double d0, double d1, double d2) {
        return new NumberVector(Arrays.asList(d0, d1, d2));
    }
}
