package com.adrian.todolist;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TodoListAdapter extends ArrayAdapter<Todo> {
    private Activity context;
    private List<Todo> todoList;

    public TodoListAdapter(Activity context, List<Todo> todoList) {
        super(context, R.layout.list_layout, todoList);
        this.context = context;
        this.todoList = todoList;
    }

    public TodoListAdapter(Context context, int resource, List<Todo> objects, Activity context1, List<Todo> todoList) {
        super(context, resource, objects);
        this.context = context1;
        this.todoList = todoList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_layout, null, true);

        TextView tvTask = listViewItem.findViewById(R.id.textViewTask);
        TextView tvName = listViewItem.findViewById(R.id.textViewName);
        TextView tvDate = listViewItem.findViewById(R.id.textViewDate);
        TextView tvDone = listViewItem.findViewById(R.id.textViewDone);

        Todo todo = todoList.get(position);
        tvTask.setText(todo.getTask());
        tvName.setText(todo.getWho());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yy");
        try {
            Date date = sdf.parse(tvDate.getText().toString().trim());
        } catch (ParseException ex) {
            Log.v("Exception", ex.getLocalizedMessage());
        }
        tvDate.setText(todo.getDueDate().toString());
        if(todo.isDone()) {
            tvDone.setText("Done");
        }
        else {
            tvDone.setText("Not yet done");
        }

        return listViewItem;
    }
}
