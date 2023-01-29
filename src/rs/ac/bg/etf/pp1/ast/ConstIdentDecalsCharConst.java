// generated with ast extension for cup
// version 0.8
// 18/7/2022 22:25:33


package rs.ac.bg.etf.pp1.ast;

public class ConstIdentDecalsCharConst extends ConstIdentDecls {

    private ConstIdentDecls ConstIdentDecls;
    private String I2;
    private Character C3;

    public ConstIdentDecalsCharConst (ConstIdentDecls ConstIdentDecls, String I2, Character C3) {
        this.ConstIdentDecls=ConstIdentDecls;
        if(ConstIdentDecls!=null) ConstIdentDecls.setParent(this);
        this.I2=I2;
        this.C3=C3;
    }

    public ConstIdentDecls getConstIdentDecls() {
        return ConstIdentDecls;
    }

    public void setConstIdentDecls(ConstIdentDecls ConstIdentDecls) {
        this.ConstIdentDecls=ConstIdentDecls;
    }

    public String getI2() {
        return I2;
    }

    public void setI2(String I2) {
        this.I2=I2;
    }

    public Character getC3() {
        return C3;
    }

    public void setC3(Character C3) {
        this.C3=C3;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ConstIdentDecls!=null) ConstIdentDecls.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ConstIdentDecls!=null) ConstIdentDecls.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ConstIdentDecls!=null) ConstIdentDecls.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ConstIdentDecalsCharConst(\n");

        if(ConstIdentDecls!=null)
            buffer.append(ConstIdentDecls.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+I2);
        buffer.append("\n");

        buffer.append(" "+tab+C3);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstIdentDecalsCharConst]");
        return buffer.toString();
    }
}
