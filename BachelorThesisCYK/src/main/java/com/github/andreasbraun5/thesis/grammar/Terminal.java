package com.github.andreasbraun5.thesis.grammar;

import lombok.EqualsAndHashCode;

/**
 * Created by Andreas Braun on 20.12.2016.
 * https://github.com/AndreasBraun5/
 */
@EqualsAndHashCode
public class Terminal implements RightHandSideElement, TreeElement {

    private final String name;

    public Terminal(String name) {
        this.name = name;
    }

    public static Terminal of(String name) {
        return new Terminal(name);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public String getTerminalName() {
        return name;
    }
}
