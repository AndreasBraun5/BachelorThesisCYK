package com.github.andreasbraun5.thesis.generator;

import com.github.andreasbraun5.thesis.exception.GrammarSettingException;
import com.github.andreasbraun5.thesis.grammar.GrammarProperties;

/**
 * Created by Andreas Braun on 15.01.2017.
 */

/**
 * By default each terminal will be distributed to at least one production.
 * By default each variableCompound can be a rightHandSideElement of none or all variables.
 * GrammarProperties are the most basic settings for a generator, and
 */
public class GeneratorGrammarDiceRollSettings extends GeneratorGrammarSettings {
	// TODO: different biases for dice rolling
	public final GrammarProperties grammarProperties;
	private int minValueCompoundVariablesAreAddedTo; // default is 0
	private int minValueTerminalsAreAddedTo; // default is 1
	private int maxValueCompoundVariablesAreAddedTo; // default is to all variables would be possible
	private int maxValueTerminalsAreAddedTo; // default is to all variables would be possible

	public GeneratorGrammarDiceRollSettings(GrammarProperties grammarProperties) {
		super(grammarProperties);
		this.grammarProperties = grammarProperties;
		minValueTerminalsAreAddedTo = 1;
		minValueCompoundVariablesAreAddedTo = 0;
		maxValueCompoundVariablesAreAddedTo = grammarProperties.variables.size();
		maxValueTerminalsAreAddedTo = grammarProperties.variables.size();
	}

	public int getMinValueCompoundVariablesAreAddedTo() {
		return minValueCompoundVariablesAreAddedTo;
	}

	public void setMinValueCompoundVariablesAreAddedTo(int minValueCompoundVariablesAreAddedTo) {
		if ( minValueCompoundVariablesAreAddedTo > maxValueCompoundVariablesAreAddedTo ) {
			throw new GrammarSettingException(
					"minValueCompoundVariablesAreAddedTo is bigger than  maxValueCompoundVariablesAreAddedTo." );
		}
		this.minValueCompoundVariablesAreAddedTo = minValueCompoundVariablesAreAddedTo;
	}

	public int getMinValueTerminalsAreAddedTo() {
		return minValueTerminalsAreAddedTo;
	}

	public void setMinValueTerminalsAreAddedTo(int minValueTerminalsAreAddedTo) {
		if ( minValueTerminalsAreAddedTo > maxValueTerminalsAreAddedTo ) {
			throw new GrammarSettingException( "minValueTerminalsAreAddedTo is bigger than maxValueTerminalsAreAddedTo." );
		}
		this.minValueTerminalsAreAddedTo = minValueTerminalsAreAddedTo;
	}

	public int getMaxValueCompoundVariablesAreAddedTo() {
		return maxValueCompoundVariablesAreAddedTo;
	}

	public void setMaxValueCompoundVariablesAreAddedTo(int maxValueCompoundVariablesAreAddedTo) {
		if ( maxValueCompoundVariablesAreAddedTo > grammarProperties.variables.size() ) {
			throw new GrammarSettingException( "maxValueCompoundVariablesAreAddedTo is bigger than variables.size()." );
		}
		this.maxValueCompoundVariablesAreAddedTo = maxValueCompoundVariablesAreAddedTo;
	}

	public int getMaxValueTerminalsAreAddedTo() {
		return maxValueTerminalsAreAddedTo;
	}

	public void setMaxValueTerminalsAreAddedTo(int maxValueTerminalsAreAddedTo) {
		if ( maxValueTerminalsAreAddedTo > grammarProperties.variables.size() ) {
			throw new GrammarSettingException( "maxValueTerminalsAreAddedTo is bigger than variables.size()." );
		}
		this.maxValueTerminalsAreAddedTo = maxValueTerminalsAreAddedTo;
	}
}
