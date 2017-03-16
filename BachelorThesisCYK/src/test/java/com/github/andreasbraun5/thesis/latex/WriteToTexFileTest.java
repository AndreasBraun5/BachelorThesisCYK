package com.github.andreasbraun5.thesis.latex;

import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.github.andreasbraun5.thesis.grammar.Grammar;
import com.github.andreasbraun5.thesis.grammar.Production;
import com.github.andreasbraun5.thesis.grammar.Terminal;
import com.github.andreasbraun5.thesis.grammar.Variable;
import com.github.andreasbraun5.thesis.grammar.VariableCompound;
import com.github.andreasbraun5.thesis.grammar.VariableK;
import com.github.andreasbraun5.thesis.grammar.VariableStart;
import com.github.andreasbraun5.thesis.parser.CYK;
import com.github.andreasbraun5.thesis.util.SetVMatrix;
import com.github.andreasbraun5.thesis.util.Util;

/**
 * Created by Andreas Braun on 14.03.2017.
 * https://github.com/AndreasBraun5/
 */
public class WriteToTexFileTest {

	@Test
	public void TexFileTest() throws Exception {
		System.out.println( "" );
		System.out.println( "############################" );
		System.out.println( "WriteToTexFileTest: Generating latex code directory structure." );

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
		SetVMatrix<Variable> setVMatrixSolutionSimple = SetVMatrix.buildEmptySetVMatrixWrapper(
				wordLength,
				Variable.class
		).setSetV(
				setVMatrixSolution.getSimpleMatrix() );

		//Pyramid pyramid = setVMatrixSolution.getPyramid();
		Pyramid pyramid = setVMatrixSolutionSimple.getPyramid();
		pyramid.word = new String[] {"b", "b", "a", "c", "b", "c"};
		CenteredTikzPicture tikz = new CenteredTikzPicture();
		WriteToTexFile.createOutputDirectory();
		WriteToTexFile.writeToMainTexFile( "pyramid" );
		WriteToTexFile.writeToTexFile( "pyramid", tikz.beginToString() + pyramid + tikz.endToString() );

	}

