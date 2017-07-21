package com.github.andreasbraun5.thesis.grammar;

import lombok.EqualsAndHashCode;

/**
 * Created by AndreasBraun on 15.06.2017.
 */
@EqualsAndHashCode
public class Epsilon implements RightHandSideElement {

    private final String name = "eps";

    @Override
    public String getTerminalName() {
        return name;
    }

    @Override
    public String toString(){
        return name;
    }
}
