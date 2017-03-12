package ml.perceptron.perceptron;

import javafx.util.Pair;
import lombok.Builder;
import lombok.experimental.Accessors;
import org.apache.commons.lang.Validate;

import javax.annotation.concurrent.Immutable;
import java.util.List;

/**
 * Created by longuy on 3/12/2017.
 */
@Builder
@Immutable
@Accessors(fluent = true)
public class BinaryClassificationTraining<TVector extends NumberVector> implements ITraining {

    private final TrainablePerceptron<TVector, Boolean> perceptron;

    private final int iterationCount;
    private final double learningRate;
    private final List<Pair<TVector, Boolean>> trainingKnowledge;

    @Override
    public boolean run() {
        try {
            Validate.isTrue(!trainingKnowledge.isEmpty());
            perceptron.acquireKnowledge(trainingKnowledge);

            for (int t=0; t<iterationCount; t++) {
                perceptron.gradientDescentNeuronsPerInputVector(learningRate);
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
