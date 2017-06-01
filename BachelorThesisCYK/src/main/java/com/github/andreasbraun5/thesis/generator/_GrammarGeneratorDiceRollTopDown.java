package com.github.andreasbraun5.thesis.generator;

import com.github.andreasbraun5.thesis.exception.GrammarRuntimeException;
import com.github.andreasbraun5.thesis.grammar.*;
import com.github.andreasbraun5.thesis.parser.CYK;
import com.github.andreasbraun5.thesis.util.SetVMatrix;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by AndreasBraun on 01.06.2017.
 */
// TODO: is it top down?
public class _GrammarGeneratorDiceRollTopDown extends _GrammarGenerator {

    public _GrammarGeneratorDiceRollTopDown(_GrammarGeneratorSettings grammarGeneratorSettings) {
        super(grammarGeneratorSettings);
        this.generatorType = "DICEROLLTOPDOWN";
    }

    @Override
    public GrammarWordWrapper generateGrammarWordWrapper(GrammarWordWrapper grammarWordWrapper) {
        // Set the variableStart specifically because grammar and grammarProperties aren't interconnected.
        // TODO: simplify
        Grammar grammar = new Grammar( grammarGeneratorSettings.grammarProperties.variableStart );
        grammarWordWrapper.setGrammar( grammar );
        grammarWordWrapper = _GrammarGeneratorUtil.distributeTerminals(
                new ArrayList<>(grammarGeneratorSettings.grammarProperties.terminals),
                grammarWordWrapper,
                grammarGeneratorSettings,
                new ArrayList<>(grammarGeneratorSettings.grammarProperties.variables )
        );
        //Grammar grammar = grammarWordWrapper.getGrammar();
        List<Terminal> word = grammarWordWrapper.getWord();
        int wordSize = word.size();
        // The stepII now needs to be done first. Usage of CYK.calculateSetVAdvanced equivalent to CYK.stepIIAdvanced.
        // But like this stepIIAdvanced can stay private.
        SetVMatrix<VariableK> setVMatrix = CYK.calculateSetVAdvanced( grammar, word );
        Set<VariableK>[][] setVAdvanced = setVMatrix.getSetV();
        ///*
        SetVMatrix<Variable> stepIIPrint = SetVMatrix.buildEmptySetVMatrixWrapper( wordSize, Variable.class ).setSetV(
                setVMatrix.getSimpleMatrix() );
        System.out.print( "\n" + grammarWordWrapper.getGrammar() );
        System.out.print( word );
        System.out.println( "\nStepII:" );
        System.out.print( stepIIPrint.getStringToPrintAsLowerTriangularMatrix() );
        //*/
        // Keep in mind that the setV matrix is a upper right matrix. But the description of how the algorithm works
        // is done, as if the setV pyramid points downwards (reflection on the diagonal + rotation to the left).
        // Regarding one cell, its upper left cell and its upper right cell are looked at.
        // setV[i][j] = down
        // setV[i + 1][j] = upper right
        // setV[i][j - 1] = upper left
        // Visited indexes are as following: 01->12->23->34; 02->13->24; 03->14; 04; with wordSize = 5;
        Set<VariableCompound> tempVarCompSet = new HashSet<>();
        for ( int k = 0; k < wordSize && setVAdvanced[0][wordSize - 1].isEmpty(); k++ ) { // row
            int i = k;
            for ( int j = i + 1; j < wordSize && setVAdvanced[0][wordSize - 1].isEmpty(); j++ ) { // column
                tempVarCompSet.clear();
                // if one, the left or right cell is empty, then break.
                if ( setVAdvanced[i][j - 1].size() != 0 && setVAdvanced[i + 1][j].size() != 0 ) {
                    // make all tuples of left and right --> tempVariablesCompound = tuples of type
                    // ({varLeft}, {varRight}) --> all possible compoundVariables
                    for ( VariableK varLeft : setVAdvanced[i][j - 1] ) {
                        for ( VariableK varRight : setVAdvanced[i + 1][j] ) {
                            tempVarCompSet.add( new VariableCompound(
                                            varLeft.getVariable(),
                                            varRight.getVariable()
                                    )
                            );
                        }
                    }
                    for ( VariableCompound varComp : tempVarCompSet ) {
                        Set<VariableCompound> varCompSet = new HashSet<>();
                        varCompSet.add( varComp );
                        // Because of dice rolling and a large amount of generated grammars, the actually to be added
                        // variable will just be ignored.
                        try {
                            grammarWordWrapper = _GrammarGeneratorUtil.distributeCompoundVariables(
                                    new ArrayList<>(varCompSet),
                                    grammarWordWrapper,
                                    grammarGeneratorSettings,
                                    new ArrayList<>(grammarGeneratorSettings.grammarProperties.variables )
                            );
                        }
                        catch (GrammarRuntimeException ignored) {
                        }
                        // Each time something is changed recalculate the setVAdvanced. The entire set is recalculated.
                        ///*
                        System.out.print( "\n" + grammarWordWrapper.getGrammar() );
                        System.out.println("\nsetV" + j + i);
                        //System.out.println("varsLeft: " + setVAdvanced[i][j - 1]);
                        //System.out.println("varsRight: " + setVAdvanced[i + 1][j]);
                        System.out.println("varsLeft: " + setVAdvanced[i][j - 1]);
                        System.out.println("varsRight: " + setVAdvanced[i + 1][j]);
                        System.out.println(tempVarCompSet);
                        System.out.print( word );
                        SetVMatrix<Variable> simpleBefore = SetVMatrix.buildEmptySetVMatrixWrapper(
                                wordSize,
                                Variable.class
                        ).setSetV( setVMatrix
                                .getSimpleMatrix() );
                        System.out.println( "\nBefore:" );
                        System.out.print( simpleBefore.getStringToPrintAsLowerTriangularMatrix() );
                        //*/
                        // Each time something is changed recalculate the setVAdvanced. The entire set is recalculated.
                        setVAdvanced = CYK.calculateSetVAdvanced( grammarWordWrapper.getGrammar(), word ).getSetV();
                        setVMatrix.setSetV( setVAdvanced );
                        ///*
                        SetVMatrix<Variable> simpleAfter = SetVMatrix.buildEmptySetVMatrixWrapper(
                                wordSize,
                                Variable.class
                        )
                                .setSetV( setVMatrix.getSimpleMatrix() );
                        System.out.println( "\nAfter:" );
                        System.out.print( simpleAfter.getStringToPrintAsLowerTriangularMatrix() );
                        //*/
                        int debugInt = 0;

                    }
                }
                i++;
            }
        }
        return grammarWordWrapper.setGrammar( grammar );    }
}
