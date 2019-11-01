package com.CtrlAltDefeat.Planner;

import com.CtrlAltDefeat.Planner.Database.AppDatabase;
import com.CtrlAltDefeat.Planner.Database.Node;

import com.CtrlAltDefeat.Planner.R;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;


public class MainActivity extends GameTemplate {
    private NodeAdapter mAdapter;
    private static String name;
    private int curr_par_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        name = "Zen";
        curr_par_id = AppDatabase.getAppDatabase(getApplicationContext()).nodeDAO().findByName("Zen").get(0).getId();

        super.onCreate(savedInstanceState);
        AppDatabase.buildAppDatabase(getApplicationContext());

        super.setParentIdName(curr_par_id, name);
        setContentView(R.layout.activity_main);

        for(Node elem : AppDatabase.getAppDatabase(getApplicationContext()).nodeDAO().getAllNodes()) {
            AppDatabase.getAppDatabase(getApplicationContext()).nodeDAO().deletefrom(elem);
            elem.setExpanded(false);
            AppDatabase.getAppDatabase(getApplicationContext()).nodeDAO().insertinto(elem);
        }

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new NodeAdapter(this, AppDatabase.getAppDatabase(getApplicationContext()).nodeDAO().findByParentId(curr_par_id), true, false);
        super.setmAdapter(mAdapter);
        recyclerView.setAdapter(mAdapter);
    }
}

