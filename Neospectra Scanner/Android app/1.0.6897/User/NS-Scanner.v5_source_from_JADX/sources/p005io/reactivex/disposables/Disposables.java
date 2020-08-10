package p005io.reactivex.disposables;

import java.util.concurrent.Future;
import org.reactivestreams.Subscription;
import p005io.reactivex.annotations.NonNull;
import p005io.reactivex.functions.Action;
import p005io.reactivex.internal.disposables.EmptyDisposable;
import p005io.reactivex.internal.functions.Functions;
import p005io.reactivex.internal.functions.ObjectHelper;

/* renamed from: io.reactivex.disposables.Disposables */
public final class Disposables {
    private Disposables() {
        throw new IllegalStateException("No instances!");
    }

    @NonNull
    public static Disposable fromRunnable(@NonNull Runnable run) {
        ObjectHelper.requireNonNull(run, "run is null");
        return new RunnableDisposable(run);
    }

    @NonNull
    public static Disposable fromAction(@NonNull Action run) {
        ObjectHelper.requireNonNull(run, "run is null");
        return new ActionDisposable(run);
    }

    @NonNull
    public static Disposable fromFuture(@NonNull Future<?> future) {
        ObjectHelper.requireNonNull(future, "future is null");
        return fromFuture(future, true);
    }

    @NonNull
    public static Disposable fromFuture(@NonNull Future<?> future, boolean allowInterrupt) {
        ObjectHelper.requireNonNull(future, "future is null");
        return new FutureDisposable(future, allowInterrupt);
    }

    @NonNull
    public static Disposable fromSubscription(@NonNull Subscription subscription) {
        ObjectHelper.requireNonNull(subscription, "subscription is null");
        return new SubscriptionDisposable(subscription);
    }

    @NonNull
    public static Disposable empty() {
        return fromRunnable(Functions.EMPTY_RUNNABLE);
    }

    @NonNull
    public static Disposable disposed() {
        return EmptyDisposable.INSTANCE;
    }
}
