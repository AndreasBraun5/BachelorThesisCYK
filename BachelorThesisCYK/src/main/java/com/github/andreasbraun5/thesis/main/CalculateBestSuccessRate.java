package com.github.andreasbraun5.thesis.main;

import com.github.andreasbraun5.thesis.generator.GrammarGeneratorDiceRollVar1;
import com.github.andreasbraun5.thesis.generator.GrammarGeneratorSettings;
import com.github.andreasbraun5.thesis.grammar.Terminal;
import com.github.andreasbraun5.thesis.grammar.Variable;
import com.github.andreasbraun5.thesis.grammar.VariableStart;
import com.github.andreasbraun5.thesis.grammarproperties.GrammarProperties;
import com.github.andreasbraun5.thesis.mylogger.WorkLog;
import com.github.andreasbraun5.thesis.resultcalculator.BestResultSamples;
import com.github.andreasbraun5.thesis.resultcalculator.Result;
import com.github.andreasbraun5.thesis.resultcalculator.ResultCalculator;
import com.github.andreasbraun5.thesis.resultcalculator.ResultSample;
import com.github.andreasbraun5.thesis.util.Tuple;
import com.sun.org.apache.xml.internal.security.algorithms.JCEMapper;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static com.github.andreasbraun5.thesis.main.Main.generateGrammarPropertiesForTesting;

public class CalculateBestSuccessRate {

    private static final int COUNT_WORDS = 10;
    private static final int COUNT_GRAMMARS_PER_WORD = 10;
    private static final int SAMPLE_SIZE = COUNT_WORDS * COUNT_GRAMMARS_PER_WORD;

    private static final char[] ALPHABET_LOWER_CASE = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final char[] ALPHABET_UPPER_CASE_MINUS_S = "ABCDEFGHIJKLMNOPQRTUVWXYZ".toCharArray();

    public static void main(String[] args) {

    }


    public static void calculateMaxSuccessRate(int minVars, int maxVars,
                                               int minTerms, int maxTerms,
                                               int minSizeWord, int maxSizeWord) throws IOException {
        Result bestResult = calculateMaxSuccessRateVar1(3, 2, 10); // Default stuff
        for (int varCount = minVars; varCount <= maxVars; ++varCount) {
            for (int termCount = minTerms; termCount <= maxTerms; ++termCount) {
                for (int wordSize = minSizeWord; wordSize <= maxSizeWord; ++wordSize) {
                    Result tempResult = calculateMaxSuccessRateVar1(varCount, termCount, wordSize);
                    if (tempResult.getSuccessRates().getSuccessRate() > bestResult.getSuccessRates().getSuccessRate()) {
                        bestResult = tempResult;
                    }
                }
            }
        }

    }

    public static Result calculateMaxSuccessRateVar1(int varCount, int termCount, int wordSize) throws IOException {
        ResultCalculator resultCalculator = ResultCalculator.builder().
                countDifferentWords(COUNT_WORDS).
                countOfGrammarsToGeneratePerWord(COUNT_GRAMMARS_PER_WORD).build();
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
            for (int i = 0; i <= varCount; i++) {
                terminals.add(new Terminal(String.valueOf(ALPHABET_LOWER_CASE[i])));
            }
        }
        grammarProperties.variables.addAll(variables);
        grammarProperties.terminals.addAll(terminals);

        GrammarGeneratorSettings settingsGrammarGeneratorDiceRollVar1 = new GrammarGeneratorSettings(
                grammarProperties, "GrammarGeneratorDiceRollVar1");
        Tuple<Result, BestResultSamples> resultGrammarGeneratorDiceRollVar1 = resultCalculator.buildResultWithGenerator(
                new GrammarGeneratorDiceRollVar1(settingsGrammarGeneratorDiceRollVar1),
                WorkLog.createFromWriter(new FileWriter(ThesisDirectory.LOGS.fileAsTxt(settingsGrammarGeneratorDiceRollVar1.name)))
        );

        return resultGrammarGeneratorDiceRollVar1.x;
    }


}
