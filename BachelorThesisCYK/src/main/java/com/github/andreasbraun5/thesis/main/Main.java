package com.github.andreasbraun5.thesis.main;

import java.util.HashSet;
import java.util.Set;

import com.github.andreasbraun5.thesis.generator.GeneratorGrammarDiceRollOnlySettings;
import com.github.andreasbraun5.thesis.generator.GeneratorWordDiceRoll;
import com.github.andreasbraun5.thesis.grammar.GrammarProperties;
import com.github.andreasbraun5.thesis.grammar.Terminal;
import com.github.andreasbraun5.thesis.grammar.Variable;
import com.github.andreasbraun5.thesis.grammar.VariableStart;
import com.github.andreasbraun5.thesis.generatortest.TestGrammar;
import com.github.andreasbraun5.thesis.generatortest.TestGrammarResult;

public class Main {

	public static void main(String[] args) {

		/**
		 * 	Generating the settings for the generatortest.
		 * 	GrammarProperties = general settings.
		 * 	GeneratorGrammarDiceRollSettings = generator specific settings.
		 */
		GrammarProperties grammarProperties = generateGrammarPropertiesForTesting();
		GeneratorGrammarDiceRollOnlySettings generatorGrammarDiceRollOnlySettings =
				new GeneratorGrammarDiceRollOnlySettings( grammarProperties );

		/**
		 *  Generating a random word.
		 */
		grammarProperties.sizeOfWord = 10; // All TestResults will be based on this sizeOfWord.
		// TODO check for succesrate being nearly 1 with maxVarPerCell = 4, and being nearly 0 for =3
		grammarProperties.maxNumberOfVarsPerCell = 2;
		GeneratorWordDiceRoll generatorWordDiceRoll = new GeneratorWordDiceRoll();
		String word = generatorWordDiceRoll.generateWord( grammarProperties ).toString();

		/**
		 * 	N = countOfGrammarsTogGenerate.
		 * 	Select the testing method.
		 * 	Comparability of the TestResults is given via using the same N and the same GrammarProperties.
		 */
		int countOfGrammarsToGenerate = 100000;
		TestGrammar testGrammar1 = new TestGrammar( countOfGrammarsToGenerate );
		TestGrammarResult test1DiceRollOnlyResult = testGrammar1.testGeneratorGrammarDiceRollOnly(
				generatorGrammarDiceRollOnlySettings );
		System.out.println( test1DiceRollOnlyResult.toString() );

		// deriving GrammarProperties from a word possible
		// deriving GrammarProperties from a grammar possible
		// Generating a random word possible
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
		 * variables.size() should be around 4, considering exam exam exercises.
		 * terminals.size() should be 2. TODO: not correctly set up till now
		 * sizeOfWord should be between 6 and 10. TODO: used for word generation only
		 * maxNumberOfVarsPerCell should be 3 TODO: not used up till now
		 */
		GrammarProperties grammarProperties = new GrammarProperties( new VariableStart( "S" ),
																	 variables, terminals
		);
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