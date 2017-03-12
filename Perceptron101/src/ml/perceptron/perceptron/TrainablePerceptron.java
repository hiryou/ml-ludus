package ml.perceptron.perceptron;

import javafx.util.Pair;
import org.apache.commons.lang.Validate;

import java.util.ArrayList;
import java.util.List;

// package private
public abstract class TrainablePerceptron<TVector extends IFeatureVector, TAnswer> extends GenericPerceptron<TVector, TAnswer> {

    static final int BIAS_INPUT = -1;

    protected List<Pair<TVector, TAnswer>> knowledge = new ArrayList<>();

    void acquireKnowledge(List<Pair<TVector, TAnswer>> knowledge) {
        Validate.isTrue(!knowledge.isEmpty());
        if (this.knowledge.isEmpty()) {
            initializeWeightsAllNeurons(1 + knowledge.get(0).getKey().getDimension());
        }
        addKnowledgeData(knowledge);
    }

    Number getBiasInput() {
        return BIAS_INPUT;
    }

    /**
     * Run through all knowledge vectors, refine all neurons once per vector
     * @param learningRate
     */
    abstract void gradientDescentNeuronsPerInputVector(double learningRate);

    protected abstract void initializeWeightsAllNeurons(int size);

    private void addKnowledgeData(List<Pair<TVector, TAnswer>> data) {
        for (Pair<TVector, TAnswer> pair: data) {
            addKnowledgeDatum(pair.getKey(), pair.getValue());
        }
    }

    private void addKnowledgeDatum(TVector tVector, TAnswer tAnswer) {
        knowledge.add(new Pair<>(tVector, tAnswer));
    }
}
