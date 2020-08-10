package com.google.android.gms.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import java.util.Iterator;

public final class zzcgx extends zzbfm implements Iterable<String> {
    public static final Creator<zzcgx> CREATOR = new zzcgz();
    /* access modifiers changed from: private */
    public final Bundle zzebe;

    zzcgx(Bundle bundle) {
        this.zzebe = bundle;
    }

    /* access modifiers changed from: 0000 */
    public final Object get(String str) {
        return this.zzebe.get(str);
    }

    /* access modifiers changed from: 0000 */
    public final Double getDouble(String str) {
        return Double.valueOf(this.zzebe.getDouble(str));
    }

    /* access modifiers changed from: 0000 */
    public final Long getLong(String str) {
        return Long.valueOf(this.zzebe.getLong(str));
    }

    /* access modifiers changed from: 0000 */
    public final String getString(String str) {
        return this.zzebe.getString(str);
    }

    public final Iterator<String> iterator() {
        return new zzcgy(this);
    }

    public final int size() {
        return this.zzebe.size();
    }

    public final String toString() {
        return this.zzebe.toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbfp.zze(parcel);
        zzbfp.zza(parcel, 2, zzayx(), false);
        zzbfp.zzai(parcel, zze);
    }

    public final Bundle zzayx() {
        return new Bundle(this.zzebe);
    }
}
