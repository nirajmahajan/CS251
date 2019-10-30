package android.example.Planner;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.example.Planner.Database.AppDatabase;
import android.example.Planner.Database.Node;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class NodeAdapter extends RecyclerView.Adapter<NodeAdapter.NodeViewHolder> {
    private Context mContext;
    public List<Node> nodes;
    public boolean canGoDeeper;
    public HashMap<Integer, Long> times;
    public NodeAdapter(Context context, List<Node> nodes, boolean canGoDeeper){
        mContext = context;
        this.canGoDeeper = canGoDeeper;
        this.nodes = nodes;
        this.times = new HashMap<>();
        for(int i = 0; i < nodes.size(); i++) {
            times.put(nodes.get(i).getId(), Long.valueOf("-1"));
        }
    }

    public class NodeViewHolder extends RecyclerView.ViewHolder{
        public TextView nameText;
        public TextView descText;
        public NodeViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.child_name);
            descText = itemView.findViewById(R.id.child_desc);
        }
    }

    @Override
    public NodeAdapter.NodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.child, parent, false);
        return new NodeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NodeViewHolder holder, final int position) {
        final Node curr = nodes.get(position);
        String name = curr.getName();
        String desc = curr.getDescription();;

        holder.nameText.setText(name);
        holder.descText.setText(desc);


        final boolean expanded = curr.isExpanded();
        TextView subItem = holder.itemView.findViewById(R.id.child_desc);
        subItem.setVisibility(expanded ? View.VISIBLE : View.GONE);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(canGoDeeper) {
                    Long prev_time = times.get(curr.getId());
                    if (prev_time != null && prev_time != -1) {
                        Long curr_time = System.currentTimeMillis();
                        times.put(curr.getId(), System.currentTimeMillis());
                        if (curr_time - prev_time < 350) {
                            Intent intent = new Intent(mContext, TaskActivity.class);
                            intent.putExtra("NAME", curr.getName());
                            mContext.startActivity(intent);
                            return;
                        }
                    } else {
                        times.put(curr.getId(), System.currentTimeMillis());
                    }
                }

                List<Node> alreadyExp = AppDatabase.getAppDatabase(mContext).nodeDAO().findExpanded(true);
                for (Node elem : alreadyExp){
                    AppDatabase.getAppDatabase(mContext).nodeDAO().Delete(elem);
                    elem.setExpanded(false);
                    AppDatabase.getAppDatabase(mContext).nodeDAO().Insert(elem);
                }
                notifyDataSetChanged();

                AppDatabase.getAppDatabase(mContext).nodeDAO().Delete(curr);
                curr.setExpanded(!curr.isExpanded());
                AppDatabase.getAppDatabase(mContext).nodeDAO().Insert(curr);



                NodeAdapter.this.notifyItemChanged(position);
            }

        });



    }

    @Override
    public int getItemCount() {
        return nodes.size();
    }
}
