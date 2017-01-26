package com.github.andreasbraun5.thesis.generatortest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Created by Andreas Braun on 19.01.2017.
 * For each test a maximum of 100 samples are kept for further inspection, if possible 50 valid and 50 invalid grammars
 * are stored.
 * It is done this ways because of the overridden method toString.
 */
public class TestGrammarRepresentativeExamples {

	private List<TestGrammarSample> testGrammarRepresentativeExamples;

	private static int countOfValidGrammarsToKeepPerWord = 5;
	private static int countOfInvalidGrammarsToKeepPerWord = 5;
	private static int countOfGramarsToKeepPerWord = 10;


	public TestGrammarRepresentativeExamples(Map<String, List<TestGrammarSample>> testGrammarSamples) {
		this.testGrammarRepresentativeExamples = calculateRepresentativeExamples( testGrammarSamples );
	}

	private List<TestGrammarSample> calculateRepresentativeExamples(Map<String, List<TestGrammarSample>> testGrammarSamples) {
		List<TestGrammarSample> tempRepresentativeTestGrammarSamples = new ArrayList<>();
		for ( Map.Entry<String, List<TestGrammarSample>> entry : testGrammarSamples.entrySet() ) {
			int tempCountValidGrammarsPerWord = 0;
			int tempCountInvalidGrammarsPerWord = 0;
			int tempCountGrammarsPerWord = 0;
			for ( TestGrammarSample testGrammarSample : entry.getValue() ) {
				if ( testGrammarSample.isValidity() && tempCountValidGrammarsPerWord < countOfValidGrammarsToKeepPerWord
						&& tempCountGrammarsPerWord < countOfGramarsToKeepPerWord ) {
					tempRepresentativeTestGrammarSamples.add( testGrammarSample );
					tempCountValidGrammarsPerWord++;
					tempCountGrammarsPerWord++;
				}
				if ( !testGrammarSample.isValidity() && tempCountInvalidGrammarsPerWord < countOfInvalidGrammarsToKeepPerWord
						&& tempCountGrammarsPerWord < countOfGramarsToKeepPerWord ) {
					tempRepresentativeTestGrammarSamples.add( testGrammarSample );
					tempCountInvalidGrammarsPerWord++;
					tempCountGrammarsPerWord++;
				}
			}
		}
		tempRepresentativeTestGrammarSamples = sortListExamples( tempRepresentativeTestGrammarSamples );
		return tempRepresentativeTestGrammarSamples;
	}

	private List<TestGrammarSample> sortListExamples(List<TestGrammarSample> representativeTestGrammarSamples) {
		Collections.sort( representativeTestGrammarSamples, new Comparator<TestGrammarSample>() {
			@Override
			public int compare(TestGrammarSample first, TestGrammarSample second) {
				if ( first.isValidity() == second.isValidity() ) {
					return 0;
				}
				if ( first.isValidity() ) {
					return -1;
				}
				return +1;
			}
		} );
		return representativeTestGrammarSamples;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder( "\n\n\nTestGrammarRepresentativeExamples{" );
		for ( TestGrammarSample testGrammarSample : this.testGrammarRepresentativeExamples ) {
			stringBuilder.append( "\n\n############################################################\n" +
										  "############################################################" )
					.append( "\nsampleGrammars=" ).append( testGrammarSample.toString() );
		}

		return stringBuilder.toString();
	}

	public List<TestGrammarSample> getTestGrammarRepresentativeExamples() {
		return testGrammarRepresentativeExamples;
	}
}
