package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Scope;
import rs.etf.pp1.symboltable.concepts.Struct;
import rs.etf.pp1.symboltable.visitors.DumpSymbolTableVisitor;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.apache.log4j.Logger;

public class SemanticPass extends VisitorAdaptor {

	boolean errorDetected = false;
	Obj currentMethod = null;
	int varDeclCount = 0;
	int nVars = 0;
	private String currentClass;

	int nFormals = 0;
	int nOpts = 0;
	boolean foundMain = false;
	boolean returnFound = false;
	boolean inDoWhile = false;

	Struct termType;
	private boolean requiredReturnWithType;

	private

	Obj cMethod;
	Struct MethReturnTypeRequired;
	Struct exprType;

	Map<String, List<Obj>> constDefaults = new HashMap<>();

	Logger log = Logger.getLogger(getClass());

	private String prObj(Obj o) {
		DumpSymbolTableVisitor d = new BoolTableVisitor();
		o.accept(d);
		return d.getOutput();
	}

	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append(" na liniji ").append(line);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append(" na liniji ").append(line);
		log.info(msg.toString());
	}

	public void visit(ProgramName programname) {
		programname.obj = Tab.insert(Obj.Prog, programname.getPrName(), Tab.noType);
		Tab.openScope();
	}

	public boolean passed() {
		return !errorDetected;
	}

	public void visit(Program program) {
		nVars = Tab.currentScope.getnVars();
		Tab.chainLocalSymbols(program.getProgName().obj);
		Tab.closeScope();
		if (!foundMain) {
			report_error("Ne postoji metoda main", program);
		}
	}

	public void visit(Type type) {
		Obj typeNode = Tab.find(type.getTypeName());
		if (typeNode == Tab.noObj) {
			report_error("Nije pronadjen tip " + type.getTypeName() + " u tabeli simbola! ", null);
			type.struct = Tab.noType;
		} else {
			if (Obj.Type == typeNode.getKind()) {
				type.struct = typeNode.getType();
			} else {
				report_error("Greska: Ime " + type.getTypeName() + " ne predstavlja tip!", type);
				type.struct = Tab.noType;
			}
		}
	}

	// CONSTDECL

	Struct currentConstType = null;

	public void visit(ConstDeclSingleNUM cdn) {
		Struct leftType = cdn.getType().struct;
		if (!leftType.assignableTo(Tab.intType)) {
			report_error("Greska ne poklapaju se tipovi ", cdn);
		}

		String s = cdn.getConstName();

		Obj o = Tab.find(s);

		currentConstType = leftType;

		if (o != Tab.noObj) {
			report_error("Ime vec postoji Ime:" + s, cdn);
		} else {
			Obj n = Tab.insert(Obj.Con, s, Tab.intType);
			n.setAdr(cdn.getVar());
		}

	}

	public void visit(ConstDeclSingleCHAR cdn) {
		Struct leftType = cdn.getType().struct;
		if (!leftType.assignableTo(Tab.charType)) {
			report_error("Greska ne poklapaju se tipovi ", cdn);
		}
		currentConstType = leftType;
		String s = cdn.getConstName();

		Obj o = Tab.find(s);

		if (o != Tab.noObj) {
			report_error("Ime vec postoji Ime:" + s, cdn);
		} else {
			Obj n = Tab.insert(Obj.Con, s, Tab.charType);
			n.setAdr(cdn.getVar());
		}

	}

	public void visit(ConstDeclSingleBOOL cdn) {
		Struct leftType = cdn.getType().struct;
		if (!leftType.equals(TabWrap.boolType)) {
			report_error("Greska ne poklapaju se tipovi ", cdn);
		}
		currentConstType = leftType;
		String s = cdn.getConstName();

		Obj o = Tab.find(s);

		if (o != Tab.noObj) {
			report_error("Ime vec postoji Ime:" + s, cdn);
		} else {
			Obj n = Tab.insert(Obj.Con, s, TabWrap.boolType);
			n.setAdr(cdn.getVar() ? 1 : 0);
		}

	}

	public void visit(ConstIdentDecalsNumConst cdn) {

		if (!currentConstType.assignableTo(Tab.intType)) {
			report_error("Greska ne poklapaju se tipovi ", cdn);
		}

		String s = cdn.getI2();

		Obj o = Tab.find(s);

		if (o != Tab.noObj) {
			report_error("Ime vec postoji Ime:" + s, cdn);
		} else {
			Obj n = Tab.insert(Obj.Con, s, Tab.intType);
			n.setAdr(cdn.getN3());
		}

	}

	public void visit(ConstIdentDecalsCharConst cdn) {

		if (!currentConstType.assignableTo(Tab.charType)) {
			report_error("Greska ne poklapaju se tipovi ", cdn);
		}
		String s = cdn.getI2();

		Obj o = Tab.find(s);

		if (o != Tab.noObj) {
			report_error("Ime vec postoji Ime:" + s, cdn);
		} else {
			Obj n = Tab.insert(Obj.Con, s, Tab.charType);
			n.setAdr(cdn.getC3());
		}

	}

	public void visit(ConstIdentDecalsBoolConst cdn) {

		if (!currentConstType.equals(TabWrap.boolType)) {
			report_error("Greska ne poklapaju se tipovi ", cdn);
		}

		String s = cdn.getI2();

		Obj o = Tab.find(s);

		if (o != Tab.noObj) {
			report_error("Ime vec postoji Ime:" + s, cdn);
		} else {
			Obj n = Tab.insert(Obj.Con, s, TabWrap.boolType);
			n.setAdr(cdn.getB3() ? 1 : 0);
		}

	}

	// FormalArgs

	public void visit(FormParsSingleVAR fvar) {
		String s = fvar.getFName();
		Struct t = fvar.getType().struct;
		Obj o = Tab.currentScope().findSymbol(s);

		if (o != null) {
			report_error("Ime vec postoji Ime:" + s, fvar);
		} else {
			Tab.insert(Obj.Var, s, t).setFpPos(++nFormals);

		}
	}

	public void visit(FormParsSingleARR fvar) {
		String s = fvar.getFName();
		Struct t = fvar.getType().struct;

		Obj o = Tab.currentScope().findSymbol(s);

		if (o != null) {
			report_error("Ime vec postoji Ime:" + s, fvar);
		} else {
			Tab.insert(Obj.Var, s, new Struct(Struct.Array, t)).setFpPos(++nFormals);
		}
	}

	// OPTARGS
	int minusCount = 0;

	private List<Obj> getListDefaults(String name) {
		List<Obj> oList = constDefaults.get(name);
		if (oList == null) {
			oList = new LinkedList<>();
			constDefaults.put(name, oList);
		}
		return oList;
	}

	public void visit(OptArgNUMCONST optarg) {

		String s = optarg.getOptArgName();

		Obj o = Tab.currentScope().findSymbol(s);

		if (o != null) {
			report_error("Ime vec postoji Ime:" + s, optarg);
		} else {
			Obj t = Tab.insert(Obj.Var, s, Tab.intType);
			t.setFpPos(--minusCount);
			nOpts++;

			Obj i = new Obj(Obj.Con, s, Tab.intType);
			i.setAdr(optarg.getN2());
			i.setFpPos(minusCount);

			List<Obj> oList = getListDefaults(cMethod.getName());

			oList.add(i);
		}
	}

	public void visit(OptArgCHARCONST optarg) {
		String s = optarg.getOptArgName();

		Obj o = Tab.currentScope().findSymbol(s);

		if (o != null) {
			report_error("Ime vec postoji Ime:" + s, optarg);
		} else {

			Obj t = Tab.insert(Obj.Var, s, Tab.charType);
			;
			t.setFpPos(--minusCount);
			// t.setAdr(optarg.getC2());
			nOpts++;

			Obj i = new Obj(Obj.Con, s, Tab.charType);
			i.setAdr(optarg.getC2());
			i.setFpPos(minusCount);

			List<Obj> oList = getListDefaults(cMethod.getName());

			oList.add(i);
		}
	}

	public void visit(OptArgBOOLCONST optarg) {
		String s = optarg.getOptArgName();

		Obj o = Tab.currentScope().findSymbol(s);

		if (o != null) {
			report_error("Ime vec postoji Ime:" + s, optarg);
		} else {
			Obj t = Tab.insert(Obj.Var, s, TabWrap.boolType);
			t.setFpPos(--minusCount);
			// t.setAdr(optarg.getB2() ? 1:0);
			nOpts++;

			Obj i = new Obj(Obj.Con, s, Tab.intType);
			i.setAdr(optarg.getB2() ? 1 : 0);
			i.setFpPos(minusCount);

			List<Obj> oList = getListDefaults(cMethod.getName());

			oList.add(i);
		}
	}

	// ActPars
	int numberActualsPars = 0;
	Map<Integer, Obj> actualsToCheck = new HashMap<>();

	Stack<Map<Integer, Obj>> actualsStack = new Stack<>();
	Stack<Integer> NumOfActualsStack = new Stack<>();

	public void visit(ActParsStart ap) {
		Struct s = ap.getExpr().struct;
		Obj o = new Obj(Obj.Var, "actual", s);
		if (numberActualsPars > 0) {
			actualsStack.push(actualsToCheck);
			NumOfActualsStack.push(numberActualsPars);
			actualsToCheck = new HashMap<>();
			numberActualsPars = 0;
		}
		o.setFpPos(++numberActualsPars);
		actualsToCheck.put(numberActualsPars, o);
	}

	public void visit(ActualParsList ap) {
		Struct s = ap.getExpr().struct;
		Obj o = new Obj(Obj.Var, "actual", s);
		o.setFpPos(++numberActualsPars);
		actualsToCheck.put(numberActualsPars, o);
	}

	public void visit(NoActParsList ap) {
		Struct s = ap.getExpr().struct;
		Obj o = new Obj(Obj.Var, "actual", s);
		o.setFpPos(++numberActualsPars);
		actualsToCheck.put(numberActualsPars, o);
	}


	// Methods

	public void visit(MStart ms) {
		String s = ms.getMethodName();
		if (s.equals("main")) {
			report_error("Greska na main metoda mora biti tipa void", ms);
		} else {
			Obj o = Tab.currentScope().findSymbol(s);

			if (o != null) {
				report_error("Ime vec postoji Ime:" + s, ms);
			} else {
				cMethod = Tab.insert(Obj.Meth, s, ms.getType().struct);
				ms.obj = cMethod;
			}

		}

		MethReturnTypeRequired = ms.getType().struct;

		returnFound = false;
		Tab.openScope();
	}

	public void visit(MStartVoid ms) {
		String s = ms.getMethodName();

		Obj o = Tab.currentScope().findSymbol(s);

		if (o != null) {
			report_error("Ime vec postoji Ime:" + s, ms);
		} else {
			cMethod = Tab.insert(Obj.Meth, s, Tab.noType);// Void ?
			ms.obj = cMethod;
		}

		MethReturnTypeRequired = Tab.noType;

		returnFound = false;
		Tab.openScope();
	}

	public void visit(MethodDeclNoParams mdnp) {

		nOpts = 0;
		nFormals = 0;
		if (!returnFound) {
			report_error("Nije nadjen return", mdnp);
		}
		Tab.chainLocalSymbols(cMethod);
		Tab.closeScope();
		minusCount = 0;
	}

	public void visit(MethodDeclParams mdnp) {

		int temp = cMethod.getLevel();// Nivo na kome je definisan simbol
		cMethod.setFpPos(temp);

		cMethod.setLevel(nFormals);// Broj FormalnihArg
		nOpts = 0;
		nFormals = 0;
		if (!returnFound) {
			report_error("Nije nadjen return", mdnp);
		}
		Tab.chainLocalSymbols(cMethod);
		Tab.closeScope();
		numberActualsPars = 0;
		minusCount = 0;
	}

	public void visit(MethodDeclVoidParams mdnp) {

		int temp = cMethod.getLevel();// Nivo na kome je definisan simbol
		cMethod.setFpPos(temp);

		cMethod.setLevel(nFormals);// Broj FormalnihArg
		nOpts = 0;
		nFormals = 0;
		Tab.chainLocalSymbols(cMethod);
		Tab.closeScope();
		numberActualsPars = 0;
		minusCount = 0;
	}

	public void visit(MethodDeclVoidNoParams mdnp) {
		String s = mdnp.getMStartVoid().getMethodName();

		if (s.equals("main") && nOpts > 0) {
			report_error("Greska na main metoda ne sme imati parametre", mdnp);
		} else if (s.equals("main")) {
			foundMain = true;
		}

		nOpts = 0;
		nFormals = 0;
		Tab.chainLocalSymbols(cMethod);
		Tab.closeScope();
		minusCount = 0;
	}

	// CLASSES

	public void visit(ClassName className) {
		String cName = className.getCName();
		Obj s = Tab.find(cName);

		if (s == Tab.noObj) {
			Tab.insert(Obj.Type, cName, Tab.nullType);
			currentClass = cName;
		} else {
			report_error("Ime vec postoji Ime:" + cName, className);
		}

	}

	public void visit(ExtendsType exType) {
		Obj s = Tab.find(exType.getType().getTypeName());

		if (s == Tab.noObj) {
			report_error("Ne postoji tip " + exType.getType().getTypeName(), exType);
		} else if (s.getKind() != Obj.Type) {
			report_error("Tip " + exType.getType().getTypeName() + " nije tip ", exType);
		} else if (s.getType().getKind() != Struct.Class) {
			report_error("Tip " + exType.getType().getTypeName() + " nije klasa ", exType);
		} else if (exType.getType().getTypeName().equals(currentClass)) {
			report_error("Nije moguce izvesti tip iz samog sebe", exType);
		}
	}

	public void visit(ConstructorDecl cd) {
		if (!cd.getConstructorName().equals(currentClass)) {
			report_error("Konstrukor mora imati isto ime kao i klasa", cd);
		}
	}

	// Vars

	Struct curType;

	public void visit(VNameAct vn) {
		String s = vn.getVarName();
		Obj o = Tab.currentScope().findSymbol(s);
		if (o != null) {
			report_error("Ime vec postoji Ime: " + s, vn);
		} else {
			Struct t = vn.getType().struct;
			Tab.insert(Obj.Var, s, t);// PromeniKind
			curType = t;
		}
	}

	public void visit(VNameArr vn) {
		String s = vn.getVarName();
		Obj o = Tab.currentScope().findSymbol(s);
		if (o != null) {
			report_error("Ime vec postoji Ime: " + s, vn);
		} else {
			Struct t = vn.getType().struct;
			Tab.insert(Obj.Var, s, new Struct(Struct.Array, t));// PromeniKind
			curType = t;
		}
	}

	public void visit(VarIdentDeclsVar var) {
		String s = var.getVarName();
		Obj o = Tab.currentScope().findSymbol(s);
		if (o != null) {
			report_error("Ime vec postoji Ime: " + s, var);
		} else {
			Struct t = curType;
			Tab.insert(Obj.Var, s, t);// PromeniKind
		}
	}

	public void visit(VarIdentDeclsArr var) {
		String s = var.getVarName();
		Obj o = Tab.currentScope().findSymbol(s);
		if (o != null) {
			report_error("Ime vec postoji Ime: " + s, var);
		} else {
			Struct t = curType;
			Tab.insert(Obj.Var, s, new Struct(Struct.Array, t));// PromeniKind
		}
	}

	// Statements

	public void visit(AssignOpDesignator daop) {
		Obj left = daop.getDesignator().obj;
		Struct right = daop.getExpr().struct;

		if (!right.assignableTo(left.getType())) {
			report_error("Tipovi nisu kompatibilni ", daop);
		}
	}

	public void visit(DesignatorStatementFunct df) {
		Obj o = df.getDesignator().obj;
		int countActuals = 1;

		if (o.getKind() != Obj.Meth) {
			report_error("Ime " + "nije metoda ", df);
		} else if (o.getLevel() > 0) {
			report_error("Pogresan broj parametra ", df);
		} else {
			Collection<Obj> oList = o.getLocalSymbols();
			int nFormals = o.getLevel();

			if (o.getFpPos() == 0) {
				report_info("Poziv globalne funkcije ", df);
				report_info("\t " + prObj(o), null);
			}

			for (Iterator iterator = oList.iterator(); iterator.hasNext();) {
				Obj obj = (Obj) iterator.next();
				if (obj.getFpPos() > 0) {
					Obj actual = actualsToCheck.get(obj.getFpPos());
					if (actual == null) {
						report_error("Ne poklapa se broj parametra ", df);
					} else if (!obj.getType().assignableTo(actual.getType())
							&& !(o.getName().equals("len") && (actual.getType().getKind() == Struct.Array))) {
						report_error("Ne poklapaju se tipovi parametra na poziciji" + actual.getFpPos() + " ", df);
					} else {
						actualsToCheck.remove(obj.getFpPos());
						nFormals--;
						// report_error("ping " + actualsToCheck.size(), df);
						countActuals++;
					}

				}
			}
			if (nFormals != 0) {
				report_error("Ne poklapa se broj formalnih parametra ", df);
			} else {
				for (Iterator iterator = oList.iterator(); iterator.hasNext();) {
					Obj obj = (Obj) iterator.next();
					if (obj.getFpPos() < 0) {
						Obj actual = actualsToCheck.get(countActuals);
						if (actual == null) {
							// report_error("Ne bi trebalo ovde da dodje ", df);
							break;
						} else if (obj.getType().assignableTo(actual.getType())) {
							actualsToCheck.remove(countActuals++);
							// report_info("Ping info inner loop 1", df);
							if (actualsToCheck.isEmpty()) {
								break;
							}
							// report_info("Ping info inner loop 2", df);
						}
//						else {
//							report_info("Ping info "+ actual.getType().getKind()  + " " + obj.getType().getKind(), df);
//						}
					}
//					else {
//						report_info("Ping info fpPos", df);
//					}
				}

				if (!actualsToCheck.isEmpty()) {
					report_error("Ne poklapa se broj parametra ", df);
				}

				countActuals = 1;
			}

		}

		if (!NumOfActualsStack.isEmpty()) {
			actualsToCheck = actualsStack.pop();
			numberActualsPars = NumOfActualsStack.pop();
		} else {
			numberActualsPars = 0;
			actualsToCheck.clear();
		}
	}

	public void visit(DesignatorStatementFunctActPars df) {
		Obj o = df.getDesignator().obj;
		int countActuals = 1;

		if (o.getKind() != Obj.Meth) {
			report_error("Ime " + "nije metoda ", df);
		} else {
			Collection<Obj> oList = o.getLocalSymbols();
			int nFormals = o.getLevel();

			if (o.getFpPos() == 0) {
				report_info("Poziv globalne funkcije ", df);
				report_info("\t " + prObj(o), null);
			}

			for (Iterator iterator = oList.iterator(); iterator.hasNext();) {
				Obj obj = (Obj) iterator.next();
				if (obj.getFpPos() > 0) {
					Obj actual = actualsToCheck.get(obj.getFpPos());
					if (actual == null) {
						report_error("Ne poklapa se broj parametra ", df);
					} else if (!obj.getType().assignableTo(actual.getType())
							&& !(o.getName().equals("len") && (actual.getType().getKind() == Struct.Array))) {
						report_error("Ne poklapaju se tipovi parametra na poziciji " + actual.getFpPos() + " ", df);
					} else {
						actualsToCheck.remove(obj.getFpPos());
						nFormals--;
						// report_error("ping " + actualsToCheck.size(), df);
						countActuals++;
					}

				}
			}
			if (nFormals != 0) {
				report_error("Ne poklapa se broj formalnih parametra ", df);
			} else {
				for (Iterator iterator = oList.iterator(); iterator.hasNext();) {
					Obj obj = (Obj) iterator.next();
					if (obj.getFpPos() < 0) {
						Obj actual = actualsToCheck.get(countActuals);
						if (actual == null) {
							// report_error("Ne bi trebalo ovde da dodje " + countActuals, df);
							break;
						} else if (obj.getType().assignableTo(actual.getType())) {
							actualsToCheck.remove(countActuals++);
							// report_info("Ping info inner loop 1", df);
							if (actualsToCheck.isEmpty()) {
								break;
							}
							// report_info("Ping info inner loop 2", df);
						}
//						else {
//							report_info("Ping info "+ actual.getType().getKind()  + " " + obj.getType().getKind(), df);
//						}
					}
//					else {
//						report_info("Ping info fpPos", df);
//					}
				}

				if (!actualsToCheck.isEmpty()) {
					report_error("Ne poklapa se broj parametra ", df);
				}

				countActuals = 1;
			}

		}

		if (!NumOfActualsStack.isEmpty()) {
			actualsToCheck = actualsStack.pop();
			numberActualsPars = NumOfActualsStack.pop();
		} else {
			numberActualsPars = 0;
			actualsToCheck.clear();
		}
	}

	public void visit(ReturnSingleStatement s) {
		returnFound = true;
		if (MethReturnTypeRequired != Tab.noType) {
			report_error("Tip u returnu se ne poklapa", s);
		}
	}

	public void visit(ReturnVarSingleStatement s) {
		returnFound = true;
		if (MethReturnTypeRequired != s.getExpr().struct) {
			report_error("Tip u returnu se ne poklapa", s);
		}
	}

	public void visit(ReadSingleStatement rs) {
		Obj o = rs.getDesignator().obj;
		if (o.getType().getKind() != Struct.Int && o.getType().getKind() != Struct.Char
				&& o.getType().getKind() != TabWrap.boolType.getKind()) {
			report_error("Designator za read mora biti int, char ili bool ", rs);
		}
	}

	public void visit(PrintSingleStatement ps) {
		Struct s = ps.getExpr().struct;

		if (s == null) {
			report_info("Ne bi trebalo ovde opet da udje tokom print ", ps);
		} else {
			if (s.getKind() != Struct.Int && s.getKind() != Struct.Char && s.getKind() != TabWrap.boolType.getKind()) {
				report_error("Expr za print mora biti int, char ili bool ", ps);
			}
		}

	}

	public void visit(PrintArgsSingleStatement ps) {
		Struct s = ps.getExpr().struct;
		if (s.getKind() != Struct.Int && s.getKind() != Struct.Char && s.getKind() != TabWrap.boolType.getKind()) {
			report_error("Expr za print mora biti int, char ili bool ", ps);
		}
	}

	public void visit(IfSingleStatement ifs) {
		Struct s = ifs.getCondition().struct;
		if (s != TabWrap.boolType) {
			report_error("Uslov u if mora biti tipa bool ", ifs);
		}
	}

	public void visit(IfElseSingleStatement ifs) {
		Struct s = ifs.getCondition().struct;
		if (s != TabWrap.boolType) {
			report_error("Uslov u if mora biti tipa bool ", ifs);
		}
	}

	public void visit(DoWhileStart d) {
		inDoWhile = true;
	}

	public void visit(DoWhileSingleStatement dws) {
		Struct s = dws.getCondition().struct;
		inDoWhile = false;
		if (s != TabWrap.boolType) {
			report_error("Uslov u do-while mora biti tipa bool ", dws);
		}
	}

	public void visit(BreakSingleStatement b) {
		if (!inDoWhile) {
			report_error("Break se moze koristiti samo unutar do-while petlje ", b);
		}
	}

	public void visit(ContinueSingleStatement c) {
		if (!inDoWhile) {
			report_error("Continue se moze koristiti samo unutar do-while petlje ", c);
		}
	}

	// Designator
	Obj curDes = null;

	public void visit(DesignatorIdent d) {
		String s = d.getDName();
		Obj o = Tab.find(s);
		// report_info("Temp des", d);
		if (o == Tab.noObj) {
			report_error("Ime " + s + " ne posotji", d);
			d.obj = Tab.noObj;
		} else {
			d.obj = o;
			if (o.getKind() == Obj.Var) {

				if (o.getLevel() == 0) {
					report_info("Pristup globalnoj promenjivi ", d);
					report_info("\t " + prObj(o), null);
				} else if (o.getFpPos() > 0) {
					report_info("Pristup formalnom parametru ", d);
					report_info("\t " + prObj(o), null);
				} else {
					report_info("Pristup lokalnoj promenjivi ", d);
					report_info("\t " + prObj(o), null);
				}
			}

		}

		// report_info("Print " + d.getDName(), d);
	}

	public void visit(DesignatorDot d) {
		Obj prev = d.getDesignator().obj;
		String s = d.getDotName();
		if (prev.getKind() != Obj.Var && prev.getKind() != Obj.Fld && prev.getKind() != Obj.Elem) {
			report_error("Polje ne posoji", d);
			d.obj = Tab.noObj;
		} else {
			Obj act = null;
			if (d.getDesignator() instanceof DesignatorThis) {
				Scope out = Tab.currentScope.getOuter();
				act = out.findSymbol(s);
			} else {
				act = prev.getType().getMembersTable().searchKey(s);
			}

			if (act != null) {
				d.obj = act;
				if (act.getKind() == Obj.Var) {
					if (act.getLevel() == 0) {
						report_info("Pristup globalnoj promenjivi ", d);
						report_info("\t " + prObj(act), null);
					} else if (act.getFpPos() > 0) {
						report_info("Pristup formalnom parametru ", d);
						report_info("\t " + prObj(act), null);
					} else {
						report_info("Pristup lokalnoj promenjivi ", d);
						report_info("\t " + prObj(act), null);
					}
				}
			} else {
				report_error("Ne postoji polje " + s, d);
			}
		}

		// report_info("Print " + d.getDotName(), d);
	}

	public void visit(DesignatorArr d) {
		Obj o = d.getDesignator().obj;
		if (o.getType().getKind() != Struct.Array) {
			report_error("Polje nije niz", d);
			d.obj = Tab.noObj;
		} else {
			Expr e = d.getExpr();
			if (e.struct.equals(Tab.intType)) {
				d.obj = new Obj(Obj.Elem, "/", o.getType().getElemType());
				report_info("Pristup elementu niza", d);
				report_info("\t " + prObj(o), null);
			} else {
				report_error("Izraz za pristup elementu niza mora biti int", d);
				d.obj = Tab.noObj;
			}
		}
	}

	public void visit(DesignatorStatementInc d) {
		Obj o = d.getDesignator().obj;
		if (o.getType().getKind() != Struct.Int) {
			report_error("Designator mora biti tipa int za operator ++ ", d);
		}
	}

	public void visit(DesignatorStatementDec d) {
		Obj o = d.getDesignator().obj;
		if (o.getType().getKind() != Struct.Int) {
			report_error("Designator mora biti tipa int za operator -- ", d);
		}
	}

	// Expr Term Factor

	public void visit(FactorClass f) {
		Obj o = f.getDesignator().obj;
		f.struct = o.getType();
	}

	public void visit(FactorFunc f) {
		Obj o = f.getDesignator().obj;
		//report_info("FF  ", f);
		if (o.getKind() != Obj.Meth) {
			report_error("Ime ne predstavlja metodu", f);
			f.struct = Tab.noType;
		}
		else if(o.getLevel() > 0) {
			report_error("Pogresan broj parametra ", f);
			f.struct = Tab.noType;
		}
		else {
			f.struct = o.getType();
			if(o.getFpPos()==0) {
				report_info("Poziv globalne funkcije ", f);
				report_info("\t " + prObj(o), null);
			}
		}
	}

