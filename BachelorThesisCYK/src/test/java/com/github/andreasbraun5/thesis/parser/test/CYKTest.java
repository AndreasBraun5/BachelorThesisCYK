package com.github.andreasbraun5.thesis.parser.test;

import com.github.andreasbraun5.thesis.generator.GeneratorWordDiceRoll;
import com.github.andreasbraun5.thesis.grammar.*;
import com.github.andreasbraun5.thesis.parser.CYK;
import com.github.andreasbraun5.thesis.util.Util;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Andreas Braun on 02.01.2017.
 */
public class CYKTest {

	/**
	 * Checking the trivial case. A word like "a^sizeOfWord" and a grammar with S-->a|SS. The grammar must be
	 * be able to generate each of the words.
	 */
	@Test
	public void CYKAlwaysTrue() {
		System.out.println( "" );
		System.out.println( "############################" );
		System.out.println( "TestGrammar CYK: AlwaysTrue" );
		// Define GrammarProperties
		GrammarProperties grammarProperties = new GrammarProperties( new VariableStart( "S" ) )
				.addTerminals( new Terminal( "a" ) );
		grammarProperties.maxNumberOfVarsPerCell = 3;
		grammarProperties.sizeOfWord = 10;
		// Generate word
		GeneratorWordDiceRoll generatorWordDiceRoll = new GeneratorWordDiceRoll();
		String word = generatorWordDiceRoll.generateWord( grammarProperties ).toString();
		// Generate Grammar
		Grammar grammar = new Grammar( new VariableStart( "S" ) );
		grammar.addProduction(
				new Production( new VariableStart( "S" ), new Terminal( "a" ) ),
				new Production(
						new VariableStart( "S" ),
						new VariableCompound( new VariableStart( "S" ), new VariableStart( "S" ) )
				)
		);
		System.out.println( grammar );
		// Check for integrity
		Util.printSetV( CYK.calculateSetV( grammar, Util.stringToTerminalList( word ) ), "setV" );
		Assert.assertEquals( "The grammar and the word aren't compatible, but should be.", true, CYK.algorithmSimple(
				grammar,
				Util.stringToTerminalList( word )
		) );
	}

