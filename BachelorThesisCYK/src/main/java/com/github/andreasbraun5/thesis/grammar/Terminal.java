package com.github.andreasbraun5.thesis.grammar;

/**
 * Created by Andreas Braun on 20.12.2016.
 */
public class Terminal implements RuleElement {

    private final String name;

    public Terminal(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Terminal terminal = (Terminal) o;

        return name.equals(terminal.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
