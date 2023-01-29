package rs.ac.bg.etf.pp1;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import rs.ac.bg.etf.pp1.Counter.ParamCounter;
import rs.ac.bg.etf.pp1.Counter.VarCounter;
import rs.ac.bg.etf.pp1.ast.ActParsMultiple;
import rs.ac.bg.etf.pp1.ast.ActParsSingle;
import rs.ac.bg.etf.pp1.ast.ActParsStart;
import rs.ac.bg.etf.pp1.ast.ActualParsList;
import rs.ac.bg.etf.pp1.ast.Addop;
import rs.ac.bg.etf.pp1.ast.AddopMINUS;
import rs.ac.bg.etf.pp1.ast.AddopPLUS;
import rs.ac.bg.etf.pp1.ast.AssignOpDesignator;
import rs.ac.bg.etf.pp1.ast.BracketCheck;
import rs.ac.bg.etf.pp1.ast.BreakSingleStatement;
import rs.ac.bg.etf.pp1.ast.CondFactRelop;
import rs.ac.bg.etf.pp1.ast.ConditionFact;
import rs.ac.bg.etf.pp1.ast.ConditionTermListClass;
import rs.ac.bg.etf.pp1.ast.ContinueSingleStatement;
import rs.ac.bg.etf.pp1.ast.Designator;
import rs.ac.bg.etf.pp1.ast.DesignatorArr;
import rs.ac.bg.etf.pp1.ast.DesignatorDot;
import rs.ac.bg.etf.pp1.ast.DesignatorIdent;
import rs.ac.bg.etf.pp1.ast.DesignatorStatementDec;
import rs.ac.bg.etf.pp1.ast.DesignatorStatementFunct;
import rs.ac.bg.etf.pp1.ast.DesignatorStatementFunctActPars;
import rs.ac.bg.etf.pp1.ast.DesignatorStatementInc;
import rs.ac.bg.etf.pp1.ast.DoWhileSingleStatement;
import rs.ac.bg.etf.pp1.ast.DoWhileStart;
import rs.ac.bg.etf.pp1.ast.ExprTermListClass;
import rs.ac.bg.etf.pp1.ast.ExpressionMultiple;
import rs.ac.bg.etf.pp1.ast.FactorBoolConst;
import rs.ac.bg.etf.pp1.ast.FactorCharConst;
import rs.ac.bg.etf.pp1.ast.FactorClass;
import rs.ac.bg.etf.pp1.ast.FactorFunc;
import rs.ac.bg.etf.pp1.ast.FactorFuncActPars;
import rs.ac.bg.etf.pp1.ast.FactorNewArr;
import rs.ac.bg.etf.pp1.ast.FactorNumConst;
import rs.ac.bg.etf.pp1.ast.IfElseSingleStatement;
import rs.ac.bg.etf.pp1.ast.MStart;
import rs.ac.bg.etf.pp1.ast.MStartVoid;
import rs.ac.bg.etf.pp1.ast.MethodDecl;
import rs.ac.bg.etf.pp1.ast.MethodDeclNoParams;
import rs.ac.bg.etf.pp1.ast.MethodDeclParams;
import rs.ac.bg.etf.pp1.ast.MethodDeclVoidNoParams;
import rs.ac.bg.etf.pp1.ast.MethodDeclVoidParams;
import rs.ac.bg.etf.pp1.ast.MinusExpr;
import rs.ac.bg.etf.pp1.ast.MinusExprMultiple;
import rs.ac.bg.etf.pp1.ast.MinusExprStart;
import rs.ac.bg.etf.pp1.ast.Mulop;
import rs.ac.bg.etf.pp1.ast.MulopDIV;
import rs.ac.bg.etf.pp1.ast.MulopMUL;
import rs.ac.bg.etf.pp1.ast.NoActParsList;
import rs.ac.bg.etf.pp1.ast.OptArgsMultiple;
import rs.ac.bg.etf.pp1.ast.OrCheck;
import rs.ac.bg.etf.pp1.ast.PrintArgsSingleStatement;
import rs.ac.bg.etf.pp1.ast.PrintSingleStatement;
import rs.ac.bg.etf.pp1.ast.ReadSingleStatement;
import rs.ac.bg.etf.pp1.ast.Relop;
import rs.ac.bg.etf.pp1.ast.RelopLEQ;
import rs.ac.bg.etf.pp1.ast.RelopLGT;
import rs.ac.bg.etf.pp1.ast.RelopLGTEQ;
import rs.ac.bg.etf.pp1.ast.RelopLLT;
import rs.ac.bg.etf.pp1.ast.RelopLLTEQ;
import rs.ac.bg.etf.pp1.ast.RelopLNEQ;
import rs.ac.bg.etf.pp1.ast.TermFactorListClass;
import rs.ac.bg.etf.pp1.ast.TermMultiple;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;
import rs.ac.bg.etf.pp1.ast.WhileCheck;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;
import rs.ac.bg.etf.pp1.ast.ElseIni;
import rs.ac.bg.etf.pp1.ast.IfIni;
import rs.ac.bg.etf.pp1.ast.IfSingleStatement;

