package com.github.andreasbraun5.thesis.latex;

/**
 * Created by Andreas Braun on 21.12.2016.
 * https://github.com/AndreasBraun5/
 */
public class TreeLatex<X> {

    public final X x;

    public final TreeLatex<X> left;
    public final TreeLatex<X> right;

    public TreeLatex(X x) {
        if(x == null) {
            throw new IllegalArgumentException("leaves must contain a data element");
        }
        this.x = x;
        this.left = null;
        this.right = null;
    }

    public TreeLatex(TreeLatex<X> left, TreeLatex<X> right) {
        if(left == null && right == null) {
            throw new IllegalArgumentException("inner nodes must have at least one child");
        }
        this.x = null;
        this.left = left;
        this.right = right;
    }

    public boolean isInnerNode() {
        return this.x == null;
    }

}
