package org.apache.poi.hssf.record.chart;

import org.apache.poi.hssf.record.RecordInputStream;
import org.apache.poi.hssf.record.StandardRecord;
import org.apache.poi.util.BitField;
import org.apache.poi.util.BitFieldFactory;
import org.apache.poi.util.HexDump;
import org.apache.poi.util.LittleEndianOutput;

public final class TextRecord extends StandardRecord {
    public static final short DATA_LABEL_PLACEMENT_ABOVE = 5;
    public static final short DATA_LABEL_PLACEMENT_AUTO = 9;
    public static final short DATA_LABEL_PLACEMENT_AXIS = 4;
    public static final short DATA_LABEL_PLACEMENT_BELOW = 6;
    public static final short DATA_LABEL_PLACEMENT_CENTER = 3;
    public static final short DATA_LABEL_PLACEMENT_CHART_DEPENDENT = 0;
    public static final short DATA_LABEL_PLACEMENT_INSIDE = 2;
    public static final short DATA_LABEL_PLACEMENT_LEFT = 7;
    public static final short DATA_LABEL_PLACEMENT_OUTSIDE = 1;
    public static final short DATA_LABEL_PLACEMENT_RIGHT = 8;
    public static final short DATA_LABEL_PLACEMENT_USER_MOVED = 10;
    public static final short DISPLAY_MODE_OPAQUE = 2;
    public static final short DISPLAY_MODE_TRANSPARENT = 1;
    public static final byte HORIZONTAL_ALIGNMENT_BOTTOM = 3;
    public static final byte HORIZONTAL_ALIGNMENT_CENTER = 2;
    public static final byte HORIZONTAL_ALIGNMENT_JUSTIFY = 4;
    public static final byte HORIZONTAL_ALIGNMENT_LEFT = 1;
    public static final short ROTATION_NONE = 0;
    public static final short ROTATION_ROTATED_90_DEGREES = 2;
    public static final short ROTATION_ROTATED_90_DEGREES_CLOCKWISE = 3;
    public static final short ROTATION_TOP_TO_BOTTOM = 1;
    public static final byte VERTICAL_ALIGNMENT_BOTTOM = 3;
    public static final byte VERTICAL_ALIGNMENT_CENTER = 2;
    public static final byte VERTICAL_ALIGNMENT_JUSTIFY = 4;
    public static final byte VERTICAL_ALIGNMENT_TOP = 1;
    private static final BitField autoBackground = BitFieldFactory.getInstance(128);
    private static final BitField autoColor = BitFieldFactory.getInstance(1);
    private static final BitField autoGeneratedText = BitFieldFactory.getInstance(16);
    private static final BitField autoLabelDeleted = BitFieldFactory.getInstance(64);
    private static final BitField dataLabelPlacement = BitFieldFactory.getInstance(15);
    private static final BitField generated = BitFieldFactory.getInstance(32);
    private static final BitField rotation = BitFieldFactory.getInstance(1792);
    private static final BitField showBubbleSizes = BitFieldFactory.getInstance(8192);
    private static final BitField showCategoryLabelAsPercentage = BitFieldFactory.getInstance(2048);
    private static final BitField showKey = BitFieldFactory.getInstance(2);
    private static final BitField showLabel = BitFieldFactory.getInstance(16384);
    private static final BitField showValue = BitFieldFactory.getInstance(4);
    private static final BitField showValueAsPercentage = BitFieldFactory.getInstance(4096);
    public static final short sid = 4133;
    private static final BitField vertical = BitFieldFactory.getInstance(8);
    private short field_10_indexOfColorValue;
    private short field_11_options2;
    private short field_12_textRotation;
    private byte field_1_horizontalAlignment;
    private byte field_2_verticalAlignment;
    private short field_3_displayMode;
    private int field_4_rgbColor;
    private int field_5_x;
    private int field_6_y;
    private int field_7_width;
    private int field_8_height;
    private short field_9_options1;

    public TextRecord() {
    }

