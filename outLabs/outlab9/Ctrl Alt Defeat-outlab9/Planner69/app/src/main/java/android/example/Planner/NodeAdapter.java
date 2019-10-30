package android.example.Planner;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.example.Planner.Database.AppDatabase;
import android.example.Planner.Database.Node;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


public class NodeAdapter extends RecyclerView.Adapter<NodeAdapter.NodeViewHolder> {
    private Context mContext;
    public List<Node> nodes;
    private Node currentexp = null;
    public boolean canGoDeeper;
    public boolean isDayView = false;
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
        public TextView dateText;
        public TextView child1;
        public TextView child2;
        public TextView child3;
        public LinearLayout ll_second;
        public Button edit;
        public NodeViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.child_name);
            descText = itemView.findViewById(R.id.child_desc);
            dateText = itemView.findViewById(R.id.child_date);
            ll_second = itemView.findViewById(R.id.ll_second);
            child1 = itemView.findViewById(R.id.child1);
            child2 = itemView.findViewById(R.id.child2);
            child3 = itemView.findViewById(R.id.child3);
            edit = itemView.findViewById(R.id.edit);
        }
    }

    @Override
    public NodeAdapter.NodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.child, parent, false);
        return new NodeViewHolder(view);
    }
    private void addItem(String title, String desc, String date, String parent) {
        List<Node> temp1 = AppDatabase.getAppDatabase(mContext).nodeDAO().findByName(title);
        if(temp1.size() != 0) {
            Toast.makeText(mContext, "Name already taken", Toast.LENGTH_LONG);
            return;
        }
        Node temp = new Node();
        temp.setName(title);
        temp.setDescription(desc);
        temp.setDate(date);
        temp.setExpanded(false);
        temp.setParent(parent);

        AppDatabase.getAppDatabase(mContext).nodeDAO().Insert(temp);
        nodes = AppDatabase.getAppDatabase(mContext).nodeDAO().findByParent(parent);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull NodeViewHolder holder, final int position) {
        final Node curr = nodes.get(position);
        String name = curr.getName();
        String desc = curr.getDescription();
        String date = curr.getDate();
        String c1, c2, c3;
        List<Node> allChildren = AppDatabase.getAppDatabase(mContext).nodeDAO().findByParent(curr.getName());
        if(allChildren.size() == 1)  {
            c1 = allChildren.get(0).getName();
            holder.child1.setText(c1);
        } else if(allChildren.size() == 0)  {
            holder.child1.setText("No Child Tasks");
        }
        else if (allChildren.size() == 2) {
            c1 = allChildren.get(0).getName();
            c2 = allChildren.get(1).getName();
            holder.child1.setText(c1);
            holder.child2.setText(c2);
        } else if (allChildren.size() == 3) {
            c1 = allChildren.get(0).getName();
            c2 = allChildren.get(1).getName();
            c3 = allChildren.get(2).getName();
            holder.child1.setText(c1);
            holder.child2.setText(c2);
            holder.child3.setText(c3);
        } else if (allChildren.size() > 3) {
            c1 = allChildren.get(0).getName();
            c2 = allChildren.get(1).getName();
            holder.child1.setText(c1);
            holder.child2.setText(c2);
            holder.child3.setText("...");
        }

        holder.nameText.setText(name);
        holder.descText.setText(desc);
        holder.dateText.setText(date);

        final boolean expanded = curr.isExpanded();
        TextView subItem = holder.itemView.findViewById(R.id.child_desc);
        subItem.setVisibility(expanded ? View.VISIBLE : View.GONE);

        Button edit_it = holder.itemView.findViewById(R.id.edit);
        edit_it.setVisibility((expanded && canGoDeeper && !isDayView) ? View.VISIBLE : View.GONE);

        if(isDayView){
            TextView subItem1 = holder.itemView.findViewById(R.id.child1);
            subItem1.setVisibility(expanded ? View.VISIBLE : View.GONE);
//            subItem1.setText("Heirarchy:" + AppDatabase.heirarchy(curr.getName()));
            TextView subItem2 = holder.itemView.findViewById(R.id.child2);
            subItem2.setVisibility(View.GONE);
            TextView subItem3 = holder.itemView.findViewById(R.id.child3);
            subItem3.setVisibility(View.GONE);
        } else {
            if (allChildren.size() == 0) {
                TextView subItem1 = holder.itemView.findViewById(R.id.child1);
                subItem1.setVisibility(expanded ? View.VISIBLE : View.GONE);
                TextView subItem2 = holder.itemView.findViewById(R.id.child2);
                subItem2.setVisibility(View.GONE);
                TextView subItem3 = holder.itemView.findViewById(R.id.child3);
                subItem3.setVisibility(View.GONE);
            } else if (allChildren.size() == 1) {
                TextView subItem1 = holder.itemView.findViewById(R.id.child1);
                subItem1.setVisibility(expanded ? View.VISIBLE : View.GONE);
                TextView subItem2 = holder.itemView.findViewById(R.id.child2);
                subItem2.setVisibility(View.GONE);
                TextView subItem3 = holder.itemView.findViewById(R.id.child3);
                subItem3.setVisibility(View.GONE);
            } else if (allChildren.size() == 2) {
                TextView subItem1 = holder.itemView.findViewById(R.id.child1);
                subItem1.setVisibility(expanded ? View.VISIBLE : View.GONE);
                TextView subItem2 = holder.itemView.findViewById(R.id.child2);
                subItem2.setVisibility(expanded ? View.VISIBLE : View.GONE);
                TextView subItem3 = holder.itemView.findViewById(R.id.child3);
                subItem3.setVisibility(View.GONE);
            } else if (allChildren.size() >= 3) {
                TextView subItem1 = holder.itemView.findViewById(R.id.child1);
                subItem1.setVisibility(expanded ? View.VISIBLE : View.GONE);
                TextView subItem2 = holder.itemView.findViewById(R.id.child2);
                subItem2.setVisibility(expanded ? View.VISIBLE : View.GONE);
                TextView subItem3 = holder.itemView.findViewById(R.id.child3);
                subItem3.setVisibility(expanded ? View.VISIBLE : View.GONE);
            }
        }

        edit_it.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = mContext;
                LinearLayout layout = new LinearLayout(context);
                layout.setOrientation(LinearLayout.VERTICAL);

                final EditText titleBox = new EditText(context);
                titleBox.setText(curr.getName());
                layout.addView(titleBox);

                final EditText descriptionBox = new EditText(context);
                descriptionBox.setText(curr.getDescription());
                layout.addView(descriptionBox);

                final Button dateBox = new Button(context);
                dateBox.setText(curr.getDate());
                dateBox.setId(12345);
                layout.addView(dateBox);

                AppDatabase.getAppDatabase(mContext).nodeDAO().Delete(curr);

                AlertDialog.Builder db = new AlertDialog.Builder(mContext);
                db.setTitle("Edit Task Details");
                db.setMessage("Click to edit any attibute");
                db.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String title = String.valueOf(titleBox.getText());
                        String desc = String.valueOf(descriptionBox.getText());
                        String date = String.valueOf(dateBox.getText());
                        if(title.trim().length() != 0 && desc.trim().length() != 0)
                            addItem(title, desc, date, curr.getParent());
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


                        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext,
                                new DatePickerDialog.OnDateSetListener() {

                                    @Override
                                    public void onDateSet(DatePicker view, int year,
                                                          int monthOfYear, int dayOfMonth) {

                                        String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                        dateBox.setText(date);

                                    }
                                }, mYear, mMonth, mDay);
                        datePickerDialog.show();
                        datePickerDialog.setCancelable(false);
                    }
                });
                db.setCancelable(false);
                db.create().show();
            }
        });

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

                if(currentexp != null) {
                    AppDatabase.getAppDatabase(mContext).nodeDAO().Delete(currentexp);
                    currentexp.setExpanded(false);
                    AppDatabase.getAppDatabase(mContext).nodeDAO().Insert(currentexp);
                }
                NodeAdapter.this.notifyDataSetChanged();

                if(curr != currentexp) {
                    AppDatabase.getAppDatabase(mContext).nodeDAO().Delete(curr);
                    curr.setExpanded(true);
                    currentexp = curr;
                } else {
                    currentexp = null;
                }
                NodeAdapter.this.notifyDataSetChanged();



                NodeAdapter.this.notifyItemChanged(position);
            }

        });



    }

    @Override
    public int getItemCount() {
        return nodes.size();
    }
}
