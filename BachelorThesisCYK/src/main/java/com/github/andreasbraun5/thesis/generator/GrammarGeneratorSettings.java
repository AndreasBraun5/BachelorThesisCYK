package com.github.andreasbraun5.thesis.generator;

import com.github.andreasbraun5.thesis.exception.GrammarSettingRuntimeException;
import com.github.andreasbraun5.thesis.grammarproperties.GrammarProperties;

/**
 * Created by AndreasBraun on 01.06.2017.
 */
public class GrammarGeneratorSettings {

    public final String name;
    public final GrammarProperties grammarProperties; //
    private int minValueCompoundVariablesAreAddedTo = 0; // default is 0
    private int minValueTerminalsAreAddedTo = 1; // default is 1
    private int maxValueCompoundVariablesAreAddedTo; // default is to all variables would be possible
    private int maxValueTerminalsAreAddedTo; // default is to all variables would be possible

    public GrammarGeneratorSettings(GrammarProperties grammarProperties, String name) {
        this.name = name;
        this.grammarProperties = grammarProperties;
        maxValueCompoundVariablesAreAddedTo = grammarProperties.variables.size();
        maxValueTerminalsAreAddedTo = grammarProperties.variables.size();
    }

    public int getMinValueCompoundVariablesAreAddedTo() {
        return minValueCompoundVariablesAreAddedTo;
    }

    public void setMinValueCompoundVariablesAreAddedTo(int minValueCompoundVariablesAreAddedTo) {
        if ( minValueCompoundVariablesAreAddedTo > maxValueCompoundVariablesAreAddedTo ) {
            throw new GrammarSettingRuntimeException(
                    "minValueCompoundVariablesAreAddedTo is bigger than  maxValueCompoundVariablesAreAddedTo." );
        }
        this.minValueCompoundVariablesAreAddedTo = minValueCompoundVariablesAreAddedTo;
    }

    public int getMinValueTerminalsAreAddedTo() {
        return minValueTerminalsAreAddedTo;
    }

    public void setMinValueTerminalsAreAddedTo(int minValueTerminalsAreAddedTo) {
        if ( minValueTerminalsAreAddedTo == 0 ) {
            throw new GrammarSettingRuntimeException( "minValueTerminalsAreAddedTo must be at least one." );
        }
        if ( minValueTerminalsAreAddedTo > maxValueTerminalsAreAddedTo ) {
            throw new GrammarSettingRuntimeException(
                    "minValueTerminalsAreAddedTo is bigger than maxValueTerminalsAreAddedTo." );
        }

        this.minValueTerminalsAreAddedTo = minValueTerminalsAreAddedTo;
    }

    public int getMaxValueCompoundVariablesAreAddedTo() {
        return maxValueCompoundVariablesAreAddedTo;
    }

    public void setMaxValueCompoundVariablesAreAddedTo(int maxValueCompoundVariablesAreAddedTo) {
        if ( maxValueCompoundVariablesAreAddedTo > grammarProperties.variables.size() ) {
            throw new GrammarSettingRuntimeException(
                    "maxValueCompoundVariablesAreAddedTo is bigger than variables.size()." );
        }
        this.maxValueCompoundVariablesAreAddedTo = maxValueCompoundVariablesAreAddedTo;
    }

    public int getMaxValueTerminalsAreAddedTo() {
        return maxValueTerminalsAreAddedTo;
    }

    public void setMaxValueTerminalsAreAddedTo(int maxValueTerminalsAreAddedTo) {
        if ( maxValueTerminalsAreAddedTo > grammarProperties.variables.size() ) {
            throw new GrammarSettingRuntimeException( "maxValueTerminalsAreAddedTo is bigger than variables.size()." );
        }
        this.maxValueTerminalsAreAddedTo = maxValueTerminalsAreAddedTo;
    }

    @Override
    public String toString() {
        return "\nGrammarGeneratorSettingsDiceRoll{" +
                "\n		minValueCompoundVariablesAreAddedTo=" + minValueCompoundVariablesAreAddedTo +
                "\n		minValueTerminalsAreAddedTo=" + minValueTerminalsAreAddedTo +
                "\n		maxValueCompoundVariablesAreAddedTo=" + maxValueCompoundVariablesAreAddedTo +
                "\n		maxValueTerminalsAreAddedTo=" + maxValueTerminalsAreAddedTo +
                "\n" + grammarProperties +
                "\n}";
    }

}
