// generated with ast extension for cup
// version 0.8
// 18/7/2022 22:25:33


package rs.ac.bg.etf.pp1.ast;

public class OptArgCHARCONST extends OptArg {

    private Type Type;
    private String optArgName;
    private Character C2;

    public OptArgCHARCONST (Type Type, String optArgName, Character C2) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.optArgName=optArgName;
        this.C2=C2;
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public String getOptArgName() {
        return optArgName;
    }

    public void setOptArgName(String optArgName) {
        this.optArgName=optArgName;
    }

    public Character getC2() {
        return C2;
    }

    public void setC2(Character C2) {
        this.C2=C2;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("OptArgCHARCONST(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+optArgName);
        buffer.append("\n");

        buffer.append(" "+tab+C2);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [OptArgCHARCONST]");
        return buffer.toString();
    }
}
