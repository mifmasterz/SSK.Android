package org.apache.poi.util;

import java.util.ArrayList;
import java.util.List;

public abstract class POILogger {
    public static int DEBUG = 1;
    public static int ERROR = 7;
    public static int FATAL = 9;
    public static int INFO = 3;
    public static int WARN = 5;

    public abstract boolean check(int i);

    public abstract void initialize(String str);

    public abstract void log(int i, Object obj);

    public abstract void log(int i, Object obj, Throwable th);

    POILogger() {
    }

    public void log(int level, Object obj1, Object obj2) {
        if (check(level)) {
            StringBuffer stringBuffer = new StringBuffer(32);
            stringBuffer.append(obj1);
            stringBuffer.append(obj2);
            log(level, (Object) stringBuffer);
        }
    }

    public void log(int level, Object obj1, Object obj2, Object obj3) {
        if (check(level)) {
            StringBuffer stringBuffer = new StringBuffer(48);
            stringBuffer.append(obj1);
            stringBuffer.append(obj2);
            stringBuffer.append(obj3);
            log(level, (Object) stringBuffer);
        }
    }

    public void log(int level, Object obj1, Object obj2, Object obj3, Object obj4) {
        if (check(level)) {
            StringBuffer stringBuffer = new StringBuffer(64);
            stringBuffer.append(obj1);
            stringBuffer.append(obj2);
            stringBuffer.append(obj3);
            stringBuffer.append(obj4);
            log(level, (Object) stringBuffer);
        }
    }

    public void log(int level, Object obj1, Object obj2, Object obj3, Object obj4, Object obj5) {
        if (check(level)) {
            StringBuffer stringBuffer = new StringBuffer(80);
            stringBuffer.append(obj1);
            stringBuffer.append(obj2);
            stringBuffer.append(obj3);
            stringBuffer.append(obj4);
            stringBuffer.append(obj5);
            log(level, (Object) stringBuffer);
        }
    }

    public void log(int level, Object obj1, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6) {
        if (check(level)) {
            StringBuffer stringBuffer = new StringBuffer(96);
            stringBuffer.append(obj1);
            stringBuffer.append(obj2);
            stringBuffer.append(obj3);
            stringBuffer.append(obj4);
            stringBuffer.append(obj5);
            stringBuffer.append(obj6);
            log(level, (Object) stringBuffer);
        }
    }

    public void log(int level, Object obj1, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6, Object obj7) {
        if (check(level)) {
            StringBuffer stringBuffer = new StringBuffer(112);
            stringBuffer.append(obj1);
            stringBuffer.append(obj2);
            stringBuffer.append(obj3);
            stringBuffer.append(obj4);
            stringBuffer.append(obj5);
            stringBuffer.append(obj6);
            stringBuffer.append(obj7);
            log(level, (Object) stringBuffer);
        }
    }

    public void log(int level, Object obj1, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6, Object obj7, Object obj8) {
        if (check(level)) {
            StringBuffer stringBuffer = new StringBuffer(128);
            stringBuffer.append(obj1);
            stringBuffer.append(obj2);
            stringBuffer.append(obj3);
            stringBuffer.append(obj4);
            stringBuffer.append(obj5);
            stringBuffer.append(obj6);
            stringBuffer.append(obj7);
            stringBuffer.append(obj8);
            log(level, (Object) stringBuffer);
        }
    }

    public void log(int level, Throwable exception) {
        log(level, (Object) null, exception);
    }

    public void log(int level, Object obj1, Object obj2, Throwable exception) {
        if (check(level)) {
            StringBuffer stringBuffer = new StringBuffer(32);
            stringBuffer.append(obj1);
            stringBuffer.append(obj2);
            log(level, (Object) stringBuffer, exception);
        }
    }

    public void log(int level, Object obj1, Object obj2, Object obj3, Throwable exception) {
        if (check(level)) {
            StringBuffer stringBuffer = new StringBuffer(48);
            stringBuffer.append(obj1);
            stringBuffer.append(obj2);
            stringBuffer.append(obj3);
            log(level, (Object) stringBuffer, exception);
        }
    }

    public void log(int level, Object obj1, Object obj2, Object obj3, Object obj4, Throwable exception) {
        if (check(level)) {
            StringBuffer stringBuffer = new StringBuffer(64);
            stringBuffer.append(obj1);
            stringBuffer.append(obj2);
            stringBuffer.append(obj3);
            stringBuffer.append(obj4);
            log(level, (Object) stringBuffer, exception);
        }
    }

