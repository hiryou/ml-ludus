package com.ml.datastructure;

import lombok.RequiredArgsConstructor;

/**
 * Created by longuy on 3/10/2017.
 */
@RequiredArgsConstructor
public class LinearModel<TVector extends NumberVector> implements ILinearModel<TVector> {

    private final NumberVector weights;
    private final int biasInput;

    @Override
    public boolean classify(TVector tVector) {
        double y = weights.dotProductWith(
                tVector.withBias(biasInput))
                .doubleValue();
        return y > 0;
    }
}
