package com.github.andreasbraun5.thesis.main;

import com.github.andreasbraun5.thesis.generator.GrammarGeneratorDiceRollOnly;
import com.github.andreasbraun5.thesis.generator.GrammarGeneratorSettings;
import com.github.andreasbraun5.thesis.grammarproperties.GrammarProperties;
import com.github.andreasbraun5.thesis.grammar.Terminal;
import com.github.andreasbraun5.thesis.grammar.Variable;
import com.github.andreasbraun5.thesis.grammar.VariableStart;
import com.github.andreasbraun5.thesis.mylogger.WorkLog;
import com.github.andreasbraun5.thesis.resultcalculator.Result;
import com.github.andreasbraun5.thesis.resultcalculator.ResultCalculator;
import com.github.andreasbraun5.thesis.util.Util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Main {

    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! SetV is in reality an upper triangular matrix !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // TODO Note: The favouritism can be set for the starting variable specifically.
    // TODO Note: Maybe use the fraction of CountVars and countTerminals.
    // TODO Note: Maybe incorporate in rightCellCombinations forced, that more than * different vars are forcing.
    // TODO Note: all checks are done on the simpleSetV
    // TODO Note: Tree package Latex: http://tex.stackexchange.com/questions/5447/how-can-i-draw-simple-trees-in-latex
    // TODO Note: C:\GitHub\BachelorThesis\BachelorThesisCYK>mvn clean install    --> .jar
    //
    // TODO Implement: CYK tree in Combination with latex picture creation.
    // TODO: Algorithm Duda Prepare, but maybe this isn't wanted
    // TODO: Implement pyramid vs methods: getRight, getLeft, getUpperRight, getUpperLeft, ... methods in SetVarKMatrix. Possible to check indices. Legacy code still works.
    // TODO: Outsource calculateSubsetForCell from CYK algorithm. Implicit testing possible and reuse.
    // TODO: Comparator needed for the stored samples in the .txt file.
    // TODO: More than 5 cell in TikzPicture CellLatex
    public static void main(String[] args) throws IOException {

        ThesisDirectory.initPaths();

        /**
         * 	Generating the settings for the generatorTest.
         * 	GrammarProperties = general settings, the same for all settings of one run.
         * 	GrammarGeneratorSettingsDiceRoll = generator specific settings.
         */
        /**
         * 	N = countOfGrammarsToGenerate.
         * 	Select the testing method.
         * 	Comparability of the TestResults is given via using the same N and the same GrammarProperties.
         */
        // It is recommended to use a high countDifferentWords. Word independent results are achieved.
        int countGeneratedGrammarsPerWord = 100;
        int countDifferentWords = 100;

        ResultCalculator resultCalculator = ResultCalculator.buildResultCalculator().
                setCountDifferentWords(countDifferentWords).
                setCountOfGrammarsToGeneratePerWord(countGeneratedGrammarsPerWord);
        GrammarProperties grammarProperties = generateGrammarPropertiesForTesting();


        GrammarGeneratorSettings settingsGrammarGeneratorDiceRollOnlyWithLog = new GrammarGeneratorSettings(
                grammarProperties, "GrammarGeneratorDiceRollOnlyWithLog.txt");
        settingsGrammarGeneratorDiceRollOnlyWithLog.setMaxValueTerminalsAreAddedTo(1);
        settingsGrammarGeneratorDiceRollOnlyWithLog.setMinValueTerminalsAreAddedTo(1);
        settingsGrammarGeneratorDiceRollOnlyWithLog.setMaxValueCompoundVariablesAreAddedTo(2);
        Result GrammarGeneratorDiceRollOnlyWithLog = resultCalculator.buildResultWithGenerator(
                new GrammarGeneratorDiceRollOnly(settingsGrammarGeneratorDiceRollOnlyWithLog),
                WorkLog.createFromWriter(new FileWriter(ThesisDirectory.LOGS.file(settingsGrammarGeneratorDiceRollOnlyWithLog.name)))
        );
        // TODO: should be done always!? How best done? Via destructor?
        Util.writeResultToTxtFile(GrammarGeneratorDiceRollOnlyWithLog);


        GrammarGeneratorSettings settingsGrammarGeneratorDiceRollOnlyWithoutLog = new GrammarGeneratorSettings(
                grammarProperties, "GrammarGeneratorDiceRollOnlyWithoutLog.txt");
        settingsGrammarGeneratorDiceRollOnlyWithoutLog.setMaxValueTerminalsAreAddedTo(1);
        settingsGrammarGeneratorDiceRollOnlyWithoutLog.setMinValueTerminalsAreAddedTo(1);
        settingsGrammarGeneratorDiceRollOnlyWithoutLog.setMaxValueCompoundVariablesAreAddedTo(2);
        Result GrammarGeneratorDiceRollOnlyWithoutLog = resultCalculator.buildResultWithGenerator(
                new GrammarGeneratorDiceRollOnly(settingsGrammarGeneratorDiceRollOnlyWithoutLog),
                // nothing will be logged now
                WorkLog.createFromWriter(null)
        );
        // TODO: should be done always!? How best done? Via destructor?
        Util.writeResultToTxtFile(GrammarGeneratorDiceRollOnlyWithoutLog);

    }

    public static GrammarProperties generateGrammarPropertiesForTesting() {
        Set<Variable> variables = new HashSet<>();
        variables.add(new Variable("A"));
        variables.add(new Variable("B"));
        variables.add(new Variable("C"));
        Set<Terminal> terminals = new HashSet<>();
        terminals.add(new Terminal("a"));
        terminals.add(new Terminal("b"));
        //terminals.add( new Terminal( "c" ) );
        //terminals.add( new Terminal( "d" ) );
        //terminals.add( new Terminal( "e" ) );
        //terminals.add( new Terminal( "f" ) );

        /**
         * Some settings for the beginning:
         * variables.size() should be around 4, considering exam exercises.
         * terminals.size() should be 2.
         * Used default setting:
         * grammarPropertiesGrammarRestrictions: sizeOfWord = 10 // All TestResults will be based on words of this size
         * grammarPropertiesGrammarRestrictions: maxNumberOfVarsPerCell = 3 // The CYK simple pyramid must contain less than 4 vars in cone cell
         * grammarPropertiesExamConstraints: minRightCellCombinationsForced = 1
         * grammarPropertiesExamConstraints: countSumOfProductions = 10; // approximate maximum value taken from the exam exercises
         * grammarPropertiesExamConstraints: minRightCellCombinationsForced = 1 countSumOfVarsInPyramid = 50; // approximate maximum value taken from the exam exercises
         */
        GrammarProperties grammarProperties = new GrammarProperties(new VariableStart("S"), variables, terminals);
        grammarProperties.grammarPropertiesGrammarRestrictions.setMaxNumberOfVarsPerCell(3); //Set like this
        return grammarProperties;
    }
}