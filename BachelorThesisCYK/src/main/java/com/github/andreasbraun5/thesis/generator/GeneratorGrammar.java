package com.github.andreasbraun5.thesis.generator;

import com.github.andreasbraun5.thesis.grammar.*;
import com.github.andreasbraun5.thesis.parser.CYK;

import java.util.*;

/**
 * Created by Andreas Braun on 21.12.2016.
 */
public class GeneratorGrammar {

    // TODO: Stepwise CYK;

    /*
    CHECK 1) Generate a string over the alphabet
    Check 2) Row1: Distribute the alphabet symbols over the variables. Every alphabet symbol needs at least one var.
        --> numberOfVars <= sizeOfAlphabet
        (make configurable!) Default: at least one, at most 2.
        --> sizeOfAlphabet/2 <= numberOfVars <=  sizeOfAlphabet

    3) Row2: Per cell, compute combinations of vars. Distribute again over right hand sides of vars such that the
        0-2 constraint (maxNumberOfVarsPerCell) is satisfied. Note: Here it hurts less to also have an empty cell.
    4) Row3 till last row: Similar procedure, but we have to take more cell combinations into account
    5) One goal is to determine and increase the success ratio
    CHECK 6) Printing of the matrices must be possible
     */

    /*
        numberOfVars from 2 to 5
        sizeOfAlphabet from 2 to 4
        int maxNumberOfVarsPerCell = 3;
    */

    public static Grammar findGrammar(String word, Set<Variable> variables, int maxVariableInCell) {
        List<Terminal> list = new ArrayList<>();
        for(int i = 0; i < word.length(); i++) {
            list.add(new Terminal(String.valueOf(word.charAt(i))));
        }
        return findGrammar(list, variables, maxVariableInCell);
    }

    public static <T extends RightHandSideElement> List<T> withoutDuplicates(Collection<T> ruleElements) {
        List<T> ret = new ArrayList<>();
        ret.addAll(new HashSet<>(ruleElements));
        return ret;
    }

    public static boolean checkIntegrity(){
        return false;
    }

    // TODO: evtl ohne Beschränkung sogar
    // TODO: every Variable must have at least one righthandsideElement before calling stepII
    public static Grammar findGrammar(List<Terminal> word, Set<Variable> variablesSet, int maxVariableInCell){
        Grammar grammar = new Grammar();
        int wordlength = word.size();
        Set<Variable>[][] setV = new Set[wordlength][wordlength];
        for (int i = 0; i < wordlength; i++) {
            for (int j = 0; j < wordlength; j++) {
                setV[i][j] = new HashSet<>();
            }
        }
        List<Terminal> alphabet = withoutDuplicates(word);
        List<Variable> variablesList = withoutDuplicates(variablesSet);
        // TODO: think about the need of the GrammarProperties class
        //GrammarProperties grammarProperties = GrammarProperties.generateGrammarPropertiesFromWord(word);
        //grammarProperties.variables.addAll(variables);

        // Each variable gets to be one Production, not depended on the count of terminals
        for (Variable variable : variablesSet) {
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
        // TODO: gleichmäßig verteilen nicht erlaubt.
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
        CYK.stepII(setV, word, grammar);
        System.out.println(grammar);
        CYK.printSetV(setV);

        // Row2: Per cell, compute combinations of vars. Distribute again over right hand sides of vars such that the


        return grammar;
    }
}
