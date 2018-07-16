package oilutt.baseproject.view.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.nineoldandroids.animation.AnimatorListenerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

import oilutt.baseproject.R;
import oilutt.baseproject.utils.AnimationUtils;

public class SplashActivity extends BaseActivity {

    @BindView(R.id.txt_app_name)
    TextView txtAppName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        animationTxt();
    }

    private void animationTxt(){
        AnimatorListenerAdapter listener = new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(com.nineoldandroids.animation.Animator animation) {
                super.onAnimationEnd(animation);

            }
        };
        AnimationUtils.fadeInWithListener(txtAppName, 1000, listener);
    }
}
