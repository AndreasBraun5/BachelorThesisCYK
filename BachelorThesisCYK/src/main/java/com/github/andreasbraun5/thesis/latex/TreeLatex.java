package com.github.andreasbraun5.thesis.latex;

/**
 * Created by Andreas Braun on 21.12.2016.
 * https://github.com/AndreasBraun5/
 */
public class TreeLatex {

    public final String x;

    public final TreeLatex left;
    public final TreeLatex right;

    public TreeLatex(String x) {
        this.x = x;
        this.left = null;
        this.right = null;
    }

    public TreeLatex(String x, TreeLatex left) {
        this.x = x;
        this.left = left;
        this.right = null;
    }

    public TreeLatex(String x, TreeLatex left, TreeLatex right) {
        this.x = x;
        this.left = left;
        this.right = right;
    }

    public boolean isInnerNode() {
        return this.left == null && this.right == null;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        return str.toString();
    }
}
