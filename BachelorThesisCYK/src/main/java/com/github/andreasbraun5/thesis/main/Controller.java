package com.github.andreasbraun5.thesis.main;

import com.github.andreasbraun5.thesis.antlr.ExerciseStringConverter;
import com.github.andreasbraun5.thesis.exercise.Exercise;
import com.github.andreasbraun5.thesis.generator.GrammarGeneratorSettings;
import com.github.andreasbraun5.thesis.generator.GrammarGeneratorSplitThenFill;
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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Controller {
    @FXML
    public TextArea modify = new TextArea();
    @FXML
    public TextArea selectFrom;

    @FXML
    public void initialise() throws IOException {
        // thesis init paths
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

    }

    public void createExercise(MouseEvent mouseEvent) throws InterruptedException, ExecutionException, IOException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        try {
            String exerciseStr = modify.getText();
            ExerciseStringConverter exerciseStringConverter = new ExerciseStringConverter();
            Exercise exercise = exerciseStringConverter.fromString(exerciseStr);

            GrammarPyramidWrapper grammarPyramidWrapper = GrammarPyramidWrapper.builder().grammar(exercise.getGrammar())
                    .pyramid(new Pyramid(exercise.getWord())).build();
            grammarPyramidWrapper = CYK.calculateSetVAdvanced(grammarPyramidWrapper);
            /*
            ExerciseLatex exerciseLatex = new ExerciseLatex(exercise.getGrammar(), grammarPyramidWrapper.getPyramid());
            WriteToTexFile.writeToTexFile("exerciseLatex", exerciseLatex.toString());

            Main.runCmd("pdflatex \"C:\\Users\\AndreasBraun\\Documents\\BachelorThesis\\B" +
                    "achelorThesisCYK\\exercise\\exerciseLatex.tex\" --output-directory=\"C:\\Users\\AndreasBraun\\Documents\\BachelorThesis\\B" +
                    "achelorThesisCYK\\exercise\"");*/
        } finally {
            executorService.shutdown();
        }

    }
}
