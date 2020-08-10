package p005io.reactivex.internal.operators.flowable;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import p005io.reactivex.Flowable;
import p005io.reactivex.FlowableSubscriber;
import p005io.reactivex.Scheduler;
import p005io.reactivex.internal.queue.SpscLinkedArrayQueue;
import p005io.reactivex.internal.subscriptions.SubscriptionHelper;
import p005io.reactivex.internal.util.BackpressureHelper;

/* renamed from: io.reactivex.internal.operators.flowable.FlowableTakeLastTimed */
public final class FlowableTakeLastTimed<T> extends AbstractFlowableWithUpstream<T, T> {
    final int bufferSize;
    final long count;
    final boolean delayError;
    final Scheduler scheduler;
    final long time;
    final TimeUnit unit;

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableTakeLastTimed$TakeLastTimedSubscriber */
    static final class TakeLastTimedSubscriber<T> extends AtomicInteger implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = -5677354903406201275L;
        final Subscriber<? super T> actual;
        volatile boolean cancelled;
        final long count;
        final boolean delayError;
        volatile boolean done;
        Throwable error;
        final SpscLinkedArrayQueue<Object> queue;
        final AtomicLong requested = new AtomicLong();

        /* renamed from: s */
        Subscription f215s;
        final Scheduler scheduler;
        final long time;
        final TimeUnit unit;

        TakeLastTimedSubscriber(Subscriber<? super T> actual2, long count2, long time2, TimeUnit unit2, Scheduler scheduler2, int bufferSize, boolean delayError2) {
            this.actual = actual2;
            this.count = count2;
            this.time = time2;
            this.unit = unit2;
            this.scheduler = scheduler2;
            this.queue = new SpscLinkedArrayQueue<>(bufferSize);
            this.delayError = delayError2;
        }

        public void onSubscribe(Subscription s) {
            if (SubscriptionHelper.validate(this.f215s, s)) {
                this.f215s = s;
                this.actual.onSubscribe(this);
                s.request(Long.MAX_VALUE);
            }
        }

        public void onNext(T t) {
            SpscLinkedArrayQueue<Object> q = this.queue;
            long now = this.scheduler.now(this.unit);
            q.offer(Long.valueOf(now), t);
            trim(now, q);
        }

        public void onError(Throwable t) {
            if (this.delayError) {
                trim(this.scheduler.now(this.unit), this.queue);
            }
            this.error = t;
            this.done = true;
            drain();
        }

        public void onComplete() {
            trim(this.scheduler.now(this.unit), this.queue);
            this.done = true;
            drain();
        }

        /* access modifiers changed from: 0000 */
        public void trim(long now, SpscLinkedArrayQueue<Object> q) {
            long time2 = this.time;
            long c = this.count;
            boolean unbounded = c == Long.MAX_VALUE;
            while (!q.isEmpty()) {
                if (((Long) q.peek()).longValue() < now - time2 || (!unbounded && ((long) (q.size() >> 1)) > c)) {
                    q.poll();
                    q.poll();
                } else {
                    return;
                }
            }
        }

        public void request(long n) {
            if (SubscriptionHelper.validate(n)) {
                BackpressureHelper.add(this.requested, n);
                drain();
            }
        }

        public void cancel() {
            if (!this.cancelled) {
                this.cancelled = true;
                this.f215s.cancel();
                if (getAndIncrement() == 0) {
                    this.queue.clear();
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public void drain() {
            if (getAndIncrement() == 0) {
                int missed = 1;
                Subscriber<? super T> a = this.actual;
                SpscLinkedArrayQueue<Object> q = this.queue;
                boolean delayError2 = this.delayError;
                do {
                    if (this.done) {
                        if (!checkTerminated(q.isEmpty(), a, delayError2)) {
                            long r = this.requested.get();
                            long e = 0;
                            while (true) {
                                if (!checkTerminated(q.peek() == null, a, delayError2)) {
                                    if (r != e) {
                                        q.poll();
                                        a.onNext(q.poll());
                                        e++;
                                    } else if (e != 0) {
                                        BackpressureHelper.produced(this.requested, e);
                                    }
                                } else {
                                    return;
                                }
                            }
                        } else {
                            return;
                        }
                    }
                    missed = addAndGet(-missed);
                } while (missed != 0);
            }
        }

        /* access modifiers changed from: 0000 */
        public boolean checkTerminated(boolean empty, Subscriber<? super T> a, boolean delayError2) {
            if (this.cancelled) {
                this.queue.clear();
                return true;
            }
            if (!delayError2) {
                Throwable e = this.error;
                if (e != null) {
                    this.queue.clear();
                    a.onError(e);
                    return true;
                } else if (empty) {
                    a.onComplete();
                    return true;
                }
            } else if (empty) {
                Throwable e2 = this.error;
                if (e2 != null) {
                    a.onError(e2);
                } else {
                    a.onComplete();
                }
                return true;
            }
            return false;
        }
    }

    public FlowableTakeLastTimed(Flowable<T> source, long count2, long time2, TimeUnit unit2, Scheduler scheduler2, int bufferSize2, boolean delayError2) {
        super(source);
        this.count = count2;
        this.time = time2;
        this.unit = unit2;
        this.scheduler = scheduler2;
        this.bufferSize = bufferSize2;
        this.delayError = delayError2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> s) {
        Flowable flowable = this.source;
        TakeLastTimedSubscriber takeLastTimedSubscriber = new TakeLastTimedSubscriber(s, this.count, this.time, this.unit, this.scheduler, this.bufferSize, this.delayError);
        flowable.subscribe((FlowableSubscriber<? super T>) takeLastTimedSubscriber);
    }
}
