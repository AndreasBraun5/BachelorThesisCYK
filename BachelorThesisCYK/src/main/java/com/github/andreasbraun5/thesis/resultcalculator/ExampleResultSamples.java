package com.github.andreasbraun5.thesis.resultcalculator;

import com.github.andreasbraun5.thesis.util.Word;
import lombok.Getter;

import java.util.*;

/**
 * Created by Andreas Braun on 19.01.2017.
 * For each test a maximum of 100 samples are kept for further inspection, if possible 50 valid and 50 invalid grammars
 * are stored.
 * It is done this ways because of the overridden method toString.
 */
@Getter
public class ExampleResultSamples {

	private final List<ResultSample> exampleRepresentativeExamples;

	private static final int COUNT_OF_VALID_GRAMMARS_TO_KEEP_PER_WORD = 5;
	private static final int COUNT_OF_INVALID_GRAMMARS_TO_KEEP_PER_WORD = 5;
	private static final int COUNT_OF_GRAMMARS_TO_KEEP_PER_WORD = 10;


	public ExampleResultSamples(Map<Word, List<ResultSample>> testGrammarSamples) {
		this.exampleRepresentativeExamples = calculateRepresentativeExamples( testGrammarSamples );
	}

	private List<ResultSample> calculateRepresentativeExamples(Map<Word, List<ResultSample>> testGrammarSamples) {
		List<ResultSample> tempRepresentativeResultSamples = new ArrayList<>();
		for ( Map.Entry<Word, List<ResultSample>> entry : testGrammarSamples.entrySet() ) {
			int tempCountValidGrammarsPerWord = 0;
			int tempCountInvalidGrammarsPerWord = 0;
			int tempCountGrammarsPerWord = 0;
			for ( ResultSample resultSample : entry.getValue() ) {
				if ( resultSample.isValid() && tempCountValidGrammarsPerWord < COUNT_OF_VALID_GRAMMARS_TO_KEEP_PER_WORD
						&& tempCountGrammarsPerWord < COUNT_OF_GRAMMARS_TO_KEEP_PER_WORD ) {
					tempRepresentativeResultSamples.add( resultSample );
					tempCountValidGrammarsPerWord++;
					tempCountGrammarsPerWord++;
				}
				if ( !resultSample.isValid() && tempCountInvalidGrammarsPerWord < COUNT_OF_INVALID_GRAMMARS_TO_KEEP_PER_WORD
						&& tempCountGrammarsPerWord < COUNT_OF_GRAMMARS_TO_KEEP_PER_WORD ) {
					tempRepresentativeResultSamples.add( resultSample );
					tempCountInvalidGrammarsPerWord++;
					tempCountGrammarsPerWord++;
				}
			}
		}
		tempRepresentativeResultSamples = sortListExamples( tempRepresentativeResultSamples );
		return tempRepresentativeResultSamples;
	}

	private List<ResultSample> sortListExamples(List<ResultSample> representativeResultSamples) {
		Collections.sort( representativeResultSamples, new Comparator<ResultSample>() {
			@Override
			public int compare(ResultSample first, ResultSample second) {
				if ( first.isValid() == second.isValid() ) {
					return 0;
				}
				if ( first.isValid() ) {
					return -1;
				}
				return +1;
			}
		} );
		return representativeResultSamples;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder( "\n\n\nRepresentativeResultSamples{" );
		for ( ResultSample resultSample : this.exampleRepresentativeExamples) {
			stringBuilder.append( "\n\n############################################################\n" +
										  "############################################################" )
                    .append(resultSample.toString());
		}
		return stringBuilder.toString();
	}
}
