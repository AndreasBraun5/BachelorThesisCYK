package com.github.andreasbraun5.thesis.main;

import com.github.andreasbraun5.thesis.antlr.ExerciseStringConverter;
import com.github.andreasbraun5.thesis.exception.TreeLatexRuntimeException;
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
import com.github.andreasbraun5.thesis.pyramid.Cell;
import com.github.andreasbraun5.thesis.pyramid.GrammarPyramidWrapper;
import com.github.andreasbraun5.thesis.pyramid.Pyramid;
import com.github.andreasbraun5.thesis.resultcalculator.BestResultSamples;
import com.github.andreasbraun5.thesis.resultcalculator.ModifyExerciseSample;
import com.github.andreasbraun5.thesis.resultcalculator.Result;
import com.github.andreasbraun5.thesis.resultcalculator.ResultCalculator;
import com.github.andreasbraun5.thesis.util.Tuple;
import com.github.andreasbraun5.thesis.util.Word;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import lombok.Setter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
    public TextArea statusOutput;

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
        int countGeneratedGrammarsPerWord = 20;
        int countDifferentWords = 16;
        ResultCalculator resultCalculator = ResultCalculator.builder().
                countDifferentWords(countDifferentWords).
                countOfGrammarsToGeneratePerWord(countGeneratedGrammarsPerWord).build();

        GrammarProperties grammarProperties = new GrammarProperties(VariableStart.of("S"), getVariables(), getTerminals());
        // TODO why ?
        if (this.getWordLength() < 4) {
            statusOutput.setText(statusOutput.getText() + "\nWord length greater 4 needed.");
            return;
        }
        if (this.getWordLength() > 20) {
            statusOutput.setText(statusOutput.getText() + "\nWord length smaller 21 needed.");
            return;
        }
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
        statusOutput.setText(statusOutput.getText() + "\nExercise creation successful.");
    }

    public void processChanges(MouseEvent mouseEvent) {
        ModifyExerciseSample modifyExerciseSample;
        modifyExerciseSample = textToModifyExerciseSample(modify.getText());
        Word word = modifyExerciseSample.getPyramid().getWord();
        Grammar grammar = modifyExerciseSample.getGrammar();
        if (!terminalsMatch(word, grammar)) {
            statusOutput.setText(statusOutput.getText() + "\nTerminals of word and\ngrammar don't match.");
            throw new RuntimeException("\nTerminals of word and\\grammar don't match.");
        }
        statusOutput.setText(statusOutput.getText() + "\nProcessing changes successful.");
        modify.setText(modifyExerciseSample.toString());
    }

    public void createExercise(MouseEvent mouseEvent) throws InterruptedException, ExecutionException, IOException {
        try {
            processChanges(null);
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            try {
                ExerciseLatex exerciseLatex = textToExerciseLatex(modify.getText());
                WriteToTexFile.writeToTexFile("exerciseLatex", exerciseLatex.toString());
                String str = new File(ThesisDirectory.EXERCISE.path).getAbsolutePath();
                Main.runCmd(executorService, "pdflatex \"" + str + "\\exerciseLatex.tex\"" +
                        " --output-directory=\"" + str + "\"");
                statusOutput.setText(statusOutput.getText() + "\nExercise creation successful.");
            } catch (TreeLatexRuntimeException re) {
                statusOutput.setText(statusOutput.getText() + re.getMessage());
                statusOutput.setText(statusOutput.getText() + "\nExercise creation unsuccessful.");
                return;
            } finally {
                executorService.shutdown();
            }
        } catch (RuntimeException re) {
            return;
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
        // This throws an Exception if the terminals don't match.
        try {
            Exercise exercise = exerciseStringConverter.fromString(exerciseStr);
            GrammarPyramidWrapper grammarPyramidWrapper = GrammarPyramidWrapper.builder().grammar(exercise.getGrammar())
                    .pyramid(new Pyramid(exercise.getWord())).build();
            grammarPyramidWrapper = CYK.calculateSetVAdvanced(grammarPyramidWrapper);
            CheckRightCellCombinationsForcedResultWrapper temp =
                    GrammarValidityChecker.checkRightCellCombinationsForcedSimpleCells(grammarPyramidWrapper.getPyramid(),
                            grammarPyramidWrapper.getGrammar());
            if (!checkCountElementsPerCell(grammarPyramidWrapper.getPyramid().getCellsK())) {
                statusOutput.setText(statusOutput.getText() + "Warning:\nThere are more than 5 VarKs,\n" +
                        "in one Cell. Please choose\nanother one or modify it.");
            }
            ModifyExerciseSample modifyExerciseSample = ModifyExerciseSample.builder()
                    .grammar(grammarPyramidWrapper.getGrammar())
                    .pyramid(grammarPyramidWrapper.getPyramid())
                    .maxNumberOfVarsPerCellCount(GrammarValidityChecker.maxNumberOfVarsPerCellCount(grammarPyramidWrapper.getPyramid()))
                    .maxSumOfVarsInPyramidCount(GrammarValidityChecker.countSumOfVarsInPyramid(grammarPyramidWrapper.getPyramid()))
                    .rightCellCombinationsForcedCount(temp.getRightCellCombinationForcedCount())
                    .markedRightCellCombinationForced(temp.getMarkedRightCellCombinationForced())
                    .maxSumOfProductionsCount(GrammarValidityChecker.checkSumOfProductions(grammarPyramidWrapper.getGrammar()))
                    .build();
            return modifyExerciseSample;

        } catch (IllegalArgumentException iae) {
            statusOutput.setText(statusOutput.getText() + "\nGrammar input is malformed.");
        }
        return null;
    }

    private boolean checkCountElementsPerCell(Cell[][] cells) {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if (cells[i][j].getCellElements().size() > 5) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * All terminals from the word must be included in the grammar but not otherwise.
     */
    private boolean terminalsMatch(Word word, Grammar grammar) {
        Set<Terminal> wordTerms = new HashSet<>();
        Set<Terminal> grammarTerms = new HashSet<>();
        wordTerms.addAll(word.getTerminals());
        grammar.getProductionsAsList().forEach(production -> {
            if (production.getRightHandSideElement() instanceof Terminal) {
                grammarTerms.add((Terminal) production.getRightHandSideElement());
            }
        });
        return grammarTerms.containsAll(wordTerms);
    }
}
