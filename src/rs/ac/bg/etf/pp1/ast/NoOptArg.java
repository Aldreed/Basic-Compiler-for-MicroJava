// generated with ast extension for cup
// version 0.8
// 18/7/2022 22:25:33


package rs.ac.bg.etf.pp1.ast;

public class NoOptArg extends OptArgs {

    public NoOptArg () {
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("NoOptArg(\n");

        buffer.append(tab);
        buffer.append(") [NoOptArg]");
        return buffer.toString();
    }
}
