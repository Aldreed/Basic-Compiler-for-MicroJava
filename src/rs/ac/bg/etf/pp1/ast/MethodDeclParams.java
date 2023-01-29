// generated with ast extension for cup
// version 0.8
// 18/7/2022 22:25:33


package rs.ac.bg.etf.pp1.ast;

public class MethodDeclParams extends MethodDecl {

    private MStart MStart;
    private FormParsMultiple FormParsMultiple;
    private OptArgs OptArgs;
    private VarDeclList VarDeclList;
    private StatementList StatementList;

    public MethodDeclParams (MStart MStart, FormParsMultiple FormParsMultiple, OptArgs OptArgs, VarDeclList VarDeclList, StatementList StatementList) {
        this.MStart=MStart;
        if(MStart!=null) MStart.setParent(this);
        this.FormParsMultiple=FormParsMultiple;
        if(FormParsMultiple!=null) FormParsMultiple.setParent(this);
        this.OptArgs=OptArgs;
        if(OptArgs!=null) OptArgs.setParent(this);
        this.VarDeclList=VarDeclList;
        if(VarDeclList!=null) VarDeclList.setParent(this);
        this.StatementList=StatementList;
        if(StatementList!=null) StatementList.setParent(this);
    }

    public MStart getMStart() {
        return MStart;
    }

    public void setMStart(MStart MStart) {
        this.MStart=MStart;
    }

    public FormParsMultiple getFormParsMultiple() {
        return FormParsMultiple;
    }

    public void setFormParsMultiple(FormParsMultiple FormParsMultiple) {
        this.FormParsMultiple=FormParsMultiple;
    }

    public OptArgs getOptArgs() {
        return OptArgs;
    }

    public void setOptArgs(OptArgs OptArgs) {
        this.OptArgs=OptArgs;
    }

    public VarDeclList getVarDeclList() {
        return VarDeclList;
    }

    public void setVarDeclList(VarDeclList VarDeclList) {
        this.VarDeclList=VarDeclList;
    }

    public StatementList getStatementList() {
        return StatementList;
    }

    public void setStatementList(StatementList StatementList) {
        this.StatementList=StatementList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MStart!=null) MStart.accept(visitor);
        if(FormParsMultiple!=null) FormParsMultiple.accept(visitor);
        if(OptArgs!=null) OptArgs.accept(visitor);
        if(VarDeclList!=null) VarDeclList.accept(visitor);
        if(StatementList!=null) StatementList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MStart!=null) MStart.traverseTopDown(visitor);
        if(FormParsMultiple!=null) FormParsMultiple.traverseTopDown(visitor);
        if(OptArgs!=null) OptArgs.traverseTopDown(visitor);
        if(VarDeclList!=null) VarDeclList.traverseTopDown(visitor);
        if(StatementList!=null) StatementList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MStart!=null) MStart.traverseBottomUp(visitor);
        if(FormParsMultiple!=null) FormParsMultiple.traverseBottomUp(visitor);
        if(OptArgs!=null) OptArgs.traverseBottomUp(visitor);
        if(VarDeclList!=null) VarDeclList.traverseBottomUp(visitor);
        if(StatementList!=null) StatementList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodDeclParams(\n");

        if(MStart!=null)
            buffer.append(MStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FormParsMultiple!=null)
            buffer.append(FormParsMultiple.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(OptArgs!=null)
            buffer.append(OptArgs.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarDeclList!=null)
            buffer.append(VarDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(StatementList!=null)
            buffer.append(StatementList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodDeclParams]");
        return buffer.toString();
    }
}
