package p005io.reactivex.internal.operators.maybe;

import java.util.concurrent.atomic.AtomicReference;
import p005io.reactivex.Maybe;
import p005io.reactivex.MaybeObserver;
import p005io.reactivex.MaybeSource;
import p005io.reactivex.disposables.Disposable;

/* renamed from: io.reactivex.internal.operators.maybe.MaybeCache */
public final class MaybeCache<T> extends Maybe<T> implements MaybeObserver<T> {
    static final CacheDisposable[] EMPTY = new CacheDisposable[0];
    static final CacheDisposable[] TERMINATED = new CacheDisposable[0];
    Throwable error;
    final AtomicReference<CacheDisposable<T>[]> observers = new AtomicReference<>(EMPTY);
    final AtomicReference<MaybeSource<T>> source;
    T value;

    /* renamed from: io.reactivex.internal.operators.maybe.MaybeCache$CacheDisposable */
    static final class CacheDisposable<T> extends AtomicReference<MaybeCache<T>> implements Disposable {
        private static final long serialVersionUID = -5791853038359966195L;
        final MaybeObserver<? super T> actual;

        CacheDisposable(MaybeObserver<? super T> actual2, MaybeCache<T> parent) {
            super(parent);
            this.actual = actual2;
        }

        public void dispose() {
            MaybeCache<T> mc = (MaybeCache) getAndSet(null);
            if (mc != null) {
                mc.remove(this);
            }
        }

        public boolean isDisposed() {
            return get() == null;
        }
    }

    public MaybeCache(MaybeSource<T> source2) {
        this.source = new AtomicReference<>(source2);
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(MaybeObserver<? super T> observer) {
        CacheDisposable<T> parent = new CacheDisposable<>(observer, this);
        observer.onSubscribe(parent);
        if (!add(parent)) {
            if (!parent.isDisposed()) {
                Throwable ex = this.error;
                if (ex != null) {
                    observer.onError(ex);
                } else {
                    T v = this.value;
                    if (v != null) {
                        observer.onSuccess(v);
                    } else {
                        observer.onComplete();
                    }
                }
            }
        } else if (parent.isDisposed()) {
            remove(parent);
        } else {
            MaybeSource<T> src = (MaybeSource) this.source.getAndSet(null);
            if (src != null) {
                src.subscribe(this);
            }
        }
    }

    public void onSubscribe(Disposable d) {
    }

    public void onSuccess(T value2) {
        CacheDisposable<T>[] cacheDisposableArr;
        this.value = value2;
        for (CacheDisposable<T> inner : (CacheDisposable[]) this.observers.getAndSet(TERMINATED)) {
            if (!inner.isDisposed()) {
                inner.actual.onSuccess(value2);
            }
        }
    }

    public void onError(Throwable e) {
        CacheDisposable<T>[] cacheDisposableArr;
        this.error = e;
        for (CacheDisposable<T> inner : (CacheDisposable[]) this.observers.getAndSet(TERMINATED)) {
            if (!inner.isDisposed()) {
                inner.actual.onError(e);
            }
        }
    }

    public void onComplete() {
        CacheDisposable<T>[] cacheDisposableArr;
        for (CacheDisposable<T> inner : (CacheDisposable[]) this.observers.getAndSet(TERMINATED)) {
            if (!inner.isDisposed()) {
                inner.actual.onComplete();
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean add(CacheDisposable<T> inner) {
        CacheDisposable<T>[] a;
        CacheDisposable<T>[] b;
        do {
            a = (CacheDisposable[]) this.observers.get();
            if (a == TERMINATED) {
                return false;
            }
            int n = a.length;
            b = new CacheDisposable[(n + 1)];
            System.arraycopy(a, 0, b, 0, n);
            b[n] = inner;
        } while (!this.observers.compareAndSet(a, b));
        return true;
    }

    /* access modifiers changed from: 0000 */
    public void remove(CacheDisposable<T> inner) {
        CacheDisposable<T>[] a;
        CacheDisposable<T>[] b;
        do {
            a = (CacheDisposable[]) this.observers.get();
            int n = a.length;
            if (n != 0) {
                int j = -1;
                int i = 0;
                while (true) {
                    if (i >= n) {
                        break;
                    } else if (a[i] == inner) {
                        j = i;
                        break;
                    } else {
                        i++;
                    }
                }
                if (j >= 0) {
                    if (n == 1) {
                        b = EMPTY;
                    } else {
                        CacheDisposable<T>[] b2 = new CacheDisposable[(n - 1)];
                        System.arraycopy(a, 0, b2, 0, j);
                        System.arraycopy(a, j + 1, b2, j, (n - j) - 1);
                        b = b2;
                    }
                } else {
                    return;
                }
            } else {
                return;
            }
        } while (!this.observers.compareAndSet(a, b));
    }
}
