package ml.perceptron.perceptron;

/**
 * Created by longuy on 3/12/2017.
 */
public interface ITraining {

    /**
     * Train and update the associated perceptron's intelligence
     * @return true if training session finishes with success
     */
    boolean run();
}
