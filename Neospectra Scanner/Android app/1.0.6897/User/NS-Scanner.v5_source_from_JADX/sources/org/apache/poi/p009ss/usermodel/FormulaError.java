package org.apache.poi.p009ss.usermodel;

import java.util.HashMap;
import java.util.Map;

/* renamed from: org.apache.poi.ss.usermodel.FormulaError */
public enum FormulaError {
    NULL(0, "#NULL!"),
    DIV0(7, "#DIV/0!"),
    VALUE(15, "#VALUE!"),
    REF(23, "#REF!"),
    NAME(29, "#NAME?"),
    NUM(36, "#NUM!"),
    NA(42, "#N/A");
    
    private static Map<Byte, FormulaError> imap;
    private static Map<String, FormulaError> smap;
    private String repr;
    private byte type;

    static {
        int i$;
        smap = new HashMap();
        imap = new HashMap();
        FormulaError[] arr$ = values();
        int len$ = arr$.length;
        while (true) {
            int i$2 = i$;
            if (i$2 < len$) {
                FormulaError error = arr$[i$2];
                imap.put(Byte.valueOf(error.getCode()), error);
                smap.put(error.getString(), error);
                i$ = i$2 + 1;
            } else {
                return;
            }
        }
    }

    private FormulaError(int type2, String repr2) {
        this.type = (byte) type2;
        this.repr = repr2;
    }

    public byte getCode() {
        return this.type;
    }

    public String getString() {
        return this.repr;
    }

    public static FormulaError forInt(byte type2) {
        FormulaError err = (FormulaError) imap.get(Byte.valueOf(type2));
        if (err != null) {
            return err;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Unknown error type: ");
        sb.append(type2);
        throw new IllegalArgumentException(sb.toString());
    }

    public static FormulaError forString(String code) {
        FormulaError err = (FormulaError) smap.get(code);
        if (err != null) {
            return err;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Unknown error code: ");
        sb.append(code);
        throw new IllegalArgumentException(sb.toString());
    }
}
