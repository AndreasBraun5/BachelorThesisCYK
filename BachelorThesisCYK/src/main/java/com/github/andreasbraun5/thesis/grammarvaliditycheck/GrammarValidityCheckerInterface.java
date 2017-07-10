package com.github.andreasbraun5.thesis.grammarvaliditycheck;

import com.github.andreasbraun5.thesis.grammar.Grammar;
import com.github.andreasbraun5.thesis.grammarproperties.GrammarProperties;
import com.github.andreasbraun5.thesis.grammarvalididtychecker.CheckMaxNumberOfVarsPerCellResultWrapper;
import com.github.andreasbraun5.thesis.grammarvalididtychecker.CheckMaxSumOfVarsInPyramidResultWrapper;
import com.github.andreasbraun5.thesis.grammarvalididtychecker.CheckRightCellCombinationsForcedResultWrapper;
import com.github.andreasbraun5.thesis.grammarvalididtychecker.CheckSumOfProductionsResultWrapper;
import com.github.andreasbraun5.thesis.pyramid.Pyramid;

/**
 * Created by AndreasBraun on 04.07.2017.
 */
// TODO: Delete
public interface GrammarValidityCheckerInterface {

    public boolean checkProducibilityCYK(Pyramid pyramid,
                                         Grammar grammar,
                                         GrammarProperties grammarProperties);

    public CheckMaxNumberOfVarsPerCellResultWrapper checkMaxNumberOfVarsPerCell(
            Pyramid pyramid,
            int maxNumberOfVarsPerCell);

    public CheckRightCellCombinationsForcedResultWrapper checkRightCellCombinationForced(
            Pyramid pyramid, int minCountRightCellCombinationsForced, Grammar grammar);

    public CheckSumOfProductionsResultWrapper checkSumOfProductions(Grammar grammar, int maxSumOfProductions);

    public CheckMaxSumOfVarsInPyramidResultWrapper checkMaxSumOfVarsInPyramid(
            Pyramid pyramid,
            int maxSumOfVarsInPyramid);
}
