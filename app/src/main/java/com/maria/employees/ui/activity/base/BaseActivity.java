package com.maria.employees.ui.activity.base;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import com.maria.employees.R;
import com.maria.employees.ui.fragment.specialities.SpecialitiesFragment;
import com.maria.employees.ui.uitools.UiTools;

public class BaseActivity extends AppCompatActivity implements BaseView {

    private static final String KEY_TURN = "turn";
    private final BasePresenter mPresenter = new BasePresenter();
    private ProgressBar mProgressBar;
    private SpecialitiesFragment mSpecialitiesFrafment;
    private boolean turn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        initViews();

        turn = savedInstanceState != null && savedInstanceState.getBoolean(KEY_TURN);

        mPresenter.onCreate(this, turn);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean(KEY_TURN, true);
    }

    @Override
    protected void onStop() {
        super.onStop();

        /*if (mSpecialitiesFrafment != null) {
            FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
            trans.detach(mSpecialitiesFrafment);
            trans.commit();
        }*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showMessage(String message) {
        UiTools.showMessage(message, this);
    }

    @Override
    public void showSpecialitiesList() {
        mProgressBar.setVisibility(View.GONE);

        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        mSpecialitiesFrafment = SpecialitiesFragment.newInstance();
        trans.add(R.id.base_contanier, mSpecialitiesFrafment);
        trans.commit();
    }

    private void initViews() {
        mProgressBar = findViewById(R.id.progressBar);
        mProgressBar.setVisibility(View.GONE);
    }
}
