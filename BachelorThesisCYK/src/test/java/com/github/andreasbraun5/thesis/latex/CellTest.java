package com.github.andreasbraun5.thesis.latex;

import org.junit.Test;

import com.github.andreasbraun5.thesis.grammar.Variable;
import com.github.andreasbraun5.thesis.grammar.VariableK;

/**
 * Created by Andreas Braun on 04.03.2017.
 * https://github.com/AndreasBraun5/
 */
public class CellTest {

	@Test
	public void toStringTest() throws Exception {
		System.out.println( "" );
		System.out.println( "############################" );
		System.out.println( "ResultCalculator CYK: algorithmSimple with input Grammar from the SS13" );
		Cell cell = new Cell( 0,0 );
		cell.addVar(  new VariableK( new Variable( "A" ), 1 ));
		cell.addVar(  new VariableK( new Variable( "B" ), 2 ));
		cell.addVar(  new VariableK( new Variable( "C" ), 3 ));
		cell.addVar(  new VariableK( new Variable( "D" ), 4 ));
		cell.addVar(  new VariableK( new Variable( "E" ), 5 ));
		System.out.println(cell);

	}

}