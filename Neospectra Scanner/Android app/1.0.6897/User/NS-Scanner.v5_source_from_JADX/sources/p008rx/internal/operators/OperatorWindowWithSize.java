package p008rx.internal.operators;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import p008rx.Observable;
import p008rx.Observable.Operator;
import p008rx.Producer;
import p008rx.Subscriber;
import p008rx.Subscription;
import p008rx.functions.Action0;
import p008rx.internal.util.atomic.SpscLinkedArrayQueue;
import p008rx.subjects.Subject;
import p008rx.subjects.UnicastSubject;
import p008rx.subscriptions.Subscriptions;

/* renamed from: rx.internal.operators.OperatorWindowWithSize */
public final class OperatorWindowWithSize<T> implements Operator<Observable<T>, T> {
    final int size;
    final int skip;

    /* renamed from: rx.internal.operators.OperatorWindowWithSize$WindowExact */
    static final class WindowExact<T> extends Subscriber<T> implements Action0 {
        final Subscriber<? super Observable<T>> actual;
        final Subscription cancel = Subscriptions.create(this);
        int index;
        final int size;
        Subject<T, T> window;
        final AtomicInteger wip = new AtomicInteger(1);

        public WindowExact(Subscriber<? super Observable<T>> actual2, int size2) {
            this.actual = actual2;
            this.size = size2;
            add(this.cancel);
            request(0);
        }

        public void onNext(T t) {
            int i = this.index;
            Subject<T, T> w = this.window;
            if (i == 0) {
                this.wip.getAndIncrement();
                w = UnicastSubject.create(this.size, this);
                this.window = w;
                this.actual.onNext(w);
            }
            int i2 = i + 1;
            w.onNext(t);
            if (i2 == this.size) {
                this.index = 0;
                this.window = null;
                w.onCompleted();
                return;
            }
            this.index = i2;
        }

        public void onError(Throwable e) {
            Subject<T, T> w = this.window;
            if (w != null) {
                this.window = null;
                w.onError(e);
            }
            this.actual.onError(e);
        }

        public void onCompleted() {
            Subject<T, T> w = this.window;
            if (w != null) {
                this.window = null;
                w.onCompleted();
            }
            this.actual.onCompleted();
        }

        /* access modifiers changed from: 0000 */
        public Producer createProducer() {
            return new Producer() {
                public void request(long n) {
                    if (n < 0) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("n >= 0 required but it was ");
                        sb.append(n);
                        throw new IllegalArgumentException(sb.toString());
                    } else if (n != 0) {
                        WindowExact.this.request(BackpressureUtils.multiplyCap((long) WindowExact.this.size, n));
                    }
                }
            };
        }

