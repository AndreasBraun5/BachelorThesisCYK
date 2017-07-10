package com.github.andreasbraun5.thesis.resultcalculator;

import com.github.andreasbraun5.thesis.util.Word;

import java.util.List;
import java.util.Map;

/**
 * Created by AndreasBraun on 10.07.2017.
 */
public class SuccessRateCalculator {

    public static SuccessRates calculateSuccessRates(Map<Word, List<ResultSample>> samples) {
        /*for ( Map.Entry<Word, List<ResultSample>> entry : chunkResultSamples.entrySet() ) {
            for ( ResultSample resultSample : entry.getValue() ) {
                countGeneratedGrammars++;
                if ( resultSample.isWordProducible() ) {
                    trueProducibilityCount++;
                }
                else {
                    falseProducibilityCount++;
                }
                if ( resultSample.isValid() ) {
                    trueCount++;
                }
                else {
                    falseCount++;
                }
            }
        }
        successRate = (double) trueCount / countGeneratedGrammars;
        successRateProducibility = (double) trueProducibilityCount / countGeneratedGrammars;
        successRatesExamConstraints.updateSuccessRatesExamConstraints( chunkResultSamples );
        successRatesGrammarConstraints.updateSuccessRatesGrammarRestrictions( chunkResultSamples );
        SuccessRates rates = new SuccessRates();*/
        return null;
    }

    public static SuccessRates flatten(List<SuccessRates> successRates) {
        return null;
    }

}
