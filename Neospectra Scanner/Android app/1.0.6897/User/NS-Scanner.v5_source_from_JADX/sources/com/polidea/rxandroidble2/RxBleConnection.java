package com.polidea.rxandroidble2;

import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import com.polidea.rxandroidble2.exceptions.BleGattException;
import com.polidea.rxandroidble2.internal.Priority;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import p005io.reactivex.Completable;
import p005io.reactivex.Observable;
import p005io.reactivex.ObservableTransformer;
import p005io.reactivex.Single;

public interface RxBleConnection {
    public static final int GATT_MTU_MAXIMUM = 517;
    public static final int GATT_MTU_MINIMUM = 23;
    public static final int GATT_READ_MTU_OVERHEAD = 1;
    public static final int GATT_WRITE_MTU_OVERHEAD = 3;

    @RequiresApi(api = 21)
    @Retention(RetentionPolicy.SOURCE)
    public @interface ConnectionPriority {
    }

    @Deprecated
    public interface Connector {
        Single<RxBleConnection> prepareConnection(boolean z);
    }

    public interface LongWriteOperationBuilder {
        Observable<byte[]> build();

        LongWriteOperationBuilder setBytes(@NonNull byte[] bArr);

        LongWriteOperationBuilder setCharacteristic(@NonNull BluetoothGattCharacteristic bluetoothGattCharacteristic);

        LongWriteOperationBuilder setCharacteristicUuid(@NonNull UUID uuid);

        LongWriteOperationBuilder setMaxBatchSize(@IntRange(from = 1, mo190to = 514) int i);

        LongWriteOperationBuilder setWriteOperationAckStrategy(@NonNull WriteOperationAckStrategy writeOperationAckStrategy);

        LongWriteOperationBuilder setWriteOperationRetryStrategy(@NonNull WriteOperationRetryStrategy writeOperationRetryStrategy);
    }

    public enum RxBleConnectionState {
        CONNECTING("CONNECTING"),
        CONNECTED("CONNECTED"),
        DISCONNECTED("DISCONNECTED"),
        DISCONNECTING("DISCONNECTING");
        
        private final String description;

        private RxBleConnectionState(String description2) {
            this.description = description2;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("RxBleConnectionState{");
            sb.append(this.description);
            sb.append('}');
            return sb.toString();
        }
    }

    public interface WriteOperationAckStrategy extends ObservableTransformer<Boolean, Boolean> {
    }

    public interface WriteOperationRetryStrategy extends ObservableTransformer<LongWriteFailure, LongWriteFailure> {

        public static class LongWriteFailure {
            final int batchIndex;
            final BleGattException cause;

            public LongWriteFailure(int batchIndex2, BleGattException cause2) {
                this.batchIndex = batchIndex2;
                this.cause = cause2;
            }

            public int getBatchIndex() {
                return this.batchIndex;
            }

            public BleGattException getCause() {
                return this.cause;
            }
        }
    }

    LongWriteOperationBuilder createNewLongWriteBuilder();

    Single<RxBleDeviceServices> discoverServices();

    Single<RxBleDeviceServices> discoverServices(@IntRange(from = 1) long j, @NonNull TimeUnit timeUnit);

    @Deprecated
    Single<BluetoothGattCharacteristic> getCharacteristic(@NonNull UUID uuid);

    int getMtu();

    <T> Observable<T> queue(@NonNull RxBleCustomOperation<T> rxBleCustomOperation);

    <T> Observable<T> queue(@NonNull RxBleCustomOperation<T> rxBleCustomOperation, Priority priority);

    Single<byte[]> readCharacteristic(@NonNull BluetoothGattCharacteristic bluetoothGattCharacteristic);

    Single<byte[]> readCharacteristic(@NonNull UUID uuid);

    Single<byte[]> readDescriptor(@NonNull BluetoothGattDescriptor bluetoothGattDescriptor);

    Single<byte[]> readDescriptor(@NonNull UUID uuid, @NonNull UUID uuid2, @NonNull UUID uuid3);

    Single<Integer> readRssi();

    @RequiresApi(api = 21)
    Completable requestConnectionPriority(int i, @IntRange(from = 1) long j, @NonNull TimeUnit timeUnit);

    @RequiresApi(api = 21)
    Single<Integer> requestMtu(@IntRange(from = 23, mo190to = 517) int i);

    Observable<Observable<byte[]>> setupIndication(@NonNull BluetoothGattCharacteristic bluetoothGattCharacteristic);

    Observable<Observable<byte[]>> setupIndication(@NonNull BluetoothGattCharacteristic bluetoothGattCharacteristic, @NonNull NotificationSetupMode notificationSetupMode);

    Observable<Observable<byte[]>> setupIndication(@NonNull UUID uuid);

    Observable<Observable<byte[]>> setupIndication(@NonNull UUID uuid, @NonNull NotificationSetupMode notificationSetupMode);

    Observable<Observable<byte[]>> setupNotification(@NonNull BluetoothGattCharacteristic bluetoothGattCharacteristic);

    Observable<Observable<byte[]>> setupNotification(@NonNull BluetoothGattCharacteristic bluetoothGattCharacteristic, @NonNull NotificationSetupMode notificationSetupMode);

    Observable<Observable<byte[]>> setupNotification(@NonNull UUID uuid);

    Observable<Observable<byte[]>> setupNotification(@NonNull UUID uuid, @NonNull NotificationSetupMode notificationSetupMode);

    Single<byte[]> writeCharacteristic(@NonNull BluetoothGattCharacteristic bluetoothGattCharacteristic, @NonNull byte[] bArr);

    Single<byte[]> writeCharacteristic(@NonNull UUID uuid, @NonNull byte[] bArr);

    Completable writeDescriptor(@NonNull BluetoothGattDescriptor bluetoothGattDescriptor, @NonNull byte[] bArr);

    Completable writeDescriptor(@NonNull UUID uuid, @NonNull UUID uuid2, @NonNull UUID uuid3, @NonNull byte[] bArr);
}
