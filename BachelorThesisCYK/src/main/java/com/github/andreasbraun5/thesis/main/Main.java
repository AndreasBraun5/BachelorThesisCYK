package com.github.andreasbraun5.thesis.main;

import java.util.HashSet;
import java.util.Set;

import com.github.andreasbraun5.thesis.exception.GrammarSettingRuntimeException;
import com.github.andreasbraun5.thesis.generator.GeneratorGrammarDiceRollSettings;
import com.github.andreasbraun5.thesis.generatortest.TestGrammar;
import com.github.andreasbraun5.thesis.generatortest.TestGrammarResult;
import com.github.andreasbraun5.thesis.generatortest.TestMethod;
import com.github.andreasbraun5.thesis.grammar.GrammarProperties;
import com.github.andreasbraun5.thesis.grammar.Terminal;
import com.github.andreasbraun5.thesis.grammar.Variable;
import com.github.andreasbraun5.thesis.grammar.VariableStart;
import com.github.andreasbraun5.thesis.util.Util;

public class Main {
	// TODO: prepare overview of test data to inspect
	// TODO: CYK advanced, instead of storing X now you must save (X,k)
	// TODO: CYK tree
	// TODO: bias implementation, prefer one of the variables specifically, check it
	// TODO: implement that at least one cell can only be filled through combination of two "correct" cells, forced
	// combination of words of the right size.
	// TODO: Hast du eigentlich den Test implementiert, dass man (mind. eine) Zelle nur durch Kombination von den
	// “korrekten” anderen Zellen und eben nicht dadurch, dass man die beiden direkt darüber anschaut, bekommt?
	// --> modify cyk advanced and store how .... no
	// TODO: Idea StartVariable has no terminal
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
		int countGeneratedGrammarsPerWord = 100;
		int countDifferentWords = 10;
		// this boundary is relevant so that the JVM doesn't run out of memory while calculating one TestGrammarResult.
		if ( ( countGeneratedGrammarsPerWord * countDifferentWords ) > 70000 ) {
			throw new GrammarSettingRuntimeException( "Too many grammars would be generated. [ N !< 70000 ]" );
		}
		TestGrammar testGrammar1 = new TestGrammar( countGeneratedGrammarsPerWord, countDifferentWords );
		TestGrammarResult test1DiceRollResult = testGrammar1.testGeneratorGrammar(
				generatorGrammarDiceRollSettings, TestMethod.DICE );
		TestGrammarResult test2DiceRollBiasResult = testGrammar1.testGeneratorGrammar(
				generatorGrammarDiceRollSettings, TestMethod.DICEANDBIAS );

		/**
		 * 	Storing all the samples in a txt.
		 */
		Util.writeToFile( "filename", test1DiceRollResult, test2DiceRollBiasResult );
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
}
/**
 * Generate N grammars (N=100000) and then evaluate these regarding the different requirements.
 * Most general approaches that use no restrictions:
 * <p>
 * FIRST Approach starting from one word to N grammars:
 * 1) Randomly generate one word.
 * 2) Randomly generate grammars derived from it. Derived means using generatePartOfGrammarPropertiesFromWord
 * which is the indirect input to generateGrammar via the settings.
 * 3) Checking restrictions and producibility.
 * 3.1) Restrictions: Check the the grammar regarding its demanded restrictions, e.g. max vars per cell and so on.
 * Restrictions are PARAMETERS which still need to be identified and optimized later on.
 * r grammars do fulfill the restrictions property.
 * Maybe even use more fine grained structure of r --> more fine grained dropout rates.
 * 3.2) Producibility: Check the grammar if it can generate the word.
 * p grammars do fulfill the producibility property.
 * This will be checked with the CYK-algorithm.
 * 4) n valid grammars are the final result. n is element of [0, N] and n = r + p.
 * Usage of different success rates:
 * // TODO: more fine grained SR's?
 * - Success rate SR = n/N;
 * - Success rate of checking for the restrictions RSR = r/N;
 * - Success rate of checking for the producibility PSR = p/N;
 * - Conditional probability for one grammar of being a validGrammar and being able to generate the word. COND = RSR*PSR.
 * <p>
 * SECOND Approach starting from one grammar to one word:
 * 1.1) Randomly generate one grammar.
 * 1.2) TestGrammar for more restrictions regarding the grammar, e.g. maxVarCount and so on.
 * [ 1.3) Accept the grammar if all the restrictions are met, otherwise goto 1.1). In this approach it may be possible to
 * use even more restrictions, e.g. more this approach regarding specific restrictions ]
 * 3) Randomly generate words derived from it. Derived means using generatePartOfGrammarPropertiesFromGrammar
 * which is the indirect input to generateWord via the settings. One would maybe define properties for the word.
 * 4) Check for the words that can be be generated out of the grammar.
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