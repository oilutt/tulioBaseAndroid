package oilutt.baseproject.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatDelegate;

public final class ResourcesUtil {

    private ResourcesUtil() {
        super();
    }

    /**
     * Busca o id de um Resource a partir de seu nome
     *
     * @param context      Contexto da Aplicação
     * @param resourceName Nome do Resource
     * @param resourceType Tipo de Resource (string, drawable etc)
     * @param packageName  Pacote que o Resource se encontra
     * @return Id do Resource
     */
    private static int getResourceId(Context context, String resourceName, String resourceType, String packageName) {
        return context.getResources().getIdentifier(resourceName, resourceType, packageName);
    }

    /**
     * @deprecated Use getDrawableByString(Context, String)
     */
    @Deprecated
    @DrawableRes
    public static int getDrawableByString(Context context, String resourceName, String packageName) {
        return getResourceId(context, resourceName, "drawable", packageName);
    }

    /**
     * Busca o Id de um Color pelo seu nome
     *
     * @param context      Contexto da Aplicação
     * @param resourceName Nome do Resource
     * @param packageName  Pacote que o Resource se encontra
     * @return Id do Resource
     */
    @ColorRes
    public static int getColorResourceByString(Context context, String resourceName) {
        return getResourceId(context, resourceName, "color", context.getPackageName());
    }

    /**
     * Busca o Id de um Drawable pelo seu nome
     *
     * @param context      Contexto da Aplicação
     * @param resourceName Nome do Resource
     * @param packageName  Pacote que o Resource se encontra
     * @return Id do Resource
     */
    @DrawableRes
    public static int getDrawableByString(Context context, String resourceName) {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        return getResourceId(context, resourceName, "drawable", context.getPackageName());
    }

    /**
     * Adiciona efeito de brilho em drawables
     *
     * @param context    Contexto da Aplicação
     * @param resourceId Id do drawable
     * @param margin     margem do efeito
     * @param glowColor  cor do efeito
     * @param glowRadius raio do efeito
     * @return Novo Drawable com o efeito aplicado.
     */
    public static Drawable setGlowEffect(Context context, @DrawableRes int resourceId, int margin,
                                         @ColorInt int glowColor, int glowRadius) {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        int halfMargin = margin / 2;

        Bitmap source = BitmapFactory.decodeResource(context.getResources(), resourceId);
        Bitmap alpha = source.extractAlpha();

        Bitmap bmp = Bitmap.createBitmap(source.getWidth() + margin, source.getHeight() + margin,
                Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bmp);

        Paint paint = new Paint();
        paint.setColor(glowColor);

        paint.setMaskFilter(new BlurMaskFilter(glowRadius, BlurMaskFilter.Blur.OUTER));
        canvas.drawBitmap(alpha, halfMargin, halfMargin, paint);

        canvas.drawBitmap(source, halfMargin, halfMargin, null);

        return new BitmapDrawable(context.getResources(), bmp);
    }

    @StringRes
    public static int getString(Context context, String resourceName) {
        return getResourceId(context, resourceName, "string", context.getPackageName());
    }
}
