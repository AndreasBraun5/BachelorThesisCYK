package com.github.andreasbraun5.thesis.generator;

import com.github.andreasbraun5.thesis.grammar.*;
import com.github.andreasbraun5.thesis.parser.CYK;

import java.util.*;

/**
 * Created by Andreas Braun on 21.12.2016.
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
    // TODO: every Variable must have at least one rightHandSideElement before calling stepII
    // TODO: consider the VariableStart
    public static Grammar generateGrammar(GrammarProperties grammarProperties){
        Set<Variable> variables = grammarProperties.variables;
        Set<Terminal> tempTerminalsSet = grammarProperties.terminals;
        List<Terminal> terminals = new ArrayList<>();
        terminals.addAll(tempTerminalsSet);

        Grammar grammar = new Grammar(new VariableStart("S"));
        int wordlength = terminals.size();
        Set<Variable>[][] setV = new Set[wordlength][wordlength];
        for (int i = 0; i < wordlength; i++) {
            for (int j = 0; j < wordlength; j++) {
                setV[i][j] = new HashSet<>();
            }
        }

        List<Terminal> alphabet = withoutDuplicates(terminals);
        List<Variable> variablesList = withoutDuplicates(variables);
        // TODO: think about the need of the GrammarProperties class
        //GrammarProperties grammarProperties = GrammarProperties.generateGrammarPropertiesFromWord(word);
        //grammarProperties.variables.addAll(variables);

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
        // TODO: gleichmäßig verteilen nicht erlaubt. random gleichmaßig
        {
            int curVar = 0;
            for(Terminal terminal : alphabet) {
                Variable variable = variablesList.get(curVar);
                Production production = new Production(variable, terminal);
                List<Production> productionSet = new ArrayList<>();
                productionSet.add(production);
                // The ProductionSet overrides the default production set that only
                // contained an empty production. In this case, this is okay
                grammar.replaceProductions(variable, productionSet);
                ++curVar;
            }
        }
        // TODO: NullPointerException, now here C-->null
        CYK.stepII(setV, terminals, grammar);
        System.out.println(grammar);
        CYK.printSetV(setV, "setV");

        // Row2: Per cell, compute combinations of vars. Distribute again over right hand sides of vars such that the
        return grammar;
    }


    /**
     *  Helper Method used by generateGrammar. Removing duplicates from a collection.
     */
    private static <T extends RightHandSideElement> List<T> withoutDuplicates(Collection<T> ruleElements) {
        List<T> ret = new ArrayList<>();
        ret.addAll(new HashSet<>(ruleElements));
        return ret;
    }
}
