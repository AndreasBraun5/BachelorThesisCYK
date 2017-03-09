package com.github.andreasbraun5.thesis.latex;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.github.andreasbraun5.thesis.exception.CellRuntimeException;
import com.github.andreasbraun5.thesis.grammar.LeftHandSideElement;

/**
 * Created by Andreas Braun on 04.03.2017.
 * https://github.com/AndreasBraun5/
 */
public class Cell {
	// No more than 5 variables in one Cell are allowed.
	private List<LeftHandSideElement> vars = new ArrayList<>();
	// center(x,y) coordinates will later on be used to position the variables in the cell using left, above, right,
	// below and center
	public double centerX = 0.0;
	public double centerY = 0.0;
	public double i = 0;
	public double j = 0;
	private String centerName = "";

	public Cell(int i, int j) {
		this.i = i;
		this.j = j;
		centerName = "center" + i + "" + j;
	}

	public <T extends LeftHandSideElement> Cell addVar(Set<T> set) {
		for ( T var : set ) {
			if ( this.vars.size() >= 5 ) {
				throw new CellRuntimeException( "There are more than 5 Variables in the pyramid cell, coordinates: " +
														i + ", " + j );
			}
			this.vars.add( var );
		}
		return this;
	}

	private String drawTheVars() {
		StringBuilder str = new StringBuilder();
		/*
		\node [left] at (center) {\fontsize{5}{12}\selectfont{A1}};
		\node [above] at (center) {\fontsize{5}{12}\selectfont{B2}};
		\node [right] at (center) {\fontsize{5}{12}\selectfont{C3}};
		\node [below] at (center) {\fontsize{5}{12}\selectfont{D4}};
		\node [] at (center) {\fontsize{5}{12}\selectfont{E5}};
		*/
		if ( vars.size() > 5 ) {
			throw new CellRuntimeException( "There are more than 5 Variables in the pyramid cell, coordinates: " +
													i + ", " + j );
		}
		String[] pos = { "", "left", "right", "above", "below" };
		for ( int i = 0; i < vars.size(); i++ ) {
			str.append( "\\node [" + pos[i] + "] at (" + centerName + ") {\\fontsize{5}{12}\\selectfont{" + vars.get( i )
					.toString() + "}};\n" );
		}
		return str.toString();
	}


	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append( "\\coordinate (" + centerName + ") at (" + centerX + "," + centerY + ");\n" );
		str.append( drawTheVars() );
		return str.toString();
	}
}

