package android.example.Planner;

import android.content.Intent;
import android.example.Planner.Database.AppDatabase;
import android.example.Planner.Database.Node;
import android.os.Bundle;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import java.util.List;


public class DayView extends GameTemplate {
    private NodeAdapter mAdapter;
    private static final String name = "Day View";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setParentIdName(-1, name);
        super.setToolbar(R.menu.day_view);
        setContentView(R.layout.activity_day_view);

        AppDatabase.buildAppDatabase(getApplicationContext());

        List<Node> init = AppDatabase.getAppDatabase(getApplicationContext()).nodeDAO().getAllNodes();
        for(Node elem:init) {
            AppDatabase.Delete(elem);
            elem.setExpanded(false);
            AppDatabase.Insert(elem);
        }

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new NodeAdapter(this, AppDatabase.getAppDatabase(getApplicationContext()).nodeDAO().getOrdered(), false, true);
        super.setmAdapter(mAdapter);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
}

