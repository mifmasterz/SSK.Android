package org.apache.poi.p009ss.usermodel;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import org.apache.poi.p009ss.usermodel.Row.MissingCellPolicy;

/* renamed from: org.apache.poi.ss.usermodel.Workbook */
public interface Workbook {
    public static final int PICTURE_TYPE_DIB = 7;
    public static final int PICTURE_TYPE_EMF = 2;
    public static final int PICTURE_TYPE_JPEG = 5;
    public static final int PICTURE_TYPE_PICT = 4;
    public static final int PICTURE_TYPE_PNG = 6;
    public static final int PICTURE_TYPE_WMF = 3;
    public static final int SHEET_STATE_HIDDEN = 1;
    public static final int SHEET_STATE_VERY_HIDDEN = 2;
    public static final int SHEET_STATE_VISIBLE = 0;

    int addPicture(byte[] bArr, int i);

    Sheet cloneSheet(int i);

    CellStyle createCellStyle();

    DataFormat createDataFormat();

    Font createFont();

    Name createName();

    Sheet createSheet();

    Sheet createSheet(String str);

    Font findFont(short s, short s2, short s3, String str, boolean z, boolean z2, short s4, byte b);

    int getActiveSheetIndex();

    List<? extends PictureData> getAllPictures();

    CellStyle getCellStyleAt(short s);

    CreationHelper getCreationHelper();

    int getFirstVisibleTab();

    Font getFontAt(short s);

    MissingCellPolicy getMissingCellPolicy();

    Name getName(String str);

    Name getNameAt(int i);

    int getNameIndex(String str);

    short getNumCellStyles();

    short getNumberOfFonts();

    int getNumberOfNames();

    int getNumberOfSheets();

    String getPrintArea(int i);

    Sheet getSheet(String str);

    Sheet getSheetAt(int i);

    int getSheetIndex(String str);

    int getSheetIndex(Sheet sheet);

    String getSheetName(int i);

    boolean isHidden();

    boolean isSheetHidden(int i);

    boolean isSheetVeryHidden(int i);

    void removeName(int i);

    void removeName(String str);

    void removePrintArea(int i);

    void removeSheetAt(int i);

    void setActiveSheet(int i);

    void setFirstVisibleTab(int i);

    void setHidden(boolean z);

    void setMissingCellPolicy(MissingCellPolicy missingCellPolicy);

    void setPrintArea(int i, int i2, int i3, int i4, int i5);

    void setPrintArea(int i, String str);

    void setRepeatingRowsAndColumns(int i, int i2, int i3, int i4, int i5);

    void setSelectedTab(int i);

    void setSheetHidden(int i, int i2);

    void setSheetHidden(int i, boolean z);

    void setSheetName(int i, String str);

    void setSheetOrder(String str, int i);

    void write(OutputStream outputStream) throws IOException;
}
