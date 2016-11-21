package com.youceferie.wakeuplove;

/**
 * Created by YouCef on 27/02/2016.
 */
public interface NotificationRegistrationListener {
    void onSuccess();

    void onError(Throwable e);

    void onCancel();
}
