package com.github.andreasbraun5.thesis.grammar;

import java.util.*;

/**
 * Created by Andreas Braun on 20.12.2016.
 */

public class Production {

    private Variable leftHandSideElement;
    /* List<RightHandSideElement> because A --> BC or A --> AB is possible and the ordering is important.
        The example above shows that there are two rightHandSideElements each.
     */
    private RightHandSideElement rightHandSideElement;

    public Production(Variable leftHandSideElement, RightHandSideElement rightHandSideElement) {
        this.leftHandSideElement = leftHandSideElement;
        this.rightHandSideElement = rightHandSideElement;
    }

    public Production(Variable leftHandSideElement) {
        this.leftHandSideElement = leftHandSideElement;
    }

    /*
    Calling isElementAtRightHandSide checks whether one specific production already exists
     */
    public boolean isElementAtRightHandSide(RightHandSideElement rightHandSideElement){
        return this.rightHandSideElement.equals(rightHandSideElement);
    }

    @Override
    public String toString() {
        return "Production{" +
                leftHandSideElement + "-->" + rightHandSideElement +
                "}\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Production that = (Production) o;

        if (leftHandSideElement != null ? !leftHandSideElement.equals(that.leftHandSideElement) : that.leftHandSideElement != null)
            return false;
        return rightHandSideElement != null ? rightHandSideElement.equals(that.rightHandSideElement) : that.rightHandSideElement == null;

    }

    @Override
    public int hashCode() {
        int result = leftHandSideElement != null ? leftHandSideElement.hashCode() : 0;
        result = 31 * result + (rightHandSideElement != null ? rightHandSideElement.hashCode() : 0);
        return result;
    }

    public Variable getLeftHandSideElement() {
        return this.leftHandSideElement;
    }

    public RightHandSideElement getRightHandSideElement() {
        return this.rightHandSideElement;
    }
}
