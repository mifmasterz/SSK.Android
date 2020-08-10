package p008rx.internal.operators;

import p008rx.Observable;
import p008rx.Observable.OnSubscribe;
import p008rx.Producer;
import p008rx.Scheduler;
import p008rx.Scheduler.Worker;
import p008rx.Subscriber;
import p008rx.functions.Action0;

/* renamed from: rx.internal.operators.OperatorSubscribeOn */
public final class OperatorSubscribeOn<T> implements OnSubscribe<T> {
    final Scheduler scheduler;
    final Observable<T> source;

    public OperatorSubscribeOn(Observable<T> source2, Scheduler scheduler2) {
        this.scheduler = scheduler2;
        this.source = source2;
    }

    public void call(final Subscriber<? super T> subscriber) {
        final Worker inner = this.scheduler.createWorker();
        subscriber.add(inner);
        inner.schedule(new Action0() {
            public void call() {
                final Thread t = Thread.currentThread();
                OperatorSubscribeOn.this.source.unsafeSubscribe(new Subscriber<T>(subscriber) {
                    public void onNext(T t) {
                        subscriber.onNext(t);
                    }

                    public void onError(Throwable e) {
                        try {
                            subscriber.onError(e);
                        } finally {
                            inner.unsubscribe();
                        }
                    }

                    public void onCompleted() {
                        try {
                            subscriber.onCompleted();
                        } finally {
                            inner.unsubscribe();
                        }
                    }

                    public void setProducer(final Producer p) {
                        subscriber.setProducer(new Producer() {
                            public void request(final long n) {
                                if (t == Thread.currentThread()) {
                                    p.request(n);
                                } else {
                                    inner.schedule(new Action0() {
                                        public void call() {
                                            p.request(n);
                                        }
                                    });
                                }
                            }
                        });
                    }
                });
            }
        });
    }
}
