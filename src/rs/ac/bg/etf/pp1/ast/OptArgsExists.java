// generated with ast extension for cup
// version 0.8
// 18/7/2022 22:25:33


package rs.ac.bg.etf.pp1.ast;

public class OptArgsExists extends OptArgs {

    private OptArgsList OptArgsList;

    public OptArgsExists (OptArgsList OptArgsList) {
        this.OptArgsList=OptArgsList;
        if(OptArgsList!=null) OptArgsList.setParent(this);
    }

    public OptArgsList getOptArgsList() {
        return OptArgsList;
    }

    public void setOptArgsList(OptArgsList OptArgsList) {
        this.OptArgsList=OptArgsList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(OptArgsList!=null) OptArgsList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(OptArgsList!=null) OptArgsList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(OptArgsList!=null) OptArgsList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("OptArgsExists(\n");

        if(OptArgsList!=null)
            buffer.append(OptArgsList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [OptArgsExists]");
        return buffer.toString();
    }
}
