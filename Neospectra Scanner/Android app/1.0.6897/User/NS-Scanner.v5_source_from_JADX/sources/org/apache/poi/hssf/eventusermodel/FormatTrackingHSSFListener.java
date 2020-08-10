package org.apache.poi.hssf.eventusermodel;

import java.io.PrintStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.poi.hssf.record.CellValueRecordInterface;
import org.apache.poi.hssf.record.ExtendedFormatRecord;
import org.apache.poi.hssf.record.FormatRecord;
import org.apache.poi.hssf.record.FormulaRecord;
import org.apache.poi.hssf.record.NumberRecord;
import org.apache.poi.hssf.record.Record;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDataFormatter;

public class FormatTrackingHSSFListener implements HSSFListener {
    private final HSSFListener _childListener;
    private final Map<Integer, FormatRecord> _customFormatRecords;
    private final NumberFormat _defaultFormat;
    private final HSSFDataFormatter _formatter;
    private final List<ExtendedFormatRecord> _xfRecords;

    public FormatTrackingHSSFListener(HSSFListener childListener) {
        this(childListener, Locale.getDefault());
    }

    public FormatTrackingHSSFListener(HSSFListener childListener, Locale locale) {
        this._customFormatRecords = new Hashtable();
        this._xfRecords = new ArrayList();
        this._childListener = childListener;
        this._formatter = new HSSFDataFormatter(locale);
        this._defaultFormat = NumberFormat.getInstance(locale);
    }

    /* access modifiers changed from: protected */
    public int getNumberOfCustomFormats() {
        return this._customFormatRecords.size();
    }

    /* access modifiers changed from: protected */
    public int getNumberOfExtendedFormats() {
        return this._xfRecords.size();
    }

    public void processRecord(Record record) {
        processRecordInternally(record);
        this._childListener.processRecord(record);
    }

    public void processRecordInternally(Record record) {
        if (record instanceof FormatRecord) {
            FormatRecord fr = (FormatRecord) record;
            this._customFormatRecords.put(Integer.valueOf(fr.getIndexCode()), fr);
        }
        if (record instanceof ExtendedFormatRecord) {
            this._xfRecords.add((ExtendedFormatRecord) record);
        }
    }

    public String formatNumberDateCell(CellValueRecordInterface cell) {
        double value;
        if (cell instanceof NumberRecord) {
            value = ((NumberRecord) cell).getValue();
        } else if (cell instanceof FormulaRecord) {
            value = ((FormulaRecord) cell).getValue();
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Unsupported CellValue Record passed in ");
            sb.append(cell);
            throw new IllegalArgumentException(sb.toString());
        }
        int formatIndex = getFormatIndex(cell);
        String formatString = getFormatString(cell);
        if (formatString == null) {
            return this._defaultFormat.format(value);
        }
        return this._formatter.formatRawCellContents(value, formatIndex, formatString);
    }

    public String getFormatString(int formatIndex) {
        if (formatIndex < HSSFDataFormat.getNumberOfBuiltinBuiltinFormats()) {
            return HSSFDataFormat.getBuiltinFormat((short) formatIndex);
        }
        FormatRecord tfr = (FormatRecord) this._customFormatRecords.get(Integer.valueOf(formatIndex));
        if (tfr != null) {
            return tfr.getFormatString();
        }
        PrintStream printStream = System.err;
        StringBuilder sb = new StringBuilder();
        sb.append("Requested format at index ");
        sb.append(formatIndex);
        sb.append(", but it wasn't found");
        printStream.println(sb.toString());
        return null;
    }

    public String getFormatString(CellValueRecordInterface cell) {
        int formatIndex = getFormatIndex(cell);
        if (formatIndex == -1) {
            return null;
        }
        return getFormatString(formatIndex);
    }

    public int getFormatIndex(CellValueRecordInterface cell) {
        ExtendedFormatRecord xfr = (ExtendedFormatRecord) this._xfRecords.get(cell.getXFIndex());
        if (xfr != null) {
            return xfr.getFormatIndex();
        }
        PrintStream printStream = System.err;
        StringBuilder sb = new StringBuilder();
        sb.append("Cell ");
        sb.append(cell.getRow());
        sb.append(",");
        sb.append(cell.getColumn());
        sb.append(" uses XF with index ");
        sb.append(cell.getXFIndex());
        sb.append(", but we don't have that");
        printStream.println(sb.toString());
        return -1;
    }
}
