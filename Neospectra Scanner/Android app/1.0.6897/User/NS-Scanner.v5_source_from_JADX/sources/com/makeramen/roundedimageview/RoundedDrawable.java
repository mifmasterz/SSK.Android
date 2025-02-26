package com.makeramen.roundedimageview;

import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Matrix.ScaleToFit;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView.ScaleType;
import java.util.HashSet;
import java.util.Set;

public class RoundedDrawable extends Drawable {
    public static final int DEFAULT_BORDER_COLOR = -16777216;
    public static final String TAG = "RoundedDrawable";
    private final Bitmap mBitmap;
    private final int mBitmapHeight;
    private final Paint mBitmapPaint;
    private final RectF mBitmapRect = new RectF();
    private final int mBitmapWidth;
    private ColorStateList mBorderColor = ColorStateList.valueOf(-16777216);
    private final Paint mBorderPaint;
    private final RectF mBorderRect = new RectF();
    private float mBorderWidth = 0.0f;
    private final RectF mBounds = new RectF();
    private float mCornerRadius = 0.0f;
    private final boolean[] mCornersRounded = {true, true, true, true};
    private final RectF mDrawableRect = new RectF();
    private boolean mOval = false;
    private boolean mRebuildShader = true;
    private ScaleType mScaleType = ScaleType.FIT_CENTER;
    private final Matrix mShaderMatrix = new Matrix();
    private final RectF mSquareCornersRect = new RectF();
    private TileMode mTileModeX = TileMode.CLAMP;
    private TileMode mTileModeY = TileMode.CLAMP;

    /* renamed from: com.makeramen.roundedimageview.RoundedDrawable$1 */
    static /* synthetic */ class C06881 {
        static final /* synthetic */ int[] $SwitchMap$android$widget$ImageView$ScaleType = new int[ScaleType.values().length];

        static {
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ScaleType.CENTER.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ScaleType.CENTER_CROP.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ScaleType.CENTER_INSIDE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ScaleType.FIT_CENTER.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ScaleType.FIT_END.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ScaleType.FIT_START.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ScaleType.FIT_XY.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
        }
    }

    public RoundedDrawable(Bitmap bitmap) {
        this.mBitmap = bitmap;
        this.mBitmapWidth = bitmap.getWidth();
        this.mBitmapHeight = bitmap.getHeight();
        this.mBitmapRect.set(0.0f, 0.0f, (float) this.mBitmapWidth, (float) this.mBitmapHeight);
        this.mBitmapPaint = new Paint();
        this.mBitmapPaint.setStyle(Style.FILL);
        this.mBitmapPaint.setAntiAlias(true);
        this.mBorderPaint = new Paint();
        this.mBorderPaint.setStyle(Style.STROKE);
        this.mBorderPaint.setAntiAlias(true);
        this.mBorderPaint.setColor(this.mBorderColor.getColorForState(getState(), -16777216));
        this.mBorderPaint.setStrokeWidth(this.mBorderWidth);
    }

