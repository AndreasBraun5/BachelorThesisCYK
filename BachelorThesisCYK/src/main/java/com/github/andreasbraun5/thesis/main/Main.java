package com.github.andreasbraun5.thesis.main;

import com.github.andreasbraun5.thesis.exception.GrammarException;
import com.github.andreasbraun5.thesis.exception.WordException;
import com.github.andreasbraun5.thesis.generator.GeneratorGrammar;
import com.github.andreasbraun5.thesis.generator.GeneratorGrammarDiceRollOnly;
import com.github.andreasbraun5.thesis.generator.GeneratorWordDiceRoll;
import com.github.andreasbraun5.thesis.grammar.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws WordException, GrammarException {
        // Defining variables and terminals that are used
        Set<Variable> variables = new HashSet<>();
        variables.add(new Variable("A"));
        //variables.add(new Variable("B"));
        //variables.add(new Variable("C"));
        Set<Terminal> terminals = new HashSet<>();
        terminals.add(new Terminal("a"));
        terminals.add(new Terminal("b"));
        //terminals.add(new Terminal("c"));
        //terminals.add(new Terminal("d"));
        //terminals.add(new Terminal("e"));
        //terminals.add(new Terminal("f"));

        // Generating a word, that will be used for generating a grammar.
        GeneratorWordDiceRoll generatorWordDiceRoll = new GeneratorWordDiceRoll();
        String word = generatorWordDiceRoll.generateWord(terminals, 10).toString();
        System.out.println(word);

        // Defining the GrammarProperties
        // numberOfVars from 2 to 5
        // sizeOfAlphabet from 2 to 4
        // maxNumberOfVarsPerCell = 3
        GrammarProperties grammarProperties = GrammarProperties.
                generatePartOfGrammarPropertiesFromWord(new VariableStart("S"), word)
                .addTerminals(terminals).addVariables(variables);
        grammarProperties.maxNumberOfVarsPerCell = 3;

        //GeneratorGrammarDiceRollOnly generatorGrammarDiceRollOnly = new GeneratorGrammarDiceRollOnly();

        // TODO Always gives back wrong
        // /*
        // Generating a grammar.
        GeneratorGrammarDiceRollOnly generatorGrammarDiceRollOnly = new GeneratorGrammarDiceRollOnly();
        int max = 10000;
        Grammar[] grammars = new Grammar[max];
        int trueCount = 0;
        int falseCount = 0;
        // 500000 Grammars works fine
        for(int i = 0; i<max; i++) {
            grammars[i] = generatorGrammarDiceRollOnly.generateGrammar(grammarProperties);
            if(GrammarIntegrityChecker.checkIntegrity(grammars[i], word)) {
                trueCount++;
            } else {
                falseCount++;
            }
        }
        System.out.println("True: " + trueCount + " and False: " + falseCount);
        // Checking if the word is in the found grammar
        //System.out.println(GrammarIntegrityChecker.checkIntegrity(grammar, grammarProperties, word));
        // */
    }
}
// Generating one million grammars takes too long ^^
/**
 * Generate N grammars (N=100000) and then evaluate these regarding the different requirements.
 * Most general approaches that use no restrictions:
 *
 * FIRST Approach starting from one word to N grammars:
 * 1) Randomly generate one word.
 * 2) Randomly generate grammars derived from it. Derived means using generatePartOfGrammarPropertiesFromWord
 *          which is input to generateGrammar.
 * 3) Checking restrictions and producibility.
 * 3.1) Restrictions: Check the the grammar regarding its demanded restrictions, e.g. max vars per cell and so on.
 *          Restrictions are PARAMETERS which still need to be identified and optimized later on.
 *          r grammars don´t fulfill the restrictions property.
 *          Maybe even use more fine grained structure of r --> more fine grained dropout rates.
 * 3.2) Producibility: Check the grammar if it can generate the word.
 *          p grammars don´t fulfill the producibility property.
 *          This will be checked with the CYK-algorithm.
 * 4) n valid grammars are the final result. n is element of [0, N]
 * Usage of different success rates:
 *  - Success rate SR = n/N;
 *  - Dropout rate of checking for the restrictions DRR = r/N;
 *  - Dropout rate of checking for the producibility DRP = p/N;
 *  - one for fulfilling the different restrictions (Maybe even one for each one). step 2.1)-->2.2); SR1 = #validGrammars/#generatedGrammars
 *  - one for finding the grammar that can generate the word. step 2.2)-->2.3); SR2 = 1/#validGrammars
 *  - Conditional probability of being a validGrammar and being the one to be able to generate the word. COND1 = SR1*SR2
 *
 * SECOND Approach starting from one grammar to one word:
 * 1.1) Randomly generate one grammar.
 * 1.2) Test for more restrictions regarding the grammar, e.g. maxVarCount and so on.
 *          Restrictions = PARAMETERS which still need to be found.
 * 3) Randomly generate words derived from it. Derived means using generatePartOfGrammarPropertiesFromGrammar
 *          which is input to generateWord.
 * 4) Check for the wor the words that can be be generated out of the grammar.
 * 5) One or more valid grammars are the final result.
 *
 *
 * THIRD Approach starting with the half of a word and half of grammar.
 *
 * Input for word generation:
 *      [GrammarProperties object] Parameters could be:
 *          variables count, terminals count, sizeOfWord,
 *
 * Understand why the grammar are broken, try to repair with hand.
 * How to improve a Grammar? Maybe deleting a right side.
 *
 *
 */