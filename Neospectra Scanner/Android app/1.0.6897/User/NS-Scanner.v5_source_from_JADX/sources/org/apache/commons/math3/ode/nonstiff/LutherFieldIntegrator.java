package org.apache.commons.math3.ode.nonstiff;

import org.apache.commons.math3.Field;
import org.apache.commons.math3.RealFieldElement;
import org.apache.commons.math3.ode.FieldEquationsMapper;
import org.apache.commons.math3.ode.FieldODEStateAndDerivative;
import org.apache.commons.math3.util.MathArrays;
import org.apache.poi.p009ss.usermodel.ShapeTypes;

public class LutherFieldIntegrator<T extends RealFieldElement<T>> extends RungeKuttaFieldIntegrator<T> {
    public LutherFieldIntegrator(Field<T> field, T step) {
        super(field, "Luther", step);
    }

    public T[] getC() {
        T q = (RealFieldElement) ((RealFieldElement) ((RealFieldElement) getField().getZero()).add(21.0d)).sqrt();
        T[] c = (RealFieldElement[]) MathArrays.buildArray(getField(), 6);
        c[0] = (RealFieldElement) getField().getOne();
        c[1] = fraction(1, 2);
        c[2] = fraction(2, 3);
        c[3] = (RealFieldElement) ((RealFieldElement) q.subtract(7.0d)).divide(-14.0d);
        c[4] = (RealFieldElement) ((RealFieldElement) q.add(7.0d)).divide(14.0d);
        c[5] = (RealFieldElement) getField().getOne();
        return c;
    }

    public T[][] getA() {
        T q = (RealFieldElement) ((RealFieldElement) ((RealFieldElement) getField().getZero()).add(21.0d)).sqrt();
        T[][] a = (RealFieldElement[][]) MathArrays.buildArray(getField(), 6, -1);
        for (int i = 0; i < a.length; i++) {
            a[i] = (RealFieldElement[]) MathArrays.buildArray(getField(), i + 1);
        }
        a[0][0] = (RealFieldElement) getField().getOne();
        a[1][0] = fraction(3, 8);
        a[1][1] = fraction(1, 8);
        a[2][0] = fraction(8, 27);
        a[2][1] = fraction(2, 27);
        a[2][2] = a[2][0];
        a[3][0] = (RealFieldElement) ((RealFieldElement) ((RealFieldElement) q.multiply(9)).add(-21.0d)).divide(392.0d);
        a[3][1] = (RealFieldElement) ((RealFieldElement) ((RealFieldElement) q.multiply(8)).add(-56.0d)).divide(392.0d);
        a[3][2] = (RealFieldElement) ((RealFieldElement) ((RealFieldElement) q.multiply(-48)).add(336.0d)).divide(392.0d);
        a[3][3] = (RealFieldElement) ((RealFieldElement) ((RealFieldElement) q.multiply(3)).add(-63.0d)).divide(392.0d);
        a[4][0] = (RealFieldElement) ((RealFieldElement) ((RealFieldElement) q.multiply(-255)).add(-1155.0d)).divide(1960.0d);
        a[4][1] = (RealFieldElement) ((RealFieldElement) ((RealFieldElement) q.multiply(-40)).add(-280.0d)).divide(1960.0d);
        a[4][2] = (RealFieldElement) ((RealFieldElement) q.multiply(-320)).divide(1960.0d);
        a[4][3] = (RealFieldElement) ((RealFieldElement) ((RealFieldElement) q.multiply(363)).add(63.0d)).divide(1960.0d);
        a[4][4] = (RealFieldElement) ((RealFieldElement) ((RealFieldElement) q.multiply(392)).add(2352.0d)).divide(1960.0d);
        a[5][0] = (RealFieldElement) ((RealFieldElement) ((RealFieldElement) q.multiply(105)).add(330.0d)).divide(180.0d);
        a[5][1] = fraction(2, 3);
        a[5][2] = (RealFieldElement) ((RealFieldElement) ((RealFieldElement) q.multiply(280)).add(-200.0d)).divide(180.0d);
        a[5][3] = (RealFieldElement) ((RealFieldElement) ((RealFieldElement) q.multiply(-189)).add(126.0d)).divide(180.0d);
        a[5][4] = (RealFieldElement) ((RealFieldElement) ((RealFieldElement) q.multiply(-126)).add(-686.0d)).divide(180.0d);
        a[5][5] = (RealFieldElement) ((RealFieldElement) ((RealFieldElement) q.multiply(-70)).add(490.0d)).divide(180.0d);
        return a;
    }

    public T[] getB() {
        T[] b = (RealFieldElement[]) MathArrays.buildArray(getField(), 7);
        b[0] = fraction(1, 20);
        b[1] = (RealFieldElement) getField().getZero();
        b[2] = fraction(16, 45);
        b[3] = (RealFieldElement) getField().getZero();
        b[4] = fraction(49, ShapeTypes.MATH_EQUAL);
        b[5] = b[4];
        b[6] = b[0];
        return b;
    }

    /* access modifiers changed from: protected */
    public LutherFieldStepInterpolator<T> createInterpolator(boolean forward, T[][] yDotK, FieldODEStateAndDerivative<T> globalPreviousState, FieldODEStateAndDerivative<T> globalCurrentState, FieldEquationsMapper<T> mapper) {
        LutherFieldStepInterpolator lutherFieldStepInterpolator = new LutherFieldStepInterpolator(getField(), forward, yDotK, globalPreviousState, globalCurrentState, globalPreviousState, globalCurrentState, mapper);
        return lutherFieldStepInterpolator;
    }
}
