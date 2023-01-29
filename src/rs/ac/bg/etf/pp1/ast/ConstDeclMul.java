// generated with ast extension for cup
// version 0.8
// 18/7/2022 22:25:33


package rs.ac.bg.etf.pp1.ast;

public class ConstDeclMul extends ConstDecl {

    private ConstSingleDecl ConstSingleDecl;
    private ConstIdentDecls ConstIdentDecls;

    public ConstDeclMul (ConstSingleDecl ConstSingleDecl, ConstIdentDecls ConstIdentDecls) {
        this.ConstSingleDecl=ConstSingleDecl;
        if(ConstSingleDecl!=null) ConstSingleDecl.setParent(this);
        this.ConstIdentDecls=ConstIdentDecls;
        if(ConstIdentDecls!=null) ConstIdentDecls.setParent(this);
    }

    public ConstSingleDecl getConstSingleDecl() {
        return ConstSingleDecl;
    }

    public void setConstSingleDecl(ConstSingleDecl ConstSingleDecl) {
        this.ConstSingleDecl=ConstSingleDecl;
    }

    public ConstIdentDecls getConstIdentDecls() {
        return ConstIdentDecls;
    }

    public void setConstIdentDecls(ConstIdentDecls ConstIdentDecls) {
        this.ConstIdentDecls=ConstIdentDecls;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConstSingleDecl!=null) ConstSingleDecl.accept(visitor);
        if(ConstIdentDecls!=null) ConstIdentDecls.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstSingleDecl!=null) ConstSingleDecl.traverseTopDown(visitor);
        if(ConstIdentDecls!=null) ConstIdentDecls.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstSingleDecl!=null) ConstSingleDecl.traverseBottomUp(visitor);
        if(ConstIdentDecls!=null) ConstIdentDecls.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstDeclMul(\n");

        if(ConstSingleDecl!=null)
            buffer.append(ConstSingleDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConstIdentDecls!=null)
            buffer.append(ConstIdentDecls.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstDeclMul]");
        return buffer.toString();
    }
}
