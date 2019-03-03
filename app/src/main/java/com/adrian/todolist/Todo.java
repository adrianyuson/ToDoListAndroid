package com.adrian.todolist;

import java.util.Date;

public class Todo {

    String taskId;
    String task;
    String who;
    String taskDate;
    boolean done;
    public Todo() {}
    public Todo(String taskId,
                String task,
                String who,
                String taskDate,
                boolean done) {
        this.taskId = taskId;
        this.task = task;
        this.who = who;
        this.taskDate = taskDate;
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

    public String getDueDate() {
        return taskDate;
    }

    public void setDueDate(String taskDate) {
        this.taskDate = taskDate;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
