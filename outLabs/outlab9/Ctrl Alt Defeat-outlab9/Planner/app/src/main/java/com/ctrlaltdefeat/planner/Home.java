package com.ctrlaltdefeat.planner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class Home extends AppCompatActivity {
    private static final String TAG = "Home";
    private SQLiteDatabase mDatabase;
    private NodeAdapter mAdapter;
    private TextView mTextView;
    private static final String name = "Zen";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Zen");
        setContentView(R.layout.home);

        mTextView = findViewById(R.id.curr_desc);

        MyDBHelper dbHelper = new MyDBHelper(this);
        mDatabase = dbHelper.getWritableDatabase();

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new NodeAdapter(this, getAllItems());
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_task:
                popup().show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void addItem(String title, String desc, String date) {
        ContentValues cv = new ContentValues();
        cv.put(TaskClass.TaskEntry.COLUMN_NAME, title);
        cv.put(TaskClass.TaskEntry.COLUMN_DESCR, desc);
        cv.put(TaskClass.TaskEntry.COLUMN_SCHEDULED, date);
        cv.put(TaskClass.TaskEntry.COLUMN_PARENT, name);
        mDatabase.insert(TaskClass.TaskEntry.TABLE_NAME, null, cv);
        mAdapter.swapCursor(getAllItems());
    }
    private Cursor getAllItems() {
        return mDatabase.query(
                TaskClass.TaskEntry.TABLE_NAME,
                null,
                "parent = ?",
                new String[] {name},
                null,
                null,
                TaskClass.TaskEntry.COLUMN_NAME
        );
    }
    public Dialog popup(){
        Context context = this.getApplicationContext();
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText titleBox = new EditText(context);
        titleBox.setHint("Title");
        layout.addView(titleBox);

        final EditText descriptionBox = new EditText(context);
        descriptionBox.setHint("Description");
        layout.addView(descriptionBox);

        final EditText dateBox = new EditText(context);
        dateBox.setHint("Date");
        layout.addView(dateBox);

        AlertDialog.Builder db = new AlertDialog.Builder(this);
        db.setTitle("Add Node");
        db.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String title = String.valueOf(titleBox.getText());
                String desc = String.valueOf(descriptionBox.getText());
                String date = String.valueOf(dateBox.getText());
                if(title.trim().length() != 0 && desc.trim().length() != 0)
                    addItem(title, desc, date);
                Log.w(TAG, "Task to add: " + title + "  " + desc + "  " + date);
            }
        });
        db.setNegativeButton("Cancel", null);
        db.setView(layout);
        return db.create();
    }
}

