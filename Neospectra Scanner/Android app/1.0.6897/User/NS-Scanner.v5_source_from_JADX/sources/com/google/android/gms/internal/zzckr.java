package com.google.android.gms.internal;

import android.os.RemoteException;
import android.text.TextUtils;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

final class zzckr implements Runnable {
    private /* synthetic */ String zzimf;
    private /* synthetic */ zzcgi zzjgn;
    private /* synthetic */ String zzjgq;
    private /* synthetic */ String zzjgr;
    private /* synthetic */ boolean zzjhf;
    private /* synthetic */ zzckg zzjij;
    private /* synthetic */ AtomicReference zzjik;

    zzckr(zzckg zzckg, AtomicReference atomicReference, String str, String str2, String str3, boolean z, zzcgi zzcgi) {
        this.zzjij = zzckg;
        this.zzjik = atomicReference;
        this.zzimf = str;
        this.zzjgq = str2;
        this.zzjgr = str3;
        this.zzjhf = z;
        this.zzjgn = zzcgi;
    }

    public final void run() {
        AtomicReference atomicReference;
        AtomicReference atomicReference2;
        List zza;
        synchronized (this.zzjik) {
            try {
                zzche zzd = this.zzjij.zzjid;
                if (zzd == null) {
                    this.zzjij.zzawy().zzazd().zzd("Failed to get user properties", zzchm.zzjk(this.zzimf), this.zzjgq, this.zzjgr);
                    this.zzjik.set(Collections.emptyList());
                    this.zzjik.notify();
                    return;
                }
                if (TextUtils.isEmpty(this.zzimf)) {
                    atomicReference2 = this.zzjik;
                    zza = zzd.zza(this.zzjgq, this.zzjgr, this.zzjhf, this.zzjgn);
                } else {
                    atomicReference2 = this.zzjik;
                    zza = zzd.zza(this.zzimf, this.zzjgq, this.zzjgr, this.zzjhf);
                }
                atomicReference2.set(zza);
                this.zzjij.zzxr();
                atomicReference = this.zzjik;
                atomicReference.notify();
            } catch (RemoteException e) {
                try {
                    this.zzjij.zzawy().zzazd().zzd("Failed to get user properties", zzchm.zzjk(this.zzimf), this.zzjgq, e);
                    this.zzjik.set(Collections.emptyList());
                    atomicReference = this.zzjik;
                } catch (Throwable th) {
                    this.zzjik.notify();
                    throw th;
                }
            }
        }
    }
}
