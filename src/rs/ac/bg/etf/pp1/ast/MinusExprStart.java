// generated with ast extension for cup
// version 0.8
// 18/7/2022 22:25:33


package rs.ac.bg.etf.pp1.ast;

public class MinusExprStart implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private ExprStart ExprStart;

    public MinusExprStart (ExprStart ExprStart) {
        this.ExprStart=ExprStart;
        if(ExprStart!=null) ExprStart.setParent(this);
    }

    public ExprStart getExprStart() {
        return ExprStart;
    }

    public void setExprStart(ExprStart ExprStart) {
        this.ExprStart=ExprStart;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ExprStart!=null) ExprStart.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ExprStart!=null) ExprStart.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ExprStart!=null) ExprStart.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MinusExprStart(\n");

        if(ExprStart!=null)
            buffer.append(ExprStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MinusExprStart]");
        return buffer.toString();
    }
}
