package android.support.p004v7.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.p001v4.widget.ImageViewCompat;
import android.support.p004v7.appcompat.C1123R;
import android.support.p004v7.content.res.AppCompatResources;
import android.util.AttributeSet;
import android.widget.ImageView;

@RestrictTo({Scope.LIBRARY_GROUP})
/* renamed from: android.support.v7.widget.AppCompatImageHelper */
public class AppCompatImageHelper {
    private TintInfo mImageTint;
    private TintInfo mInternalImageTint;
    private TintInfo mTmpInfo;
    private final ImageView mView;

    public AppCompatImageHelper(ImageView view) {
        this.mView = view;
    }

    public void loadFromAttributes(AttributeSet attrs, int defStyleAttr) {
        TintTypedArray a = TintTypedArray.obtainStyledAttributes(this.mView.getContext(), attrs, C1123R.styleable.AppCompatImageView, defStyleAttr, 0);
        try {
            Drawable drawable = this.mView.getDrawable();
            if (drawable == null) {
                int id = a.getResourceId(C1123R.styleable.AppCompatImageView_srcCompat, -1);
                if (id != -1) {
                    drawable = AppCompatResources.getDrawable(this.mView.getContext(), id);
                    if (drawable != null) {
                        this.mView.setImageDrawable(drawable);
                    }
                }
            }
            if (drawable != null) {
                DrawableUtils.fixDrawable(drawable);
            }
            if (a.hasValue(C1123R.styleable.AppCompatImageView_tint)) {
                ImageViewCompat.setImageTintList(this.mView, a.getColorStateList(C1123R.styleable.AppCompatImageView_tint));
            }
            if (a.hasValue(C1123R.styleable.AppCompatImageView_tintMode)) {
                ImageViewCompat.setImageTintMode(this.mView, DrawableUtils.parseTintMode(a.getInt(C1123R.styleable.AppCompatImageView_tintMode, -1), null));
            }
        } finally {
            a.recycle();
        }
    }

    public void setImageResource(int resId) {
        if (resId != 0) {
            Drawable d = AppCompatResources.getDrawable(this.mView.getContext(), resId);
            if (d != null) {
                DrawableUtils.fixDrawable(d);
            }
            this.mView.setImageDrawable(d);
        } else {
            this.mView.setImageDrawable(null);
        }
        applySupportImageTint();
    }

    /* access modifiers changed from: 0000 */
    public boolean hasOverlappingRendering() {
        Drawable background = this.mView.getBackground();
        if (VERSION.SDK_INT < 21 || !(background instanceof RippleDrawable)) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: 0000 */
    public void setSupportImageTintList(ColorStateList tint) {
        if (this.mImageTint == null) {
            this.mImageTint = new TintInfo();
        }
        this.mImageTint.mTintList = tint;
        this.mImageTint.mHasTintList = true;
        applySupportImageTint();
    }

    /* access modifiers changed from: 0000 */
    public ColorStateList getSupportImageTintList() {
        if (this.mImageTint != null) {
            return this.mImageTint.mTintList;
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public void setSupportImageTintMode(Mode tintMode) {
        if (this.mImageTint == null) {
            this.mImageTint = new TintInfo();
        }
        this.mImageTint.mTintMode = tintMode;
        this.mImageTint.mHasTintMode = true;
        applySupportImageTint();
    }

    /* access modifiers changed from: 0000 */
    public Mode getSupportImageTintMode() {
        if (this.mImageTint != null) {
            return this.mImageTint.mTintMode;
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public void applySupportImageTint() {
        Drawable imageViewDrawable = this.mView.getDrawable();
        if (imageViewDrawable != null) {
            DrawableUtils.fixDrawable(imageViewDrawable);
        }
        if (imageViewDrawable != null && (!shouldApplyFrameworkTintUsingColorFilter() || !applyFrameworkTintUsingColorFilter(imageViewDrawable))) {
            if (this.mImageTint != null) {
                AppCompatDrawableManager.tintDrawable(imageViewDrawable, this.mImageTint, this.mView.getDrawableState());
            } else if (this.mInternalImageTint != null) {
                AppCompatDrawableManager.tintDrawable(imageViewDrawable, this.mInternalImageTint, this.mView.getDrawableState());
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void setInternalImageTint(ColorStateList tint) {
        if (tint != null) {
            if (this.mInternalImageTint == null) {
                this.mInternalImageTint = new TintInfo();
            }
            this.mInternalImageTint.mTintList = tint;
            this.mInternalImageTint.mHasTintList = true;
        } else {
            this.mInternalImageTint = null;
        }
        applySupportImageTint();
    }

    private boolean shouldApplyFrameworkTintUsingColorFilter() {
        int sdk = VERSION.SDK_INT;
        boolean z = false;
        if (sdk <= 21) {
            return sdk == 21;
        }
        if (this.mInternalImageTint != null) {
            z = true;
        }
        return z;
    }

    private boolean applyFrameworkTintUsingColorFilter(@NonNull Drawable imageSource) {
        if (this.mTmpInfo == null) {
            this.mTmpInfo = new TintInfo();
        }
        TintInfo info = this.mTmpInfo;
        info.clear();
        ColorStateList tintList = ImageViewCompat.getImageTintList(this.mView);
        if (tintList != null) {
            info.mHasTintList = true;
            info.mTintList = tintList;
        }
        Mode mode = ImageViewCompat.getImageTintMode(this.mView);
        if (mode != null) {
            info.mHasTintMode = true;
            info.mTintMode = mode;
        }
        if (!info.mHasTintList && !info.mHasTintMode) {
            return false;
        }
        AppCompatDrawableManager.tintDrawable(imageSource, info, this.mView.getDrawableState());
        return true;
    }
}
