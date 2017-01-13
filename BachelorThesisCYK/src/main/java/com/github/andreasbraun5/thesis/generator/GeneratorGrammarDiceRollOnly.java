package com.github.andreasbraun5.thesis.generator;

import com.github.andreasbraun5.thesis.grammar.*;

import java.util.*;

/**
 * Created by Andreas Braun on 21.12.2016.
 *  - consider terminals (terminal set) and alphabet as synonyms
 */
public class GeneratorGrammarDiceRollOnly implements GeneratorGrammar, GeneratorGrammarDiceRoll {

    private final Random random;

    public GeneratorGrammarDiceRollOnly(Random random) {
        this.random = random;
    }

    public GeneratorGrammarDiceRollOnly() {
        this(new Random());
    }

    /**
     * Generate a random grammar via dice rolling. Overloaded method for simple usage.
     * public Grammar generateGrammar(Set<Variable> variables, String word)
     */
    /**
     *  Each terminal must not be element of the rightHandSide. If you are deriving a GrammarProperties obj from
     *  a word, then there is a probability that not all terminals needed for construction of the word are at least
     *  at one RightHandSide of the generated grammar.
     */
    public Grammar generateGrammar(GrammarProperties grammarProperties) {
        // Set the variableStart specifically because grammar and grammarProperties aren't interconnected any more.
        Grammar grammar = new Grammar(grammarProperties.variableStart);
        // Distribute the terminals.
        // TODO think about the 1
        grammar = distributeDiceRollRightHandSideElements(grammar, grammarProperties, grammarProperties.terminals, 1);
        //System.out.println(grammar);
        // Distribute all combinations of size two of the vars.
        Set<VariableCompound> varTupel = new HashSet<>();
        for(Variable var1 : grammarProperties.variables) {
            for(Variable var2 : grammarProperties.variables) {
                varTupel.add(new VariableCompound(var1, var2));
            }
        }
        // TODO think about the 1
        grammar = distributeDiceRollRightHandSideElements(grammar, grammarProperties, varTupel, 0);
        return grammar;
    }

    /**
     *
     * @param elementDistributedToAtLeast: If you want to distribute the terminals to at least one rightHandSide then
     *                                   this value is 1.
     */
    private Grammar distributeDiceRollRightHandSideElements(Grammar grammar,
                                                            GrammarProperties grammarProperties,
                                                            Set<? extends RightHandSideElement> rightHandSideElements,
                                                            int elementDistributedToAtLeast) {
        for (RightHandSideElement tempRhse : rightHandSideElements) {
            // Each rightHandSideElement can be distributed to none or to all possible variables.
            // randomNumber is element of [0, grammarProperties.variables.size()]
            // TODO make configurable: Give Parameters of min and max for countOfLeftSideRhseWillBeAdded
            // Deciding to how many leftSides the rhse will be added. To none as min and to all as max.
            int countOfLeftSideRhseWillBeAdded = random.nextInt(
                    grammarProperties.variables.size())+elementDistributedToAtLeast; // TODO: Think about the elementDistributedToAtLeast
            // TODO: try biased dice rolling, like here as example
            // Removing Variables from tempVariables until countOfVarsTerminalWillBeAdded vars are left.
            List<Variable> tempVariables = new ArrayList<>(grammarProperties.variables);
            for (int i = tempVariables.size(); i > countOfLeftSideRhseWillBeAdded; i--) {
                tempVariables.remove(random.nextInt(tempVariables.size()));
            }
            for (Variable var : tempVariables) {
                grammar.addProduction(new Production(var, tempRhse));
            }
        }
        return grammar;
    }
}