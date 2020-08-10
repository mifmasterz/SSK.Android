package com.polidea.rxandroidble2.internal.connection;

import com.polidea.rxandroidble2.ConnectionSetup;
import com.polidea.rxandroidble2.RxBleConnection;
import p005io.reactivex.Observable;

public interface Connector {
    Observable<RxBleConnection> prepareConnection(ConnectionSetup connectionSetup);
}
