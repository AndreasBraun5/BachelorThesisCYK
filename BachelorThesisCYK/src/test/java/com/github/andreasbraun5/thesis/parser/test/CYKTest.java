package com.github.andreasbraun5.thesis.parser.test;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.github.andreasbraun5.thesis.generator.WordGeneratorDiceRoll;
import com.github.andreasbraun5.thesis.grammar.Grammar;
import com.github.andreasbraun5.thesis.grammar.GrammarProperties;
import com.github.andreasbraun5.thesis.grammar.Production;
import com.github.andreasbraun5.thesis.grammar.Terminal;
import com.github.andreasbraun5.thesis.grammar.Variable;
import com.github.andreasbraun5.thesis.grammar.VariableCompound;
import com.github.andreasbraun5.thesis.grammar.VariableKWrapper;
import com.github.andreasbraun5.thesis.grammar.VariableStart;
import com.github.andreasbraun5.thesis.parser.CYK;
import com.github.andreasbraun5.thesis.util.Util;

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
		System.out.println( "ResultCalculator CYK: AlwaysTrue" );
		// Define GrammarProperties
		GrammarProperties grammarProperties = new GrammarProperties( new VariableStart( "S" ) )
				.addTerminals( new Terminal( "a" ) );
		grammarProperties.grammarPropertiesGrammarRestrictions.setMaxNumberOfVarsPerCell( 3 );
		grammarProperties.grammarPropertiesGrammarRestrictions.setSizeOfWord( 10 );
		// Generate word
		// @formatter:off
		String word = WordGeneratorDiceRoll.generateWordAsString( grammarProperties );
		// Generate Grammar
		Grammar grammar = new Grammar( new VariableStart( "S" ) );
		grammar.addProduction(
				new Production( new VariableStart( "S" ), new Terminal( "a" ) ),
				new Production( new VariableStart( "S" ),
								new VariableCompound( new VariableStart( "S" ), new VariableStart( "S" ) ) )
		);
		// @formatter:on
		System.out.println( grammar );
		// Check for integrity
		System.out.println( Util.getSetVVariableAsStringForPrintingAsLowerTriangularMatrix(
				Util.getVarsFromSetDoubleArray(
						CYK.calculateSetVAdvanced( grammar, Util.stringToTerminalList( word ) ) ), "setV" )
		);
		Assert.assertEquals( "The grammar and the word aren't compatible, but should be.", true, CYK.algorithmAdvanced(
				grammar, Util.stringToTerminalList( word ) )
		);
	}

	@Test
	public void CYKCalculateSetVTestWithScript() {
		System.out.println( "" );
		System.out.println( "############################" );
		System.out.println( "ResultCalculator CYK: algorithmSimple with input Grammar from the TI1 script" );
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
		Set<VariableKWrapper> setVAdvanced[][] = CYK.calculateSetVAdvanced( grammar, Util.stringToTerminalList( word ));
		Set<Variable> setV[][] = Util.getVarsFromSetDoubleArray( setVAdvanced );
		System.out.println( Util.getSetVVariableAsStringForPrintingAsLowerTriangularMatrix(
				setV,
				"setV calculated:"
		) );
		// @formatter:on


		int wordLength = word.length();
		@SuppressWarnings("unchecked")
		Set<Variable>[][] setVTemp = new Set[wordLength][wordLength];
		for ( int i = 0; i < wordLength; i++ ) {
			for ( int j = 0; j < wordLength; j++ ) {
				setVTemp[i][j] = new HashSet<>();
			}
		}
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

		System.out.println( Util.getSetVVariableAsStringForPrintingAsLowerTriangularMatrix(
				setVTemp,
				"setVSolution"
		) );
		System.out.println( Util.getSetVVariableAsStringForPrintingAsUpperTriangularMatrix(
				setVTemp,
				"setVSolution"
		) );
		boolean temp = checkSetVCalculated( setVTemp, setV, wordLength );
		Assert.assertEquals( true, temp );
		System.out.println( "\nSetV from script is the same as the calculated SetV: " + temp );
	}

	@Test
	public void CYKCalculateSetVTestWithSS12() {
		System.out.println( "" );
		System.out.println( "############################" );
		System.out.println( "ResultCalculator CYK: algorithmSimple with input Grammar from the SS12" );
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
		Set<VariableKWrapper> setVAdvanced[][] = CYK.calculateSetVAdvanced( grammar, Util.stringToTerminalList( word ));
		Set<Variable> setV[][] = Util.getVarsFromSetDoubleArray( setVAdvanced );
		System.out.println( Util.getSetVVariableAsStringForPrintingAsLowerTriangularMatrix(
				setV, "setV calculated:" )
		);
        // @formatter:on
		int wordLength = word.length();
		@SuppressWarnings("unchecked")
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

		System.out.println( Util.getSetVVariableAsStringForPrintingAsLowerTriangularMatrix(
				setVTemp,
				"setVSolution"
		) );
		boolean temp = checkSetVCalculated( setVTemp, setV, wordLength );
		Assert.assertEquals( true, temp );
		System.out.println( "\nSetV from script is the same as the calculated SetV: " + temp );

	}

	@Test
	public void CYKCalculateSetVTestWithSS13() {
		System.out.println( "" );
		System.out.println( "############################" );
		System.out.println( "ResultCalculator CYK: algorithmSimple with input Grammar from the SS13" );
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
		grammar.addProduction( productions );
		String word = "bbacbc";
		Set<VariableKWrapper> setVAdvanced[][] = CYK.calculateSetVAdvanced( grammar, Util.stringToTerminalList( word ));
		Set<Variable> setV[][] = Util.getVarsFromSetDoubleArray( setVAdvanced );
		System.out.println( Util.getSetVVariableAsStringForPrintingAsLowerTriangularMatrix(
				setV, "setV calculated:" )
		);
		// @formatter:on

		int wordLength = word.length();
		@SuppressWarnings("unchecked")
		Set<Variable>[][] setVTemp = new Set[wordLength][wordLength];
		for ( int i = 0; i < wordLength; i++ ) {
			for ( int j = 0; j < wordLength; j++ ) {
				setVTemp[i][j] = new HashSet<>();
			}
		}
		// reconstructing example matrix
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

		System.out.println( Util.getSetVVariableAsStringForPrintingAsLowerTriangularMatrix(
				setVTemp,
				"setVSolution"
		) );
		boolean temp = checkSetVCalculated( setVTemp, setV, wordLength );
		Assert.assertEquals( true, temp );
		System.out.println( "\nSetV from script is the same as the calculated SetV: " + temp );
	}

	@Test
	public void CYKCalculateSetVariableKTestWithSS12() {
		System.out.println( "" );
		System.out.println( "############################" );
		System.out.println( "ResultCalculator CYK: algorithmAdvanced with input Grammar from the SS12" );
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

		Set<VariableKWrapper> setV[][] = CYK.calculateSetVAdvanced( grammar, Util.stringToTerminalList( word ) );
		System.out.println( Util.getSetVVariableKAsStringForPrintingAsLowerTriangularMatrix(
				setV,
				"setV calculated:"
		) );

		int wordLength = word.length();
		@SuppressWarnings("unchecked")
		Set<VariableKWrapper>[][] setVTemp = new Set[wordLength][wordLength];
		for ( int i = 0; i < wordLength; i++ ) {
			for ( int j = 0; j < wordLength; j++ ) {
				setVTemp[i][j] = new HashSet<>();
			}
		}

		// reconstructing example matrix from SS12
		setVTemp[0][0].add( new VariableKWrapper( new Variable( "A" ), 1 ) );
		setVTemp[0][0].add( new VariableKWrapper( new Variable( "C" ), 1 ) );
		setVTemp[0][1].add( new VariableKWrapper( new VariableStart( "S" ), 1 ) );
		setVTemp[0][1].add( new VariableKWrapper( new Variable( "B" ), 1 ) );
		setVTemp[0][3].add( new VariableKWrapper( new Variable( "A" ), 2 ) );
		setVTemp[0][5].add( new VariableKWrapper( new Variable( "C" ), 1 ) );
		setVTemp[0][5].add( new VariableKWrapper( new Variable( "C" ), 4 ) );
		setVTemp[0][5].add( new VariableKWrapper( new VariableStart( "S" ), 2 ) );
		setVTemp[0][6].add( new VariableKWrapper( new Variable( "C" ), 1 ) );
		setVTemp[0][6].add( new VariableKWrapper( new Variable( "C" ), 4 ) );
		setVTemp[0][6].add( new VariableKWrapper( new VariableStart( "S" ), 2 ) );
		setVTemp[0][7].add( new VariableKWrapper( new VariableStart( "S" ), 1 ) );
		setVTemp[0][7].add( new VariableKWrapper( new VariableStart( "S" ), 4 ) );
		setVTemp[0][7].add( new VariableKWrapper( new Variable( "B" ), 1 ) );
		setVTemp[0][7].add( new VariableKWrapper( new Variable( "B" ), 6 ) );
		setVTemp[0][7].add( new VariableKWrapper( new Variable( "B" ), 7 ) );

		setVTemp[1][1].add( new VariableKWrapper( new Variable( "B" ), 2 ) );
		setVTemp[1][3].add( new VariableKWrapper( new Variable( "A" ), 2 ) );
		setVTemp[1][5].add( new VariableKWrapper( new Variable( "C" ), 4 ) );
		setVTemp[1][5].add( new VariableKWrapper( new VariableStart( "S" ), 2 ) );
		setVTemp[1][6].add( new VariableKWrapper( new Variable( "C" ), 4 ) );
		setVTemp[1][6].add( new VariableKWrapper( new VariableStart( "S" ), 2 ) );
		setVTemp[1][7].add( new VariableKWrapper( new Variable( "B" ), 6 ) );
		setVTemp[1][7].add( new VariableKWrapper( new Variable( "B" ), 7 ) );
		setVTemp[1][7].add( new VariableKWrapper( new VariableStart( "S" ), 4 ) );

		setVTemp[2][2].add( new VariableKWrapper( new Variable( "B" ), 3 ) );
		setVTemp[2][3].add( new VariableKWrapper( new Variable( "A" ), 3 ) );
		setVTemp[2][5].add( new VariableKWrapper( new Variable( "C" ), 4 ) );
		setVTemp[2][5].add( new VariableKWrapper( new VariableStart( "S" ), 3 ) );
		setVTemp[2][6].add( new VariableKWrapper( new Variable( "C" ), 4 ) );
		setVTemp[2][6].add( new VariableKWrapper( new VariableStart( "S" ), 3 ) );
		setVTemp[2][7].add( new VariableKWrapper( new VariableStart( "S" ), 4 ) );
		setVTemp[2][7].add( new VariableKWrapper( new Variable( "B" ), 6 ) );
		setVTemp[2][7].add( new VariableKWrapper( new Variable( "B" ), 7 ) );

		setVTemp[3][3].add( new VariableKWrapper( new Variable( "A" ), 4 ) );
		setVTemp[3][5].add( new VariableKWrapper( new Variable( "C" ), 4 ) );
		setVTemp[3][6].add( new VariableKWrapper( new Variable( "C" ), 4 ) );
		setVTemp[3][7].add( new VariableKWrapper( new VariableStart( "S" ), 4 ) );
		setVTemp[3][7].add( new VariableKWrapper( new Variable( "B" ), 6 ) );
		setVTemp[3][7].add( new VariableKWrapper( new Variable( "B" ), 7 ) );

		setVTemp[4][4].add( new VariableKWrapper( new Variable( "A" ), 5 ) );
		setVTemp[4][5].add( new VariableKWrapper( new Variable( "C" ), 5 ) );
		setVTemp[4][6].add( new VariableKWrapper( new Variable( "C" ), 5 ) );
		setVTemp[4][7].add( new VariableKWrapper( new VariableStart( "S" ), 5 ) );
		setVTemp[4][7].add( new VariableKWrapper( new Variable( "B" ), 6 ) );
		setVTemp[4][7].add( new VariableKWrapper( new Variable( "B" ), 7 ) );

		setVTemp[5][5].add( new VariableKWrapper( new Variable( "A" ), 6 ) );
		setVTemp[5][5].add( new VariableKWrapper( new Variable( "C" ), 6 ) );
		setVTemp[5][6].add( new VariableKWrapper( new Variable( "C" ), 6 ) );
		setVTemp[5][7].add( new VariableKWrapper( new VariableStart( "S" ), 6 ) );
		setVTemp[5][7].add( new VariableKWrapper( new Variable( "B" ), 6 ) );
		setVTemp[5][7].add( new VariableKWrapper( new Variable( "B" ), 7 ) );

		setVTemp[6][6].add( new VariableKWrapper( new Variable( "A" ), 7 ) );
		setVTemp[6][6].add( new VariableKWrapper( new Variable( "C" ), 7 ) );
		setVTemp[6][7].add( new VariableKWrapper( new Variable( "B" ), 7 ) );
		setVTemp[6][7].add( new VariableKWrapper( new VariableStart( "S" ), 7 ) );

		setVTemp[7][7].add( new VariableKWrapper( new Variable( "B" ), 8 ) );

		System.out.println( Util.getSetVVariableKAsStringForPrintingAsLowerTriangularMatrix(
				setVTemp,
				"setVSolution"
		) );
		boolean temp = checkSetVCalculated( setVTemp, setV, wordLength );
		Assert.assertEquals( true, temp );
		System.out.println( "\nSetV from script is the same as the calculated SetV: " + temp );
	}

	@Test
	public void CYKCalculateSetVVariableKTestWithSS13() {
		System.out.println( "" );
		System.out.println( "############################" );
		System.out.println( "ResultCalculator CYK: algorithmAdvanced with input Grammar from the SS13" );
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

		Set<VariableKWrapper> setV[][] = CYK.calculateSetVAdvanced( grammar, Util.stringToTerminalList( word ) );
		System.out.println( Util.getSetVVariableKAsStringForPrintingAsLowerTriangularMatrix(
				setV,
				"setV calculated:"
		) );

		int wordLength = word.length();
		@SuppressWarnings("unchecked")
		Set<VariableKWrapper>[][] setVTemp = new Set[wordLength][wordLength];
		for ( int i = 0; i < wordLength; i++ ) {
			for ( int j = 0; j < wordLength; j++ ) {
				setVTemp[i][j] = new HashSet<>();
			}
		}

		// reconstructing example matrix
		setVTemp[0][0].add( new VariableKWrapper( new Variable( "A" ), 1 ) );
		setVTemp[0][0].add( new VariableKWrapper( new Variable( "B" ), 1 ) );
		setVTemp[0][1].add( new VariableKWrapper( new VariableStart( "S" ), 1 ) );
		setVTemp[0][1].add( new VariableKWrapper( new Variable( "B" ), 1 ) );
		setVTemp[0][3].add( new VariableKWrapper( new VariableStart( "S" ), 1 ) );
		setVTemp[0][3].add( new VariableKWrapper( new Variable( "C" ), 1 ) );
		setVTemp[0][3].add( new VariableKWrapper( new Variable( "A" ), 1 ) );
		setVTemp[0][3].add( new VariableKWrapper( new Variable( "A" ), 2 ) );
		setVTemp[0][4].add( new VariableKWrapper( new VariableStart( "S" ), 4 ) );
		setVTemp[0][5].add( new VariableKWrapper( new VariableStart( "S" ), 1 ) );
		setVTemp[0][5].add( new VariableKWrapper( new VariableStart( "S" ), 4 ) );
		setVTemp[0][5].add( new VariableKWrapper( new Variable( "C" ), 1 ) );
		setVTemp[0][5].add( new VariableKWrapper( new Variable( "C" ), 4 ) );
		setVTemp[0][5].add( new VariableKWrapper( new Variable( "A" ), 1 ) );

		setVTemp[1][1].add( new VariableKWrapper( new Variable( "A" ), 2 ) );
		setVTemp[1][1].add( new VariableKWrapper( new Variable( "B" ), 2 ) );
		setVTemp[1][2].add( new VariableKWrapper( new VariableStart( "S" ), 2 ) );
		setVTemp[1][3].add( new VariableKWrapper( new Variable( "C" ), 2 ) );
		setVTemp[1][3].add( new VariableKWrapper( new VariableStart( "S" ), 2 ) );
		setVTemp[1][3].add( new VariableKWrapper( new Variable( "A" ), 2 ) );
		setVTemp[1][4].add( new VariableKWrapper( new VariableStart( "S" ), 4 ) );
		setVTemp[1][5].add( new VariableKWrapper( new Variable( "C" ), 4 ) );
		setVTemp[1][5].add( new VariableKWrapper( new VariableStart( "S" ), 4 ) );

		setVTemp[2][2].add( new VariableKWrapper( new Variable( "A" ), 3 ) );
		setVTemp[2][3].add( new VariableKWrapper( new VariableStart( "S" ), 3 ) );
		setVTemp[2][3].add( new VariableKWrapper( new Variable( "C" ), 3 ) );
		setVTemp[2][4].add( new VariableKWrapper( new VariableStart( "S" ), 4 ) );

		setVTemp[3][3].add( new VariableKWrapper( new Variable( "C" ), 4 ) );
		setVTemp[3][4].add( new VariableKWrapper( new VariableStart( "S" ), 4 ) );

		setVTemp[4][4].add( new VariableKWrapper( new Variable( "A" ), 5 ) );
		setVTemp[4][4].add( new VariableKWrapper( new Variable( "B" ), 5 ) );
		setVTemp[4][5].add( new VariableKWrapper( new Variable( "C" ), 5 ) );
		setVTemp[4][5].add( new VariableKWrapper( new VariableStart( "S" ), 5 ) );
		setVTemp[4][5].add( new VariableKWrapper( new Variable( "A" ), 5 ) );

		setVTemp[5][5].add( new VariableKWrapper( new Variable( "C" ), 6 ) );

		System.out.println( Util.getSetVVariableKAsStringForPrintingAsLowerTriangularMatrix(
				setVTemp,
				"setVSolution"
		) );
		boolean temp = checkSetVCalculated( setVTemp, setV, wordLength );
		Assert.assertEquals( true, temp );
		System.out.println( "\nSetV from script is the same as the calculated SetV: " + temp );
	}


	@Test
	public void CYKCalculateSetVVariableKTestWithScript() {
		System.out.println( "" );
		System.out.println( "############################" );
		System.out.println( "ResultCalculator CYK: algorithmAdvanced with input Grammar from the TI1 script" );
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

		Set<VariableKWrapper> setV[][] = CYK.calculateSetVAdvanced( grammar, Util.stringToTerminalList( word ) );
		System.out.println( Util.getSetVVariableKAsStringForPrintingAsLowerTriangularMatrix(
				setV,
				"setV calculated:"
		) );

		int wordLength = word.length();
		@SuppressWarnings("unchecked")
		Set<VariableKWrapper>[][] setVTemp = new Set[wordLength][wordLength];
		for ( int i = 0; i < wordLength; i++ ) {
			for ( int j = 0; j < wordLength; j++ ) {
				setVTemp[i][j] = new HashSet<>();
			}
		}
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

		System.out.println( Util.getSetVVariableKAsStringForPrintingAsLowerTriangularMatrix(
				setVTemp,
				"setVSolution"
		) );
		boolean temp = checkSetVCalculated( setVTemp, setV, wordLength );
		Assert.assertEquals( true, checkSetVCalculated( setVTemp, setV, wordLength ) );
		System.out.println( "\nSetV from script is the same as the calculated SetV: " + temp );
	}


	private static <T extends Variable> boolean checkSetVCalculated(
			Set<T>[][] setVTemp,
			Set<T>[][] setV,
			int wordLength) {
		boolean temp = true;
		for ( int i = 0; i < wordLength; i++ ) {
			for ( int j = 0; j < wordLength; j++ ) {
				// TODO Martin: Why not possible? Set<T>[][] setVTemp2 = new HashSet<T>(). setVTemp.addAll(setVTemp)
				if ( !setVTemp[i][j].containsAll( setV[i][j] ) ||
						!setV[i][j].containsAll( setV[i][j] ) ||
						setVTemp[i][j].size() != setV[i][j].size() ) {
					temp = false;
				}
			}
		}
		return temp;
	}

}
