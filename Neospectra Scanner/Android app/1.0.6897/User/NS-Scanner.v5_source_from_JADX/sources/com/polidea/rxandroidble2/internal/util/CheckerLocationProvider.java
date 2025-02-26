package com.polidea.rxandroidble2.internal.util;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.location.LocationManager;
import android.os.Build.VERSION;
import android.provider.Settings.Secure;
import android.provider.Settings.SettingNotFoundException;
import bleshadow.javax.inject.Inject;
import com.polidea.rxandroidble2.internal.RxBleLog;

@TargetApi(19)
public class CheckerLocationProvider {
    private final ContentResolver contentResolver;
    private final LocationManager locationManager;

    @Inject
    CheckerLocationProvider(ContentResolver contentResolver2, LocationManager locationManager2) {
        this.contentResolver = contentResolver2;
        this.locationManager = locationManager2;
    }

    public boolean isLocationProviderEnabled() {
        boolean z = true;
        if (VERSION.SDK_INT >= 19) {
            try {
                if (Secure.getInt(this.contentResolver, "location_mode") == 0) {
                    z = false;
                }
                return z;
            } catch (SettingNotFoundException e) {
                RxBleLog.m26w(e, "Could not use LOCATION_MODE check. Falling back to legacy method.", new Object[0]);
            }
        }
        if (!this.locationManager.isProviderEnabled("network") && !this.locationManager.isProviderEnabled("gps")) {
            z = false;
        }
        return z;
    }
}
