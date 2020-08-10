package org.apache.commons.math3.stat.descriptive.moment;

import java.io.Serializable;
import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.exception.NullArgumentException;
import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.commons.math3.stat.descriptive.AbstractStorelessUnivariateStatistic;
import org.apache.commons.math3.stat.descriptive.WeightedEvaluation;
import org.apache.commons.math3.util.MathUtils;

public class Variance extends AbstractStorelessUnivariateStatistic implements Serializable, WeightedEvaluation {
    private static final long serialVersionUID = -9111962718267217978L;
    protected boolean incMoment;
    private boolean isBiasCorrected;
    protected SecondMoment moment;

    public Variance() {
        this.moment = null;
        this.incMoment = true;
        this.isBiasCorrected = true;
        this.moment = new SecondMoment();
    }

    public Variance(SecondMoment m2) {
        this.moment = null;
        this.incMoment = true;
        this.isBiasCorrected = true;
        this.incMoment = false;
        this.moment = m2;
    }

    public Variance(boolean isBiasCorrected2) {
        this.moment = null;
        this.incMoment = true;
        this.isBiasCorrected = true;
        this.moment = new SecondMoment();
        this.isBiasCorrected = isBiasCorrected2;
    }

    public Variance(boolean isBiasCorrected2, SecondMoment m2) {
        this.moment = null;
        this.incMoment = true;
        this.isBiasCorrected = true;
        this.incMoment = false;
        this.moment = m2;
        this.isBiasCorrected = isBiasCorrected2;
    }

    public Variance(Variance original) throws NullArgumentException {
        this.moment = null;
        this.incMoment = true;
        this.isBiasCorrected = true;
        copy(original, this);
    }

    public void increment(double d) {
        if (this.incMoment) {
            this.moment.increment(d);
        }
    }

    public double getResult() {
        if (this.moment.f779n == 0) {
            return Double.NaN;
        }
        if (this.moment.f779n == 1) {
            return 0.0d;
        }
        if (this.isBiasCorrected) {
            return this.moment.f781m2 / (((double) this.moment.f779n) - 1.0d);
        }
        return this.moment.f781m2 / ((double) this.moment.f779n);
    }

    public long getN() {
        return this.moment.getN();
    }

    public void clear() {
        if (this.incMoment) {
            this.moment.clear();
        }
    }

    public double evaluate(double[] values) throws MathIllegalArgumentException {
        if (values != null) {
            return evaluate(values, 0, values.length);
        }
        throw new NullArgumentException(LocalizedFormats.INPUT_ARRAY, new Object[0]);
    }

    public double evaluate(double[] values, int begin, int length) throws MathIllegalArgumentException {
        if (!test(values, begin, length)) {
            return Double.NaN;
        }
        clear();
        if (length == 1) {
            return 0.0d;
        }
        if (length > 1) {
            return evaluate(values, new Mean().evaluate(values, begin, length), begin, length);
        }
        return Double.NaN;
    }

    public double evaluate(double[] values, double[] weights, int begin, int length) throws MathIllegalArgumentException {
        int i = length;
        double var = Double.NaN;
        if (test(values, weights, begin, length)) {
            clear();
            if (i == 1) {
                var = 0.0d;
            } else if (i > 1) {
                double[] dArr = values;
                double[] dArr2 = weights;
                int i2 = begin;
                return evaluate(dArr, dArr2, new Mean().evaluate(dArr, dArr2, i2, i), i2, i);
            }
        }
        double[] dArr3 = values;
        double[] dArr4 = weights;
        int i3 = begin;
        return var;
    }

    public double evaluate(double[] values, double[] weights) throws MathIllegalArgumentException {
        return evaluate(values, weights, 0, values.length);
    }

    public double evaluate(double[] values, double mean, int begin, int length) throws MathIllegalArgumentException {
        double[] dArr = values;
        int i = begin;
        int i2 = length;
        if (!test(dArr, i, i2)) {
            return Double.NaN;
        }
        if (i2 == 1) {
            return 0.0d;
        }
        if (i2 <= 1) {
            return Double.NaN;
        }
        double accum2 = 0.0d;
        double accum = 0.0d;
        for (int i3 = i; i3 < i + i2; i3++) {
            double dev = dArr[i3] - mean;
            accum += dev * dev;
            accum2 += dev;
        }
        double len = (double) i2;
        if (this.isBiasCorrected) {
            return (accum - ((accum2 * accum2) / len)) / (len - 1.0d);
        }
        return (accum - ((accum2 * accum2) / len)) / len;
    }

    public double evaluate(double[] values, double mean) throws MathIllegalArgumentException {
        return evaluate(values, mean, 0, values.length);
    }

    public double evaluate(double[] values, double[] weights, double mean, int begin, int length) throws MathIllegalArgumentException {
        double[] dArr = values;
        double[] dArr2 = weights;
        int i = begin;
        int i2 = length;
        if (!test(dArr, dArr2, i, i2)) {
            return Double.NaN;
        }
        if (i2 == 1) {
            return 0.0d;
        }
        if (i2 <= 1) {
            return Double.NaN;
        }
        double accum2 = 0.0d;
        double accum = 0.0d;
        for (int i3 = i; i3 < i + i2; i3++) {
            double dev = dArr[i3] - mean;
            accum += dArr2[i3] * dev * dev;
            accum2 += dArr2[i3] * dev;
        }
        double sumWts = 0.0d;
        for (int i4 = i; i4 < i + i2; i4++) {
            sumWts += dArr2[i4];
        }
        if (this.isBiasCorrected != 0) {
            return (accum - ((accum2 * accum2) / sumWts)) / (sumWts - 1.0d);
        }
        return (accum - ((accum2 * accum2) / sumWts)) / sumWts;
    }

    public double evaluate(double[] values, double[] weights, double mean) throws MathIllegalArgumentException {
        return evaluate(values, weights, mean, 0, values.length);
    }

    public boolean isBiasCorrected() {
        return this.isBiasCorrected;
    }

    public void setBiasCorrected(boolean biasCorrected) {
        this.isBiasCorrected = biasCorrected;
    }

    public Variance copy() {
        Variance result = new Variance();
        copy(this, result);
        return result;
    }

    public static void copy(Variance source, Variance dest) throws NullArgumentException {
        MathUtils.checkNotNull(source);
        MathUtils.checkNotNull(dest);
        dest.setData(source.getDataRef());
        dest.moment = source.moment.copy();
        dest.isBiasCorrected = source.isBiasCorrected;
        dest.incMoment = source.incMoment;
    }
}
