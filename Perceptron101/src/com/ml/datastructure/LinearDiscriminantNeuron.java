package com.ml.datastructure;

import javafx.util.Pair;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by longuy on 3/10/2017.
 */
@NotThreadSafe
@RequiredArgsConstructor
public class LinearDiscriminantNeuron<TVector extends NumberVector> implements ILinearDiscriminantNeuron<TVector> {

    private static final int BIAS_INPUT = -1;

    private List<Pair<NumberVector, Boolean>> knowledge = new ArrayList<>();
    private ILinearModel model = new LinearModel(TrainingSession.randomWeights(1));

    private volatile boolean isInTraining = false;

    @Override
    public LinearDiscriminantNeuron<TVector> resetIntelligence() {
        this.knowledge.clear();
        this.model = new LinearModel(TrainingSession.randomWeights(1));
        return this;
    }

    @Override
    public ILinearDiscriminantNeuron<TVector> addKnowledgeData(List<Pair<TVector, Boolean>> data) {
        for (Pair<TVector, Boolean> pair: data) {
            addKnowledgeDatum(pair.getKey(), pair.getValue());
        }
        return this;
    }

    @Override
    public LinearDiscriminantNeuron<TVector> addKnowledgeDatum(TVector tVector, boolean target) {
        knowledge.add(new Pair<NumberVector, Boolean>(tVector.withBias(BIAS_INPUT), target));
        return this;
    }

    @Override
    public TrainingSession newTrainingSession() {
        if (!isInTraining) synchronized (this) {
            if (!isInTraining) {
                isInTraining = true;
                return new TrainingSession(this);
            }
        }

        throw new RuntimeException("Neuron is in training. Only 1 training session is supported at anytime.");
    }

    @Override
    public boolean classify(TVector tVector) {
        double y = model.getWeightVector().dotProductWith(
                tVector.withBias(BIAS_INPUT))
                .doubleValue();
        return y > 0;
    }

    private void acquireNewModel(ILinearModel newModel) {
        model = newModel;
    }

    private void finishTraining() {
        isInTraining = false;
    }

    @RequiredArgsConstructor
    static class TrainingSession implements ILinearDiscriminantNeuron.ITrainingSession {

        private final LinearDiscriminantNeuron learningNeuron;

        @Setter
        @Accessors(fluent = true)
        private int iterationCount = 16;
        @Setter
        @Accessors(fluent = true)
        private double learningRate = 0.05;

        private volatile boolean isTrainingDone = false;

        @Override
        public void train() {
            if (!isTrainingDone) synchronized (this) {
                if (!isTrainingDone) {
                    List<Pair<NumberVector, Boolean>> data = learningNeuron.knowledge;

                    int dimension = (data.isEmpty()) ?0 : data.get(0).getKey().size();
                    NumberVector wVector = TrainingSession.randomWeights(dimension);

                    for (int t=0; t<iterationCount; t++) {
                        wVector = updateWeightsPerInputVector(wVector);
                    }

                    learningNeuron.acquireNewModel(new LinearModel(wVector));
                    this.finish();
                    return;
                }
            }

            throw new RuntimeException(
                    "This training session is finished. Create a new training session on the neuron for training.");
        }

        private void finish() {
            isTrainingDone = true;
            learningNeuron.finishTraining();
        }

        private NumberVector updateWeightsPerInputVector(final NumberVector wVector) {
            List<Pair<NumberVector, Boolean>> data = learningNeuron.knowledge;
            NumberVector currentWeights = wVector;

            for (Pair<NumberVector, Boolean> d: data) {
                NumberVector xVector = d.getKey();
                int target = d.getValue() ?1 :0;
                int y =  (currentWeights.dotProductWith(xVector).doubleValue() > 0) ?1 :0;

                if (y != target) {
                    currentWeights = updateWeights(currentWeights, target, y, xVector);
                }
            }

            return currentWeights;
        }

        NumberVector updateWeights(NumberVector weights, int target, int y, NumberVector xVector) {
            List<Number> newWeights = new ArrayList<>();
            for (int j=0; j<weights.size(); j++) {
                Number wj = weights.get(j).doubleValue() + learningRate*(target - y)*xVector.get(j).doubleValue();
                newWeights.add(wj);
            }
            return new NumberVector(newWeights);
        }

        private static NumberVector randomWeights(int size) {
            final List<Number> result = new ArrayList<>();
            Random gen = new Random();
            for (int i=0; i<size; i++) {
                result.add(gen.nextDouble() - 0.5);
            }
            return new NumberVector(result);
        }
    }
}
