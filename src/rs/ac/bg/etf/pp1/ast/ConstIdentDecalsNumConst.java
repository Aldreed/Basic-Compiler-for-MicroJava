// generated with ast extension for cup
// version 0.8
// 18/7/2022 22:25:33


package rs.ac.bg.etf.pp1.ast;

public class ConstIdentDecalsNumConst extends ConstIdentDecls {

    private ConstIdentDecls ConstIdentDecls;
    private String I2;
    private Integer N3;

    public ConstIdentDecalsNumConst (ConstIdentDecls ConstIdentDecls, String I2, Integer N3) {
        this.ConstIdentDecls=ConstIdentDecls;
        if(ConstIdentDecls!=null) ConstIdentDecls.setParent(this);
        this.I2=I2;
        this.N3=N3;
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

    public Integer getN3() {
        return N3;
    }

    public void setN3(Integer N3) {
        this.N3=N3;
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
        buffer.append("ConstIdentDecalsNumConst(\n");

        if(ConstIdentDecls!=null)
            buffer.append(ConstIdentDecls.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+I2);
        buffer.append("\n");

        buffer.append(" "+tab+N3);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ConstIdentDecalsNumConst]");
        return buffer.toString();
    }
}
