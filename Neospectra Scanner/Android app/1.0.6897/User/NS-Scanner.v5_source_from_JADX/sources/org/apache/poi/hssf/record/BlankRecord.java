package org.apache.poi.hssf.record;

import org.apache.poi.util.HexDump;
import org.apache.poi.util.LittleEndianOutput;

public final class BlankRecord extends StandardRecord implements CellValueRecordInterface {
    public static final short sid = 513;
    private int field_1_row;
    private short field_2_col;
    private short field_3_xf;

    public BlankRecord() {
    }

    public BlankRecord(RecordInputStream in) {
        this.field_1_row = in.readUShort();
        this.field_2_col = in.readShort();
        this.field_3_xf = in.readShort();
    }

    public void setRow(int row) {
        this.field_1_row = row;
    }

    public int getRow() {
        return this.field_1_row;
    }

    public short getColumn() {
        return this.field_2_col;
    }

    public void setXFIndex(short xf) {
        this.field_3_xf = xf;
    }

    public short getXFIndex() {
        return this.field_3_xf;
    }

    public void setColumn(short col) {
        this.field_2_col = col;
    }

    public short getSid() {
        return 513;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[BLANK]\n");
        sb.append("    row= ");
        sb.append(HexDump.shortToHex(getRow()));
        sb.append("\n");
        sb.append("    col= ");
        sb.append(HexDump.shortToHex(getColumn()));
        sb.append("\n");
        sb.append("    xf = ");
        sb.append(HexDump.shortToHex(getXFIndex()));
        sb.append("\n");
        sb.append("[/BLANK]\n");
        return sb.toString();
    }

    public void serialize(LittleEndianOutput out) {
        out.writeShort(getRow());
        out.writeShort(getColumn());
        out.writeShort(getXFIndex());
    }

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 6;
    }

    public Object clone() {
        BlankRecord rec = new BlankRecord();
        rec.field_1_row = this.field_1_row;
        rec.field_2_col = this.field_2_col;
        rec.field_3_xf = this.field_3_xf;
        return rec;
    }
}
