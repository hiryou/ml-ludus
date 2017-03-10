package com.ml.evaluation;

import com.ml.datastructure.ILinearModel;
import javafx.util.Pair;

import java.util.List;

/**
 * Created by longuy on 3/10/2017.
 */
public interface IEvaluator<TVector> {

    double getAccuracyRatio(List<Pair<TVector, Boolean>> dataPoints, ILinearModel<TVector> model);
}
