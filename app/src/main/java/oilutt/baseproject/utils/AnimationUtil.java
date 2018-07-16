

package oilutt.baseproject.utils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

public final class AnimationUtil {
    private AnimationUtil() {

    }

    /**
     * Applies a Scale Animation into a View.
     *
     * @param view The view that will be animated.
     * @param from From scale
     * @param to   To scale
     * @return ObjectAnimator
     */
    public static AnimatorSet scaleAnimation(final View view, int from, int to) {
        final AnimatorSet set = new AnimatorSet();

        set.play(ObjectAnimator.ofFloat(view, View.SCALE_X, from, to))
                .with(ObjectAnimator.ofFloat(view, View.SCALE_Y, from, to));
        set.setInterpolator(new AccelerateInterpolator());

        return set;
    }

    /**
     * Applies a Fade In Animation into a View.
     *
     * @param context Activity Context
     * @param view    The view that will be animated.
     * @return ObjectAnimator
     */
    @NonNull
    public static ObjectAnimator fadeInAnimation(final Context context, final View view) {
        final Resources resources = context.getResources();
        return ObjectAnimator.ofFloat(view, View.ALPHA, 0, 1).setDuration(resources.getInteger(android.R.integer.config_longAnimTime));
    }


    /**
     * Applies a Fade Out Animation into a View.
     *
     * @param view The view that will be animated.
     * @return ObjectAnimator
     * @deprecated use {@link #fadeOutAnimation(Context, View)} instead
     */
    @Deprecated
    public static ObjectAnimator fadeOutAnimation(final View view) {
        return ObjectAnimator.ofFloat(view, View.ALPHA, 1, 0);
    }

    /**
     * Applies a Fade Out Animation into a View.
     *
     * @param context Activity Context
     * @param view    The view that will be animated.
     * @return ObjectAnimator
     */
    public static ObjectAnimator fadeOutAnimation(final Context context, final View view) {
        final Resources resources = context.getResources();
        return ObjectAnimator.ofFloat(view, View.ALPHA, 1, 0).setDuration(resources.getInteger(android.R.integer.config_longAnimTime));
    }
}
