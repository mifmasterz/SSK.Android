package com.polidea.rxandroidble2;

import bleshadow.dagger.internal.Factory;
import bleshadow.dagger.internal.Preconditions;
import com.polidea.rxandroidble2.ClientComponent.ClientModule;

public final class ClientComponent_ClientModule_ProvideEnableIndicationValueFactory implements Factory<byte[]> {
    private static final ClientComponent_ClientModule_ProvideEnableIndicationValueFactory INSTANCE = new ClientComponent_ClientModule_ProvideEnableIndicationValueFactory();

    public byte[] get() {
        return (byte[]) Preconditions.checkNotNull(ClientModule.provideEnableIndicationValue(), "Cannot return null from a non-@Nullable @Provides method");
    }

    public static ClientComponent_ClientModule_ProvideEnableIndicationValueFactory create() {
        return INSTANCE;
    }

    public static byte[] proxyProvideEnableIndicationValue() {
        return (byte[]) Preconditions.checkNotNull(ClientModule.provideEnableIndicationValue(), "Cannot return null from a non-@Nullable @Provides method");
    }
}
