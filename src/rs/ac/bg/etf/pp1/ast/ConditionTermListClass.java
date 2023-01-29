// generated with ast extension for cup
// version 0.8
// 18/7/2022 22:25:33


package rs.ac.bg.etf.pp1.ast;

public class ConditionTermListClass extends ConditionTermList {

    private ConditionTermList ConditionTermList;
    private OrCheck OrCheck;
    private CondTerm CondTerm;

    public ConditionTermListClass (ConditionTermList ConditionTermList, OrCheck OrCheck, CondTerm CondTerm) {
        this.ConditionTermList=ConditionTermList;
        if(ConditionTermList!=null) ConditionTermList.setParent(this);
        this.OrCheck=OrCheck;
        if(OrCheck!=null) OrCheck.setParent(this);
        this.CondTerm=CondTerm;
        if(CondTerm!=null) CondTerm.setParent(this);
    }

    public ConditionTermList getConditionTermList() {
        return ConditionTermList;
    }

    public void setConditionTermList(ConditionTermList ConditionTermList) {
        this.ConditionTermList=ConditionTermList;
    }

    public OrCheck getOrCheck() {
        return OrCheck;
    }

    public void setOrCheck(OrCheck OrCheck) {
        this.OrCheck=OrCheck;
    }

    public CondTerm getCondTerm() {
        return CondTerm;
    }

    public void setCondTerm(CondTerm CondTerm) {
        this.CondTerm=CondTerm;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConditionTermList!=null) ConditionTermList.accept(visitor);
        if(OrCheck!=null) OrCheck.accept(visitor);
        if(CondTerm!=null) CondTerm.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConditionTermList!=null) ConditionTermList.traverseTopDown(visitor);
        if(OrCheck!=null) OrCheck.traverseTopDown(visitor);
        if(CondTerm!=null) CondTerm.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConditionTermList!=null) ConditionTermList.traverseBottomUp(visitor);
        if(OrCheck!=null) OrCheck.traverseBottomUp(visitor);
        if(CondTerm!=null) CondTerm.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConditionTermListClass(\n");

        if(ConditionTermList!=null)
            buffer.append(ConditionTermList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(OrCheck!=null)
            buffer.append(OrCheck.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(CondTerm!=null)
            buffer.append(CondTerm.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConditionTermListClass]");
        return buffer.toString();
    }
}
