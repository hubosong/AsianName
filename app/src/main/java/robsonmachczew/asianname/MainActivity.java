package robsonmachczew.asianname;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Animation animZoomin, animZoomout, animBlink;
    private ImageView imageView1;
    private Button accessBtn, contactBtn;
    private MediaPlayer player;
    //private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        overridePendingTransition(R.anim.right_in, R.anim.right_out);

        //usado para descobrir o local do celular e usar a string com idioma correto
        Locale locale = Locale.getDefault();
        String country = locale.getCountry();
        String language = locale.getLanguage();

        //status bar transparent >> deixar com noactionbar no style theme
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        //getWindow().setStatusBarColor(this.getResources().getColor(R.color.status_color));
        /*
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.status_color));
        */

        accessBtn = findViewById(R.id.access);
        accessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent namesActivity = new Intent(MainActivity.this, NamesActivity.class);
                overridePendingTransition(R.anim.right_in, R.anim.right_out);
                startActivity(namesActivity);
            }
        });
        contactBtn = findViewById(R.id.contact);
        contactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_contact, null);

                Button btn = mView.findViewById(R.id.close);

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; //ANIMATION
                dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
                dialog.show();

                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(MainActivity.this, "Thanks!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
            }
        });

        imageView1 = findViewById(R.id.imageView1);
        zoomin();
        delay(5);

        /*iniciar aplicativo tocando*/
        player = MediaPlayer.create(this, R.raw.yinyue);
        player.setLooping(true);
        player.setVolume(100,100);
        player.start();

    }




    public void zoomin(){
        animZoomin = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoom_in);
        imageView1.startAnimation(animZoomin);

    }
    public void zoomout(){
        animZoomout = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoom_out);
        imageView1.startAnimation(animZoomout);
    }
    public void delay(int seconds){
        final int milliseconds = seconds * 1000;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        zoomout();
                    }
                }, milliseconds);
            }
        });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            finish();
            Toast.makeText(this, "Bye Bye 拜 拜", Toast.LENGTH_LONG).show();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void yinyue(View view) {
        if(player.isPlaying()){
            player.pause();
        } else {
            player.start();
        }

        /*
        if(i == 0){
            super.onPause();
            player.stop();
            if (isFinishing()){
                player.pause();
            }
            i++;
        } else if(i == 1){
            player = MediaPlayer.create(this, R.raw.yinyue);
            player.release();
            i=0;
        }
        */

    }
    /*para o som quando sair*/
    protected void onDestroy(){
        if(player != null){
            player.stop();
        }
        super.onDestroy();
    }
}
