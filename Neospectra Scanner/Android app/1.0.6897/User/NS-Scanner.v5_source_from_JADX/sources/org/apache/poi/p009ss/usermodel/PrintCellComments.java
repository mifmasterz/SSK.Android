package org.apache.poi.p009ss.usermodel;

/* renamed from: org.apache.poi.ss.usermodel.PrintCellComments */
public enum PrintCellComments {
    NONE(1),
    AS_DISPLAYED(2),
    AT_END(3);
    
    private static PrintCellComments[] _table;
    private int comments;

    static {
        int i$;
        PrintCellComments[] arr$;
        _table = new PrintCellComments[4];
        for (PrintCellComments c : values()) {
            _table[c.getValue()] = c;
        }
    }

    private PrintCellComments(int comments2) {
        this.comments = comments2;
    }

    public int getValue() {
        return this.comments;
    }

    public static PrintCellComments valueOf(int value) {
        return _table[value];
    }
}
