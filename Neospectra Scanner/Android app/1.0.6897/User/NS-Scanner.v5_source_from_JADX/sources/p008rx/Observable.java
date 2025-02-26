package p008rx;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import p008rx.AsyncEmitter.BackpressureMode;
import p008rx.BackpressureOverflow.Strategy;
import p008rx.annotations.Beta;
import p008rx.annotations.Experimental;
import p008rx.exceptions.Exceptions;
import p008rx.exceptions.OnErrorFailedException;
import p008rx.functions.Action0;
import p008rx.functions.Action1;
import p008rx.functions.Action2;
import p008rx.functions.Actions;
import p008rx.functions.Func0;
import p008rx.functions.Func1;
import p008rx.functions.Func2;
import p008rx.functions.Func3;
import p008rx.functions.Func4;
import p008rx.functions.Func5;
import p008rx.functions.Func6;
import p008rx.functions.Func7;
import p008rx.functions.Func8;
import p008rx.functions.Func9;
import p008rx.functions.FuncN;
import p008rx.functions.Functions;
import p008rx.internal.operators.CachedObservable;
import p008rx.internal.operators.EmptyObservableHolder;
import p008rx.internal.operators.NeverObservableHolder;
import p008rx.internal.operators.OnSubscribeAmb;
import p008rx.internal.operators.OnSubscribeCollect;
import p008rx.internal.operators.OnSubscribeCombineLatest;
import p008rx.internal.operators.OnSubscribeConcatMap;
import p008rx.internal.operators.OnSubscribeDefer;
import p008rx.internal.operators.OnSubscribeDelaySubscription;
import p008rx.internal.operators.OnSubscribeDelaySubscriptionOther;
import p008rx.internal.operators.OnSubscribeDelaySubscriptionWithSelector;
import p008rx.internal.operators.OnSubscribeDetach;
import p008rx.internal.operators.OnSubscribeDoOnEach;
import p008rx.internal.operators.OnSubscribeFilter;
import p008rx.internal.operators.OnSubscribeFlattenIterable;
import p008rx.internal.operators.OnSubscribeFromArray;
import p008rx.internal.operators.OnSubscribeFromAsyncEmitter;
import p008rx.internal.operators.OnSubscribeFromCallable;
import p008rx.internal.operators.OnSubscribeFromEmitter;
import p008rx.internal.operators.OnSubscribeFromIterable;
import p008rx.internal.operators.OnSubscribeGroupJoin;
import p008rx.internal.operators.OnSubscribeJoin;
import p008rx.internal.operators.OnSubscribeLift;
import p008rx.internal.operators.OnSubscribeMap;
import p008rx.internal.operators.OnSubscribeRange;
import p008rx.internal.operators.OnSubscribeRedo;
import p008rx.internal.operators.OnSubscribeReduce;
import p008rx.internal.operators.OnSubscribeReduceSeed;
import p008rx.internal.operators.OnSubscribeSingle;
import p008rx.internal.operators.OnSubscribeSkipTimed;
import p008rx.internal.operators.OnSubscribeTakeLastOne;
import p008rx.internal.operators.OnSubscribeThrow;
import p008rx.internal.operators.OnSubscribeTimerOnce;
import p008rx.internal.operators.OnSubscribeTimerPeriodically;
import p008rx.internal.operators.OnSubscribeToMap;
import p008rx.internal.operators.OnSubscribeToMultimap;
import p008rx.internal.operators.OnSubscribeToObservableFuture;
import p008rx.internal.operators.OnSubscribeUsing;
import p008rx.internal.operators.OperatorAll;
import p008rx.internal.operators.OperatorAny;
import p008rx.internal.operators.OperatorAsObservable;
import p008rx.internal.operators.OperatorBufferWithSingleObservable;
import p008rx.internal.operators.OperatorBufferWithSize;
import p008rx.internal.operators.OperatorBufferWithStartEndObservable;
import p008rx.internal.operators.OperatorBufferWithTime;
import p008rx.internal.operators.OperatorCast;
import p008rx.internal.operators.OperatorDebounceWithSelector;
import p008rx.internal.operators.OperatorDebounceWithTime;
import p008rx.internal.operators.OperatorDelay;
import p008rx.internal.operators.OperatorDelayWithSelector;
import p008rx.internal.operators.OperatorDematerialize;
import p008rx.internal.operators.OperatorDistinct;
import p008rx.internal.operators.OperatorDistinctUntilChanged;
import p008rx.internal.operators.OperatorDoAfterTerminate;
import p008rx.internal.operators.OperatorDoOnRequest;
import p008rx.internal.operators.OperatorDoOnSubscribe;
import p008rx.internal.operators.OperatorDoOnUnsubscribe;
import p008rx.internal.operators.OperatorEagerConcatMap;
import p008rx.internal.operators.OperatorElementAt;
import p008rx.internal.operators.OperatorGroupBy;
import p008rx.internal.operators.OperatorIgnoreElements;
import p008rx.internal.operators.OperatorMapNotification;
import p008rx.internal.operators.OperatorMapPair;
import p008rx.internal.operators.OperatorMaterialize;
import p008rx.internal.operators.OperatorMerge;
import p008rx.internal.operators.OperatorObserveOn;
import p008rx.internal.operators.OperatorOnBackpressureBuffer;
import p008rx.internal.operators.OperatorOnBackpressureDrop;
import p008rx.internal.operators.OperatorOnBackpressureLatest;
import p008rx.internal.operators.OperatorOnErrorResumeNextViaFunction;
import p008rx.internal.operators.OperatorPublish;
import p008rx.internal.operators.OperatorReplay;
import p008rx.internal.operators.OperatorRetryWithPredicate;
import p008rx.internal.operators.OperatorSampleWithObservable;
import p008rx.internal.operators.OperatorSampleWithTime;
import p008rx.internal.operators.OperatorScan;
import p008rx.internal.operators.OperatorSequenceEqual;
import p008rx.internal.operators.OperatorSerialize;
import p008rx.internal.operators.OperatorSingle;
import p008rx.internal.operators.OperatorSkip;
import p008rx.internal.operators.OperatorSkipLast;
import p008rx.internal.operators.OperatorSkipLastTimed;
import p008rx.internal.operators.OperatorSkipUntil;
import p008rx.internal.operators.OperatorSkipWhile;
import p008rx.internal.operators.OperatorSubscribeOn;
import p008rx.internal.operators.OperatorSwitch;
import p008rx.internal.operators.OperatorSwitchIfEmpty;
import p008rx.internal.operators.OperatorTake;
import p008rx.internal.operators.OperatorTakeLast;
import p008rx.internal.operators.OperatorTakeLastTimed;
import p008rx.internal.operators.OperatorTakeTimed;
import p008rx.internal.operators.OperatorTakeUntil;
import p008rx.internal.operators.OperatorTakeUntilPredicate;
import p008rx.internal.operators.OperatorTakeWhile;
import p008rx.internal.operators.OperatorThrottleFirst;
import p008rx.internal.operators.OperatorTimeInterval;
import p008rx.internal.operators.OperatorTimeout;
import p008rx.internal.operators.OperatorTimeoutWithSelector;
import p008rx.internal.operators.OperatorTimestamp;
import p008rx.internal.operators.OperatorToObservableList;
import p008rx.internal.operators.OperatorToObservableSortedList;
import p008rx.internal.operators.OperatorUnsubscribeOn;
import p008rx.internal.operators.OperatorWindowWithObservable;
import p008rx.internal.operators.OperatorWindowWithObservableFactory;
import p008rx.internal.operators.OperatorWindowWithSize;
import p008rx.internal.operators.OperatorWindowWithStartEndObservable;
import p008rx.internal.operators.OperatorWindowWithTime;
import p008rx.internal.operators.OperatorWithLatestFrom;
import p008rx.internal.operators.OperatorWithLatestFromMany;
import p008rx.internal.operators.OperatorZip;
import p008rx.internal.operators.OperatorZipIterable;
import p008rx.internal.util.ActionNotificationObserver;
import p008rx.internal.util.ActionObserver;
import p008rx.internal.util.ActionSubscriber;
import p008rx.internal.util.InternalObservableUtils;
import p008rx.internal.util.ObserverSubscriber;
import p008rx.internal.util.RxRingBuffer;
import p008rx.internal.util.ScalarSynchronousObservable;
import p008rx.internal.util.UtilityFunctions;
import p008rx.observables.AsyncOnSubscribe;
import p008rx.observables.BlockingObservable;
import p008rx.observables.ConnectableObservable;
import p008rx.observables.GroupedObservable;
import p008rx.observables.SyncOnSubscribe;
import p008rx.observers.SafeSubscriber;
import p008rx.plugins.RxJavaHooks;
import p008rx.schedulers.Schedulers;
import p008rx.schedulers.TimeInterval;
import p008rx.schedulers.Timestamped;

/* renamed from: rx.Observable */
public class Observable<T> {
    final OnSubscribe<T> onSubscribe;

    /* renamed from: rx.Observable$OnSubscribe */
    public interface OnSubscribe<T> extends Action1<Subscriber<? super T>> {
    }

    /* renamed from: rx.Observable$OnSubscribeExtend */
    static final class OnSubscribeExtend<T> implements OnSubscribe<T> {
        final Observable<T> parent;

        OnSubscribeExtend(Observable<T> parent2) {
            this.parent = parent2;
        }

        public void call(Subscriber<? super T> subscriber) {
            subscriber.add(Observable.subscribe(subscriber, this.parent));
        }
    }

    /* renamed from: rx.Observable$Operator */
    public interface Operator<R, T> extends Func1<Subscriber<? super R>, Subscriber<? super T>> {
    }

    /* renamed from: rx.Observable$Transformer */
    public interface Transformer<T, R> extends Func1<Observable<T>, Observable<R>> {
    }

    protected Observable(OnSubscribe<T> f) {
        this.onSubscribe = f;
    }

    public static <T> Observable<T> create(OnSubscribe<T> f) {
        return new Observable<>(RxJavaHooks.onCreate(f));
    }

    public static <S, T> Observable<T> create(SyncOnSubscribe<S, T> syncOnSubscribe) {
        return create((OnSubscribe<T>) syncOnSubscribe);
    }

    @Experimental
    public static <S, T> Observable<T> create(AsyncOnSubscribe<S, T> asyncOnSubscribe) {
        return create((OnSubscribe<T>) asyncOnSubscribe);
    }

    public final <R> Observable<R> lift(Operator<? extends R, ? super T> operator) {
        return create((OnSubscribe<T>) new OnSubscribeLift<T>(this.onSubscribe, operator));
    }

    public <R> Observable<R> compose(Transformer<? super T, ? extends R> transformer) {
        return (Observable) transformer.call(this);
    }

    @Experimental
    /* renamed from: to */
    public final <R> R mo25233to(Func1<? super Observable<T>, R> converter) {
        return converter.call(this);
    }

    public Single<T> toSingle() {
        return new Single<>((p008rx.Single.OnSubscribe<T>) OnSubscribeSingle.create(this));
    }

    @Beta
    public Completable toCompletable() {
        return Completable.fromObservable(this);
    }

    public static <T> Observable<T> amb(Iterable<? extends Observable<? extends T>> sources) {
        return create(OnSubscribeAmb.amb(sources));
    }

    public static <T> Observable<T> amb(Observable<? extends T> o1, Observable<? extends T> o2) {
        return create(OnSubscribeAmb.amb(o1, o2));
    }

    public static <T> Observable<T> amb(Observable<? extends T> o1, Observable<? extends T> o2, Observable<? extends T> o3) {
        return create(OnSubscribeAmb.amb(o1, o2, o3));
    }

    public static <T> Observable<T> amb(Observable<? extends T> o1, Observable<? extends T> o2, Observable<? extends T> o3, Observable<? extends T> o4) {
        return create(OnSubscribeAmb.amb(o1, o2, o3, o4));
    }

    public static <T> Observable<T> amb(Observable<? extends T> o1, Observable<? extends T> o2, Observable<? extends T> o3, Observable<? extends T> o4, Observable<? extends T> o5) {
        return create(OnSubscribeAmb.amb(o1, o2, o3, o4, o5));
    }

    public static <T> Observable<T> amb(Observable<? extends T> o1, Observable<? extends T> o2, Observable<? extends T> o3, Observable<? extends T> o4, Observable<? extends T> o5, Observable<? extends T> o6) {
        return create(OnSubscribeAmb.amb(o1, o2, o3, o4, o5, o6));
    }

