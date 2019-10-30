package com.ctrlaltdefeat.planner;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NodeAdapter extends RecyclerView.Adapter<NodeAdapter.NodeViewHolder> {
    private Context mContext;
    private Cursor mCursor;
   public NodeAdapter(Context context, Cursor cursor){
       mContext = context;
       mCursor = cursor;
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
    public NodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.child, parent, false);
        return new NodeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NodeViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) {
            return;
        }

        String name = mCursor.getString(mCursor.getColumnIndex(TaskClass.TaskEntry.COLUMN_NAME));
        String desc = mCursor.getString(mCursor.getColumnIndex(TaskClass.TaskEntry.COLUMN_DESCR));

        holder.nameText.setText(name);
        holder.descText.setText(desc);
        boolean expanded = TaskClass.TaskEntry.expanded;
        TextView subItem = holder.itemView.findViewById(R.id.child_desc);
        subItem.setVisibility(expanded ? View.VISIBLE : View.GONE);

        holder.itemView.setOnClickListener(v -> {
            TaskClass.TaskEntry.setExpanded(!expanded);
            notifyItemChanged(position);
        });

    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        if (mCursor != null) {
            mCursor.close();
        }
        mCursor = newCursor;
        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }
}
