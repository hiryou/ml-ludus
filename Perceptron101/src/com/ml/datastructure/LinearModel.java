package com.ml.datastructure;

import lombok.Data;
import lombok.Getter;

import javax.annotation.concurrent.Immutable;

/**
 * Created by longuy on 3/10/2017.
 */
@Data
@Immutable
public class LinearModel implements ILinearModel {

    @Getter
    private final NumberVector weightVector;
}
