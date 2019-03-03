package com.adrian.todolist;

import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText editTextTask;
    EditText editTextName;
    Button buttonAddTask;
    EditText taskDate;
    CheckBox status;
    DatabaseReference databaseTodos;

    ListView lvTodos;
    List<Todo> todoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseTodos = FirebaseDatabase.getInstance().getReference("tasks");

        editTextTask = findViewById(R.id.editTextTask);
        editTextName = findViewById(R.id.editTextName);
        taskDate = findViewById(R.id.editTextDate);
        status = findViewById(R.id.checkBoxStatus);
        buttonAddTask = findViewById(R.id.buttonAddTask);

        buttonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask();
            }
        });

        lvTodos = findViewById(R.id.lvTodos);
        todoList = new ArrayList<Todo>();

        lvTodos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Todo todo = todoList.get(position);

//                String
//                if(todo.isDone()) {
//                    tvDone.setText("Done");
//                }
//                else {
//                    tvDone.setText("Not yet done");
//                }

                showUpdateDialog(todo.getTaskId(),
                        todo.getTask(),
                        todo.getWho(),
                        todo.getDueDate(),
                        todo.isDone());

                return false;
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseTodos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                todoList.clear();
                for (DataSnapshot studentSnapshot : dataSnapshot.getChildren()) {
                    Todo todo = studentSnapshot.getValue(Todo.class);
                    todoList.add(todo);
                }

                TodoListAdapter adapter = new TodoListAdapter(MainActivity.this, todoList);
                lvTodos.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    private void addTask() {
        String task = editTextTask.getText().toString().trim();
        String name = editTextName.getText().toString().trim();
        String date = taskDate.getText().toString().trim();
        boolean done = ((CheckBox) findViewById(R.id.checkBoxStatus)).isChecked();

        if (TextUtils.isEmpty(task)) {
            Toast.makeText(this, "You must enter a task.", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "You must enter a name.", Toast.LENGTH_LONG).show();
            return;
        }

        String id = databaseTodos.push().getKey();
        Todo todo = new Todo(id, task, name, date, done);

        Task setValueTask = databaseTodos.child(id).setValue(todo);

        setValueTask.addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(MainActivity.this,
                        "Todo added.",Toast.LENGTH_LONG).show();
            }
        });

        setValueTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this,
                        "something went wrong.\n" + e.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateTodo(String id, String task, String name, String date, boolean done) {
        DatabaseReference dbRef = databaseTodos.child(id);

        Todo todo = new Todo(id, task, name, date, done);

        Task setValueTask = dbRef.setValue(todo);

        setValueTask.addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(MainActivity.this,
                        "Todo Updated.",Toast.LENGTH_LONG).show();
            }
        });

        setValueTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this,
                        "Something went wrong.\n" + e.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showUpdateDialog(final String id, String task, String name, String date, boolean status) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextTask = dialogView.findViewById(R.id.editTextTask);
        editTextTask.setText(task);

        final EditText editTextName = dialogView.findViewById(R.id.editTextName);
        editTextName.setText(name);

        final EditText editTextDate = dialogView.findViewById(R.id.editTextDate);
        editTextDate.setText(date);

        final CheckBox editStatus = dialogView.findViewById(R.id.checkBoxStatus);
        editStatus.setChecked(status);
        editStatus.setText("Done?");

        final Button btnUpdate = dialogView.findViewById(R.id.btnUpdate);

//        dialogBuilder.setTitle("Update Todo " + firstName + " " + lastName);
        dialogBuilder.setTitle("Update Todo");

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String task = editTextTask.getText().toString().trim();
                String name = editTextName.getText().toString().trim();
                String date = editTextDate.getText().toString().trim();
                boolean done = editStatus.isChecked();

                updateTodo(id, task, name, date, done);

                alertDialog.dismiss();
            }
        });
    }


}



