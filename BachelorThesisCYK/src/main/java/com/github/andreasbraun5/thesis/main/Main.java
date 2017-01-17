package com.github.andreasbraun5.thesis.main;

import com.github.andreasbraun5.thesis.generator.GeneratorGrammarDiceRollOnly;
import com.github.andreasbraun5.thesis.generator.GeneratorGrammarDiceRollSettings;
import com.github.andreasbraun5.thesis.generator.GeneratorWordDiceRoll;
import com.github.andreasbraun5.thesis.grammar.*;

import java.util.HashSet;
import java.util.Set;

public class Main {

	public static void main(String[] args) {

		// Defining variables and terminals that are used for the tests.
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

		GrammarProperties grammarProperties = new GrammarProperties( new VariableStart( "S" ),
																	 variables, terminals
		);
		grammarProperties.sizeOfWord = 10;

		GeneratorGrammarDiceRollSettings generatorGrammarDiceRollSettings = new GeneratorGrammarDiceRollSettings(
				grammarProperties );

		int countOfGrammarsTogGenerate = 5000;
		Test test = new Test( countOfGrammarsTogGenerate, generatorGrammarDiceRollSettings );
		test.testGeneratorGrammarDiceRollOnly();


		// Generating a random word with length 10.
		GeneratorWordDiceRoll generatorWordDiceRoll = new GeneratorWordDiceRoll();
		String word = generatorWordDiceRoll.generateWord( terminals, 10 ).toString();
		System.out.println( word );

		// Derive the GrammarProperties from
		GrammarProperties grammarPropertiesExample = GrammarProperties.
				generatePartOfGrammarPropertiesFromWord( new VariableStart( "S" ), word )
				.addTerminals( terminals ).addVariables( variables );
		grammarProperties.maxNumberOfVarsPerCell = 3;


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
 * 1.2) Test for more restrictions regarding the grammar, e.g. maxVarCount and so on.
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