package com.github.andreasbraun5.thesis.main;

import java.util.HashSet;
import java.util.Set;

import com.github.andreasbraun5.thesis.exception.GrammarSettingRuntimeException;
import com.github.andreasbraun5.thesis.generator.GrammarGeneratorDiceRollBias;
import com.github.andreasbraun5.thesis.generator.GrammarGeneratorDiceRollOnly;
import com.github.andreasbraun5.thesis.generator.GrammarGeneratorSettingsDiceRoll;
import com.github.andreasbraun5.thesis.grammar.GrammarProperties;
import com.github.andreasbraun5.thesis.grammar.Terminal;
import com.github.andreasbraun5.thesis.grammar.Variable;
import com.github.andreasbraun5.thesis.grammar.VariableStart;
import com.github.andreasbraun5.thesis.resultcalculator.Result;
import com.github.andreasbraun5.thesis.resultcalculator.ResultCalculator;
import com.github.andreasbraun5.thesis.util.Util;

public class Main {
	// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! SetV is in reality an upper triangular matrix !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	// TODO Note: The favouritism can be set for the starting variable specifically.
	// TODO Note: Maybe use the fraction of CountVars and countTerminals.
	// TODO: CYK tree
	// TODO: Write Test for removeUselessProductions.
	// TODO: write test for rightCellCombinationsForced
	// TODO: Implement Wim's algorithm from meeting 6!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	// TODO: all checks are done on the simpleSetV
	// TODO: DONE? Only keep producing and reachable rhse's in the grammar. Also see script in TI.
	//
	// TODO: See [Duda, Pattern Classification] chapter 8.5 Recognition with strings. In more Detail:
	//			8.5.2 Edit Distance(p. 418): Definition of similarity or difference between two strings. Deletion,
	//				insertion and exchange operations with different weighting possible. In general this is used for
	//				string matching with errors. So to say more error robust classification or recognition of spoken
	// 				words and and hand written words.
	// TODO: See [Duda, Pattern Classification] chapter 8.6 Grammatical Methods. In more Detail:
	//			8.6.2 Recognition Using Grammars (p. 426): Bottom-Up Parsing = CKY, starting from the leaves;
	// 				Top-Down, starting from root node, "with some specified criteria that guide the choice of which
	//				rule to apply. Such criteria could include beginning the parse ...";
	// TODO: See [Duda, Pattern Classification] chapter 8.7 Grammatical Inference. In more Detail:
	//			8.7 "In many applications, the grammar is designed by hand. ... It is important to learn a grammar from
	//				example sentences [ for our case this would only be the wanted or validity==true samples ]
	// 				{ Guess of myself: So to find some kind of meta grammar that describes grammars we seek }
	//				https://en.wikipedia.org/wiki/Grammar_induction
	//				See algorithm 5: Grammatical Inference (Overview).
	//				https://en.wikipedia.org/wiki/Grammar_induction
	// TODO: use wrapper classes for holden its properties and checking if true and so on.
	public static void main(String[] args) {
		/**
		 * 	Generating the settings for the generatorTest.
		 * 	GrammarProperties = general settings, the same for all settings of one run.
		 * 	GrammarGeneratorSettingsDiceRoll = generator specific settings.
		 */
		/**
		 * 	N = countOfGrammarsToGenerate.
		 * 	Select the testing method.
		 * 	Comparability of the TestResults is given via using the same N and the same GrammarProperties.
		 */
		// It is recommended to use a high countDifferentWords. Word independent results are achieved.
		int countGeneratedGrammarsPerWord = 150;
		int countDifferentWords = 100;
		// this boundary is relevant so that the JVM doesn't run out of memory on my computer while calculating one Result.
		if ( ( countGeneratedGrammarsPerWord * countDifferentWords ) > 70000 ) {
			throw new GrammarSettingRuntimeException( "Too many grammars would be generated. [ N !< 70000 ]" );
		}
		ResultCalculator resultCalculator = ResultCalculator.buildResultCalculator().
				setCountDifferentWords( countDifferentWords ).
				setCountOfGrammarsToGeneratePerWord( countGeneratedGrammarsPerWord );

		/*GrammarProperties grammarProperties1 = generateGrammarPropertiesForTesting();
		GrammarGeneratorSettingsDiceRoll settings1 = new GrammarGeneratorSettingsDiceRoll( grammarProperties1 );
		Result result1 = resultCalculator.buildResultFromGenerator(
				new GrammarGeneratorDiceRollOnly( settings1 ) );

		GrammarProperties grammarProperties2 = generateGrammarPropertiesForTesting();
		grammarProperties2.grammarPropertiesGrammarRestrictions.setMaxNumberOfVarsPerCell( 2 );
		GrammarGeneratorSettingsDiceRoll settings2 = new GrammarGeneratorSettingsDiceRoll( grammarProperties2 );
		Result result2 = resultCalculator.buildResultFromGenerator(
				new GrammarGeneratorDiceRollOnly( settings2 ) );
		*/

		GrammarProperties grammarProperties3 = generateGrammarPropertiesForTesting();
		GrammarGeneratorSettingsDiceRoll settings3 = new GrammarGeneratorSettingsDiceRoll( grammarProperties3 );
		settings3.setMaxValueCompoundVariablesAreAddedTo( 2 );
		settings3.setMaxValueTerminalsAreAddedTo( 2 );
		Result result3 = resultCalculator.buildResultFromGenerator(
				new GrammarGeneratorDiceRollOnly( settings3 ) );

		GrammarProperties grammarProperties4 = generateGrammarPropertiesForTesting();
		GrammarGeneratorSettingsDiceRoll settings4 = new GrammarGeneratorSettingsDiceRoll( grammarProperties4 );
		settings4.setMaxValueTerminalsAreAddedTo( 2 );
		settings4.setMaxValueCompoundVariablesAreAddedTo( 2 );
		int[] favouritism = { 4, 2, 1, 1 };
		settings4.setFavouritism( favouritism );
		Result result4 = resultCalculator.buildResultFromGenerator(
				new GrammarGeneratorDiceRollBias( settings4 ) );

		GrammarProperties grammarProperties5 = generateGrammarPropertiesForTesting();
		grammarProperties5.addTerminals( new Terminal( "c" ), new Terminal( "d" ) );
		GrammarGeneratorSettingsDiceRoll settings5 = new GrammarGeneratorSettingsDiceRoll( grammarProperties5 );
		settings5.setMaxValueTerminalsAreAddedTo( 2 );
		settings5.setMaxValueCompoundVariablesAreAddedTo( 2 );
		Result result5 = resultCalculator.buildResultFromGenerator(
				new GrammarGeneratorDiceRollOnly( settings5 ) );

		/**
		 * 	Storing all the results in a txt.
		 */
		Util.writeToFile( //result1, result2,
						  result3, result4, result5
		);
	}