//	public void visit(FactorFuncActPars f) {
//		Obj o = f.getDesignator().obj;
//		if (o.getKind() != Obj.Meth) {
//			report_error("Ime ne predstavlja metodu", f);
//			f.struct = Tab.noType;
//		} else {
//			f.struct = o.getType();
//			if(o.getFpPos()==0) {
//				report_info("Poziv globalne funkcije ", f);
//			}
//		}
//	}


	public void visit(FactorFuncActPars f) {
		Obj o = f.getDesignator().obj;
		int countActuals = 1;
		Struct s = Tab.nullType;

		if (o.getKind() != Obj.Meth) {
			report_error("Ime " + "nije metoda ", f);
			s = Tab.noType;
		} else {

			Collection<Obj> oList = o.getLocalSymbols();
			int nFormals = o.getLevel();

			if (o.getFpPos() == 0) {
				report_info("Poziv globalne funkcije ", f);
				report_info("\t " + prObj(o), null);
			}

			for (Iterator iterator = oList.iterator(); iterator.hasNext();) {
				Obj obj = (Obj) iterator.next();
				if (obj.getFpPos() > 0) {
					Obj actual = actualsToCheck.get(obj.getFpPos());
					if (actual == null) {
						report_error("Ne poklapa se broj parametra ", f);
						s = Tab.noType;
					} else if (!obj.getType().assignableTo(actual.getType())
							&& !(o.getName().equals("len") && (actual.getType().getKind() == Struct.Array))) {
						report_error("Ne poklapaju se tipovi parametra na poziciji " + actual.getFpPos() + " ", f);
						s = Tab.noType;
					} else {
						actualsToCheck.remove(obj.getFpPos());
						nFormals--;
						// report_error("ping " + actualsToCheck.size(), f);
						countActuals++;
					}

				}
			}
			if (nFormals != 0) {
				report_error("Ne poklapa se broj formalnih parametra ", f);
				s = Tab.noType;
			} else {
				for (Iterator iterator = oList.iterator(); iterator.hasNext();) {
					Obj obj = (Obj) iterator.next();
					if (obj.getFpPos() < 0) {
						Obj actual = actualsToCheck.get(countActuals);
						if (actual == null) {
							// report_error("Ne bi trebalo ovde da dodje " + countActuals, f);
							break;
						} else if (obj.getType().assignableTo(actual.getType())) {
							actualsToCheck.remove(countActuals++);
							// report_info("Ping info inner loop 1", f);
							if (actualsToCheck.isEmpty()) {
								break;
							}
							// report_info("Ping info inner loop 2", f);
						}
//						else {
//							report_info("Ping info "+ actual.getType().getKind()  + " " + obj.getType().getKind(), f);
//						}
					}
//					else {
//						report_info("Ping info fpPos", f);
//					}
				}

				if (!actualsToCheck.isEmpty()) {
					report_error("Ne poklapa se broj parametra ", f);
					s = Tab.noType;
				}

				countActuals = 1;
			}

		}
		if (s == Tab.nullType) {

			f.struct = o.getType();
		}
		else {
			f.struct = s;
		}
		
		if (!NumOfActualsStack.isEmpty()) {
			actualsToCheck = actualsStack.pop();
			numberActualsPars = NumOfActualsStack.pop();
		} else {
			numberActualsPars = 0;
			actualsToCheck.clear();
		}

	}

	public void visit(FactorNumConst f) {
		f.struct = Tab.intType;
	}

	public void visit(FactorCharConst f) {
		f.struct = Tab.charType;
	}

	public void visit(FactorBoolConst f) {
		f.struct = TabWrap.boolType;
	}

	public void visit(FactorNew f) {
		Struct o = f.getType().struct;
		f.struct = o;
	}

	public void visit(FactorNewArr f) {
		Struct s = f.getExpr().struct;
		if (s.getKind() != Struct.Int) {
			report_error("Expr nije tipa int ", f);
			s = Tab.noType;
		} else {
			s = new Struct(Struct.Array);
			s.setElementType(f.getType().struct);

		}
		f.struct = s;
	}

	public void visit(FactorBracketsExpr f) {
		Struct s = f.getExpr().struct;
		f.struct = s;
	}

	public void visit(TermStart t) {
		termType = t.getFactor().struct;
	}

	public void visit(TermSingle t) {
		t.struct = termType;
	}

	public void visit(TermMultiple t) {
		Struct Right = t.getTermFactorList().struct;
		if (!termType.compatibleWith(Right)) {
			report_error("Nisu kompatibilni tipovi", t);
			t.struct = Tab.noType;
		} else if (termType.getKind() != Struct.Int || Right.getKind() != Struct.Int) {
			report_error("Tipovi moraju biti int", t);
			t.struct = Tab.noType;
		} else {
			t.struct = termType;
		}
	}

	public void visit(NoTermFactorList t) {
		t.struct = t.getFactor().struct;
	}

	public void visit(TermFactorListClass t) {
		Struct Left = t.getTermFactorList().struct;
		Struct Right = t.getFactor().struct;
		if (!Left.compatibleWith(Right) || !Left.compatibleWith(termType)) {
			report_error("Nisu kompatibilni tipovi", t);
			t.struct = Tab.noType;
		} else if (Left.getKind() != Struct.Int || Right.getKind() != Struct.Int) {
			report_error("Tipovi moraju biti int", t);
			t.struct = Tab.noType;
		} else {
			t.struct = Right;
		}
	}

	public void visit(ExprStart e) {
		exprType = e.getTerm().struct;
	}

	public void visit(ExpressionSingle e) {
		e.struct = exprType;
	}

	public void visit(ExpressionMultiple e) {
		Struct Right = e.getExprTermList().struct;
		if (!exprType.compatibleWith(Right)) {
			report_error("Nisu kompatibilni tipovi", e);
			e.struct = Tab.noType;
		} else if (exprType.getKind() != Struct.Int || Right.getKind() != Struct.Int) {
			report_error("Tipovi moraju biti int", e);
			e.struct = Tab.noType;
		} else {
			e.struct = exprType;
		}
	}

	public void visit(MinusExpr e) {
		e.struct = exprType;
	}

	public void visit(MinusExprMultiple e) {
		Struct Right = e.getExprTermList().struct;
		if (!exprType.compatibleWith(Right)) {
			report_error("Nisu kompatibilni tipovi", e);
			e.struct = Tab.noType;
		} else if (exprType.getKind() != Struct.Int || Right.getKind() != Struct.Int) {
			report_error("Tipovi moraju biti int", e);
			e.struct = Tab.noType;
		} else {
			e.struct = exprType;
		}
	}

	public void visit(ExprTermListClass e) {
		Struct Right = e.getTerm().struct;
		Struct Left = e.getExprTermList().struct;
		if (!Left.compatibleWith(Right)) {
			report_error("Nisu kompatibilni tipovi", e);
			e.struct = Tab.noType;
		} else if (Left.getKind() != Struct.Int || Left.getKind() != Struct.Int) {
			report_error("Tipovi moraju biti int", e);
			e.struct = Tab.noType;
		} else {
			e.struct = exprType;
		}
	}

	public void visit(NoExprTermList e) {
		e.struct = e.getTerm().struct;
	}

	// Conditions
	public void visit(ConditionSingle c) {
		c.struct = c.getCondTerm().struct;
	}

	public void visit(ConditionMultiple c) {
		c.struct = c.getCondTerm().struct;
	}

	public void visit(ConditionError c) {
		c.struct = Tab.noType;
	}

	public void visit(ConditionTermListClass cf) {
		cf.struct = cf.getCondTerm().struct;
	}

	public void visit(NoConditionTermList cf) {
		cf.struct = cf.getCondTerm().struct;
	}

	public void visit(CondTermSingle cf) {
		cf.struct = cf.getCondFact().struct;
	}

	public void visit(CondTermMultiple cf) {
		cf.struct = cf.getCondFact().struct;
	}

	public void visit(CondFactListClass cf) {
		cf.struct = cf.getCondFact().struct;
	}

	public void visit(NoCondFactList cf) {
		cf.struct = cf.getCondFact().struct;
	}

	public void visit(ConditionFact cf) {
		Struct s = cf.getExpr().struct;
		cf.struct = s;

	}

	public void visit(CondFactRelop cf) {
		Struct left = cf.getExpr().struct;
		Struct right = cf.getExpr1().struct;
		Relop rop = cf.getRelop();
		if (!left.compatibleWith(right)) {
			report_error("Tipovi nisu kompatibilni za relop", cf);
			cf.struct = Tab.noType;
		} else if ((left.getKind() == Struct.Array || left.getKind() == Struct.Class)
				&& !(rop instanceof RelopLEQ || rop instanceof RelopLNEQ)) {
			report_error("Za klase i nizove se mora koristiti relop:== ili relop:!=", cf);
			cf.struct = Tab.noType;
		} else if (left.getKind() == Struct.Array || left.getKind() == Struct.Class) {
			cf.struct = TabWrap.boolType;
		} else {
			cf.struct = TabWrap.boolType; // TODO proveri za constante i intove
		}

	}

}
