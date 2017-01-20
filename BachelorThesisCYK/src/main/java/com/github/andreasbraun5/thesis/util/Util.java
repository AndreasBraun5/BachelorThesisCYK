package com.github.andreasbraun5.thesis.util;

import com.github.andreasbraun5.thesis.grammar.Grammar;
import com.github.andreasbraun5.thesis.grammar.RightHandSideElement;
import com.github.andreasbraun5.thesis.grammar.Variable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Andreas Braun on 05.01.2017.
 */
public class Util {
	/**
	 * Removing duplicates from a collection.
	 */
	public static <T extends RightHandSideElement> List<T> withoutDuplicates(Collection<T> ruleElements) {
		List<T> ret = new ArrayList<>();
		ret.addAll( new HashSet<>( ruleElements ) );
		return ret;
	}

	public static int getMaxVarPerCellForSetV(Set<Variable>[][] setV){
		int numberOfVarsPerCell = 0;
		int temp = setV.length;
		for ( int i = 0; i < temp; i++ ) {
			for ( int j = 0; j < temp; j++ ) {
				if(setV[i][j].size() > numberOfVarsPerCell){
					numberOfVarsPerCell = setV[i][j].size();
				}
			}
		}
		return numberOfVarsPerCell;
	}
}