    public TextRecord(RecordInputStream in) {
        this.field_1_horizontalAlignment = in.readByte();
        this.field_2_verticalAlignment = in.readByte();
        this.field_3_displayMode = in.readShort();
        this.field_4_rgbColor = in.readInt();
        this.field_5_x = in.readInt();
        this.field_6_y = in.readInt();
        this.field_7_width = in.readInt();
        this.field_8_height = in.readInt();
        this.field_9_options1 = in.readShort();
        this.field_10_indexOfColorValue = in.readShort();
        this.field_11_options2 = in.readShort();
        this.field_12_textRotation = in.readShort();
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("[TEXT]\n");
        buffer.append("    .horizontalAlignment  = ");
        buffer.append("0x");
        buffer.append(HexDump.toHex(getHorizontalAlignment()));
        buffer.append(" (");
        buffer.append(getHorizontalAlignment());
        buffer.append(" )");
        buffer.append(System.getProperty("line.separator"));
        buffer.append("    .verticalAlignment    = ");
        buffer.append("0x");
        buffer.append(HexDump.toHex(getVerticalAlignment()));
        buffer.append(" (");
        buffer.append(getVerticalAlignment());
        buffer.append(" )");
        buffer.append(System.getProperty("line.separator"));
        buffer.append("    .displayMode          = ");
        buffer.append("0x");
        buffer.append(HexDump.toHex(getDisplayMode()));
        buffer.append(" (");
        buffer.append(getDisplayMode());
        buffer.append(" )");
        buffer.append(System.getProperty("line.separator"));
        buffer.append("    .rgbColor             = ");
        buffer.append("0x");
        buffer.append(HexDump.toHex(getRgbColor()));
        buffer.append(" (");
        buffer.append(getRgbColor());
        buffer.append(" )");
        buffer.append(System.getProperty("line.separator"));
        buffer.append("    .x                    = ");
        buffer.append("0x");
        buffer.append(HexDump.toHex(getX()));
        buffer.append(" (");
        buffer.append(getX());
        buffer.append(" )");
        buffer.append(System.getProperty("line.separator"));
        buffer.append("    .y                    = ");
        buffer.append("0x");
        buffer.append(HexDump.toHex(getY()));
        buffer.append(" (");
        buffer.append(getY());
        buffer.append(" )");
        buffer.append(System.getProperty("line.separator"));
        buffer.append("    .width                = ");
        buffer.append("0x");
        buffer.append(HexDump.toHex(getWidth()));
        buffer.append(" (");
        buffer.append(getWidth());
        buffer.append(" )");
        buffer.append(System.getProperty("line.separator"));
        buffer.append("    .height               = ");
        buffer.append("0x");
        buffer.append(HexDump.toHex(getHeight()));
        buffer.append(" (");
        buffer.append(getHeight());
        buffer.append(" )");
        buffer.append(System.getProperty("line.separator"));
        buffer.append("    .options1             = ");
        buffer.append("0x");
        buffer.append(HexDump.toHex(getOptions1()));
        buffer.append(" (");
        buffer.append(getOptions1());
        buffer.append(" )");
        buffer.append(System.getProperty("line.separator"));
        buffer.append("         .autoColor                = ");
        buffer.append(isAutoColor());
        buffer.append(10);
        buffer.append("         .showKey                  = ");
        buffer.append(isShowKey());
        buffer.append(10);
        buffer.append("         .showValue                = ");
        buffer.append(isShowValue());
        buffer.append(10);
        buffer.append("         .vertical                 = ");
        buffer.append(isVertical());
        buffer.append(10);
        buffer.append("         .autoGeneratedText        = ");
        buffer.append(isAutoGeneratedText());
        buffer.append(10);
        buffer.append("         .generated                = ");
        buffer.append(isGenerated());
        buffer.append(10);
        buffer.append("         .autoLabelDeleted         = ");
        buffer.append(isAutoLabelDeleted());
        buffer.append(10);
        buffer.append("         .autoBackground           = ");
        buffer.append(isAutoBackground());
        buffer.append(10);
        buffer.append("         .rotation                 = ");
        buffer.append(getRotation());
        buffer.append(10);
        buffer.append("         .showCategoryLabelAsPercentage     = ");
        buffer.append(isShowCategoryLabelAsPercentage());
        buffer.append(10);
        buffer.append("         .showValueAsPercentage     = ");
        buffer.append(isShowValueAsPercentage());
        buffer.append(10);
        buffer.append("         .showBubbleSizes          = ");
        buffer.append(isShowBubbleSizes());
        buffer.append(10);
        buffer.append("         .showLabel                = ");
        buffer.append(isShowLabel());
        buffer.append(10);
        buffer.append("    .indexOfColorValue    = ");
        buffer.append("0x");
        buffer.append(HexDump.toHex(getIndexOfColorValue()));
        buffer.append(" (");
        buffer.append(getIndexOfColorValue());
        buffer.append(" )");
        buffer.append(System.getProperty("line.separator"));
        buffer.append("    .options2             = ");
        buffer.append("0x");
        buffer.append(HexDump.toHex(getOptions2()));
        buffer.append(" (");
        buffer.append(getOptions2());
        buffer.append(" )");
        buffer.append(System.getProperty("line.separator"));
        buffer.append("         .dataLabelPlacement       = ");
        buffer.append(getDataLabelPlacement());
        buffer.append(10);
        buffer.append("    .textRotation         = ");
        buffer.append("0x");
        buffer.append(HexDump.toHex(getTextRotation()));
        buffer.append(" (");
        buffer.append(getTextRotation());
        buffer.append(" )");
        buffer.append(System.getProperty("line.separator"));
        buffer.append("[/TEXT]\n");
        return buffer.toString();
    }

