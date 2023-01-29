// generated with ast extension for cup
// version 0.8
// 18/7/2022 22:25:33


package rs.ac.bg.etf.pp1.ast;

public class TermMultiple extends Term {

    private TermStart TermStart;
    private Mulop Mulop;
    private TermFactorList TermFactorList;

    public TermMultiple (TermStart TermStart, Mulop Mulop, TermFactorList TermFactorList) {
        this.TermStart=TermStart;
        if(TermStart!=null) TermStart.setParent(this);
        this.Mulop=Mulop;
        if(Mulop!=null) Mulop.setParent(this);
        this.TermFactorList=TermFactorList;
        if(TermFactorList!=null) TermFactorList.setParent(this);
    }

    public TermStart getTermStart() {
        return TermStart;
    }

    public void setTermStart(TermStart TermStart) {
        this.TermStart=TermStart;
    }

    public Mulop getMulop() {
        return Mulop;
    }

    public void setMulop(Mulop Mulop) {
        this.Mulop=Mulop;
    }

    public TermFactorList getTermFactorList() {
        return TermFactorList;
    }

    public void setTermFactorList(TermFactorList TermFactorList) {
        this.TermFactorList=TermFactorList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(TermStart!=null) TermStart.accept(visitor);
        if(Mulop!=null) Mulop.accept(visitor);
        if(TermFactorList!=null) TermFactorList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(TermStart!=null) TermStart.traverseTopDown(visitor);
        if(Mulop!=null) Mulop.traverseTopDown(visitor);
        if(TermFactorList!=null) TermFactorList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(TermStart!=null) TermStart.traverseBottomUp(visitor);
        if(Mulop!=null) Mulop.traverseBottomUp(visitor);
        if(TermFactorList!=null) TermFactorList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("TermMultiple(\n");

        if(TermStart!=null)
            buffer.append(TermStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Mulop!=null)
            buffer.append(Mulop.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(TermFactorList!=null)
            buffer.append(TermFactorList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [TermMultiple]");
        return buffer.toString();
    }
}
