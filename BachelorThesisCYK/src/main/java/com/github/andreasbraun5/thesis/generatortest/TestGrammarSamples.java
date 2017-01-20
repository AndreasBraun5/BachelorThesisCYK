package com.github.andreasbraun5.thesis.generatortest;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.github.andreasbraun5.thesis.grammar.Grammar;
import com.github.andreasbraun5.thesis.grammar.Variable;
import com.github.andreasbraun5.thesis.parser.CYK;
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
	private List<Boolean> sampleBooleanOverall;
	private List<Boolean> sampleBooleanProducibility;
	private List<Boolean> sampleBooleanRestrictions;
	private List<Integer> sampleMaxVarsPerCellSetV;

	public TestGrammarSamples(
			List<Grammar> sampleGrammars,
			List<String> sampleWords,
			List<Set<Variable>[][]> sampleSetVs,
			List<Boolean> sampleBooleanOverall,
			List<Boolean> sampleBooleanProducibility,
			List<Boolean> sampleBooleanRestrictions) {
		this.sampleGrammars = sampleGrammars;
		this.sampleWords = sampleWords;
		this.sampleSetVs = sampleSetVs;
		this.sampleBooleanOverall = sampleBooleanOverall;
		this.sampleBooleanProducibility = sampleBooleanProducibility;
		this.sampleBooleanRestrictions = sampleBooleanRestrictions;
		for ( int i = 0; i < sampleGrammars.size(); i++ ) {
			this.sampleMaxVarsPerCellSetV.add( Util.getMaxVarPerCellForSetV( sampleSetVs.get( i ) ) );
		}
	}

	public TestGrammarSamples(
			List<Grammar> sampleGrammars,
			List<String> sampleWords,
			List<Set<Variable>[][]> sampleSetVs,
			List<Boolean> sampleBooleanOverall,
			List<Boolean> sampleBooleanProducibility,
			List<Boolean> sampleBooleanRestrictions,
			List<Integer> sampleMaxVarsPerCellSetV) {
		this.sampleGrammars = sampleGrammars;
		this.sampleWords = sampleWords;
		this.sampleSetVs = sampleSetVs;
		this.sampleBooleanOverall = sampleBooleanOverall;
		this.sampleBooleanProducibility = sampleBooleanProducibility;
		this.sampleBooleanRestrictions = sampleBooleanRestrictions;
		this.sampleMaxVarsPerCellSetV = sampleMaxVarsPerCellSetV;
	}

	public static TestGrammarSamples calculateTestGrammarResult(
			int countOfGrammarsToGeneratePerWord,
			List<Grammar> grammars,
			List<String> wordsToGenerateSetVs,
			List<Set<Variable>[][]> setVs,
			List<Boolean> booleanOverall,
			List<Boolean> booleanProducibility,
			List<Boolean> booleanRestrictions
	) {
		int countSamplesToKeepPerWord = 10;
		int countSamplesAreStoredForWords = 10;
		if ( wordsToGenerateSetVs.size() < countSamplesAreStoredForWords ) {
			countSamplesAreStoredForWords = wordsToGenerateSetVs.size();
		}
		List<String> sampleWords = new ArrayList<>();
		List<Grammar> sampleGrammars = new ArrayList<>();
		List<Set<Variable>[][]> sampleSetVs = new ArrayList<>();
		List<Boolean> sampleBooleanOverall = new ArrayList<>();
		List<Boolean> sampleBooleanProducibility = new ArrayList<>();
		List<Boolean> sampleBooleanRestrictions = new ArrayList<>();
		List<Integer> sampleMaxVarsPerCellSetV = new ArrayList<>();
		for ( int i = 0; i < countSamplesAreStoredForWords; i++ ) {
			sampleWords.add( wordsToGenerateSetVs.get( i ) );
			for ( int j = 0; j < countSamplesToKeepPerWord; j++ ) {
				int index = i * countOfGrammarsToGeneratePerWord + j;
				sampleGrammars.add( grammars.get( index ) );
				sampleSetVs.add( setVs.get( index ) );
				sampleBooleanOverall.add( booleanOverall.get( index ) );
				sampleBooleanProducibility.add( booleanProducibility.get( index ) );
				sampleBooleanRestrictions.add( booleanRestrictions.get( index ) );
			}
		}
		for ( int i = 0; i < sampleGrammars.size(); i++ ) {
			sampleMaxVarsPerCellSetV.add( Util.getMaxVarPerCellForSetV( sampleSetVs.get( i ) ) );
		}
		return new TestGrammarSamples(
				sampleGrammars,
				sampleWords,
				sampleSetVs,
				sampleBooleanOverall,
				sampleBooleanProducibility,
				sampleBooleanRestrictions,
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

	public List<Boolean> getSampleBooleanOverall() {
		return sampleBooleanOverall;
	}

	public List<Boolean> getSampleBooleanProducibility() {
		return sampleBooleanProducibility;
	}

	public List<Boolean> getSampleBooleanRestrictions() {
		return sampleBooleanRestrictions;
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
						.append( "\nsampleSetVs=" ).append( CYK.getStringSetV( sampleSetVs.get( i ), "" ) )
						.append( "sampleBooleanOverall=" ).append( sampleBooleanOverall.get( i ) )
						.append( "\nsampleBooleanProducibility=" ).append( sampleBooleanProducibility.get( i ) )
						.append( "\nsampleBooleanRestrictions=" ).append( sampleBooleanRestrictions.get( i ) );
			}
		}
		return stringBuilder.toString();


	}
}
