package org.apache.poi.hssf.record;

import org.apache.poi.hssf.record.formula.Area3DPtg;
import org.apache.poi.hssf.record.formula.Ptg;
import org.apache.poi.hssf.record.formula.Ref3DPtg;
import org.apache.poi.p009ss.formula.Formula;
import org.apache.poi.util.HexDump;
import org.apache.poi.util.LittleEndianInput;
import org.apache.poi.util.LittleEndianOutput;
import org.apache.poi.util.StringUtil;

public final class NameRecord extends StandardRecord {
    public static final byte BUILTIN_AUTO_ACTIVATE = 10;
    public static final byte BUILTIN_AUTO_CLOSE = 3;
    public static final byte BUILTIN_AUTO_DEACTIVATE = 11;
    public static final byte BUILTIN_AUTO_OPEN = 2;
    public static final byte BUILTIN_CONSOLIDATE_AREA = 1;
    public static final byte BUILTIN_CRITERIA = 5;
    public static final byte BUILTIN_DATABASE = 4;
    public static final byte BUILTIN_DATA_FORM = 9;
    public static final byte BUILTIN_FILTER_DB = 13;
    public static final byte BUILTIN_PRINT_AREA = 6;
    public static final byte BUILTIN_PRINT_TITLE = 7;
    public static final byte BUILTIN_RECORDER = 8;
    public static final byte BUILTIN_SHEET_TITLE = 12;
    public static final short sid = 24;
    private boolean field_11_nameIsMultibyte;
    private byte field_12_built_in_code;
    private String field_12_name_text;
    private Formula field_13_name_definition;
    private String field_14_custom_menu_text;
    private String field_15_description_text;
    private String field_16_help_topic_text;
    private String field_17_status_bar_text;
    private short field_1_option_flag;
    private byte field_2_keyboard_shortcut;
    private short field_5_externSheetIndex_plus1;
    private int field_6_sheetNumber;

    private static final class Option {
        public static final int OPT_BINDATA = 4096;
        public static final int OPT_BUILTIN = 32;
        public static final int OPT_COMMAND_NAME = 4;
        public static final int OPT_COMPLEX = 16;
        public static final int OPT_FUNCTION_NAME = 2;
        public static final int OPT_HIDDEN_NAME = 1;
        public static final int OPT_MACRO = 8;

        private Option() {
        }

        public static final boolean isFormula(int optValue) {
            return (optValue & 15) == 0;
        }
    }

    public NameRecord() {
        this.field_13_name_definition = Formula.create(Ptg.EMPTY_PTG_ARRAY);
        this.field_12_name_text = "";
        this.field_14_custom_menu_text = "";
        this.field_15_description_text = "";
        this.field_16_help_topic_text = "";
        this.field_17_status_bar_text = "";
    }

    public NameRecord(byte builtin, int sheetNumber) {
        this();
        this.field_12_built_in_code = builtin;
        setOptionFlag((short) (this.field_1_option_flag | 32));
        this.field_6_sheetNumber = sheetNumber;
    }

    public void setOptionFlag(short flag) {
        this.field_1_option_flag = flag;
    }

    public void setKeyboardShortcut(byte shortcut) {
        this.field_2_keyboard_shortcut = shortcut;
    }

    public int getSheetNumber() {
        return this.field_6_sheetNumber;
    }

    public byte getFnGroup() {
        return (byte) ((this.field_1_option_flag & 4032) >> 4);
    }

    public void setSheetNumber(int value) {
        this.field_6_sheetNumber = value;
    }

    public void setNameText(String name) {
        this.field_12_name_text = name;
        this.field_11_nameIsMultibyte = StringUtil.hasMultibyte(name);
    }

    public void setCustomMenuText(String text) {
        this.field_14_custom_menu_text = text;
    }

    public void setDescriptionText(String text) {
        this.field_15_description_text = text;
    }

    public void setHelpTopicText(String text) {
        this.field_16_help_topic_text = text;
    }

    public void setStatusBarText(String text) {
        this.field_17_status_bar_text = text;
    }

    public short getOptionFlag() {
        return this.field_1_option_flag;
    }

    public byte getKeyboardShortcut() {
        return this.field_2_keyboard_shortcut;
    }

    private int getNameTextLength() {
        if (isBuiltInName()) {
            return 1;
        }
        return this.field_12_name_text.length();
    }

    public boolean isHiddenName() {
        return (this.field_1_option_flag & 1) != 0;
    }

    public void setHidden(boolean b) {
        if (b) {
            this.field_1_option_flag = (short) (this.field_1_option_flag | 1);
        } else {
            this.field_1_option_flag = (short) (this.field_1_option_flag & -2);
        }
    }

