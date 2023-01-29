// generated with ast extension for cup
// version 0.8
// 18/7/2022 22:25:33


package rs.ac.bg.etf.pp1.ast;

public class ConditionMultiple extends Condition {

    private CondTerm CondTerm;
    private OrCheck OrCheck;
    private ConditionTermList ConditionTermList;
    private BracketCheck BracketCheck;

    public ConditionMultiple (CondTerm CondTerm, OrCheck OrCheck, ConditionTermList ConditionTermList, BracketCheck BracketCheck) {
        this.CondTerm=CondTerm;
        if(CondTerm!=null) CondTerm.setParent(this);
        this.OrCheck=OrCheck;
        if(OrCheck!=null) OrCheck.setParent(this);
        this.ConditionTermList=ConditionTermList;
        if(ConditionTermList!=null) ConditionTermList.setParent(this);
        this.BracketCheck=BracketCheck;
        if(BracketCheck!=null) BracketCheck.setParent(this);
    }

    public CondTerm getCondTerm() {
        return CondTerm;
    }

    public void setCondTerm(CondTerm CondTerm) {
        this.CondTerm=CondTerm;
    }

    public OrCheck getOrCheck() {
        return OrCheck;
    }

    public void setOrCheck(OrCheck OrCheck) {
        this.OrCheck=OrCheck;
    }

    public ConditionTermList getConditionTermList() {
        return ConditionTermList;
    }

    public void setConditionTermList(ConditionTermList ConditionTermList) {
        this.ConditionTermList=ConditionTermList;
    }

    public BracketCheck getBracketCheck() {
        return BracketCheck;
    }

    public void setBracketCheck(BracketCheck BracketCheck) {
        this.BracketCheck=BracketCheck;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(CondTerm!=null) CondTerm.accept(visitor);
        if(OrCheck!=null) OrCheck.accept(visitor);
        if(ConditionTermList!=null) ConditionTermList.accept(visitor);
        if(BracketCheck!=null) BracketCheck.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(CondTerm!=null) CondTerm.traverseTopDown(visitor);
        if(OrCheck!=null) OrCheck.traverseTopDown(visitor);
        if(ConditionTermList!=null) ConditionTermList.traverseTopDown(visitor);
        if(BracketCheck!=null) BracketCheck.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(CondTerm!=null) CondTerm.traverseBottomUp(visitor);
        if(OrCheck!=null) OrCheck.traverseBottomUp(visitor);
        if(ConditionTermList!=null) ConditionTermList.traverseBottomUp(visitor);
        if(BracketCheck!=null) BracketCheck.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConditionMultiple(\n");

        if(CondTerm!=null)
            buffer.append(CondTerm.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(OrCheck!=null)
            buffer.append(OrCheck.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ConditionTermList!=null)
            buffer.append(ConditionTermList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(BracketCheck!=null)
            buffer.append(BracketCheck.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConditionMultiple]");
        return buffer.toString();
    }
}
