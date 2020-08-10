package p005io.reactivex.internal.subscribers;

import java.util.Queue;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscription;
import p005io.reactivex.FlowableSubscriber;
import p005io.reactivex.internal.subscriptions.SubscriptionHelper;
import p005io.reactivex.internal.util.NotificationLite;

/* renamed from: io.reactivex.internal.subscribers.BlockingSubscriber */
public final class BlockingSubscriber<T> extends AtomicReference<Subscription> implements FlowableSubscriber<T>, Subscription {
    public static final Object TERMINATED = new Object();
    private static final long serialVersionUID = -4875965440900746268L;
    final Queue<Object> queue;

    public BlockingSubscriber(Queue<Object> queue2) {
        this.queue = queue2;
    }

    public void onSubscribe(Subscription s) {
        if (SubscriptionHelper.setOnce(this, s)) {
            this.queue.offer(NotificationLite.subscription(this));
        }
    }

    public void onNext(T t) {
        this.queue.offer(NotificationLite.next(t));
    }

    public void onError(Throwable t) {
        this.queue.offer(NotificationLite.error(t));
    }

    public void onComplete() {
        this.queue.offer(NotificationLite.complete());
    }

    public void request(long n) {
        ((Subscription) get()).request(n);
    }

    public void cancel() {
        if (SubscriptionHelper.cancel(this)) {
            this.queue.offer(TERMINATED);
        }
    }

    public boolean isCancelled() {
        return get() == SubscriptionHelper.CANCELLED;
    }
}
