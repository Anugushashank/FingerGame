package com.example.shashank.fingergame;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import java.util.Random;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.ActionMode;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Shashank on 14-04-2016.
 */

public class Fragment3 extends Fragment {
    private GridView list;
    ArrayList<String> data = new ArrayList<>();
    View v;
    GridView gridView;
    GridViewCustomAdapter adapter;
    Handler handler = new Handler();
    Button button;
    Random rand=new Random();

    int i = 0,n,g=0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /** Inflating the layout for this fragment **/

        v = inflater.inflate(R.layout.game, null);
        gridView = (GridView) v.findViewById(R.id.grid_view);
        String strtext = getArguments().getString("message");
        n = Integer.parseInt(strtext);
        Global.count=1;
        if(g==0) {

            Global.random = rand.nextInt((n * n - 1 - 0) + 1) + 0;
            Global.fun();
            g++;
        }
        Global.ar[Global.random] = 1;
        DisplayMetrics display = this.getResources().getDisplayMetrics();
        int width = display.widthPixels;
        int height = display.heightPixels;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                data.add(i + "-" + j);
        }
        adapter = new GridViewCustomAdapter(getActivity(), data, width, height, n);
        gridView.setNumColumns(n);
        gridView.setAdapter(adapter);
        timer.start();
        return v;
    }
        Thread timer = new Thread() {
            @Override
            public void run() {
                if(Global.stop==true) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            View view = gridView.getChildAt(Global.random);
                            button = (Button) view;
                            if (i == 0) {
                                button.setBackgroundColor(Color.YELLOW);
                                i++;
                            } else {
                                button.setBackgroundColor(Color.RED);
                                i--;
                            }
                        }

                    });
                    handler.postDelayed(timer, 200);
                }
            }
        };
    public class GridViewCustomAdapter extends BaseAdapter {
        int f=1;
        ArrayList<String> items;
        Activity mActivity;
        int width,height,n;

        private  LayoutInflater inflater = null;

        public GridViewCustomAdapter(Activity activity, ArrayList<String> tempTitle,int Width,int Height,int N) {
            mActivity = activity;
            items = tempTitle;
            width=Width;
            height=Height;
            n=N;

            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public final int getCount() {

            return items.size();

        }

        @Override
        public final Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public final long getItemId(int position) {

            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View v =null;
            v = inflater.inflate(R.layout.item,null );
            v.setMinimumWidth(width);
            double h=(height)/n;
            h=h - (n-1)*1.5;
            v.setMinimumHeight((int) h);
            Random rnd = new Random();
            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            v.setBackgroundColor(color);
            v.setId(position);
            v.setTag(position);
                v.setOnTouchListener(new View.OnTouchListener() {
                    boolean pressed = false;

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (Global.main == 0) {
                            switch (event.getAction()) {
                                case MotionEvent.ACTION_DOWN:
                                    pressed = true;
                                    if (position == Global.random && Global.count < n * n) {
                                        Global.stop = false;
                                        if (Global.count % 2 == 0) {
                                            Global.per[position] = 2;
                                        } else {
                                            Global.per[position] = 1;
                                        }
                                        v.setBackgroundColor(Color.BLACK);
                                        Global.random = rand.nextInt((n * n - 1 - 0) + 1) + 0;
                                        while (Global.ar[Global.random] != 100) {
                                            Global.random = rand.nextInt((n * n - 1 - 0) + 1) + 0;
                                        }
                                        Global.ar[Global.random] = 1;
                                        Global.count++;
                                        Global.stop = true;
                                    } else if (Global.count == n * n) {
                                        Global.stop = false;
                                        v.setBackgroundColor(Color.BLACK);
                                    }
                                    break;
                                case MotionEvent.ACTION_POINTER_DOWN:
                                    pressed = true;
                                    if (position == Global.random && Global.count < n * n) {
                                        Global.stop = false;
                                        if (Global.count % 2 == 0) {
                                            Global.per[position] = 2;
                                        } else {
                                            Global.per[position] = 1;
                                        }
                                        v.setBackgroundColor(Color.BLACK);
                                        Global.random = rand.nextInt((n * n - 1 - 0) + 1) + 0;
                                        while (Global.ar[Global.random] != 100) {
                                            Global.random = rand.nextInt((n * n - 1 - 0) + 1) + 0;
                                        }
                                        Global.ar[Global.random] = 1;
                                        Global.count++;
                                        Global.stop = true;
                                    } else if (position == Global.random && Global.count == n * n) {
                                        Global.stop = false;
                                        v.setBackgroundColor(Color.BLACK);
                                        Global.main++;
                                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                        // Set the Alert Dialog Message
                                        builder.setMessage("Match has been a draw")
                                                .setCancelable(false)
                                                .setPositiveButton("Play again",
                                                        new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int id) {
                                                                // Restart the Activity
                                                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                                                startActivity(intent);
                                                            }
                                                        });
                                        AlertDialog alert = builder.create();
                                        alert.show();
                                    } else if (position != Global.random) {
                                        Global.stop = false;
                                        Global.main++;
                                        if (Global.per[position] == 2) {
                                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                            // Set the Alert Dialog Message
                                            builder.setMessage("Player-1 has won")
                                                    .setCancelable(false)
                                                    .setPositiveButton("Play again",
                                                            new DialogInterface.OnClickListener() {
                                                                public void onClick(DialogInterface dialog, int id) {
                                                                    // Restart the Activity
                                                                    Intent intent = new Intent(getActivity(), MainActivity.class);
                                                                    startActivity(intent);
                                                                }
                                                            });
                                            AlertDialog alert = builder.create();
                                            alert.show();
                                            break;
                                        } else {
                                            Global.stop = false;
                                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                            // Set the Alert Dialog Message
                                            builder.setMessage("Player-2 has won")
                                                    .setCancelable(false)
                                                    .setPositiveButton("Play again",
                                                            new DialogInterface.OnClickListener() {
                                                                public void onClick(DialogInterface dialog, int id) {
                                                                    // Restart the Activity
                                                                    Intent intent = new Intent(getActivity(), MainActivity.class);
                                                                    startActivity(intent);
                                                                }
                                                            });
                                            AlertDialog alert = builder.create();
                                            alert.show();
                                            break;
                                        }
                                    }
                                    break;
                                case MotionEvent.ACTION_MOVE:

                                    break;

                                case MotionEvent.ACTION_UP:
                                    Global.stop = false;
                                    pressed = false;
                                    Global.main++;
                                    if (Global.per[position] == 1) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                        // Set the Alert Dialog Message
                                        builder.setMessage("Player 1 has moved.Player-2 has won the game")
                                                .setCancelable(false)
                                                .setPositiveButton("Play Again",
                                                        new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int id) {
                                                                // Restart the Activity
                                                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                                                startActivity(intent);
                                                            }
                                                        });
                                        AlertDialog alert = builder.create();
                                        alert.show();
                                        break;
                                    } else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                        // Set the Alert Dialog Message
                                        builder.setMessage("Player 2 has moved.Player-1 has won the game")
                                                .setCancelable(false)
                                                .setPositiveButton("Play Again",
                                                        new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int id) {
                                                                // Restart the Activity
                                                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                                                startActivity(intent);
                                                            }
                                                        });
                                        AlertDialog alert = builder.create();
                                        alert.show();
                                        break;
                                    }


                            }

                        }
                        return pressed;
                    }

                });
            f++;
            return v;
        }

    }



}