	@Test
	public void CYKCalculateSetVTestWithScript() {
		System.out.println( "" );
		System.out.println( "############################" );
		System.out.println( "TestGrammar CYK: algorithmSimple with input Grammar from the TI1 script" );
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
        String word = "01110100";
		// @formatter:on

		Set<Variable> setV[][] = CYK.calculateSetV( grammar, Util.stringToTerminalList( word ) );
		Util.printSetV( setV, "setV calculated:" );

		int wordLength = word.length();
		Set<Variable>[][] setVTemp = new Set[wordLength][wordLength];
		for ( int i = 0; i < wordLength; i++ ) {
			for ( int j = 0; j < wordLength; j++ ) {
				setVTemp[i][j] = new HashSet<>();
			}
		}
		// TODO: make this a comment and use the VariableKWrapper stuff
		// reconstructing example matrix from scriptTI1
		setVTemp[0][0].add( new Variable( "A" ) );
		setVTemp[0][0].add( new Variable( "N" ) );
		setVTemp[0][1].add( new VariableStart( "S" ) );
		setVTemp[0][1].add( new Variable( "S'" ) );
		setVTemp[0][2].add( new Variable( "B" ) );
		setVTemp[0][3].add( new Variable( "D" ) );
		setVTemp[0][4].add( new Variable( "B" ) );
		setVTemp[0][5].add( new Variable( "D" ) );
		setVTemp[0][6].add( new Variable( "B" ) );
		setVTemp[0][7].add( new VariableStart( "S" ) );
		setVTemp[0][7].add( new Variable( "S'" ) );

		setVTemp[1][1].add( new Variable( "E" ) );
		setVTemp[1][1].add( new Variable( "B" ) );
		setVTemp[1][2].add( new Variable( "D" ) );
		setVTemp[1][4].add( new Variable( "D" ) );
		setVTemp[1][6].add( new Variable( "D" ) );
		setVTemp[1][7].add( new Variable( "B" ) );

		setVTemp[2][2].add( new Variable( "E" ) );
		setVTemp[2][2].add( new Variable( "B" ) );
		setVTemp[2][3].add( new Variable( "D" ) );
		setVTemp[2][4].add( new Variable( "B" ) );
		setVTemp[2][5].add( new Variable( "D" ) );
		setVTemp[2][6].add( new Variable( "B" ) );
		setVTemp[2][7].add( new VariableStart( "S" ) );
		setVTemp[2][7].add( new Variable( "S'" ) );

		setVTemp[3][3].add( new Variable( "E" ) );
		setVTemp[3][3].add( new Variable( "B" ) );
		setVTemp[3][4].add( new VariableStart( "S" ) );
		setVTemp[3][4].add( new Variable( "S'" ) );
		setVTemp[3][5].add( new Variable( "B" ) );
		setVTemp[3][6].add( new VariableStart( "S" ) );
		setVTemp[3][6].add( new Variable( "S'" ) );
		setVTemp[3][7].add( new Variable( "A" ) );

		setVTemp[4][4].add( new Variable( "A" ) );
		setVTemp[4][4].add( new Variable( "N" ) );
		setVTemp[4][5].add( new VariableStart( "S" ) );
		setVTemp[4][5].add( new Variable( "S'" ) );
		setVTemp[4][6].add( new Variable( "A" ) );
		setVTemp[4][7].add( new Variable( "C" ) );

		setVTemp[5][5].add( new Variable( "E" ) );
		setVTemp[5][5].add( new Variable( "B" ) );
		setVTemp[5][6].add( new VariableStart( "S" ) );
		setVTemp[5][6].add( new Variable( "S'" ) );
		setVTemp[5][7].add( new Variable( "A" ) );

		setVTemp[6][6].add( new Variable( "A" ) );
		setVTemp[6][6].add( new Variable( "N" ) );
		setVTemp[6][7].add( new Variable( "C" ) );

		setVTemp[7][7].add( new Variable( "A" ) );
		setVTemp[7][7].add( new Variable( "N" ) );

		/*
		// reconstructing example matrix from scriptTI1
		setVTemp[0][0].add( new Variable( "A1" ) );
		setVTemp[0][0].add( new Variable( "N1" ) );
		setVTemp[0][1].add( new VariableStart( "S1" ) );
		setVTemp[0][1].add( new Variable( "S'1" ) );
		setVTemp[0][2].add( new Variable( "B1" ) );
		setVTemp[0][3].add( new Variable( "D3" ) );
		setVTemp[0][4].add( new Variable( "B1" ) );
		setVTemp[0][5].add( new Variable( "D3" ) );
		setVTemp[0][6].add( new Variable( "B1" ) );
		setVTemp[0][7].add( new VariableStart( "S1" ) );
		setVTemp[0][7].add( new Variable( "S'1" ) );

		setVTemp[1][1].add( new Variable( "E2" ) );
		setVTemp[1][1].add( new Variable( "B2" ) );
		setVTemp[1][2].add( new Variable( "D2" ) );
		setVTemp[1][4].add( new Variable( "D2" ) );
		setVTemp[1][6].add( new Variable( "D2" ) );
		setVTemp[1][7].add( new Variable( "B2" ) );

		setVTemp[2][2].add( new Variable( "E3" ) );
		setVTemp[2][2].add( new Variable( "B3" ) );
		setVTemp[2][3].add( new Variable( "D3" ) );
		setVTemp[2][4].add( new Variable( "B3" ) );
		setVTemp[2][5].add( new Variable( "D3" ) );
		setVTemp[2][6].add( new Variable( "B3" ) );
		setVTemp[2][7].add( new VariableStart( "S3" ) );
		setVTemp[2][7].add( new Variable( "S'3" ) );

		setVTemp[3][3].add( new Variable( "E4" ) );
		setVTemp[3][3].add( new Variable( "B4" ) );
		setVTemp[3][4].add( new VariableStart( "S4" ) );
		setVTemp[3][4].add( new Variable( "S'4" ) );
		setVTemp[3][5].add( new Variable( "B4" ) );
		setVTemp[3][6].add( new VariableStart( "S4" ) );
		setVTemp[3][6].add( new Variable( "S'4" ) );
		setVTemp[3][7].add( new Variable( "A4" ) );

		setVTemp[4][4].add( new Variable( "A5" ) );
		setVTemp[4][4].add( new Variable( "N5" ) );
		setVTemp[4][5].add( new VariableStart( "S5" ) );
		setVTemp[4][5].add( new Variable( "S'5" ) );
		setVTemp[4][6].add( new Variable( "A5" ) );
		setVTemp[4][7].add( new Variable( "C5" ) );

		setVTemp[5][5].add( new Variable( "E6" ) );
		setVTemp[5][5].add( new Variable( "B6" ) );
		setVTemp[5][6].add( new VariableStart( "S6" ) );
		setVTemp[5][6].add( new Variable( "S'6" ) );
		setVTemp[5][7].add( new Variable( "A6" ) );

		setVTemp[6][6].add( new Variable( "A7" ) );
		setVTemp[6][6].add( new Variable( "N7" ) );
		setVTemp[6][7].add( new Variable( "C7" ) );

		setVTemp[7][7].add( new Variable( "A8" ) );
		setVTemp[7][7].add( new Variable( "N8" ) );
		*/
		Util.printSetV( setVTemp, "setVSolution" );

		boolean temp = true;
		// optimizing possible, if one time temp == false, then stop executing the loops
		for ( int i = 0; i < wordLength; i++ ) {
			for ( int j = 0; j < wordLength; j++ ) {
				if ( !( setVTemp[i][j].containsAll( setV[i][j] ) ) &&
						setVTemp[i][j].size() == setV[i][j].size() ) {
					temp = false;
				}
			}
		}
		Assert.assertEquals( true, temp );
		System.out.println( "\nSetV from script is the same as the calculated SetV: " + temp );
	}

