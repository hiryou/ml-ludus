package com.ml.datastructure;

/**
 * Created by longuy on 3/10/2017.
 */
public interface ILinearDiscriminantLearner<TVector> {

    void addDataPoint(TVector tVector, boolean target);

    void resetData();

    ILinearModel<TVector> train();
}
