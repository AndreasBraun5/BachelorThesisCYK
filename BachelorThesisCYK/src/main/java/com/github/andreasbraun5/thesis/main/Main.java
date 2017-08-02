package com.github.andreasbraun5.thesis.main;

import com.github.andreasbraun5.thesis.antlr.ExerciseCompiler;
import com.github.andreasbraun5.thesis.antlr.ExerciseStringConverter;
import com.github.andreasbraun5.thesis.exercise.Exercise;
import com.github.andreasbraun5.thesis.generator.*;
import com.github.andreasbraun5.thesis.grammar.Grammar;
import com.github.andreasbraun5.thesis.grammar.Terminal;
import com.github.andreasbraun5.thesis.grammar.Variable;
import com.github.andreasbraun5.thesis.grammar.VariableStart;
import com.github.andreasbraun5.thesis.grammarproperties.GrammarProperties;
import com.github.andreasbraun5.thesis.latex.ExerciseLatex;
import com.github.andreasbraun5.thesis.latex.WriteToTexFile;
import com.github.andreasbraun5.thesis.mylogger.WorkLog;
import com.github.andreasbraun5.thesis.parser.CYK;
import com.github.andreasbraun5.thesis.pyramid.GrammarPyramidWrapper;
import com.github.andreasbraun5.thesis.pyramid.Pyramid;
import com.github.andreasbraun5.thesis.resultcalculator.BestResultSamples;
import com.github.andreasbraun5.thesis.resultcalculator.Result;
import com.github.andreasbraun5.thesis.resultcalculator.ResultCalculator;
import com.github.andreasbraun5.thesis.util.Tuple;
import com.github.andreasbraun5.thesis.util.Util;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.Assert;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class Main {
    static final Logger LOGGER = Logger.getLogger(Main.class.getName());


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
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {

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

        int countGeneratedGrammarsPerWord = 10;
        int countDifferentWords = 10;

        ResultCalculator resultCalculator = ResultCalculator.builder().
                countDifferentWords(countDifferentWords).
                countOfGrammarsToGeneratePerWord(countGeneratedGrammarsPerWord).build();
        GrammarProperties grammarProperties = generateGrammarPropertiesForTesting();


        GrammarGeneratorSettings settingsGrammarGeneratorDiceRollVar1 = new GrammarGeneratorSettings(
                grammarProperties, "GrammarGeneratorDiceRollVar1");
        Tuple<Result, BestResultSamples> resultGrammarGeneratorDiceRollVar1 = resultCalculator.buildResultWithGenerator(
                new GrammarGeneratorDiceRollVar1(settingsGrammarGeneratorDiceRollVar1),
                WorkLog.createFromWriter(new FileWriter(ThesisDirectory.LOGS.fileAsTxt(settingsGrammarGeneratorDiceRollVar1.name)))
        );
        Util.writeResultToTxtFile(resultGrammarGeneratorDiceRollVar1);

        GrammarGeneratorSettings settingsGrammarGeneratorDiceRollVar2 = new GrammarGeneratorSettings(
                grammarProperties, "GrammarGeneratorDiceRollVar2");
        Tuple<Result, BestResultSamples> resultGrammarGeneratorDiceRollVar2 = resultCalculator.buildResultWithGenerator(
                new GrammarGeneratorDiceRollVar2(settingsGrammarGeneratorDiceRollVar2),
                WorkLog.createFromWriter(new FileWriter(ThesisDirectory.LOGS.fileAsTxt(settingsGrammarGeneratorDiceRollVar2.name)))
        );
        Util.writeResultToTxtFile(resultGrammarGeneratorDiceRollVar2);

        GrammarGeneratorSettings settingsGrammarGeneratorDiceRollOnly = new GrammarGeneratorSettings(
                grammarProperties, "GrammarGeneratorDiceRollOnly");
        Tuple<Result, BestResultSamples> resultGrammarGeneratorDiceRollOnly = resultCalculator.buildResultWithGenerator(
                new GrammarGeneratorDiceRollOnly(settingsGrammarGeneratorDiceRollOnly),
                WorkLog.createFromWriter(new FileWriter(ThesisDirectory.LOGS.fileAsTxt(settingsGrammarGeneratorDiceRollOnly.name)))
        );
        Util.writeResultToTxtFile(resultGrammarGeneratorDiceRollOnly);


        GrammarGeneratorSettings settingsGrammarGeneratorSplitThenFill = new GrammarGeneratorSettings(
                grammarProperties, "GrammarGeneratorSplitThenFill");
        settingsGrammarGeneratorSplitThenFill.setMinValueCompoundVariablesAreAddedTo(1);
        settingsGrammarGeneratorSplitThenFill.setMaxValueCompoundVariablesAreAddedTo(1);
        Tuple<Result, BestResultSamples> resultGrammarGeneratorSplitThenFill = resultCalculator.buildResultWithGenerator(
                new GrammarGeneratorSplitThenFill(settingsGrammarGeneratorSplitThenFill),
                WorkLog.createFromWriter(new FileWriter(ThesisDirectory.LOGS.fileAsTxt(settingsGrammarGeneratorSplitThenFill.name)))
        );
        Util.writeResultToTxtFile(resultGrammarGeneratorSplitThenFill);

        GrammarGeneratorSettings settingsGrammarGeneratorSplitAndFill = new GrammarGeneratorSettings(
                grammarProperties, "GrammarGeneratorSplitAndFill");
        settingsGrammarGeneratorSplitAndFill.setMinValueCompoundVariablesAreAddedTo(1);
        settingsGrammarGeneratorSplitAndFill.setMaxValueCompoundVariablesAreAddedTo(1);
        settingsGrammarGeneratorSplitAndFill.setMinValueTerminalsAreAddedTo(1);
        settingsGrammarGeneratorSplitAndFill.setMaxValueTerminalsAreAddedTo(1);
        Tuple<Result, BestResultSamples> resultGrammarGeneratorSplitAndFill = resultCalculator.buildResultWithGenerator(
                new GrammarGeneratorSplitAndFill(settingsGrammarGeneratorSplitAndFill),
                WorkLog.createFromWriter(new FileWriter(ThesisDirectory.LOGS.fileAsTxt(settingsGrammarGeneratorSplitAndFill.name)))
        );
        Util.writeResultToTxtFile(resultGrammarGeneratorSplitAndFill);

    }

    public static void runCmd(ExecutorService executorService, String cmd) throws IOException, ExecutionException, InterruptedException {
        LOGGER.info("running cmd: " + cmd);

        final Process p = Runtime.getRuntime().exec(cmd);
        executorService.submit(() -> {
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            try {
                while ((line = input.readLine()) != null) {
                    LOGGER.info(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).get();
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