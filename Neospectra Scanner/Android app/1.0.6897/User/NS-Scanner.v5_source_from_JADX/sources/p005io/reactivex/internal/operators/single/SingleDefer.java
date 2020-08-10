package p005io.reactivex.internal.operators.single;

import java.util.concurrent.Callable;
import p005io.reactivex.Single;
import p005io.reactivex.SingleObserver;
import p005io.reactivex.SingleSource;
import p005io.reactivex.exceptions.Exceptions;
import p005io.reactivex.internal.disposables.EmptyDisposable;
import p005io.reactivex.internal.functions.ObjectHelper;

/* renamed from: io.reactivex.internal.operators.single.SingleDefer */
public final class SingleDefer<T> extends Single<T> {
    final Callable<? extends SingleSource<? extends T>> singleSupplier;

    public SingleDefer(Callable<? extends SingleSource<? extends T>> singleSupplier2) {
        this.singleSupplier = singleSupplier2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(SingleObserver<? super T> s) {
        try {
            ((SingleSource) ObjectHelper.requireNonNull(this.singleSupplier.call(), "The singleSupplier returned a null SingleSource")).subscribe(s);
        } catch (Throwable e) {
            Exceptions.throwIfFatal(e);
            EmptyDisposable.error(e, s);
        }
    }
}
