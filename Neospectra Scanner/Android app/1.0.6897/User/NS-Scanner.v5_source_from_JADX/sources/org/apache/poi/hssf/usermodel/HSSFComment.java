package org.apache.poi.hssf.usermodel;

import org.apache.poi.hssf.record.NoteRecord;
import org.apache.poi.hssf.record.TextObjectRecord;
import org.apache.poi.p009ss.usermodel.Comment;
import org.apache.poi.p009ss.usermodel.RichTextString;

public class HSSFComment extends HSSFTextbox implements Comment {
    private String _author;
    private int _col;
    private NoteRecord _note;
    private int _row;
    private TextObjectRecord _txo;
    private boolean _visible;

    public /* bridge */ /* synthetic */ RichTextString getString() {
        return super.getString();
    }

    public HSSFComment(HSSFShape parent, HSSFAnchor anchor) {
        super(parent, anchor);
        setShapeType(25);
        this._fillColor = 134217808;
        this._visible = false;
        this._author = "";
    }

    protected HSSFComment(NoteRecord note, TextObjectRecord txo) {
        this((HSSFShape) null, (HSSFAnchor) null);
        this._txo = txo;
        this._note = note;
    }

    public void setVisible(boolean visible) {
        if (this._note != null) {
            this._note.setFlags(visible ? (short) 2 : 0);
        }
        this._visible = visible;
    }

    public boolean isVisible() {
        return this._visible;
    }

    public int getRow() {
        return this._row;
    }

    public void setRow(int row) {
        if (this._note != null) {
            this._note.setRow(row);
        }
        this._row = row;
    }

    public int getColumn() {
        return this._col;
    }

    public void setColumn(int col) {
        if (this._note != null) {
            this._note.setColumn(col);
        }
        this._col = col;
    }

    @Deprecated
    public void setColumn(short col) {
        setColumn((int) col);
    }

    public String getAuthor() {
        return this._author;
    }

    public void setAuthor(String author) {
        if (this._note != null) {
            this._note.setAuthor(author);
        }
        this._author = author;
    }

    public void setString(RichTextString string) {
        HSSFRichTextString hstring = (HSSFRichTextString) string;
        if (hstring.numFormattingRuns() == 0) {
            hstring.applyFont(0);
        }
        if (this._txo != null) {
            this._txo.setStr(hstring);
        }
        super.setString(string);
    }

    /* access modifiers changed from: protected */
    public NoteRecord getNoteRecord() {
        return this._note;
    }

    /* access modifiers changed from: protected */
    public TextObjectRecord getTextObjectRecord() {
        return this._txo;
    }
}
