package android.example.Planner;

import android.example.Planner.Database.AppDatabase;
import android.example.Planner.Database.Node;
import android.os.Bundle;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import java.util.List;


public class MainActivity extends GameTemplate {
    private NodeAdapter mAdapter;
    private static final String name = "Zen";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setParentName(name);
        setContentView(R.layout.activity_main);

        AppDatabase.buildAppDatabase(getApplicationContext());

        for(Node elem : AppDatabase.getAppDatabase(getApplicationContext()).nodeDAO().getAllNodes()) {
            AppDatabase.getAppDatabase(getApplicationContext()).nodeDAO().deletefrom(elem);
            elem.setExpanded(false);
            AppDatabase.getAppDatabase(getApplicationContext()).nodeDAO().insertinto(elem);
        }

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new NodeAdapter(this, AppDatabase.getAppDatabase(getApplicationContext()).nodeDAO().findByParent(name), true);
        super.setmAdapter(mAdapter);
        recyclerView.setAdapter(mAdapter);
    }
}

