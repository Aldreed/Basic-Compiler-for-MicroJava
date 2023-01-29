package rs.ac.bg.etf.pp1;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import java_cup.runtime.Symbol;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import rs.ac.bg.etf.pp1.ast.Program;
import rs.ac.bg.etf.pp1.util.Log4JUtils;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class Compiler {

	static {
		DOMConfigurator.configure(Log4JUtils.instance().findLoggerConfigFile());
		Log4JUtils.instance().prepareLogFile(Logger.getRootLogger());
	}
	
	public static void main(String[] args) throws Exception {
		
		Logger log = Logger.getLogger(Compiler.class);
		
		Reader br = null;
		try {
			
			if(args.length < 2) {
				System.out.println("Nema dovoljno argumenta");
				return;
			}
			
			File sourceCode = new File(args[0]);
			
			//File sourceCode = new File("test/programPrimerPostavka.mj");
			//File sourceCode = new File("test/programSpecPrimer.mj");
			//File sourceCode = new File("test/smallTest.mj");
			//File sourceCode = new File("test/parserTest.mj");
			//File sourceCode = new File("test/countingTest.mj");
			//File sourceCode = new File("test/CodeGenTest.mj");
			//File sourceCode = new File("test/functAsFact.mj");
			
			//File sourceCode = new File("test/test301.mj");
			//File sourceCode = new File("test/test302.mj");
			//File sourceCode = new File("test/test303.mj");
			
			log.info("Compiling source file: " + sourceCode.getAbsolutePath());
			
			br = new BufferedReader(new FileReader(sourceCode));
			Yylex lexer = new Yylex(br);
			
			MJParser p = new MJParser(lexer);
	        Symbol s = p.parse();  //pocetak parsiranja
	        
	        
	        TabWrap.initWrap();
	        
	        
	        
	        
	        Program prog = (Program)(s.value); 
			// ispis sintaksnog stabla
			log.info(prog.toString(""));
			log.info("===================================");

			// ispis prepoznatih programskih konstrukcija
			SemanticPass v = new SemanticPass();
			//RuleVisitor v = new RuleVisitor();
			prog.traverseBottomUp(v); 
	      
			//log.info(" Print count calls = " + v.printCallCount);

			//log.info(" Deklarisanih promenljivih ima = " + v.varDeclCount);
			
			//log.info(" Deklarisanih parametra ima = " + v.parmDeclCount);
			
			if(!p.errorDetected && v.passed()) {
				//log.info("Dingoo");
				log.info("Tabela sibola");
				//Tab.dump();
				new Compiler().tsdump();
				CodeGenerator cg = new CodeGenerator();
				cg.constDefaults = v.constDefaults;
				//File objFile = new File("test/program.obj");
				File objFile = new File(args[1]);
				if(objFile.exists()) objFile.delete();
				
				prog.traverseBottomUp(cg);
				Code.dataSize = v.nVars;
				Code.mainPc = cg.mainPc;
				Code.write(new FileOutputStream(objFile));
				log.info("Parsiranje uspesno zavrseno!");
			}
			
		} 
		finally {
			if (br != null) try { br.close(); } catch (IOException e1) { log.error(e1.getMessage(), e1); }
		}

	}
	
	public void tsdump() {
		Tab.dump(new BoolTableVisitor());
	}
	
}
