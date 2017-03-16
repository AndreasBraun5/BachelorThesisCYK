package com.github.andreasbraun5.thesis.latex;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import com.github.andreasbraun5.thesis.exception.FileRuntimeException;

/**
 * Created by Andreas Braun on 14.03.2017.
 * https://github.com/AndreasBraun5/
 */
public class WriteToTexFile {
	/**
	 * Storing the result output in a text file.
	 */

	public static void createOutputDirectory() {
		File dir = new File( "./Output" );
		if ( dir.mkdirs() ) {
			throw new FileRuntimeException( "Output directory was not created." );
		}
	}

	public static void writeToTexFile(String filename, String data) {
		try {
			File file = new File( "./Output/" + filename + ".tex" );
			PrintWriter out = new PrintWriter( file );
			out.println( data );
			out.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void writeToMainTexFile(String... input) {
		StringBuilder str = new StringBuilder();
		str.append( "% allgem. Dokumentenformat\n" +
							"\\documentclass[a4paper,12pt,headsepline]{scrartcl}\n" +
							"\n" +
							"% weitere Pakete\n" +
							"% Grafiken aus PNG Dateien einbinden\n" +
							"\\usepackage{graphicx}\n" +
							"\n" +
							"%for psuedo code\n" +
							"\\usepackage[linesnumbered, ruled]{algorithm2e} \n" +
							"%\n" +
							"\\newcommand{\\nosemic}{\\SetEndCharOfAlgoLine{\\relax}}% Drop semi-colon ;\n" +
							"\\newcommand{\\dosemic}{\\SetEndCharOfAlgoLine{\\string;}}% Reinstate\n" +
							"\\newcommand{\\pushline}{\\Indp}% Indent\n" +
							"\\newcommand{\\popline}{\\Indm\\dosemic}% Undent\n" +
							"%\\stackMath\n" +
							"%\\[\\def\\stacktype{L}\\setstackgap{L}{.4ex}\n" +
							"%x\\mathrel{\\stackon{\\cup}{\\scriptscriptstyle+}}y\n" +
							"%\\mathrel{\\stackon{\\cup}{\\scriptscriptstyle-}}z\n" +
							"%\\]\n" +
							"%\n" +
							"% Incompatibility with ctable, both implement package transparency\n" +
							"% http://tex.stackexchange.com/questions/253401/tikz-and-ctable-incompatibility-gives-error-when-printing\n" +
							"\\usepackage{tikz}\n" +
							"\\usetikzlibrary{calc, shapes, arrows}\n" +
							"% \\circled is used for circling chars\n" +
							"\\newcommand*\\circled[1]{\\tikz[baseline=(char.base)]{\\node[shape=circle,draw,inner sep=2pt] (char) {#1};}}\n" +
							"\n" +
							"% Eurozeichen einbinden\n" +
							"\\usepackage[right]{eurosym}\n" +
							"\n" +
							"% Incompatibility tikz, both implement package transparency\n" +
							"%\\usepackage{ctable}\n" +
							"\n" +
							"% Umlaute unter UTF8 nutzen\n" +
							"\\usepackage[utf8]{inputenc}\n" +
							"\n" +
							"% Zeichenencoding\n" +
							"\\usepackage[T1]{fontenc}\n" +
							"\n" +
							"\\usepackage{lmodern}\n" +
							"\\usepackage{fix-cm}\n" +
							"\n" +
							"\\usepackage{svg}\n" +
							"\n" +
							"% floatende Bilder ermöglichen\n" +
							"%\\usepackage{floatflt}\n" +
							"\n" +
							"% mehrseitige Tabellen ermöglichen\n" +
							"\\usepackage{longtable}\n" +
							"\n" +
							"\\usepackage{afterpage}\n" +
							"\n" +
							"% Unterstützung für Schriftarten\n" +
							"%\\newcommand{\\changefont}[3]{ \n" +
							"%\\fontfamily{#1} \\fontseries{#2} \\fontshape{#3} \\selectfont}\n" +
							"\n" +
							"\\setcounter{secnumdepth}{4}\n" +
							"\\setcounter{tocdepth}{4}\n" +
							"\n" +
							"% Packet für Seitenrandabständex und Einstellung für Seitenränder\n" +
							"\\usepackage{geometry}\n" +
							"\\geometry{left=3.5cm, right=2cm, top=2.5cm, bottom=2cm}\n" +
							"\n" +
							"% Paket für Boxen im Text\n" +
							"\\usepackage{fancybox}\n" +
							"\n" +
							"% bricht lange URLs \"schoen\" um\n" +
							"\\usepackage[hyphens,obeyspaces,spaces]{url}\n" +
							"\n" +
							"% Paket für Textfarben\n" +
							"\\usepackage{color}\n" +
							"\n" +
							"% Mathematische Symbole importieren\n" +
							"\\usepackage{amssymb}\n" +
							"\n" +
							"% Writing text over arrows\n" +
							"\\usepackage{mathtools}\n" +
							"\n" +
							"% auf jeder Seite eine Überschrift (alt, zentriert)\n" +
							"%\\pagestyle{headings}\n" +
							"\n" +
							"% neue Kopfzeilen mit fancypaket\n" +
							"\\usepackage{fancyhdr} %Paket laden\n" +
							"\\pagestyle{fancy} %eigener Seitenstil\n" +
							"\\fancyhf{} %alle Kopf- und Fußzeilenfelder bereinigen\n" +
							"\\fancyhead[L]{\\nouppercase{\\leftmark}} %Kopfzeile links\n" +
							"\\fancyhead[C]{} %zentrierte Kopfzeile\n" +
							"\\fancyhead[R]{\\thepage} %Kopfzeile rechts\n" +
							"\\renewcommand{\\headrulewidth}{0.4pt} %obere Trennlinie\n" +
							"%\\fancyfoot[C]{\\thepage} %Seitennummer\n" +
							"%\\renewcommand{\\footrulewidth}{0.4pt} %untere Trennlinie\n" +
							"\n" +
							"% für Tabellen\n" +
							"\\usepackage{array}\n" +
							"\n" +
							"% Runde Klammern für Zitate\n" +
							"%\\usepackage[numbers,round]{natbib}\n" +
							"\n" +
							"% Festlegung Art der Zitierung - Havardmethode: Abkuerzung Autor + Jahr\n" +
							"\\bibliographystyle{alphadin}\n" +
							"\n" +
							"% Schaltet den zusätzlichen Zwischenraum ab, den LaTeX normalerweise nach einem Satzzeichen einfügt.\n" +
							"\\frenchspacing\n" +
							"\n" +
							"% Paket für Zeilenabstand\n" +
							"\\usepackage{setspace}\n" +
							"\n" +
							"% für Bildbezeichner\n" +
							"\\usepackage{capt-of}\n" +
							"\n" +
							"% für Stichwortverzeichnis\n" +
							"\\usepackage{makeidx}\n" +
							"\n" +
							"% für Listings\n" +
							"\\usepackage{listings}\n" +
							"\\lstset{numbers=left, numberstyle=\\tiny, numbersep=5pt, keywordstyle=\\color{black}\\bfseries, stringstyle=\\ttfamily,showstringspaces=false,basicstyle=\\footnotesize,captionpos=b}\n" +
							"\\lstset{language=java}\n" +
							"\n" +
							"% Indexerstellung\n" +
							"\\makeindex\n" +
							"\n" +
							"% Abkürzungsverzeichnis\n" +
							"\\usepackage[english]{nomencl}\n" +
							"\\let\\abbrev\\nomenclature\n" +
							"\n" +
							"% Abkürzungsverzeichnis LiveTex Version\n" +
							"\\renewcommand{\\nomname}{Abbreviations}\n" +
							"\\setlength{\\nomlabelwidth}{.25\\hsize}\n" +
							"\\renewcommand{\\nomlabel}[1]{#1 \\dotfill}\n" +
							"\\setlength{\\nomitemsep}{-\\parsep}\n" +
							"\\makenomenclature\n" +
							"%\\makeglossary\n" +
							"\n" +
							"% Abkürzungsverzeichnis TeTEX Version\n" +
							"% \\usepackage[german]{nomencl}\n" +
							"% \\makenomenclature\n" +
							"% %\\makeglossary\n" +
							"% \\renewcommand{\\nomname}{Abkürzungsverzeichnis}\n" +
							"% \\setlength{\\nomlabelwidth}{.25\\hsize}\n" +
							"% \\renewcommand{\\nomlabel}[1]{#1 \\dotfill}\n" +
							"% \\setlength{\\nomitemsep}{-\\parsep}\n" +
							"\n" +
							"% Disable single lines at the start of a paragraph (Schusterjungen)\n" +
							"\\clubpenalty = 10000\n" +
							"% Disable single lines at the end of a paragraph (Hurenkinder)\n" +
							"\\widowpenalty = 10000\n" +
							"\\displaywidowpenalty = 10000\n" +
							"\n" +
							"\\begin{document}\n"
							);

		for ( String in : input ) {
			str.append( "\\input{" +
								in + "}\n" );
		}
		str.append( "\\section*{ }\n" +
							"\n" +
							"\\end{document}" );

		try {
			File file = new File( "./Output/Main.tex" );
			PrintWriter out = new PrintWriter( file );
			out.println( str.toString() );
			out.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
