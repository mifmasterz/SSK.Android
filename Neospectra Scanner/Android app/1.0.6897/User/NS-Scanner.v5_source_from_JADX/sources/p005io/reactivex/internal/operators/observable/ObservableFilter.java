package p005io.reactivex.internal.operators.observable;

import p005io.reactivex.ObservableSource;
import p005io.reactivex.Observer;
import p005io.reactivex.annotations.Nullable;
import p005io.reactivex.functions.Predicate;
import p005io.reactivex.internal.observers.BasicFuseableObserver;

/* renamed from: io.reactivex.internal.operators.observable.ObservableFilter */
public final class ObservableFilter<T> extends AbstractObservableWithUpstream<T, T> {
    final Predicate<? super T> predicate;

    /* renamed from: io.reactivex.internal.operators.observable.ObservableFilter$FilterObserver */
    static final class FilterObserver<T> extends BasicFuseableObserver<T, T> {
        final Predicate<? super T> filter;

        FilterObserver(Observer<? super T> actual, Predicate<? super T> filter2) {
            super(actual);
            this.filter = filter2;
        }

        public void onNext(T t) {
            if (this.sourceMode == 0) {
                try {
                    if (this.filter.test(t)) {
                        this.actual.onNext(t);
                    }
                } catch (Throwable e) {
                    fail(e);
                }
            } else {
                this.actual.onNext(null);
            }
        }

        public int requestFusion(int mode) {
            return transitiveBoundaryFusion(mode);
        }

        @Nullable
        public T poll() throws Exception {
            T v;
            do {
                v = this.f77qs.poll();
                if (v == null) {
                    break;
                }
            } while (!this.filter.test(v));
            return v;
        }
    }

    public ObservableFilter(ObservableSource<T> source, Predicate<? super T> predicate2) {
        super(source);
        this.predicate = predicate2;
    }

    public void subscribeActual(Observer<? super T> s) {
        this.source.subscribe(new FilterObserver(s, this.predicate));
    }
}
