package p005io.reactivex.internal.operators.observable;

import p005io.reactivex.ObservableSource;
import p005io.reactivex.Observer;
import p005io.reactivex.annotations.Nullable;
import p005io.reactivex.functions.BiPredicate;
import p005io.reactivex.functions.Function;
import p005io.reactivex.internal.observers.BasicFuseableObserver;

/* renamed from: io.reactivex.internal.operators.observable.ObservableDistinctUntilChanged */
public final class ObservableDistinctUntilChanged<T, K> extends AbstractObservableWithUpstream<T, T> {
    final BiPredicate<? super K, ? super K> comparer;
    final Function<? super T, K> keySelector;

    /* renamed from: io.reactivex.internal.operators.observable.ObservableDistinctUntilChanged$DistinctUntilChangedObserver */
    static final class DistinctUntilChangedObserver<T, K> extends BasicFuseableObserver<T, T> {
        final BiPredicate<? super K, ? super K> comparer;
        boolean hasValue;
        final Function<? super T, K> keySelector;
        K last;

        DistinctUntilChangedObserver(Observer<? super T> actual, Function<? super T, K> keySelector2, BiPredicate<? super K, ? super K> comparer2) {
            super(actual);
            this.keySelector = keySelector2;
            this.comparer = comparer2;
        }

        public void onNext(T t) {
            if (!this.done) {
                if (this.sourceMode != 0) {
                    this.actual.onNext(t);
                    return;
                }
                try {
                    K key = this.keySelector.apply(t);
                    if (this.hasValue) {
                        boolean equal = this.comparer.test(this.last, key);
                        this.last = key;
                        if (equal) {
                            return;
                        }
                    } else {
                        this.hasValue = true;
                        this.last = key;
                    }
                    this.actual.onNext(t);
                } catch (Throwable ex) {
                    fail(ex);
                }
            }
        }

        public int requestFusion(int mode) {
            return transitiveBoundaryFusion(mode);
        }

        @Nullable
        public T poll() throws Exception {
            while (true) {
                T v = this.f77qs.poll();
                if (v == null) {
                    return null;
                }
                K key = this.keySelector.apply(v);
                if (!this.hasValue) {
                    this.hasValue = true;
                    this.last = key;
                    return v;
                } else if (!this.comparer.test(this.last, key)) {
                    this.last = key;
                    return v;
                } else {
                    this.last = key;
                }
            }
        }
    }

    public ObservableDistinctUntilChanged(ObservableSource<T> source, Function<? super T, K> keySelector2, BiPredicate<? super K, ? super K> comparer2) {
        super(source);
        this.keySelector = keySelector2;
        this.comparer = comparer2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Observer<? super T> s) {
        this.source.subscribe(new DistinctUntilChangedObserver(s, this.keySelector, this.comparer));
    }
}
