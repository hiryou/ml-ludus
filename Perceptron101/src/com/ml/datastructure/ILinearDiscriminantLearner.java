package com.ml.datastructure;

import javafx.util.Pair;

import java.util.List;

/**
 * Created by longuy on 3/10/2017.
 */
public interface ILinearDiscriminantLearner<TVector> {

    void addDataPoint(TVector tVector, boolean target);

    void addDataPoints(List<Pair<TVector, Boolean>> dataPoints);

    void resetData();

    ILinearModel<TVector> train();
}
