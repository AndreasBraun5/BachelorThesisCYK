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
import com.github.andreasbraun5.thesis.resultcalculator.Result;
import com.github.andreasbraun5.thesis.resultcalculator.ResultCalculator;
import com.github.andreasbraun5.thesis.util.Util;
import org.junit.Assert;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class Main {
    static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    private static ExecutorService executorService;

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

        executorService = Executors.newSingleThreadExecutor();
        try {

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

            ResultCalculator resultCalculator = ResultCalculator.builder().
                    countDifferentWords(countDifferentWords).
                    countOfGrammarsToGeneratePerWord(countGeneratedGrammarsPerWord).build();
            GrammarProperties grammarProperties = generateGrammarPropertiesForTesting();


            GrammarGeneratorSettings settingsGrammarGeneratorDiceRollVar1 = new GrammarGeneratorSettings(
                    grammarProperties, "GrammarGeneratorDiceRollVar1");
            Result resultGrammarGeneratorDiceRollVar1 = resultCalculator.buildResultWithGenerator(
                    new GrammarGeneratorDiceRollVar1(settingsGrammarGeneratorDiceRollVar1),
                    WorkLog.createFromWriter(new FileWriter(ThesisDirectory.LOGS.fileAsTxt(settingsGrammarGeneratorDiceRollVar1.name)))
            );
            Util.writeResultToTxtFile(resultGrammarGeneratorDiceRollVar1);

            GrammarGeneratorSettings settingsGrammarGeneratorDiceRollVar2 = new GrammarGeneratorSettings(
                    grammarProperties, "GrammarGeneratorDiceRollVar2");
            Result resultGrammarGeneratorDiceRollVar2 = resultCalculator.buildResultWithGenerator(
                    new GrammarGeneratorDiceRollVar2(settingsGrammarGeneratorDiceRollVar2),
                    WorkLog.createFromWriter(new FileWriter(ThesisDirectory.LOGS.fileAsTxt(settingsGrammarGeneratorDiceRollVar2.name)))
            );
            Util.writeResultToTxtFile(resultGrammarGeneratorDiceRollVar2);

            GrammarGeneratorSettings settingsGrammarGeneratorDiceRollOnly = new GrammarGeneratorSettings(
                    grammarProperties, "GrammarGeneratorDiceRollOnly");
            Result resultGrammarGeneratorDiceRollOnly = resultCalculator.buildResultWithGenerator(
                    new GrammarGeneratorDiceRollOnly(settingsGrammarGeneratorDiceRollOnly),
                    WorkLog.createFromWriter(new FileWriter(ThesisDirectory.LOGS.fileAsTxt(settingsGrammarGeneratorDiceRollOnly.name)))
            );
            Util.writeResultToTxtFile(resultGrammarGeneratorDiceRollOnly);

            GrammarGeneratorSettings settingsGrammarGeneratorSplitThenFill = new GrammarGeneratorSettings(
                    grammarProperties, "GrammarGeneratorSplitThenFill");
            settingsGrammarGeneratorSplitThenFill.setMinValueCompoundVariablesAreAddedTo(1);
            Result resultGrammarGeneratorSplitThenFill = resultCalculator.buildResultWithGenerator(
                    new GrammarGeneratorSplitThenFill(settingsGrammarGeneratorSplitThenFill),
                    WorkLog.createFromWriter(new FileWriter(ThesisDirectory.LOGS.fileAsTxt(settingsGrammarGeneratorSplitThenFill.name)))
            );
            Util.writeResultToTxtFile(resultGrammarGeneratorSplitThenFill);

            String exerciseStr = "start:S;\n" +
                    "rules:{\n" +
                    "E -> 1\n" +
                    "N -> 0\n" +
                    "A -> E C\n" +
                    "A -> N S'\n" +
                    "A -> 0\n" +
                    "B -> E S'\n" +
                    "B -> N D\n" +
                    "B -> 1\n" +
                    "S -> eps\n" +
                    "S -> E A\n" +
                    "S -> N B\n" +
                    "C -> A A\n" +
                    "S' -> E A\n" +
                    "S' -> N B\n" +
                    "D -> B B\n" +
                    "};\n" +
                    "word: 0 1 1 1 0 1 0 0;";

            ExerciseStringConverter exerciseStringConverter = new ExerciseStringConverter();
            Exercise exercise = exerciseStringConverter.fromString(exerciseStr);

            GrammarPyramidWrapper grammarPyramidWrapper = GrammarPyramidWrapper.builder().grammar(exercise.getGrammar())
                    .pyramid(new Pyramid(exercise.getWord())).build();
            grammarPyramidWrapper = CYK.calculateSetVAdvanced(grammarPyramidWrapper);

            ExerciseLatex exerciseLatex = new ExerciseLatex(exercise.getGrammar(), grammarPyramidWrapper.getPyramid());

            WriteToTexFile.writeToTexFile("exerciseLatex", exerciseLatex.toString());

            /*runCmd("pdflatex \"C:\\Users\\AndreasBraun\\Documents\\BachelorThesis\\B" +
                    "achelorThesisCYK\\exercise\\exerciseLatex.tex\" --output-directory=\"C:\\Users\\AndreasBraun\\Documents\\BachelorThesis\\B" +
                    "achelorThesisCYK\\exercise\"");*/

        } finally {
            executorService.shutdown();
        }
    }

    public static void runCmd(String cmd) throws IOException, ExecutionException, InterruptedException {
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