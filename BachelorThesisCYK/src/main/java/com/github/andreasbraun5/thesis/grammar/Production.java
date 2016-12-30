package com.github.andreasbraun5.thesis.grammar;

import java.util.*;

/**
 * Created by Andreas Braun on 20.12.2016.
 */

public class Production {

    private Variable leftHandSideElement;
    // List<RightHandSideElement> because A --> BC or A --> AB is possible and the ordering is important
    private List<RightHandSideElement> rightHandSideElements = new ArrayList<>();

    public Production(Variable leftHandSideElement, RightHandSideElement... rightHandSideElements) {
        this.leftHandSideElement = leftHandSideElement;
        this.rightHandSideElements.addAll(Arrays.asList(rightHandSideElements).subList(0, rightHandSideElements.length));
    }

    /*
    Calling isElementAtRightHandSide checks whether one specific production already exists
     */
    public boolean isElementAtRightHandSide(RightHandSideElement rightHandSideElement){
        //System.out.println();
        //System.out.println(this.rightHandSideElements);
        //System.out.println(rightHandSideElement);
        return this.rightHandSideElements.contains(rightHandSideElement);
    }

    @Override
    public String toString() {
        return "Production{" +
                leftHandSideElement + "-->" + rightHandSideElements +
                "}\n";
    }

    public Variable getLeftHandSideElement() {
        return leftHandSideElement;
    }

    public List<RightHandSideElement> getRightHandSideElements() {
        return rightHandSideElements;
    }
}
