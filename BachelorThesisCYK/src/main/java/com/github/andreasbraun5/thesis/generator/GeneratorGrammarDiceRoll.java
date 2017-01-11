package com.github.andreasbraun5.thesis.generator;

import com.github.andreasbraun5.thesis.exception.GrammarException;
import com.github.andreasbraun5.thesis.grammar.*;

import java.util.*;

/**
 * Created by Andreas Braun on 21.12.2016.
 *  - consider terminals and alphabet as synonyms
 */
public class GeneratorGrammarDiceRoll implements GeneratorGrammar {

    private final Random random;

    public GeneratorGrammarDiceRoll(Random random) {
        this.random = random;
    }

    public GeneratorGrammarDiceRoll() {
        this(new Random());
    }

    // TODO: Stepwise CYK = equals a backtracking attempt.
    /*
    2) Row1:
    3) Row2: Per cell, compute combinations of vars. Distribute again over right hand sides of vars such that the
        0-2 constraint (maxNumberOfVarsPerCell) is satisfied. Note: Here it hurts less to also have an empty cell.
    4) Row3 till last row: Similar procedure, but we have to take more cell combinations into account
    5) One goal is to determine and increase the success ratio
     */
    /**
     * Generate a random grammar via dice rolling. Overloaded method for simple usage.
     */
    /*
    public Grammar generateGrammar(Set<Variable> variables, String word) {

        List<Terminal> list = new ArrayList<>();
        for (int i = 0; i < word.length(); i++) {
            list.add(new Terminal(String.valueOf(word.charAt(i))));
        }
        return generateGrammar(variables, list);
    }*/
    /**
     *  Each terminal must not be element of the rightHandSide. If you are deriving a GrammarProperties obj from
     *  a word, then there is a probability that not all terminals needed for construction of the word are at least
     *  at one RightHandSide of the generated grammar.
     */
    public Grammar generateGrammar(GrammarProperties grammarProperties) throws GrammarException {
        // Set the variableStart specifically because  grammar and grammarProperties aren´t interconnected any more.
        Grammar grammar = new Grammar(grammarProperties.variableStart);
        // Distribute the terminals.
        grammar = distributeDiceRollRightHandSideElement(grammar, grammarProperties, grammarProperties.terminals);
        //System.out.println(grammar);
        // Distribute all combinations of size two of the vars.
        Set<VariableCompound> varTupel = new HashSet<>();
        for(Variable var1 : grammarProperties.variables) {
            for(Variable var2 : grammarProperties.variables) {
                varTupel.add(new VariableCompound(var1, var2));
            }
        }
        grammar = distributeDiceRollRightHandSideElement(grammar, grammarProperties, varTupel);
        //System.out.println(grammar);
        return grammar;
    }

    /**
     *  Each terminal must not be element of the
     */
    private Grammar distributeDiceRollRightHandSideElement(Grammar grammar,
                                                           GrammarProperties grammarProperties,
                                                           Set<? extends RightHandSideElement> rightHandSideElements)
            throws GrammarException {
        for (RightHandSideElement tempRhse : rightHandSideElements) {
            // Each rightHandSideElement can be distributed to none or to all possible variables.
            // randomNumber is element of [0, grammarProperties.variables.size()]
            // TODO: Maybe exclude 0 by adding random.nextInt(grammarProperties.variables.size()-1) + 1;
            // TODO make configurable: Give Parameters of min and max for countOfLeftSideRhseWillBeAdded
            // Deciding to how many leftSides the rhse will be added. To none as min and to all as max.
            int countOfLeftSideRhseWillBeAdded = random.nextInt(grammarProperties.variables.size());
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


/*  // TODO: Backtracking attempt code to find here
    // old Code with the goal to use the backtracking attempt to always generate a valid grammar. --> No increase of
    // the succes rate would have been possible or more specific. The success rate is defined differently than that of the DiceRoll
    public Grammar generateGrammar(GrammarProperties grammarProperties){
        // using lists now, because of easier access.
        List<Terminal> terminals = new ArrayList<>();
        terminals.addAll(grammarProperties.terminals);
        List<Variable> variables = new ArrayList<>();
        variables.addAll(grammarProperties.variables);
        // GrammarProperties and Grammar are no longer interconnected. Therefore the startingVariable needs to be added
        // to the grammar specifically.
        Grammar grammar = new Grammar(grammarProperties.variableStart);

        int wordlength = terminals.size();
        Set<Variable>[][] setV = new Set[wordlength][wordlength];
        for (int i = 0; i < wordlength; i++) {
            for (int j = 0; j < wordlength; j++) {
                setV[i][j] = new HashSet<>();
            }
        }
        // Each variable gets to be one Production, not depended on the count of terminals
        for (Variable variable : variables) {
            Production production = new Production(variable);
            grammar.addProduction(production);
        }
        System.out.println();
        System.out.println();
        System.out.println("Grammar after each Variable has its Production: ");
        // Now you have a production for every possible variable with an empty rightSide (Set with size of 0)
        System.out.println(grammar);

        // Row1: Distributing the Terminals equally over the Variables. For over Terminal symbole: An nur eine oder an zwei seiten.
        // Würfle an welch an welche Variable es dran gemacht werden soll.
        // TTODO: gleichmäßig verteilen nicht erlaubt. random gleichmaßig
        {
            int curVar = 0;
            for(Terminal terminal : terminals) {
                Variable variable = variables.get(curVar);
                Production production = new Production(variable, terminal);
                List<Production> productionSet = new ArrayList<>();
                productionSet.add(production);
                // The ProductionSet overrides the default production set that only
                // contained an empty production. In this case, this is okay
                grammar.replaceProductions(variable, productionSet);
                ++curVar;
            }
        }
        // TTODO: NullPointerException, now here C-->null
        CYK.stepII(setV, terminals, grammar);
        System.out.println(grammar);
        CYK.printSetV(setV, "setV");

        // Row2: Per cell, compute combinations of vars. Distribute again over right hand sides of vars such that the
     }*/
