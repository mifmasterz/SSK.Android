package p008rx.internal.util.unsafe;

import p008rx.internal.util.SuppressAnimalSniffer;

@SuppressAnimalSniffer
/* renamed from: rx.internal.util.unsafe.SpmcArrayQueueProducerIndexCacheField */
/* compiled from: SpmcArrayQueue */
abstract class SpmcArrayQueueProducerIndexCacheField<E> extends SpmcArrayQueueMidPad<E> {
    private volatile long producerIndexCache;

    public SpmcArrayQueueProducerIndexCacheField(int capacity) {
        super(capacity);
    }

    /* access modifiers changed from: protected */
    public final long lvProducerIndexCache() {
        return this.producerIndexCache;
    }

    /* access modifiers changed from: protected */
    public final void svProducerIndexCache(long v) {
        this.producerIndexCache = v;
    }
}
