// generated with ast extension for cup
// version 0.8
// 18/7/2022 22:25:33


package rs.ac.bg.etf.pp1.ast;

public class VariableDecl extends VarDecl {

    private VName vName;
    private VarIdentDecls VarIdentDecls;

    public VariableDecl (VName vName, VarIdentDecls VarIdentDecls) {
        this.vName=vName;
        if(vName!=null) vName.setParent(this);
        this.VarIdentDecls=VarIdentDecls;
        if(VarIdentDecls!=null) VarIdentDecls.setParent(this);
    }

    public VName getVName() {
        return vName;
    }

    public void setVName(VName vName) {
        this.vName=vName;
    }

    public VarIdentDecls getVarIdentDecls() {
        return VarIdentDecls;
    }

    public void setVarIdentDecls(VarIdentDecls VarIdentDecls) {
        this.VarIdentDecls=VarIdentDecls;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(vName!=null) vName.accept(visitor);
        if(VarIdentDecls!=null) VarIdentDecls.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(vName!=null) vName.traverseTopDown(visitor);
        if(VarIdentDecls!=null) VarIdentDecls.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(vName!=null) vName.traverseBottomUp(visitor);
        if(VarIdentDecls!=null) VarIdentDecls.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VariableDecl(\n");

        if(vName!=null)
            buffer.append(vName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarIdentDecls!=null)
            buffer.append(VarIdentDecls.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VariableDecl]");
        return buffer.toString();
    }
}
