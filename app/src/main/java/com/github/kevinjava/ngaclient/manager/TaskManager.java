package com.github.kevinjava.ngaclient.manager;

import java.util.concurrent.LinkedBlockingQueue;

import com.github.kevinjava.ngaclient.service.Task;

/**
 * Created by kevliu on 6/24/14.
 */
public class TaskManager {

    LinkedBlockingQueue<Task> tasks;

    static class TaskManagerHandler{
        static TaskManager taskManager = new TaskManager();
    }

    private TaskManager(){
        tasks = new LinkedBlockingQueue<Task>();
        TaskExecutor.start();
    }

    public static TaskManager getInstance(){
        return TaskManagerHandler.taskManager;
    }

    public void addTask(Task task){
        tasks.offer(task);
    }

    Thread TaskExecutor = new Thread(){
        @Override
        public void run() {
            while (true){
                try {
                    Task task = tasks.take();
                    task.execue();
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    };
}
