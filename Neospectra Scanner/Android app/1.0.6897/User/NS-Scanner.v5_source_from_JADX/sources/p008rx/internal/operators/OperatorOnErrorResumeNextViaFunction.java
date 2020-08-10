package p008rx.internal.operators;

import p008rx.Observable;
import p008rx.Observable.Operator;
import p008rx.Observer;
import p008rx.Producer;
import p008rx.Subscriber;
import p008rx.exceptions.Exceptions;
import p008rx.functions.Func1;
import p008rx.internal.producers.ProducerArbiter;
import p008rx.plugins.RxJavaHooks;
import p008rx.subscriptions.SerialSubscription;

/* renamed from: rx.internal.operators.OperatorOnErrorResumeNextViaFunction */
public final class OperatorOnErrorResumeNextViaFunction<T> implements Operator<T, T> {
    final Func1<? super Throwable, ? extends Observable<? extends T>> resumeFunction;

    public static <T> OperatorOnErrorResumeNextViaFunction<T> withSingle(final Func1<? super Throwable, ? extends T> resumeFunction2) {
        return new OperatorOnErrorResumeNextViaFunction<>(new Func1<Throwable, Observable<? extends T>>() {
            public Observable<? extends T> call(Throwable t) {
                return Observable.just(resumeFunction2.call(t));
            }
        });
    }

    public static <T> OperatorOnErrorResumeNextViaFunction<T> withOther(final Observable<? extends T> other) {
        return new OperatorOnErrorResumeNextViaFunction<>(new Func1<Throwable, Observable<? extends T>>() {
            public Observable<? extends T> call(Throwable t) {
                return other;
            }
        });
    }

    public static <T> OperatorOnErrorResumeNextViaFunction<T> withException(final Observable<? extends T> other) {
        return new OperatorOnErrorResumeNextViaFunction<>(new Func1<Throwable, Observable<? extends T>>() {
            public Observable<? extends T> call(Throwable t) {
                if (t instanceof Exception) {
                    return other;
                }
                return Observable.error(t);
            }
        });
    }

    public OperatorOnErrorResumeNextViaFunction(Func1<? super Throwable, ? extends Observable<? extends T>> f) {
        this.resumeFunction = f;
    }

    public Subscriber<? super T> call(final Subscriber<? super T> child) {
        final ProducerArbiter pa = new ProducerArbiter();
        final SerialSubscription serial = new SerialSubscription();
        Subscriber<T> parent = new Subscriber<T>() {
            private boolean done;
            long produced;

            public void onCompleted() {
                if (!this.done) {
                    this.done = true;
                    child.onCompleted();
                }
            }

            public void onError(Throwable e) {
                if (this.done) {
                    Exceptions.throwIfFatal(e);
                    RxJavaHooks.onError(e);
                    return;
                }
                this.done = true;
                try {
                    unsubscribe();
                    Subscriber<T> next = new Subscriber<T>() {
                        public void onNext(T t) {
                            child.onNext(t);
                        }

                        public void onError(Throwable e) {
                            child.onError(e);
                        }

                        public void onCompleted() {
                            child.onCompleted();
                        }

                        public void setProducer(Producer producer) {
                            pa.setProducer(producer);
                        }
                    };
                    serial.set(next);
                    long p = this.produced;
                    if (p != 0) {
                        pa.produced(p);
                    }
                    ((Observable) OperatorOnErrorResumeNextViaFunction.this.resumeFunction.call(e)).unsafeSubscribe(next);
                } catch (Throwable e2) {
                    Exceptions.throwOrReport(e2, (Observer<?>) child);
                }
            }

            public void onNext(T t) {
                if (!this.done) {
                    this.produced++;
                    child.onNext(t);
                }
            }

            public void setProducer(Producer producer) {
                pa.setProducer(producer);
            }
        };
        serial.set(parent);
        child.add(serial);
        child.setProducer(pa);
        return parent;
    }
}
