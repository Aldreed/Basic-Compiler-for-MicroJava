// generated with ast extension for cup
// version 0.8
// 18/7/2022 22:25:33


package rs.ac.bg.etf.pp1.ast;

public class ReturnSingleStatement extends SingleStatement {

    public ReturnSingleStatement () {
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
        buffer.append("ReturnSingleStatement(\n");

        buffer.append(tab);
        buffer.append(") [ReturnSingleStatement]");
        return buffer.toString();
    }
}
