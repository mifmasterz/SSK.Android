package org.apache.poi.hssf.record;

import org.apache.poi.util.LittleEndianInput;
import org.apache.poi.util.LittleEndianOutput;

public final class EndSubRecord extends SubRecord {
    private static final int ENCODED_SIZE = 0;
    public static final short sid = 0;

    public EndSubRecord() {
    }

    public EndSubRecord(LittleEndianInput in, int size) {
        if ((size & 255) != 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("Unexpected size (");
            sb.append(size);
            sb.append(")");
            throw new RecordFormatException(sb.toString());
        }
    }

    public boolean isTerminating() {
        return true;
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[ftEnd]\n");
        buffer.append("[/ftEnd]\n");
        return buffer.toString();
    }

    public void serialize(LittleEndianOutput out) {
        out.writeShort(0);
        out.writeShort(0);
    }

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 0;
    }

    public short getSid() {
        return 0;
    }

    public Object clone() {
        return new EndSubRecord();
    }
}
