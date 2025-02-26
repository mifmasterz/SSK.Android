package org.apache.poi.hssf.record;

import java.util.ArrayList;
import java.util.List;
import org.apache.poi.util.LittleEndianOutput;

public final class PaletteRecord extends StandardRecord {
    public static final short FIRST_COLOR_INDEX = 8;
    public static final byte STANDARD_PALETTE_SIZE = 56;
    public static final short sid = 146;
    private final List<PColor> _colors;

    private static final class PColor {
        public static final short ENCODED_SIZE = 4;
        private int _blue;
        private int _green;
        private int _red;

        public PColor(int red, int green, int blue) {
            this._red = red;
            this._green = green;
            this._blue = blue;
        }

        public byte[] getTriplet() {
            return new byte[]{(byte) this._red, (byte) this._green, (byte) this._blue};
        }

        public PColor(RecordInputStream in) {
            this._red = in.readByte();
            this._green = in.readByte();
            this._blue = in.readByte();
            in.readByte();
        }

        public void serialize(LittleEndianOutput out) {
            out.writeByte(this._red);
            out.writeByte(this._green);
            out.writeByte(this._blue);
            out.writeByte(0);
        }

        public String toString() {
            StringBuffer buffer = new StringBuffer();
            buffer.append("  red   = ");
            buffer.append(this._red & 255);
            buffer.append(10);
            buffer.append("  green = ");
            buffer.append(this._green & 255);
            buffer.append(10);
            buffer.append("  blue  = ");
            buffer.append(this._blue & 255);
            buffer.append(10);
            return buffer.toString();
        }
    }

    public PaletteRecord() {
        PColor[] defaultPalette = createDefaultPalette();
        this._colors = new ArrayList(defaultPalette.length);
        for (PColor add : defaultPalette) {
            this._colors.add(add);
        }
    }

    public PaletteRecord(RecordInputStream in) {
        int field_1_numcolors = in.readShort();
        this._colors = new ArrayList(field_1_numcolors);
        for (int k = 0; k < field_1_numcolors; k++) {
            this._colors.add(new PColor(in));
        }
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[PALETTE]\n");
        buffer.append("  numcolors     = ");
        buffer.append(this._colors.size());
        buffer.append(10);
        for (int i = 0; i < this._colors.size(); i++) {
            PColor c = (PColor) this._colors.get(i);
            buffer.append("* colornum      = ");
            buffer.append(i);
            buffer.append(10);
            buffer.append(c.toString());
            buffer.append("/*colornum      = ");
            buffer.append(i);
            buffer.append(10);
        }
        buffer.append("[/PALETTE]\n");
        return buffer.toString();
    }

    public void serialize(LittleEndianOutput out) {
        out.writeShort(this._colors.size());
        for (int i = 0; i < this._colors.size(); i++) {
            ((PColor) this._colors.get(i)).serialize(out);
        }
    }

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return (this._colors.size() * 4) + 2;
    }

    public short getSid() {
        return 146;
    }

    public byte[] getColor(int byteIndex) {
        int i = byteIndex - 8;
        if (i < 0 || i >= this._colors.size()) {
            return null;
        }
        return ((PColor) this._colors.get(i)).getTriplet();
    }

    public void setColor(short byteIndex, byte red, byte green, byte blue) {
        int i = byteIndex - 8;
        if (i >= 0 && i < 56) {
            while (this._colors.size() <= i) {
                this._colors.add(new PColor(0, 0, 0));
            }
            this._colors.set(i, new PColor(red, green, blue));
        }
    }

    private static PColor[] createDefaultPalette() {
        return new PColor[]{m65pc(0, 0, 0), m65pc(255, 255, 255), m65pc(255, 0, 0), m65pc(0, 255, 0), m65pc(0, 0, 255), m65pc(255, 255, 0), m65pc(255, 0, 255), m65pc(0, 255, 255), m65pc(128, 0, 0), m65pc(0, 128, 0), m65pc(0, 0, 128), m65pc(128, 128, 0), m65pc(128, 0, 128), m65pc(0, 128, 128), m65pc(192, 192, 192), m65pc(128, 128, 128), m65pc(153, 153, 255), m65pc(153, 51, 102), m65pc(255, 255, 204), m65pc(204, 255, 255), m65pc(102, 0, 102), m65pc(255, 128, 128), m65pc(0, 102, 204), m65pc(204, 204, 255), m65pc(0, 0, 128), m65pc(255, 0, 255), m65pc(255, 255, 0), m65pc(0, 255, 255), m65pc(128, 0, 128), m65pc(128, 0, 0), m65pc(0, 128, 128), m65pc(0, 0, 255), m65pc(0, 204, 255), m65pc(204, 255, 255), m65pc(204, 255, 204), m65pc(255, 255, 153), m65pc(153, 204, 255), m65pc(255, 153, 204), m65pc(204, 153, 255), m65pc(255, 204, 153), m65pc(51, 102, 255), m65pc(51, 204, 204), m65pc(153, 204, 0), m65pc(255, 204, 0), m65pc(255, 153, 0), m65pc(255, 102, 0), m65pc(102, 102, 153), m65pc(150, 150, 150), m65pc(0, 51, 102), m65pc(51, 153, 102), m65pc(0, 51, 0), m65pc(51, 51, 0), m65pc(153, 51, 0), m65pc(153, 51, 102), m65pc(51, 51, 153), m65pc(51, 51, 51)};
    }

    /* renamed from: pc */
    private static PColor m65pc(int r, int g, int b) {
        return new PColor(r, g, b);
    }
}
