package com.github.andreasbraun5.thesis.latex;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.github.andreasbraun5.thesis.exception.CellRuntimeException;
import com.github.andreasbraun5.thesis.grammar.LeftHandSideElement;
import com.github.andreasbraun5.thesis.grammar.VariableK;
import com.github.andreasbraun5.thesis.util.Util;

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
		if ( vars.size() > 5 ) {
			throw new CellRuntimeException( "There are more than 5 Variables in the pyramid cell, coordinates: " +
													i + ", " + j );
		}
		if ( vars.get( 0 ) instanceof VariableK ) {
			ArrayList<VariableK> varK = new ArrayList<>( Util.filter( vars, VariableK.class ) );
			int k = 0;
			str.append( "\\node [] at (" + centerName + ") {\\myfontvars{" );
			for ( int i = k; i < 3 && i < varK.size(); i++ ) {
				str.append( varK.get( i ).getVariable() + "$_" + varK.get( i ).getK() + "$" );
			}
			str.append( "}};\n" );
			k = 3;
			if ( vars.size() > k ) {
				str.append( "\\node [above] at (" + centerName + ") {\\myfontvars{" + varK.get( k )
						.getVariable() + "$_" + varK.get( k ).getK() + "$}};\n" );
			}
			k = 4;
			if ( vars.size() > k ) {
				str.append( "\\node [below] at (" + centerName + ") {\\myfontvars{" + varK.get( k )
						.getVariable() + "$_" + varK.get( k ).getK() + "$}};\n" );
			}

		}
		else {
			int k = 0;
			str.append( "\\node [] at (" + centerName + ") {\\myfontvars{~" );
			for ( int i = k; i < 3 && i < vars.size(); i++ ) {
				str.append( "$" + vars.get( i ).toString() + "$~" );
			}
			str.append( "}};\n" );
			k = 3;
			if ( vars.size() > k ) {
				str.append( "\\node [above] at (" + centerName + ") {\\myfontvars{" + vars.get( k )
									+ "$_" + vars.get( k ) + "$}};\n" );
			}
			k = 4;
			if ( vars.size() > k ) {
				str.append( "\\node [below] at (" + centerName + ") {\\myfontvars{" + vars.get( k )
									+ "$_" + vars.get( k ) + "$}};\n" );
			}

		}
		return str.toString();
	}


	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		if ( vars.size() > 0 ) {
			str.append( "%cell" + (int) i + "" + (int) j + "\n" );
			str.append( "\\coordinate (" + centerName + ") at (" + centerX + "," + centerY + ");\n" );
			str.append( "\\node [below=0.18cm] at (" + centerName + ") {\\myfontnumbering{$(" + (int) i + "" + (int) j + ")$}};\n" );
			str.append( drawTheVars() );
		}
		else {
			str.append( "%cell" + (int) i + "" + (int) j + "\n" );
			str.append( "\\coordinate (" + centerName + ") at (" + centerX + "," + centerY + ");\n" );
			str.append( "\\node [below=0.18cm] at (" + centerName + ") {\\myfontnumbering{$(" + (int) i + "" + (int) j + ")$}};\n" );
			str.append( "" );
		}
		return str.toString();
	}
}

