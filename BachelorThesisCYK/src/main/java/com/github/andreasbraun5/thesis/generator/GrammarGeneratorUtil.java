package com.github.andreasbraun5.thesis.generator;

import com.github.andreasbraun5.thesis.grammarproperties.GrammarWordMatrixWrapper;
import com.github.andreasbraun5.thesis.grammar.*;
import com.github.andreasbraun5.thesis.pyramid.VariableK;
import com.github.andreasbraun5.thesis.util.SetVarKMatrix;
import com.github.andreasbraun5.thesis.util.Util;
import com.github.andreasbraun5.thesis.util.Word;

import java.util.*;

/**
 * Created by AndreasBraun on 01.06.2017.
 */
public class GrammarGeneratorUtil {

    private static final Random random = new Random();

    /**
     * If you want to distribute the terminals to at least one rightHandSide then minCountElementDistributedTo is 1.
     * Dice roll for every rhse how often it is added and to which vars in the grammar it is added.
     * Equals the Distribute standard method.
     */
    private static GrammarWordMatrixWrapper distributeDiceRollRightHandSideElements(
            GrammarWordMatrixWrapper grammarWordMatrixWrapper,
            List<? extends RightHandSideElement> rightHandSideElements,
            int minCountElementDistributedTo,
            int maxCountElementDistributedTo,
            List<Variable> variablesWeighted) {
        Grammar grammar = grammarWordMatrixWrapper.getGrammar();
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
        grammarWordMatrixWrapper.setGrammar(grammar);
        return grammarWordMatrixWrapper;
    }

    /**
     * Equals the circled A Method.
     * grammarWordMatrixWrapper only needed for its contained Grammar here.
     */
    public static GrammarWordMatrixWrapper distributeTerminals(
            List<Terminal> terminals,
            GrammarWordMatrixWrapper grammarWordMatrixWrapper,
            GrammarGeneratorSettings grammarGeneratorSettings,
            List<Variable> variablesWeighted) {
        return distributeDiceRollRightHandSideElements(
                grammarWordMatrixWrapper,
                terminals,
                grammarGeneratorSettings.getMinValueTerminalsAreAddedTo(),
                grammarGeneratorSettings.getMaxValueTerminalsAreAddedTo(),
                variablesWeighted
        );
    }

    /**
     * Equals the circled B Method.
     * grammarWordMatrixWrapper only needed for its contained Grammar here.
     */
    public static GrammarWordMatrixWrapper distributeCompoundVariables(
            List<VariableCompound> varComp,
            GrammarWordMatrixWrapper grammarWordMatrixWrapper,
            GrammarGeneratorSettings grammarGeneratorSettings,
            List<Variable> variablesWeighted) {
        return distributeDiceRollRightHandSideElements(
                grammarWordMatrixWrapper,
                varComp,
                grammarGeneratorSettings.getMinValueCompoundVariablesAreAddedTo(),
                grammarGeneratorSettings.getMaxValueCompoundVariablesAreAddedTo(),
                variablesWeighted
        );
    }

    // Its structure is very similar to stepIIAdvanced and calculateSetVAdvanced.
    public static GrammarWordMatrixWrapper removeUselessProductions(
            GrammarWordMatrixWrapper grammarWordMatrixWrapper) {
        Grammar grammar = grammarWordMatrixWrapper.getGrammar();
        Word word = grammarWordMatrixWrapper.getWord();
        List<Terminal> wordAsTerminalList = word.getTerminals();
        SetVarKMatrix SetVarKMatrix = grammarWordMatrixWrapper.getVarKMatrix();
        Set<VariableK>[][] setV = SetVarKMatrix.getSetV();
        int wordLength = SetVarKMatrix.getSetV().length;
        Map<Variable, List<Production>> productions = grammar.getProductionsMap();
        Set<Production> onlyUsefulProductions = new HashSet<>();
        // Similar to stepIIAdvanced
        // Look at each terminal of the word
        for ( int i = 1; i <= wordLength; i++ ) {
            RightHandSideElement tempTerminal = wordAsTerminalList.get( i - 1 );
            // Get all productions that have the same leftHandSide variable. This is done for all unique variables.
            // So all production in general are taken into account.
            for ( Map.Entry<Variable, List<Production>> entry : grammar.getProductionsMap().entrySet() ) {
                VariableK var = new VariableK( entry.getKey(), i );
                List<Production> prods = entry.getValue();
                // Check if there is one rightHandSideElement that equals the observed terminal.
                for ( Production prod : prods ) {
                    if ( prod.isElementAtRightHandSide( tempTerminal ) ) {
                        setV[i - 1][i - 1].add( var );
                        // This here was added.
                        onlyUsefulProductions.add( prod );
                    }
                }
            }
        }
        // Similar to calculateSetVAdvanced
        for ( int l = 1; l <= wordLength - 1; l++ ) {
            // i loop of the described algorithm.
            // Needs to be 1 <= i <= n-1-l, because of index starting from 0 for an array.
            for ( int i = 0; i <= wordLength - l - 1; i++ ) {
                // k loop of the described algorithm
                // Needs to be i <= k <= i+l, because of index starting from 0 for i already.
                for ( int k = i; k < i + l; k++ ) {
                    // tempSetX contains the newly to be added variables, regarding the "X-->YZ" rule.
                    // If the substring X can be concatenated with the substring Y and substring Z, whereas Y and Z
                    // must be element of its specified subsets, then add the element X to setV[i][i+l]
                    Set<Variable> tempSetX = new HashSet<>();
                    Set<Variable> tempSetY = Util.varKSetToVarSet( setV[i][k] );
                    Set<Variable> tempSetZ = Util.varKSetToVarSet( setV[k + 1][i + l] );
                    Set<VariableCompound> tempSetYZ = new HashSet<>();
                    // All possible concatenations of the variables yz are constructed. And so its substrings, which
                    // they are able to generate
                    for ( Variable y : tempSetY ) {
                        for ( Variable z : tempSetZ ) {
                            @SuppressWarnings("SuspiciousNameCombination")
                            VariableCompound tempVariable = new VariableCompound( y, z );
                            tempSetYZ.add( tempVariable );
                        }
                    }
                    // Looking at all productions of the grammar, it is checked if there is one rightHandSideElement that
                    // equals any of the concatenated variables tempSetYZ. If yes, the LeftHandSideElement or more
                    // specific the variable of the production is added to the tempSetX. All according to the "X-->YZ" rule.
                    for ( List<Production> tempProductions : productions.values() ) {
                        for ( Production tempProduction : tempProductions ) {
                            for ( VariableCompound yz : tempSetYZ ) {
                                if ( tempProduction.isElementAtRightHandSide( yz ) ) {
                                    // This here is changed.
                                    onlyUsefulProductions.add( tempProduction );
                                }
                            }
                        }
                    }
                    for ( Variable var : tempSetX ) {
                        // ( k + 1) because of index range of k  because of i.
                        setV[i][i + l].add( new VariableK( var, ( k + 1 ) ) );
                    }
                }
            }
        }
        grammar.removeAllProductions();
        Production[] productionsArray = new Production[onlyUsefulProductions.size()];
        onlyUsefulProductions.toArray( productionsArray );
        grammar.addProduction( productionsArray );
        grammarWordMatrixWrapper.setGrammar(grammar);
        return grammarWordMatrixWrapper;
    }

}
