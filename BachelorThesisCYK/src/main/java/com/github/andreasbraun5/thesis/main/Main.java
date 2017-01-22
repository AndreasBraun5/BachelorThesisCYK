package com.github.andreasbraun5.thesis.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

import com.github.andreasbraun5.thesis.exception.GrammarSettingRuntimeException;
import com.github.andreasbraun5.thesis.generator.GeneratorGrammarDiceRollSettings;
import com.github.andreasbraun5.thesis.generatortest.TestGrammar;
import com.github.andreasbraun5.thesis.generatortest.TestGrammarResult;
import com.github.andreasbraun5.thesis.generatortest.TestMethods;
import com.github.andreasbraun5.thesis.grammar.GrammarProperties;
import com.github.andreasbraun5.thesis.grammar.Terminal;
import com.github.andreasbraun5.thesis.grammar.Variable;
import com.github.andreasbraun5.thesis.grammar.VariableStart;

public class Main {
	// TODO: prepare overview of test data to inspect
	// TODO: CYK advanced
	// TODO: CYK tree
	// TODO: bias implementation, prefer one of the variables specifically
	// TODO: implement that at least one cell can only be filled through combination of two "correct" cells, forced combination of words of the right size.
	public static void main(String[] args) {

		/**
		 * 	Generating the settings for the generatorTest.
		 * 	GrammarProperties = general settings.
		 * 	GeneratorGrammarDiceRollSettings = generator specific settings.
		 */
		GrammarProperties grammarProperties = generateGrammarPropertiesForTesting();
		GeneratorGrammarDiceRollSettings generatorGrammarDiceRollSettings =
				new GeneratorGrammarDiceRollSettings( grammarProperties );

		/**
		 * 	N = countOfGrammarsTogGenerate.
		 * 	Select the testing method.
		 * 	Comparability of the TestResults is given via using the same N and the same GrammarProperties.
		 */
		// It is recommended to use a high countDifferentWords. Word independent results are achieved.
		grammarProperties.sizeOfWord = 10; // All TestResults will be based on words of this size.
		grammarProperties.maxNumberOfVarsPerCell = 3; // Used for restrictions check.
		int countGeneratedGrammarsPerWord = 500;
		int countDifferentWords = 20;
		// this boundary is relevant so that the JVM doesn't run out of memory while calculating one TestGrammarResult.
		if ( ( countGeneratedGrammarsPerWord * countDifferentWords ) > 70000 ) {
			throw new GrammarSettingRuntimeException( "Too many grammars would be generated. [ N !< 70000 ]" );
		}
		TestGrammar testGrammar1 = new TestGrammar( countGeneratedGrammarsPerWord, countDifferentWords );
		TestGrammarResult test1DiceRollResult = testGrammar1.testGeneratorGrammar(
				generatorGrammarDiceRollSettings, TestMethods.DICE );
		TestGrammarResult test2DiceRollBiasResult = testGrammar1.testGeneratorGrammar(
				generatorGrammarDiceRollSettings, TestMethods.DICEANDBIAS );

		/**
		 * 	Storing the samples in a txt.
		 */
		writeToFile( test1DiceRollResult, test2DiceRollBiasResult );
	}

	public static GrammarProperties generateGrammarPropertiesForTesting() {
		Set<Variable> variables = new HashSet<>();
		variables.add( new Variable( "A" ) );
		variables.add( new Variable( "B" ) );
		variables.add( new Variable( "C" ) );
		Set<Terminal> terminals = new HashSet<>();
		terminals.add( new Terminal( "a" ) );
		terminals.add( new Terminal( "b" ) );
		terminals.add( new Terminal( "c" ) );
		terminals.add( new Terminal( "d" ) );
		terminals.add( new Terminal( "e" ) );
		terminals.add( new Terminal( "f" ) );
		/**
		 * Some settings for the beginning:
		 * variables.size() should be around 4, considering exam exam exercises.
		 * terminals.size() should be 2.
		 * sizeOfWord should be between 6 and 10.
		 * maxNumberOfVarsPerCell should be 3.
		 */
		GrammarProperties grammarProperties = new GrammarProperties( new VariableStart( "S" ),
																	 variables, terminals
		);
		grammarProperties.sizeOfWord = 10; // All TestResults will be based on words of this size.
		grammarProperties.maxNumberOfVarsPerCell = 2;
		return grammarProperties;
	}

	public static void writeToFile(TestGrammarResult... testGrammarResult) {
		try {
			File file = new File( "./filename.txt");
			file.getParentFile().mkdirs();
			PrintWriter out = new PrintWriter( file );
			for(TestGrammarResult aResult : testGrammarResult) {
				out.println( aResult );
				out.print( aResult.getGeneratorGrammarDiceRollSettings().grammarProperties );
				out.println( aResult.getTestGrammarSamples().toString() );
			}
			out.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
/**
 * Generate N grammars (N=100000) and then evaluate these regarding the different requirements.
 * Most general approaches that use no restrictions:
 * <p>
 * FIRST Approach starting from one word to N grammars:
 * 1) Randomly generate one word.
 * 2) Randomly generate grammars derived from it. Derived means using generatePartOfGrammarPropertiesFromWord
 * which is input to generateGrammar.
 * 3) Checking restrictions and producibility.
 * 3.1) Restrictions: Check the the grammar regarding its demanded restrictions, e.g. max vars per cell and so on.
 * Restrictions are PARAMETERS which still need to be identified and optimized later on.
 * r grammars don´t fulfill the restrictions property.
 * Maybe even use more fine grained structure of r --> more fine grained dropout rates.
 * 3.2) Producibility: Check the grammar if it can generate the word.
 * p grammars don´t fulfill the producibility property.
 * This will be checked with the CYK-algorithm.
 * 4) n valid grammars are the final result. n is element of [0, N]
 * Usage of different success rates:
 * // TODO: correct documentation
 * - Success rate SR = n/N;
 * - Dropout rate of checking for the restrictions DRR = r/N;
 * - Dropout rate of checking for the producibility DRP = p/N;
 * - one for fulfilling the different restrictions (Maybe even one for each one). step 2.1)-->2.2); SR1 = #validGrammars/#generatedGrammars
 * - one for finding the grammar that can generate the word. step 2.2)-->2.3); SR2 = 1/#validGrammars
 * - Conditional probability of being a validGrammar and being the one to be able to generate the word. COND1 = SR1*SR2
 * <p>
 * SECOND Approach starting from one grammar to one word:
 * 1.1) Randomly generate one grammar.
 * 1.2) TestGrammar for more restrictions regarding the grammar, e.g. maxVarCount and so on.
 * Restrictions = PARAMETERS which still need to be found.
 * 3) Randomly generate words derived from it. Derived means using generatePartOfGrammarPropertiesFromGrammar
 * which is input to generateWord.
 * 4) Check for the wor the words that can be be generated out of the grammar.
 * 5) One or more valid grammars are the final result.
 * <p>
 * <p>
 * THIRD Approach starting with the half of a word and half of grammar.
 * <p>
 * Input for word generation:
 * [GrammarProperties object] Parameters could be:
 * variables count, terminals count, sizeOfWord,
 * <p>
 * Understand why the grammar are broken, try to repair with hand.
 * How to improve a Grammar? Maybe deleting a right side.
 * <p>
 * [numberOfVars from 2 to 5, sizeOfAlphabet from 2 to 4, maxNumberOfVarsPerCell = 3]
 */