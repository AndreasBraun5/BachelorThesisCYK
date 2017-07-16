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

import java.util.*;
import java.util.stream.IntStream;

/**
 * Created by AndreasBraun on 05.06.2017.
 */
public class GrammarGeneratorDiceRollVar1 extends GrammarGenerator {

    private static final Random random = new Random();

    public GrammarGeneratorDiceRollVar1(GrammarGeneratorSettings grammarGeneratorSettings) {
        super("GrammarGeneratorDiceRollVar1", grammarGeneratorSettings);
    }

    @Override
    public GrammarPyramidWrapper generateGrammarPyramidWrapper(
            GrammarPyramidWrapper grammarPyramidWrapper, WorkLog workLog) {
        {   // Start of logging
            workLog.log("#########################################################################################################");
            workLog.log("START of Logging of GrammarGeneratorDiceRollVar1.");
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
        {
            // Line 3: Update pyramid
            grammarPyramidWrapper = CYK.calculateSetVAdvanced(grammarPyramidWrapper);
            workLog.log("Pyramid after distributing the terminals:");
            workLog.log(Pyramid.printPyramid(grammarPyramidWrapper.getPyramid().getCellsK()));
        }
        {
            // Line 4 to 14: Iterating row wise over the pyramid. Each cell of one row must be visited at least once.
            int i_max = grammarPyramidWrapper.getPyramid().getWord().getWordLength() - 1;
            // Line 4:
            for (int i = 0; i < i_max; i++) {
                // Line 5
                List<Integer> setJ = new ArrayList<>();
                {   // Fill setJ with values regarding the current i
                    int[] intArray = IntStream.range(0, grammarPyramidWrapper.getPyramid().getCellsK()[i].length - 1).toArray();
                    for (int iTemp : intArray) {
                        setJ.add(iTemp);
                    }
                }
                // Line 6
                Set<VariableCompound> cellSet;
                // Line 7
                while (setJ.size() > 0) {
                    // Line 8
                    int randomElementIndex = random.nextInt(setJ.size());
                    int j = setJ.get(randomElementIndex); // upper bound exclusive
                    // Line 9
                    setJ.remove(randomElementIndex);
                    // Line 10
                    Pyramid pyramid = grammarPyramidWrapper.getPyramid();
                    workLog.log("Working on cell with indexes:" + i + j);
                    CellK cellK = pyramid.getCellK(i, j);
                    cellSet = GrammarGeneratorUtil.calculateSubSetForCell(cellK, pyramid);
                    workLog.log("SubsetOfCell:");
                    workLog.log(cellSet.toString());
                    // Line 11
                    grammarPyramidWrapper = B_DistributeVariables.distributeCompoundVariables(
                            new ArrayList<>(cellSet),
                            grammarPyramidWrapper,
                            grammarGeneratorSettings,
                            new ArrayList<>(variables)
                    );
                    workLog.log("Grammar after distributing all possible compound variables:");
                    workLog.log(grammarPyramidWrapper.getGrammar().toString());
                    // Line 12
                    grammarPyramidWrapper = CYK.calculateSetVAdvanced(grammarPyramidWrapper);
                    workLog.log("Pyramid after distributing all possible compound variables:");
                    workLog.log(grammarPyramidWrapper.getGrammar().toString());
                    // Line 13
                    if (C_StoppingCriteria.stoppingCriteriaMet(grammarPyramidWrapper)) {
                        // End of logging
                        workLog.log("Final cell worked with:" + i + j);
                        workLog.log("Final grammar and pyramid");
                        workLog.log(grammarPyramidWrapper.getGrammar().toString());
                        workLog.log(grammarPyramidWrapper.getPyramid().toString());
                        workLog.log("END of Logging of GrammarGeneratorDiceRollVar1.");
                        workLog.log("#########################################################################################################");
                        // Line 14
                        return grammarPyramidWrapper;
                    }
                }
            }
        }
        {   // End of logging
            workLog.log("Worked with all cells");
            workLog.log("Final grammar and pyramid");
            workLog.log(grammarPyramidWrapper.getGrammar().toString());
            workLog.log(grammarPyramidWrapper.getPyramid().toString());
            workLog.log("END of Logging of GrammarGeneratorDiceRollVar1.");
            workLog.log("#########################################################################################################");
        }
        return grammarPyramidWrapper;
    }
}
