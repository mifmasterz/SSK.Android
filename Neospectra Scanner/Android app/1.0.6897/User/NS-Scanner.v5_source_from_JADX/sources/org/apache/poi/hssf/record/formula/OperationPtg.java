package org.apache.poi.hssf.record.formula;

public abstract class OperationPtg extends Ptg {
    public static final int TYPE_BINARY = 1;
    public static final int TYPE_FUNCTION = 2;
    public static final int TYPE_UNARY = 0;

    public abstract int getNumberOfOperands();

    public abstract String toFormulaString(String[] strArr);

    public byte getDefaultOperandClass() {
        return 32;
    }
}
