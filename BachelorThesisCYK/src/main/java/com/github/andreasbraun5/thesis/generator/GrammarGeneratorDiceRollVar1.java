package com.github.andreasbraun5.thesis.generator;

import com.github.andreasbraun5.thesis.grammarproperties.GrammarWordMatrixWrapper;
import com.github.andreasbraun5.thesis.grammar.*;
import com.github.andreasbraun5.thesis.mylogger.WorkLog;

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
    public GrammarWordMatrixWrapper generateGrammarWordMatrixWrapper(
            GrammarWordMatrixWrapper grammarWordMatrixWrapper, WorkLog workLog) {
        workLog.log("#########################################################################################################");
        workLog.log("START of Logging of GrammarGeneratorDiceRollOnly.");
        // Set the variableStart specifically because grammar and grammarProperties aren't interconnected.
        grammarWordMatrixWrapper.setGrammar(new Grammar(grammarGeneratorSettings.grammarProperties.variableStart));
        workLog.log("Used word:");
        workLog.log(grammarWordMatrixWrapper.getWord().toString());
        grammarWordMatrixWrapper = GrammarGeneratorUtil.distributeTerminals(
                new ArrayList<>(grammarGeneratorSettings.grammarProperties.terminals),
                grammarWordMatrixWrapper,
                grammarGeneratorSettings,
                new ArrayList<>(grammarGeneratorSettings.grammarProperties.variables)
        );
        workLog.log("After distributing the terminals:");
        workLog.log(grammarWordMatrixWrapper.getGrammar().toString());
        int j_max = grammarWordMatrixWrapper.getWord().getWordLength();
        for (int i = 0; i < j_max; i++) {
            // start inclusive and end exclusive
            int[] intArray = IntStream.range(1, j_max + 1).toArray();
            Set<Integer> J = new HashSet<>();
            for (int integer : intArray) {
                J.add(integer);
            }
            // upper bound exclusive
            int j = random.nextInt(J.size() + 1);
            //Set<Variable> cellSet = CYK.calculateSubSetForCell(CYK.calculateSetVAdvanced(grammarWordMatrixWrapper), );


            J.remove(j);


            if (false) { // Insert stopping criteria here
                workLog.log("END of Logging of GrammarGeneratorDiceRollOnly.");
                workLog.log("#########################################################################################################");
                return grammarWordMatrixWrapper;
            }
        }
        workLog.log("END of Logging of GrammarGeneratorDiceRollOnly.");
        workLog.log("#########################################################################################################");
        return grammarWordMatrixWrapper;
    }
}