	@Test
	public void CYKCalculateSetVTestWithSS12() {
		System.out.println( "" );
		System.out.println( "############################" );
		System.out.println( "TestGrammar CYK: algorithmSimple with input Grammar from the SS12" );
		// @formatter:off
        Grammar grammar = new Grammar(new VariableStart("S"));
        Production productions[] = new Production[9];
        productions[0] = new Production(new VariableStart("S"), new VariableCompound(new Variable("A"), new Variable("B")));
        productions[1] = new Production(new VariableStart("S"), new VariableCompound(new Variable("B"), new Variable("C")));
        productions[2] = new Production(new Variable("A"), new VariableCompound(new Variable("B"), new Variable("A")));
        productions[3] = new Production(new Variable("A"), new Terminal("a"));
        productions[4] = new Production(new Variable("A"), new Terminal("c"));
        productions[5] = new Production(new Variable("B"), new VariableCompound(new Variable("C"), new Variable("B")));
        productions[6] = new Production(new Variable("B"), new Terminal("b"));
        productions[7] = new Production(new Variable("C"), new VariableCompound(new Variable("A"), new Variable("C")));
        productions[8] = new Production(new Variable("C"), new Terminal("c"));
        grammar.addProduction(productions);
        String word = "cbbaaccb";
        // @formatter:on
		Set<Variable> setV[][] = CYK.calculateSetV( grammar, Util.stringToTerminalList( word ) );
		Util.printSetV( setV, "setV calculated:" );

		int wordLength = word.length();
		Set<Variable>[][] setVTemp = new Set[wordLength][wordLength];
		for ( int i = 0; i < wordLength; i++ ) {
			for ( int j = 0; j < wordLength; j++ ) {
				setVTemp[i][j] = new HashSet<>();
			}
		}
		// reconstructing example matrix from SS12
		setVTemp[0][0].add( new Variable( "A" ) );
		setVTemp[0][0].add( new Variable( "C" ) );
		setVTemp[0][1].add( new VariableStart( "S" ) );
		setVTemp[0][1].add( new Variable( "B" ) );
		setVTemp[0][3].add( new Variable( "A" ) );
		setVTemp[0][5].add( new Variable( "C" ) );
		setVTemp[0][5].add( new VariableStart( "S" ) );
		setVTemp[0][6].add( new Variable( "C" ) );
		setVTemp[0][6].add( new VariableStart( "S" ) );
		setVTemp[0][7].add( new VariableStart( "S" ) );
		setVTemp[0][7].add( new Variable( "B" ) );

		setVTemp[1][1].add( new Variable( "B" ) );
		setVTemp[1][3].add( new Variable( "A" ) );
		setVTemp[1][5].add( new Variable( "C" ) );
		setVTemp[1][5].add( new VariableStart( "S" ) );
		setVTemp[1][6].add( new Variable( "C" ) );
		setVTemp[1][6].add( new VariableStart( "S" ) );
		setVTemp[1][7].add( new Variable( "B" ) );
		setVTemp[1][7].add( new VariableStart( "S" ) );

		setVTemp[2][2].add( new Variable( "B" ) );
		setVTemp[2][3].add( new Variable( "A" ) );
		setVTemp[2][5].add( new Variable( "C" ) );
		setVTemp[2][5].add( new VariableStart( "S" ) );
		setVTemp[2][6].add( new Variable( "C" ) );
		setVTemp[2][6].add( new VariableStart( "S" ) );
		setVTemp[2][7].add( new VariableStart( "S" ) );
		setVTemp[2][7].add( new Variable( "B" ) );

		setVTemp[3][3].add( new Variable( "A" ) );
		setVTemp[3][5].add( new Variable( "C" ) );
		setVTemp[3][6].add( new Variable( "C" ) );
		setVTemp[3][7].add( new VariableStart( "S" ) );
		setVTemp[3][7].add( new Variable( "B" ) );

		setVTemp[4][4].add( new Variable( "A" ) );
		setVTemp[4][5].add( new Variable( "C" ) );
		setVTemp[4][6].add( new Variable( "C" ) );
		setVTemp[4][7].add( new VariableStart( "S" ) );
		setVTemp[4][7].add( new Variable( "B" ) );

		setVTemp[5][5].add( new Variable( "A" ) );
		setVTemp[5][5].add( new Variable( "C" ) );
		setVTemp[5][6].add( new Variable( "C" ) );
		setVTemp[5][7].add( new VariableStart( "S" ) );
		setVTemp[5][7].add( new Variable( "B" ) );

		setVTemp[6][6].add( new Variable( "A" ) );
		setVTemp[6][6].add( new Variable( "C" ) );
		setVTemp[6][7].add( new Variable( "B" ) );
		setVTemp[6][7].add( new VariableStart( "S" ) );

		setVTemp[7][7].add( new Variable( "B" ) );
		/*
		// reconstructing example matrix from SS12
		setVTemp[0][0].add( new Variable( "A1" ) );
		setVTemp[0][0].add( new Variable( "C1" ) );
		setVTemp[0][1].add( new VariableStart( "S1" ) );
		setVTemp[0][1].add( new Variable( "B1" ) );
		setVTemp[0][3].add( new Variable( "A2" ) );
		setVTemp[0][5].add( new Variable( "C1" ) );
		setVTemp[0][5].add( new Variable( "C4" ) );
		setVTemp[0][5].add( new VariableStart( "S2" ) );
		setVTemp[0][6].add( new Variable( "C1" ) );
		setVTemp[0][6].add( new Variable( "C4" ) );
		setVTemp[0][6].add( new VariableStart( "S2" ) );
		setVTemp[0][7].add( new VariableStart( "S1" ) );
		setVTemp[0][7].add( new VariableStart( "S4" ) );
		setVTemp[0][7].add( new Variable( "B1" ) );
		setVTemp[0][7].add( new Variable( "B6" ) );
		setVTemp[0][7].add( new Variable( "B7" ) );

		setVTemp[1][1].add( new Variable( "B2" ) );
		setVTemp[1][3].add( new Variable( "A2" ) );
		setVTemp[1][5].add( new Variable( "C4" ) );
		setVTemp[1][5].add( new VariableStart( "S2" ) );
		setVTemp[1][6].add( new Variable( "C4" ) );
		setVTemp[1][6].add( new VariableStart( "S2" ) );
		setVTemp[1][7].add( new Variable( "B6" ) );
		setVTemp[1][7].add( new Variable( "B7" ) );
		setVTemp[1][7].add( new VariableStart( "S4" ) );

		setVTemp[2][2].add( new Variable( "B3" ) );
		setVTemp[2][3].add( new Variable( "A3" ) );
		setVTemp[2][5].add( new Variable( "C4" ) );
		setVTemp[2][5].add( new VariableStart( "S3" ) );
		setVTemp[2][6].add( new Variable( "C4" ) );
		setVTemp[2][6].add( new VariableStart( "S3" ) );
		setVTemp[2][7].add( new VariableStart( "S4" ) );
		setVTemp[2][7].add( new Variable( "B6" ) );
		setVTemp[2][7].add( new Variable( "B7" ) );

		setVTemp[3][3].add( new Variable( "A5" ) );
		setVTemp[3][5].add( new Variable( "C5" ) );
		setVTemp[3][6].add( new Variable( "C5" ) );
		setVTemp[3][7].add( new VariableStart( "S5" ) );
		setVTemp[3][7].add( new Variable( "B6" ) );
		setVTemp[3][7].add( new Variable( "B7" ) );

		setVTemp[4][4].add( new Variable( "A5" ) );
		setVTemp[4][5].add( new Variable( "C5" ) );
		setVTemp[4][6].add( new Variable( "C"5 ) );
		setVTemp[4][7].add( new VariableStart( "S5" ) );
		setVTemp[4][7].add( new Variable( "B6" ) );
		setVTemp[4][7].add( new Variable( "B7" ) );

		setVTemp[5][5].add( new Variable( "A6" ) );
		setVTemp[5][5].add( new Variable( "C6" ) );
		setVTemp[5][6].add( new Variable( "C6" ) );
		setVTemp[5][7].add( new VariableStart( "S6" ) );
		setVTemp[5][7].add( new Variable( "B6" ) );
		setVTemp[5][7].add( new Variable( "B7" ) );

		setVTemp[6][6].add( new Variable( "A7" ) );
		setVTemp[6][6].add( new Variable( "C7" ) );
		setVTemp[6][7].add( new Variable( "B7" ) );
		setVTemp[6][7].add( new VariableStart( "S7" ) );

		setVTemp[7][7].add( new Variable( "B8" ) );
		 */
		Util.printSetV( setVTemp, "setVSolution" );

		boolean temp = true;
		// optimizing possible, if one time temp == false, then stop executing the loops
		for ( int i = 0; i < wordLength; i++ ) {
			for ( int j = 0; j < wordLength; j++ ) {
				if ( !( setVTemp[i][j].containsAll( setV[i][j] ) ) &&
						setVTemp[i][j].size() == setV[i][j].size() ) {
					temp = false;
				}
			}
		}
		Assert.assertEquals( true, temp );
		System.out.println( "\nSetV from script is the same as the calculated SetV: " + temp );

	}

