// generated with ast extension for cup
// version 0.8
// 18/7/2022 22:25:33


package rs.ac.bg.etf.pp1.ast;

public class OptArgsMultiple extends OptArgsList {

    private OptArgsList OptArgsList;
    private OptArg OptArg;

    public OptArgsMultiple (OptArgsList OptArgsList, OptArg OptArg) {
        this.OptArgsList=OptArgsList;
        if(OptArgsList!=null) OptArgsList.setParent(this);
        this.OptArg=OptArg;
        if(OptArg!=null) OptArg.setParent(this);
    }

    public OptArgsList getOptArgsList() {
        return OptArgsList;
    }

    public void setOptArgsList(OptArgsList OptArgsList) {
        this.OptArgsList=OptArgsList;
    }

    public OptArg getOptArg() {
        return OptArg;
    }

    public void setOptArg(OptArg OptArg) {
        this.OptArg=OptArg;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(OptArgsList!=null) OptArgsList.accept(visitor);
        if(OptArg!=null) OptArg.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(OptArgsList!=null) OptArgsList.traverseTopDown(visitor);
        if(OptArg!=null) OptArg.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(OptArgsList!=null) OptArgsList.traverseBottomUp(visitor);
        if(OptArg!=null) OptArg.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("OptArgsMultiple(\n");

        if(OptArgsList!=null)
            buffer.append(OptArgsList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(OptArg!=null)
            buffer.append(OptArg.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [OptArgsMultiple]");
        return buffer.toString();
    }
}
