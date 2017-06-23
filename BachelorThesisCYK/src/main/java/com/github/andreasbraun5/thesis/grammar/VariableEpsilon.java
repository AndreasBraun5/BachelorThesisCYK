package com.github.andreasbraun5.thesis.grammar;

/**
 * Created by AndreasBraun on 15.06.2017.
 */
public class VariableEpsilon implements RightHandSideElement {

    private final String name = "";

    @Override
    public String getTerminalName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }

        VariableEpsilon varEps = (VariableEpsilon) o;

        return name.equals( varEps.name );
    }

    @Override
    public String toString(){
        return name;
    }
}
