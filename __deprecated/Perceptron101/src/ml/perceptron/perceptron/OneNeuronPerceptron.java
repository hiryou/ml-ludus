package ml.perceptron.perceptron;

import javafx.util.Pair;

import javax.annotation.concurrent.Immutable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A binary perceptron with 1 single neuron. Thus, it only gives binary answer
 */
@Immutable
public class OneNeuronPerceptron extends TrainablePerceptron<NumberVector, Boolean> {

    private final Neuron neuron = new Neuron(this);

    @Override
    public Boolean getAnswer(NumberVector tVector) {
        return neuron.isFired(tVector);
    }

    @Override
    protected void initializeWeightsAllNeurons(int size) {
        final List<Number> vector = new ArrayList<>();
        Random gen = new Random();
        for (int i=0; i<size; i++) {
            vector.add(gen.nextDouble() - 0.5);
        }

        neuron.setWeightVector(new NumberVector(vector));
    }

    /**
     * Run through all knowledge vectors, refine all neurons once per vector
     * @param learningRate
     */
    @Override
    void gradientDescentNeuronsPerInputVector(double learningRate) {
        for (Pair<NumberVector, Boolean> d: knowledge) {
            NumberVector xVector = d.getKey();
            int target = d.getValue() ?1 :0;
            int actual =  (neuron.isFired(xVector)) ?1 :0;

            if (actual != target) {
                neuron.refineWeightVector(target, actual, xVector, learningRate);
            }
        }
    }
}