    public static <T> Observable<T> amb(Observable<? extends T> o1, Observable<? extends T> o2, Observable<? extends T> o3, Observable<? extends T> o4, Observable<? extends T> o5, Observable<? extends T> o6, Observable<? extends T> o7) {
        return create(OnSubscribeAmb.amb(o1, o2, o3, o4, o5, o6, o7));
    }

    public static <T> Observable<T> amb(Observable<? extends T> o1, Observable<? extends T> o2, Observable<? extends T> o3, Observable<? extends T> o4, Observable<? extends T> o5, Observable<? extends T> o6, Observable<? extends T> o7, Observable<? extends T> o8) {
        return create(OnSubscribeAmb.amb(o1, o2, o3, o4, o5, o6, o7, o8));
    }

    public static <T> Observable<T> amb(Observable<? extends T> o1, Observable<? extends T> o2, Observable<? extends T> o3, Observable<? extends T> o4, Observable<? extends T> o5, Observable<? extends T> o6, Observable<? extends T> o7, Observable<? extends T> o8, Observable<? extends T> o9) {
        return create(OnSubscribeAmb.amb(o1, o2, o3, o4, o5, o6, o7, o8, o9));
    }

    public static <T1, T2, R> Observable<R> combineLatest(Observable<? extends T1> o1, Observable<? extends T2> o2, Func2<? super T1, ? super T2, ? extends R> combineFunction) {
        return combineLatest(Arrays.asList(new Observable[]{o1, o2}), Functions.fromFunc(combineFunction));
    }

    public static <T1, T2, T3, R> Observable<R> combineLatest(Observable<? extends T1> o1, Observable<? extends T2> o2, Observable<? extends T3> o3, Func3<? super T1, ? super T2, ? super T3, ? extends R> combineFunction) {
        return combineLatest(Arrays.asList(new Observable[]{o1, o2, o3}), Functions.fromFunc(combineFunction));
    }

    public static <T1, T2, T3, T4, R> Observable<R> combineLatest(Observable<? extends T1> o1, Observable<? extends T2> o2, Observable<? extends T3> o3, Observable<? extends T4> o4, Func4<? super T1, ? super T2, ? super T3, ? super T4, ? extends R> combineFunction) {
        return combineLatest(Arrays.asList(new Observable[]{o1, o2, o3, o4}), Functions.fromFunc(combineFunction));
    }

    public static <T1, T2, T3, T4, T5, R> Observable<R> combineLatest(Observable<? extends T1> o1, Observable<? extends T2> o2, Observable<? extends T3> o3, Observable<? extends T4> o4, Observable<? extends T5> o5, Func5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? extends R> combineFunction) {
        return combineLatest(Arrays.asList(new Observable[]{o1, o2, o3, o4, o5}), Functions.fromFunc(combineFunction));
    }

