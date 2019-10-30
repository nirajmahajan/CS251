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

        List<Node> init = AppDatabase.getAppDatabase(getApplicationContext()).nodeDAO().getAllNodes();
        for(Node elem:init) {
            AppDatabase.getAppDatabase(getApplicationContext()).nodeDAO().Delete(elem);
            elem.setExpanded(false);
            AppDatabase.getAppDatabase(getApplicationContext()).nodeDAO().Insert(elem);
        }

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new NodeAdapter(this, AppDatabase.getAppDatabase(getApplicationContext()).nodeDAO().findByParent(name), true);
        super.setmAdapter(mAdapter);
        recyclerView.setAdapter(mAdapter);
    }
}

