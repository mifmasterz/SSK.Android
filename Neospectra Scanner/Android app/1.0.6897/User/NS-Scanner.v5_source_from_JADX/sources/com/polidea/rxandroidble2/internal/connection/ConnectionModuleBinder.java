package com.polidea.rxandroidble2.internal.connection;

import android.bluetooth.BluetoothGatt;
import bleshadow.dagger.Binds;
import bleshadow.dagger.Module;
import bleshadow.dagger.Provides;
import bleshadow.dagger.multibindings.IntoSet;
import bleshadow.javax.inject.Named;
import com.polidea.rxandroidble2.RxBleConnection;
import com.polidea.rxandroidble2.RxBleConnection.LongWriteOperationBuilder;
import com.polidea.rxandroidble2.internal.operations.OperationsProvider;
import com.polidea.rxandroidble2.internal.operations.OperationsProviderImpl;
import com.polidea.rxandroidble2.internal.serialization.ConnectionOperationQueue;
import com.polidea.rxandroidble2.internal.serialization.ConnectionOperationQueueImpl;

@Module
abstract class ConnectionModuleBinder {
    /* access modifiers changed from: 0000 */
    @Binds
    public abstract ConnectionOperationQueue bindConnectionOperationQueue(ConnectionOperationQueueImpl connectionOperationQueueImpl);

    /* access modifiers changed from: 0000 */
    @IntoSet
    @Binds
    public abstract ConnectionSubscriptionWatcher bindConnectionQueueSubscriptionWatcher(ConnectionOperationQueueImpl connectionOperationQueueImpl);

    /* access modifiers changed from: 0000 */
    @Binds
    public abstract MtuProvider bindCurrentMtuProvider(MtuWatcher mtuWatcher);

    /* access modifiers changed from: 0000 */
    @IntoSet
    @Binds
    public abstract ConnectionSubscriptionWatcher bindDisconnectActionSubscriptionWatcher(DisconnectAction disconnectAction);

    /* access modifiers changed from: 0000 */
    @Binds
    public abstract DisconnectionRouterInput bindDisconnectionRouterInput(DisconnectionRouter disconnectionRouter);

    /* access modifiers changed from: 0000 */
    @Binds
    public abstract DisconnectionRouterOutput bindDisconnectionRouterOutput(DisconnectionRouter disconnectionRouter);

    /* access modifiers changed from: 0000 */
    @Binds
    public abstract LongWriteOperationBuilder bindLongWriteOperationBuilder(LongWriteOperationBuilderImpl longWriteOperationBuilderImpl);

    /* access modifiers changed from: 0000 */
    @IntoSet
    @Binds
    public abstract ConnectionSubscriptionWatcher bindMtuWatcherSubscriptionWatcher(MtuWatcher mtuWatcher);

    /* access modifiers changed from: 0000 */
    @Binds
    public abstract OperationsProvider bindOperationsProvider(OperationsProviderImpl operationsProviderImpl);

    /* access modifiers changed from: 0000 */
    @Binds
    @ConnectionScope
    public abstract RxBleConnection bindRxBleConnection(RxBleConnectionImpl rxBleConnectionImpl);

    ConnectionModuleBinder() {
    }

    @Provides
    @Named("GATT_WRITE_MTU_OVERHEAD")
    static int gattWriteMtuOverhead() {
        return 3;
    }

    @Provides
    @Named("GATT_MTU_MINIMUM")
    static int minimumMtu() {
        return 23;
    }

    @ConnectionScope
    @Provides
    static BluetoothGatt provideBluetoothGatt(BluetoothGattProvider bluetoothGattProvider) {
        return bluetoothGattProvider.getBluetoothGatt();
    }
}
