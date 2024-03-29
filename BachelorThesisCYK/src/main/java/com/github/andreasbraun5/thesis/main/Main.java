package com.github.andreasbraun5.thesis.main;

import com.github.andreasbraun5.thesis.generator.*;
import com.github.andreasbraun5.thesis.grammar.Terminal;
import com.github.andreasbraun5.thesis.grammar.Variable;
import com.github.andreasbraun5.thesis.grammar.VariableStart;
import com.github.andreasbraun5.thesis.grammarproperties.GrammarProperties;
import com.github.andreasbraun5.thesis.mylogger.WorkLog;
import com.github.andreasbraun5.thesis.resultcalculator.BestResultSamples;
import com.github.andreasbraun5.thesis.resultcalculator.Result;
import com.github.andreasbraun5.thesis.resultcalculator.ResultCalculator;
import com.github.andreasbraun5.thesis.util.Tuple;
import com.github.andreasbraun5.thesis.util.Util;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
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

        int countGeneratedGrammarsPerWord = 32;
        int countDifferentWords = 32;

        ResultCalculator resultCalculator = ResultCalculator.builder().
                countDifferentWords(countDifferentWords).
                countOfGrammarsToGeneratePerWord(countGeneratedGrammarsPerWord).build();
        GrammarProperties grammarProperties = generateGrammarPropertiesForTesting();
        grammarProperties.examConstraints.sizeOfWord = 5;

        /*GrammarGeneratorSettings settingsGrammarGeneratorDiceRollOnly = new GrammarGeneratorSettings(
                grammarProperties, "GrammarGeneratorDiceRollOnly");
        Tuple<Result, BestResultSamples> resultGrammarGeneratorDiceRollOnly = resultCalculator.buildResultWithGenerator(
                new GrammarGeneratorDiceRollOnly(settingsGrammarGeneratorDiceRollOnly),
                WorkLog.createFromWriter(new FileWriter(ThesisDirectory.LOGS.fileAsTxt(settingsGrammarGeneratorDiceRollOnly.name)))
        );
        Util.writeResultToTxtFile(resultGrammarGeneratorDiceRollOnly);


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

        GrammarGeneratorSettings settingsGrammarGeneratorSplitThenFill = new GrammarGeneratorSettings(
                grammarProperties, "GrammarGeneratorSplitThenFill");
        settingsGrammarGeneratorSplitThenFill.setMinValueCompoundVariablesAreAddedTo(1);
        settingsGrammarGeneratorSplitThenFill.setMaxValueCompoundVariablesAreAddedTo(1);
        Tuple<Result, BestResultSamples> resultGrammarGeneratorSplitThenFill = resultCalculator.buildResultWithGenerator(
                new GrammarGeneratorSplitThenFill(settingsGrammarGeneratorSplitThenFill),
                WorkLog.createFromWriter(new FileWriter(ThesisDirectory.LOGS.fileAsTxt(settingsGrammarGeneratorSplitThenFill.name)))
        );
        Util.writeResultToTxtFile(resultGrammarGeneratorSplitThenFill);
        */

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
        variables.add(new Variable("D"));
        variables.add(new Variable("E"));
        variables.add(new Variable("F"));
        variables.add(new Variable("G"));

        Set<Terminal> terminals = new HashSet<>();
        terminals.add(new Terminal("a"));
        terminals.add(new Terminal("b"));
        terminals.add( new Terminal( "c" ) );
        //terminals.add( new Terminal( "d" ) );
        //terminals.add( new Terminal( "e" ) );
        //terminals.add( new Terminal( "f" ) );
        return new GrammarProperties(new VariableStart("S"), variables, terminals);
    }
}