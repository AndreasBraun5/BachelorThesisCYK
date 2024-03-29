package com.github.andreasbraun5.thesis.grammar;

/**
 * Created by Andreas Braun on 03.01.2017.
 * https://github.com/AndreasBraun5/
 */
public class VariableStart extends Variable {

    public VariableStart(String name) {
        super(name);
    }

    public static VariableStart of(String name) {
        return new VariableStart(name);
    }

}