    public void serialize(LittleEndianOutput out) {
        out.writeByte(this.field_1_horizontalAlignment);
        out.writeByte(this.field_2_verticalAlignment);
        out.writeShort(this.field_3_displayMode);
        out.writeInt(this.field_4_rgbColor);
        out.writeInt(this.field_5_x);
        out.writeInt(this.field_6_y);
        out.writeInt(this.field_7_width);
        out.writeInt(this.field_8_height);
        out.writeShort(this.field_9_options1);
        out.writeShort(this.field_10_indexOfColorValue);
        out.writeShort(this.field_11_options2);
        out.writeShort(this.field_12_textRotation);
    }

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return 32;
    }

    public short getSid() {
        return sid;
    }

    public Object clone() {
        TextRecord rec = new TextRecord();
        rec.field_1_horizontalAlignment = this.field_1_horizontalAlignment;
        rec.field_2_verticalAlignment = this.field_2_verticalAlignment;
        rec.field_3_displayMode = this.field_3_displayMode;
        rec.field_4_rgbColor = this.field_4_rgbColor;
        rec.field_5_x = this.field_5_x;
        rec.field_6_y = this.field_6_y;
        rec.field_7_width = this.field_7_width;
        rec.field_8_height = this.field_8_height;
        rec.field_9_options1 = this.field_9_options1;
        rec.field_10_indexOfColorValue = this.field_10_indexOfColorValue;
        rec.field_11_options2 = this.field_11_options2;
        rec.field_12_textRotation = this.field_12_textRotation;
        return rec;
    }

    public byte getHorizontalAlignment() {
        return this.field_1_horizontalAlignment;
    }

    public void setHorizontalAlignment(byte field_1_horizontalAlignment2) {
        this.field_1_horizontalAlignment = field_1_horizontalAlignment2;
    }

    public byte getVerticalAlignment() {
        return this.field_2_verticalAlignment;
    }

    public void setVerticalAlignment(byte field_2_verticalAlignment2) {
        this.field_2_verticalAlignment = field_2_verticalAlignment2;
    }

    public short getDisplayMode() {
        return this.field_3_displayMode;
    }

    public void setDisplayMode(short field_3_displayMode2) {
        this.field_3_displayMode = field_3_displayMode2;
    }

    public int getRgbColor() {
        return this.field_4_rgbColor;
    }

    public void setRgbColor(int field_4_rgbColor2) {
        this.field_4_rgbColor = field_4_rgbColor2;
    }

    public int getX() {
        return this.field_5_x;
    }

    public void setX(int field_5_x2) {
        this.field_5_x = field_5_x2;
    }

    public int getY() {
        return this.field_6_y;
    }

    public void setY(int field_6_y2) {
        this.field_6_y = field_6_y2;
    }

    public int getWidth() {
        return this.field_7_width;
    }

    public void setWidth(int field_7_width2) {
        this.field_7_width = field_7_width2;
    }

    public int getHeight() {
        return this.field_8_height;
    }

    public void setHeight(int field_8_height2) {
        this.field_8_height = field_8_height2;
    }

    public short getOptions1() {
        return this.field_9_options1;
    }

    public void setOptions1(short field_9_options12) {
        this.field_9_options1 = field_9_options12;
    }

    public short getIndexOfColorValue() {
        return this.field_10_indexOfColorValue;
    }

    public void setIndexOfColorValue(short field_10_indexOfColorValue2) {
        this.field_10_indexOfColorValue = field_10_indexOfColorValue2;
    }

    public short getOptions2() {
        return this.field_11_options2;
    }

    public void setOptions2(short field_11_options22) {
        this.field_11_options2 = field_11_options22;
    }

    public short getTextRotation() {
        return this.field_12_textRotation;
    }

    public void setTextRotation(short field_12_textRotation2) {
        this.field_12_textRotation = field_12_textRotation2;
    }

    public void setAutoColor(boolean value) {
        this.field_9_options1 = autoColor.setShortBoolean(this.field_9_options1, value);
    }

    public boolean isAutoColor() {
        return autoColor.isSet(this.field_9_options1);
    }

    public void setShowKey(boolean value) {
        this.field_9_options1 = showKey.setShortBoolean(this.field_9_options1, value);
    }

    public boolean isShowKey() {
        return showKey.isSet(this.field_9_options1);
    }

    public void setShowValue(boolean value) {
        this.field_9_options1 = showValue.setShortBoolean(this.field_9_options1, value);
    }

    public boolean isShowValue() {
        return showValue.isSet(this.field_9_options1);
    }

    public void setVertical(boolean value) {
        this.field_9_options1 = vertical.setShortBoolean(this.field_9_options1, value);
    }

    public boolean isVertical() {
        return vertical.isSet(this.field_9_options1);
    }

    public void setAutoGeneratedText(boolean value) {
        this.field_9_options1 = autoGeneratedText.setShortBoolean(this.field_9_options1, value);
    }

    public boolean isAutoGeneratedText() {
        return autoGeneratedText.isSet(this.field_9_options1);
    }

    public void setGenerated(boolean value) {
        this.field_9_options1 = generated.setShortBoolean(this.field_9_options1, value);
    }

    public boolean isGenerated() {
        return generated.isSet(this.field_9_options1);
    }

    public void setAutoLabelDeleted(boolean value) {
        this.field_9_options1 = autoLabelDeleted.setShortBoolean(this.field_9_options1, value);
    }

    public boolean isAutoLabelDeleted() {
        return autoLabelDeleted.isSet(this.field_9_options1);
    }

    public void setAutoBackground(boolean value) {
        this.field_9_options1 = autoBackground.setShortBoolean(this.field_9_options1, value);
    }

    public boolean isAutoBackground() {
        return autoBackground.isSet(this.field_9_options1);
    }

    public void setRotation(short value) {
        this.field_9_options1 = rotation.setShortValue(this.field_9_options1, value);
    }

    public short getRotation() {
        return rotation.getShortValue(this.field_9_options1);
    }

    public void setShowCategoryLabelAsPercentage(boolean value) {
        this.field_9_options1 = showCategoryLabelAsPercentage.setShortBoolean(this.field_9_options1, value);
    }

    public boolean isShowCategoryLabelAsPercentage() {
        return showCategoryLabelAsPercentage.isSet(this.field_9_options1);
    }

    public void setShowValueAsPercentage(boolean value) {
        this.field_9_options1 = showValueAsPercentage.setShortBoolean(this.field_9_options1, value);
    }

    public boolean isShowValueAsPercentage() {
        return showValueAsPercentage.isSet(this.field_9_options1);
    }

    public void setShowBubbleSizes(boolean value) {
        this.field_9_options1 = showBubbleSizes.setShortBoolean(this.field_9_options1, value);
    }

    public boolean isShowBubbleSizes() {
        return showBubbleSizes.isSet(this.field_9_options1);
    }

    public void setShowLabel(boolean value) {
        this.field_9_options1 = showLabel.setShortBoolean(this.field_9_options1, value);
    }

    public boolean isShowLabel() {
        return showLabel.isSet(this.field_9_options1);
    }

    public void setDataLabelPlacement(short value) {
        this.field_11_options2 = dataLabelPlacement.setShortValue(this.field_11_options2, value);
    }

    public short getDataLabelPlacement() {
        return dataLabelPlacement.getShortValue(this.field_11_options2);
    }
}
