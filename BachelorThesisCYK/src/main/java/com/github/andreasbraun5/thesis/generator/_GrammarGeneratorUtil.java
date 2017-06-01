package com.github.andreasbraun5.thesis.generator;

import com.github.andreasbraun5.thesis.grammar.*;

import java.util.*;

/**
 * Created by AndreasBraun on 01.06.2017.
 */
public class _GrammarGeneratorUtil {

    private static final Random random = new Random();

    /**
     * If you want to distribute the terminals to at least one rightHandSide then minCountElementDistributedTo is 1.
     * Dice roll for every rhse how often it is added and to which vars in the grammar it is added.
     * Equals the Distribute standard method.
     */
    private static GrammarWordWrapper distributeDiceRollRightHandSideElements(
            GrammarWordWrapper grammarWordWrapper,
            List<? extends RightHandSideElement> rightHandSideElements,
            int minCountElementDistributedTo,
            int maxCountElementDistributedTo,
            List<Variable> variablesWeighted) {
        Grammar grammar = grammarWordWrapper.getGrammar();
        for (RightHandSideElement tempRhse : rightHandSideElements) {
            // countOfLeftSideRhseWillBeAdded is element of the interval [minCountElementDistributedTo, maxCountElementDistributedTo]
            int countOfLeftSideRhseWillBeAdded = random.nextInt(maxCountElementDistributedTo) + minCountElementDistributedTo;
            //Removing Variables from variablesWeighted until countOfVarsTerminalWillBeAdded vars are left.
            List<Variable> tempVar = new ArrayList<>(variablesWeighted);
            for (int i = tempVar.size(); i > countOfLeftSideRhseWillBeAdded; i--) {
                tempVar.remove(random.nextInt(tempVar.size()));
            }
            //Adding the element to the leftover variables
            for (Variable var : tempVar) {
                grammar.addProduction(new Production(var, tempRhse));
            }
        }
        grammarWordWrapper.setGrammar(grammar);
        return grammarWordWrapper;
    }

    /**
     * Equals the circled A Method.
     * grammarWordWrapper only needed for its contained Grammar here.
     */
    public static GrammarWordWrapper distributeTerminals(
            List<Terminal> terminals,
            GrammarWordWrapper grammarWordWrapper,
            _GrammarGeneratorSettings grammarGeneratorSettings,
            List<Variable> variablesWeighted) {
        return distributeDiceRollRightHandSideElements(
                grammarWordWrapper,
                terminals,
                grammarGeneratorSettings.getMinValueTerminalsAreAddedTo(),
                grammarGeneratorSettings.getMaxValueTerminalsAreAddedTo(),
                variablesWeighted
        );
    }

    /**
     * Equals the circled B Method.
     * grammarWordWrapper only needed for its contained Grammar here.
     */
    public static GrammarWordWrapper distributeCompoundVariables(
            List<VariableCompound> varComp,
            GrammarWordWrapper grammarWordWrapper,
            _GrammarGeneratorSettings grammarGeneratorSettings,
            List<Variable> variablesWeighted) {
        return distributeDiceRollRightHandSideElements(
                grammarWordWrapper,
                varComp,
                grammarGeneratorSettings.getMinValueCompoundVariablesAreAddedTo(),
                grammarGeneratorSettings.getMaxValueCompoundVariablesAreAddedTo(),
                variablesWeighted
        );
    }
}
