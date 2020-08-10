package p005io.reactivex.internal.util;

import java.util.Queue;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import p005io.reactivex.Observer;
import p005io.reactivex.disposables.Disposable;
import p005io.reactivex.exceptions.Exceptions;
import p005io.reactivex.exceptions.MissingBackpressureException;
import p005io.reactivex.functions.BooleanSupplier;
import p005io.reactivex.internal.fuseable.SimplePlainQueue;
import p005io.reactivex.internal.fuseable.SimpleQueue;
import p005io.reactivex.internal.queue.SpscArrayQueue;
import p005io.reactivex.internal.queue.SpscLinkedArrayQueue;

/* renamed from: io.reactivex.internal.util.QueueDrainHelper */
public final class QueueDrainHelper {
    static final long COMPLETED_MASK = Long.MIN_VALUE;
    static final long REQUESTED_MASK = Long.MAX_VALUE;

    private QueueDrainHelper() {
        throw new IllegalStateException("No instances!");
    }

    public static <T, U> void drainMaxLoop(SimplePlainQueue<T> q, Subscriber<? super U> a, boolean delayError, Disposable dispose, QueueDrain<T, U> qd) {
        int missed = 1;
        while (true) {
            boolean d = qd.done();
            T v = q.poll();
            boolean empty = v == null;
            if (checkTerminated(d, empty, a, delayError, q, qd)) {
                if (dispose != null) {
                    dispose.dispose();
                }
                return;
            } else if (empty) {
                missed = qd.leave(-missed);
                if (missed == 0) {
                    return;
                }
            } else {
                long r = qd.requested();
                if (r == 0) {
                    q.clear();
                    if (dispose != null) {
                        dispose.dispose();
                    }
                    a.onError(new MissingBackpressureException("Could not emit value due to lack of requests."));
                    return;
                } else if (qd.accept(a, v) && r != REQUESTED_MASK) {
                    qd.produced(1);
                }
            }
        }
    }

    public static <T, U> boolean checkTerminated(boolean d, boolean empty, Subscriber<?> s, boolean delayError, SimpleQueue<?> q, QueueDrain<T, U> qd) {
        if (qd.cancelled()) {
            q.clear();
            return true;
        }
        if (d) {
            if (!delayError) {
                Throwable err = qd.error();
                if (err != null) {
                    q.clear();
                    s.onError(err);
                    return true;
                } else if (empty) {
                    s.onComplete();
                    return true;
                }
            } else if (empty) {
                Throwable err2 = qd.error();
                if (err2 != null) {
                    s.onError(err2);
                } else {
                    s.onComplete();
                }
                return true;
            }
        }
        return false;
    }

    public static <T, U> void drainLoop(SimplePlainQueue<T> q, Observer<? super U> a, boolean delayError, Disposable dispose, ObservableQueueDrain<T, U> qd) {
        ObservableQueueDrain<T, U> observableQueueDrain = qd;
        int missed = 1;
        do {
            int missed2 = missed;
            if (!checkTerminated(qd.done(), q.isEmpty(), a, delayError, q, dispose, observableQueueDrain)) {
                while (true) {
                    boolean d = qd.done();
                    T v = q.poll();
                    boolean empty = v == null;
                    if (!checkTerminated(d, empty, a, delayError, q, dispose, observableQueueDrain)) {
                        if (empty) {
                            missed = observableQueueDrain.leave(-missed2);
                        } else {
                            observableQueueDrain.accept(a, v);
                        }
                    } else {
                        return;
                    }
                }
            } else {
                return;
            }
        } while (missed != 0);
    }

    public static <T, U> boolean checkTerminated(boolean d, boolean empty, Observer<?> s, boolean delayError, SimpleQueue<?> q, Disposable disposable, ObservableQueueDrain<T, U> qd) {
        if (qd.cancelled()) {
            q.clear();
            disposable.dispose();
            return true;
        }
        if (d) {
            if (!delayError) {
                Throwable err = qd.error();
                if (err != null) {
                    q.clear();
                    if (disposable != null) {
                        disposable.dispose();
                    }
                    s.onError(err);
                    return true;
                } else if (empty) {
                    if (disposable != null) {
                        disposable.dispose();
                    }
                    s.onComplete();
                    return true;
                }
            } else if (empty) {
                if (disposable != null) {
                    disposable.dispose();
                }
                Throwable err2 = qd.error();
                if (err2 != null) {
                    s.onError(err2);
                } else {
                    s.onComplete();
                }
                return true;
            }
        }
        return false;
    }

    public static <T> SimpleQueue<T> createQueue(int capacityHint) {
        if (capacityHint < 0) {
            return new SpscLinkedArrayQueue(-capacityHint);
        }
        return new SpscArrayQueue(capacityHint);
    }

    public static void request(Subscription s, int prefetch) {
        s.request(prefetch < 0 ? REQUESTED_MASK : (long) prefetch);
    }

    public static <T> boolean postCompleteRequest(long n, Subscriber<? super T> actual, Queue<T> queue, AtomicLong state, BooleanSupplier isCancelled) {
        long r;
        AtomicLong atomicLong;
        long j = n;
        do {
            r = state.get();
            atomicLong = state;
        } while (!atomicLong.compareAndSet(r, (r & COMPLETED_MASK) | BackpressureHelper.addCap(REQUESTED_MASK & r, j)));
        if (r != COMPLETED_MASK) {
            return false;
        }
        postCompleteDrain(j | COMPLETED_MASK, actual, queue, atomicLong, isCancelled);
        return true;
    }

    static boolean isCancelled(BooleanSupplier cancelled) {
        try {
            return cancelled.getAsBoolean();
        } catch (Throwable ex) {
            Exceptions.throwIfFatal(ex);
            return true;
        }
    }

    static <T> boolean postCompleteDrain(long n, Subscriber<? super T> actual, Queue<T> queue, AtomicLong state, BooleanSupplier isCancelled) {
        long e = n & COMPLETED_MASK;
        while (true) {
            if (e != n) {
                if (isCancelled(isCancelled)) {
                    return true;
                }
                T t = queue.poll();
                if (t == null) {
                    actual.onComplete();
                    return true;
                }
                actual.onNext(t);
                e++;
            } else if (isCancelled(isCancelled)) {
                return true;
            } else {
                if (queue.isEmpty()) {
                    actual.onComplete();
                    return true;
                }
                n = state.get();
                if (n == e) {
                    n = state.addAndGet(-(e & REQUESTED_MASK));
                    if ((REQUESTED_MASK & n) == 0) {
                        return false;
                    }
                    e = n & COMPLETED_MASK;
                } else {
                    continue;
                }
            }
        }
    }

    public static <T> void postComplete(Subscriber<? super T> actual, Queue<T> queue, AtomicLong state, BooleanSupplier isCancelled) {
        long r;
        long u;
        AtomicLong atomicLong;
        if (queue.isEmpty()) {
            actual.onComplete();
        } else if (!postCompleteDrain(state.get(), actual, queue, state, isCancelled)) {
            do {
                r = state.get();
                if ((r & COMPLETED_MASK) == 0) {
                    u = COMPLETED_MASK | r;
                    atomicLong = state;
                } else {
                    return;
                }
            } while (!atomicLong.compareAndSet(r, u));
            if (r != 0) {
                postCompleteDrain(u, actual, queue, atomicLong, isCancelled);
            }
        }
    }
}