public class CodeGenerator extends VisitorAdaptor {

	int mainPc;
	int ArgsPresent = 0;
	Map<String, List<Obj>> constDefaults;

	public static class JumpsIfElse {
		List<Integer> factAdrs = new LinkedList<>();
		List<Integer> multipleFactAdrs = new LinkedList<>();

		int jumpOutAdr = -1;

	}

	public static class JumpsLoop {
		List<Integer> continueAdrs = new LinkedList<>();
		List<Integer> breakAdrs = new LinkedList<>();

		int loopBegin = -1;

	}

	Stack<JumpsIfElse> basicStack = new Stack<>();
	Stack<JumpsLoop> loopStack = new Stack<>();

	// Conds

	public void visit(IfIni i) {
		basicStack.push(new JumpsIfElse());
	}

	public void visit(ElseIni e) {
		JumpsIfElse j = basicStack.peek();
		Code.putJump(0);
		j.jumpOutAdr = Code.pc - 2;
		for (int oldadr : j.factAdrs) {
			Code.fixup(oldadr);
		}
		j.factAdrs.clear();
	}

	public void visit(IfSingleStatement is) {
		JumpsIfElse j = basicStack.pop();
		for (int oldadr : j.factAdrs) {
			Code.fixup(oldadr);
		}
	}

	public void visit(IfElseSingleStatement is) {
		JumpsIfElse j = basicStack.pop();
		Code.fixup(j.jumpOutAdr);
	}

	public void visit(OrCheck ct) {
		JumpsIfElse j = basicStack.peek();
		Code.putJump(0);
		j.multipleFactAdrs.add(Code.pc - 2);
		for (int oldadr : j.factAdrs) {
			Code.fixup(oldadr);
		}
		j.factAdrs.clear();
	}

	public void visit(BracketCheck bc) {
		JumpsIfElse j = basicStack.peek();
		for (int oldadr : j.multipleFactAdrs) {
			Code.fixup(oldadr);
		}
		j.multipleFactAdrs.clear();
	}

	public void visit(ConditionFact cd) {
		Code.loadConst(1);
		Code.putFalseJump(Code.eq, 0);
		basicStack.peek().factAdrs.add(Code.pc - 2);

	}

	public void visit(CondFactRelop cf) {
		Code.putFalseJump(checkRelop(cf.getRelop()), 0);
		basicStack.peek().factAdrs.add(Code.pc - 2);
	}

	// Loop
	public void visit(DoWhileStart dws) {
		basicStack.push(new JumpsIfElse());
		loopStack.push(new JumpsLoop());

		loopStack.peek().loopBegin = Code.pc;
	}

	public void visit(BreakSingleStatement bs) {
		Code.putJump(0);
		loopStack.peek().breakAdrs.add(Code.pc - 2);
	}

	public void visit(ContinueSingleStatement bs) {
		Code.putJump(0);
		loopStack.peek().continueAdrs.add(Code.pc - 2);
	}

	public void visit(WhileCheck wc) {
		JumpsLoop l = loopStack.peek();
		for (int oldadr : l.continueAdrs) {
			Code.fixup(oldadr);
		}
	}

	public void visit(DoWhileSingleStatement dw) {
		JumpsIfElse j = basicStack.pop();
		JumpsLoop l = loopStack.pop();
		int start = l.loopBegin;

		Code.putJump(start);
		for (int oldadr : j.factAdrs) {
			Code.fixup(oldadr);
		}
		j.factAdrs.clear();

		for (int oldadr : l.breakAdrs) {
			Code.fixup(oldadr);
		}
		l.breakAdrs.clear();

	}

	private int checkRelop(Relop rel) {

		if (rel instanceof RelopLEQ) {
			return Code.eq;
		} else if (rel instanceof RelopLNEQ) {
			return Code.ne;
		} else if (rel instanceof RelopLGT) {
			return Code.gt;
		} else if (rel instanceof RelopLGTEQ) {
			return Code.ge;
		} else if (rel instanceof RelopLLT) {
			return Code.lt;
		} else if (rel instanceof RelopLLTEQ) {
			return Code.le;
		}

		return -1;

	}

