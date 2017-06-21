package com.github.andreasbraun5.thesis.generator;

import com.github.andreasbraun5.thesis.exception.GrammarSettingRuntimeException;
import com.github.andreasbraun5.thesis.grammarproperties.GrammarProperties;

import java.util.Arrays;

/**
 * Created by AndreasBraun on 01.06.2017.
 */
public class GrammarGeneratorSettings {

    //TODO: store here which circled method A,B,C and D are to be called.
    public final String name;
    public final GrammarProperties grammarProperties; //
    private int minValueCompoundVariablesAreAddedTo; // default is 0
    private int minValueTerminalsAreAddedTo; // default is 1
    private int maxValueCompoundVariablesAreAddedTo; // default is to all variables would be possible
    private int maxValueTerminalsAreAddedTo; // default is to all variables would be possible
    // favouritism element of [1, ...]. One is neutral favouritism. All numbers bigger indicate more favouritism, e.g.
    // 2 == 200% favouritism, it is 2 times more likely to use this variables for distributing the elements. It is not
    // intended to use negative favouritism, e.g. numbers element of [0 ; 1[, instead favour all other variables, than
    // the ones you want to weight negatively.
    private int[] favouritism; // default is one for each --> same weighting

    public GrammarGeneratorSettings(GrammarProperties grammarProperties, String name) {
        this.name = name;
        this.grammarProperties = grammarProperties;
        minValueTerminalsAreAddedTo = 1;
        minValueCompoundVariablesAreAddedTo = 0;
        maxValueCompoundVariablesAreAddedTo = grammarProperties.variables.size();
        maxValueTerminalsAreAddedTo = grammarProperties.variables.size();
        this.favouritism = new int[grammarProperties.variables.size()];
        for ( int i = 0; i < grammarProperties.variables.size(); i++ ) {
            // no favouritism here because of the 1
            favouritism[i] = 1;
        }
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

    public int[] getFavouritism() {
        return favouritism;
    }

    public void setFavouritism(int[] favouritism) {
        if ( favouritism.length != this.favouritism.length ) {
            throw new GrammarSettingRuntimeException("favouritism array length is not right. Not equal the count of variables.");
        }
        this.favouritism = favouritism;
    }

    @Override
    public String toString() {
        return "\nGrammarGeneratorSettingsDiceRoll{" +
                "\n		minValueCompoundVariablesAreAddedTo=" + minValueCompoundVariablesAreAddedTo +
                "\n		minValueTerminalsAreAddedTo=" + minValueTerminalsAreAddedTo +
                "\n		maxValueCompoundVariablesAreAddedTo=" + maxValueCompoundVariablesAreAddedTo +
                "\n		maxValueTerminalsAreAddedTo=" + maxValueTerminalsAreAddedTo +
                "\n		favouritism=" + Arrays.toString( favouritism ) +
                "\n" + grammarProperties +
                "\n}";
    }

}
