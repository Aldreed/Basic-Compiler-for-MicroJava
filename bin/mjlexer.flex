package rs.ac.bg.etf.pp1;

import java_cup.runtime.Symbol;

%%

%{

	// ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type) {
		return new Symbol(type, yyline+1, yycolumn);
	}
	
	// ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type, Object value) {
		return new Symbol(type, yyline+1, yycolumn, value);
	}

%}

%cup
%line
%column

%xstate COMMENT

%eofval{
	return new_symbol(sym.EOF);
%eofval}

%%

" " 	{ }
"\b" 	{ }
"\t" 	{ }
"\r\n" 	{ }
"\f" 	{ }

"program"	{ return new_symbol(sym.PROGRAM, yytext()); }
"break"		{ return new_symbol(sym.BREAK, yytext()); }
"class"		{ return new_symbol(sym.CLASS, yytext()); }
"enum"		{ return new_symbol(sym.ENUM, yytext()); }
"else"		{ return new_symbol(sym.ELSE, yytext()); }
"const"		{ return new_symbol(sym.CONST, yytext()); }
"if"		{ return new_symbol(sym.IF, yytext()); }
"do"		{ return new_symbol(sym.DO, yytext()); }
"while"		{ return new_symbol(sym.WHILE, yytext()); }
"new"		{ return new_symbol(sym.NEW, yytext()); }
"print"		{ return new_symbol(sym.PRINT, yytext()); }
"read"		{ return new_symbol(sym.READ, yytext()); }
"return"	{ return new_symbol(sym.RETURN, yytext()); }
"void"		{ return new_symbol(sym.VOID, yytext()); }
"extends"	{ return new_symbol(sym.EXTENDS, yytext()); }
"continue"	{ return new_symbol(sym.CONTINUE, yytext()); }
"this"		{ return new_symbol(sym.THIS, yytext()); }
"super"		{ return new_symbol(sym.SUPER, yytext()); }
"goto"		{ return new_symbol(sym.GOTO, yytext()); }
"record"	{ return new_symbol(sym.RECORD, yytext()); }
<YYINITIAL> "+"		{ return new_symbol(sym.PLUS, yytext()); }
"-"		{ return new_symbol(sym.MINUS, yytext()); }
"*"		{ return new_symbol(sym.MUL, yytext()); }
"/"		{ return new_symbol(sym.DIV, yytext()); }
"%"		{ return new_symbol(sym.MOD, yytext()); }
"="		{ return new_symbol(sym.ASSIGN, yytext()); }
"=="	{ return new_symbol(sym.LEQ, yytext()); }
"!="	{ return new_symbol(sym.LNEQ, yytext()); }
">"		{ return new_symbol(sym.LGT, yytext()); }
"<"		{ return new_symbol(sym.LLT, yytext()); }
">="	{ return new_symbol(sym.LGTEQ, yytext()); }
"<="	{ return new_symbol(sym.LLTEQ, yytext()); }
"&&"	{ return new_symbol(sym.LAND, yytext()); }
"||"	{ return new_symbol(sym.LOR, yytext()); }
"++"	{ return new_symbol(sym.INC, yytext()); }
"--"	{ return new_symbol(sym.DEC, yytext()); }
";"		{ return new_symbol(sym.SEMICOLON, yytext()); }
":"		{ return new_symbol(sym.COLON, yytext()); }
"."		{ return new_symbol(sym.PERIOD, yytext()); }
"("		{ return new_symbol(sym.LEFTBRACKET, yytext()); }
")"		{ return new_symbol(sym.RIGHTBRACKET, yytext()); }
"["		{ return new_symbol(sym.LEFTBRACKET_SQ, yytext()); }
"]"		{ return new_symbol(sym.RIGHTBRACKET_SQ, yytext()); }
"{"		{ return new_symbol(sym.LEFTBRACKET_CUR, yytext()); }
"}"		{ return new_symbol(sym.RIGHTBRACKET_CUR, yytext()); }
","		{ return new_symbol(sym.COMMA, yytext()); }

<YYINITIAL> "//" 		     { yybegin(COMMENT); }
<COMMENT> .      { yybegin(COMMENT); }
<COMMENT> "\r\n" { yybegin(YYINITIAL); }


"true"	{return new_symbol(sym.BOOLCONST,new Boolean(true)); }
"false"	{return new_symbol(sym.BOOLCONST,new Boolean(false)); }

"'"[\040-\176]"'"	{return new_symbol (sym.CHARCONST,new Character(yytext().charAt(1))); }

[0-9]+  { return new_symbol(sym.NUMCONST, new Integer (yytext())); }
([a-z]|[A-Z])[a-z|A-Z|0-9|_]* 	{return new_symbol (sym.IDENT, yytext()); }

. { System.err.println("Leksicka greska ("+yytext()+") u liniji "+(yyline+1)); }






