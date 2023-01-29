// generated with ast extension for cup
// version 0.8
// 18/7/2022 22:25:33


package rs.ac.bg.etf.pp1.ast;

public class VarIdentDeclsArr extends VarIdentDecls {

    private VarIdentDecls VarIdentDecls;
    private String varName;

    public VarIdentDeclsArr (VarIdentDecls VarIdentDecls, String varName) {
        this.VarIdentDecls=VarIdentDecls;
        if(VarIdentDecls!=null) VarIdentDecls.setParent(this);
        this.varName=varName;
    }

    public VarIdentDecls getVarIdentDecls() {
        return VarIdentDecls;
    }

    public void setVarIdentDecls(VarIdentDecls VarIdentDecls) {
        this.VarIdentDecls=VarIdentDecls;
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName=varName;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarIdentDecls!=null) VarIdentDecls.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarIdentDecls!=null) VarIdentDecls.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarIdentDecls!=null) VarIdentDecls.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VarIdentDeclsArr(\n");

        if(VarIdentDecls!=null)
            buffer.append(VarIdentDecls.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+varName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VarIdentDeclsArr]");
        return buffer.toString();
    }
}
