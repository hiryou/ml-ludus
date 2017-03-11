package com.ml.datastructure;

import com.ml.learning.OrFunction;
import javafx.util.Pair;

import java.util.List;

/**
 * A neuron which can give binary answer to a predefined class of feature vector
 */
public interface ILinearDiscriminantNeuron<TVector> {

    ILinearDiscriminantNeuron<TVector> resetIntelligence();

    ILinearDiscriminantNeuron<TVector> addKnowledgeData(List<Pair<TVector, Boolean>> data);

    ILinearDiscriminantNeuron<TVector> addKnowledgeDatum(TVector tVector, boolean target);

    
    ITrainingSession newTrainingSession();

    boolean classify(TVector tVector);

    interface ITrainingSession {

        /**
         * Setter
         */
        ITrainingSession iterationCount(int value);

        /**
         * Setter
         */
        ITrainingSession learningRate(double value);

        /**
         * Train and update the associated neuron's linear model
         */
        void train();
    }
}
