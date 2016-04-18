package com.example.shashank.fingergame;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import android.os.Handler;

public class MainActivity extends Activity {
    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment1 hello = new Fragment1();
        fragmentTransaction.add(R.id.frameLayout, hello, "frag1");
        fragmentTransaction.commit();
    }
    public void onClickBtn(View v) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment2 hello = new Fragment2();
        fragmentTransaction.replace(R.id.frameLayout, hello, "frag2");
        fragmentTransaction.commit();
    }
    public void onClickBtn1(View v){
        EditText editText=(EditText)findViewById(R.id.editText);
        String st=editText.getText().toString();
        if (st.matches("")) {
            Toast.makeText(this, "Enter a value.", Toast.LENGTH_SHORT).show();
        }
        else {
            Bundle bundle = new Bundle();
            bundle.putString("message", st);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            Fragment3 hello = new Fragment3();
            hello.setArguments(bundle);
            fragmentTransaction.replace(R.id.frameLayout, hello, "frag2");
            fragmentTransaction.commit();
        }

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

        return super.onOptionsItemSelected(item);
    }
}
