package com.github.andreasbraun5.thesis.generator;

import com.github.andreasbraun5.thesis.grammar.*;
import com.github.andreasbraun5.thesis.parser.CYK;

import java.util.*;

/**
 * Created by Andreas Braun on 21.12.2016.
 *  - consider terminals and alphabet as synonyms
 */
public class GeneratorGrammarDiceRoll implements GeneratorGrammar{
    // TODO: Stepwise CYK = equals a backtracking attempt.
    /*
    Check 2) Row1: Distribute the alphabet symbols over the variables. Every alphabet symbol needs at least one var.
        --> numberOfVars <= sizeOfAlphabet
        (make configurable!) Default: at least one, at most 2.
        --> sizeOfAlphabet/2 <= numberOfVars <=  sizeOfAlphabet

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
    public Grammar generateGrammar(GrammarProperties grammarProperties){
        // using lists now, because of easier access.
        List<Terminal> terminals = new ArrayList<>();
        terminals.addAll(grammarProperties.terminals);
        List<Variable> variables = new ArrayList<>();
        variables.addAll(grammarProperties.variables);
        // GrammarProperties and Grammar are no longer interconnected. Therefore the startingVariable needs to be added
        // to the grammar specifically.
        Grammar grammar = new Grammar(grammarProperties.variableStart);
        // Each variable gets to be one production on its own, not depended on the count of terminals
        for (Variable variable : variables) {
            Production production = new Production(variable);
            grammar.addProduction(production);
        }
        System.out.println();
        System.out.println();
        System.out.println("Grammar after each Variable has its Production: ");
        // Now you have a production for every possible variable with an empty rightSide (Set with size of 0)
        System.out.println(grammar);
        // Each terminal can be distributed to none or to all possible variables
        //  - decide to how many variables the terminal will be added
        //  - decide to which variables the terminal will be added
        int varCount = variables.size();
        for (Terminal terminal : terminals) {
            Random random = new Random();
            // randomNumber is element of [0, variables.size()]
            // TODO: Maybe exclude 0 by adding random.nextInt(varCount-1) + 1;
            int numberOfVarsTerminalWillBeAdded = random.nextInt(varCount);
            {
                // Removing Variables from tempVariables until numberOfVarsTerminalWillBeAdded is left.
                // Then add to these variables the looked at terminal.
                List<Variable> tempVariables = new ArrayList<>(variables);
                int tempVariablesSize = tempVariables.size();
                for (int i = tempVariablesSize; i <= numberOfVarsTerminalWillBeAdded; i--){
                    tempVariables.remove(random.nextInt(tempVariables.size()));
                }
            }
            {
                // For each left variable, the Productions needs to be added to its ProductionList regarding its key.
                for (int i = 0; i <= varCount; i++){
                    //TODO left here for writing the Tests
                }
            }
        }




        {
            int curVar = 0;
            for(Terminal terminal : terminals) {
                Variable variable = variables.get(curVar%2);
                List<Production> productionSet = new ArrayList<>();
                Production production = new Production(variable, terminal);
                productionSet.add(production);
                // The ProductionSet overrides the default production set that only
                // contained an empty production. In this case, this is okay
                grammar.replaceProductions(variable, productionSet);
                ++curVar;
            }
        }
        System.out.println(grammar);
        return grammar;
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
}
