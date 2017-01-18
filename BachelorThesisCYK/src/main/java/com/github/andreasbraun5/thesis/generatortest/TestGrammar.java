package com.github.andreasbraun5.thesis.generatortest;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.github.andreasbraun5.thesis.generator.GeneratorGrammarDiceRoll;
import com.github.andreasbraun5.thesis.generator.GeneratorGrammarDiceRollSettings;
import com.github.andreasbraun5.thesis.generator.GeneratorWordDiceRoll;
import com.github.andreasbraun5.thesis.grammar.Grammar;
import com.github.andreasbraun5.thesis.grammar.GrammarProperties;
import com.github.andreasbraun5.thesis.grammar.GrammarValidityChecker;
import com.github.andreasbraun5.thesis.grammar.Variable;
import com.github.andreasbraun5.thesis.parser.CYK;

/**
 * Created by Andreas Braun on 15.01.2017.
 * Each instance of TestGrammar should use the same countOfGrammarsToGenerate that the results are comparable.
 */
public class TestGrammar {

	private int countOfGrammarsToGenerate;

	public TestGrammar(int countOfGrammarsToGenerate) {
		this.countOfGrammarsToGenerate = countOfGrammarsToGenerate;
	}

	public TestGrammarResult testGeneratorGrammarDiceRollOnly(GeneratorGrammarDiceRollSettings generatorGrammarDiceRollSettings) {

		GrammarProperties tempGrammarProperties = generatorGrammarDiceRollSettings.grammarProperties;
		long startTime = System.currentTimeMillis();
		//	Initialising the specific generatorTest with its settings.
		GeneratorGrammarDiceRoll generatorGrammarDiceRoll =
				new GeneratorGrammarDiceRoll( generatorGrammarDiceRollSettings );

		// Generate a random word that is used to decide whether the Grammar is true or false.
		GeneratorWordDiceRoll generatorWordDiceRoll = new GeneratorWordDiceRoll();
		String word = generatorWordDiceRoll.generateWord( tempGrammarProperties )
				.toString();

		// looping to generate the TestGrammarResult instance
		List<Grammar> grammars = new ArrayList<>();
		List<Set<Variable>[][]> setVs = new ArrayList<>();
		List<Boolean> booleanProducibility = new ArrayList<>();
		List<Boolean> booleanRestrictions = new ArrayList<>();
		List<Boolean> booleanOverall = new ArrayList<>();
		for ( int i = 0; i < countOfGrammarsToGenerate; i++ ) {
			grammars.add( generatorGrammarDiceRoll.generateGrammar() );
			setVs.add( CYK.calculateSetV( grammars.get( i ), word ) );
			boolean producibility = GrammarValidityChecker.checkProducibilityCYK(
					setVs.get( i ),
					grammars.get( i ),
					tempGrammarProperties
			);
			// TODO: up till now only maxVarPerCell is checked
			boolean restrictions = GrammarValidityChecker.checkGrammarRestrictions(
					tempGrammarProperties, setVs.get( i ) );
			if ( producibility ) {
				booleanProducibility.add( Boolean.TRUE );
			}
			else {
				booleanProducibility.add( Boolean.FALSE );
			}
			if ( restrictions ) {
				booleanRestrictions.add( Boolean.TRUE );
			}
			else {
				booleanRestrictions.add( Boolean.FALSE );
			}
			if ( restrictions && producibility ) {
				booleanOverall.add( Boolean.TRUE );
			}
			else {
				booleanOverall.add( Boolean.FALSE );
			}
		}
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		return new TestGrammarResult(
				countOfGrammarsToGenerate,
				totalTime,
				grammars,
				setVs,
				booleanOverall,
				booleanProducibility,
				booleanRestrictions
		);
	}
}
