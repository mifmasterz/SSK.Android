package p005io.reactivex.internal.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import p005io.reactivex.functions.Function;

/* renamed from: io.reactivex.internal.util.SorterFunction */
public final class SorterFunction<T> implements Function<List<T>, List<T>> {
    final Comparator<? super T> comparator;

    public SorterFunction(Comparator<? super T> comparator2) {
        this.comparator = comparator2;
    }

    public List<T> apply(List<T> t) throws Exception {
        Collections.sort(t, this.comparator);
        return t;
    }
}
