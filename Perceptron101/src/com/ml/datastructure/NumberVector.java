package com.ml.datastructure;

import com.google.common.collect.ForwardingList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.Validate;

import javax.annotation.concurrent.Immutable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by longuy on 3/10/2017.
 */
@Immutable
@RequiredArgsConstructor
public class NumberVector extends ForwardingList<Number> {

    private final List<Number> numberVector;

    @Override
    protected List<Number> delegate() {

        return numberVector;
    }

    Number dotProductWith(NumberVector vector) {
        Validate.isTrue(this.size() == vector.size());
        double dot = 0;
        for (int i=0; i<this.size(); i++) {
            dot += numberVector.get(i).doubleValue() * vector.get(i).doubleValue();
        }
        return dot;
    }

    /**
     * Clone to a new vector with 1 additional bias dimension
     * @param bias
     * @return
     */
    NumberVector withBias(Number bias) {
        return new NumberVector(Lists.newArrayList(
                Iterables.concat(
                        Arrays.asList(bias), new ArrayList(this.numberVector)
                )
        ));
    }
}
