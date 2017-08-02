package com.github.andreasbraun5.thesis.main;

import com.github.andreasbraun5.thesis.antlr.ExerciseStringConverter;
import com.github.andreasbraun5.thesis.exercise.Exercise;
import com.github.andreasbraun5.thesis.generator.GrammarGeneratorSettings;
import com.github.andreasbraun5.thesis.generator.GrammarGeneratorSplitThenFill;
import com.github.andreasbraun5.thesis.grammar.Grammar;
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
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            createNew(null);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void createNew(MouseEvent mouseEvent) throws IOException {
        int countGeneratedGrammarsPerWord = 10;
        int countDifferentWords = 10;
        ResultCalculator resultCalculator = ResultCalculator.builder().
                countDifferentWords(countDifferentWords).
                countOfGrammarsToGeneratePerWord(countGeneratedGrammarsPerWord).build();
        GrammarProperties grammarProperties = Main.generateGrammarPropertiesForTesting();
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
            Main.runCmd(executorService, "pdflatex \"C:\\Users\\AndreasBraun\\Documents\\BachelorThesis\\B" +
                    "achelorThesisCYK\\exercise\\exerciseLatex.tex\" --output-directory=\"C:\\Users\\AndreasBraun\\Documents\\BachelorThesis\\B" +
                    "achelorThesisCYK\\exercise\"");
        } finally {
            executorService.shutdown();
        }

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
