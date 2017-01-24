package com.github.andreasbraun5.thesis.generatortest;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.github.andreasbraun5.thesis.grammar.Grammar;
import com.github.andreasbraun5.thesis.grammar.Variable;
import com.github.andreasbraun5.thesis.util.Util;

/**
 * Created by Andreas Braun on 19.01.2017.
 * For each test a maximum of 100 samples are kept for further inspection. For each of the first 10 words of sample
 * words, its related properties are stored.
 */
public class TestGrammarSamples {
	private List<Grammar> sampleGrammars;
	private List<String> sampleWords;
	private List<Set<Variable>[][]> sampleSetVs;
	private List<Boolean> sampleIsOverall;
	private List<Boolean> sampleIsProducibility;
	private List<Boolean> sampleIsRestrictions;
	private List<Integer> sampleMaxVarsPerCellSetV;

	public TestGrammarSamples(
			List<Grammar> sampleGrammars,
			List<String> sampleWords,
			List<Set<Variable>[][]> sampleSetVs,
			List<Boolean> sampleIsOverall,
			List<Boolean> sampleIsProducibility,
			List<Boolean> sampleIsRestrictions) {
		this.sampleGrammars = sampleGrammars;
		this.sampleWords = sampleWords;
		this.sampleSetVs = sampleSetVs;
		this.sampleIsOverall = sampleIsOverall;
		this.sampleIsProducibility = sampleIsProducibility;
		this.sampleIsRestrictions = sampleIsRestrictions;
		for ( int i = 0; i < sampleGrammars.size(); i++ ) {
			this.sampleMaxVarsPerCellSetV.add( Util.getMaxVarPerCellForSetV( sampleSetVs.get( i ) ) );
		}
	}

	public TestGrammarSamples(
			List<Grammar> sampleGrammars,
			List<String> sampleWords,
			List<Set<Variable>[][]> sampleSetVs,
			List<Boolean> sampleIsOverall,
			List<Boolean> sampleIsProducibility,
			List<Boolean> sampleIsRestrictions,
			List<Integer> sampleMaxVarsPerCellSetV) {
		this.sampleGrammars = sampleGrammars;
		this.sampleWords = sampleWords;
		this.sampleSetVs = sampleSetVs;
		this.sampleIsOverall = sampleIsOverall;
		this.sampleIsProducibility = sampleIsProducibility;
		this.sampleIsRestrictions = sampleIsRestrictions;
		this.sampleMaxVarsPerCellSetV = sampleMaxVarsPerCellSetV;
	}

	public static TestGrammarSamples calculateTestGrammarResult(
			int countOfGrammarsToGeneratePerWord,
			List<Grammar> grammars,
			List<String> wordsToGenerateSetVs,
			List<Set<Variable>[][]> setVs,
			List<Boolean> isOverall,
			List<Boolean> isProducibility,
			List<Boolean> isRestrictions
	) {
		int countSamplesToKeepPerWord = 10;
		int countSamplesAreStoredForWords = 10;
		if ( wordsToGenerateSetVs.size() < countSamplesAreStoredForWords ) {
			countSamplesAreStoredForWords = wordsToGenerateSetVs.size();
		}
		List<String> sampleWords = new ArrayList<>();
		List<Grammar> sampleGrammars = new ArrayList<>();
		List<Set<Variable>[][]> sampleSetVs = new ArrayList<>();
		List<Boolean> sampleIsOverall = new ArrayList<>();
		List<Boolean> sampleIsProducibility = new ArrayList<>();
		List<Boolean> sampleIsRestrictions = new ArrayList<>();
		List<Integer> sampleMaxVarsPerCellSetV = new ArrayList<>();
		for ( int i = 0; i < countSamplesAreStoredForWords; i++ ) {
			sampleWords.add( wordsToGenerateSetVs.get( i ) );
			for ( int j = 0; j < countSamplesToKeepPerWord; j++ ) {
				int index = i * countOfGrammarsToGeneratePerWord + j;
				sampleGrammars.add( grammars.get( index ) );
				sampleSetVs.add( setVs.get( index ) );
				sampleIsOverall.add( isOverall.get( index ) );
				sampleIsProducibility.add( isProducibility.get( index ) );
				sampleIsRestrictions.add( isRestrictions.get( index ) );
			}
		}
		for ( int i = 0; i < sampleGrammars.size(); i++ ) {
			sampleMaxVarsPerCellSetV.add( Util.getMaxVarPerCellForSetV( sampleSetVs.get( i ) ) );
		}
		return new TestGrammarSamples(
				sampleGrammars,
				sampleWords,
				sampleSetVs,
				sampleIsOverall,
				sampleIsProducibility,
				sampleIsRestrictions,
				sampleMaxVarsPerCellSetV
		);
	}

	public List<String> getSampleWords() {
		return sampleWords;
	}

	public List<Grammar> getSampleGrammars() {
		return sampleGrammars;
	}

	public List<Set<Variable>[][]> getSampleSetVs() {
		return sampleSetVs;
	}

	public List<Boolean> getSampleIsOverall() {
		return sampleIsOverall;
	}

	public List<Boolean> getSampleIsProducibility() {
		return sampleIsProducibility;
	}

	public List<Boolean> getSampleIsRestrictions() {
		return sampleIsRestrictions;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder( "\n\n\nTestGrammarSamples{" );
		for ( int j = 0; j < sampleWords.size(); j++ ) {
			for ( int i = 0; i < sampleGrammars.size(); i++ ) {
				stringBuilder.append( "\n\n############################################################\n" +
											  "############################################################" )
						.append( "\nsampleGrammars=" ).append( sampleGrammars.get( i ).toString() )
						.append( "\nsampleWords=" ).append( sampleWords.get( j ) )
						.append( "\nsampleMaxVarsPerCellSetV=" ).append( sampleMaxVarsPerCellSetV.get( i ) )
						.append( "\nsampleSetVs=" ).append( Util.getSetVAsStringForPrinting(
						sampleSetVs.get( i ),
						""
				) )
						.append( "sampleIsOverall=" ).append( sampleIsOverall.get( i ) )
						.append( "\nsampleIsProducibility=" ).append( sampleIsProducibility.get( i ) )
						.append( "\nsampleIsRestrictions=" ).append( sampleIsRestrictions.get( i ) );
			}
		}
		return stringBuilder.toString();


	}
}
