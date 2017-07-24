package com.github.andreasbraun5.thesis.generator;

import com.github.andreasbraun5.thesis.grammar.Grammar;
import com.github.andreasbraun5.thesis.grammar.Terminal;
import com.github.andreasbraun5.thesis.grammar.Variable;
import com.github.andreasbraun5.thesis.grammar.VariableCompound;
import com.github.andreasbraun5.thesis.mylogger.WorkLog;
import com.github.andreasbraun5.thesis.parser.CYK;
import com.github.andreasbraun5.thesis.pyramid.CellK;
import com.github.andreasbraun5.thesis.pyramid.GrammarPyramidWrapper;
import com.github.andreasbraun5.thesis.util.Tuple;
import com.github.andreasbraun5.thesis.util.Util;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

/**
 * Created by AndreasBraun on 24.07.2017.
 */
public class GrammarGeneratorSplitThenFill extends GrammarGenerator {

    private static final Random random = new Random();

    public GrammarGeneratorSplitThenFill(GrammarGeneratorSettings grammarGeneratorSettings) {
        super("SPLITTHENFILL", grammarGeneratorSettings);
    }

    @Override
    /**
     * Corresponding to algorithm SplitzThenFillPrep that calls the recursive algorithm SplitThenFill
     */
    public GrammarPyramidWrapper generateGrammarPyramidWrapper(GrammarPyramidWrapper grammarPyramidWrapper, WorkLog workLog) {
        {   // Start of logging
            workLog.log("#########################################################################################################");
            workLog.log("START of Logging of GrammarGeneratorSplitThenFill.");
            // Set the variableStart specifically because grammar and grammarProperties aren't interconnected.
            grammarPyramidWrapper.setGrammar(new Grammar(grammarGeneratorSettings.grammarProperties.variableStart));
            workLog.log("Used word:");
            workLog.log(grammarPyramidWrapper.getPyramid().getWord().toString());
        }
        Set<Terminal> terminals = grammarGeneratorSettings.grammarProperties.terminals;
        Set<Variable> variables = grammarGeneratorSettings.grammarProperties.variables;
        {   // Line 2: Distributing terminals
            grammarPyramidWrapper = A_DistributeTerminals.distributeTerminals(
                    new ArrayList<>(terminals),
                    grammarPyramidWrapper,
                    grammarGeneratorSettings,
                    new ArrayList<>(variables)
            );
            workLog.log("Grammar after distributing the terminals:");
            workLog.log(grammarPyramidWrapper.getGrammar().toString());
        }
        grammarPyramidWrapper = CYK.calculateSetVAdvanced(grammarPyramidWrapper);
        // Line 3: Define the Sol tuple
        Tuple<GrammarPyramidWrapper, CellK> sol;
        {   // Line 4: recursive call of SplitThenFill
            int imax = grammarPyramidWrapper.getPyramid().getSize()-1;
            sol = splitThenFill(grammarPyramidWrapper, imax, 0, workLog);
        }
        return sol.x;
    }

    private Tuple<GrammarPyramidWrapper, CellK> splitThenFill(
            GrammarPyramidWrapper grammarPyramidWrapper,
            int i,
            int j,
            WorkLog workLog) {
        CellK cell_ij = grammarPyramidWrapper.getPyramid().getCellK(i, j);
        {   // Line 1 to 3
            if (i == 0) {
                return new Tuple<>(grammarPyramidWrapper, cell_ij);
            }
        }
        {   // Line 4 to 6
            if (C_StoppingCriteria.stoppingCriteriaMet(grammarPyramidWrapper)) {
                return new Tuple<>(grammarPyramidWrapper, cell_ij);
            }
        }
        // Line 7:
        int m = random.nextInt(j + i + 1) + j + 1;
        workLog.log("Chosen m: " + m);
        // Line 8 + 9:
        workLog.log("call for left with:" + (m - j - 1) + " and " + j);
        Tuple<GrammarPyramidWrapper, CellK> left = splitThenFill(grammarPyramidWrapper, (m - j - 1), j, workLog);
        workLog.log("call for right with:" + (j + i - m) + " and " + m);
        Tuple<GrammarPyramidWrapper, CellK> right = splitThenFill(grammarPyramidWrapper, (j + i - m), m, workLog);
        {   // Line 10:
            workLog.log("pyramid before line 10:");
            grammarPyramidWrapper = CYK.calculateSetVAdvanced(grammarPyramidWrapper);
            workLog.log("pyramid after line 10:");
        }
        {   // Line 11 to 14:
            if (cell_ij.getCellElements().size() == 0) {
                // Line 12:
                workLog.log("Cell left: " + left.y.getCellElements());
                workLog.log("Cell right: " + right.y.getCellElements());
                Set<VariableCompound> VC = Util.uniformRandomSubset(
                        Util.calculateVariablesCompoundForCellPair(Tuple.of(left.y, right.y))
                );
                workLog.log("VC: " + VC);
                // Line 13:
                workLog.log("Grammar before distributing: " + grammarPyramidWrapper.getGrammar());
                grammarPyramidWrapper = B_DistributeVariables.distributeCompoundVariables(
                        new ArrayList<>(VC),
                        grammarPyramidWrapper,
                        grammarGeneratorSettings,
                        new ArrayList<>(grammarGeneratorSettings.grammarProperties.variables)
                );
                workLog.log("Grammar after distributing: " + grammarPyramidWrapper.getGrammar());
            }
        }
        return new Tuple<>(grammarPyramidWrapper, cell_ij);
    }
}