	@Test
	public void TexFileTest2() throws Exception {
		System.out.println( "" );
		System.out.println( "############################" );
		System.out.println( "WriteToTexFileTest2: Generating latex code directory structure." );

		int wordLength = 8;

		Set<VariableK>[][] setVTemp = Util.getInitialisedHashSetArray( wordLength );
		// reconstructing example matrix from SS12
		setVTemp[0][0].add( new VariableK( new Variable( "A" ), 1 ) );
		setVTemp[0][0].add( new VariableK( new Variable( "C" ), 1 ) );
		setVTemp[0][1].add( new VariableK( new VariableStart( "S" ), 1 ) );
		setVTemp[0][1].add( new VariableK( new Variable( "B" ), 1 ) );
		setVTemp[0][3].add( new VariableK( new Variable( "A" ), 2 ) );
		setVTemp[0][5].add( new VariableK( new Variable( "C" ), 1 ) );
		setVTemp[0][5].add( new VariableK( new Variable( "C" ), 4 ) );
		setVTemp[0][5].add( new VariableK( new VariableStart( "S" ), 2 ) );
		setVTemp[0][6].add( new VariableK( new Variable( "C" ), 1 ) );
		setVTemp[0][6].add( new VariableK( new Variable( "C" ), 4 ) );
		setVTemp[0][6].add( new VariableK( new VariableStart( "S" ), 2 ) );
		setVTemp[0][7].add( new VariableK( new VariableStart( "S" ), 1 ) );
		setVTemp[0][7].add( new VariableK( new VariableStart( "S" ), 4 ) );
		setVTemp[0][7].add( new VariableK( new Variable( "B" ), 1 ) );
		setVTemp[0][7].add( new VariableK( new Variable( "B" ), 6 ) );
		setVTemp[0][7].add( new VariableK( new Variable( "B" ), 7 ) );

		setVTemp[1][1].add( new VariableK( new Variable( "B" ), 2 ) );
		setVTemp[1][3].add( new VariableK( new Variable( "A" ), 2 ) );
		setVTemp[1][5].add( new VariableK( new Variable( "C" ), 4 ) );
		setVTemp[1][5].add( new VariableK( new VariableStart( "S" ), 2 ) );
		setVTemp[1][6].add( new VariableK( new Variable( "C" ), 4 ) );
		setVTemp[1][6].add( new VariableK( new VariableStart( "S" ), 2 ) );
		setVTemp[1][7].add( new VariableK( new Variable( "B" ), 6 ) );
		setVTemp[1][7].add( new VariableK( new Variable( "B" ), 7 ) );
		setVTemp[1][7].add( new VariableK( new VariableStart( "S" ), 4 ) );

		setVTemp[2][2].add( new VariableK( new Variable( "B" ), 3 ) );
		setVTemp[2][3].add( new VariableK( new Variable( "A" ), 3 ) );
		setVTemp[2][5].add( new VariableK( new Variable( "C" ), 4 ) );
		setVTemp[2][5].add( new VariableK( new VariableStart( "S" ), 3 ) );
		setVTemp[2][6].add( new VariableK( new Variable( "C" ), 4 ) );
		setVTemp[2][6].add( new VariableK( new VariableStart( "S" ), 3 ) );
		setVTemp[2][7].add( new VariableK( new VariableStart( "S" ), 4 ) );
		setVTemp[2][7].add( new VariableK( new Variable( "B" ), 6 ) );
		setVTemp[2][7].add( new VariableK( new Variable( "B" ), 7 ) );

		setVTemp[3][3].add( new VariableK( new Variable( "A" ), 4 ) );
		setVTemp[3][5].add( new VariableK( new Variable( "C" ), 4 ) );
		setVTemp[3][6].add( new VariableK( new Variable( "C" ), 4 ) );
		setVTemp[3][7].add( new VariableK( new VariableStart( "S" ), 4 ) );
		setVTemp[3][7].add( new VariableK( new Variable( "B" ), 6 ) );
		setVTemp[3][7].add( new VariableK( new Variable( "B" ), 7 ) );

		setVTemp[4][4].add( new VariableK( new Variable( "A" ), 5 ) );
		setVTemp[4][5].add( new VariableK( new Variable( "C" ), 5 ) );
		setVTemp[4][6].add( new VariableK( new Variable( "C" ), 5 ) );
		setVTemp[4][7].add( new VariableK( new VariableStart( "S" ), 5 ) );
		setVTemp[4][7].add( new VariableK( new Variable( "B" ), 6 ) );
		setVTemp[4][7].add( new VariableK( new Variable( "B" ), 7 ) );

		setVTemp[5][5].add( new VariableK( new Variable( "A" ), 6 ) );
		setVTemp[5][5].add( new VariableK( new Variable( "C" ), 6 ) );
		setVTemp[5][6].add( new VariableK( new Variable( "C" ), 6 ) );
		setVTemp[5][7].add( new VariableK( new VariableStart( "S" ), 6 ) );
		setVTemp[5][7].add( new VariableK( new Variable( "B" ), 6 ) );
		setVTemp[5][7].add( new VariableK( new Variable( "B" ), 7 ) );

		setVTemp[6][6].add( new VariableK( new Variable( "A" ), 7 ) );
		setVTemp[6][6].add( new VariableK( new Variable( "C" ), 7 ) );
		setVTemp[6][7].add( new VariableK( new Variable( "B" ), 7 ) );
		setVTemp[6][7].add( new VariableK( new VariableStart( "S" ), 7 ) );

		setVTemp[7][7].add( new VariableK( new Variable( "B" ), 8 ) );

		SetVMatrix<VariableK> setVMatrixSolution = SetVMatrix.buildEmptySetVMatrixWrapper(
				wordLength,
				VariableK.class
		).setSetV( setVTemp );
		Pyramid pyramid = setVMatrixSolution.getPyramid();
		pyramid.word = new String[] {"c", "b", "c", "a", "a", "c", "c", "b"};
		CenteredTikzPicture tikz = new CenteredTikzPicture();
		WriteToTexFile.createOutputDirectory();
		WriteToTexFile.writeToMainTexFile( "pyramid" );
		WriteToTexFile.writeToTexFile( "pyramid", tikz.beginToString() + pyramid + tikz.endToString() );

	}

}