    public static RoundedDrawable fromBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            return new RoundedDrawable(bitmap);
        }
        return null;
    }

    public static Drawable fromDrawable(Drawable drawable) {
        if (drawable == null || (drawable instanceof RoundedDrawable)) {
            return drawable;
        }
        if (drawable instanceof LayerDrawable) {
            LayerDrawable ld = (LayerDrawable) drawable;
            int num = ld.getNumberOfLayers();
            for (int i = 0; i < num; i++) {
                ld.setDrawableByLayerId(ld.getId(i), fromDrawable(ld.getDrawable(i)));
            }
            return ld;
        }
        Bitmap bm = drawableToBitmap(drawable);
        if (bm != null) {
            return new RoundedDrawable(bm);
        }
        return drawable;
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap;
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        try {
            bitmap = Bitmap.createBitmap(Math.max(drawable.getIntrinsicWidth(), 2), Math.max(drawable.getIntrinsicHeight(), 2), Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
        } catch (Exception e) {
            e.printStackTrace();
            Log.w(TAG, "Failed to create bitmap from drawable!");
            bitmap = null;
        }
        return bitmap;
    }

    public Bitmap getSourceBitmap() {
        return this.mBitmap;
    }

    public boolean isStateful() {
        return this.mBorderColor.isStateful();
    }

    /* access modifiers changed from: protected */
    public boolean onStateChange(int[] state) {
        int newColor = this.mBorderColor.getColorForState(state, 0);
        if (this.mBorderPaint.getColor() == newColor) {
            return super.onStateChange(state);
        }
        this.mBorderPaint.setColor(newColor);
        return true;
    }

    private void updateShaderMatrix() {
        float scale;
        float scale2;
        switch (C06881.$SwitchMap$android$widget$ImageView$ScaleType[this.mScaleType.ordinal()]) {
            case 1:
                this.mBorderRect.set(this.mBounds);
                this.mBorderRect.inset(this.mBorderWidth / 2.0f, this.mBorderWidth / 2.0f);
                this.mShaderMatrix.reset();
                this.mShaderMatrix.setTranslate((float) ((int) (((this.mBorderRect.width() - ((float) this.mBitmapWidth)) * 0.5f) + 0.5f)), (float) ((int) (((this.mBorderRect.height() - ((float) this.mBitmapHeight)) * 0.5f) + 0.5f)));
                break;
            case 2:
                this.mBorderRect.set(this.mBounds);
                this.mBorderRect.inset(this.mBorderWidth / 2.0f, this.mBorderWidth / 2.0f);
                this.mShaderMatrix.reset();
                float dx = 0.0f;
                float dy = 0.0f;
                if (((float) this.mBitmapWidth) * this.mBorderRect.height() > this.mBorderRect.width() * ((float) this.mBitmapHeight)) {
                    scale = this.mBorderRect.height() / ((float) this.mBitmapHeight);
                    dx = (this.mBorderRect.width() - (((float) this.mBitmapWidth) * scale)) * 0.5f;
                } else {
                    scale = this.mBorderRect.width() / ((float) this.mBitmapWidth);
                    dy = (this.mBorderRect.height() - (((float) this.mBitmapHeight) * scale)) * 0.5f;
                }
                this.mShaderMatrix.setScale(scale, scale);
                this.mShaderMatrix.postTranslate(((float) ((int) (dx + 0.5f))) + (this.mBorderWidth / 2.0f), ((float) ((int) (0.5f + dy))) + (this.mBorderWidth / 2.0f));
                break;
            case 3:
                this.mShaderMatrix.reset();
                if (((float) this.mBitmapWidth) > this.mBounds.width() || ((float) this.mBitmapHeight) > this.mBounds.height()) {
                    scale2 = Math.min(this.mBounds.width() / ((float) this.mBitmapWidth), this.mBounds.height() / ((float) this.mBitmapHeight));
                } else {
                    scale2 = 1.0f;
                }
                float dx2 = (float) ((int) (((this.mBounds.width() - (((float) this.mBitmapWidth) * scale2)) * 0.5f) + 0.5f));
                float dy2 = (float) ((int) (((this.mBounds.height() - (((float) this.mBitmapHeight) * scale2)) * 0.5f) + 0.5f));
                this.mShaderMatrix.setScale(scale2, scale2);
                this.mShaderMatrix.postTranslate(dx2, dy2);
                this.mBorderRect.set(this.mBitmapRect);
                this.mShaderMatrix.mapRect(this.mBorderRect);
                this.mBorderRect.inset(this.mBorderWidth / 2.0f, this.mBorderWidth / 2.0f);
                this.mShaderMatrix.setRectToRect(this.mBitmapRect, this.mBorderRect, ScaleToFit.FILL);
                break;
            case 5:
                this.mBorderRect.set(this.mBitmapRect);
                this.mShaderMatrix.setRectToRect(this.mBitmapRect, this.mBounds, ScaleToFit.END);
                this.mShaderMatrix.mapRect(this.mBorderRect);
                this.mBorderRect.inset(this.mBorderWidth / 2.0f, this.mBorderWidth / 2.0f);
                this.mShaderMatrix.setRectToRect(this.mBitmapRect, this.mBorderRect, ScaleToFit.FILL);
                break;
            case 6:
                this.mBorderRect.set(this.mBitmapRect);
                this.mShaderMatrix.setRectToRect(this.mBitmapRect, this.mBounds, ScaleToFit.START);
                this.mShaderMatrix.mapRect(this.mBorderRect);
                this.mBorderRect.inset(this.mBorderWidth / 2.0f, this.mBorderWidth / 2.0f);
                this.mShaderMatrix.setRectToRect(this.mBitmapRect, this.mBorderRect, ScaleToFit.FILL);
                break;
            case 7:
                this.mBorderRect.set(this.mBounds);
                this.mBorderRect.inset(this.mBorderWidth / 2.0f, this.mBorderWidth / 2.0f);
                this.mShaderMatrix.reset();
                this.mShaderMatrix.setRectToRect(this.mBitmapRect, this.mBorderRect, ScaleToFit.FILL);
                break;
            default:
                this.mBorderRect.set(this.mBitmapRect);
                this.mShaderMatrix.setRectToRect(this.mBitmapRect, this.mBounds, ScaleToFit.CENTER);
                this.mShaderMatrix.mapRect(this.mBorderRect);
                this.mBorderRect.inset(this.mBorderWidth / 2.0f, this.mBorderWidth / 2.0f);
                this.mShaderMatrix.setRectToRect(this.mBitmapRect, this.mBorderRect, ScaleToFit.FILL);
                break;
        }
        this.mDrawableRect.set(this.mBorderRect);
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(@NonNull Rect bounds) {
        super.onBoundsChange(bounds);
        this.mBounds.set(bounds);
        updateShaderMatrix();
    }

    public void draw(@NonNull Canvas canvas) {
        if (this.mRebuildShader) {
            BitmapShader bitmapShader = new BitmapShader(this.mBitmap, this.mTileModeX, this.mTileModeY);
            if (this.mTileModeX == TileMode.CLAMP && this.mTileModeY == TileMode.CLAMP) {
                bitmapShader.setLocalMatrix(this.mShaderMatrix);
            }
            this.mBitmapPaint.setShader(bitmapShader);
            this.mRebuildShader = false;
        }
        if (this.mOval) {
            if (this.mBorderWidth > 0.0f) {
                canvas.drawOval(this.mDrawableRect, this.mBitmapPaint);
                canvas.drawOval(this.mBorderRect, this.mBorderPaint);
                return;
            }
            canvas.drawOval(this.mDrawableRect, this.mBitmapPaint);
        } else if (any(this.mCornersRounded)) {
            float radius = this.mCornerRadius;
            if (this.mBorderWidth > 0.0f) {
                canvas.drawRoundRect(this.mDrawableRect, radius, radius, this.mBitmapPaint);
                canvas.drawRoundRect(this.mBorderRect, radius, radius, this.mBorderPaint);
                redrawBitmapForSquareCorners(canvas);
                redrawBorderForSquareCorners(canvas);
                return;
            }
            canvas.drawRoundRect(this.mDrawableRect, radius, radius, this.mBitmapPaint);
            redrawBitmapForSquareCorners(canvas);
        } else {
            canvas.drawRect(this.mDrawableRect, this.mBitmapPaint);
            if (this.mBorderWidth > 0.0f) {
                canvas.drawRect(this.mBorderRect, this.mBorderPaint);
            }
        }
    }

    private void redrawBitmapForSquareCorners(Canvas canvas) {
        if (!all(this.mCornersRounded) && this.mCornerRadius != 0.0f) {
            float left = this.mDrawableRect.left;
            float top = this.mDrawableRect.top;
            float right = this.mDrawableRect.width() + left;
            float bottom = this.mDrawableRect.height() + top;
            float radius = this.mCornerRadius;
            if (!this.mCornersRounded[0]) {
                this.mSquareCornersRect.set(left, top, left + radius, top + radius);
                canvas.drawRect(this.mSquareCornersRect, this.mBitmapPaint);
            }
            if (!this.mCornersRounded[1]) {
                this.mSquareCornersRect.set(right - radius, top, right, radius);
                canvas.drawRect(this.mSquareCornersRect, this.mBitmapPaint);
            }
            if (!this.mCornersRounded[2]) {
                this.mSquareCornersRect.set(right - radius, bottom - radius, right, bottom);
                canvas.drawRect(this.mSquareCornersRect, this.mBitmapPaint);
            }
            if (!this.mCornersRounded[3]) {
                this.mSquareCornersRect.set(left, bottom - radius, left + radius, bottom);
                canvas.drawRect(this.mSquareCornersRect, this.mBitmapPaint);
            }
        }
    }

    private void redrawBorderForSquareCorners(Canvas canvas) {
        if (!all(this.mCornersRounded) && this.mCornerRadius != 0.0f) {
            float left = this.mDrawableRect.left;
            float top = this.mDrawableRect.top;
            float right = left + this.mDrawableRect.width();
            float bottom = top + this.mDrawableRect.height();
            float radius = this.mCornerRadius;
            float offset = this.mBorderWidth / 2.0f;
            if (!this.mCornersRounded[0]) {
                canvas.drawLine(left - offset, top, left + radius, top, this.mBorderPaint);
                canvas.drawLine(left, top - offset, left, top + radius, this.mBorderPaint);
            }
            if (!this.mCornersRounded[1]) {
                Canvas canvas2 = canvas;
                float f = right;
                canvas2.drawLine((right - radius) - offset, top, f, top, this.mBorderPaint);
                canvas2.drawLine(right, top - offset, f, top + radius, this.mBorderPaint);
            }
            if (!this.mCornersRounded[2]) {
                Canvas canvas3 = canvas;
                float f2 = bottom;
                canvas3.drawLine((right - radius) - offset, bottom, right + offset, f2, this.mBorderPaint);
                canvas3.drawLine(right, bottom - radius, right, f2, this.mBorderPaint);
            }
            if (!this.mCornersRounded[3]) {
                canvas.drawLine(left - offset, bottom, left + radius, bottom, this.mBorderPaint);
                canvas.drawLine(left, bottom - radius, left, bottom, this.mBorderPaint);
            }
        }
    }

    public int getOpacity() {
        return -3;
    }

    public int getAlpha() {
        return this.mBitmapPaint.getAlpha();
    }

    public void setAlpha(int alpha) {
        this.mBitmapPaint.setAlpha(alpha);
        invalidateSelf();
    }

    public ColorFilter getColorFilter() {
        return this.mBitmapPaint.getColorFilter();
    }

    public void setColorFilter(ColorFilter cf) {
        this.mBitmapPaint.setColorFilter(cf);
        invalidateSelf();
    }

    public void setDither(boolean dither) {
        this.mBitmapPaint.setDither(dither);
        invalidateSelf();
    }

    public void setFilterBitmap(boolean filter) {
        this.mBitmapPaint.setFilterBitmap(filter);
        invalidateSelf();
    }

    public int getIntrinsicWidth() {
        return this.mBitmapWidth;
    }

    public int getIntrinsicHeight() {
        return this.mBitmapHeight;
    }

    public float getCornerRadius() {
        return this.mCornerRadius;
    }

    public float getCornerRadius(int corner) {
        if (this.mCornersRounded[corner]) {
            return this.mCornerRadius;
        }
        return 0.0f;
    }

    public RoundedDrawable setCornerRadius(float radius) {
        setCornerRadius(radius, radius, radius, radius);
        return this;
    }

    public RoundedDrawable setCornerRadius(int corner, float radius) {
        if (radius == 0.0f || this.mCornerRadius == 0.0f || this.mCornerRadius == radius) {
            if (radius == 0.0f) {
                if (only(corner, this.mCornersRounded)) {
                    this.mCornerRadius = 0.0f;
                }
                this.mCornersRounded[corner] = false;
            } else {
                if (this.mCornerRadius == 0.0f) {
                    this.mCornerRadius = radius;
                }
                this.mCornersRounded[corner] = true;
            }
            return this;
        }
        throw new IllegalArgumentException("Multiple nonzero corner radii not yet supported.");
    }

    public RoundedDrawable setCornerRadius(float topLeft, float topRight, float bottomRight, float bottomLeft) {
        Set<Float> radiusSet = new HashSet<>(4);
        radiusSet.add(Float.valueOf(topLeft));
        radiusSet.add(Float.valueOf(topRight));
        radiusSet.add(Float.valueOf(bottomRight));
        radiusSet.add(Float.valueOf(bottomLeft));
        radiusSet.remove(Float.valueOf(0.0f));
        boolean z = true;
        if (radiusSet.size() > 1) {
            throw new IllegalArgumentException("Multiple nonzero corner radii not yet supported.");
        }
        if (!radiusSet.isEmpty()) {
            float radius = ((Float) radiusSet.iterator().next()).floatValue();
            if (Float.isInfinite(radius) || Float.isNaN(radius) || radius < 0.0f) {
                StringBuilder sb = new StringBuilder();
                sb.append("Invalid radius value: ");
                sb.append(radius);
                throw new IllegalArgumentException(sb.toString());
            }
            this.mCornerRadius = radius;
        } else {
            this.mCornerRadius = 0.0f;
        }
        this.mCornersRounded[0] = topLeft > 0.0f;
        this.mCornersRounded[1] = topRight > 0.0f;
        this.mCornersRounded[2] = bottomRight > 0.0f;
        boolean[] zArr = this.mCornersRounded;
        if (bottomLeft <= 0.0f) {
            z = false;
        }
        zArr[3] = z;
        return this;
    }

    public float getBorderWidth() {
        return this.mBorderWidth;
    }

    public RoundedDrawable setBorderWidth(float width) {
        this.mBorderWidth = width;
        this.mBorderPaint.setStrokeWidth(this.mBorderWidth);
        return this;
    }

    public int getBorderColor() {
        return this.mBorderColor.getDefaultColor();
    }

    public RoundedDrawable setBorderColor(@ColorInt int color) {
        return setBorderColor(ColorStateList.valueOf(color));
    }

    public ColorStateList getBorderColors() {
        return this.mBorderColor;
    }

    public RoundedDrawable setBorderColor(ColorStateList colors) {
        this.mBorderColor = colors != null ? colors : ColorStateList.valueOf(0);
        this.mBorderPaint.setColor(this.mBorderColor.getColorForState(getState(), -16777216));
        return this;
    }

    public boolean isOval() {
        return this.mOval;
    }

    public RoundedDrawable setOval(boolean oval) {
        this.mOval = oval;
        return this;
    }

    public ScaleType getScaleType() {
        return this.mScaleType;
    }

    public RoundedDrawable setScaleType(ScaleType scaleType) {
        if (scaleType == null) {
            scaleType = ScaleType.FIT_CENTER;
        }
        if (this.mScaleType != scaleType) {
            this.mScaleType = scaleType;
            updateShaderMatrix();
        }
        return this;
    }

    public TileMode getTileModeX() {
        return this.mTileModeX;
    }

    public RoundedDrawable setTileModeX(TileMode tileModeX) {
        if (this.mTileModeX != tileModeX) {
            this.mTileModeX = tileModeX;
            this.mRebuildShader = true;
            invalidateSelf();
        }
        return this;
    }

    public TileMode getTileModeY() {
        return this.mTileModeY;
    }

    public RoundedDrawable setTileModeY(TileMode tileModeY) {
        if (this.mTileModeY != tileModeY) {
            this.mTileModeY = tileModeY;
            this.mRebuildShader = true;
            invalidateSelf();
        }
        return this;
    }

    private static boolean only(int index, boolean[] booleans) {
        int i = 0;
        int len = booleans.length;
        while (true) {
            boolean z = true;
            if (i >= len) {
                return true;
            }
            boolean z2 = booleans[i];
            if (i != index) {
                z = false;
            }
            if (z2 != z) {
                return false;
            }
            i++;
        }
    }

    private static boolean any(boolean[] booleans) {
        for (boolean b : booleans) {
            if (b) {
                return true;
            }
        }
        return false;
    }

    private static boolean all(boolean[] booleans) {
        for (boolean b : booleans) {
            if (b) {
                return false;
            }
        }
        return true;
    }

    public Bitmap toBitmap() {
        return drawableToBitmap(this);
    }
}
