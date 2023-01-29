// generated with ast extension for cup
// version 0.8
// 18/7/2022 22:25:33


package rs.ac.bg.etf.pp1.ast;

public class ExpressionMultiple extends Expr {

    private ExprStart ExprStart;
    private Addop Addop;
    private ExprTermList ExprTermList;

    public ExpressionMultiple (ExprStart ExprStart, Addop Addop, ExprTermList ExprTermList) {
        this.ExprStart=ExprStart;
        if(ExprStart!=null) ExprStart.setParent(this);
        this.Addop=Addop;
        if(Addop!=null) Addop.setParent(this);
        this.ExprTermList=ExprTermList;
        if(ExprTermList!=null) ExprTermList.setParent(this);
    }

    public ExprStart getExprStart() {
        return ExprStart;
    }

    public void setExprStart(ExprStart ExprStart) {
        this.ExprStart=ExprStart;
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
        if(ExprStart!=null) ExprStart.accept(visitor);
        if(Addop!=null) Addop.accept(visitor);
        if(ExprTermList!=null) ExprTermList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ExprStart!=null) ExprStart.traverseTopDown(visitor);
        if(Addop!=null) Addop.traverseTopDown(visitor);
        if(ExprTermList!=null) ExprTermList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ExprStart!=null) ExprStart.traverseBottomUp(visitor);
        if(Addop!=null) Addop.traverseBottomUp(visitor);
        if(ExprTermList!=null) ExprTermList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ExpressionMultiple(\n");

        if(ExprStart!=null)
            buffer.append(ExprStart.toString("  "+tab));
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
        buffer.append(") [ExpressionMultiple]");
        return buffer.toString();
    }
}
