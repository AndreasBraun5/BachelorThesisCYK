package com.github.andreasbraun5.thesis.main;

import com.github.andreasbraun5.thesis.generator.GrammarGeneratorSettings;
import com.github.andreasbraun5.thesis.generator.GrammarGeneratorSplitThenFill;
import com.github.andreasbraun5.thesis.grammarproperties.GrammarProperties;
import com.github.andreasbraun5.thesis.mylogger.WorkLog;
import com.github.andreasbraun5.thesis.resultcalculator.BestResultSamples;
import com.github.andreasbraun5.thesis.resultcalculator.Result;
import com.github.andreasbraun5.thesis.resultcalculator.ResultCalculator;
import com.github.andreasbraun5.thesis.util.Tuple;
import com.github.andreasbraun5.thesis.util.Util;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;

public class Controller {

    @FXML
    public TextArea modify;
    @FXML
    public TextArea selectFrom;

    public Controller() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    }

    public void init() throws IOException {
        int countGeneratedGrammarsPerWord = 100;
        int countDifferentWords = 100;
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


}
