package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.FormParsSingleARR;
import rs.ac.bg.etf.pp1.ast.FormParsSingleVAR;
import rs.ac.bg.etf.pp1.ast.OptArgsMultiple;
import rs.ac.bg.etf.pp1.ast.SingleOptArg;
import rs.ac.bg.etf.pp1.ast.VarIdentDeclsArr;
import rs.ac.bg.etf.pp1.ast.VarIdentDeclsVar;
import rs.ac.bg.etf.pp1.ast.VariableDecl;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;

public class Counter extends VisitorAdaptor {

	int num;
	
	public static class ParamCounter extends Counter{
		
		public void visit(FormParsSingleVAR oa) {
			num++;
		}
		public void visit(FormParsSingleARR oa) {
			num++;
		}
		public void visit(SingleOptArg oa) {
			num++;
		}
		public void visit(OptArgsMultiple oa) {
			num++;
		}
	}
	
	public static class VarCounter extends Counter{
		
		public void visit(VariableDecl oa) {
			num++;
		}
		public void visit(VarIdentDeclsVar oa) {
			num++;
		}
		public void visit(VarIdentDeclsArr oa) {
			num++;
		}

	}
	
	
}
