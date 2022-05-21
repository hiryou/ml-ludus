package ml.perceptron.perceptron;

import java.util.List;

// package private
abstract class GenericNeuron<TVector> {

    abstract boolean isFired(TVector tVector);
}
