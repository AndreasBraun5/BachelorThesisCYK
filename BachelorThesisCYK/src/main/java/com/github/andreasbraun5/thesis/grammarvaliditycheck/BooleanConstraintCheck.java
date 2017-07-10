package com.github.andreasbraun5.thesis.grammarvaliditycheck;

import com.github.andreasbraun5.thesis.grammarvalididtychecker.ValidityResult;
import com.github.andreasbraun5.thesis.pyramid.GrammarPyramidWrapper;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by AndreasBraun on 02.07.2017.
 */
public enum BooleanConstraintCheck {
    TEST(true) {
        @Override
        public void check(GrammarPyramidWrapper grammarPyramidWrapper,
                                GrammarConstraintValues restrictions,
                                ValidityResult validityResult) {
            int maxNumberOfVarsPerCell = restrictions.getMaxNumberOfVarsPerCell();
            boolean ret = true;
        }
    },
    CHECK2(false) {
        @Override
        public void check(GrammarPyramidWrapper grammarPyramidWrapper,
                                GrammarConstraintValues restrictions,
                                ValidityResult validityResult) {

        }
    };

    BooleanConstraintCheck(boolean examConstraint) {
        this.examConstraint = examConstraint;
    }

    private boolean examConstraint;

    public abstract void check(GrammarPyramidWrapper grammarPyramidWrapper,
                                  GrammarConstraintValues restrictions,
                                  ValidityResult validityResult);

    public static List<BooleanConstraintCheck> getAllConstraints(boolean includeExamConstraints) {
        return Arrays.stream(BooleanConstraintCheck.values())
                .filter(cons -> includeExamConstraints || !cons.examConstraint)
                .collect(Collectors.toList());
    }

}
