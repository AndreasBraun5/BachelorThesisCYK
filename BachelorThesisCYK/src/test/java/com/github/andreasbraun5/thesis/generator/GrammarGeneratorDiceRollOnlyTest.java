package com.github.andreasbraun5.thesis.generator;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.github.andreasbraun5.thesis.grammar.GrammarProperties;
import com.github.andreasbraun5.thesis.grammar.GrammarWrapper;
import com.github.andreasbraun5.thesis.grammar.Terminal;
import com.github.andreasbraun5.thesis.grammar.Variable;
import com.github.andreasbraun5.thesis.grammar.VariableStart;

/**
 * Created by Andreas Braun on 06.01.2017.
 * https://github.com/AndreasBraun5/
 */
public class GrammarGeneratorDiceRollOnlyTest {

	@Test
	public void generateGrammar() {
		// TODO: This test is kinda senseless
		System.out.println( "" );
		System.out.println( "############################" );
		System.out.println( "ResultCalculator GrammarGeneratorDiceRollOnly: generateGrammarWrapper" );
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
		GrammarProperties grammarProperties = new GrammarProperties( new VariableStart( "S" ) );
		grammarProperties.addTerminals( terminals ).addVariables( variables );
		grammarProperties.variableStart = new VariableStart( "S" );
		grammarProperties.grammarPropertiesGrammarRestrictions.setMaxNumberOfVarsPerCell( 3 );
		System.out.println( grammarProperties );
		GrammarGeneratorSettingsDiceRoll generatorGrammarDiceRollSettings = new GrammarGeneratorSettingsDiceRoll(
				grammarProperties );
		GrammarGeneratorDiceRollOnly generatorGrammarDiceRollOnly = new GrammarGeneratorDiceRollOnly(
				generatorGrammarDiceRollSettings );
		GrammarWrapper grammarWrapper = GrammarWrapper.buildGrammarWrapper();
		grammarWrapper = generatorGrammarDiceRollOnly.generateGrammarWrapper(grammarWrapper);
		System.out.println( grammarWrapper.getGrammar() );
		GrammarProperties grammarProperties2 = GrammarProperties.generatePartOfGrammarPropertiesFromGrammar( grammarWrapper.getGrammar() );
		Assert.assertEquals(
				"terminals size is not the same",
				grammarProperties.terminals.size(),
				grammarProperties2.terminals.size()
		);
		Assert.assertEquals(
				"variables size is not the same",
				grammarProperties.variables.size(),
				grammarProperties2.variables.size()
		);
		Assert.assertEquals(
				"variable start is not the same",
				grammarProperties.variableStart,
				grammarProperties.variableStart
		);
	}
}
