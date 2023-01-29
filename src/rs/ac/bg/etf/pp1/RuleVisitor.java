package rs.ac.bg.etf.pp1;

import org.apache.log4j.Logger;
import rs.ac.bg.etf.pp1.ast.*;

public class RuleVisitor extends VisitorAdaptor{

	int printCallCount = 0;
	int varDeclCount = 0;
	int parmDeclCount = 0;
	
	Logger log = Logger.getLogger(getClass());
	
	public void visit(VariableDecl vardecl){
		varDeclCount++;
	}
	
    public void visit(ClassDecl print) {
		printCallCount++;
	}

    public void visit(VarIdentDeclsVar vardecl){
		varDeclCount++;
	}
       
    public void visit(SingleFormPars vardecl){
    	parmDeclCount++;
	}
    
}
