package p005io.reactivex.internal.operators.single;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;
import p005io.reactivex.Scheduler;
import p005io.reactivex.Single;
import p005io.reactivex.SingleObserver;
import p005io.reactivex.SingleSource;
import p005io.reactivex.disposables.Disposable;
import p005io.reactivex.internal.disposables.DisposableHelper;
import p005io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.single.SingleTimeout */
public final class SingleTimeout<T> extends Single<T> {
    final SingleSource<? extends T> other;
    final Scheduler scheduler;
    final SingleSource<T> source;
    final long timeout;
    final TimeUnit unit;

    /* renamed from: io.reactivex.internal.operators.single.SingleTimeout$TimeoutMainObserver */
    static final class TimeoutMainObserver<T> extends AtomicReference<Disposable> implements SingleObserver<T>, Runnable, Disposable {
        private static final long serialVersionUID = 37497744973048446L;
        final SingleObserver<? super T> actual;
        final TimeoutFallbackObserver<T> fallback;
        SingleSource<? extends T> other;
        final AtomicReference<Disposable> task = new AtomicReference<>();

        /* renamed from: io.reactivex.internal.operators.single.SingleTimeout$TimeoutMainObserver$TimeoutFallbackObserver */
        static final class TimeoutFallbackObserver<T> extends AtomicReference<Disposable> implements SingleObserver<T> {
            private static final long serialVersionUID = 2071387740092105509L;
            final SingleObserver<? super T> actual;

            TimeoutFallbackObserver(SingleObserver<? super T> actual2) {
                this.actual = actual2;
            }

            public void onSubscribe(Disposable d) {
                DisposableHelper.setOnce(this, d);
            }

            public void onSuccess(T t) {
                this.actual.onSuccess(t);
            }

            public void onError(Throwable e) {
                this.actual.onError(e);
            }
        }

        TimeoutMainObserver(SingleObserver<? super T> actual2, SingleSource<? extends T> other2) {
            this.actual = actual2;
            this.other = other2;
            if (other2 != null) {
                this.fallback = new TimeoutFallbackObserver<>(actual2);
            } else {
                this.fallback = null;
            }
        }

        public void run() {
            Disposable d = (Disposable) get();
            if (d != DisposableHelper.DISPOSED && compareAndSet(d, DisposableHelper.DISPOSED)) {
                if (d != null) {
                    d.dispose();
                }
                SingleSource<? extends T> other2 = this.other;
                if (other2 == null) {
                    this.actual.onError(new TimeoutException());
                    return;
                }
                this.other = null;
                other2.subscribe(this.fallback);
            }
        }

        public void onSubscribe(Disposable d) {
            DisposableHelper.setOnce(this, d);
        }

        public void onSuccess(T t) {
            Disposable d = (Disposable) get();
            if (d != DisposableHelper.DISPOSED && compareAndSet(d, DisposableHelper.DISPOSED)) {
                DisposableHelper.dispose(this.task);
                this.actual.onSuccess(t);
            }
        }

        public void onError(Throwable e) {
            Disposable d = (Disposable) get();
            if (d == DisposableHelper.DISPOSED || !compareAndSet(d, DisposableHelper.DISPOSED)) {
                RxJavaPlugins.onError(e);
                return;
            }
            DisposableHelper.dispose(this.task);
            this.actual.onError(e);
        }

        public void dispose() {
            DisposableHelper.dispose(this);
            DisposableHelper.dispose(this.task);
            if (this.fallback != null) {
                DisposableHelper.dispose(this.fallback);
            }
        }

        public boolean isDisposed() {
            return DisposableHelper.isDisposed((Disposable) get());
        }
    }

    public SingleTimeout(SingleSource<T> source2, long timeout2, TimeUnit unit2, Scheduler scheduler2, SingleSource<? extends T> other2) {
        this.source = source2;
        this.timeout = timeout2;
        this.unit = unit2;
        this.scheduler = scheduler2;
        this.other = other2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super T> s) {
        TimeoutMainObserver<T> parent = new TimeoutMainObserver<>(s, this.other);
        s.onSubscribe(parent);
        DisposableHelper.replace(parent.task, this.scheduler.scheduleDirect(parent, this.timeout, this.unit));
        this.source.subscribe(parent);
    }
}
