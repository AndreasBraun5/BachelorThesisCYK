package com.github.andreasbraun5.thesis.latex;

/**
 * Created by Andreas Braun on 08.03.2017.
 * https://github.com/AndreasBraun5/
 */
public class CenteredTikzPicture {

	public static String beginToString(){
		StringBuilder str = new StringBuilder( "" );
		str.append( "\\begin{center}\n" +
							"\\resizebox{\\linewidth}{!}{\n" +
							"\\begin{tikzpicture}[baseline]\n");
		return str.toString();
	}

	public static String endToString(){
		StringBuilder str = new StringBuilder( "" );
		str.append( "\\end{tikzpicture}\n" +
							"}\n" +
							"\\end{center}\n");
		return str.toString();
	}
}