    public void log(int level, Object obj1, Object obj2, Object obj3, Object obj4, Object obj5, Throwable exception) {
        if (check(level)) {
            StringBuffer stringBuffer = new StringBuffer(80);
            stringBuffer.append(obj1);
            stringBuffer.append(obj2);
            stringBuffer.append(obj3);
            stringBuffer.append(obj4);
            stringBuffer.append(obj5);
            log(level, (Object) stringBuffer, exception);
        }
    }

    public void log(int level, Object obj1, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6, Throwable exception) {
        if (check(level)) {
            StringBuffer stringBuffer = new StringBuffer(96);
            stringBuffer.append(obj1);
            stringBuffer.append(obj2);
            stringBuffer.append(obj3);
            stringBuffer.append(obj4);
            stringBuffer.append(obj5);
            stringBuffer.append(obj6);
            log(level, (Object) stringBuffer, exception);
        }
    }

    public void log(int level, Object obj1, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6, Object obj7, Throwable exception) {
        if (check(level)) {
            StringBuffer stringBuffer = new StringBuffer(112);
            stringBuffer.append(obj1);
            stringBuffer.append(obj2);
            stringBuffer.append(obj3);
            stringBuffer.append(obj4);
            stringBuffer.append(obj5);
            stringBuffer.append(obj6);
            stringBuffer.append(obj7);
            log(level, (Object) stringBuffer, exception);
        }
    }

    public void log(int level, Object obj1, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6, Object obj7, Object obj8, Throwable exception) {
        if (check(level)) {
            StringBuffer stringBuffer = new StringBuffer(128);
            stringBuffer.append(obj1);
            stringBuffer.append(obj2);
            stringBuffer.append(obj3);
            stringBuffer.append(obj4);
            stringBuffer.append(obj5);
            stringBuffer.append(obj6);
            stringBuffer.append(obj7);
            stringBuffer.append(obj8);
            log(level, (Object) stringBuffer, exception);
        }
    }

    public void logFormatted(int level, String message, Object obj1) {
        commonLogFormatted(level, message, new Object[]{obj1});
    }

    public void logFormatted(int level, String message, Object obj1, Object obj2) {
        commonLogFormatted(level, message, new Object[]{obj1, obj2});
    }

    public void logFormatted(int level, String message, Object obj1, Object obj2, Object obj3) {
        commonLogFormatted(level, message, new Object[]{obj1, obj2, obj3});
    }

    public void logFormatted(int level, String message, Object obj1, Object obj2, Object obj3, Object obj4) {
        commonLogFormatted(level, message, new Object[]{obj1, obj2, obj3, obj4});
    }

    private void commonLogFormatted(int level, String message, Object[] unflatParams) {
        if (check(level)) {
            Object[] params = flattenArrays(unflatParams);
            if (params[params.length - 1] instanceof Throwable) {
                log(level, (Object) StringUtil.format(message, params), (Throwable) params[params.length - 1]);
            } else {
                log(level, (Object) StringUtil.format(message, params));
            }
        }
    }

    private Object[] flattenArrays(Object[] objects) {
        List<Object> results = new ArrayList<>();
        for (Object objectToObjectArray : objects) {
            results.addAll(objectToObjectArray(objectToObjectArray));
        }
        return results.toArray(new Object[results.size()]);
    }

    private List<Object> objectToObjectArray(Object object) {
        List<Object> results = new ArrayList<>();
        int j = 0;
        if (object instanceof byte[]) {
            byte[] array = (byte[]) object;
            for (byte valueOf : array) {
                results.add(Byte.valueOf(valueOf));
            }
        }
        if (object instanceof char[]) {
            char[] array2 = (char[]) object;
            while (j < array2.length) {
                results.add(new Character(array2[j]));
                j++;
            }
        } else if (object instanceof short[]) {
            short[] array3 = (short[]) object;
            while (j < array3.length) {
                results.add(Short.valueOf(array3[j]));
                j++;
            }
        } else if (object instanceof int[]) {
            int[] array4 = (int[]) object;
            while (j < array4.length) {
                results.add(Integer.valueOf(array4[j]));
                j++;
            }
        } else if (object instanceof long[]) {
            long[] array5 = (long[]) object;
            while (j < array5.length) {
                results.add(Long.valueOf(array5[j]));
                j++;
            }
        } else if (object instanceof float[]) {
            float[] array6 = (float[]) object;
            while (j < array6.length) {
                results.add(new Float(array6[j]));
                j++;
            }
        } else if (object instanceof double[]) {
            double[] array7 = (double[]) object;
            while (j < array7.length) {
                results.add(new Double(array7[j]));
                j++;
            }
        } else if (object instanceof Object[]) {
            Object[] array8 = (Object[]) object;
            while (j < array8.length) {
                results.add(array8[j]);
                j++;
            }
        } else {
            results.add(object);
        }
        return results;
    }
}