    public boolean isFunctionName() {
        return (this.field_1_option_flag & 2) != 0;
    }

    public void setFunction(boolean function) {
        if (function) {
            this.field_1_option_flag = (short) (this.field_1_option_flag | 2);
        } else {
            this.field_1_option_flag = (short) (this.field_1_option_flag & -3);
        }
    }

    public boolean hasFormula() {
        return Option.isFormula(this.field_1_option_flag) && this.field_13_name_definition.getEncodedTokenSize() > 0;
    }

    public boolean isCommandName() {
        return (this.field_1_option_flag & 4) != 0;
    }

    public boolean isMacro() {
        return (this.field_1_option_flag & 8) != 0;
    }

    public boolean isComplexFunction() {
        return (this.field_1_option_flag & 16) != 0;
    }

    public boolean isBuiltInName() {
        return (this.field_1_option_flag & 32) != 0;
    }

    public String getNameText() {
        return isBuiltInName() ? translateBuiltInName(getBuiltInName()) : this.field_12_name_text;
    }

    public byte getBuiltInName() {
        return this.field_12_built_in_code;
    }

    public Ptg[] getNameDefinition() {
        return this.field_13_name_definition.getTokens();
    }

    public void setNameDefinition(Ptg[] ptgs) {
        this.field_13_name_definition = Formula.create(ptgs);
    }

    public String getCustomMenuText() {
        return this.field_14_custom_menu_text;
    }

    public String getDescriptionText() {
        return this.field_15_description_text;
    }

    public String getHelpTopicText() {
        return this.field_16_help_topic_text;
    }

    public String getStatusBarText() {
        return this.field_17_status_bar_text;
    }

    public void serialize(LittleEndianOutput out) {
        int field_7_length_custom_menu = this.field_14_custom_menu_text.length();
        int field_8_length_description_text = this.field_15_description_text.length();
        int field_9_length_help_topic_text = this.field_16_help_topic_text.length();
        int field_10_length_status_bar_text = this.field_17_status_bar_text.length();
        out.writeShort(getOptionFlag());
        out.writeByte(getKeyboardShortcut());
        out.writeByte(getNameTextLength());
        out.writeShort(this.field_13_name_definition.getEncodedTokenSize());
        out.writeShort(this.field_5_externSheetIndex_plus1);
        out.writeShort(this.field_6_sheetNumber);
        out.writeByte(field_7_length_custom_menu);
        out.writeByte(field_8_length_description_text);
        out.writeByte(field_9_length_help_topic_text);
        out.writeByte(field_10_length_status_bar_text);
        out.writeByte(this.field_11_nameIsMultibyte ? 1 : 0);
        if (isBuiltInName()) {
            out.writeByte(this.field_12_built_in_code);
        } else {
            String nameText = this.field_12_name_text;
            if (this.field_11_nameIsMultibyte) {
                StringUtil.putUnicodeLE(nameText, out);
            } else {
                StringUtil.putCompressedUnicode(nameText, out);
            }
        }
        this.field_13_name_definition.serializeTokens(out);
        this.field_13_name_definition.serializeArrayConstantData(out);
        StringUtil.putCompressedUnicode(getCustomMenuText(), out);
        StringUtil.putCompressedUnicode(getDescriptionText(), out);
        StringUtil.putCompressedUnicode(getHelpTopicText(), out);
        StringUtil.putCompressedUnicode(getStatusBarText(), out);
    }

    private int getNameRawSize() {
        if (isBuiltInName()) {
            return 1;
        }
        int nChars = this.field_12_name_text.length();
        if (this.field_11_nameIsMultibyte) {
            return nChars * 2;
        }
        return nChars;
    }

    /* access modifiers changed from: protected */
    public int getDataSize() {
        return getNameRawSize() + 13 + this.field_14_custom_menu_text.length() + this.field_15_description_text.length() + this.field_16_help_topic_text.length() + this.field_17_status_bar_text.length() + this.field_13_name_definition.getEncodedSize();
    }

    public int getExternSheetNumber() {
        if (this.field_13_name_definition.getEncodedSize() < 1) {
            return 0;
        }
        Ptg ptg = this.field_13_name_definition.getTokens()[0];
        if (ptg.getClass() == Area3DPtg.class) {
            return ((Area3DPtg) ptg).getExternSheetIndex();
        }
        if (ptg.getClass() == Ref3DPtg.class) {
            return ((Ref3DPtg) ptg).getExternSheetIndex();
        }
        return 0;
    }

