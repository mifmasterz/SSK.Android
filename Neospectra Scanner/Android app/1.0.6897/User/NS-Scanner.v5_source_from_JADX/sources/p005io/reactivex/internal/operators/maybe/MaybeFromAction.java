package p005io.reactivex.internal.operators.maybe;

import java.util.concurrent.Callable;
import p005io.reactivex.Maybe;
import p005io.reactivex.MaybeObserver;
import p005io.reactivex.disposables.Disposable;
import p005io.reactivex.disposables.Disposables;
import p005io.reactivex.exceptions.Exceptions;
import p005io.reactivex.functions.Action;
import p005io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.maybe.MaybeFromAction */
public final class MaybeFromAction<T> extends Maybe<T> implements Callable<T> {
    final Action action;

    public MaybeFromAction(Action action2) {
        this.action = action2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(MaybeObserver<? super T> observer) {
        Disposable d = Disposables.empty();
        observer.onSubscribe(d);
        if (!d.isDisposed()) {
            try {
                this.action.run();
                if (!d.isDisposed()) {
                    observer.onComplete();
                }
            } catch (Throwable ex) {
                Exceptions.throwIfFatal(ex);
                if (!d.isDisposed()) {
                    observer.onError(ex);
                } else {
                    RxJavaPlugins.onError(ex);
                }
            }
        }
    }

    public T call() throws Exception {
        this.action.run();
        return null;
    }
}
