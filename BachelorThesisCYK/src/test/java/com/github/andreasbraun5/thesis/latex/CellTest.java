package com.github.andreasbraun5.thesis.latex;

import com.github.andreasbraun5.thesis.grammar.Variable;
import com.github.andreasbraun5.thesis.grammar.VariableK;
import com.github.andreasbraun5.thesis.grammar.VariableStart;
import com.github.andreasbraun5.thesis.util.SetVMatrix;
import com.github.andreasbraun5.thesis.util.Util;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Andreas Braun on 04.03.2017.
 * https://github.com/AndreasBraun5/
 */
public class CellTest {

	@Test
	public void toStringTest() throws Exception {
		System.out.println( "" );
		System.out.println( "############################" );
		System.out.println( "CellTest: Generating LaTeX code for the cells." );
		Cell cell = new Cell( 2, 2 );
		cell.centerX = 2;
		cell.centerY = -0.5;
		Set<VariableK> setvk = new HashSet<>();
		setvk.add( new VariableK( new Variable( "A" ), 1 ) );
		setvk.add( new VariableK( new Variable( "B" ), 2 ) );
		setvk.add( new VariableK( new Variable( "C" ), 3 ) );
		setvk.add( new VariableK( new Variable( "D" ), 4 ) );
		setvk.add( new VariableK( new Variable( "E" ), 5 ) );
		cell.addVar( setvk );
		System.out.println( cell );

	}


	@Test
	public void toStringTest2() throws Exception {
		System.out.println( "" );
		System.out.println( "############################" );
		System.out.println( "CellTest: Generating LaTeX code for the cells." );
		int wordLength = 6;

		Set<VariableK>[][] setVTemp = Util.getInitialisedHashSetArray( wordLength );
		// reconstructing example matrix
		setVTemp[0][0].add( new VariableK( new Variable( "A" ), 1 ) );
		setVTemp[0][0].add( new VariableK( new Variable( "B" ), 1 ) );
		setVTemp[0][1].add( new VariableK( new VariableStart( "S" ), 1 ) );
		setVTemp[0][1].add( new VariableK( new Variable( "B" ), 1 ) );
		setVTemp[0][3].add( new VariableK( new VariableStart( "S" ), 1 ) );
		setVTemp[0][3].add( new VariableK( new Variable( "C" ), 1 ) );
		setVTemp[0][3].add( new VariableK( new Variable( "A" ), 1 ) );
		setVTemp[0][3].add( new VariableK( new Variable( "A" ), 2 ) );
		setVTemp[0][4].add( new VariableK( new VariableStart( "S" ), 4 ) );
		setVTemp[0][5].add( new VariableK( new VariableStart( "S" ), 1 ) );
		setVTemp[0][5].add( new VariableK( new VariableStart( "S" ), 4 ) );
		setVTemp[0][5].add( new VariableK( new Variable( "C" ), 1 ) );
		setVTemp[0][5].add( new VariableK( new Variable( "C" ), 4 ) );
		setVTemp[0][5].add( new VariableK( new Variable( "A" ), 1 ) );

		setVTemp[1][1].add( new VariableK( new Variable( "A" ), 2 ) );
		setVTemp[1][1].add( new VariableK( new Variable( "B" ), 2 ) );
		setVTemp[1][2].add( new VariableK( new VariableStart( "S" ), 2 ) );
		setVTemp[1][3].add( new VariableK( new Variable( "C" ), 2 ) );
		setVTemp[1][3].add( new VariableK( new VariableStart( "S" ), 2 ) );
		setVTemp[1][3].add( new VariableK( new Variable( "A" ), 2 ) );
		setVTemp[1][4].add( new VariableK( new VariableStart( "S" ), 4 ) );
		setVTemp[1][5].add( new VariableK( new Variable( "C" ), 4 ) );
		setVTemp[1][5].add( new VariableK( new VariableStart( "S" ), 4 ) );

		setVTemp[2][2].add( new VariableK( new Variable( "A" ), 3 ) );
		setVTemp[2][3].add( new VariableK( new VariableStart( "S" ), 3 ) );
		setVTemp[2][3].add( new VariableK( new Variable( "C" ), 3 ) );
		setVTemp[2][4].add( new VariableK( new VariableStart( "S" ), 4 ) );

		setVTemp[3][3].add( new VariableK( new Variable( "C" ), 4 ) );
		setVTemp[3][4].add( new VariableK( new VariableStart( "S" ), 4 ) );

		setVTemp[4][4].add( new VariableK( new Variable( "A" ), 5 ) );
		setVTemp[4][4].add( new VariableK( new Variable( "B" ), 5 ) );
		setVTemp[4][5].add( new VariableK( new Variable( "C" ), 5 ) );
		setVTemp[4][5].add( new VariableK( new VariableStart( "S" ), 5 ) );
		setVTemp[4][5].add( new VariableK( new Variable( "A" ), 5 ) );

		setVTemp[5][5].add( new VariableK( new Variable( "C" ), 6 ) );

		SetVMatrix<VariableK> setVMatrixSolution = SetVMatrix.buildEmptySetVMatrixWrapper(
				wordLength,
				VariableK.class
		).setSetV( setVTemp );
		System.out.print( setVMatrixSolution.getStringToPrintAsLowerTriangularMatrix() );
		Pyramid pyramid = setVMatrixSolution.getPyramid();
		pyramid.word = new String[] {"b", "b", "a", "c", "b", "c"};
		System.out.print( pyramid );
	}

}