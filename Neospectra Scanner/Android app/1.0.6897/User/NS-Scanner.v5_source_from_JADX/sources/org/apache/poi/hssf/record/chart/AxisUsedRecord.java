package org.apache.poi.hssf.record.chart;

import org.apache.poi.hssf.record.RecordInputStream;
import org.apache.poi.hssf.record.StandardRecord;
import org.apache.poi.util.HexDump;
import org.apache.poi.util.LittleEndianOutput;

public final class AxisUsedRecord extends StandardRecord {
    public static final short sid = 4166;
    private short field_1_numAxis;

    public AxisUsedRecord() {
    }

    public AxisUsedRecord(RecordInputStream in) {
        this.field_1_numAxis = in.readShort();
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[AXISUSED]\n");
        buffer.append("    .numAxis              = ");
        buffer.append("0x");
        buffer.append(HexDump.toHex(getNumAxis()));
        buffer.append(" (");
        buffer.append(getNumAxis());
        buffer.append(" )");
        buffer.append(System.getProperty("line.separator"));
        buffer.append("[/AXISUSED]\n");
        return buffer.toString();
    }

    public void serialize(LittleEndianOutput out) {
        out.writeShort(this.field_1_numAxis);
    }

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 2;
    }

    public short getSid() {
        return sid;
    }

    public Object clone() {
        AxisUsedRecord rec = new AxisUsedRecord();
        rec.field_1_numAxis = this.field_1_numAxis;
        return rec;
    }

    public short getNumAxis() {
        return this.field_1_numAxis;
    }

    public void setNumAxis(short field_1_numAxis2) {
        this.field_1_numAxis = field_1_numAxis2;
    }
}
