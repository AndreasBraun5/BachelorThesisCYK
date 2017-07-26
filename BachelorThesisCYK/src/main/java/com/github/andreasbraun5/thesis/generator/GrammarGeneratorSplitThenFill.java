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
     * Corresponding to algorithm SplitThenFillPrep that calls the recursive algorithm SplitThenFill
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
            int imax = grammarPyramidWrapper.getPyramid().getSize() - 1;
            sol = splitThenFill(grammarPyramidWrapper, imax, 0, workLog);
        }
        {
            workLog.log("Final grammar and pyramid:");
            workLog.log(sol.x.getGrammar().toString());
            workLog.log(sol.x.getPyramid().getWord().toString());
            workLog.log(sol.x.getPyramid().toString());
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
        // Line 4:
        int m = random.nextInt(i) + j + 1; // ( j + i + 1 - j - 1) || + j + 1
        // Line 5 + 6:
        Tuple<GrammarPyramidWrapper, CellK> left = splitThenFill(grammarPyramidWrapper, (m - j - 1), j, workLog);
        Tuple<GrammarPyramidWrapper, CellK> right = splitThenFill(grammarPyramidWrapper, (j + i - m), m, workLog);
        {   // Line 7:
            grammarPyramidWrapper = CYK.calculateSetVAdvanced(grammarPyramidWrapper);
        }
        {   // Line 8 to 10
            if (C_StoppingCriteria.stoppingCriteriaMetSplitThenFill(grammarPyramidWrapper)) {
                return new Tuple<>(grammarPyramidWrapper, cell_ij);
            }
        }
        {   // Line 11 to 14:
            if (cell_ij.getCellElements().size() == 0) {
                // Line 12:
                CellK cellLeft = grammarPyramidWrapper.getPyramid().getCellK(left.y.getI(), left.y.getJ());
                CellK cellRight = grammarPyramidWrapper.getPyramid().getCellK(right.y.getI(), right.y.getJ());
                workLog.log("Cell left: " + cellLeft.getCellElements());
                workLog.log("Cell right: " + cellRight.getCellElements());
                Set<VariableCompound> VC = Util.uniformRandomSubset(
                        Util.calculateVariablesCompoundForCellPair(Tuple.of(cellLeft, cellRight)),
                        grammarGeneratorSettings.getMinValueCompoundVariablesAreAddedTo()
                );
                workLog.log("VC: " + VC);
                // Line 13:
                workLog.log("Grammar before distributing: " + grammarPyramidWrapper.getGrammar());
                {
                    workLog.log("pyramid before line 13:");
                    workLog.log(grammarPyramidWrapper.getPyramid().toString());
                }
                grammarPyramidWrapper = B_DistributeVariables.distributeCompoundVariables(
                        new ArrayList<>(VC),
                        grammarPyramidWrapper,
                        grammarGeneratorSettings,
                        new ArrayList<>(grammarGeneratorSettings.grammarProperties.variables)
                );
                workLog.log("Grammar after distributing: " + grammarPyramidWrapper.getGrammar());
                {
                    workLog.log("pyramid after line 13:");
                    workLog.log(grammarPyramidWrapper.getPyramid().toString());
                }
            }
        }
        return new Tuple<>(grammarPyramidWrapper, cell_ij);
    }
}
