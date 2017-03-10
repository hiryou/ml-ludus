package com.ml.datastructure;

/**
 * Created by longuy on 3/10/2017.
 */
public interface ILinearModel<TVector> {

    boolean classify(TVector tVector);
}
