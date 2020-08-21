package robsonmachczew.asianname;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;

public class NamesActivity extends AppCompatActivity {

    private EditText edtName, edtClear;
    private TextView txtNames, txtPinYin, txtLanguage;
    private TextToSpeech voice;
    private Typeface cnName, cnPinYin, normal;

    private String name;

    private ClipboardManager clipboardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_names);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        edtName = findViewById(R.id.edt_nome);
        txtNames = findViewById(R.id.txt_name);
        txtPinYin = findViewById(R.id.txt_pinyin);
        txtLanguage = findViewById(R.id.txt_language);

        edtClear = findViewById(R.id.edt_clear);
        edtClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtName.setText("");
            }
        });

        //copy_paste
        txtNames.setMovementMethod(new ScrollingMovementMethod());
        clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        registerForContextMenu(txtNames);

    }

    //copy
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.copy_paste, menu);
        //selected color
        txtNames.setBackgroundColor(getResources().getColor(R.color.status_color));
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemcopy:
                copyText();
                return true;
            //case R.id.itempaste:
                //pasteText();
                //return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
    public void copyText(){
        ClipData clipData = ClipData.newPlainText(txtNames.toString(), txtNames.getText().toString());
        clipboardManager.setPrimaryClip(clipData);
        //unselected color
        txtNames.setBackgroundColor(getResources().getColor(R.color.transparent_color));
    }

    /*
    public void pasteText(){
        //read data from clipboard and insert it into the EditText at the cursor position
        ClipData clipData=clipboardManager.getPrimaryClip();
        try{
            int curPos=edtName.getSelectionStart();
            String textToPaste=clipData.getItemAt(0).getText().toString();
            String oldText=edtName.getText().toString();
            String textBeforeCursor=oldText.substring(0,curPos);
            String textAfterCursor=oldText.substring(curPos);
            String newText=textBeforeCursor+textToPaste+textAfterCursor;
            edtName.setText(newText);
        }catch(NullPointerException e){}

    }
    */


    public void chinese(View view) throws IOException {
        // hidden keybord when button is clicked
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edtName.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);

        cnName = Typeface.createFromAsset(getAssets(), "fonts/simkai.ttf");
        cnPinYin = Typeface.createFromAsset(getAssets(), "fonts/pinyin.TTF");
        txtNames.setTypeface(cnName);
        txtPinYin.setTypeface(cnPinYin);

        name = "";

        if(edtName.getText().toString().equals("")){
            Toast.makeText(this, "Please, enter with your first name.", Toast.LENGTH_LONG).show();
        }
        else {
            name = edtName.getText().toString().toLowerCase();
            InputStream in = this.getAssets().open("nomeschineses.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while((line = reader.readLine())!=null){
                int indicePrimeiroEspaco = line.indexOf(" ");
                String p1 = line.substring(0, indicePrimeiroEspaco);

                int indiceSegundoEspaco = line.indexOf(" ",indicePrimeiroEspaco+1);
                String p2 = line.substring(indicePrimeiroEspaco+1, indiceSegundoEspaco);

                int indiceParenteses = line.indexOf("(");
                String p3 = line.substring(indiceParenteses+1, line.length()-1);

                p1 = p1.toLowerCase();

                if(name.equals(p1)){
                    txtNames.setText(p2);
                    txtPinYin.setText(p3);
                    txtLanguage.setText("cn");

                    txtNames.setTextSize(70);
                    txtNames.setTextColor(Color.YELLOW);
                    txtPinYin.setTextSize(30);

                    break;

                } else {
                    txtNames.setText("Nome não cadastrado");
                    txtNames.setTextSize(20);
                    txtPinYin.setText("");
                }
            }

        }

    }

    public void japanese(View view) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edtName.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);

        txtNames.setTypeface(normal);
        txtPinYin.setTypeface(normal);

        if(edtName.getText().toString().equals("")){
            Toast.makeText(this, "Please, enter with your first name.", Toast.LENGTH_LONG).show();
        }
        else {
            name = edtName.getText().toString().toLowerCase();

            if (name.equals("Robson") || name.equals("robson")){
                txtNames.setText("ロブソン");
                txtLanguage.setText("jp");
                txtNames.setTextSize(55);
                txtPinYin.setTextSize(25);
                txtPinYin.setText("Robuson");
                txtLanguage.setText("jp");
                txtNames.setTextSize(55);
                txtPinYin.setTextSize(25);
            }else if (name.equals("Caroline") || name.equals("caroline")){
                txtNames.setText("カロリネ ");
                txtLanguage.setText("jp");
                txtNames.setTextSize(55);
                txtPinYin.setTextSize(25);
                txtPinYin.setText("Karorine");
                txtLanguage.setText("jp");
                txtNames.setTextSize(55);
                txtPinYin.setTextSize(25);
            } else if (name.equals("Renata") || name.equals("renata")) {
                txtNames.setText("レナタ");
                txtLanguage.setText("jp");
                txtNames.setTextSize(55);
                txtPinYin.setTextSize(25);
                txtPinYin.setText("Renata");
                txtLanguage.setText("jp");
                txtNames.setTextSize(55);
                txtPinYin.setTextSize(25);
            } else if (name.equals("Guilherme") || name.equals("guilherme")) {
                txtNames.setText("ギリェルメ");
                txtLanguage.setText("jp");
                txtNames.setTextSize(55);
                txtPinYin.setTextSize(25);
                txtPinYin.setText("Giryerume");
                txtLanguage.setText("jp");
                txtNames.setTextSize(55);
                txtPinYin.setTextSize(25);
            } else if (name.equals("André") || name.equals("andré") || name.equals("andre") || name.equals("Andre")) {
                txtNames.setText("アンドレ");
                txtLanguage.setText("jp");
                txtNames.setTextSize(55);
                txtPinYin.setTextSize(25);
                txtPinYin.setText("Andore");
                txtLanguage.setText("jp");
                txtNames.setTextSize(55);
                txtPinYin.setTextSize(25);
            } else if (name.equals("Pablo") || name.equals("pablo")) {
                txtNames.setText("パブロ ");
                txtLanguage.setText("jp");
                txtNames.setTextSize(55);
                txtPinYin.setTextSize(25);
                txtPinYin.setText("Pa buro");
                txtLanguage.setText("jp");
                txtNames.setTextSize(55);
                txtPinYin.setTextSize(25);
            } else if (name.equals("Alessandro") || name.equals("alessandro")) {
                txtNames.setText("アレサンドロ");
                txtLanguage.setText("jp");
                txtNames.setTextSize(55);
                txtPinYin.setTextSize(25);
                txtPinYin.setText("Aresandoro");
                txtLanguage.setText("jp");
                txtNames.setTextSize(55);
                txtPinYin.setTextSize(25);
            } else if (name.equals("Alexandre") || name.equals("alexandre")) {
                txtNames.setText("アレシャンドレ");
                txtLanguage.setText("jp");
                txtNames.setTextSize(55);
                txtPinYin.setTextSize(25);
                txtPinYin.setText("Areshandore");
                txtLanguage.setText("jp");
                txtNames.setTextSize(55);
                txtPinYin.setTextSize(25);
            } else if (name.equals("Silvio") || name.equals("silvio")) {
                txtNames.setText("シルビオ");
                txtLanguage.setText("jp");
                txtNames.setTextSize(55);
                txtPinYin.setTextSize(25);
                txtPinYin.setText("Shirubio");
                txtLanguage.setText("jp");
                txtNames.setTextSize(55);
                txtPinYin.setTextSize(25);
            } else if (name.equals("Gustavo") || name.equals("gustavo")) {
                txtNames.setText("グスタボ");
                txtLanguage.setText("jp");
                txtNames.setTextSize(55);
                txtPinYin.setTextSize(25);
                txtPinYin.setText("Gusutabo");
                txtLanguage.setText("jp");
                txtNames.setTextSize(55);
                txtPinYin.setTextSize(25);
            } else if (name.equals("Reiner") || name.equals("reiner")) {
                txtNames.setText("レイネル");
                txtLanguage.setText("jp");
                txtNames.setTextSize(55);
                txtPinYin.setTextSize(25);
                txtPinYin.setText("Reineru");
                txtLanguage.setText("jp");
                txtNames.setTextSize(55);
                txtPinYin.setTextSize(25);
            } else if (name.equals("Sergio") || name.equals("sergio")) {
                txtNames.setText("セルジオ");
                txtLanguage.setText("jp");
                txtNames.setTextSize(55);
                txtPinYin.setTextSize(25);
                txtPinYin.setText("Serujio");
                txtLanguage.setText("jp");
                txtNames.setTextSize(55);
                txtPinYin.setTextSize(25);
            } else if (name.equals("Beatriz") || name.equals("beatriz")) {
                txtNames.setText("ベアトリズ ");
                txtLanguage.setText("jp");
                txtNames.setTextSize(55);
                txtPinYin.setTextSize(25);
                txtPinYin.setText("Beatorizu");
                txtLanguage.setText("jp");
                txtNames.setTextSize(55);
                txtPinYin.setTextSize(25);
            } else if (name.equals("Luna") || name.equals("luna")) {
                txtNames.setText("ルナ");
                txtLanguage.setText("jp");
                txtNames.setTextSize(55);
                txtPinYin.setTextSize(25);
                txtPinYin.setText("Runa");
                txtLanguage.setText("jp");
                txtNames.setTextSize(55);
                txtPinYin.setTextSize(25);
            } else if (name.equals("Sulema") || name.equals("sulema")) {
                txtNames.setText("スレマ");
                txtLanguage.setText("jp");
                txtNames.setTextSize(55);
                txtPinYin.setTextSize(25);
                txtPinYin.setText("Surema");
                txtLanguage.setText("jp");
                txtNames.setTextSize(55);
                txtPinYin.setTextSize(25);
            } else if (name.equals("Maria Luiza") || name.equals("maria luiza")) {
                txtNames.setText("マリア ルイザ");
                txtLanguage.setText("jp");
                txtNames.setTextSize(55);
                txtPinYin.setTextSize(25);
                txtPinYin.setText("Mariaruiza");
                txtLanguage.setText("jp");
                txtNames.setTextSize(55);
                txtPinYin.setTextSize(25);
            } else if (name.equals("David") || name.equals("david")) {
                txtNames.setText("ダビド");
                txtLanguage.setText("jp");
                txtNames.setTextSize(55);
                txtPinYin.setTextSize(25);
                txtPinYin.setText("Dabido");
                txtLanguage.setText("jp");
                txtNames.setTextSize(55);
                txtPinYin.setTextSize(25);
            } else if (name.equals("Manuela") || name.equals("manuela")) {
                txtNames.setText("マヌエラ");
                txtLanguage.setText("jp");
                txtNames.setTextSize(55);
                txtPinYin.setTextSize(25);
                txtPinYin.setText("Manuera");
                txtLanguage.setText("jp");
                txtNames.setTextSize(55);
                txtPinYin.setTextSize(25);
            } else if (name.equals("Eduarda") || name.equals("eduarda")) {
                txtNames.setText("エヅアルダ");
                txtLanguage.setText("jp");
                txtNames.setTextSize(55);
                txtPinYin.setTextSize(25);
                txtPinYin.setText("Edzuaruda");
                txtLanguage.setText("jp");
                txtNames.setTextSize(55);
                txtPinYin.setTextSize(25);
            } else if (name.equals("Maria") || name.equals("maria")) {
                txtNames.setText("マリア");
                txtLanguage.setText("jp");
                txtNames.setTextSize(55);
                txtPinYin.setTextSize(25);
                txtPinYin.setText("Maria");
                txtLanguage.setText("jp");
                txtNames.setTextSize(55);
                txtPinYin.setTextSize(25);
            } else if (name.equals("Fernanda") || name.equals("fernanda")) {
                txtNames.setText("フェルナンダ");
                txtLanguage.setText("jp");
                txtNames.setTextSize(55);
                txtPinYin.setTextSize(25);
                txtPinYin.setText("Ferunanda");
                txtLanguage.setText("jp");
                txtNames.setTextSize(55);
                txtPinYin.setTextSize(25);
            } else if (name.equals("Tiago") || name.equals("tiago")) {
                txtNames.setText("チアゴ");
                txtLanguage.setText("jp");
                txtNames.setTextSize(55);
                txtPinYin.setTextSize(25);
                txtPinYin.setText("Achigo");
                txtLanguage.setText("jp");
                txtNames.setTextSize(55);
                txtPinYin.setTextSize(25);
            } else if (name.equals("Bernardo") || name.equals("bernardo")) {
                txtNames.setText("ベルナルド");
                txtLanguage.setText("jp");
                txtNames.setTextSize(55);
                txtPinYin.setTextSize(25);
                txtPinYin.setText("Berunarudo");
                txtLanguage.setText("jp");
                txtNames.setTextSize(55);
                txtPinYin.setTextSize(25);
            } else if (name.equals("Fabricio") || name.equals("fabricio")) {
                txtNames.setText("ファブリシオ");
                txtLanguage.setText("jp");
                txtNames.setTextSize(55);
                txtPinYin.setTextSize(25);
                txtPinYin.setText("Faburishio");
                txtLanguage.setText("jp");
                txtNames.setTextSize(55);
                txtPinYin.setTextSize(25);
            } else if (name.equals("Aldio") || name.equals("aldio")) {
                txtNames.setText("ハロー");
                txtLanguage.setText("jp");
                txtNames.setTextSize(55);
                txtPinYin.setTextSize(25);
                txtPinYin.setText("Harō");
                txtLanguage.setText("jp");
                txtNames.setTextSize(55);
                txtPinYin.setTextSize(25);
            } else {
                txtNames.setText("Nome não cadastrado");
                txtNames.setTextSize(20);
                txtPinYin.setText(" ");
            }

        }

    }

    public void korean(View view) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edtName.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);

        txtNames.setTypeface(normal);
        txtPinYin.setTypeface(normal);

        if(edtName.getText().toString().equals("")){
            Toast.makeText(this, "Please, enter with your first name.", Toast.LENGTH_LONG).show();
        }
        else {
            name = edtName.getText().toString().toLowerCase();
            if (name.equals("robson")){
                txtNames.setText("로브손");
                txtPinYin.setText("Lo Beu Son");
                txtLanguage.setText("ko");
                txtNames.setTextSize(55);
                txtPinYin.setTextSize(25);
            } else if (name.equals("caroline")){
                txtNames.setText("오아롤리네 ");
                txtPinYin.setText("o a Lol Li Ne");
                txtLanguage.setText("ko");
                txtNames.setTextSize(55);
                txtPinYin.setTextSize(25);
            } else if (name.equals("renata")) {
                txtNames.setText("레나타");
                txtPinYin.setText("Le Na Ta");
                txtLanguage.setText("ko");
                txtNames.setTextSize(55);
                txtPinYin.setTextSize(25);
            } else if (name.equals("luna")) {
                txtNames.setText("루나");
                txtPinYin.setText("Lu Na");
                txtNames.setTextSize(55);
                txtPinYin.setTextSize(25);
            } else if (name.equals("sulema")) {
                txtNames.setText("술레마");
                txtPinYin.setText("Sul Le Ma");
                txtNames.setTextSize(55);
                txtPinYin.setTextSize(25);
            } else if (name.equals("aldio")) {
                txtNames.setText("안녕");
                txtPinYin.setText("Anmyeong");
                txtNames.setTextSize(55);
                txtPinYin.setTextSize(25);
            }else {
                txtNames.setText("Nome não cadastrado");
                txtNames.setTextSize(20);
                txtPinYin.setText(" ");
            }

        }

    }

    public void oldChinese(View view) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edtName.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);

        cnName = Typeface.createFromAsset(getAssets(), "fonts/hanyu.TTF");
        cnPinYin = Typeface.createFromAsset(getAssets(), "fonts/pinyin.TTF");
        txtNames.setTypeface(cnName);
        txtPinYin.setTypeface(cnPinYin);

        if(edtName.getText().toString().equals("")){
            Toast.makeText(this, "Please, enter with your first name.", Toast.LENGTH_LONG).show();
        }
        else {
            name = edtName.getText().toString().toLowerCase();
            try {
                InputStream in = this.getAssets().open("nomeschineses.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line = "";
                while((line = reader.readLine())!=null){
                    int indicePrimeiroEspaco = line.indexOf(" ");
                    String p1 = line.substring(0, indicePrimeiroEspaco);

                    int indiceSegundoEspaco = line.indexOf(" ",indicePrimeiroEspaco+1);
                    String p2 = line.substring(indicePrimeiroEspaco+1, indiceSegundoEspaco);

                    int indiceParenteses = line.indexOf("(");
                    String p3 = line.substring(indiceParenteses+1, line.length()-1);

                    p1 = p1.toLowerCase();

                    if(name.equals(p1)){
                        txtNames.setText(p2);
                        txtPinYin.setText(p3);
                        txtLanguage.setText("zh");

                        txtNames.setTextSize(70);
                        txtNames.setTextColor(Color.YELLOW);
                        txtPinYin.setTextSize(30);

                        break;
                    } else {
                        txtNames.setText("Nome não cadastrado");
                        txtNames.setTextSize(20);
                        txtPinYin.setText(" ");
                    }
                }
            } catch (IOException e) { }

        }

    }

    public void mongolia(View view) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edtName.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);

        txtNames.setTypeface(normal);
        txtPinYin.setTypeface(normal);

        if(edtName.getText().toString().equals("")){
            Toast.makeText(this, "Please, enter with your first name.", Toast.LENGTH_LONG).show();
        }
        else {
            name = edtName.getText().toString().toLowerCase();
            if (name.equals("robson")){
                txtNames.setText("Робсон");
                txtPinYin.setText("");
                txtLanguage.setText("mon");
                txtNames.setTextSize(55);
                txtPinYin.setTextSize(25);
            }else {
                txtNames.setText("Nome não cadastrado");
                txtNames.setTextSize(20);
                txtPinYin.setText(" ");
            }

        }

    }

    public void tailand(View view) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edtName.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);

        txtNames.setTypeface(normal);
        txtPinYin.setTypeface(normal);

        if(edtName.getText().toString().equals("")){
            Toast.makeText(this, "Please, enter with your first name.", Toast.LENGTH_LONG).show();
        }
        else {
            name = edtName.getText().toString().toLowerCase();
            if (name.equals("robson")){
                txtNames.setText("ร็อบสัน");
                txtPinYin.setText("R̆ xb s̄ạn");
                txtLanguage.setText("tai");
                txtNames.setTextSize(55);
                txtPinYin.setTextSize(25);
            }else {
                txtNames.setText("Nome não cadastrado");
                txtNames.setTextSize(20);
                txtPinYin.setText(" ");
            }

        }

    }

    public void vietam(View view) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edtName.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);

        txtNames.setTypeface(normal);
        txtPinYin.setTypeface(normal);

        if(edtName.getText().toString().equals("")){
            Toast.makeText(this, "Please, enter with your first name.", Toast.LENGTH_LONG).show();
        }
        else {
            name = edtName.getText().toString().toLowerCase();
            if (name.equals("robson")){
                txtNames.setText("Robson");
                txtPinYin.setText("");
                txtLanguage.setText("viet");
                txtNames.setTextSize(55);
                txtPinYin.setTextSize(25);
            }else {
                txtNames.setText("Nome não cadastrado");
                txtNames.setTextSize(20);
                txtPinYin.setText(" ");
            }

        }

    }

    public void tibet(View view) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edtName.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);

        txtNames.setTypeface(normal);
        txtPinYin.setTypeface(normal);

        if(edtName.getText().toString().equals("")){
            Toast.makeText(this, "Please, enter with your first name.", Toast.LENGTH_LONG).show();
        }
        else {
            name = edtName.getText().toString().toLowerCase();
            if (name.equals("robson")){
                txtNames.setText("రాబ్సన్");
                txtPinYin.setText("Rābsan");
                txtLanguage.setText("tib");
                txtNames.setTextSize(55);
                txtPinYin.setTextSize(25);
            }else {
                txtNames.setText("Nome não cadastrado");
                txtNames.setTextSize(20);
                txtPinYin.setText(" ");
            }

        }

    }

    public void voiceButton(View v) {
        final String language = txtLanguage.getText().toString();
        voice = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    if(language.equals("cn") || language.equals("zh")){
                        int result = voice.setLanguage(Locale.SIMPLIFIED_CHINESE);
                        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                            Log.e("Error", "This Language is not supported");
                        } else {
                            String text = txtNames.getText().toString();
                            voice.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                        }
                    }
                    else if(language.equals("jp")){
                        int result = voice.setLanguage(Locale.JAPANESE);
                        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                            Log.e("Error", "This Language is not supported");
                        } else {
                            String text = txtNames.getText().toString();
                            voice.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                        }
                    }
                    else if(language.equals("ko")){
                        int result = voice.setLanguage(Locale.KOREAN);
                        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                            Log.e("Error", "This Language is not supported");
                        } else {
                            String text = txtNames.getText().toString();
                            voice.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                        }
                    } else {
                        Toast.makeText(NamesActivity.this, "This Language is not supported", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("error", "Initilization Failed!");
                }
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
