// generated with ast extension for cup
// version 0.8
// 18/7/2022 22:25:33


package rs.ac.bg.etf.pp1.ast;

public class ConstIdentDecalsBoolConst extends ConstIdentDecls {

    private ConstIdentDecls ConstIdentDecls;
    private String I2;
    private Boolean B3;

    public ConstIdentDecalsBoolConst (ConstIdentDecls ConstIdentDecls, String I2, Boolean B3) {
        this.ConstIdentDecls=ConstIdentDecls;
        if(ConstIdentDecls!=null) ConstIdentDecls.setParent(this);
        this.I2=I2;
        this.B3=B3;
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

    public Boolean getB3() {
        return B3;
    }

    public void setB3(Boolean B3) {
        this.B3=B3;
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
        buffer.append("ConstIdentDecalsBoolConst(\n");

        if(ConstIdentDecls!=null)
            buffer.append(ConstIdentDecls.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+I2);
        buffer.append("\n");

        buffer.append(" "+tab+B3);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstIdentDecalsBoolConst]");
        return buffer.toString();
    }
}
