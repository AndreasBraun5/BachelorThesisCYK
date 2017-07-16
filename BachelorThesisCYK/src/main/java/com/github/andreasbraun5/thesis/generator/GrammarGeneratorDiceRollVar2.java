package com.github.andreasbraun5.thesis.generator;

import com.github.andreasbraun5.thesis.grammar.Grammar;
import com.github.andreasbraun5.thesis.grammar.Terminal;
import com.github.andreasbraun5.thesis.grammar.Variable;
import com.github.andreasbraun5.thesis.grammar.VariableCompound;
import com.github.andreasbraun5.thesis.mylogger.WorkLog;
import com.github.andreasbraun5.thesis.parser.CYK;
import com.github.andreasbraun5.thesis.pyramid.CellK;
import com.github.andreasbraun5.thesis.pyramid.GrammarPyramidWrapper;
import com.github.andreasbraun5.thesis.pyramid.Pyramid;
import com.github.andreasbraun5.thesis.util.Tuple;

import java.util.*;

/**
 * Created by AndreasBraun on 16.07.2017.
 */
public class GrammarGeneratorDiceRollVar2 extends GrammarGenerator {


    private static final Random random = new Random();

    public GrammarGeneratorDiceRollVar2(GrammarGeneratorSettings grammarGeneratorSettings) {
        super("GrammarGeneratorDiceRollVar2", grammarGeneratorSettings);
    }

    @Override
    public GrammarPyramidWrapper generateGrammarPyramidWrapper(
            GrammarPyramidWrapper grammarPyramidWrapper, WorkLog workLog) {
        {   // Start of logging
            workLog.log("#########################################################################################################");
            workLog.log("START of Logging of GrammarGeneratorDiceRollVar2.");
            // Set the variableStart specifically because grammar and grammarProperties aren't interconnected.
            grammarPyramidWrapper.setGrammar(new Grammar(grammarGeneratorSettings.grammarProperties.variableStart));
            workLog.log("Used word:");
            workLog.log(grammarPyramidWrapper.getPyramid().getWord().toString());
        }
        Set<Terminal> terminals = grammarGeneratorSettings.grammarProperties.terminals;
        Set<Variable> variables = grammarGeneratorSettings.grammarProperties.variables;
        // Line 2
        Set<Tuple<VariableCompound, Integer>> rowSet = new HashSet<>();
        {   // Line 3: Distributing terminals
            grammarPyramidWrapper = A_DistributeTerminals.distributeTerminals(
                    new ArrayList<>(terminals),
                    grammarPyramidWrapper,
                    grammarGeneratorSettings,
                    new ArrayList<>(variables)
            );
            workLog.log("Grammar after distributing the terminals:");
            workLog.log(grammarPyramidWrapper.getGrammar().toString());
        }
        {
            // Line 4: Update pyramid
            grammarPyramidWrapper = CYK.calculateSetVAdvanced(grammarPyramidWrapper);
            workLog.log("Pyramid after distributing the terminals:");
            workLog.log(Pyramid.printPyramid(grammarPyramidWrapper.getPyramid().getCellsK()));
        }
        {   // Line 5 to 14: Iterating row wise over the pyramid.
            int i_max = grammarPyramidWrapper.getPyramid().getWord().getWordLength() - 1;
            for (int i = 1; i < i_max; i++) {
                {   // Line 6 + 7: Calculate the subset for each cell in the row i and add it to rowSet
                    for (int j = 0; j < grammarPyramidWrapper.getPyramid().getCellsK()[i].length; j++) {
                        Pyramid pyramid = grammarPyramidWrapper.getPyramid();
                        CellK cellK = pyramid.getCellK(i, j);
                        Set<VariableCompound> subSetForCell = GrammarGeneratorUtil.calculateSubSetForCell(cellK, pyramid);
                        for (VariableCompound varComp : subSetForCell) {
                            rowSet.add(new Tuple<>(varComp, i));
                        }
                    }
                    workLog.log("Calculated rowSet:");
                    workLog.log(rowSet.toString());
                }
                // Line 9
                // TODO: already distributed varComps might be distributed more often.
                while (!thresholdRowiReached(grammarPyramidWrapper, i)) {
                    // Line 10
                    Set<VariableCompound> varComp = new HashSet<>();
                    varComp.add(D_ChooseXYDependingOnIFromRowSet.chooseXYDependingOnIFromRowSet(rowSet));
                    {   // Line 11
                        grammarPyramidWrapper = B_DistributeVariables.distributeCompoundVariables(
                                new ArrayList<>(varComp),
                                grammarPyramidWrapper,
                                grammarGeneratorSettings,
                                new ArrayList<>(variables)
                        );
                        workLog.log("Chosen varComp:" + varComp);
                    }
                    // Line 12
                    grammarPyramidWrapper = CYK.calculateSetVAdvanced(grammarPyramidWrapper);
                    workLog.log("Pyramid after distributing the varComp");
                    workLog.log(grammarPyramidWrapper.getGrammar().toString());
                    workLog.log(grammarPyramidWrapper.getPyramid().toString());
                    {   // Line 13 + 14
                        if (C_StoppingCriteria.stoppingCriteriaMet(grammarPyramidWrapper)) {
                            // End of logging
                            workLog.log("Final grammar and pyramid");
                            workLog.log(grammarPyramidWrapper.getGrammar().toString());
                            workLog.log(grammarPyramidWrapper.getPyramid().toString());
                            workLog.log("END of Logging of GrammarGeneratorDiceRollVar2.");
                            workLog.log("#########################################################################################################");
                            // Line 14
                            return grammarPyramidWrapper;
                        }
                    }
                }

            }
        }
        {   // End of logging
            grammarPyramidWrapper = GrammarGeneratorUtil.onlyKeepContributingProductions(grammarPyramidWrapper);
            workLog.log("Final grammar and pyramid");
            workLog.log(grammarPyramidWrapper.getGrammar().toString());
            workLog.log(grammarPyramidWrapper.getPyramid().toString());
            workLog.log("END of Logging of GrammarGeneratorDiceRollVar2.");
            workLog.log("#########################################################################################################");
        }
        return grammarPyramidWrapper;
    }

    /**
     * At least less than half of the cells of one row must be not empty.
     */
    static boolean thresholdRowiReached(GrammarPyramidWrapper grammarPyramidWrapper, int rowI) {
        CellK[] cells = grammarPyramidWrapper.getPyramid().getCellsK()[rowI];
        int countCellsInRow = cells.length;
        int cellsFilled = 0;
        for (CellK cell : cells) {
            if (cell.getCellElements().size() > 0) {
                cellsFilled++;
            }
        }
        return ((double) (cellsFilled / countCellsInRow)) >= 0.5;
    }

}
