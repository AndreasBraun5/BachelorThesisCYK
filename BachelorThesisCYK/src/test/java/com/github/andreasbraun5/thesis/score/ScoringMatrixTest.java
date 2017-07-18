package com.github.andreasbraun5.thesis.score;

import com.github.andreasbraun5.thesis.generator.GrammarGeneratorSettings;
import com.github.andreasbraun5.thesis.resultcalculator.ResultSample;
import com.github.andreasbraun5.thesis.util.GrammarPropertiesUtil;
import com.github.andreasbraun5.thesis.util.SS12Exercise;
import com.github.andreasbraun5.thesis.util.SS13Exercise;
import com.github.andreasbraun5.thesis.util.TiScriptExercise;
import org.junit.Test;

/**
 * Created by AndreasBraun on 18.07.2017.
 */
public class ScoringMatrixTest {
    @Test
    public void scoreResultSample() {
        System.out.println("");
        System.out.println("############################");
        System.out.println("ScoringMatrixTest scoreResultSample: Scoring the available exercises.");
        GrammarGeneratorSettings grammarGeneratorSettingsSS12 = new GrammarGeneratorSettings(
                GrammarPropertiesUtil.describeGrammar(SS12Exercise.SS12_GRAMMAR), "GrammarGeneratorDiceRollOnlyWithLog.txt");
        ResultSample resultSampleSS12 = new ResultSample(SS12Exercise.SS12_GRAMMAR_PYRAMID_WRAPPER, grammarGeneratorSettingsSS12);

        GrammarGeneratorSettings grammarGeneratorSettingsSS13 = new GrammarGeneratorSettings(
                GrammarPropertiesUtil.describeGrammar(SS13Exercise.SS13_GRAMMAR), "GrammarGeneratorDiceRollOnlyWithLog.txt");
        ResultSample resultSampleSS13 = new ResultSample(SS13Exercise.SS13_GRAMMAR_PYRAMID_WRAPPER, grammarGeneratorSettingsSS13);

        GrammarGeneratorSettings grammarGeneratorSettingsTIScript = new GrammarGeneratorSettings(
                GrammarPropertiesUtil.describeGrammar(TiScriptExercise.SCRIPT_GRAMMAR), "GrammarGeneratorDiceRollOnlyWithLog.txt");
        ResultSample resultSampleScript = new ResultSample(TiScriptExercise.SCRIPT_GRAMMAR_PYRAMID_WRAPPER, grammarGeneratorSettingsTIScript);

        System.out.println(ScoringMatrix.scoreResultSample(resultSampleSS12));
        System.out.println(ScoringMatrix.scoreResultSample(resultSampleSS13));
        System.out.println(ScoringMatrix.scoreResultSample(resultSampleScript));
    }

}