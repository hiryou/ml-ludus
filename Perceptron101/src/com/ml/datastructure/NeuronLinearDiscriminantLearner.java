package com.ml.datastructure;

import javafx.util.Pair;
import lombok.RequiredArgsConstructor;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by longuy on 3/10/2017.
 */
@NotThreadSafe
@RequiredArgsConstructor
public class NeuronLinearDiscriminantLearner<TVector extends NumberVector> implements ILinearDiscriminantLearner<TVector> {

    private static final int BIAS_INPUT = -1;

    private final int trainIterationCount;
    private final double learningRate;

    private List<Pair<NumberVector, Boolean>> data = new ArrayList<>();

    @Override
    public void addDataPoint(TVector tVector, boolean target) {
        data.add(new Pair<NumberVector, Boolean>(tVector.withBias(BIAS_INPUT), target));
    }

    @Override
    public void addDataPoints(List<Pair<TVector, Boolean>> dataPoints) {
        for (Pair<TVector, Boolean> pair: dataPoints) {
            addDataPoint(pair.getKey(), pair.getValue());
        }
    }

    @Override
    public void resetData() {
        this.data.clear();
    }

    @Override
    public ILinearModel<TVector> train() {
        int dimension = (data.isEmpty()) ?0 :data.get(0).getKey().size();
        NumberVector wVector = randomWeights(dimension);

        for (int t=0; t<trainIterationCount; t++) {
            wVector = updateWeightsPerInputVector(wVector);
        }

        return new LinearModel<TVector>(wVector, BIAS_INPUT);
    }

    private NumberVector updateWeightsPerInputVector(final NumberVector wVector) {
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
            Number wj = weights.get(j).doubleValue() + this.learningRate*(target - y)*xVector.get(j).doubleValue();
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
