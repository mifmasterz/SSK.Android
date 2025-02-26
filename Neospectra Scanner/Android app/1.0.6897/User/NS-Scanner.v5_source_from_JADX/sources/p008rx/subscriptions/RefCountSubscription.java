package p008rx.subscriptions;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import p008rx.Subscription;

/* renamed from: rx.subscriptions.RefCountSubscription */
public final class RefCountSubscription implements Subscription {
    static final State EMPTY_STATE = new State(false, 0);
    private final Subscription actual;
    final AtomicReference<State> state = new AtomicReference<>(EMPTY_STATE);

    /* renamed from: rx.subscriptions.RefCountSubscription$InnerSubscription */
    static final class InnerSubscription extends AtomicInteger implements Subscription {
        private static final long serialVersionUID = 7005765588239987643L;
        final RefCountSubscription parent;

        public InnerSubscription(RefCountSubscription parent2) {
            this.parent = parent2;
        }

        public void unsubscribe() {
            if (compareAndSet(0, 1)) {
                this.parent.unsubscribeAChild();
            }
        }

        public boolean isUnsubscribed() {
            return get() != 0;
        }
    }

    /* renamed from: rx.subscriptions.RefCountSubscription$State */
    static final class State {
        final int children;
        final boolean isUnsubscribed;

        State(boolean u, int c) {
            this.isUnsubscribed = u;
            this.children = c;
        }

        /* access modifiers changed from: 0000 */
        public State addChild() {
            return new State(this.isUnsubscribed, this.children + 1);
        }

        /* access modifiers changed from: 0000 */
        public State removeChild() {
            return new State(this.isUnsubscribed, this.children - 1);
        }

        /* access modifiers changed from: 0000 */
        public State unsubscribe() {
            return new State(true, this.children);
        }
    }

    public RefCountSubscription(Subscription s) {
        if (s == null) {
            throw new IllegalArgumentException("s");
        }
        this.actual = s;
    }

    public Subscription get() {
        State oldState;
        AtomicReference<State> localState = this.state;
        do {
            oldState = (State) localState.get();
            if (oldState.isUnsubscribed) {
                return Subscriptions.unsubscribed();
            }
        } while (!localState.compareAndSet(oldState, oldState.addChild()));
        return new InnerSubscription(this);
    }

    public boolean isUnsubscribed() {
        return ((State) this.state.get()).isUnsubscribed;
    }

    public void unsubscribe() {
        State oldState;
        State newState;
        AtomicReference<State> localState = this.state;
        do {
            oldState = (State) localState.get();
            if (!oldState.isUnsubscribed) {
                newState = oldState.unsubscribe();
            } else {
                return;
            }
        } while (!localState.compareAndSet(oldState, newState));
        unsubscribeActualIfApplicable(newState);
    }

    private void unsubscribeActualIfApplicable(State state2) {
        if (state2.isUnsubscribed && state2.children == 0) {
            this.actual.unsubscribe();
        }
    }

    /* access modifiers changed from: 0000 */
    public void unsubscribeAChild() {
        State oldState;
        State newState;
        AtomicReference<State> localState = this.state;
        do {
            oldState = (State) localState.get();
            newState = oldState.removeChild();
        } while (!localState.compareAndSet(oldState, newState));
        unsubscribeActualIfApplicable(newState);
    }
}
