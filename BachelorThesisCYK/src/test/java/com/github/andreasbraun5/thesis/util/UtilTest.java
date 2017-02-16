package com.github.andreasbraun5.thesis.util;

import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.github.andreasbraun5.thesis.grammar.Grammar;
import com.github.andreasbraun5.thesis.grammar.LeftHandSideElement;
import com.github.andreasbraun5.thesis.grammar.Production;
import com.github.andreasbraun5.thesis.grammar.Terminal;
import com.github.andreasbraun5.thesis.grammar.Variable;
import com.github.andreasbraun5.thesis.grammar.VariableCompound;
import com.github.andreasbraun5.thesis.grammar.VariableKWrapper;
import com.github.andreasbraun5.thesis.grammar.VariableStart;
import com.github.andreasbraun5.thesis.grammarvalididtychecker.GrammarValidityChecker;

/**
 * Created by Andreas Braun on 13.02.2017.
 * https://github.com/AndreasBraun5/
 */
public class UtilTest {
	@Test
	public void removeUselessProductions() {
		System.out.println( "" );
		System.out.println( "############################" );
		System.out.println( "UtilTest: removeUselessProductions" );
		// @formatter:off
        Grammar grammar = new Grammar(new VariableStart("S"));
        Production productions[] = new Production[15];
        productions[0] = new Production(new VariableStart("S"), new VariableCompound(new Variable("N"), new Variable("B")));
        productions[1] = new Production(new VariableStart("S"), new VariableCompound(new Variable("E"), new Variable("A")));
        productions[2] = new Production(new VariableStart("S"), new Terminal(""));
        productions[3] = new Production(new Variable("S'"), new VariableCompound(new Variable("N"), new Variable("B")));
        productions[4] = new Production(new Variable("S'"), new VariableCompound(new Variable("E"), new Variable("A")));
        productions[5] = new Production(new Variable("N"), new Terminal("0"));
        productions[6] = new Production(new Variable("E"), new Terminal("1"));
        productions[7] = new Production(new Variable("A"), new Terminal("0"));
        productions[8] = new Production(new Variable("A"), new VariableCompound(new Variable("N"), new Variable("S'")));
        productions[9] = new Production(new Variable("A"), new VariableCompound(new Variable("E"), new Variable("C")));
        productions[10] = new Production(new Variable("B"), new Terminal("1"));
        productions[11] = new Production(new Variable("B"), new VariableCompound(new Variable("E"), new Variable("S'")));
        productions[12] = new Production(new Variable("B"), new VariableCompound(new Variable("N"), new Variable("D")));
        productions[13] = new Production(new Variable("C"), new VariableCompound(new Variable("A"), new Variable("A")));
        productions[14] = new Production(new Variable("D"), new VariableCompound(new Variable("B"), new Variable("B")));
        grammar.addProduction(productions);
		Production useless1 = new Production(new Variable("E"), new Terminal("5"));
		grammar.addProduction( useless1 );
		Production useless2 = new Production(new Variable("B"), new VariableCompound(new Variable("K"), new Variable("D")));
		grammar.addProduction( useless2 );
        String word = "01110100";
		// @formatter:on
		int wordLength = word.length();
		Set<LeftHandSideElement>[][] setVTemp = Util.getInitialisedHashSetArray( wordLength );
		SetVMatrix setVMatrix = SetVMatrix.buildEmptySetVMatrixWrapper( setVTemp.length ).setSetV( setVTemp );

		//reconstructing example matrix from scriptTI1
		setVTemp[0][0].add( new VariableKWrapper( new Variable( "A" ), 1 ) );
		setVTemp[0][0].add( new VariableKWrapper( new Variable( "N" ), 1 ) );
		setVTemp[0][1].add( new VariableKWrapper( new VariableStart( "S" ), 1 ) );
		setVTemp[0][1].add( new VariableKWrapper( new Variable( "S'" ), 1 ) );
		setVTemp[0][2].add( new VariableKWrapper( new Variable( "B" ), 1 ) );
		setVTemp[0][3].add( new VariableKWrapper( new Variable( "D" ), 3 ) );
		setVTemp[0][4].add( new VariableKWrapper( new Variable( "B" ), 1 ) );
		setVTemp[0][5].add( new VariableKWrapper( new Variable( "D" ), 3 ) );
		setVTemp[0][5].add( new VariableKWrapper( new Variable( "D" ), 5 ) );
		setVTemp[0][6].add( new VariableKWrapper( new Variable( "B" ), 1 ) );
		setVTemp[0][7].add( new VariableKWrapper( new VariableStart( "S" ), 1 ) );
		setVTemp[0][7].add( new VariableKWrapper( new Variable( "S'" ), 1 ) );

		setVTemp[1][1].add( new VariableKWrapper( new Variable( "E" ), 2 ) );
		setVTemp[1][1].add( new VariableKWrapper( new Variable( "B" ), 2 ) );
		setVTemp[1][2].add( new VariableKWrapper( new Variable( "D" ), 2 ) );
		setVTemp[1][4].add( new VariableKWrapper( new Variable( "D" ), 2 ) );
		setVTemp[1][6].add( new VariableKWrapper( new Variable( "D" ), 2 ) );
		setVTemp[1][7].add( new VariableKWrapper( new Variable( "B" ), 2 ) );

		setVTemp[2][2].add( new VariableKWrapper( new Variable( "E" ), 3 ) );
		setVTemp[2][2].add( new VariableKWrapper( new Variable( "B" ), 3 ) );
		setVTemp[2][3].add( new VariableKWrapper( new Variable( "D" ), 3 ) );
		setVTemp[2][4].add( new VariableKWrapper( new Variable( "B" ), 3 ) );
		setVTemp[2][5].add( new VariableKWrapper( new Variable( "D" ), 3 ) );
		setVTemp[2][5].add( new VariableKWrapper( new Variable( "D" ), 5 ) );
		setVTemp[2][6].add( new VariableKWrapper( new Variable( "B" ), 3 ) );
		setVTemp[2][7].add( new VariableKWrapper( new VariableStart( "S" ), 3 ) );
		setVTemp[2][7].add( new VariableKWrapper( new Variable( "S'" ), 3 ) );

		setVTemp[3][3].add( new VariableKWrapper( new Variable( "E" ), 4 ) );
		setVTemp[3][3].add( new VariableKWrapper( new Variable( "B" ), 4 ) );
		setVTemp[3][4].add( new VariableKWrapper( new VariableStart( "S" ), 4 ) );
		setVTemp[3][4].add( new VariableKWrapper( new Variable( "S'" ), 4 ) );
		setVTemp[3][5].add( new VariableKWrapper( new Variable( "B" ), 4 ) );
		setVTemp[3][6].add( new VariableKWrapper( new VariableStart( "S" ), 4 ) );
		setVTemp[3][6].add( new VariableKWrapper( new Variable( "S'" ), 4 ) );
		setVTemp[3][7].add( new VariableKWrapper( new Variable( "A" ), 4 ) );

		setVTemp[4][4].add( new VariableKWrapper( new Variable( "A" ), 5 ) );
		setVTemp[4][4].add( new VariableKWrapper( new Variable( "N" ), 5 ) );
		setVTemp[4][5].add( new VariableKWrapper( new VariableStart( "S" ), 5 ) );
		setVTemp[4][5].add( new VariableKWrapper( new Variable( "S'" ), 5 ) );
		setVTemp[4][6].add( new VariableKWrapper( new Variable( "A" ), 5 ) );
		setVTemp[4][7].add( new VariableKWrapper( new Variable( "C" ), 5 ) );
		setVTemp[4][7].add( new VariableKWrapper( new Variable( "C" ), 7 ) );

		setVTemp[5][5].add( new VariableKWrapper( new Variable( "E" ), 6 ) );
		setVTemp[5][5].add( new VariableKWrapper( new Variable( "B" ), 6 ) );
		setVTemp[5][6].add( new VariableKWrapper( new VariableStart( "S" ), 6 ) );
		setVTemp[5][6].add( new VariableKWrapper( new Variable( "S'" ), 6 ) );
		setVTemp[5][7].add( new VariableKWrapper( new Variable( "A" ), 6 ) );

		setVTemp[6][6].add( new VariableKWrapper( new Variable( "A" ), 7 ) );
		setVTemp[6][6].add( new VariableKWrapper( new Variable( "N" ), 7 ) );
		setVTemp[6][7].add( new VariableKWrapper( new Variable( "C" ), 7 ) );

		setVTemp[7][7].add( new VariableKWrapper( new Variable( "A" ), 8 ) );
		setVTemp[7][7].add( new VariableKWrapper( new Variable( "N" ), 8 ) );
		System.out.println( grammar );
		System.out.println( word );
		Set<Variable>[][] setVSimple = setVMatrix.getSimpleMatrix();
		SetVMatrix setVSimpleWrapper = SetVMatrix.buildEmptySetVMatrixWrapper( wordLength ).setSetV( setVSimple );
		System.out.println( setVSimpleWrapper.getStringToPrintAsLowerTriangularMatrix() );
		System.out.println( "Useless productions are" + useless1 + useless2 + "\n" );
		Util.removeUselessProductions( grammar, setVMatrix, Util.stringToTerminalList( word ) );
		System.out.println( grammar );
		Assert.assertTrue(
				"There should be only 15 productions left.",
				GrammarValidityChecker.checkSumOfProductions( grammar, 15 )
		);
		System.out.println( "UtilTest: removeUselessProductions was successful" );
	}

}