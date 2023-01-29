// generated with ast extension for cup
// version 0.8
// 18/7/2022 22:25:33


package rs.ac.bg.etf.pp1.ast;

public class MinusExprMultiple extends Expr {

    private MinusExprStart MinusExprStart;
    private Addop Addop;
    private ExprTermList ExprTermList;

    public MinusExprMultiple (MinusExprStart MinusExprStart, Addop Addop, ExprTermList ExprTermList) {
        this.MinusExprStart=MinusExprStart;
        if(MinusExprStart!=null) MinusExprStart.setParent(this);
        this.Addop=Addop;
        if(Addop!=null) Addop.setParent(this);
        this.ExprTermList=ExprTermList;
        if(ExprTermList!=null) ExprTermList.setParent(this);
    }

    public MinusExprStart getMinusExprStart() {
        return MinusExprStart;
    }

    public void setMinusExprStart(MinusExprStart MinusExprStart) {
        this.MinusExprStart=MinusExprStart;
    }

    public Addop getAddop() {
        return Addop;
    }

    public void setAddop(Addop Addop) {
        this.Addop=Addop;
    }

    public ExprTermList getExprTermList() {
        return ExprTermList;
    }

    public void setExprTermList(ExprTermList ExprTermList) {
        this.ExprTermList=ExprTermList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MinusExprStart!=null) MinusExprStart.accept(visitor);
        if(Addop!=null) Addop.accept(visitor);
        if(ExprTermList!=null) ExprTermList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MinusExprStart!=null) MinusExprStart.traverseTopDown(visitor);
        if(Addop!=null) Addop.traverseTopDown(visitor);
        if(ExprTermList!=null) ExprTermList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MinusExprStart!=null) MinusExprStart.traverseBottomUp(visitor);
        if(Addop!=null) Addop.traverseBottomUp(visitor);
        if(ExprTermList!=null) ExprTermList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MinusExprMultiple(\n");

        if(MinusExprStart!=null)
            buffer.append(MinusExprStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Addop!=null)
            buffer.append(Addop.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ExprTermList!=null)
            buffer.append(ExprTermList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MinusExprMultiple]");
        return buffer.toString();
    }
}