	@Test
	public void CYKCalculateSetVTestWithSS13() {
		System.out.println( "" );
		System.out.println( "############################" );
		System.out.println( "TestGrammar CYK: algorithmSimple with input Grammar from the SS13" );
		// @formatter:off
        Grammar grammar = new Grammar(new VariableStart("S"));
        Production productions[] = new Production[10];
        productions[0] = new Production(new VariableStart("S"), new VariableCompound(new Variable("A"), new Variable("A")));
        productions[1] = new Production(new VariableStart("S"), new VariableCompound(new Variable("A"), new Variable("C")));
        productions[2] = new Production(new VariableStart("S"), new VariableCompound(new Variable("C"), new Variable("B")));
        productions[3] = new Production(new Variable("A"), new VariableCompound(new Variable("B"), new Variable("C")));
        productions[4] = new Production(new Variable("A"), new Terminal("a"));
        productions[5] = new Production(new Variable("A"), new Terminal("b"));
        productions[6] = new Production(new Variable("B"), new VariableCompound(new Variable("B"), new Variable("B")));
        productions[7] = new Production(new Variable("B"), new Terminal("b"));
        productions[8] = new Production(new Variable("C"), new VariableCompound(new Variable("A"), new Variable("C")));
        productions[9] = new Production(new Variable("C"), new Terminal("c"));
        // @formatter:on
		grammar.addProduction( productions );
		String word = "bbacbc";

		Set<Variable> setV[][] = CYK.calculateSetV( grammar, Util.stringToTerminalList( word ) );
		Util.printSetV( setV, "setV calculated:" );

		int wordLength = word.length();
		Set<Variable>[][] setVTemp = new Set[wordLength][wordLength];
		for ( int i = 0; i < wordLength; i++ ) {
			for ( int j = 0; j < wordLength; j++ ) {
				setVTemp[i][j] = new HashSet<>();
			}
		}
		// reconstructing example matrix from scriptTI1
		setVTemp[0][0].add( new Variable( "A" ) );
		setVTemp[0][0].add( new Variable( "B" ) );
		setVTemp[0][1].add( new VariableStart( "S" ) );
		setVTemp[0][1].add( new Variable( "B" ) );
		setVTemp[0][3].add( new VariableStart( "S" ) );
		setVTemp[0][3].add( new Variable( "C" ) );
		setVTemp[0][3].add( new Variable( "A" ) );
		setVTemp[0][4].add( new VariableStart( "S" ) );
		setVTemp[0][5].add( new VariableStart( "S" ) );
		setVTemp[0][5].add( new Variable( "C" ) );
		setVTemp[0][5].add( new Variable( "A" ) );

		setVTemp[1][1].add( new Variable( "A" ) );
		setVTemp[1][1].add( new Variable( "B" ) );
		setVTemp[1][2].add( new VariableStart( "S" ) );
		setVTemp[1][3].add( new Variable( "C" ) );
		setVTemp[1][3].add( new VariableStart( "S" ) );
		setVTemp[1][3].add( new Variable( "A" ) );
		setVTemp[1][4].add( new VariableStart( "S" ) );
		setVTemp[1][5].add( new Variable( "C" ) );
		setVTemp[1][5].add( new VariableStart( "S" ) );

		setVTemp[2][2].add( new Variable( "A" ) );
		setVTemp[2][3].add( new VariableStart( "S" ) );
		setVTemp[2][3].add( new Variable( "C" ) );
		setVTemp[2][4].add( new VariableStart( "S" ) );

		setVTemp[3][3].add( new Variable( "C" ) );
		setVTemp[3][4].add( new VariableStart( "S" ) );

		setVTemp[4][4].add( new Variable( "A" ) );
		setVTemp[4][4].add( new Variable( "B" ) );
		setVTemp[4][5].add( new Variable( "C" ) );
		setVTemp[4][5].add( new VariableStart( "S" ) );
		setVTemp[4][5].add( new Variable( "A" ) );

		setVTemp[5][5].add( new Variable( "C" ) );
		/*
		// reconstructing example matrix from scriptTI1
		setVTemp[0][0].add( new Variable( "A1" ) );
		setVTemp[0][0].add( new Variable( "B1" ) );
		setVTemp[0][1].add( new VariableStart( "S1" ) );
		setVTemp[0][1].add( new Variable( "B1" ) );
		setVTemp[0][3].add( new VariableStart( "S1" ) );
		setVTemp[0][3].add( new Variable( "C1" ) );
		setVTemp[0][3].add( new Variable( "A1" ) );
		setVTemp[0][3].add( new Variable( "A2" ) );
		setVTemp[0][4].add( new VariableStart( "S4" ) );
		setVTemp[0][5].add( new VariableStart( "S1" ) );
		setVTemp[0][5].add( new VariableStart( "S4" ) );
		setVTemp[0][5].add( new Variable( "C1" ) );
		setVTemp[0][5].add( new Variable( "C4" ) );
		setVTemp[0][5].add( new Variable( "A1" ) );

		setVTemp[1][1].add( new Variable( "A2" ) );
		setVTemp[1][1].add( new Variable( "B2" ) );
		setVTemp[1][2].add( new VariableStart( "S2" ) );
		setVTemp[1][3].add( new Variable( "C2" ) );
		setVTemp[1][3].add( new VariableStart( "S2" ) );
		setVTemp[1][3].add( new Variable( "A2" ) );
		setVTemp[1][4].add( new VariableStart( "S4" ) );
		setVTemp[1][5].add( new Variable( "C4" ) );
		setVTemp[1][5].add( new VariableStart( "S4" ) );

		setVTemp[2][2].add( new Variable( "A3" ) );
		setVTemp[2][3].add( new VariableStart( "S3" ) );
		setVTemp[2][3].add( new Variable( "C3" ) );
		setVTemp[2][4].add( new VariableStart( "S4" ) );

		setVTemp[3][3].add( new Variable( "C4" ) );
		setVTemp[3][4].add( new VariableStart( "S4" ) );

		setVTemp[4][4].add( new Variable( "A5" ) );
		setVTemp[4][4].add( new Variable( "B5" ) );
		setVTemp[4][5].add( new Variable( "C5" ) );
		setVTemp[4][5].add( new VariableStart( "S5" ) );
		setVTemp[4][5].add( new Variable( "A5" ) );

		setVTemp[5][5].add( new Variable( "C6" ) );
		 */

		Util.printSetV( setVTemp, "setVSolution" );

		boolean temp = true;
		// optimizing possible, if one time temp == false, then stop executing the loops
		for ( int i = 0; i < wordLength; i++ ) {
			for ( int j = 0; j < wordLength; j++ ) {
				if ( !( setVTemp[i][j].containsAll( setV[i][j] ) ) &&
						setVTemp[i][j].size() == setV[i][j].size() ) {
					temp = false;
				}
			}
		}
		Assert.assertEquals( true, temp );
		System.out.println( "\nSetV from script is the same as the calculated SetV: " + temp );
	}
}
