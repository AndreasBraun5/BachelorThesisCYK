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
        return this.left != null && this.right != null;
    }

    public boolean hasLeave() {
        return this.left == null || this.right == null;
    }

    private int treeSize( ) {
        if (hasLeave()) {
            return 1;
        } else if (isInnerNode()) {
            return Math.max(this.left.treeSize(), this.right.treeSize())+1;
        } throw new RuntimeException("This should not have happened.");
    }

    private String treeToString() {
        StringBuilder str = new StringBuilder();
        if (hasLeave()) {
            // [.C6 c ]
            str.append("[.")
                    .append(x)
                    .append(" ")
                    .append(left.x)
                    .append(" ]\n");
        } else if (isInnerNode()) {
            // [.S1 ]
            str.append("[.")
                    .append(x)
                    .append("\n")
                    .append(left.treeToString())
                    .append("")
                    .append(right.treeToString())
                    .append("]\n");
        }
        return str.toString();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("");
        str.append(TreeLatex.begin(treeSize()))
                .append(treeToString())
                .append(end());
        return str.toString();
    }

    public static String begin(int treeSize) {
        StringBuilder str = new StringBuilder("");
        str.append("\\begin{center}\n")
                .append("\\resizebox{\\linewidth}{!}{\n")
                .append("\\begin{tikzpicture}[baseline]\n")
                .append("\t\t\\tikzset{frontier/.style={distance from root=")
                .append(30 * treeSize)
                .append("pt}} %height of tree times 30pt\n")
                .append("\t\t\\tikzset{edge from parent/.style= {\n")
                .append("\t\t\t\tdraw,\n")
                .append("\t\t\t\tedge from parent path={(\\tikzparentnode.south)\t-- +(0,-8pt)-| (\\tikzchildnode)}\n")
                .append("\t\t\t}\n")
                .append("\t\t}\n").append("\t\t\\Tree\n");
        return str.toString();
    }

    public static String end() {
        StringBuilder str = new StringBuilder("");
        str.append("\\end{tikzpicture}\n")
                .append("}\n")
                .append("\\end{center}\n");
        return str.toString();
    }
}