	// Easy Factors
	public void visit(FactorNumConst f) {
		Obj n = new Obj(Obj.Con, "#", Tab.intType);
		n.setAdr(f.getN1());
		Code.load(n);

	}

	public void visit(FactorCharConst f) {
		Obj n = new Obj(Obj.Con, "#", Tab.charType);
		n.setAdr(f.getC1());
		Code.load(n);
	}

	public void visit(FactorBoolConst f) {
		Obj n = new Obj(Obj.Con, "#", Tab.intType);
		n.setAdr(f.getB1() ? 1 : 0);
		Code.load(n);
	}

	public void visit(FactorNewArr f) {
		Code.put(Code.newarray);
		if (f.getType().struct != Tab.charType) {
			Code.put(1);
		} else {
			Code.put(0);
		}
	}

	public void visit(FactorFunc f) {
		Obj o = f.getDesignator().obj;
		if (o.getName().equals("ord") || o.getName().equals("chr")) {

		} else if (o.getName().equals("len")) {
			Code.put(Code.arraylength);
		} else {
			List<Obj> ol = constDefaults.get(o.getName());
			int cnt = 0;
			Collection<Obj> oList = o.getLocalSymbols();
			for (Iterator iterator = oList.iterator(); iterator.hasNext();) {
				Obj obj = (Obj) iterator.next();
				if (obj.getFpPos() < 0) {
					Obj def = ol.get(cnt++);
					Code.load(def);
				}
			}

			int off = o.getAdr() - Code.pc;
			Code.put(Code.call);
			Code.put2(off);
			if(!ArgsPresentStack.isEmpty()) {
				ArgsPresent = ArgsPresentStack.pop();
			}
			else {
				ArgsPresent =0;
			}

		}
	}

	public void visit(FactorFuncActPars f) {
		Obj o = f.getDesignator().obj;

		if (o.getName().equals("ord") || o.getName().equals("chr")) {

		} else if (o.getName().equals("len")) {
			Code.put(Code.arraylength);
		} else {

			

			ArgsPresent -= o.getLevel();
			//System.out.println("Ime: " + o.getName() + " ArgsP: " + ArgsPresent + " LVL: " + o.getLevel());
			List<Obj> ol = constDefaults.get(o.getName());
			int cnt = 0;
			Collection<Obj> oList = o.getLocalSymbols();
			for (Iterator iterator = oList.iterator(); iterator.hasNext();) {
				Obj obj = (Obj) iterator.next();
				if (obj.getFpPos() < 0) {
					if (ArgsPresent > 0) {
						ArgsPresent--;
						cnt++;
					} else {
						Obj def = ol.get(cnt++);
						Code.load(def);
					}
				}
			}
			int off = o.getAdr() - Code.pc;
			Code.put(Code.call);
			Code.put2(off);
		}
		if(!ArgsPresentStack.isEmpty()) {
			ArgsPresent = ArgsPresentStack.pop();
		}
		else {
			ArgsPresent =0;
		}
	}

	public void visit(FactorClass f) {
		Obj o = f.getDesignator().obj;
		Code.load(o);
	}

	public void visit(PrintSingleStatement ps) {
		Struct type = ps.getExpr().struct;
		if (type == Tab.charType) {
			Code.loadConst(1);
			Code.put(Code.bprint);
		} else if (type == Tab.intType) {
			Code.loadConst(1);
			Code.put(Code.print);
		} else if (type == TabWrap.boolType) {
			Code.loadConst(0);

			Code.put(Code.jcc + Code.eq);
			Code.put2(34);

			Code.loadConst('t');
			Code.loadConst(1);
			Code.put(Code.bprint);
			Code.loadConst('r');
			Code.loadConst(1);
			Code.put(Code.bprint);
			Code.loadConst('u');
			Code.loadConst(1);
			Code.put(Code.bprint);
			Code.loadConst('e');
			Code.loadConst(1);
			Code.put(Code.bprint);

			Code.put(Code.jmp);
			Code.put2(38);

			Code.loadConst('f');
			Code.loadConst(1);
			Code.put(Code.bprint);
			Code.loadConst('a');
			Code.loadConst(1);
			Code.put(Code.bprint);
			Code.loadConst('l');
			Code.loadConst(1);
			Code.put(Code.bprint);
			Code.loadConst('s');
			Code.loadConst(1);
			Code.put(Code.bprint);
			Code.loadConst('e');
			Code.loadConst(1);
			Code.put(Code.bprint);
		}
	}
	
