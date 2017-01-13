package com.github.andreasbraun5.thesis.util;

import com.github.andreasbraun5.thesis.grammar.RightHandSideElement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Andreas Braun on 05.01.2017.
 */
public class Util {
    /**
     *  Removing duplicates from a collection.
     */
    private static <T extends RightHandSideElement> List<T> withoutDuplicates(Collection<T> ruleElements) {
        List<T> ret = new ArrayList<>();
        ret.addAll(new HashSet<>(ruleElements));
        return ret;
    }
}
