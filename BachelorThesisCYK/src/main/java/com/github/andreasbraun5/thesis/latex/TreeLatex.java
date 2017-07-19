package com.github.andreasbraun5.thesis.latex;

import com.github.andreasbraun5.thesis.pyramid.CellElement;
import com.github.andreasbraun5.thesis.pyramid.CellK;
import com.github.andreasbraun5.thesis.pyramid.Pyramid;
import com.github.andreasbraun5.thesis.pyramid.VariableK;
import com.github.andreasbraun5.thesis.util.Tuple;
import com.github.andreasbraun5.thesis.util.Util;
import com.github.andreasbraun5.thesis.util.Word;

import java.util.*;

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

    public boolean hasLeaf() {
        return this.left == null || this.right == null;
    }

    public static TreeLatex generateRandomTree(Pyramid pyramid, CellK root) {
        List<Tuple<CellK, CellK>> leftAndRights = new ArrayList<>(Util.calculatePossibleCellPairs(root, pyramid));
        if(leftAndRights.size() == 0) {
            return new TreeLatex(root.getCellElements().toString());
        }
        Random random = new Random();
        Tuple<CellK, CellK> leftAndRight = leftAndRights.get(random.nextInt(leftAndRights.size()));
        TreeLatex left = generateRandomTree(pyramid, leftAndRight.x);
        TreeLatex right = generateRandomTree(pyramid, leftAndRight.y);
        return new TreeLatex(root.getCellElements().toString(), left, right);
    }

    public static TreeLatex generateRandomTreeFromPyramid(Pyramid pyramid) {

        Word word = pyramid.getWord();
        CellK[][] cells = pyramid.getCellsK();
        Random random = new Random();
        Map<String, TreeLatex> tree = new HashMap<>();

        // Speichern aller zu verwendenden cellPairs bis bei leafs angekommen.
        // Generieren des leaf node sub trees

       {
            if (hasLeaf()) {

            } else if (isInnerNode()) {

            }
        }

        Set<Tuple<CellK, CellK>> cellPairs = Util.calculatePossibleCellPairs(cells[word.getWordLength()][0], pyramid);
        // chosen cellPair cells must not be empty
        Tuple<CellK, CellK> cellPair = new ArrayList<>(cellPairs).get(random.nextInt(cellPairs.size()));
        List<VariableK> varsOfX = cellPair.x.getCellElements();
        List<VariableK> varsOfY = cellPair.y.getCellElements();
        VariableK varOfX = varsOfX.get(random.nextInt(varsOfX.size()));
        VariableK varOfY = varsOfY.get(random.nextInt(varsOfY.size()));

        // pick random element from each cell, they are the new sub trees left and right
        // do the same "recursively" until you read hasLeaf, if hasLeaf then add the rest of the tree directly.

        {   // generate all leave trees
            for (int i = 0; i < word.getWordLength(); i++) {
                tree.put("w" + i, new TreeLatex(word.getTerminals().get(i).getTerminalName()));
            }
        }


        TreeLatex w1 = new TreeLatex("c");
        TreeLatex w2 = new TreeLatex("b");
        TreeLatex w3 = new TreeLatex("b");
        TreeLatex w4 = new TreeLatex("a");
        TreeLatex w5 = new TreeLatex("a");
        TreeLatex w6 = new TreeLatex("c");
        TreeLatex w7 = new TreeLatex("c");
        TreeLatex w8 = new TreeLatex("b");

        TreeLatex A1_1 = new TreeLatex("A1", w1);
        TreeLatex B2_1 = new TreeLatex("B2", w2);
        TreeLatex B3_1 = new TreeLatex("B3", w3);
        TreeLatex A4_1 = new TreeLatex("A4", w4);
        TreeLatex A5_1 = new TreeLatex("A5", w5);
        TreeLatex C6_1 = new TreeLatex("C6", w6);
        TreeLatex C7_1 = new TreeLatex("C7", w7);
        TreeLatex B8_1 = new TreeLatex("B8", w8);

        TreeLatex A3_2 = new TreeLatex("A3", B3_1, A4_1);
        TreeLatex C5_2 = new TreeLatex("C5", A5_1, C6_1);
        TreeLatex B7_2 = new TreeLatex("B7", C7_1, B8_1);

        TreeLatex A2_3 = new TreeLatex("A2", B2_1, A3_2);
        TreeLatex C4_4 = new TreeLatex("C4", A2_3, C5_2);
        TreeLatex B6_5 = new TreeLatex("B6", C4_4, B7_2);
        TreeLatex S1_6 = new TreeLatex("S1", A1_1, B6_5);

        return S1_6;
    }


    private int treeSize() {
        if (hasLeaf()) {
            return 1;
        } else if (isInnerNode()) {
            return Math.max(this.left.treeSize(), this.right.treeSize()) + 1;
        }
        throw new RuntimeException("This should not have happened.");
    }

    private String treeToString() {
        StringBuilder str = new StringBuilder();
        if (hasLeaf()) {
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
