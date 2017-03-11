package com.ml.learning;

import com.ml.datastructure.ILinearDiscriminantNeuron;
import com.ml.datastructure.LinearDiscriminantNeuron;
import com.ml.datastructure.NumberVector;
import com.ml.evaluation.Evaluator;
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
                new Pair<FeatureVector, Boolean>(new FeatureVector(0, 0), false),
                new Pair<FeatureVector, Boolean>(new FeatureVector(0, 1), true),
                new Pair<FeatureVector, Boolean>(new FeatureVector(1, 0), true),
                new Pair<FeatureVector, Boolean>(new FeatureVector(1, 1), true)
        );

        ILinearDiscriminantNeuron<FeatureVector> simpleNeuron = new LinearDiscriminantNeuron<>();

        for (int t=0; t<8; t++) {
            ILinearDiscriminantNeuron.ITrainingSession trainingSession =
                    simpleNeuron.resetIntelligence().addKnowledgeData(data)
                            .newTrainingSession()
                            .iterationCount(25)
                            .learningRate(0.05);
            trainingSession.train();

            System.out.println(String.format("Accuracy = %f", Evaluator.getAccuracyRatio(data, simpleNeuron)));
        }
    }

    public static class FeatureVector extends NumberVector {

        FeatureVector(int x1, int x2) {
            super(Arrays.asList(x1, x2));
        }
    }
}
