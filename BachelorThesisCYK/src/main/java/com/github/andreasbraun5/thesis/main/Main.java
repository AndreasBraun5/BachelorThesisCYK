package com.github.andreasbraun5.thesis.main;

import com.github.andreasbraun5.thesis.generator.GrammarGeneratorDiceRollOnly;
import com.github.andreasbraun5.thesis.generator.GrammarGeneratorDiceRollVar1;
import com.github.andreasbraun5.thesis.generator.GrammarGeneratorDiceRollVar2;
import com.github.andreasbraun5.thesis.generator.GrammarGeneratorSettings;
import com.github.andreasbraun5.thesis.grammar.Terminal;
import com.github.andreasbraun5.thesis.grammar.Variable;
import com.github.andreasbraun5.thesis.grammar.VariableStart;
import com.github.andreasbraun5.thesis.grammarproperties.GrammarProperties;
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
    // Note: C:\GitHub\BachelorThesis\BachelorThesisCYK>mvn clean install    --> .jar
    // TODO: Maven not working correctly.
    // TODO: Daten vs. Logik (Entweder Klasse oder zumindest Methoden)
    // TODO: Factory Pattern (siehe "of" methoden usw) MARTIN: NICHT ÜBERALL BENUTZEN, nur wegen usability in Unit Tests
    // TODO: Attribute Ableiten und nicht alles speichern, INfos nicht doppelt speichern
    // TODO: use one constructor and various static methods that delegate to it
    // TODO: Factory Pattern2 : Siehe ResultSample: Berechnung nicht im Konstruktor, sondern in einer Berechnenden Methode
    // TODO: Final verwenden,
    // TODO: Collections.unmodifiable(List|Set|Map) etc. in gettern, vll nach hinten an stellen
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
        int countDifferentWords = 10;

        ResultCalculator resultCalculator = ResultCalculator.builder().
                countDifferentWords(countDifferentWords).
                countOfGrammarsToGeneratePerWord(countGeneratedGrammarsPerWord).build();
        GrammarProperties grammarProperties = generateGrammarPropertiesForTesting();


        GrammarGeneratorSettings settingsGrammarGeneratorDiceRollVar1 = new GrammarGeneratorSettings(
                grammarProperties, "GrammarGeneratorDiceRollVar1");
        Result resultGrammarGeneratorDiceRollVar1 = resultCalculator.buildResultWithGenerator(
                new GrammarGeneratorDiceRollVar1(settingsGrammarGeneratorDiceRollVar1),
                WorkLog.createFromWriter(new FileWriter(ThesisDirectory.LOGS.file(settingsGrammarGeneratorDiceRollVar1.name)))
        );
        Util.writeResultToTxtFile(resultGrammarGeneratorDiceRollVar1);

        GrammarGeneratorSettings settingsGrammarGeneratorDiceRollVar2 = new GrammarGeneratorSettings(
                grammarProperties, "GrammarGeneratorDiceRollVar2");
        Result resultGrammarGeneratorDiceRollVar2 = resultCalculator.buildResultWithGenerator(
                new GrammarGeneratorDiceRollVar2(settingsGrammarGeneratorDiceRollVar2),
                WorkLog.createFromWriter(new FileWriter(ThesisDirectory.LOGS.file(settingsGrammarGeneratorDiceRollVar2.name)))
        );
        Util.writeResultToTxtFile(resultGrammarGeneratorDiceRollVar2);

        GrammarGeneratorSettings settingsGrammarGeneratorDiceRollOnly = new GrammarGeneratorSettings(
                grammarProperties, "GrammarGeneratorDiceRollOnly");
        Result resultGrammarGeneratorDiceRollOnly = resultCalculator.buildResultWithGenerator(
                new GrammarGeneratorDiceRollOnly(settingsGrammarGeneratorDiceRollOnly),
                WorkLog.createFromWriter(new FileWriter(ThesisDirectory.LOGS.file(settingsGrammarGeneratorDiceRollOnly.name)))
        );
        Util.writeResultToTxtFile(resultGrammarGeneratorDiceRollOnly);
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
         * grammarConstraints: sizeOfWord = 10 // All TestResults will be based on words of this size
         * grammarConstraints: maxNumberOfVarsPerCell = 3 // The CYK simple pyramid must contain less than 4 vars in cone cells
         * examConstraints: minRightCellCombinationsForced = 1
         * examConstraints: countSumOfProductions = 10; // approximate maximum value taken from the exam exercises
         * examConstraints: minRightCellCombinationsForced = 1 countSumOfVarsInPyramid = 50; // approximate maximum value taken from the exam exercises
         */
        GrammarProperties grammarProperties = new GrammarProperties(new VariableStart("S"), variables, terminals);
        grammarProperties.examConstraints.maxNumberOfVarsPerCell = 3; //Set like this
        return grammarProperties;
    }
}