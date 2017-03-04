package com.github.andreasbraun5.thesis.latex;

import java.util.ArrayList;
import java.util.List;

import com.github.andreasbraun5.thesis.exception.CellRuntimeException;
import com.github.andreasbraun5.thesis.grammar.VariableK;

/**
 * Created by Andreas Braun on 04.03.2017.
 * https://github.com/AndreasBraun5/
 */
public class Cell {
	// No more than 5 variables in one Cell are allowed.
	private List<VariableK> vars = new ArrayList<>();
	// center(x,y) coordinates will later on be used to position the variables in the cell using left, above, right,
	// below and center
	public double centerX = 0.0;
	public double centerY = 0.0;
	private String centerName = "center_" + centerX + "_" + centerY;

	public Cell(int centerX, int centerY) {
		this.centerX = centerX;
		this.centerY = centerY;
	}

	public List<VariableK> getVars() {
		return vars;
	}

	public Cell addVar(VariableK... vars) {
		for ( VariableK var : vars ) {
			if ( this.vars.size() >= 5 ) {
				throw new CellRuntimeException( "There are more than 5 Variables in the pyramid cell, coordinates: " +
														centerX + ", " +
														centerY );
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
		if ( vars.size() <= 1 ) {
			str.append( "\\node [] at (" + centerName + "){\\fontsize{5}{12}\\selectfont{" + vars.get( 1 )
					.toString() + "}};\n" );
		}
		if ( vars.size() <= 2 ) {
			str.append( "\\node [] at (" + centerName + "){\\fontsize{5}{12}\\selectfont{" + vars.get( 2 )
					.toString() + "}};\n" );
		}
		if ( vars.size() <= 3 ) {
			str.append( "\\node [] at (" + centerName + "){\\fontsize{5}{12}\\selectfont{" + vars.get( 3 )
					.toString() + "}};\n" );
		}
		if ( vars.size() <= 4 ) {
			str.append( "\\node [] at (" + centerName + "){\\fontsize{5}{12}\\selectfont{" + vars.get( 4 )
					.toString() + "}};\n" );
		}
		if ( vars.size() <= 5 ) {
			str.append( "\\node [] at (" + centerName + "){\\fontsize{5}{12}\\selectfont{" + vars.get( 5 )
					.toString() + "}};\n" );
		}
		return str.toString();
	}


	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		/*
	 	\coordinate (center) at (2, -0.5);
		\node [left] at (center) {\fontsize{5}{12}\selectfont{A1}};
		\node [above] at (center) {\fontsize{5}{12}\selectfont{B2}};
		\node [right] at (center) {\fontsize{5}{12}\selectfont{C3}};
		\node [below] at (center) {\fontsize{5}{12}\selectfont{D4}};
		\node [] at (center) {\fontsize{5}{12}\selectfont{E5}};
		*/
		str.append( "\\coordinate (" + centerName + ") at (" + centerX + "," + centerY + ");\n" );
		str.append( drawTheVars() );
		return str.toString();
	}
}

