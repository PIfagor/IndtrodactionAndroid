package com.troystudios.wackgame;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import area730.com.first.R;


public class RankingTable extends MusicActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking_table);

        rank = (ListView)findViewById(R.id.lvRanking);
       // String [] s = {"xyq","qyq","sdf","sdf","sdf","qyq","sdf","sdf","sdf","qyq","sdf","sdf","sdf","qyq","sdf","sdf","sdf","qyq","sdf","sdf","sdf","qyq","sdf","sdf","sdf","qyq","sdf","sdf","sdf","qyq","sdf","sdf","sdf","qyq","sdf","sdf","sdf","qyq","sdf","sdf","sdf","qyq","sdf","sdf","sdf","qyq","sdf","sdf","sdf","qyq","sdf","sdf","sdf","qyq","sdf","sdf","sdf","qyq","sdf","sdf","sdf"};
        //rank.setAdapter(new ArrayAdapter<String>(this, R.layout.simple_list_item_1, s));
        ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map = new HashMap<String, String>();


        users = new DBHelper(this);
        db = users.getWritableDatabase();
        cr = db.query(users.TABLE,null,null,null,null,null,users.C_RESULTS+" DESC");

        int i = 0;
        while (cr.moveToNext()){
            map.put("id", Integer.toString(++i));
            map.put("name", cr.getString(cr.getColumnIndex(users.C_NICKNAME)));
            map.put("res",  cr.getString(cr.getColumnIndex(users.C_RESULTS)));
            mylist.add(map);
            map = new HashMap<String, String>();

        }



//        mylist.add(map);
//        map = new HashMap<String, String>();
//        map.put("id", "103(x)");
//        map.put("name", "6:35 AM");
//        map.put("res", "7:45 AM");
//        mylist.add(map);
// ...
         mSchedule = new SimpleAdapter(this, mylist, R.layout.row,
                new String[] {"id", "name", "res"}, new int[] {R.id.NUMBER, R.id.LOGIN, R.id.RESULT});

        rank.setAdapter(mSchedule);

        int f = rank.getFirstVisiblePosition();
        int l = rank.getLastVisiblePosition();
       // TODO:rank.getChildAt(l-f).setBackgroundColor(Color.BLUE);
        //setActivityBackgroundColor(0x777777);
    }
//    public void setActivityBackgroundColor(int color) {
//        View view = this.getWindow().getDecorView();
//        view.setBackgroundColor(color);
//    }
//    private void initListView()
//    {
//        final String   AuthorName    = "Author: ";
//        final String   CopyrightName = "CopyRight: ";
//        final String   PriceName     = "Price: ";
//
//        final String[] matrix  = { "_id", "name", "value" };
//        final String[] columns = { "name", "value" };
//        final int[]    layouts = { android.R.id.text1, android.R.id.text2 };
//
//        MatrixCursor cursor = new MatrixCursor(matrix);
//
//        DecimalFormat formatter = new DecimalFormat("##,##0.00");
//        int key = 0;
//        cursor.addRow(new Object[] { key++, AuthorName });
//        cursor.addRow(new Object[] { key++, CopyrightName, });
//        cursor.addRow(new Object[] { key++, PriceName,
//                "$" + formatter.format(12) });
//
//        SimpleCursorAdapter data =
//                new SimpleCursorAdapter(this,
//                        R.layout.viewlist_two_items,
//                        cursor,
//                        columns,
//                        layouts);
//
//        rank.setAdapter( data );
//
//    }   // end of initListView()

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ranking_table, menu);
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
            Intent mainInten = new Intent(RankingTable.this,MainActivity.class);
            RankingTable.this.startActivity(mainInten);
            RankingTable.this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private ListView rank;
    private SimpleAdapter mSchedule;

    private DBHelper users;
    private SQLiteDatabase db;
    private Cursor cr;
}