    public NameRecord(RecordInputStream ris) {
        LittleEndianInput in = ris;
        this.field_1_option_flag = in.readShort();
        this.field_2_keyboard_shortcut = in.readByte();
        int field_3_length_name_text = in.readUByte();
        int field_4_length_name_definition = in.readShort();
        this.field_5_externSheetIndex_plus1 = in.readShort();
        this.field_6_sheetNumber = in.readUShort();
        int f7_customMenuLen = in.readUByte();
        int f8_descriptionTextLen = in.readUByte();
        int f9_helpTopicTextLen = in.readUByte();
        int f10_statusBarTextLen = in.readUByte();
        this.field_11_nameIsMultibyte = in.readByte() != 0;
        if (isBuiltInName()) {
            this.field_12_built_in_code = in.readByte();
        } else if (this.field_11_nameIsMultibyte) {
            this.field_12_name_text = StringUtil.readUnicodeLE(in, field_3_length_name_text);
        } else {
            this.field_12_name_text = StringUtil.readCompressedUnicode(in, field_3_length_name_text);
        }
        this.field_13_name_definition = Formula.read(field_4_length_name_definition, in, in.available() - (((f7_customMenuLen + f8_descriptionTextLen) + f9_helpTopicTextLen) + f10_statusBarTextLen));
        this.field_14_custom_menu_text = StringUtil.readCompressedUnicode(in, f7_customMenuLen);
        this.field_15_description_text = StringUtil.readCompressedUnicode(in, f8_descriptionTextLen);
        this.field_16_help_topic_text = StringUtil.readCompressedUnicode(in, f9_helpTopicTextLen);
        this.field_17_status_bar_text = StringUtil.readCompressedUnicode(in, f10_statusBarTextLen);
    }

    public short getSid() {
        return 24;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[NAME]\n");
        sb.append("    .option flags           = ");
        sb.append(HexDump.shortToHex(this.field_1_option_flag));
        sb.append("\n");
        sb.append("    .keyboard shortcut      = ");
        sb.append(HexDump.byteToHex(this.field_2_keyboard_shortcut));
        sb.append("\n");
        sb.append("    .length of the name     = ");
        sb.append(getNameTextLength());
        sb.append("\n");
        sb.append("    .extSheetIx(1-based, 0=Global)= ");
        sb.append(this.field_5_externSheetIndex_plus1);
        sb.append("\n");
        sb.append("    .sheetTabIx             = ");
        sb.append(this.field_6_sheetNumber);
        sb.append("\n");
        sb.append("    .Menu text length       = ");
        sb.append(this.field_14_custom_menu_text.length());
        sb.append("\n");
        sb.append("    .Description text length= ");
        sb.append(this.field_15_description_text.length());
        sb.append("\n");
        sb.append("    .Help topic text length = ");
        sb.append(this.field_16_help_topic_text.length());
        sb.append("\n");
        sb.append("    .Status bar text length = ");
        sb.append(this.field_17_status_bar_text.length());
        sb.append("\n");
        sb.append("    .NameIsMultibyte        = ");
        sb.append(this.field_11_nameIsMultibyte);
        sb.append("\n");
        sb.append("    .Name (Unicode text)    = ");
        sb.append(getNameText());
        sb.append("\n");
        Ptg[] ptgs = this.field_13_name_definition.getTokens();
        sb.append("    .Formula (nTokens=");
        sb.append(ptgs.length);
        sb.append("):");
        sb.append("\n");
        for (Ptg ptg : ptgs) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("       ");
            sb2.append(ptg.toString());
            sb.append(sb2.toString());
            sb.append(ptg.getRVAType());
            sb.append("\n");
        }
        sb.append("    .Menu text       = ");
        sb.append(this.field_14_custom_menu_text);
        sb.append("\n");
        sb.append("    .Description text= ");
        sb.append(this.field_15_description_text);
        sb.append("\n");
        sb.append("    .Help topic text = ");
        sb.append(this.field_16_help_topic_text);
        sb.append("\n");
        sb.append("    .Status bar text = ");
        sb.append(this.field_17_status_bar_text);
        sb.append("\n");
        sb.append("[/NAME]\n");
        return sb.toString();
    }

    private static String translateBuiltInName(byte name) {
        switch (name) {
            case 1:
                return "Consolidate_Area";
            case 2:
                return "Auto_Open";
            case 3:
                return "Auto_Close";
            case 4:
                return "Database";
            case 5:
                return "Criteria";
            case 6:
                return "Print_Area";
            case 7:
                return "Print_Titles";
            case 8:
                return "Recorder";
            case 9:
                return "Data_Form";
            case 10:
                return "Auto_Activate";
            case 11:
                return "Auto_Deactivate";
            case 12:
                return "Sheet_Title";
            case 13:
                return "_FilterDatabase";
            default:
                return "Unknown";
        }
    }
}
