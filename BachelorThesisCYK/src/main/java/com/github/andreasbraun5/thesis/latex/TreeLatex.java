package com.github.andreasbraun5.thesis.latex;

import com.github.andreasbraun5.thesis.exception.TreeLatexRuntimeException;
import com.github.andreasbraun5.thesis.grammar.VariableStart;
import com.github.andreasbraun5.thesis.pyramid.CellElement;
import com.github.andreasbraun5.thesis.pyramid.CellK;
import com.github.andreasbraun5.thesis.pyramid.Pyramid;
import com.github.andreasbraun5.thesis.pyramid.VariableK;
import com.github.andreasbraun5.thesis.util.Tuple;
import com.github.andreasbraun5.thesis.util.Util;
import com.github.andreasbraun5.thesis.util.Word;
import sun.reflect.generics.tree.Tree;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Andreas Braun on 21.12.2016.
 * https://github.com/AndreasBraun5/
 */
public class TreeLatex {

    public final String name;

    public final TreeLatex left;
    public final TreeLatex right;

    public TreeLatex(String name) {
        this.name = name;
        this.left = null;
        this.right = null;
    }

    public TreeLatex(String name, TreeLatex left) {
        this.name = name;
        this.left = left;
        this.right = null;
    }

    public TreeLatex(String name, TreeLatex left, TreeLatex right) {
        this.name = name;
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
        int indexLast = pyramid.getCellsK().length - 1;
        Random random = new Random();
        if (root.getI() >= 2) {
            List<Tuple<CellK, CellK>> leftAndRights = new ArrayList<>(Util.calculatePossibleCellPairs(root, pyramid));
            Tuple<CellK, CellK> leftAndRight = leftAndRights.get(random.nextInt(leftAndRights.size()));
            // Guarantee that both cells have elements in it
            int count = 0;
            while (leftAndRight.x.getCellElements().size() == 0 || leftAndRight.y.getCellElements().size() == 0) {
                leftAndRight = leftAndRights.get(random.nextInt(leftAndRights.size()));
                count++;
                if(count > 50) {
                    throw new TreeLatexRuntimeException("\nTree generation not possible.\nToo many empty cells.");
                }
            }
            if (pyramid.getCellsK()[indexLast][0].getCellElements().size() == 0) {
                throw new TreeLatexRuntimeException("\nNo variable in the top.");
            }
            TreeLatex left = generateRandomTree(pyramid, leftAndRight.x);
            TreeLatex right = generateRandomTree(pyramid, leftAndRight.y);
            List<VariableK> varsInCell = root.getCellElements();
            // Pick random element from the cell, but
            CellElement varK = varsInCell.get(random.nextInt(varsInCell.size()));
            // guarantee that the start variable is picked as the top root node
            if (root.getI() == indexLast) {
                try {
                    varK = varsInCell.stream().filter(variableK ->
                            variableK.getLhse() instanceof VariableStart).collect(Collectors.toList()).get(0);
                } catch (Exception e) {
                    throw new TreeLatexRuntimeException("\nNo start variable in the top.");
                }
            }
            return new TreeLatex(varK.toString(), left, right);
        } else if (root.getI() == 1)

        { // Case for sub tree with hasLeaf == true
            List<VariableK> varsInCell = root.getCellElements();
            List<Tuple<CellK, CellK>> leftAndRights = new ArrayList<>(Util.calculatePossibleCellPairs(root, pyramid));
            Tuple<CellK, CellK> leftAndRight = leftAndRights.get(random.nextInt(leftAndRights.size()));
            TreeLatex left = generateRandomTree(pyramid, leftAndRight.x);
            TreeLatex right = generateRandomTree(pyramid, leftAndRight.y);
            // Pick random element from the cell
            return new TreeLatex(varsInCell.get(random.nextInt(varsInCell.size())).toString(), left, right);
        } else if (root.getI() == 0)

        { // Case for Terminals
            List<VariableK> varsInCell = root.getCellElements();
            Word word = pyramid.getWord();
            try {
                // Add the corresponding terminal of the word
                return new TreeLatex(
                        varsInCell.get(random.nextInt(varsInCell.size())).toString(),
                        new TreeLatex(word.getTerminals().get(root.getJ()).toString()));
            } catch (Exception e) {
                throw new TreeLatexRuntimeException("Terminal not included in grammar.");
            }
        } else

        {
            throw new TreeLatexRuntimeException("TreeLatex.generateRandomTree() failure.");
        }

    }

    private int treeSize() {
        if (hasLeaf()) {
            return 1;
        } else if (isInnerNode()) {
            return Math.max(this.left.treeSize(), this.right.treeSize()) + 1;
        }
        throw new TreeLatexRuntimeException("This should not have happened.");
    }

    private String treeToString() {
        StringBuilder str = new StringBuilder();
        if (hasLeaf()) {
            // [.C6 c ]
            str.append("[.")
                    .append(name)
                    .append(" ")
                    .append(left.name)
                    .append(" ]\n");
        } else if (isInnerNode()) {
            // [.S1 ]
            str.append("[.")
                    .append(name)
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
