package ml.perceptron.perceptron;

/**
 * A perceptron consists of
 * - A network of neurons inter-connected
 * - Inputs of type {TVector, TAnswer} that it has seen, i.e. knowledge
 */
public abstract class GenericPerceptron<TVector extends IFeatureVector, TAnswer> {

    public abstract TAnswer getAnswer(TVector tVector);
}
