package com.ctrlaltdefeat.planner3.ui.home;

import android.provider.BaseColumns;

public class TaskClass {
    private TaskClass() {
    }
    public static final class TaskEntry implements BaseColumns {
        public static final String TABLE_NAME = "nodes";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DESCR = "descr";
        public static final String COLUMN_SCHEDULED = "scheduled";
        public static final String COLUMN_PARENT = "parent";
        public static boolean expanded = false;

        public static void setExpanded( boolean b){
            expanded = b;
        }
    }
}
