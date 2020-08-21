package robsonmachczew.asianname;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.TransitionDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.logging.Handler;

import static java.lang.Thread.sleep;

public class SplashActivity extends AppCompatActivity {

    private ImageView image;
    private TransitionDrawable trans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //status bar transparent >> deixar com noactionbar no style theme
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        image = findViewById(R.id.image);
        trans = (TransitionDrawable) this.getResources().getDrawable(R.drawable.transition);
        image.setImageDrawable(trans);
        trans.reverseTransition(2700);

        Thread timer = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    final Intent itTelainicial = new Intent (SplashActivity.this, MainActivity.class);
                    startActivity(itTelainicial);
                    finish();
                }
            }
        });
        timer.start();

    }
}
