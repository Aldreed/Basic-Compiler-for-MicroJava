// generated with ast extension for cup
// version 0.8
// 18/7/2022 22:25:33


package rs.ac.bg.etf.pp1.ast;

public class SingleFormPars extends FormPars {

    private FormParsSingle FormParsSingle;
    private FormParsMultiple FormParsMultiple;
    private OptArgs OptArgs;

    public SingleFormPars (FormParsSingle FormParsSingle, FormParsMultiple FormParsMultiple, OptArgs OptArgs) {
        this.FormParsSingle=FormParsSingle;
        if(FormParsSingle!=null) FormParsSingle.setParent(this);
        this.FormParsMultiple=FormParsMultiple;
        if(FormParsMultiple!=null) FormParsMultiple.setParent(this);
        this.OptArgs=OptArgs;
        if(OptArgs!=null) OptArgs.setParent(this);
    }

    public FormParsSingle getFormParsSingle() {
        return FormParsSingle;
    }

    public void setFormParsSingle(FormParsSingle FormParsSingle) {
        this.FormParsSingle=FormParsSingle;
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

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(FormParsSingle!=null) FormParsSingle.accept(visitor);
        if(FormParsMultiple!=null) FormParsMultiple.accept(visitor);
        if(OptArgs!=null) OptArgs.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FormParsSingle!=null) FormParsSingle.traverseTopDown(visitor);
        if(FormParsMultiple!=null) FormParsMultiple.traverseTopDown(visitor);
        if(OptArgs!=null) OptArgs.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FormParsSingle!=null) FormParsSingle.traverseBottomUp(visitor);
        if(FormParsMultiple!=null) FormParsMultiple.traverseBottomUp(visitor);
        if(OptArgs!=null) OptArgs.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SingleFormPars(\n");

        if(FormParsSingle!=null)
            buffer.append(FormParsSingle.toString("  "+tab));
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

        buffer.append(tab);
        buffer.append(") [SingleFormPars]");
        return buffer.toString();
    }
}
