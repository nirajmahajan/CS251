package android.example.Planner;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.example.Planner.Database.AppDatabase;
import android.example.Planner.Database.Node;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.Calendar;
import java.util.List;

public class GameTemplate extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    DrawerLayout fullView;
    DrawerLayout drawer;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    AppCompatActivity activity = this;
    private NodeAdapter mAdapter;
    private String parent;
    private int toolbar_id;
    TextView descrip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID)
    {
        fullView = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_game_template, null);
        FrameLayout activityContainer = (FrameLayout) fullView.findViewById(R.id.game_template_activity_content);
        getLayoutInflater().inflate(layoutResID, activityContainer, true);
        super.setContentView(fullView);

        toolbar = findViewById(R.id.game_toolbar);
        setSupportActionBar(toolbar);
        setTitle(parent);
        toolbar.setTitleTextColor(Color.WHITE);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                hideKeyboard(activity);
                super.onDrawerOpened(drawerView);
            }
        };
        if (useDrawer())
        {
            drawer.addDrawerListener(toggle);
            toggle.syncState();
            navigationView.setNavigationItemSelectedListener(this);
        }
        else
        {
            drawer.setVisibility(View.GONE);
        }
    }
    protected boolean useDrawer() {
        return true;
    }

    protected void setParentName(String name) {
        this.parent = name;
        toolbar_id = R.menu.main_menu;
    }

    protected void setmAdapter(NodeAdapter mAdapter) {
        this.mAdapter = mAdapter;
        descrip = (TextView) findViewById(R.id.description);
        if(parent.equals("Day View")) {
            descrip.setText("Day View for all entries");
        } else {
            List<Node> temp = AppDatabase.getAppDatabase(getApplicationContext()).nodeDAO().findByName(parent);
            if (temp.size() <= 0) {
                descrip.setText("No description Found");
                return;
            }
            descrip.setText(AppDatabase.getAppDatabase(getApplicationContext()).nodeDAO().findByName(parent).get(0).getDescription());
        }
    }

    protected void setToolbar(int toolbar_id){
        this.toolbar_id = toolbar_id;
    }

    public void chooseDateAndChange(){
        // Get Current Date
        int mYear, mMonth, mDay, mHour, mMinute;
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        mAdapter.nodes = AppDatabase.getAppDatabase(getApplicationContext()).nodeDAO().findByDate(date);
                        mAdapter.notifyDataSetChanged();
                        descrip.setText("Entries for the date : " + date);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
        datePickerDialog.setCancelable(false);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(toolbar_id, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_task:
                popup().show();
                return true;
            case R.id.action_date_selector:
                chooseDateAndChange();
                return true;
            case R.id.action_all_again:
                mAdapter.nodes = AppDatabase.getAppDatabase(getApplicationContext()).nodeDAO().getOrdered();
                mAdapter.notifyDataSetChanged();
                descrip.setText("Entries for all dates");
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addItem(String title, String desc, String date) {
        if(date.equals("Click here to Select Date")){date = "";}
        Node temp = new Node();
        temp.setName(title);
        temp.setDescription(desc);
        temp.setDate(date);
        temp.setExpanded(false);
        temp.setParent(parent);

        AppDatabase.Insert(temp);
        mAdapter.nodes = AppDatabase.getAppDatabase(getApplicationContext()).nodeDAO().findByParent(parent);
        mAdapter.notifyDataSetChanged();
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

        final Button dateBox = new Button(context);
        dateBox.setText("Click here to Select Date");
        dateBox.setId(12345);
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
            }
        });
        db.setNegativeButton("Cancel", null);
        db.setView(layout);
        Button dater = (Button)dateBox.findViewById(12345);
        dater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mYear, mMonth, mDay, mHour, mMinute;
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(GameTemplate.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                dateBox.setText(date);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.setCancelable(false);
            }
        });
        db.setCancelable(false);
        return db.create();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_dayview) {
            Intent intent = new Intent(getApplicationContext(), DayView.class);
            startActivity(intent);
        } else if (id == R.id.nav_about) {
            Intent intent = new Intent(getApplicationContext(), About.class);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public static void hideKeyboard(AppCompatActivity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
