package com.google.android.gms.internal;

import android.accounts.Account;
import com.google.android.gms.auth.account.WorkAccount;
import com.google.android.gms.auth.account.WorkAccountApi;
import com.google.android.gms.auth.account.WorkAccountApi.AddAccountResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;

public final class zzaug implements WorkAccountApi {
    /* access modifiers changed from: private */
    public static final Status zzecr = new Status(13);

    public final PendingResult<AddAccountResult> addWorkAccount(GoogleApiClient googleApiClient, String str) {
        return googleApiClient.zze(new zzaui(this, WorkAccount.API, googleApiClient, str));
    }

    public final PendingResult<Result> removeWorkAccount(GoogleApiClient googleApiClient, Account account) {
        return googleApiClient.zze(new zzauk(this, WorkAccount.API, googleApiClient, account));
    }

    public final void setWorkAuthenticatorEnabled(GoogleApiClient googleApiClient, boolean z) {
        setWorkAuthenticatorEnabledWithResult(googleApiClient, z);
    }

    public final PendingResult<Result> setWorkAuthenticatorEnabledWithResult(GoogleApiClient googleApiClient, boolean z) {
        return googleApiClient.zze(new zzauh(this, WorkAccount.API, googleApiClient, z));
    }
}