        public void call() {
            if (this.wip.decrementAndGet() == 0) {
                unsubscribe();
            }
        }
    }

    /* renamed from: rx.internal.operators.OperatorWindowWithSize$WindowOverlap */
    static final class WindowOverlap<T> extends Subscriber<T> implements Action0 {
        final Subscriber<? super Observable<T>> actual;
        final Subscription cancel = Subscriptions.create(this);
        volatile boolean done;
        final AtomicInteger drainWip = new AtomicInteger();
        Throwable error;
        int index;
        int produced;
        final Queue<Subject<T, T>> queue;
        final AtomicLong requested = new AtomicLong();
        final int size;
        final int skip;
        final ArrayDeque<Subject<T, T>> windows = new ArrayDeque<>();
        final AtomicInteger wip = new AtomicInteger(1);

        /* renamed from: rx.internal.operators.OperatorWindowWithSize$WindowOverlap$WindowOverlapProducer */
        final class WindowOverlapProducer extends AtomicBoolean implements Producer {
            private static final long serialVersionUID = 4625807964358024108L;

            WindowOverlapProducer() {
            }

            public void request(long n) {
                if (n < 0) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("n >= 0 required but it was ");
                    sb.append(n);
                    throw new IllegalArgumentException(sb.toString());
                } else if (n != 0) {
                    WindowOverlap<T> parent = WindowOverlap.this;
                    if (get() || !compareAndSet(false, true)) {
                        WindowOverlap.this.request(BackpressureUtils.multiplyCap((long) parent.skip, n));
                    } else {
                        parent.request(BackpressureUtils.addCap(BackpressureUtils.multiplyCap((long) parent.skip, n - 1), (long) parent.size));
                    }
                    BackpressureUtils.getAndAddRequest(parent.requested, n);
                    parent.drain();
                }
            }
        }

        public WindowOverlap(Subscriber<? super Observable<T>> actual2, int size2, int skip2) {
            this.actual = actual2;
            this.size = size2;
            this.skip = skip2;
            add(this.cancel);
            request(0);
            this.queue = new SpscLinkedArrayQueue(((skip2 - 1) + size2) / skip2);
        }

        public void onNext(T t) {
            int i = this.index;
            ArrayDeque<Subject<T, T>> q = this.windows;
            if (i == 0 && !this.actual.isUnsubscribed()) {
                this.wip.getAndIncrement();
                Subject<T, T> w = UnicastSubject.create(16, this);
                q.offer(w);
                this.queue.offer(w);
                drain();
            }
            Iterator i$ = this.windows.iterator();
            while (i$.hasNext()) {
                ((Subject) i$.next()).onNext(t);
            }
            int p = this.produced + 1;
            if (p == this.size) {
                this.produced = p - this.skip;
                Subject<T, T> w2 = (Subject) q.poll();
                if (w2 != null) {
                    w2.onCompleted();
                }
            } else {
                this.produced = p;
            }
            int i2 = i + 1;
            if (i2 == this.skip) {
                this.index = 0;
            } else {
                this.index = i2;
            }
        }

        public void onError(Throwable e) {
            Iterator i$ = this.windows.iterator();
            while (i$.hasNext()) {
                ((Subject) i$.next()).onError(e);
            }
            this.windows.clear();
            this.error = e;
            this.done = true;
            drain();
        }

        public void onCompleted() {
            Iterator i$ = this.windows.iterator();
            while (i$.hasNext()) {
                ((Subject) i$.next()).onCompleted();
            }
            this.windows.clear();
            this.done = true;
            drain();
        }

        /* access modifiers changed from: 0000 */
        public Producer createProducer() {
            return new WindowOverlapProducer();
        }

        public void call() {
            if (this.wip.decrementAndGet() == 0) {
                unsubscribe();
            }
        }

        /* access modifiers changed from: 0000 */
        public void drain() {
            AtomicInteger dw = this.drainWip;
            if (dw.getAndIncrement() == 0) {
                Subscriber<? super Observable<T>> subscriber = this.actual;
                Queue<Subject<T, T>> q = this.queue;
                int missed = 1;
                do {
                    long r = this.requested.get();
                    long e = 0;
                    while (e != r) {
                        boolean d = this.done;
                        Subject<T, T> v = (Subject) q.poll();
                        boolean empty = v == null;
                        if (!checkTerminated(d, empty, subscriber, q)) {
                            if (empty) {
                                break;
                            }
                            subscriber.onNext(v);
                            e++;
                        } else {
                            return;
                        }
                    }
                    if (e != r || !checkTerminated(this.done, q.isEmpty(), subscriber, q)) {
                        if (!(e == 0 || r == Long.MAX_VALUE)) {
                            this.requested.addAndGet(-e);
                        }
                        missed = dw.addAndGet(-missed);
                    } else {
                        return;
                    }
                } while (missed != 0);
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean checkTerminated(boolean d, boolean empty, Subscriber<? super Subject<T, T>> a, Queue<Subject<T, T>> q) {
            if (a.isUnsubscribed()) {
                q.clear();
                return true;
            }
            if (d) {
                Throwable e = this.error;
                if (e != null) {
                    q.clear();
                    a.onError(e);
                    return true;
                } else if (empty) {
                    a.onCompleted();
                    return true;
                }
            }
            return false;
        }
    }

    /* renamed from: rx.internal.operators.OperatorWindowWithSize$WindowSkip */
    static final class WindowSkip<T> extends Subscriber<T> implements Action0 {
        final Subscriber<? super Observable<T>> actual;
        final Subscription cancel = Subscriptions.create(this);
        int index;
        final int size;
        final int skip;
        Subject<T, T> window;
        final AtomicInteger wip = new AtomicInteger(1);

        /* renamed from: rx.internal.operators.OperatorWindowWithSize$WindowSkip$WindowSkipProducer */
        final class WindowSkipProducer extends AtomicBoolean implements Producer {
            private static final long serialVersionUID = 4625807964358024108L;

            WindowSkipProducer() {
            }

            public void request(long n) {
                if (n < 0) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("n >= 0 required but it was ");
                    sb.append(n);
                    throw new IllegalArgumentException(sb.toString());
                } else if (n != 0) {
                    WindowSkip<T> parent = WindowSkip.this;
                    if (get() || !compareAndSet(false, true)) {
                        parent.request(BackpressureUtils.multiplyCap(n, (long) parent.skip));
                    } else {
                        parent.request(BackpressureUtils.addCap(BackpressureUtils.multiplyCap(n, (long) parent.size), BackpressureUtils.multiplyCap((long) (parent.skip - parent.size), n - 1)));
                    }
                }
            }
        }

        public WindowSkip(Subscriber<? super Observable<T>> actual2, int size2, int skip2) {
            this.actual = actual2;
            this.size = size2;
            this.skip = skip2;
            add(this.cancel);
            request(0);
        }

        public void onNext(T t) {
            int i = this.index;
            Subject<T, T> w = this.window;
            if (i == 0) {
                this.wip.getAndIncrement();
                w = UnicastSubject.create(this.size, this);
                this.window = w;
                this.actual.onNext(w);
            }
            int i2 = i + 1;
            if (w != null) {
                w.onNext(t);
            }
            if (i2 == this.size) {
                this.index = i2;
                this.window = null;
                w.onCompleted();
            } else if (i2 == this.skip) {
                this.index = 0;
            } else {
                this.index = i2;
            }
        }

        public void onError(Throwable e) {
            Subject<T, T> w = this.window;
            if (w != null) {
                this.window = null;
                w.onError(e);
            }
            this.actual.onError(e);
        }

        public void onCompleted() {
            Subject<T, T> w = this.window;
            if (w != null) {
                this.window = null;
                w.onCompleted();
            }
            this.actual.onCompleted();
        }

        /* access modifiers changed from: 0000 */
        public Producer createProducer() {
            return new WindowSkipProducer();
        }

        public void call() {
            if (this.wip.decrementAndGet() == 0) {
                unsubscribe();
            }
        }
    }

    public OperatorWindowWithSize(int size2, int skip2) {
        this.size = size2;
        this.skip = skip2;
    }

    public Subscriber<? super T> call(Subscriber<? super Observable<T>> child) {
        if (this.skip == this.size) {
            WindowExact<T> parent = new WindowExact<>(child, this.size);
            child.add(parent.cancel);
            child.setProducer(parent.createProducer());
            return parent;
        } else if (this.skip > this.size) {
            WindowSkip<T> parent2 = new WindowSkip<>(child, this.size, this.skip);
            child.add(parent2.cancel);
            child.setProducer(parent2.createProducer());
            return parent2;
        } else {
            WindowOverlap<T> parent3 = new WindowOverlap<>(child, this.size, this.skip);
            child.add(parent3.cancel);
            child.setProducer(parent3.createProducer());
            return parent3;
        }
    }
}
