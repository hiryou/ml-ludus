package ml.perceptron.perceptron;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by longuy on 3/10/2017.
 */
@NotThreadSafe
@RequiredArgsConstructor
class Neuron extends GenericNeuron<NumberVector> {

    private final TrainablePerceptron parentPerceptron;

    @Getter(AccessLevel.PACKAGE)
    @Setter(AccessLevel.PACKAGE)
    private NumberVector weightVector;

    @Override
    public boolean isFired(NumberVector tVector) {
        double y = weightVector.dotProductWith(
                tVector.withBias(parentPerceptron.getBiasInput()))
                .doubleValue();
        return y > 0;
    }

    void refineWeightVector(Number target, Number actual, NumberVector xVector, double learningRate) {
        NumberVector xVectorWBias = xVector.withBias(parentPerceptron.getBiasInput());
        List<Number> newWeights = new ArrayList<>();

        for (int j=0; j<weightVector.size(); j++) {
            Number wj = weightVector.get(j).doubleValue()
                    + learningRate * (target.doubleValue() - actual.doubleValue())*xVectorWBias.get(j).doubleValue();
            newWeights.add(wj);
        }

        setWeightVector(new NumberVector(newWeights));
    }
}