	public void visit(PrintArgsSingleStatement ps) {
		Struct type = ps.getExpr().struct;
		if (type == Tab.charType) {
			Code.loadConst(ps.getN2());
			Code.put(Code.bprint);
		} else if (type == Tab.intType) {
			Code.loadConst(ps.getN2());
			Code.put(Code.print);
		} else if (type == TabWrap.boolType) {
			Code.loadConst(0);
			
			Code.put(Code.jcc + Code.eq);
			int jump1 = Code.pc;
			Code.put2(0);
			
			
			Code.loadConst('t');
			Code.loadConst(1);
			Code.put(Code.bprint);
			Code.loadConst('r');
			Code.loadConst(1);
			Code.put(Code.bprint);
			Code.loadConst('u');
			Code.loadConst(1);
			Code.put(Code.bprint);
			Code.loadConst('e');
			Code.loadConst(1);
			Code.put(Code.bprint);
			
			Code.put(Code.jmp);
			int jump2 = Code.pc;
			Code.put2(0);
			
			Code.fixup(jump1);
			
			Code.loadConst('f');
			Code.loadConst(1);
			Code.put(Code.bprint);
			Code.loadConst('a');
			Code.loadConst(1);
			Code.put(Code.bprint);
			Code.loadConst('l');
			Code.loadConst(1);
			Code.put(Code.bprint);
			Code.loadConst('s');
			Code.loadConst(1);
			Code.put(Code.bprint);
			Code.loadConst('e');
			Code.loadConst(1);
			Code.put(Code.bprint);
			
			Code.fixup(jump2);
		}
	}
	

	
	public void visit(ReadSingleStatement rs) {
		Struct ty = rs.getDesignator().obj.getType();
		if(ty ==Tab.charType) {
			Code.put(Code.bread);
			Code.store(rs.getDesignator().obj);
		}
		else if(ty == Tab.intType) {
			Code.put(Code.read);
			Code.store(rs.getDesignator().obj);
		}
		else if(ty == TabWrap.boolType) {
			Code.put(Code.read);
			Code.loadConst(0);
			
			Code.put(Code.jcc + Code.eq);
			int jump1 = Code.pc;
			Code.put2(0);
			
			Code.loadConst(1);
			Code.store(rs.getDesignator().obj);
			
			Code.put(Code.jmp);
			int jump2 = Code.pc;
			Code.put2(0);
			
			Code.fixup(jump1);
			Code.loadConst(0);
			Code.store(rs.getDesignator().obj);
			
			Code.fixup(jump2);
			
		}
	}

	// Methods

	public void visit(MStartVoid ms) {
		if (ms.getMethodName().equals("main")
		// && ms.obj.getLevel == 0
		) {
			mainPc = Code.pc;
		}
		Obj o = ms.obj;

		o.setAdr(Code.pc);
		//System.out.println(o.getAdr());
		ParamCounter pc = new ParamCounter();
		VarCounter vc = new VarCounter();
		MethodDecl md = (MethodDecl) ms.getParent();

		md.traverseTopDown(pc);
		md.traverseTopDown(vc);

		Code.put(Code.enter);
		Code.put(pc.num);// TODO
		Code.put(pc.num + vc.num);// TODO
	}

	public void visit(MStart ms) {

		Obj o = ms.obj;

		o.setAdr(Code.pc);

		ParamCounter pc = new ParamCounter();
		VarCounter vc = new VarCounter();
		MethodDecl md = (MethodDecl) ms.getParent();

		md.traverseTopDown(pc);
		md.traverseTopDown(vc);

		Code.put(Code.enter);
		Code.put(pc.num);// TODO
		Code.put(pc.num + vc.num);// TODO
	}

	public void visit(MethodDeclNoParams mdnp) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	public void visit(MethodDeclVoidNoParams mdnp) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	public void visit(MethodDeclParams mdnp) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	public void visit(MethodDeclVoidParams mdnp) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	// Mulop Addop Relop

	public void checkMulop(Mulop m) {
		if (m instanceof MulopMUL) {
			Code.put(Code.mul);
		} else if (m instanceof MulopDIV) {
			Code.put(Code.div);
		} else {
			Code.put(Code.rem);
		}
	}

	public void checkAddop(Addop a) {
		if (a instanceof AddopPLUS) {
			Code.put(Code.add);
		} else if (a instanceof AddopMINUS) {
			Code.put(Code.sub);
		}
	}

	// Expr Term

