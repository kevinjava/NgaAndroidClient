package com.github.kevinjava.ngaclient.service;

import com.github.kevinjava.ngaclient.NgaApp;
import com.github.kevinjava.ngaclient.listener.IRequestFinished;
import com.github.kevinjava.ngaclient.model.HttpRequestBean;
import com.github.kevinjava.ngaclient.model.ThreadData;
import com.github.kevinjava.ngaclient.parse.ParseHelper;
import com.github.kevinjava.ngaclient.service.tasks.NotLoginTask;
import com.github.kevinjava.ngaclient.util.NgaLog;
import com.kevinjava.ngaclient.controller.NetRequestType;

import java.util.HashMap;
import java.util.Observable;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by kevliu on 6/24/14.
 */
public class Datastore extends Observable implements IRequestFinished {
    public HashMap<String, ThreadData> forumDatas;
    private ReentrantLock lock = new ReentrantLock();


    public Datastore() {
        forumDatas = new HashMap<String, ThreadData>();
        NgaLog.i("TAG", "create the datastore");
    }

    @Override
    public void onRequestFinished(HttpRequestBean bean, String response) {
        lock.lock();
        NgaLog.i("TAG", response);
        NgaLog.i("TAG", forumDatas.size() + "==========");
        Object obj = ParseHelper.parse(response);
        if (obj != null) {
            if (obj instanceof ThreadData) {
                ThreadData data = (ThreadData) obj;
                if (data == null) {
                    // not login
                    NgaLog.e("TAG", "object is null");
                    return;
                }

                ThreadData oldData = forumDatas.get(String.valueOf(bean.getFid()));
                if (oldData == null) {
                    forumDatas.put(String.valueOf(bean.getFid()), data);
                } else {
                    if (bean.getType() == NetRequestType.ForumDataRefresh) {
                        NgaLog.i("TAG", "refresh old size:  " + data.getRowNum());
                        data.mergeList(oldData);
                        forumDatas.put(String.valueOf(bean.getFid()), data);
                        NgaLog.i("TAG", "refresh new size:  " + data.getRowNum());
                    } else if (bean.getType() == NetRequestType.ForumDataLoadMore) {
                        NgaLog.i("TAG", "load more old size:  " + oldData.getRowNum());
                        oldData.mergeList(data);
                        forumDatas.put(String.valueOf(bean.getFid()), oldData);
                        NgaLog.i("TAG", "load more new size:  " + oldData.getRowNum());
                    } else {
                        NgaLog.i("TAG", "Show not have the other else");
                    }
                }

                if (bean.getFatherIndex() == NgaApp.getCurrentIndex()) {
                    NgaLog.i("TAG", "send notification to the observer");
                    setChanged();
                    notifyObservers(bean);
                } else {
                    NgaLog.i("TAG", "give the notification because of the father id not the same");
                }
            } else if (obj instanceof NotLoginTask) {
                NgaLog.i("TAG", "need to login");
                NotLoginTask task = (NotLoginTask) obj;
                task.setHttpRequestBean(bean);
                setChanged();
                notifyObservers(task);
            }

        } else {
            NgaLog.e("TAG", "object is null");
        }

        lock.unlock();
    }

    @Override
    public void onError(HttpRequestBean bean, Throwable error) {
        lock.lock();
        NgaLog.e("TAG", error);
        setChanged();
        notifyObservers(new ErrorResponse(bean, error));
        lock.unlock();
    }

    public static class ErrorResponse{
        public HttpRequestBean bean;
        public Throwable error;

        public ErrorResponse(HttpRequestBean bean, Throwable error){
            this.bean = bean;
            this.error = error;
        }
    }

}