    public static <T1, T2, T3, T4, T5, T6, R> Observable<R> combineLatest(Observable<? extends T1> o1, Observable<? extends T2> o2, Observable<? extends T3> o3, Observable<? extends T4> o4, Observable<? extends T5> o5, Observable<? extends T6> o6, Func6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? extends R> combineFunction) {
        return combineLatest(Arrays.asList(new Observable[]{o1, o2, o3, o4, o5, o6}), Functions.fromFunc(combineFunction));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, R> Observable<R> combineLatest(Observable<? extends T1> o1, Observable<? extends T2> o2, Observable<? extends T3> o3, Observable<? extends T4> o4, Observable<? extends T5> o5, Observable<? extends T6> o6, Observable<? extends T7> o7, Func7<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? extends R> combineFunction) {
        return combineLatest(Arrays.asList(new Observable[]{o1, o2, o3, o4, o5, o6, o7}), Functions.fromFunc(combineFunction));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, R> Observable<R> combineLatest(Observable<? extends T1> o1, Observable<? extends T2> o2, Observable<? extends T3> o3, Observable<? extends T4> o4, Observable<? extends T5> o5, Observable<? extends T6> o6, Observable<? extends T7> o7, Observable<? extends T8> o8, Func8<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? extends R> combineFunction) {
        return combineLatest(Arrays.asList(new Observable[]{o1, o2, o3, o4, o5, o6, o7, o8}), Functions.fromFunc(combineFunction));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, R> Observable<R> combineLatest(Observable<? extends T1> o1, Observable<? extends T2> o2, Observable<? extends T3> o3, Observable<? extends T4> o4, Observable<? extends T5> o5, Observable<? extends T6> o6, Observable<? extends T7> o7, Observable<? extends T8> o8, Observable<? extends T9> o9, Func9<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? extends R> combineFunction) {
        return combineLatest(Arrays.asList(new Observable[]{o1, o2, o3, o4, o5, o6, o7, o8, o9}), Functions.fromFunc(combineFunction));
    }

    public static <T, R> Observable<R> combineLatest(List<? extends Observable<? extends T>> sources, FuncN<? extends R> combineFunction) {
        return create((OnSubscribe<T>) new OnSubscribeCombineLatest<T>(sources, combineFunction));
    }

    public static <T, R> Observable<R> combineLatest(Iterable<? extends Observable<? extends T>> sources, FuncN<? extends R> combineFunction) {
        return create((OnSubscribe<T>) new OnSubscribeCombineLatest<T>(sources, combineFunction));
    }

    public static <T, R> Observable<R> combineLatestDelayError(Iterable<? extends Observable<? extends T>> sources, FuncN<? extends R> combineFunction) {
        OnSubscribeCombineLatest onSubscribeCombineLatest = new OnSubscribeCombineLatest(null, sources, combineFunction, RxRingBuffer.SIZE, true);
        return create((OnSubscribe<T>) onSubscribeCombineLatest);
    }

    public static <T> Observable<T> concat(Iterable<? extends Observable<? extends T>> sequences) {
        return concat(from(sequences));
    }

    public static <T> Observable<T> concat(Observable<? extends Observable<? extends T>> observables) {
        return observables.concatMap(UtilityFunctions.identity());
    }

    public static <T> Observable<T> concat(Observable<? extends T> t1, Observable<? extends T> t2) {
        return concat(just(t1, t2));
    }

    public static <T> Observable<T> concat(Observable<? extends T> t1, Observable<? extends T> t2, Observable<? extends T> t3) {
        return concat(just(t1, t2, t3));
    }

    public static <T> Observable<T> concat(Observable<? extends T> t1, Observable<? extends T> t2, Observable<? extends T> t3, Observable<? extends T> t4) {
        return concat(just(t1, t2, t3, t4));
    }

    public static <T> Observable<T> concat(Observable<? extends T> t1, Observable<? extends T> t2, Observable<? extends T> t3, Observable<? extends T> t4, Observable<? extends T> t5) {
        return concat(just(t1, t2, t3, t4, t5));
    }

    public static <T> Observable<T> concat(Observable<? extends T> t1, Observable<? extends T> t2, Observable<? extends T> t3, Observable<? extends T> t4, Observable<? extends T> t5, Observable<? extends T> t6) {
        return concat(just(t1, t2, t3, t4, t5, t6));
    }

    public static <T> Observable<T> concat(Observable<? extends T> t1, Observable<? extends T> t2, Observable<? extends T> t3, Observable<? extends T> t4, Observable<? extends T> t5, Observable<? extends T> t6, Observable<? extends T> t7) {
        return concat(just(t1, t2, t3, t4, t5, t6, t7));
    }

    public static <T> Observable<T> concat(Observable<? extends T> t1, Observable<? extends T> t2, Observable<? extends T> t3, Observable<? extends T> t4, Observable<? extends T> t5, Observable<? extends T> t6, Observable<? extends T> t7, Observable<? extends T> t8) {
        return concat(just(t1, t2, t3, t4, t5, t6, t7, t8));
    }

    public static <T> Observable<T> concat(Observable<? extends T> t1, Observable<? extends T> t2, Observable<? extends T> t3, Observable<? extends T> t4, Observable<? extends T> t5, Observable<? extends T> t6, Observable<? extends T> t7, Observable<? extends T> t8, Observable<? extends T> t9) {
        return concat(just(t1, t2, t3, t4, t5, t6, t7, t8, t9));
    }

    @Beta
    public static <T> Observable<T> concatDelayError(Observable<? extends Observable<? extends T>> sources) {
        return sources.concatMapDelayError(UtilityFunctions.identity());
    }

    @Beta
    public static <T> Observable<T> concatDelayError(Iterable<? extends Observable<? extends T>> sources) {
        return concatDelayError(from(sources));
    }

    @Beta
    public static <T> Observable<T> concatDelayError(Observable<? extends T> t1, Observable<? extends T> t2) {
        return concatDelayError(just(t1, t2));
    }

    @Beta
    public static <T> Observable<T> concatDelayError(Observable<? extends T> t1, Observable<? extends T> t2, Observable<? extends T> t3) {
        return concatDelayError(just(t1, t2, t3));
    }

    @Beta
    public static <T> Observable<T> concatDelayError(Observable<? extends T> t1, Observable<? extends T> t2, Observable<? extends T> t3, Observable<? extends T> t4) {
        return concatDelayError(just(t1, t2, t3, t4));
    }

    @Beta
    public static <T> Observable<T> concatDelayError(Observable<? extends T> t1, Observable<? extends T> t2, Observable<? extends T> t3, Observable<? extends T> t4, Observable<? extends T> t5) {
        return concatDelayError(just(t1, t2, t3, t4, t5));
    }

    @Beta
    public static <T> Observable<T> concatDelayError(Observable<? extends T> t1, Observable<? extends T> t2, Observable<? extends T> t3, Observable<? extends T> t4, Observable<? extends T> t5, Observable<? extends T> t6) {
        return concatDelayError(just(t1, t2, t3, t4, t5, t6));
    }

    @Beta
    public static <T> Observable<T> concatDelayError(Observable<? extends T> t1, Observable<? extends T> t2, Observable<? extends T> t3, Observable<? extends T> t4, Observable<? extends T> t5, Observable<? extends T> t6, Observable<? extends T> t7) {
        return concatDelayError(just(t1, t2, t3, t4, t5, t6, t7));
    }

    @Beta
    public static <T> Observable<T> concatDelayError(Observable<? extends T> t1, Observable<? extends T> t2, Observable<? extends T> t3, Observable<? extends T> t4, Observable<? extends T> t5, Observable<? extends T> t6, Observable<? extends T> t7, Observable<? extends T> t8) {
        return concatDelayError(just(t1, t2, t3, t4, t5, t6, t7, t8));
    }

    @Beta
    public static <T> Observable<T> concatDelayError(Observable<? extends T> t1, Observable<? extends T> t2, Observable<? extends T> t3, Observable<? extends T> t4, Observable<? extends T> t5, Observable<? extends T> t6, Observable<? extends T> t7, Observable<? extends T> t8, Observable<? extends T> t9) {
        return concatDelayError(just(t1, t2, t3, t4, t5, t6, t7, t8, t9));
    }

    public static <T> Observable<T> defer(Func0<Observable<T>> observableFactory) {
        return create((OnSubscribe<T>) new OnSubscribeDefer<T>(observableFactory));
    }

    public static <T> Observable<T> empty() {
        return EmptyObservableHolder.instance();
    }

    public static <T> Observable<T> error(Throwable exception) {
        return create((OnSubscribe<T>) new OnSubscribeThrow<T>(exception));
    }

    public static <T> Observable<T> from(Future<? extends T> future) {
        return create(OnSubscribeToObservableFuture.toObservableFuture(future));
    }

    public static <T> Observable<T> from(Future<? extends T> future, long timeout, TimeUnit unit) {
        return create(OnSubscribeToObservableFuture.toObservableFuture(future, timeout, unit));
    }

    public static <T> Observable<T> from(Future<? extends T> future, Scheduler scheduler) {
        return create(OnSubscribeToObservableFuture.toObservableFuture(future)).subscribeOn(scheduler);
    }

    public static <T> Observable<T> from(Iterable<? extends T> iterable) {
        return create((OnSubscribe<T>) new OnSubscribeFromIterable<T>(iterable));
    }

    public static <T> Observable<T> from(T[] array) {
        int n = array.length;
        if (n == 0) {
            return empty();
        }
        if (n == 1) {
            return just(array[0]);
        }
        return create((OnSubscribe<T>) new OnSubscribeFromArray<T>(array));
    }

    @Experimental
    @Deprecated
    public static <T> Observable<T> fromEmitter(Action1<AsyncEmitter<T>> emitter, BackpressureMode backpressure) {
        return create((OnSubscribe<T>) new OnSubscribeFromAsyncEmitter<T>(emitter, backpressure));
    }

    @Experimental
    public static <T> Observable<T> fromEmitter(Action1<Emitter<T>> emitter, Emitter.BackpressureMode backpressure) {
        return create((OnSubscribe<T>) new OnSubscribeFromEmitter<T>(emitter, backpressure));
    }

    public static <T> Observable<T> fromCallable(Callable<? extends T> func) {
        return create((OnSubscribe<T>) new OnSubscribeFromCallable<T>(func));
    }

    public static Observable<Long> interval(long interval, TimeUnit unit) {
        return interval(interval, interval, unit, Schedulers.computation());
    }

    public static Observable<Long> interval(long interval, TimeUnit unit, Scheduler scheduler) {
        return interval(interval, interval, unit, scheduler);
    }

    public static Observable<Long> interval(long initialDelay, long period, TimeUnit unit) {
        return interval(initialDelay, period, unit, Schedulers.computation());
    }

    public static Observable<Long> interval(long initialDelay, long period, TimeUnit unit, Scheduler scheduler) {
        OnSubscribeTimerPeriodically onSubscribeTimerPeriodically = new OnSubscribeTimerPeriodically(initialDelay, period, unit, scheduler);
        return create((OnSubscribe<T>) onSubscribeTimerPeriodically);
    }

    public static <T> Observable<T> just(T value) {
        return ScalarSynchronousObservable.create(value);
    }

    public static <T> Observable<T> just(T t1, T t2) {
        return from((T[]) new Object[]{t1, t2});
    }

    public static <T> Observable<T> just(T t1, T t2, T t3) {
        return from((T[]) new Object[]{t1, t2, t3});
    }

    public static <T> Observable<T> just(T t1, T t2, T t3, T t4) {
        return from((T[]) new Object[]{t1, t2, t3, t4});
    }

    public static <T> Observable<T> just(T t1, T t2, T t3, T t4, T t5) {
        return from((T[]) new Object[]{t1, t2, t3, t4, t5});
    }

    public static <T> Observable<T> just(T t1, T t2, T t3, T t4, T t5, T t6) {
        return from((T[]) new Object[]{t1, t2, t3, t4, t5, t6});
    }

    public static <T> Observable<T> just(T t1, T t2, T t3, T t4, T t5, T t6, T t7) {
        return from((T[]) new Object[]{t1, t2, t3, t4, t5, t6, t7});
    }

    public static <T> Observable<T> just(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8) {
        return from((T[]) new Object[]{t1, t2, t3, t4, t5, t6, t7, t8});
    }

    public static <T> Observable<T> just(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9) {
        return from((T[]) new Object[]{t1, t2, t3, t4, t5, t6, t7, t8, t9});
    }

    public static <T> Observable<T> just(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9, T t10) {
        return from((T[]) new Object[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10});
    }

    public static <T> Observable<T> merge(Iterable<? extends Observable<? extends T>> sequences) {
        return merge(from(sequences));
    }

    public static <T> Observable<T> merge(Iterable<? extends Observable<? extends T>> sequences, int maxConcurrent) {
        return merge(from(sequences), maxConcurrent);
    }

    public static <T> Observable<T> merge(Observable<? extends Observable<? extends T>> source) {
        if (source.getClass() == ScalarSynchronousObservable.class) {
            return ((ScalarSynchronousObservable) source).scalarFlatMap(UtilityFunctions.identity());
        }
        return source.lift(OperatorMerge.instance(false));
    }

    public static <T> Observable<T> merge(Observable<? extends Observable<? extends T>> source, int maxConcurrent) {
        if (source.getClass() == ScalarSynchronousObservable.class) {
            return ((ScalarSynchronousObservable) source).scalarFlatMap(UtilityFunctions.identity());
        }
        return source.lift(OperatorMerge.instance(false, maxConcurrent));
    }

    public static <T> Observable<T> merge(Observable<? extends T> t1, Observable<? extends T> t2) {
        return merge((Observable<? extends T>[]) new Observable[]{t1, t2});
    }

    public static <T> Observable<T> merge(Observable<? extends T> t1, Observable<? extends T> t2, Observable<? extends T> t3) {
        return merge((Observable<? extends T>[]) new Observable[]{t1, t2, t3});
    }

    public static <T> Observable<T> merge(Observable<? extends T> t1, Observable<? extends T> t2, Observable<? extends T> t3, Observable<? extends T> t4) {
        return merge((Observable<? extends T>[]) new Observable[]{t1, t2, t3, t4});
    }

    public static <T> Observable<T> merge(Observable<? extends T> t1, Observable<? extends T> t2, Observable<? extends T> t3, Observable<? extends T> t4, Observable<? extends T> t5) {
        return merge((Observable<? extends T>[]) new Observable[]{t1, t2, t3, t4, t5});
    }

    public static <T> Observable<T> merge(Observable<? extends T> t1, Observable<? extends T> t2, Observable<? extends T> t3, Observable<? extends T> t4, Observable<? extends T> t5, Observable<? extends T> t6) {
        return merge((Observable<? extends T>[]) new Observable[]{t1, t2, t3, t4, t5, t6});
    }

    public static <T> Observable<T> merge(Observable<? extends T> t1, Observable<? extends T> t2, Observable<? extends T> t3, Observable<? extends T> t4, Observable<? extends T> t5, Observable<? extends T> t6, Observable<? extends T> t7) {
        return merge((Observable<? extends T>[]) new Observable[]{t1, t2, t3, t4, t5, t6, t7});
    }

    public static <T> Observable<T> merge(Observable<? extends T> t1, Observable<? extends T> t2, Observable<? extends T> t3, Observable<? extends T> t4, Observable<? extends T> t5, Observable<? extends T> t6, Observable<? extends T> t7, Observable<? extends T> t8) {
        return merge((Observable<? extends T>[]) new Observable[]{t1, t2, t3, t4, t5, t6, t7, t8});
    }

    public static <T> Observable<T> merge(Observable<? extends T> t1, Observable<? extends T> t2, Observable<? extends T> t3, Observable<? extends T> t4, Observable<? extends T> t5, Observable<? extends T> t6, Observable<? extends T> t7, Observable<? extends T> t8, Observable<? extends T> t9) {
        return merge((Observable<? extends T>[]) new Observable[]{t1, t2, t3, t4, t5, t6, t7, t8, t9});
    }

    public static <T> Observable<T> merge(Observable<? extends T>[] sequences) {
        return merge(from((T[]) sequences));
    }

    public static <T> Observable<T> merge(Observable<? extends T>[] sequences, int maxConcurrent) {
        return merge(from((T[]) sequences), maxConcurrent);
    }

    public static <T> Observable<T> mergeDelayError(Observable<? extends Observable<? extends T>> source) {
        return source.lift(OperatorMerge.instance(true));
    }

    @Beta
    public static <T> Observable<T> mergeDelayError(Observable<? extends Observable<? extends T>> source, int maxConcurrent) {
        return source.lift(OperatorMerge.instance(true, maxConcurrent));
    }

    public static <T> Observable<T> mergeDelayError(Iterable<? extends Observable<? extends T>> sequences) {
        return mergeDelayError(from(sequences));
    }

    public static <T> Observable<T> mergeDelayError(Iterable<? extends Observable<? extends T>> sequences, int maxConcurrent) {
        return mergeDelayError(from(sequences), maxConcurrent);
    }

    public static <T> Observable<T> mergeDelayError(Observable<? extends T> t1, Observable<? extends T> t2) {
        return mergeDelayError(just(t1, t2));
    }

    public static <T> Observable<T> mergeDelayError(Observable<? extends T> t1, Observable<? extends T> t2, Observable<? extends T> t3) {
        return mergeDelayError(just(t1, t2, t3));
    }

    public static <T> Observable<T> mergeDelayError(Observable<? extends T> t1, Observable<? extends T> t2, Observable<? extends T> t3, Observable<? extends T> t4) {
        return mergeDelayError(just(t1, t2, t3, t4));
    }

    public static <T> Observable<T> mergeDelayError(Observable<? extends T> t1, Observable<? extends T> t2, Observable<? extends T> t3, Observable<? extends T> t4, Observable<? extends T> t5) {
        return mergeDelayError(just(t1, t2, t3, t4, t5));
    }

    public static <T> Observable<T> mergeDelayError(Observable<? extends T> t1, Observable<? extends T> t2, Observable<? extends T> t3, Observable<? extends T> t4, Observable<? extends T> t5, Observable<? extends T> t6) {
        return mergeDelayError(just(t1, t2, t3, t4, t5, t6));
    }

    public static <T> Observable<T> mergeDelayError(Observable<? extends T> t1, Observable<? extends T> t2, Observable<? extends T> t3, Observable<? extends T> t4, Observable<? extends T> t5, Observable<? extends T> t6, Observable<? extends T> t7) {
        return mergeDelayError(just(t1, t2, t3, t4, t5, t6, t7));
    }

    public static <T> Observable<T> mergeDelayError(Observable<? extends T> t1, Observable<? extends T> t2, Observable<? extends T> t3, Observable<? extends T> t4, Observable<? extends T> t5, Observable<? extends T> t6, Observable<? extends T> t7, Observable<? extends T> t8) {
        return mergeDelayError(just(t1, t2, t3, t4, t5, t6, t7, t8));
    }

    public static <T> Observable<T> mergeDelayError(Observable<? extends T> t1, Observable<? extends T> t2, Observable<? extends T> t3, Observable<? extends T> t4, Observable<? extends T> t5, Observable<? extends T> t6, Observable<? extends T> t7, Observable<? extends T> t8, Observable<? extends T> t9) {
        return mergeDelayError(just(t1, t2, t3, t4, t5, t6, t7, t8, t9));
    }

    public final Observable<Observable<T>> nest() {
        return just(this);
    }

    public static <T> Observable<T> never() {
        return NeverObservableHolder.instance();
    }

    public static Observable<Integer> range(int start, int count) {
        if (count < 0) {
            throw new IllegalArgumentException("Count can not be negative");
        } else if (count == 0) {
            return empty();
        } else {
            if (start > (Integer.MAX_VALUE - count) + 1) {
                throw new IllegalArgumentException("start + count can not exceed Integer.MAX_VALUE");
            } else if (count == 1) {
                return just(Integer.valueOf(start));
            } else {
                return create((OnSubscribe<T>) new OnSubscribeRange<T>(start, (count - 1) + start));
            }
        }
    }

    public static Observable<Integer> range(int start, int count, Scheduler scheduler) {
        return range(start, count).subscribeOn(scheduler);
    }

    public static <T> Observable<Boolean> sequenceEqual(Observable<? extends T> first, Observable<? extends T> second) {
        return sequenceEqual(first, second, InternalObservableUtils.OBJECT_EQUALS);
    }

    public static <T> Observable<Boolean> sequenceEqual(Observable<? extends T> first, Observable<? extends T> second, Func2<? super T, ? super T, Boolean> equality) {
        return OperatorSequenceEqual.sequenceEqual(first, second, equality);
    }

    public static <T> Observable<T> switchOnNext(Observable<? extends Observable<? extends T>> sequenceOfSequences) {
        return sequenceOfSequences.lift(OperatorSwitch.instance(false));
    }

    @Beta
    public static <T> Observable<T> switchOnNextDelayError(Observable<? extends Observable<? extends T>> sequenceOfSequences) {
        return sequenceOfSequences.lift(OperatorSwitch.instance(true));
    }

    @Deprecated
    public static Observable<Long> timer(long initialDelay, long period, TimeUnit unit) {
        return interval(initialDelay, period, unit, Schedulers.computation());
    }

    @Deprecated
    public static Observable<Long> timer(long initialDelay, long period, TimeUnit unit, Scheduler scheduler) {
        return interval(initialDelay, period, unit, scheduler);
    }

    public static Observable<Long> timer(long delay, TimeUnit unit) {
        return timer(delay, unit, Schedulers.computation());
    }

    public static Observable<Long> timer(long delay, TimeUnit unit, Scheduler scheduler) {
        return create((OnSubscribe<T>) new OnSubscribeTimerOnce<T>(delay, unit, scheduler));
    }

    public static <T, Resource> Observable<T> using(Func0<Resource> resourceFactory, Func1<? super Resource, ? extends Observable<? extends T>> observableFactory, Action1<? super Resource> disposeAction) {
        return using(resourceFactory, observableFactory, disposeAction, false);
    }

    @Beta
    public static <T, Resource> Observable<T> using(Func0<Resource> resourceFactory, Func1<? super Resource, ? extends Observable<? extends T>> observableFactory, Action1<? super Resource> disposeAction, boolean disposeEagerly) {
        return create((OnSubscribe<T>) new OnSubscribeUsing<T>(resourceFactory, observableFactory, disposeAction, disposeEagerly));
    }

    public static <R> Observable<R> zip(Iterable<? extends Observable<?>> ws, FuncN<? extends R> zipFunction) {
        List<Observable<?>> os = new ArrayList<>();
        for (Observable<?> o : ws) {
            os.add(o);
        }
        return just(os.toArray(new Observable[os.size()])).lift(new OperatorZip(zipFunction));
    }

    @Experimental
    public static <R> Observable<R> zip(Observable<?>[] ws, FuncN<? extends R> zipFunction) {
        return just(ws).lift(new OperatorZip(zipFunction));
    }

    public static <R> Observable<R> zip(Observable<? extends Observable<?>> ws, FuncN<? extends R> zipFunction) {
        return ws.toList().map(InternalObservableUtils.TO_ARRAY).lift(new OperatorZip(zipFunction));
    }

    public static <T1, T2, R> Observable<R> zip(Observable<? extends T1> o1, Observable<? extends T2> o2, Func2<? super T1, ? super T2, ? extends R> zipFunction) {
        return just(new Observable[]{o1, o2}).lift(new OperatorZip((Func2) zipFunction));
    }

    public static <T1, T2, T3, R> Observable<R> zip(Observable<? extends T1> o1, Observable<? extends T2> o2, Observable<? extends T3> o3, Func3<? super T1, ? super T2, ? super T3, ? extends R> zipFunction) {
        return just(new Observable[]{o1, o2, o3}).lift(new OperatorZip((Func3) zipFunction));
    }

    public static <T1, T2, T3, T4, R> Observable<R> zip(Observable<? extends T1> o1, Observable<? extends T2> o2, Observable<? extends T3> o3, Observable<? extends T4> o4, Func4<? super T1, ? super T2, ? super T3, ? super T4, ? extends R> zipFunction) {
        return just(new Observable[]{o1, o2, o3, o4}).lift(new OperatorZip((Func4) zipFunction));
    }

    public static <T1, T2, T3, T4, T5, R> Observable<R> zip(Observable<? extends T1> o1, Observable<? extends T2> o2, Observable<? extends T3> o3, Observable<? extends T4> o4, Observable<? extends T5> o5, Func5<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? extends R> zipFunction) {
        return just(new Observable[]{o1, o2, o3, o4, o5}).lift(new OperatorZip((Func5) zipFunction));
    }

    public static <T1, T2, T3, T4, T5, T6, R> Observable<R> zip(Observable<? extends T1> o1, Observable<? extends T2> o2, Observable<? extends T3> o3, Observable<? extends T4> o4, Observable<? extends T5> o5, Observable<? extends T6> o6, Func6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? extends R> zipFunction) {
        return just(new Observable[]{o1, o2, o3, o4, o5, o6}).lift(new OperatorZip((Func6) zipFunction));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, R> Observable<R> zip(Observable<? extends T1> o1, Observable<? extends T2> o2, Observable<? extends T3> o3, Observable<? extends T4> o4, Observable<? extends T5> o5, Observable<? extends T6> o6, Observable<? extends T7> o7, Func7<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? extends R> zipFunction) {
        return just(new Observable[]{o1, o2, o3, o4, o5, o6, o7}).lift(new OperatorZip((Func7) zipFunction));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, R> Observable<R> zip(Observable<? extends T1> o1, Observable<? extends T2> o2, Observable<? extends T3> o3, Observable<? extends T4> o4, Observable<? extends T5> o5, Observable<? extends T6> o6, Observable<? extends T7> o7, Observable<? extends T8> o8, Func8<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? extends R> zipFunction) {
        return just(new Observable[]{o1, o2, o3, o4, o5, o6, o7, o8}).lift(new OperatorZip((Func8) zipFunction));
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, R> Observable<R> zip(Observable<? extends T1> o1, Observable<? extends T2> o2, Observable<? extends T3> o3, Observable<? extends T4> o4, Observable<? extends T5> o5, Observable<? extends T6> o6, Observable<? extends T7> o7, Observable<? extends T8> o8, Observable<? extends T9> o9, Func9<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, ? super T9, ? extends R> zipFunction) {
        return just(new Observable[]{o1, o2, o3, o4, o5, o6, o7, o8, o9}).lift(new OperatorZip((Func9) zipFunction));
    }

    public final Observable<Boolean> all(Func1<? super T, Boolean> predicate) {
        return lift(new OperatorAll(predicate));
    }

    public final Observable<T> ambWith(Observable<? extends T> t1) {
        return amb(this, t1);
    }

    public final Observable<T> asObservable() {
        return lift(OperatorAsObservable.instance());
    }

    public final <TClosing> Observable<List<T>> buffer(Func0<? extends Observable<? extends TClosing>> bufferClosingSelector) {
        return lift(new OperatorBufferWithSingleObservable(bufferClosingSelector, 16));
    }

    public final Observable<List<T>> buffer(int count) {
        return buffer(count, count);
    }

    public final Observable<List<T>> buffer(int count, int skip) {
        return lift(new OperatorBufferWithSize(count, skip));
    }

    public final Observable<List<T>> buffer(long timespan, long timeshift, TimeUnit unit) {
        return buffer(timespan, timeshift, unit, Schedulers.computation());
    }

    public final Observable<List<T>> buffer(long timespan, long timeshift, TimeUnit unit, Scheduler scheduler) {
        OperatorBufferWithTime operatorBufferWithTime = new OperatorBufferWithTime(timespan, timeshift, unit, Integer.MAX_VALUE, scheduler);
        return lift(operatorBufferWithTime);
    }

    public final Observable<List<T>> buffer(long timespan, TimeUnit unit) {
        return buffer(timespan, unit, Integer.MAX_VALUE, Schedulers.computation());
    }

    public final Observable<List<T>> buffer(long timespan, TimeUnit unit, int count) {
        OperatorBufferWithTime operatorBufferWithTime = new OperatorBufferWithTime(timespan, timespan, unit, count, Schedulers.computation());
        return lift(operatorBufferWithTime);
    }

    public final Observable<List<T>> buffer(long timespan, TimeUnit unit, int count, Scheduler scheduler) {
        OperatorBufferWithTime operatorBufferWithTime = new OperatorBufferWithTime(timespan, timespan, unit, count, scheduler);
        return lift(operatorBufferWithTime);
    }

    public final Observable<List<T>> buffer(long timespan, TimeUnit unit, Scheduler scheduler) {
        return buffer(timespan, timespan, unit, scheduler);
    }

    public final <TOpening, TClosing> Observable<List<T>> buffer(Observable<? extends TOpening> bufferOpenings, Func1<? super TOpening, ? extends Observable<? extends TClosing>> bufferClosingSelector) {
        return lift(new OperatorBufferWithStartEndObservable(bufferOpenings, bufferClosingSelector));
    }

    public final <B> Observable<List<T>> buffer(Observable<B> boundary) {
        return buffer(boundary, 16);
    }

    public final <B> Observable<List<T>> buffer(Observable<B> boundary, int initialCapacity) {
        return lift(new OperatorBufferWithSingleObservable(boundary, initialCapacity));
    }

    public final Observable<T> cache() {
        return CachedObservable.from(this);
    }

    @Deprecated
    public final Observable<T> cache(int initialCapacity) {
        return cacheWithInitialCapacity(initialCapacity);
    }

    public final Observable<T> cacheWithInitialCapacity(int initialCapacity) {
        return CachedObservable.from(this, initialCapacity);
    }

    public final <R> Observable<R> cast(Class<R> klass) {
        return lift(new OperatorCast(klass));
    }

    public final <R> Observable<R> collect(Func0<R> stateFactory, Action2<R, ? super T> collector) {
        return create((OnSubscribe<T>) new OnSubscribeCollect<T>(this, stateFactory, collector));
    }

    public final <R> Observable<R> concatMap(Func1<? super T, ? extends Observable<? extends R>> func) {
        if (this instanceof ScalarSynchronousObservable) {
            return ((ScalarSynchronousObservable) this).scalarFlatMap(func);
        }
        return create((OnSubscribe<T>) new OnSubscribeConcatMap<T>(this, func, 2, 0));
    }

    @Beta
    public final <R> Observable<R> concatMapDelayError(Func1<? super T, ? extends Observable<? extends R>> func) {
        if (this instanceof ScalarSynchronousObservable) {
            return ((ScalarSynchronousObservable) this).scalarFlatMap(func);
        }
        return create((OnSubscribe<T>) new OnSubscribeConcatMap<T>(this, func, 2, 2));
    }

    public final <R> Observable<R> concatMapIterable(Func1<? super T, ? extends Iterable<? extends R>> collectionSelector) {
        return OnSubscribeFlattenIterable.createFrom(this, collectionSelector, RxRingBuffer.SIZE);
    }

    public final Observable<T> concatWith(Observable<? extends T> t1) {
        return concat(this, t1);
    }

    public final Observable<Boolean> contains(Object element) {
        return exists(InternalObservableUtils.equalsWith(element));
    }

    public final Observable<Integer> count() {
        return reduce(Integer.valueOf(0), InternalObservableUtils.COUNTER);
    }

    public final Observable<Long> countLong() {
        return reduce(Long.valueOf(0), InternalObservableUtils.LONG_COUNTER);
    }

    public final <U> Observable<T> debounce(Func1<? super T, ? extends Observable<U>> debounceSelector) {
        return lift(new OperatorDebounceWithSelector(debounceSelector));
    }

    public final Observable<T> debounce(long timeout, TimeUnit unit) {
        return debounce(timeout, unit, Schedulers.computation());
    }

    public final Observable<T> debounce(long timeout, TimeUnit unit, Scheduler scheduler) {
        return lift(new OperatorDebounceWithTime(timeout, unit, scheduler));
    }

    public final Observable<T> defaultIfEmpty(T defaultValue) {
        return switchIfEmpty(just(defaultValue));
    }

    public final Observable<T> switchIfEmpty(Observable<? extends T> alternate) {
        return lift(new OperatorSwitchIfEmpty(alternate));
    }

    public final <U, V> Observable<T> delay(Func0<? extends Observable<U>> subscriptionDelay, Func1<? super T, ? extends Observable<V>> itemDelay) {
        return delaySubscription(subscriptionDelay).lift(new OperatorDelayWithSelector(this, itemDelay));
    }

    public final <U> Observable<T> delay(Func1<? super T, ? extends Observable<U>> itemDelay) {
        return lift(new OperatorDelayWithSelector(this, itemDelay));
    }

    public final Observable<T> delay(long delay, TimeUnit unit) {
        return delay(delay, unit, Schedulers.computation());
    }

    public final Observable<T> delay(long delay, TimeUnit unit, Scheduler scheduler) {
        return lift(new OperatorDelay(delay, unit, scheduler));
    }

    public final Observable<T> delaySubscription(long delay, TimeUnit unit) {
        return delaySubscription(delay, unit, Schedulers.computation());
    }

    public final Observable<T> delaySubscription(long delay, TimeUnit unit, Scheduler scheduler) {
        OnSubscribeDelaySubscription onSubscribeDelaySubscription = new OnSubscribeDelaySubscription(this, delay, unit, scheduler);
        return create((OnSubscribe<T>) onSubscribeDelaySubscription);
    }

    public final <U> Observable<T> delaySubscription(Func0<? extends Observable<U>> subscriptionDelay) {
        return create((OnSubscribe<T>) new OnSubscribeDelaySubscriptionWithSelector<T>(this, subscriptionDelay));
    }

    @Beta
    public final <U> Observable<T> delaySubscription(Observable<U> other) {
        if (other != null) {
            return create((OnSubscribe<T>) new OnSubscribeDelaySubscriptionOther<T>(this, other));
        }
        throw new NullPointerException();
    }

    public final <T2> Observable<T2> dematerialize() {
        return lift(OperatorDematerialize.instance());
    }

    public final Observable<T> distinct() {
        return lift(OperatorDistinct.instance());
    }

    public final <U> Observable<T> distinct(Func1<? super T, ? extends U> keySelector) {
        return lift(new OperatorDistinct(keySelector));
    }

    public final Observable<T> distinctUntilChanged() {
        return lift(OperatorDistinctUntilChanged.instance());
    }

    public final <U> Observable<T> distinctUntilChanged(Func1<? super T, ? extends U> keySelector) {
        return lift(new OperatorDistinctUntilChanged(keySelector));
    }

    @Beta
    public final Observable<T> distinctUntilChanged(Func2<? super T, ? super T, Boolean> comparator) {
        return lift(new OperatorDistinctUntilChanged(comparator));
    }

    public final Observable<T> doOnCompleted(Action0 onCompleted) {
        return create((OnSubscribe<T>) new OnSubscribeDoOnEach<T>(this, new ActionObserver<>(Actions.empty(), Actions.empty(), onCompleted)));
    }

    public final Observable<T> doOnEach(Action1<Notification<? super T>> onNotification) {
        return create((OnSubscribe<T>) new OnSubscribeDoOnEach<T>(this, new ActionNotificationObserver<>(onNotification)));
    }

    public final Observable<T> doOnEach(Observer<? super T> observer) {
        return create((OnSubscribe<T>) new OnSubscribeDoOnEach<T>(this, observer));
    }

    public final Observable<T> doOnError(Action1<? super Throwable> onError) {
        return create((OnSubscribe<T>) new OnSubscribeDoOnEach<T>(this, new ActionObserver<>(Actions.empty(), onError, Actions.empty())));
    }

    public final Observable<T> doOnNext(Action1<? super T> onNext) {
        return create((OnSubscribe<T>) new OnSubscribeDoOnEach<T>(this, new ActionObserver<>(onNext, Actions.empty(), Actions.empty())));
    }

    public final Observable<T> doOnRequest(Action1<? super Long> onRequest) {
        return lift(new OperatorDoOnRequest(onRequest));
    }

    public final Observable<T> doOnSubscribe(Action0 subscribe) {
        return lift(new OperatorDoOnSubscribe(subscribe));
    }

    public final Observable<T> doOnTerminate(Action0 onTerminate) {
        return create((OnSubscribe<T>) new OnSubscribeDoOnEach<T>(this, new ActionObserver<>(Actions.empty(), Actions.toAction1(onTerminate), onTerminate)));
    }

    public final Observable<T> doOnUnsubscribe(Action0 unsubscribe) {
        return lift(new OperatorDoOnUnsubscribe(unsubscribe));
    }

    @Beta
    public static <T> Observable<T> concatEager(Observable<? extends T> o1, Observable<? extends T> o2) {
        return concatEager((Iterable<? extends Observable<? extends T>>) Arrays.asList(new Observable[]{o1, o2}));
    }

    @Beta
    public static <T> Observable<T> concatEager(Observable<? extends T> o1, Observable<? extends T> o2, Observable<? extends T> o3) {
        return concatEager((Iterable<? extends Observable<? extends T>>) Arrays.asList(new Observable[]{o1, o2, o3}));
    }

    @Beta
    public static <T> Observable<T> concatEager(Observable<? extends T> o1, Observable<? extends T> o2, Observable<? extends T> o3, Observable<? extends T> o4) {
        return concatEager((Iterable<? extends Observable<? extends T>>) Arrays.asList(new Observable[]{o1, o2, o3, o4}));
    }

    @Beta
    public static <T> Observable<T> concatEager(Observable<? extends T> o1, Observable<? extends T> o2, Observable<? extends T> o3, Observable<? extends T> o4, Observable<? extends T> o5) {
        return concatEager((Iterable<? extends Observable<? extends T>>) Arrays.asList(new Observable[]{o1, o2, o3, o4, o5}));
    }

    @Beta
    public static <T> Observable<T> concatEager(Observable<? extends T> o1, Observable<? extends T> o2, Observable<? extends T> o3, Observable<? extends T> o4, Observable<? extends T> o5, Observable<? extends T> o6) {
        return concatEager((Iterable<? extends Observable<? extends T>>) Arrays.asList(new Observable[]{o1, o2, o3, o4, o5, o6}));
    }

    @Beta
    public static <T> Observable<T> concatEager(Observable<? extends T> o1, Observable<? extends T> o2, Observable<? extends T> o3, Observable<? extends T> o4, Observable<? extends T> o5, Observable<? extends T> o6, Observable<? extends T> o7) {
        return concatEager((Iterable<? extends Observable<? extends T>>) Arrays.asList(new Observable[]{o1, o2, o3, o4, o5, o6, o7}));
    }

    @Beta
    public static <T> Observable<T> concatEager(Observable<? extends T> o1, Observable<? extends T> o2, Observable<? extends T> o3, Observable<? extends T> o4, Observable<? extends T> o5, Observable<? extends T> o6, Observable<? extends T> o7, Observable<? extends T> o8) {
        return concatEager((Iterable<? extends Observable<? extends T>>) Arrays.asList(new Observable[]{o1, o2, o3, o4, o5, o6, o7, o8}));
    }

    @Beta
    public static <T> Observable<T> concatEager(Observable<? extends T> o1, Observable<? extends T> o2, Observable<? extends T> o3, Observable<? extends T> o4, Observable<? extends T> o5, Observable<? extends T> o6, Observable<? extends T> o7, Observable<? extends T> o8, Observable<? extends T> o9) {
        return concatEager((Iterable<? extends Observable<? extends T>>) Arrays.asList(new Observable[]{o1, o2, o3, o4, o5, o6, o7, o8, o9}));
    }

    @Beta
    public static <T> Observable<T> concatEager(Iterable<? extends Observable<? extends T>> sources) {
        return from(sources).concatMapEager(UtilityFunctions.identity());
    }

    @Beta
    public static <T> Observable<T> concatEager(Iterable<? extends Observable<? extends T>> sources, int capacityHint) {
        return from(sources).concatMapEager(UtilityFunctions.identity(), capacityHint);
    }

    @Beta
    public static <T> Observable<T> concatEager(Observable<? extends Observable<? extends T>> sources) {
        return sources.concatMapEager(UtilityFunctions.identity());
    }

    @Beta
    public static <T> Observable<T> concatEager(Observable<? extends Observable<? extends T>> sources, int capacityHint) {
        return sources.concatMapEager(UtilityFunctions.identity(), capacityHint);
    }

    @Beta
    public final <R> Observable<R> concatMapEager(Func1<? super T, ? extends Observable<? extends R>> mapper) {
        return concatMapEager(mapper, RxRingBuffer.SIZE);
    }

    @Beta
    public final <R> Observable<R> concatMapEager(Func1<? super T, ? extends Observable<? extends R>> mapper, int capacityHint) {
        if (capacityHint >= 1) {
            return lift(new OperatorEagerConcatMap(mapper, capacityHint, Integer.MAX_VALUE));
        }
        StringBuilder sb = new StringBuilder();
        sb.append("capacityHint > 0 required but it was ");
        sb.append(capacityHint);
        throw new IllegalArgumentException(sb.toString());
    }

    @Beta
    public final <R> Observable<R> concatMapEager(Func1<? super T, ? extends Observable<? extends R>> mapper, int capacityHint, int maxConcurrent) {
        if (capacityHint < 1) {
            StringBuilder sb = new StringBuilder();
            sb.append("capacityHint > 0 required but it was ");
            sb.append(capacityHint);
            throw new IllegalArgumentException(sb.toString());
        } else if (maxConcurrent >= 1) {
            return lift(new OperatorEagerConcatMap(mapper, capacityHint, maxConcurrent));
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("maxConcurrent > 0 required but it was ");
            sb2.append(capacityHint);
            throw new IllegalArgumentException(sb2.toString());
        }
    }

    public final Observable<T> elementAt(int index) {
        return lift(new OperatorElementAt(index));
    }

    public final Observable<T> elementAtOrDefault(int index, T defaultValue) {
        return lift(new OperatorElementAt(index, defaultValue));
    }

    public final Observable<Boolean> exists(Func1<? super T, Boolean> predicate) {
        return lift(new OperatorAny(predicate, false));
    }

    public final Observable<T> filter(Func1<? super T, Boolean> predicate) {
        return create((OnSubscribe<T>) new OnSubscribeFilter<T>(this, predicate));
    }

    @Deprecated
    public final Observable<T> finallyDo(Action0 action) {
        return lift(new OperatorDoAfterTerminate(action));
    }

    public final Observable<T> doAfterTerminate(Action0 action) {
        return lift(new OperatorDoAfterTerminate(action));
    }

    public final Observable<T> first() {
        return take(1).single();
    }

    public final Observable<T> first(Func1<? super T, Boolean> predicate) {
        return takeFirst(predicate).single();
    }

    public final Observable<T> firstOrDefault(T defaultValue) {
        return take(1).singleOrDefault(defaultValue);
    }

    public final Observable<T> firstOrDefault(T defaultValue, Func1<? super T, Boolean> predicate) {
        return takeFirst(predicate).singleOrDefault(defaultValue);
    }

    public final <R> Observable<R> flatMap(Func1<? super T, ? extends Observable<? extends R>> func) {
        if (getClass() == ScalarSynchronousObservable.class) {
            return ((ScalarSynchronousObservable) this).scalarFlatMap(func);
        }
        return merge(map(func));
    }

    public final <R> Observable<R> flatMap(Func1<? super T, ? extends Observable<? extends R>> func, int maxConcurrent) {
        if (getClass() == ScalarSynchronousObservable.class) {
            return ((ScalarSynchronousObservable) this).scalarFlatMap(func);
        }
        return merge(map(func), maxConcurrent);
    }

    public final <R> Observable<R> flatMap(Func1<? super T, ? extends Observable<? extends R>> onNext, Func1<? super Throwable, ? extends Observable<? extends R>> onError, Func0<? extends Observable<? extends R>> onCompleted) {
        return merge(mapNotification(onNext, onError, onCompleted));
    }

    public final <R> Observable<R> flatMap(Func1<? super T, ? extends Observable<? extends R>> onNext, Func1<? super Throwable, ? extends Observable<? extends R>> onError, Func0<? extends Observable<? extends R>> onCompleted, int maxConcurrent) {
        return merge(mapNotification(onNext, onError, onCompleted), maxConcurrent);
    }

    public final <U, R> Observable<R> flatMap(Func1<? super T, ? extends Observable<? extends U>> collectionSelector, Func2<? super T, ? super U, ? extends R> resultSelector) {
        return merge(lift(new OperatorMapPair(collectionSelector, resultSelector)));
    }

    public final <U, R> Observable<R> flatMap(Func1<? super T, ? extends Observable<? extends U>> collectionSelector, Func2<? super T, ? super U, ? extends R> resultSelector, int maxConcurrent) {
        return merge(lift(new OperatorMapPair(collectionSelector, resultSelector)), maxConcurrent);
    }

    public final <R> Observable<R> flatMapIterable(Func1<? super T, ? extends Iterable<? extends R>> collectionSelector) {
        return flatMapIterable(collectionSelector, RxRingBuffer.SIZE);
    }

    public final <R> Observable<R> flatMapIterable(Func1<? super T, ? extends Iterable<? extends R>> collectionSelector, int maxConcurrent) {
        return OnSubscribeFlattenIterable.createFrom(this, collectionSelector, maxConcurrent);
    }

    public final <U, R> Observable<R> flatMapIterable(Func1<? super T, ? extends Iterable<? extends U>> collectionSelector, Func2<? super T, ? super U, ? extends R> resultSelector) {
        return flatMap(OperatorMapPair.convertSelector(collectionSelector), resultSelector);
    }

    public final <U, R> Observable<R> flatMapIterable(Func1<? super T, ? extends Iterable<? extends U>> collectionSelector, Func2<? super T, ? super U, ? extends R> resultSelector, int maxConcurrent) {
        return flatMap(OperatorMapPair.convertSelector(collectionSelector), resultSelector, maxConcurrent);
    }

    public final void forEach(Action1<? super T> onNext) {
        subscribe(onNext);
    }

    public final void forEach(Action1<? super T> onNext, Action1<Throwable> onError) {
        subscribe(onNext, onError);
    }

    public final void forEach(Action1<? super T> onNext, Action1<Throwable> onError, Action0 onComplete) {
        subscribe(onNext, onError, onComplete);
    }

    public final <K, R> Observable<GroupedObservable<K, R>> groupBy(Func1<? super T, ? extends K> keySelector, Func1<? super T, ? extends R> elementSelector) {
        return lift(new OperatorGroupBy(keySelector, elementSelector));
    }

    @Experimental
    public final <K, R> Observable<GroupedObservable<K, R>> groupBy(Func1<? super T, ? extends K> keySelector, Func1<? super T, ? extends R> elementSelector, Func1<Action1<K>, Map<K, Object>> evictingMapFactory) {
        if (evictingMapFactory != null) {
            return lift(new OperatorGroupBy(keySelector, elementSelector, evictingMapFactory));
        }
        throw new NullPointerException("evictingMapFactory cannot be null");
    }

    public final <K> Observable<GroupedObservable<K, T>> groupBy(Func1<? super T, ? extends K> keySelector) {
        return lift(new OperatorGroupBy(keySelector));
    }

    public final <T2, D1, D2, R> Observable<R> groupJoin(Observable<T2> right, Func1<? super T, ? extends Observable<D1>> leftDuration, Func1<? super T2, ? extends Observable<D2>> rightDuration, Func2<? super T, ? super Observable<T2>, ? extends R> resultSelector) {
        OnSubscribeGroupJoin onSubscribeGroupJoin = new OnSubscribeGroupJoin(this, right, leftDuration, rightDuration, resultSelector);
        return create((OnSubscribe<T>) onSubscribeGroupJoin);
    }

    public final Observable<T> ignoreElements() {
        return lift(OperatorIgnoreElements.instance());
    }

    public final Observable<Boolean> isEmpty() {
        return lift(InternalObservableUtils.IS_EMPTY);
    }

    public final <TRight, TLeftDuration, TRightDuration, R> Observable<R> join(Observable<TRight> right, Func1<T, Observable<TLeftDuration>> leftDurationSelector, Func1<TRight, Observable<TRightDuration>> rightDurationSelector, Func2<T, TRight, R> resultSelector) {
        OnSubscribeJoin onSubscribeJoin = new OnSubscribeJoin(this, right, leftDurationSelector, rightDurationSelector, resultSelector);
        return create((OnSubscribe<T>) onSubscribeJoin);
    }

    public final Observable<T> last() {
        return takeLast(1).single();
    }

    public final Observable<T> last(Func1<? super T, Boolean> predicate) {
        return filter(predicate).takeLast(1).single();
    }

    public final Observable<T> lastOrDefault(T defaultValue) {
        return takeLast(1).singleOrDefault(defaultValue);
    }

    public final Observable<T> lastOrDefault(T defaultValue, Func1<? super T, Boolean> predicate) {
        return filter(predicate).takeLast(1).singleOrDefault(defaultValue);
    }

    public final Observable<T> limit(int count) {
        return take(count);
    }

    public final <R> Observable<R> map(Func1<? super T, ? extends R> func) {
        return create((OnSubscribe<T>) new OnSubscribeMap<T>(this, func));
    }

    private <R> Observable<R> mapNotification(Func1<? super T, ? extends R> onNext, Func1<? super Throwable, ? extends R> onError, Func0<? extends R> onCompleted) {
        return lift(new OperatorMapNotification(onNext, onError, onCompleted));
    }

    public final Observable<Notification<T>> materialize() {
        return lift(OperatorMaterialize.instance());
    }

    public final Observable<T> mergeWith(Observable<? extends T> t1) {
        return merge(this, t1);
    }

    public final Observable<T> observeOn(Scheduler scheduler) {
        return observeOn(scheduler, RxRingBuffer.SIZE);
    }

    public final Observable<T> observeOn(Scheduler scheduler, int bufferSize) {
        return observeOn(scheduler, false, bufferSize);
    }

    public final Observable<T> observeOn(Scheduler scheduler, boolean delayError) {
        return observeOn(scheduler, delayError, RxRingBuffer.SIZE);
    }

    public final Observable<T> observeOn(Scheduler scheduler, boolean delayError, int bufferSize) {
        if (this instanceof ScalarSynchronousObservable) {
            return ((ScalarSynchronousObservable) this).scalarScheduleOn(scheduler);
        }
        return lift(new OperatorObserveOn(scheduler, delayError, bufferSize));
    }

    public final <R> Observable<R> ofType(Class<R> klass) {
        return filter(InternalObservableUtils.isInstanceOf(klass)).cast(klass);
    }

    public final Observable<T> onBackpressureBuffer() {
        return lift(OperatorOnBackpressureBuffer.instance());
    }

    public final Observable<T> onBackpressureBuffer(long capacity) {
        return lift(new OperatorOnBackpressureBuffer(capacity));
    }

    public final Observable<T> onBackpressureBuffer(long capacity, Action0 onOverflow) {
        return lift(new OperatorOnBackpressureBuffer(capacity, onOverflow));
    }

    @Beta
    public final Observable<T> onBackpressureBuffer(long capacity, Action0 onOverflow, Strategy overflowStrategy) {
        return lift(new OperatorOnBackpressureBuffer(capacity, onOverflow, overflowStrategy));
    }

    public final Observable<T> onBackpressureDrop(Action1<? super T> onDrop) {
        return lift(new OperatorOnBackpressureDrop(onDrop));
    }

    public final Observable<T> onBackpressureDrop() {
        return lift(OperatorOnBackpressureDrop.instance());
    }

    public final Observable<T> onBackpressureLatest() {
        return lift(OperatorOnBackpressureLatest.instance());
    }

    public final Observable<T> onErrorResumeNext(Func1<? super Throwable, ? extends Observable<? extends T>> resumeFunction) {
        return lift(new OperatorOnErrorResumeNextViaFunction(resumeFunction));
    }

    public final Observable<T> onErrorResumeNext(Observable<? extends T> resumeSequence) {
        return lift(OperatorOnErrorResumeNextViaFunction.withOther(resumeSequence));
    }

    public final Observable<T> onErrorReturn(Func1<? super Throwable, ? extends T> resumeFunction) {
        return lift(OperatorOnErrorResumeNextViaFunction.withSingle(resumeFunction));
    }

    public final Observable<T> onExceptionResumeNext(Observable<? extends T> resumeSequence) {
        return lift(OperatorOnErrorResumeNextViaFunction.withException(resumeSequence));
    }

    @Experimental
    public final Observable<T> onTerminateDetach() {
        return create((OnSubscribe<T>) new OnSubscribeDetach<T>(this));
    }

    public final ConnectableObservable<T> publish() {
        return OperatorPublish.create(this);
    }

    public final <R> Observable<R> publish(Func1<? super Observable<T>, ? extends Observable<R>> selector) {
        return OperatorPublish.create(this, selector);
    }

    @Experimental
    public final Observable<T> rebatchRequests(int n) {
        if (n > 0) {
            return lift(OperatorObserveOn.rebatch(n));
        }
        StringBuilder sb = new StringBuilder();
        sb.append("n > 0 required but it was ");
        sb.append(n);
        throw new IllegalArgumentException(sb.toString());
    }

    public final Observable<T> reduce(Func2<T, T, T> accumulator) {
        return create((OnSubscribe<T>) new OnSubscribeReduce<T>(this, accumulator));
    }

    public final <R> Observable<R> reduce(R initialValue, Func2<R, ? super T, R> accumulator) {
        return create((OnSubscribe<T>) new OnSubscribeReduceSeed<T>(this, initialValue, accumulator));
    }

    public final Observable<T> repeat() {
        return OnSubscribeRedo.repeat(this);
    }

    public final Observable<T> repeat(Scheduler scheduler) {
        return OnSubscribeRedo.repeat(this, scheduler);
    }

    public final Observable<T> repeat(long count) {
        return OnSubscribeRedo.repeat(this, count);
    }

    public final Observable<T> repeat(long count, Scheduler scheduler) {
        return OnSubscribeRedo.repeat(this, count, scheduler);
    }

    public final Observable<T> repeatWhen(Func1<? super Observable<? extends Void>, ? extends Observable<?>> notificationHandler, Scheduler scheduler) {
        return OnSubscribeRedo.repeat(this, InternalObservableUtils.createRepeatDematerializer(notificationHandler), scheduler);
    }

    public final Observable<T> repeatWhen(Func1<? super Observable<? extends Void>, ? extends Observable<?>> notificationHandler) {
        return OnSubscribeRedo.repeat(this, InternalObservableUtils.createRepeatDematerializer(notificationHandler));
    }

    public final ConnectableObservable<T> replay() {
        return OperatorReplay.create(this);
    }

    public final <R> Observable<R> replay(Func1<? super Observable<T>, ? extends Observable<R>> selector) {
        return OperatorReplay.multicastSelector(InternalObservableUtils.createReplaySupplier(this), selector);
    }

    public final <R> Observable<R> replay(Func1<? super Observable<T>, ? extends Observable<R>> selector, int bufferSize) {
        return OperatorReplay.multicastSelector(InternalObservableUtils.createReplaySupplier(this, bufferSize), selector);
    }

    public final <R> Observable<R> replay(Func1<? super Observable<T>, ? extends Observable<R>> selector, int bufferSize, long time, TimeUnit unit) {
        return replay(selector, bufferSize, time, unit, Schedulers.computation());
    }

    public final <R> Observable<R> replay(Func1<? super Observable<T>, ? extends Observable<R>> selector, int bufferSize, long time, TimeUnit unit, Scheduler scheduler) {
        if (bufferSize >= 0) {
            return OperatorReplay.multicastSelector(InternalObservableUtils.createReplaySupplier(this, bufferSize, time, unit, scheduler), selector);
        }
        throw new IllegalArgumentException("bufferSize < 0");
    }

    public final <R> Observable<R> replay(Func1<? super Observable<T>, ? extends Observable<R>> selector, int bufferSize, Scheduler scheduler) {
        return OperatorReplay.multicastSelector(InternalObservableUtils.createReplaySupplier(this, bufferSize), InternalObservableUtils.createReplaySelectorAndObserveOn(selector, scheduler));
    }

    public final <R> Observable<R> replay(Func1<? super Observable<T>, ? extends Observable<R>> selector, long time, TimeUnit unit) {
        return replay(selector, time, unit, Schedulers.computation());
    }

    public final <R> Observable<R> replay(Func1<? super Observable<T>, ? extends Observable<R>> selector, long time, TimeUnit unit, Scheduler scheduler) {
        return OperatorReplay.multicastSelector(InternalObservableUtils.createReplaySupplier(this, time, unit, scheduler), selector);
    }

    public final <R> Observable<R> replay(Func1<? super Observable<T>, ? extends Observable<R>> selector, Scheduler scheduler) {
        return OperatorReplay.multicastSelector(InternalObservableUtils.createReplaySupplier(this), InternalObservableUtils.createReplaySelectorAndObserveOn(selector, scheduler));
    }

    public final ConnectableObservable<T> replay(int bufferSize) {
        return OperatorReplay.create(this, bufferSize);
    }

    public final ConnectableObservable<T> replay(int bufferSize, long time, TimeUnit unit) {
        return replay(bufferSize, time, unit, Schedulers.computation());
    }

    public final ConnectableObservable<T> replay(int bufferSize, long time, TimeUnit unit, Scheduler scheduler) {
        if (bufferSize >= 0) {
            return OperatorReplay.create(this, time, unit, scheduler, bufferSize);
        }
        throw new IllegalArgumentException("bufferSize < 0");
    }

    public final ConnectableObservable<T> replay(int bufferSize, Scheduler scheduler) {
        return OperatorReplay.observeOn(replay(bufferSize), scheduler);
    }

    public final ConnectableObservable<T> replay(long time, TimeUnit unit) {
        return replay(time, unit, Schedulers.computation());
    }

    public final ConnectableObservable<T> replay(long time, TimeUnit unit, Scheduler scheduler) {
        return OperatorReplay.create(this, time, unit, scheduler);
    }

    public final ConnectableObservable<T> replay(Scheduler scheduler) {
        return OperatorReplay.observeOn(replay(), scheduler);
    }

    public final Observable<T> retry() {
        return OnSubscribeRedo.retry(this);
    }

    public final Observable<T> retry(long count) {
        return OnSubscribeRedo.retry(this, count);
    }

    public final Observable<T> retry(Func2<Integer, Throwable, Boolean> predicate) {
        return nest().lift(new OperatorRetryWithPredicate(predicate));
    }

    public final Observable<T> retryWhen(Func1<? super Observable<? extends Throwable>, ? extends Observable<?>> notificationHandler) {
        return OnSubscribeRedo.retry(this, InternalObservableUtils.createRetryDematerializer(notificationHandler));
    }

    public final Observable<T> retryWhen(Func1<? super Observable<? extends Throwable>, ? extends Observable<?>> notificationHandler, Scheduler scheduler) {
        return OnSubscribeRedo.retry(this, InternalObservableUtils.createRetryDematerializer(notificationHandler), scheduler);
    }

    public final Observable<T> sample(long period, TimeUnit unit) {
        return sample(period, unit, Schedulers.computation());
    }

    public final Observable<T> sample(long period, TimeUnit unit, Scheduler scheduler) {
        return lift(new OperatorSampleWithTime(period, unit, scheduler));
    }

    public final <U> Observable<T> sample(Observable<U> sampler) {
        return lift(new OperatorSampleWithObservable(sampler));
    }

    public final Observable<T> scan(Func2<T, T, T> accumulator) {
        return lift(new OperatorScan(accumulator));
    }

    public final <R> Observable<R> scan(R initialValue, Func2<R, ? super T, R> accumulator) {
        return lift(new OperatorScan(initialValue, accumulator));
    }

    public final Observable<T> serialize() {
        return lift(OperatorSerialize.instance());
    }

    public final Observable<T> share() {
        return publish().refCount();
    }

    public final Observable<T> single() {
        return lift(OperatorSingle.instance());
    }

    public final Observable<T> single(Func1<? super T, Boolean> predicate) {
        return filter(predicate).single();
    }

    public final Observable<T> singleOrDefault(T defaultValue) {
        return lift(new OperatorSingle(defaultValue));
    }

    public final Observable<T> singleOrDefault(T defaultValue, Func1<? super T, Boolean> predicate) {
        return filter(predicate).singleOrDefault(defaultValue);
    }

    public final Observable<T> skip(int count) {
        return lift(new OperatorSkip(count));
    }

    public final Observable<T> skip(long time, TimeUnit unit) {
        return skip(time, unit, Schedulers.computation());
    }

    public final Observable<T> skip(long time, TimeUnit unit, Scheduler scheduler) {
        OnSubscribeSkipTimed onSubscribeSkipTimed = new OnSubscribeSkipTimed(this, time, unit, scheduler);
        return create((OnSubscribe<T>) onSubscribeSkipTimed);
    }

    public final Observable<T> skipLast(int count) {
        return lift(new OperatorSkipLast(count));
    }

    public final Observable<T> skipLast(long time, TimeUnit unit) {
        return skipLast(time, unit, Schedulers.computation());
    }

    public final Observable<T> skipLast(long time, TimeUnit unit, Scheduler scheduler) {
        return lift(new OperatorSkipLastTimed(time, unit, scheduler));
    }

    public final <U> Observable<T> skipUntil(Observable<U> other) {
        return lift(new OperatorSkipUntil(other));
    }

    public final Observable<T> skipWhile(Func1<? super T, Boolean> predicate) {
        return lift(new OperatorSkipWhile(OperatorSkipWhile.toPredicate2(predicate)));
    }

    public final Observable<T> startWith(Observable<T> values) {
        return concat(values, this);
    }

    public final Observable<T> startWith(Iterable<T> values) {
        return concat(from(values), this);
    }

    public final Observable<T> startWith(T t1) {
        return concat(just(t1), this);
    }

    public final Observable<T> startWith(T t1, T t2) {
        return concat(just(t1, t2), this);
    }

    public final Observable<T> startWith(T t1, T t2, T t3) {
        return concat(just(t1, t2, t3), this);
    }

    public final Observable<T> startWith(T t1, T t2, T t3, T t4) {
        return concat(just(t1, t2, t3, t4), this);
    }

    public final Observable<T> startWith(T t1, T t2, T t3, T t4, T t5) {
        return concat(just(t1, t2, t3, t4, t5), this);
    }

    public final Observable<T> startWith(T t1, T t2, T t3, T t4, T t5, T t6) {
        return concat(just(t1, t2, t3, t4, t5, t6), this);
    }

    public final Observable<T> startWith(T t1, T t2, T t3, T t4, T t5, T t6, T t7) {
        return concat(just(t1, t2, t3, t4, t5, t6, t7), this);
    }

    public final Observable<T> startWith(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8) {
        return concat(just(t1, t2, t3, t4, t5, t6, t7, t8), this);
    }

    public final Observable<T> startWith(T t1, T t2, T t3, T t4, T t5, T t6, T t7, T t8, T t9) {
        return concat(just(t1, t2, t3, t4, t5, t6, t7, t8, t9), this);
    }

    public final Subscription subscribe() {
        return subscribe((Subscriber<? super T>) new ActionSubscriber<Object>(Actions.empty(), InternalObservableUtils.ERROR_NOT_IMPLEMENTED, Actions.empty()));
    }

    public final Subscription subscribe(Action1<? super T> onNext) {
        if (onNext != null) {
            return subscribe((Subscriber<? super T>) new ActionSubscriber<Object>(onNext, InternalObservableUtils.ERROR_NOT_IMPLEMENTED, Actions.empty()));
        }
        throw new IllegalArgumentException("onNext can not be null");
    }

    public final Subscription subscribe(Action1<? super T> onNext, Action1<Throwable> onError) {
        if (onNext == null) {
            throw new IllegalArgumentException("onNext can not be null");
        } else if (onError != null) {
            return subscribe((Subscriber<? super T>) new ActionSubscriber<Object>(onNext, onError, Actions.empty()));
        } else {
            throw new IllegalArgumentException("onError can not be null");
        }
    }

    public final Subscription subscribe(Action1<? super T> onNext, Action1<Throwable> onError, Action0 onCompleted) {
        if (onNext == null) {
            throw new IllegalArgumentException("onNext can not be null");
        } else if (onError == null) {
            throw new IllegalArgumentException("onError can not be null");
        } else if (onCompleted != null) {
            return subscribe((Subscriber<? super T>) new ActionSubscriber<Object>(onNext, onError, onCompleted));
        } else {
            throw new IllegalArgumentException("onComplete can not be null");
        }
    }

    public final Subscription subscribe(Observer<? super T> observer) {
        if (observer instanceof Subscriber) {
            return subscribe((Subscriber) observer);
        }
        if (observer != null) {
            return subscribe((Subscriber<? super T>) new ObserverSubscriber<Object>(observer));
        }
        throw new NullPointerException("observer is null");
    }

    public final Subscription unsafeSubscribe(Subscriber<? super T> subscriber) {
        try {
            subscriber.onStart();
            RxJavaHooks.onObservableStart(this, this.onSubscribe).call(subscriber);
            return RxJavaHooks.onObservableReturn(subscriber);
        } catch (Throwable e2) {
            Exceptions.throwIfFatal(e2);
            StringBuilder sb = new StringBuilder();
            sb.append("Error occurred attempting to subscribe [");
            sb.append(e.getMessage());
            sb.append("] and then again while trying to pass to onError.");
            RuntimeException r = new OnErrorFailedException(sb.toString(), e2);
            RxJavaHooks.onObservableError(r);
            throw r;
        }
    }

    public final Subscription subscribe(Subscriber<? super T> subscriber) {
        return subscribe(subscriber, this);
    }

    static <T> Subscription subscribe(Subscriber<? super T> subscriber, Observable<T> observable) {
        if (subscriber == null) {
            throw new IllegalArgumentException("subscriber can not be null");
        } else if (observable.onSubscribe == null) {
            throw new IllegalStateException("onSubscribe function can not be null.");
        } else {
            subscriber.onStart();
            if (!(subscriber instanceof SafeSubscriber)) {
                subscriber = new SafeSubscriber<>(subscriber);
            }
            try {
                RxJavaHooks.onObservableStart(observable, observable.onSubscribe).call(subscriber);
                return RxJavaHooks.onObservableReturn(subscriber);
            } catch (Throwable e2) {
                Exceptions.throwIfFatal(e2);
                StringBuilder sb = new StringBuilder();
                sb.append("Error occurred attempting to subscribe [");
                sb.append(e.getMessage());
                sb.append("] and then again while trying to pass to onError.");
                RuntimeException r = new OnErrorFailedException(sb.toString(), e2);
                RxJavaHooks.onObservableError(r);
                throw r;
            }
        }
    }

    public final Observable<T> subscribeOn(Scheduler scheduler) {
        if (this instanceof ScalarSynchronousObservable) {
            return ((ScalarSynchronousObservable) this).scalarScheduleOn(scheduler);
        }
        return create((OnSubscribe<T>) new OperatorSubscribeOn<T>(this, scheduler));
    }

    public final <R> Observable<R> switchMap(Func1<? super T, ? extends Observable<? extends R>> func) {
        return switchOnNext(map(func));
    }

    @Beta
    public final <R> Observable<R> switchMapDelayError(Func1<? super T, ? extends Observable<? extends R>> func) {
        return switchOnNextDelayError(map(func));
    }

    public final Observable<T> take(int count) {
        return lift(new OperatorTake(count));
    }

    public final Observable<T> take(long time, TimeUnit unit) {
        return take(time, unit, Schedulers.computation());
    }

    public final Observable<T> take(long time, TimeUnit unit, Scheduler scheduler) {
        return lift(new OperatorTakeTimed(time, unit, scheduler));
    }

    public final Observable<T> takeFirst(Func1<? super T, Boolean> predicate) {
        return filter(predicate).take(1);
    }

    public final Observable<T> takeLast(int count) {
        if (count == 0) {
            return ignoreElements();
        }
        if (count == 1) {
            return create((OnSubscribe<T>) new OnSubscribeTakeLastOne<T>(this));
        }
        return lift(new OperatorTakeLast(count));
    }

    public final Observable<T> takeLast(int count, long time, TimeUnit unit) {
        return takeLast(count, time, unit, Schedulers.computation());
    }

    public final Observable<T> takeLast(int count, long time, TimeUnit unit, Scheduler scheduler) {
        OperatorTakeLastTimed operatorTakeLastTimed = new OperatorTakeLastTimed(count, time, unit, scheduler);
        return lift(operatorTakeLastTimed);
    }

    public final Observable<T> takeLast(long time, TimeUnit unit) {
        return takeLast(time, unit, Schedulers.computation());
    }

    public final Observable<T> takeLast(long time, TimeUnit unit, Scheduler scheduler) {
        return lift(new OperatorTakeLastTimed(time, unit, scheduler));
    }

    public final Observable<List<T>> takeLastBuffer(int count) {
        return takeLast(count).toList();
    }

    public final Observable<List<T>> takeLastBuffer(int count, long time, TimeUnit unit) {
        return takeLast(count, time, unit).toList();
    }

    public final Observable<List<T>> takeLastBuffer(int count, long time, TimeUnit unit, Scheduler scheduler) {
        return takeLast(count, time, unit, scheduler).toList();
    }

    public final Observable<List<T>> takeLastBuffer(long time, TimeUnit unit) {
        return takeLast(time, unit).toList();
    }

    public final Observable<List<T>> takeLastBuffer(long time, TimeUnit unit, Scheduler scheduler) {
        return takeLast(time, unit, scheduler).toList();
    }

    public final <E> Observable<T> takeUntil(Observable<? extends E> other) {
        return lift(new OperatorTakeUntil(other));
    }

    public final Observable<T> takeWhile(Func1<? super T, Boolean> predicate) {
        return lift(new OperatorTakeWhile(predicate));
    }

    public final Observable<T> takeUntil(Func1<? super T, Boolean> stopPredicate) {
        return lift(new OperatorTakeUntilPredicate(stopPredicate));
    }

    public final Observable<T> throttleFirst(long windowDuration, TimeUnit unit) {
        return throttleFirst(windowDuration, unit, Schedulers.computation());
    }

    public final Observable<T> throttleFirst(long skipDuration, TimeUnit unit, Scheduler scheduler) {
        return lift(new OperatorThrottleFirst(skipDuration, unit, scheduler));
    }

    public final Observable<T> throttleLast(long intervalDuration, TimeUnit unit) {
        return sample(intervalDuration, unit);
    }

    public final Observable<T> throttleLast(long intervalDuration, TimeUnit unit, Scheduler scheduler) {
        return sample(intervalDuration, unit, scheduler);
    }

    public final Observable<T> throttleWithTimeout(long timeout, TimeUnit unit) {
        return debounce(timeout, unit);
    }

    public final Observable<T> throttleWithTimeout(long timeout, TimeUnit unit, Scheduler scheduler) {
        return debounce(timeout, unit, scheduler);
    }

    public final Observable<TimeInterval<T>> timeInterval() {
        return timeInterval(Schedulers.computation());
    }

    public final Observable<TimeInterval<T>> timeInterval(Scheduler scheduler) {
        return lift(new OperatorTimeInterval(scheduler));
    }

    public final <U, V> Observable<T> timeout(Func0<? extends Observable<U>> firstTimeoutSelector, Func1<? super T, ? extends Observable<V>> timeoutSelector) {
        return timeout(firstTimeoutSelector, timeoutSelector, null);
    }

    public final <U, V> Observable<T> timeout(Func0<? extends Observable<U>> firstTimeoutSelector, Func1<? super T, ? extends Observable<V>> timeoutSelector, Observable<? extends T> other) {
        if (timeoutSelector != null) {
            return lift(new OperatorTimeoutWithSelector(firstTimeoutSelector, timeoutSelector, other));
        }
        throw new NullPointerException("timeoutSelector is null");
    }

    public final <V> Observable<T> timeout(Func1<? super T, ? extends Observable<V>> timeoutSelector) {
        return timeout(null, timeoutSelector, null);
    }

    public final <V> Observable<T> timeout(Func1<? super T, ? extends Observable<V>> timeoutSelector, Observable<? extends T> other) {
        return timeout(null, timeoutSelector, other);
    }

    public final Observable<T> timeout(long timeout, TimeUnit timeUnit) {
        return timeout(timeout, timeUnit, null, Schedulers.computation());
    }

    public final Observable<T> timeout(long timeout, TimeUnit timeUnit, Observable<? extends T> other) {
        return timeout(timeout, timeUnit, other, Schedulers.computation());
    }

    public final Observable<T> timeout(long timeout, TimeUnit timeUnit, Observable<? extends T> other, Scheduler scheduler) {
        OperatorTimeout operatorTimeout = new OperatorTimeout(timeout, timeUnit, other, scheduler);
        return lift(operatorTimeout);
    }

    public final Observable<T> timeout(long timeout, TimeUnit timeUnit, Scheduler scheduler) {
        return timeout(timeout, timeUnit, null, scheduler);
    }

    public final Observable<Timestamped<T>> timestamp() {
        return timestamp(Schedulers.computation());
    }

    public final Observable<Timestamped<T>> timestamp(Scheduler scheduler) {
        return lift(new OperatorTimestamp(scheduler));
    }

    public final BlockingObservable<T> toBlocking() {
        return BlockingObservable.from(this);
    }

    public final Observable<List<T>> toList() {
        return lift(OperatorToObservableList.instance());
    }

    public final <K> Observable<Map<K, T>> toMap(Func1<? super T, ? extends K> keySelector) {
        return create((OnSubscribe<T>) new OnSubscribeToMap<T>(this, keySelector, UtilityFunctions.identity()));
    }

    public final <K, V> Observable<Map<K, V>> toMap(Func1<? super T, ? extends K> keySelector, Func1<? super T, ? extends V> valueSelector) {
        return create((OnSubscribe<T>) new OnSubscribeToMap<T>(this, keySelector, valueSelector));
    }

    public final <K, V> Observable<Map<K, V>> toMap(Func1<? super T, ? extends K> keySelector, Func1<? super T, ? extends V> valueSelector, Func0<? extends Map<K, V>> mapFactory) {
        return create((OnSubscribe<T>) new OnSubscribeToMap<T>(this, keySelector, valueSelector, mapFactory));
    }

    public final <K> Observable<Map<K, Collection<T>>> toMultimap(Func1<? super T, ? extends K> keySelector) {
        return create((OnSubscribe<T>) new OnSubscribeToMultimap<T>(this, keySelector, UtilityFunctions.identity()));
    }

    public final <K, V> Observable<Map<K, Collection<V>>> toMultimap(Func1<? super T, ? extends K> keySelector, Func1<? super T, ? extends V> valueSelector) {
        return create((OnSubscribe<T>) new OnSubscribeToMultimap<T>(this, keySelector, valueSelector));
    }

    public final <K, V> Observable<Map<K, Collection<V>>> toMultimap(Func1<? super T, ? extends K> keySelector, Func1<? super T, ? extends V> valueSelector, Func0<? extends Map<K, Collection<V>>> mapFactory) {
        return create((OnSubscribe<T>) new OnSubscribeToMultimap<T>(this, keySelector, valueSelector, mapFactory));
    }

    public final <K, V> Observable<Map<K, Collection<V>>> toMultimap(Func1<? super T, ? extends K> keySelector, Func1<? super T, ? extends V> valueSelector, Func0<? extends Map<K, Collection<V>>> mapFactory, Func1<? super K, ? extends Collection<V>> collectionFactory) {
        OnSubscribeToMultimap onSubscribeToMultimap = new OnSubscribeToMultimap(this, keySelector, valueSelector, mapFactory, collectionFactory);
        return create((OnSubscribe<T>) onSubscribeToMultimap);
    }

    public final Observable<List<T>> toSortedList() {
        return lift(new OperatorToObservableSortedList(10));
    }

    public final Observable<List<T>> toSortedList(Func2<? super T, ? super T, Integer> sortFunction) {
        return lift(new OperatorToObservableSortedList(sortFunction, 10));
    }

    @Beta
    public final Observable<List<T>> toSortedList(int initialCapacity) {
        return lift(new OperatorToObservableSortedList(initialCapacity));
    }

    @Beta
    public final Observable<List<T>> toSortedList(Func2<? super T, ? super T, Integer> sortFunction, int initialCapacity) {
        return lift(new OperatorToObservableSortedList(sortFunction, initialCapacity));
    }

    @Experimental
    public final Observable<T> sorted() {
        return toSortedList().flatMapIterable(UtilityFunctions.identity());
    }

    @Experimental
    public final Observable<T> sorted(Func2<? super T, ? super T, Integer> sortFunction) {
        return toSortedList(sortFunction).flatMapIterable(UtilityFunctions.identity());
    }

    public final Observable<T> unsubscribeOn(Scheduler scheduler) {
        return lift(new OperatorUnsubscribeOn(scheduler));
    }

    @Experimental
    public final <U, R> Observable<R> withLatestFrom(Observable<? extends U> other, Func2<? super T, ? super U, ? extends R> resultSelector) {
        return lift(new OperatorWithLatestFrom(other, resultSelector));
    }

    @Experimental
    public final <T1, T2, R> Observable<R> withLatestFrom(Observable<T1> o1, Observable<T2> o2, Func3<? super T, ? super T1, ? super T2, R> combiner) {
        return create((OnSubscribe<T>) new OperatorWithLatestFromMany<T>(this, new Observable[]{o1, o2}, null, Functions.fromFunc(combiner)));
    }

    @Experimental
    public final <T1, T2, T3, R> Observable<R> withLatestFrom(Observable<T1> o1, Observable<T2> o2, Observable<T3> o3, Func4<? super T, ? super T1, ? super T2, ? super T3, R> combiner) {
        return create((OnSubscribe<T>) new OperatorWithLatestFromMany<T>(this, new Observable[]{o1, o2, o3}, null, Functions.fromFunc(combiner)));
    }

    @Experimental
    public final <T1, T2, T3, T4, R> Observable<R> withLatestFrom(Observable<T1> o1, Observable<T2> o2, Observable<T3> o3, Observable<T4> o4, Func5<? super T, ? super T1, ? super T2, ? super T3, ? super T4, R> combiner) {
        return create((OnSubscribe<T>) new OperatorWithLatestFromMany<T>(this, new Observable[]{o1, o2, o3, o4}, null, Functions.fromFunc(combiner)));
    }

    @Experimental
    public final <T1, T2, T3, T4, T5, R> Observable<R> withLatestFrom(Observable<T1> o1, Observable<T2> o2, Observable<T3> o3, Observable<T4> o4, Observable<T5> o5, Func6<? super T, ? super T1, ? super T2, ? super T3, ? super T4, ? super T5, R> combiner) {
        return create((OnSubscribe<T>) new OperatorWithLatestFromMany<T>(this, new Observable[]{o1, o2, o3, o4, o5}, null, Functions.fromFunc(combiner)));
    }

    @Experimental
    public final <T1, T2, T3, T4, T5, T6, R> Observable<R> withLatestFrom(Observable<T1> o1, Observable<T2> o2, Observable<T3> o3, Observable<T4> o4, Observable<T5> o5, Observable<T6> o6, Func7<? super T, ? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, R> combiner) {
        return create((OnSubscribe<T>) new OperatorWithLatestFromMany<T>(this, new Observable[]{o1, o2, o3, o4, o5, o6}, null, Functions.fromFunc(combiner)));
    }

    @Experimental
    public final <T1, T2, T3, T4, T5, T6, T7, R> Observable<R> withLatestFrom(Observable<T1> o1, Observable<T2> o2, Observable<T3> o3, Observable<T4> o4, Observable<T5> o5, Observable<T6> o6, Observable<T7> o7, Func8<? super T, ? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, R> combiner) {
        return create((OnSubscribe<T>) new OperatorWithLatestFromMany<T>(this, new Observable[]{o1, o2, o3, o4, o5, o6, o7}, null, Functions.fromFunc(combiner)));
    }

    @Experimental
    public final <T1, T2, T3, T4, T5, T6, T7, T8, R> Observable<R> withLatestFrom(Observable<T1> o1, Observable<T2> o2, Observable<T3> o3, Observable<T4> o4, Observable<T5> o5, Observable<T6> o6, Observable<T7> o7, Observable<T8> o8, Func9<? super T, ? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super T6, ? super T7, ? super T8, R> combiner) {
        return create((OnSubscribe<T>) new OperatorWithLatestFromMany<T>(this, new Observable[]{o1, o2, o3, o4, o5, o6, o7, o8}, null, Functions.fromFunc(combiner)));
    }

    @Experimental
    public final <R> Observable<R> withLatestFrom(Observable<?>[] others, FuncN<R> combiner) {
        return create((OnSubscribe<T>) new OperatorWithLatestFromMany<T>(this, others, null, combiner));
    }

    @Experimental
    public final <R> Observable<R> withLatestFrom(Iterable<Observable<?>> others, FuncN<R> combiner) {
        return create((OnSubscribe<T>) new OperatorWithLatestFromMany<T>(this, null, others, combiner));
    }

    public final <TClosing> Observable<Observable<T>> window(Func0<? extends Observable<? extends TClosing>> closingSelector) {
        return lift(new OperatorWindowWithObservableFactory(closingSelector));
    }

    public final Observable<Observable<T>> window(int count) {
        return window(count, count);
    }

    public final Observable<Observable<T>> window(int count, int skip) {
        if (count <= 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("count > 0 required but it was ");
            sb.append(count);
            throw new IllegalArgumentException(sb.toString());
        } else if (skip > 0) {
            return lift(new OperatorWindowWithSize(count, skip));
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("skip > 0 required but it was ");
            sb2.append(skip);
            throw new IllegalArgumentException(sb2.toString());
        }
    }

    public final Observable<Observable<T>> window(long timespan, long timeshift, TimeUnit unit) {
        return window(timespan, timeshift, unit, Integer.MAX_VALUE, Schedulers.computation());
    }

    public final Observable<Observable<T>> window(long timespan, long timeshift, TimeUnit unit, Scheduler scheduler) {
        return window(timespan, timeshift, unit, Integer.MAX_VALUE, scheduler);
    }

    public final Observable<Observable<T>> window(long timespan, long timeshift, TimeUnit unit, int count, Scheduler scheduler) {
        OperatorWindowWithTime operatorWindowWithTime = new OperatorWindowWithTime(timespan, timeshift, unit, count, scheduler);
        return lift(operatorWindowWithTime);
    }

    public final Observable<Observable<T>> window(long timespan, TimeUnit unit) {
        return window(timespan, timespan, unit, Schedulers.computation());
    }

    public final Observable<Observable<T>> window(long timespan, TimeUnit unit, int count) {
        return window(timespan, unit, count, Schedulers.computation());
    }

    public final Observable<Observable<T>> window(long timespan, TimeUnit unit, int count, Scheduler scheduler) {
        return window(timespan, timespan, unit, count, scheduler);
    }

    public final Observable<Observable<T>> window(long timespan, TimeUnit unit, Scheduler scheduler) {
        return window(timespan, unit, Integer.MAX_VALUE, scheduler);
    }

    public final <TOpening, TClosing> Observable<Observable<T>> window(Observable<? extends TOpening> windowOpenings, Func1<? super TOpening, ? extends Observable<? extends TClosing>> closingSelector) {
        return lift(new OperatorWindowWithStartEndObservable(windowOpenings, closingSelector));
    }

    public final <U> Observable<Observable<T>> window(Observable<U> boundary) {
        return lift(new OperatorWindowWithObservable(boundary));
    }

    public final <T2, R> Observable<R> zipWith(Iterable<? extends T2> other, Func2<? super T, ? super T2, ? extends R> zipFunction) {
        return lift(new OperatorZipIterable(other, zipFunction));
    }

    public final <T2, R> Observable<R> zipWith(Observable<? extends T2> other, Func2<? super T, ? super T2, ? extends R> zipFunction) {
        return zip(this, other, zipFunction);
    }
}
