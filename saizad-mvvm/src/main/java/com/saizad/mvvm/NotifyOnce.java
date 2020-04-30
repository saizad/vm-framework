package com.saizad.mvvm;


public class NotifyOnce<T extends BaseNotificationModel> {


    private T notificationModel;
    private boolean isRead;

    public NotifyOnce(T notificationModel) {
        this.notificationModel = notificationModel;
    }

    public boolean isRead() {
        return isRead;
    }

    public String getType() {
        return notificationModel.getType();
    }

    public boolean isSameType(String type){
        return notificationModel.getType().equalsIgnoreCase(type);
    }

    public T getNotificationModel() throws Exception {
        if(isRead){
            throw new Exception("Value already read");
        }
        isRead = true;
        return notificationModel;
    }
}
