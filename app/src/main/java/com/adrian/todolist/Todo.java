package com.adrian.todolist;

import java.util.Date;

public class Todo {

    String taskId;
    String task;
    String who;
    Date dueDate;
    boolean done;
    public Todo() {}
    public Todo(String taskId,
                String task,
                String who,
                Date dueDate,
                boolean done) {
        this.taskId = taskId;
        this.task = task;
        this.who = who;
        this.dueDate = dueDate;
        this.done = done;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
