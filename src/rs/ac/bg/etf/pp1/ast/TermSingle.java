// generated with ast extension for cup
// version 0.8
// 18/7/2022 22:25:33


package rs.ac.bg.etf.pp1.ast;

public class TermSingle extends Term {

    private TermStart TermStart;

    public TermSingle (TermStart TermStart) {
        this.TermStart=TermStart;
        if(TermStart!=null) TermStart.setParent(this);
    }

    public TermStart getTermStart() {
        return TermStart;
    }

    public void setTermStart(TermStart TermStart) {
        this.TermStart=TermStart;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(TermStart!=null) TermStart.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(TermStart!=null) TermStart.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(TermStart!=null) TermStart.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("TermSingle(\n");

        if(TermStart!=null)
            buffer.append(TermStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [TermSingle]");
        return buffer.toString();
    }
}
