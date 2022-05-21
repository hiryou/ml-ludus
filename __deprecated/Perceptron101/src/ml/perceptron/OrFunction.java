package ml.perceptron;

import ml.perceptron.perceptron.*;
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
                new Pair<>(new FeatureVector(0, 0), false),
                new Pair<>(new FeatureVector(0, 1), true),
                new Pair<>(new FeatureVector(1, 0), true),
                new Pair<>(new FeatureVector(1, 1), true)
        );

        TrainablePerceptron<FeatureVector, Boolean> perceptron = new OneNeuronPerceptron();

        ITraining binaryTraining = BinaryClassificationTraining.<FeatureVector>builder()
                .perceptron(perceptron)
                .iterationCount(25)
                .learningRate(0.05)
                .trainingKnowledge(data)
                .build();
        binaryTraining.run();

        System.out.println(String.format("Accuracy = %f", Evaluator.getAccuracyRatio(data, perceptron)));
    }

    public static class FeatureVector extends NumberVector {

        FeatureVector(int x1, int x2) {
            super(Arrays.asList(x1, x2));
        }
    }
}