	public void visit(TermFactorListClass tf) {
		Mulop m = tf.getMulop();
		checkMulop(m);
	}

	public void visit(TermMultiple tf) {
		Mulop m = tf.getMulop();
		checkMulop(m);
	}

	public void visit(MinusExprStart e) {
		Code.put(Code.neg);
	}

	public void visit(ExprTermListClass e) {
		Addop a = e.getAddop();
		checkAddop(a);
	}

	public void visit(ExpressionMultiple e) {
		Addop a = e.getAddop();
		checkAddop(a);
	}

	public void visit(MinusExprMultiple e) {
		Addop a = e.getAddop();
		checkAddop(a);
	}

	public void visit(MinusExpr e) {
		Code.put(Code.neg);
	}

	// Designator Statements and Designators
	public void visit(DesignatorIdent d) {
		if (d.getParent() instanceof Designator) {
			Code.load(d.obj);
		}
	}

	public void visit(DesignatorDot d) {
		if (d.getParent() instanceof Designator) {
			Code.load(d.obj);
		}
	}

	public void visit(DesignatorArr d) {
		if (d.getParent() instanceof Designator) {
			Code.load(d.obj);
		}
	}

	public void visit(AssignOpDesignator ad) {
		Code.store(ad.getDesignator().obj);
	}

	public void visit(DesignatorStatementFunct ad) {
		Obj o = ad.getDesignator().obj;

		if (o.getName().equals("ord") || o.getName().equals("chr")) {

		} else if (o.getName().equals("len")) {
			Code.put(Code.arraylength);
		} else {

			List<Obj> ol = constDefaults.get(o.getName());
			int cnt = 0;
			Collection<Obj> oList = o.getLocalSymbols();
			for (Iterator iterator = oList.iterator(); iterator.hasNext();) {
				Obj obj = (Obj) iterator.next();
				if (obj.getFpPos() < 0) {
					Obj def = ol.get(cnt++);
					Code.load(def);
				}
			}

			int off = o.getAdr() - Code.pc;
			Code.put(Code.call);
			Code.put2(off);
			
			if(o.getType() != Tab.noType) {
				Code.put(Code.pop);
			}
		}
		if(!ArgsPresentStack.isEmpty()) {
			ArgsPresent = ArgsPresentStack.pop();
		}
		else {
			ArgsPresent =0;
		}

	}

	public void visit(DesignatorStatementFunctActPars ad) {
		Obj o = ad.getDesignator().obj;

		if (o.getName().equals("ord") || o.getName().equals("chr")) {

		} else if (o.getName().equals("len")) {
			Code.put(Code.arraylength);
		} else {

			ArgsPresent -= o.getLevel();
			List<Obj> ol = constDefaults.get(o.getName());
			int cnt = 0;
			Collection<Obj> oList = o.getLocalSymbols();
			for (Iterator iterator = oList.iterator(); iterator.hasNext();) {
				Obj obj = (Obj) iterator.next();
				if (obj.getFpPos() < 0) {
					if (ArgsPresent > 0) {
						ArgsPresent--;
						cnt++;
					} else {
						Obj def = ol.get(cnt++);
						Code.load(def);
					}
				}
			}
			int off = o.getAdr() - Code.pc;
			Code.put(Code.call);
			Code.put2(off);
			
			if(o.getType() != Tab.noType) {
				Code.put(Code.pop);
			}

		}
		if(!ArgsPresentStack.isEmpty()) {
			ArgsPresent = ArgsPresentStack.pop();
		}
		else {
			ArgsPresent =0;
		}
	}

	public void visit(DesignatorStatementInc d) {
		d.getDesignator().traverseBottomUp(new CodeGenerator());
		Code.load(d.getDesignator().obj);
		Code.loadConst(1);
		Code.put(Code.add);
		Code.store(d.getDesignator().obj);
	}

	public void visit(DesignatorStatementDec d) {
		d.getDesignator().traverseBottomUp(new CodeGenerator());
		Code.load(d.getDesignator().obj);
		Code.loadConst(1);
		Code.put(Code.sub);
		Code.store(d.getDesignator().obj);
	}

	// ActualArgs
	Stack<Integer> ArgsPresentStack = new Stack<>();
	
	public void visit(ActParsStart ap) {
		if(ArgsPresent > 0) {
			ArgsPresentStack.push(ArgsPresent);
			ArgsPresent= 0;
		}
		ArgsPresent++;
	}

	public void visit(ActualParsList ap) {
		ArgsPresent++;
	}

	public void visit(NoActParsList ap) {
		ArgsPresent++;
	}
	

}
