package com.jakewharton.rxbinding.view;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.view.ViewGroup;
import com.jakewharton.rxbinding.internal.Preconditions;
import p008rx.Observable;
import p008rx.Observable.OnSubscribe;

public final class RxViewGroup {
    @CheckResult
    @NonNull
    public static Observable<ViewGroupHierarchyChangeEvent> changeEvents(@NonNull ViewGroup viewGroup) {
        Preconditions.checkNotNull(viewGroup, "viewGroup == null");
        return Observable.create((OnSubscribe<T>) new ViewGroupHierarchyChangeEventOnSubscribe<T>(viewGroup));
    }

    private RxViewGroup() {
        throw new AssertionError("No instances.");
    }
}
