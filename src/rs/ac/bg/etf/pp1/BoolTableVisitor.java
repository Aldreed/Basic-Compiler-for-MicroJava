package rs.ac.bg.etf.pp1;

import rs.etf.pp1.symboltable.concepts.Struct;
import rs.etf.pp1.symboltable.visitors.DumpSymbolTableVisitor;

public class BoolTableVisitor extends DumpSymbolTableVisitor {

	@Override
	public void visitStructNode(Struct structToVisit) {
		super.visitStructNode(structToVisit);
		if(structToVisit.getKind() == Struct.Bool) {
			output.append("bool");
		}
		else if(structToVisit.getKind() == Struct.Array && structToVisit.getElemType().getKind() == Struct.Bool) {
			output.append("bool");
		}
		
	}


	
}