	public static GrammarProperties generateGrammarPropertiesForTesting() {
		Set<Variable> variables = new HashSet<>();
		variables.add( new Variable( "A" ) );
		variables.add( new Variable( "B" ) );
		variables.add( new Variable( "C" ) );
		Set<Terminal> terminals = new HashSet<>();
		terminals.add( new Terminal( "a" ) );
		terminals.add( new Terminal( "b" ) );
		//terminals.add( new Terminal( "c" ) );
		//terminals.add( new Terminal( "d" ) );
		//terminals.add( new Terminal( "e" ) );
		//terminals.add( new Terminal( "f" ) );
		/**
		 * Some settings for the beginning:
		 * variables.size() should be around 4, considering exam exercises.
		 * terminals.size() should be 2.
		 * Used default setting:
		 * grammarPropertiesGrammarRestrictions: sizeOfWord = 10 // All TestResults will be based on words of this size
		 * grammarPropertiesGrammarRestrictions: maxNumberOfVarsPerCell = 3 // The CYK simple pyramid must contain less than 4 vars in cone cell
		 * grammarPropertiesExamConstraints: minRightCellCombinationsForced = 1
		 * grammarPropertiesExamConstraints: countSumOfProductions = 10; // approximate maximum value taken from the exam exercises
		 * grammarPropertiesExamConstraints: minRightCellCombinationsForced = 1 countSumOfVarsInPyramid = 50; // approximate maximum value taken from the exam exercises
		 */
		GrammarProperties grammarProperties = new GrammarProperties( new VariableStart( "S" ), variables, terminals );
		grammarProperties.grammarPropertiesGrammarRestrictions.setMaxNumberOfVarsPerCell( 3 ); //Set like this
		return grammarProperties;
	}
}