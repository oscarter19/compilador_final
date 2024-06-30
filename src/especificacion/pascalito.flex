package ve.edu.unet;

import java_cup.runtime.*;
import java.io.Reader;

%%

%class Lexico
%unicode
%cup

/*-- DECLARACIONES --*/
%{
	public Lexico(Reader r, SymbolFactory sf){
        this(r);
		this.sf=sf;
		lineanum=0;
		debug=true;
	}
	private SymbolFactory sf;
	private int lineanum;
	private boolean debug;


/******************************************************************
BORRAR SI NO SE NECESITA
	//TODO: Cambiar la SF por esto o ver que se hace
	//Crear un nuevo objeto java_cup.runtime.Symbol con informaci�n sobre el token actual sin valor
 	  private Symbol symbol(int type){
    		return new Symbol(type,yyline,yycolumn);
	  }
	//Crear un nuevo objeto java_cup.runtime.Symbol con informaci�n sobre el token actual con valor
	  private Symbol symbol(int type,Object value){
    		return new Symbol(type,yyline,yycolumn,value);
	  }
******************************************************************/
%}
%eofval{
    return sf.newSymbol("EOF",sym.EOF);
%eofval}

nuevalinea		= \n | \n\r | \r\n
espacio		= [ \t]+
%%

"var"                   { if(debug) System.out.println("token VAR"); return sf.newSymbol("VAR",sym.VAR); }
"begin"                 { if(debug) System.out.println("token BEGIN"); return sf.newSymbol("BEGIN",sym.BEGIN); }
"end"                   { if(debug) System.out.println("token END"); return sf.newSymbol("END",sym.END); }
"if"                    { if(debug) System.out.println("token IF"); return sf.newSymbol("IF",sym.IF); }
"then"                  { if(debug) System.out.println("token THEN"); return sf.newSymbol("THEN",sym.THEN); }
"else"                  { if(debug) System.out.println("token ELSE"); return sf.newSymbol("ELSE",sym.ELSE); }
"for"                   { if(debug) System.out.println("token FOR"); return sf.newSymbol("FOR",sym.FOR); }
"to"                    { if(debug) System.out.println("token TO"); return sf.newSymbol("TO",sym.TO); }
"do"                    { if(debug) System.out.println("token DO"); return sf.newSymbol("DO",sym.DO); }
"downto"                { if(debug) System.out.println("token DOWNTO"); return sf.newSymbol("TO",sym.DOWNTO); }
"repeat"                { if(debug) System.out.println("token REPEAT"); return sf.newSymbol("REPEAT",sym.REPEAT); }
"until"                 { if(debug) System.out.println("token UNTIL"); return sf.newSymbol("UNTIL",sym.UNTIL); }
"read"                  { if(debug) System.out.println("token READ"); return sf.newSymbol("READ",sym.READ); }
"write"                 { if(debug) System.out.println("token WRITE"); return sf.newSymbol("WRITE",sym.WRITE); }
"and"                   { if(debug) System.out.println("token AND"); return sf.newSymbol("AND",sym.AND); }
"or"                    { if(debug) System.out.println("token OR"); return sf.newSymbol("OR",sym.OR); }
"not"                   { if(debug) System.out.println("token NOT"); return sf.newSymbol("NOT",sym.NOT); }
"mod"                   { if(debug) System.out.println("token MOD"); return sf.newSymbol("MOD",sym.MOD); }
[0-9]+                  { if(debug) System.out.println("token NUMBER"); return sf.newSymbol("INTEGER",sym.INTEGER, Integer.parseInt(yytext())); }
"true"                  { if(debug) System.out.println("token BOOLEAN"); return sf.newSymbol("BOOLEAN",sym.BOOLEAN, true); }
"false"                 { if(debug) System.out.println("token BOOLEAN"); return sf.newSymbol("BOOLEAN",sym.BOOLEAN, false); }
[a-zA-Z][a-zA-Z0-9]*    { if(debug) System.out.println("token ID"); return sf.newSymbol("ID",sym.ID, yytext()); }
"{"[^}]*"}"             { /* salto comentarios */ if(debug) System.out.println("token COMENTARIO"); }
":="                    { if(debug) System.out.println("token ASSIGN"); return sf.newSymbol("ASSIGN",sym.ASSIGN); }
"<="                    { if(debug) System.out.println("token LEQ"); return sf.newSymbol("LEQ",sym.LEQ); }
">="                    { if(debug) System.out.println("token GEQ"); return sf.newSymbol("GEQ",sym.GEQ); }
"="                     { if(debug) System.out.println("token EQ"); return sf.newSymbol("EQ",sym.EQ); }
"<>"                    { if(debug) System.out.println("token NEQ"); return sf.newSymbol("NEQ",sym.NEQ); }
"<"                     { if(debug) System.out.println("token LT"); return sf.newSymbol("LT",sym.LT); }
">"                     { if(debug) System.out.println("token GT"); return sf.newSymbol("GT",sym.GT); }
"+"                     { if(debug) System.out.println("token PLUS"); return sf.newSymbol("PLUS",sym.PLUS); }
"-"                     { if(debug) System.out.println("token MINUS"); return sf.newSymbol("MINUS",sym.MINUS); }
"*"                     { if(debug) System.out.println("token TIMES"); return sf.newSymbol("TIMES",sym.TIMES); }
"/"                     { if(debug) System.out.println("token DIV"); return sf.newSymbol("DIV",sym.DIV); }
"("                     { if(debug) System.out.println("token LPAREN"); return sf.newSymbol("LPAREN",sym.LPAREN); }
")"                     { if(debug) System.out.println("token RPAREN"); return sf.newSymbol("RPAREN",sym.RPAREN); }
";"                     { if(debug) System.out.println("token SEMI"); return sf.newSymbol("SEMI",sym.SEMI); }
{nuevalinea}       {lineanum++;}
{espacio}    { /* saltos espacios en blanco*/}
.               {System.err.println("Caracter Ilegal encontrado en analisis lexico: " + yytext() + "\n");}
