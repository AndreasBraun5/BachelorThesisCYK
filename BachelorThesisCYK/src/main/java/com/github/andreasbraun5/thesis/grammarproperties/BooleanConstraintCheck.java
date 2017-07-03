package com.github.andreasbraun5.thesis.grammarproperties;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by AndreasBraun on 02.07.2017.
 */
public enum BooleanConstraintCheck {
    TEST("testkey", true) {
        @Override
        protected boolean check(GrammarWordMatrixWrapper wrapper,
                                GrammarConstraintValues restrictions) {
            int maxNumberOfVarsPerCell = restrictions.getMaxNumberOfVarsPerCell();
            boolean ret = true;
            return ret;
        }
    },
    CHECK2("check2", false) {
        @Override
        protected boolean check(GrammarWordMatrixWrapper wrapper, GrammarConstraintValues restrictions) {
            return false;
        }
    };

    BooleanConstraintCheck(String key, boolean examConstraint) {
        this.key = key;
        this.examConstraint = examConstraint;
    }

    private final String key;
    private boolean examConstraint;

    protected abstract boolean check(GrammarWordMatrixWrapper wrapper,
                                     GrammarConstraintValues restrictions);

    public final void check(GrammarWordMatrixWrapper wrapper,
                            GrammarConstraintValues restrictions,
                            Map<String, Boolean> results) {
        boolean result = this.check(wrapper, restrictions);
        results.put(this.key, result);
    }

    public static List<BooleanConstraintCheck> getAllConstraints(boolean includeExamConstraints) {
        return Arrays.stream(BooleanConstraintCheck.values())
                .filter(cons -> includeExamConstraints || !cons.examConstraint)
                .collect(Collectors.toList());
    }

}
