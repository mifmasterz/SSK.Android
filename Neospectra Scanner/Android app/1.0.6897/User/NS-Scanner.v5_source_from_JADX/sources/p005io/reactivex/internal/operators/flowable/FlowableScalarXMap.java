package p005io.reactivex.internal.operators.flowable;

import java.util.concurrent.Callable;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import p005io.reactivex.Flowable;
import p005io.reactivex.exceptions.Exceptions;
import p005io.reactivex.functions.Function;
import p005io.reactivex.internal.functions.ObjectHelper;
import p005io.reactivex.internal.subscriptions.EmptySubscription;
import p005io.reactivex.internal.subscriptions.ScalarSubscription;
import p005io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.flowable.FlowableScalarXMap */
public final class FlowableScalarXMap {

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableScalarXMap$ScalarXMapFlowable */
    static final class ScalarXMapFlowable<T, R> extends Flowable<R> {
        final Function<? super T, ? extends Publisher<? extends R>> mapper;
        final T value;

        ScalarXMapFlowable(T value2, Function<? super T, ? extends Publisher<? extends R>> mapper2) {
            this.value = value2;
            this.mapper = mapper2;
        }

        public void subscribeActual(Subscriber<? super R> s) {
            try {
                Publisher<? extends R> other = (Publisher) ObjectHelper.requireNonNull(this.mapper.apply(this.value), "The mapper returned a null Publisher");
                if (other instanceof Callable) {
                    try {
                        R u = ((Callable) other).call();
                        if (u == null) {
                            EmptySubscription.complete(s);
                            return;
                        }
                        s.onSubscribe(new ScalarSubscription(s, u));
                    } catch (Throwable ex) {
                        Exceptions.throwIfFatal(ex);
                        EmptySubscription.error(ex, s);
                    }
                } else {
                    other.subscribe(s);
                }
            } catch (Throwable e) {
                EmptySubscription.error(e, s);
            }
        }
    }

    private FlowableScalarXMap() {
        throw new IllegalStateException("No instances!");
    }

    public static <T, R> boolean tryScalarXMapSubscribe(Publisher<T> source, Subscriber<? super R> subscriber, Function<? super T, ? extends Publisher<? extends R>> mapper) {
        if (!(source instanceof Callable)) {
            return false;
        }
        try {
            T t = ((Callable) source).call();
            if (t == null) {
                EmptySubscription.complete(subscriber);
                return true;
            }
            try {
                Publisher<? extends R> r = (Publisher) ObjectHelper.requireNonNull(mapper.apply(t), "The mapper returned a null Publisher");
                if (r instanceof Callable) {
                    try {
                        R u = ((Callable) r).call();
                        if (u == null) {
                            EmptySubscription.complete(subscriber);
                            return true;
                        }
                        subscriber.onSubscribe(new ScalarSubscription(subscriber, u));
                    } catch (Throwable ex) {
                        Exceptions.throwIfFatal(ex);
                        EmptySubscription.error(ex, subscriber);
                        return true;
                    }
                } else {
                    r.subscribe(subscriber);
                }
                return true;
            } catch (Throwable ex2) {
                Exceptions.throwIfFatal(ex2);
                EmptySubscription.error(ex2, subscriber);
                return true;
            }
        } catch (Throwable ex3) {
            Exceptions.throwIfFatal(ex3);
            EmptySubscription.error(ex3, subscriber);
            return true;
        }
    }

    public static <T, U> Flowable<U> scalarXMap(T value, Function<? super T, ? extends Publisher<? extends U>> mapper) {
        return RxJavaPlugins.onAssembly((Flowable<T>) new ScalarXMapFlowable<T>(value, mapper));
    }
}
