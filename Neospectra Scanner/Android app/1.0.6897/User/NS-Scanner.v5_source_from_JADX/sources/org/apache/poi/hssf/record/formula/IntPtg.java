package org.apache.poi.hssf.record.formula;

import org.apache.poi.util.LittleEndianInput;
import org.apache.poi.util.LittleEndianOutput;

public final class IntPtg extends ScalarConstantPtg {
    private static final int MAX_VALUE = 65535;
    private static final int MIN_VALUE = 0;
    public static final int SIZE = 3;
    public static final byte sid = 30;
    private final int field_1_value;

    public static boolean isInRange(int i) {
        return i >= 0 && i <= 65535;
    }

    public IntPtg(LittleEndianInput in) {
        this(in.readUShort());
    }

    public IntPtg(int value) {
        if (!isInRange(value)) {
            StringBuilder sb = new StringBuilder();
            sb.append("value is out of range: ");
            sb.append(value);
            throw new IllegalArgumentException(sb.toString());
        }
        this.field_1_value = value;
    }

    public int getValue() {
        return this.field_1_value;
    }

    public void write(LittleEndianOutput out) {
        out.writeByte(getPtgClass() + sid);
        out.writeShort(getValue());
    }

    public int getSize() {
        return 3;
    }

    public String toFormulaString() {
        return String.valueOf(getValue());
    }
}
