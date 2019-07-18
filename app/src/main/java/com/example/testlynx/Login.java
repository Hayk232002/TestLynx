package com.example.testlynx;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Guideline;

import android.os.Build;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Button;

public class Login extends AppCompatActivity {

    ConstraintLayout cl_login;
    Guideline line;
    Button btn_log_login;
    float prec = 42;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        cl_login = (ConstraintLayout) findViewById(R.id.cl_login);
        line = (Guideline) findViewById(R.id.line);
        btn_log_login = (Button) findViewById(R.id.btn_log_login);

        btn_log_login.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(cl_login);
                constraintSet.setGuidelinePercent(R.id.line, 0.42f);

                AutoTransition autoTransition = new AutoTransition();
                autoTransition.setDuration(200);
                TransitionManager.beginDelayedTransition(cl_login,autoTransition);
                constraintSet.applyTo(cl_login);
            }
        });
    }
}
