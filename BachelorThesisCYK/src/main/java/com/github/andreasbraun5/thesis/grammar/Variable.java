package com.github.andreasbraun5.thesis.grammar;

/**
 * Created by Andreas Braun on 20.12.2016.
 */
public class Variable implements RightHandSideElement, LeftHandSideElement {

    protected String name;

    /*
    Constructing single variables like A, B for the LeftHandSideElement and also compound variables like AB, BC, BD for
    the RightHandSideElement possible.
    */
    // TODO: Compound variables allowed?
    /*public Variable(Variable... variable) {
        StringBuilder string = new StringBuilder("");
        for (Variable v : variable) {
            string.append(v.getName());
        }
        this.name = string.toString();
    }*/
    public Variable(String str1, String str2) {
        StringBuilder string = new StringBuilder("");
        string.append(str1);
        string.append(str2);
        this.name = string.toString();
    }

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Variable variable = (Variable) o;

        return name != null ? name.equals(variable.name) : variable.name == null;

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

}
/*
            boolean t1 = o==null;
            Class c1 = getClass();
            Class c2 = o.getClass();
            boolean t2 = getClass() != o.getClass();
 */
