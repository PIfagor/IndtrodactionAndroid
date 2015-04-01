package com.troystudios.wackgame;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import area730.com.first.R;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = (Button)findViewById(R.id.bNext);
        b1.setOnClickListener(clickToX);

        b2 = (Button)findViewById(R.id.bPrew);
        b2.setOnClickListener(clickToY);

        confrim = (Button)findViewById(R.id.btnConfrim);
        confrim.setOnClickListener(clickToConfrim);

        myTextView = (TextView) findViewById(R.id.text);
        myTextView.setTextSize(30);
        name = (TextView) findViewById(R.id.twName);
        name.setTextSize(30);
        //radGrp = (RadioGroup) findViewById(R.id.rbGroup);
        Bundle extras = getIntent().getExtras();
       sName = extras.getString("EMAIL");
        name.setText(sName);

        result = (EditText) findViewById(R.id.ewRecord);

        result.setText(extras.getString("RESULT"));
//        postParameters = new ArrayList<NameValuePair>();
//        postParameters.add(new BasicNameValuePair("mode","getResult"));
//        postParameters.add(new BasicNameValuePair("email",sName));
//        // postParameters.add(new BasicNameValuePair("password", mPassword));
//        String response = null;
//        try {
//            response = CustomHttpClient.executeHttpPost("http://bastion.ga/check.php", postParameters);  //Enetr Your remote PHP,ASP, Servlet file link
//            String res=response.toString();
//            int i = res.indexOf('\n');
//            res = res.substring(0,i);
//            result.setText(res);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;
        }


        if (id == R.id.raiting) {
            Intent mainInten = new Intent(MainActivity.this,RankingTable.class);
            MainActivity.this.startActivity(mainInten);
            MainActivity.this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    View.OnClickListener clickToX;
    {
        clickToX = new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onClick(View v) {


               // Intent mainInten = new Intent(MainActivity.this,RankingTable.class);
              //  MainActivity.this.startActivity(mainInten);
               // MainActivity.this.finish();

                int deltaX = 5;
                myTextView.setLeft((int) (myTextView.getLeft() + deltaX));
                String st = String.valueOf(myTextView.getLeft());

                Bundle extras = getIntent().getExtras();
                if (extras != null) {
                    st = extras.getString("PASSWORD");
                }
                myTextView.setText(st);



            }
        };
    }

    View.OnClickListener clickToY;
    {
        clickToY = new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onClick(View v) {
               // TextView myTextView = (TextView) findViewById(R.id.text);
                int deltaY = 5;
                myTextView.setLeft((int) (myTextView.getLeft() - deltaY));
                String st = String.valueOf(myTextView.getLeft());

                Bundle extras = getIntent().getExtras();
                if (extras != null) {
                     st = extras.getString("EMAIL");
                }
                myTextView.setText(st);
            }
        };
    }

    View.OnClickListener clickToConfrim;
    {
        clickToConfrim = new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onClick(View v) {
                String resul = String.valueOf(result.getText());
//                postParameters.add(new BasicNameValuePair("mode","setResult"));
//                postParameters.add(new BasicNameValuePair("result",resul));
//                // postParameters.add(new BasicNameValuePair("password", mPassword));
//                String response = null;
//                try {
//                    response = CustomHttpClient.executeHttpPost("http://bastion.ga/check.php", postParameters);  //Enetr Your remote PHP,ASP, Servlet file link
//                    String res=response.toString();
//                    int i = res.indexOf('\n');
//                    res = res.substring(0,i);
//                    if(res.equals("1"))
//                        Log.d(TAG,"Score updated");
//                    else
//                        Log.d(TAG,"Error in UPDATING SCORE");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
               // ContentValues cv = new ContentValues();
               // cv.clear();

               // cv.put (users.C_RESULTS, res);

               // String sql = "UPDATE "+users.TABLE+" SET result = '"+ res+"' WHERE email = '"+sName+"'";
               // db.execSQL(sql);
               // DBHandler.GET.updateResults(sName,res);
               // String emaill = (String) cv.get(users.C_EMAIL);
               // db.update(users.TABLE,cv,"email=" +sName ,null);
                myTextView.setText("Result: "+resul+"saved in DB");
                Log.d(TAG,"Record added");

            }
        };
    }


//    private RadioGroup  radGrp = (RadioGroup) findViewById(R.id.rbGroup);
//    int checkedRadioButtonID = radGrp.getCheckedRadioButtonId();
//    radGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
//    { {
//        public void onCheckedChanged(RadioGroup arg0, int id) {
//            switch (id) {
//                case -1:
//                    Log.v(TAG, "Choices cleared!");
//                    break;
//                case R.id.chRBtn:
//                    Log.v(TAG, "Chose Chicken");
//                    break;
//                case R.id.fishRBtn:
//                    Log.v(TAG, "Chose Fish");
//                    break;
//                case R.id.stkRBtn:
//                    Log.v(TAG, "Chose Steak");
//                    break;
//                default:
//                    Log.v(TAG, "Huh?");
//                    break;
//            }
//        }
//    });

    private TextView myTextView;
    private TextView name;
    private EditText result;
    private Button b1;
    private  Button b2;
    private Button confrim;
    private RadioButton rb;

//    private DBHelper users;
//    private SQLiteDatabase db;
//    private Cursor cr;  private DBHelper users;
//    private SQLiteDatabase db;
//    private Cursor cr;

    private  String sName;
    private final String TAG = "MainGame";

  //  private  ArrayList<NameValuePair> postParameters ;
}
