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

import java.io.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class CalculateBestSuccessRate {

    private enum Generators {
        DiceRollOnly,
        DiceRollVar1,
        DiceRollVar2,
        SplitThenFill,
        SplitAndFill
    }

    private static final int COUNT_WORDS = 32;
    private static final int COUNT_GRAMMARS_PER_WORD = 32;
    private static final int SAMPLE_SIZE = COUNT_WORDS * COUNT_GRAMMARS_PER_WORD;

    private static final char[] ALPHABET_LOWER_CASE = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final char[] ALPHABET_UPPER_CASE_MINUS_S = "ABCDEFGHIJKLMNOPQRTUVWXYZ".toCharArray();

    private static final int MIN_VARS = 1; // plus the Variable Start S
    private static final int MAX_VARS = 8; // plus the Variable Start S
    private static final int MIN_TERMS = 2;
    private static final int MAX_TERMS = 8;
    private static final int MIN_SIZE_WORD = 8;
    private static final int MAX_SIZE_WORD = 11;

    public static void main(String[] args) throws IOException {
        calculateMaxSuccessRate(Generators.DiceRollOnly);
        calculateMaxSuccessRate(Generators.DiceRollVar1);
        calculateMaxSuccessRate(Generators.DiceRollVar2);
        calculateMaxSuccessRate(Generators.SplitThenFill);
        calculateMaxSuccessRate(Generators.SplitAndFill);

        // 12.5s bei wordsize 20 mit 1024 wörter pro Result
        // bei wordSize 20 werden aber bereits 625 Results bei je 5 Algorithmen berechnet
        // 625 * 20 = 7813 zu je 5 Algos entspricht 130 min zu je 5 Algos
        //

    }

    public static void calculateMaxSuccessRate(Generators generatorType) throws IOException {
        GrammarProperties grammarProperties = createGrammarProperties(1, 2, 4);
        Result bestResult = startAlgorithms(generatorType, grammarProperties); // Default stuff
        for (int varCount = MIN_VARS; varCount < MAX_VARS; ++varCount) {
            for (int termCount = MIN_TERMS; termCount < MAX_TERMS; ++termCount) {
                for (int wordSize = MIN_SIZE_WORD; wordSize < MAX_SIZE_WORD; ++wordSize) {
                    grammarProperties = createGrammarProperties(varCount, termCount, wordSize);
                    Result tempResult = startAlgorithms(generatorType, grammarProperties);
                    if (tempResult.getSuccessRates().getSuccessRate() > bestResult.getSuccessRates().getSuccessRate()) {
                        bestResult = tempResult;
                    }
                }
            }
        }
        // Save the best Result for each type of generator in the folder successRates
        File test = new File(ThesisDirectory.SUCCESSRATES.path, bestResult.name + ".txt");
        try (PrintWriter out = new PrintWriter(test)) {
            out.println(bestResult);
            out.println(bestResult.getExampleResultSamples().toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static Result startAlgorithms(Generators generatorType, GrammarProperties grammarProperties) throws IOException {
        ResultCalculator resultCalculator = ResultCalculator.builder().
                countDifferentWords(COUNT_WORDS).
                countOfGrammarsToGeneratePerWord(COUNT_GRAMMARS_PER_WORD).build();
        GrammarGeneratorSettings settings = new GrammarGeneratorSettings(
                grammarProperties, generatorType.name());
        GrammarGeneratorSettings settings2 = new GrammarGeneratorSettings(
                grammarProperties, generatorType.name());
        settings2.setMinValueTerminalsAreAddedTo(1);
        settings2.setMaxValueTerminalsAreAddedTo(1);
        settings2.setMinValueCompoundVariablesAreAddedTo(1);
        settings2.setMaxValueCompoundVariablesAreAddedTo(1);
        if (Objects.equals(generatorType.name(), "DiceRollOnly")) {
            return resultCalculator.buildResultWithGenerator(
                    new GrammarGeneratorDiceRollOnly(settings),
                    WorkLog.createFromWriter(null)).x;
        } else if (Objects.equals(generatorType.name(), "DiceRollVar1")) {
            return resultCalculator.buildResultWithGenerator(
                    new GrammarGeneratorDiceRollVar1(settings),
                    WorkLog.createFromWriter(null)).x;
        } else if (Objects.equals(generatorType.name(), "DiceRollVar2")) {
            return resultCalculator.buildResultWithGenerator(
                    new GrammarGeneratorDiceRollVar2(settings),
                    WorkLog.createFromWriter(null)).x;
        } else if (Objects.equals(generatorType.name(), "SplitThenFill")) {
            return resultCalculator.buildResultWithGenerator(
                    new GrammarGeneratorSplitThenFill(settings2),
                    WorkLog.createFromWriter(null)).x;
        } else if (Objects.equals(generatorType.name(), "SplitAndFill")) {
            return resultCalculator.buildResultWithGenerator(
                    new GrammarGeneratorSplitAndFill(settings2),
                    WorkLog.createFromWriter(null)).x;
        } else {
            throw new RuntimeException("No correct type of Generator picked.");
        }
    }

    private static GrammarProperties createGrammarProperties(int varCount, int termCount, int wordSize) throws IOException {
        GrammarProperties grammarProperties = new GrammarProperties(new VariableStart("S"));
        grammarProperties.examConstraints.sizeOfWord = wordSize;
        Set<Variable> variables = new HashSet<>();
        {
            for (int i = 0; i <= varCount; i++) {
                variables.add(new Variable(String.valueOf(ALPHABET_UPPER_CASE_MINUS_S[i])));
            }
        }
        Set<Terminal> terminals = new HashSet<>();
        {
            for (int i = 0; i <= termCount; i++) {
                terminals.add(new Terminal(String.valueOf(ALPHABET_LOWER_CASE[i])));
            }
        }
        grammarProperties.variables.addAll(variables);
        grammarProperties.terminals.addAll(terminals);
        return grammarProperties;
    }
}
