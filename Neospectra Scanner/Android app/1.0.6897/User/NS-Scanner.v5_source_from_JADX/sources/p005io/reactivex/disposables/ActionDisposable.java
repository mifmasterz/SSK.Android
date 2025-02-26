package p005io.reactivex.disposables;

import p005io.reactivex.annotations.NonNull;
import p005io.reactivex.functions.Action;
import p005io.reactivex.internal.util.ExceptionHelper;

/* renamed from: io.reactivex.disposables.ActionDisposable */
final class ActionDisposable extends ReferenceDisposable<Action> {
    private static final long serialVersionUID = -8219729196779211169L;

    ActionDisposable(Action value) {
        super(value);
    }

    /* access modifiers changed from: protected */
    public void onDisposed(@NonNull Action value) {
        try {
            value.run();
        } catch (Throwable ex) {
            throw ExceptionHelper.wrapOrThrow(ex);
        }
    }
}
