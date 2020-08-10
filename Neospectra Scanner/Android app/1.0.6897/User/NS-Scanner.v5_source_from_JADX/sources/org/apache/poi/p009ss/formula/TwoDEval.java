package org.apache.poi.p009ss.formula;

import org.apache.poi.hssf.record.formula.eval.ValueEval;

/* renamed from: org.apache.poi.ss.formula.TwoDEval */
public interface TwoDEval extends ValueEval {
    TwoDEval getColumn(int i);

    int getHeight();

    TwoDEval getRow(int i);

    ValueEval getValue(int i, int i2);

    int getWidth();

    boolean isColumn();

    boolean isRow();
}
