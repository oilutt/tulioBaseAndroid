package oilutt.baseproject.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import com.arellomobile.mvp.MvpAppCompatActivity;

import oilutt.baseproject.R;

abstract class BaseActivity extends MvpAppCompatActivity {

    protected Toolbar toolbar;
    protected boolean runningBackground = false;

    @Override
    protected void onResume() {
        super.onResume();
        runningBackground = false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        runningBackground = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void goToActivity(Context context, Class<?> target){
        startActivity(new Intent(context, target));
    }

    public void goToActivityClearTop(Context context, Class<?> target){
        Intent intent = new Intent(context, target);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(new Intent(context, target));
    }

    public void goToActivity(Context context, Class<?> target, Bundle extras){
        Intent intent = new Intent(context, target);
        intent.putExtras(extras);
        startActivity(intent);
    }

    protected void setupToolbar(boolean isBack) {
        toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            if (isBack) {
                toolbar.setNavigationIcon(R.drawable.ic_back);
                toolbar.setNavigationOnClickListener(getToolbarOnBackClick());
            }
            if(getSupportActionBar() != null)
                getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    public void setUpToolbarText(int title, boolean isBack) {
        setUpToolbarText(getString(title), isBack);
    }

    public void setUpToolbarText(String title, boolean isBack) {
        toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitleTextAppearance(this, R.style.TextTitleTooblar);
            setSupportActionBar(toolbar);
            assert getSupportActionBar() != null;
            if (!TextUtils.isEmpty(title)) {
                getSupportActionBar().setDisplayShowTitleEnabled(true);
                getSupportActionBar().setTitle(title);
            } else {
                getSupportActionBar().setDisplayShowTitleEnabled(false);
            }
            if (isBack) {
                toolbar.setNavigationIcon(R.drawable.ic_back);
                toolbar.setNavigationOnClickListener(v -> onBackPressed());
            }
        }
    }

    public void changeTitleToolbar(String text) {
        if (toolbar == null || getSupportActionBar() == null) return;
        getSupportActionBar().setTitle(text);
    }

    public void showBackToolback() {
        if (toolbar == null) return;
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    public void hideBackToolbar() {
        if (toolbar == null) return;
        toolbar.setNavigationIcon(null);
    }

    public void showSnack(int msg, View view) {
        showSnack(getString(msg), view);
    }

    public void showSnack(String msg, View view) {
        if (!TextUtils.isEmpty(msg)) {
            msg = msg.replace("\\n", " ");
            Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show();
        }
    }

    public void showSnackWithAction(String msg, String button, View view, View.OnClickListener clickListener){
        if (!TextUtils.isEmpty(msg) && !TextUtils.isEmpty(button)) {
            msg = msg.replace("\\n", " ");
            Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
                    .setAction(button, clickListener)
                    .show();
        }
    }

    public View.OnClickListener getToolbarOnBackClick() {
        return view -> finish();
    }
}
