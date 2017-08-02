package com.github.andreasbraun5.thesis.main;

import com.github.andreasbraun5.thesis.antlr.ExerciseStringConverter;
import com.github.andreasbraun5.thesis.exercise.Exercise;
import com.github.andreasbraun5.thesis.generator.GrammarGeneratorSettings;
import com.github.andreasbraun5.thesis.generator.GrammarGeneratorSplitThenFill;
import com.github.andreasbraun5.thesis.grammar.Grammar;
import com.github.andreasbraun5.thesis.grammar.Terminal;
import com.github.andreasbraun5.thesis.grammar.Variable;
import com.github.andreasbraun5.thesis.grammar.VariableStart;
import com.github.andreasbraun5.thesis.grammarproperties.GrammarProperties;
import com.github.andreasbraun5.thesis.grammarvalididtychecker.CheckRightCellCombinationsForcedResultWrapper;
import com.github.andreasbraun5.thesis.grammarvalididtychecker.GrammarValidityChecker;
import com.github.andreasbraun5.thesis.latex.ExerciseLatex;
import com.github.andreasbraun5.thesis.latex.WriteToTexFile;
import com.github.andreasbraun5.thesis.mylogger.WorkLog;
import com.github.andreasbraun5.thesis.parser.CYK;
import com.github.andreasbraun5.thesis.pyramid.CellSimple;
import com.github.andreasbraun5.thesis.pyramid.GrammarPyramidWrapper;
import com.github.andreasbraun5.thesis.pyramid.Pyramid;
import com.github.andreasbraun5.thesis.resultcalculator.*;
import com.github.andreasbraun5.thesis.util.Tuple;
import com.github.andreasbraun5.thesis.util.Util;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import lombok.Setter;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Controller implements Initializable {

    @Setter
    private Thesis thesis;

    @FXML
    public TextArea modify;
    @FXML
    public TextArea selectFrom;
    @FXML
    public TextArea wordLength;
    @FXML
    public TextArea terminals;
    @FXML
    public TextArea variables;

    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            initWordLength();
            initTerminals();
            initVariables();
            createNew(null);
            initModify();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void initTerminals() {
        terminals.setText("a b");
    }

    private void initWordLength() {
        wordLength.setText("10");
    }

    private void initVariables() {
        variables.setText("A B C");
    }

    private Set<Variable> getVariables() {
        String str = variables.getText();
        Set<Variable> vars = new HashSet<>();
        String[] varsStr = str.split(" ");
        for (String string : varsStr) {
            vars.add(Variable.of(string));
        }
        return vars;
    }

    private Set<Terminal> getTerminals() {
        String str = terminals.getText();
        Set<Terminal> terms = new HashSet<>();
        String[] termsStr = str.split(" ");
        for (String string : termsStr) {
            terms.add(Terminal.of(string));
        }
        return terms;
    }

    private int getWordLength() {
        String str = wordLength.getText();
        return Integer.parseInt(str);
    }

    public void createNew(MouseEvent mouseEvent) throws IOException {
        int countGeneratedGrammarsPerWord = 10;
        int countDifferentWords = 50;
        ResultCalculator resultCalculator = ResultCalculator.builder().
                countDifferentWords(countDifferentWords).
                countOfGrammarsToGeneratePerWord(countGeneratedGrammarsPerWord).build();

        GrammarProperties grammarProperties = new GrammarProperties(VariableStart.of("S"), getVariables(), getTerminals());
        grammarProperties.examConstraints.sizeOfWord = this.getWordLength();

        GrammarGeneratorSettings settingsGrammarGeneratorSplitThenFill = new GrammarGeneratorSettings(
                grammarProperties, "GrammarGeneratorSplitThenFill");
        settingsGrammarGeneratorSplitThenFill.setMinValueCompoundVariablesAreAddedTo(1);
        settingsGrammarGeneratorSplitThenFill.setMaxValueCompoundVariablesAreAddedTo(1);
        Tuple<Result, BestResultSamples> resultGrammarGeneratorSplitThenFill = resultCalculator.buildResultWithGenerator(
                new GrammarGeneratorSplitThenFill(settingsGrammarGeneratorSplitThenFill),
                WorkLog.createFromWriter(new FileWriter(ThesisDirectory.LOGS.fileAsTxt(settingsGrammarGeneratorSplitThenFill.name)))
        );
        this.selectFrom.setText(resultGrammarGeneratorSplitThenFill.y.toString());
    }

    public void processChanges(MouseEvent mouseEvent) {
        ModifyExerciseSample modifyExerciseSample = textToModifyExerciseSample(modify.getText());
        modify.setText(modifyExerciseSample.toString());
    }

    public void createExercise(MouseEvent mouseEvent) throws InterruptedException, ExecutionException, IOException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        try {
            ExerciseLatex exerciseLatex = textToExerciseLatex(modify.getText());
            WriteToTexFile.writeToTexFile("exerciseLatex", exerciseLatex.toString());
            String str = new File(ThesisDirectory.EXERCISE.path).getAbsolutePath();
            Main.runCmd(executorService, "pdflatex \"" + str + "\\exerciseLatex.tex\"" +
                    " --output-directory=\"" + str + "\"");
        } finally {
            executorService.shutdown();
        }

    }

    private void initModify() {
        modify.setText("start:S;\n" +
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
                "word: 0 1 1 1 0 1 0 0;");
    }

    private ExerciseLatex textToExerciseLatex(String exerciseStr) {
        ExerciseStringConverter exerciseStringConverter = new ExerciseStringConverter();
        Exercise exercise = exerciseStringConverter.fromString(exerciseStr);
        GrammarPyramidWrapper grammarPyramidWrapper = GrammarPyramidWrapper.builder().grammar(exercise.getGrammar())
                .pyramid(new Pyramid(exercise.getWord())).build();
        grammarPyramidWrapper = CYK.calculateSetVAdvanced(grammarPyramidWrapper);
        ExerciseLatex exerciseLatex = new ExerciseLatex(exercise.getGrammar(), grammarPyramidWrapper.getPyramid());
        return exerciseLatex;
    }

    private ModifyExerciseSample textToModifyExerciseSample(String exerciseStr) {
        ExerciseStringConverter exerciseStringConverter = new ExerciseStringConverter();
        Exercise exercise = exerciseStringConverter.fromString(exerciseStr);
        GrammarPyramidWrapper grammarPyramidWrapper = GrammarPyramidWrapper.builder().grammar(exercise.getGrammar())
                .pyramid(new Pyramid(exercise.getWord())).build();
        grammarPyramidWrapper = CYK.calculateSetVAdvanced(grammarPyramidWrapper);
        CheckRightCellCombinationsForcedResultWrapper temp =
                GrammarValidityChecker.checkRightCellCombinationsForcedSimpleCells(grammarPyramidWrapper.getPyramid(),
                        grammarPyramidWrapper.getGrammar());
        ModifyExerciseSample modifyExerciseSample = ModifyExerciseSample.builder()
                .grammar(grammarPyramidWrapper.getGrammar())
                .pyramid(grammarPyramidWrapper.getPyramid())
                .maxNumberOfVarsPerCellCount(GrammarValidityChecker.maxNumberOfVarsPerCellCount(grammarPyramidWrapper.getPyramid()))
                .maxSumOfVarsInPyramidCount(GrammarValidityChecker.maxNumberOfVarsPerCellCount(grammarPyramidWrapper.getPyramid()))
                .rightCellCombinationsForcedCount(temp.getRightCellCombinationForcedCount())
                .markedRightCellCombinationForced(temp.getMarkedRightCellCombinationForced())
                .maxSumOfProductionsCount(GrammarValidityChecker.maxNumberOfVarsPerCellCount(grammarPyramidWrapper.getPyramid()))
                .build();
        return modifyExerciseSample;
    }
}
