package com.youceferie.wakeuplove;



import org.robovm.apple.foundation.NSDictionary;
import org.robovm.apple.foundation.NSNotification;
import org.robovm.apple.foundation.NSNotificationCenter;
import org.robovm.apple.foundation.NSObject;
import org.robovm.apple.foundation.NSOperationQueue;
import org.robovm.apple.foundation.NSString;
import org.robovm.apple.uikit.UIRemoteNotification;
import org.robovm.objc.block.VoidBlock1;


/**
 * Created by YouCef on 29/02/2016.
 */
public class NotificationManager {
    public static NSObject addObserver(NotificationEnum notification, VoidBlock1<NSNotification> callback) {
        return NSNotificationCenter.getDefaultCenter().addObserver(notification.getName(), null,
                NSOperationQueue.getMainQueue(), callback);
    }

    public static NSObject addObserver(NotificationEnum notification, NSObject object,
                                       VoidBlock1<NSNotification> callback) {
        return NSNotificationCenter.getDefaultCenter().addObserver(notification.getName(), object,
                NSOperationQueue.getMainQueue(), callback);
    }

    public static void removeObserver(NSObject observer) {
        NSNotificationCenter.getDefaultCenter().removeObserver(observer);
    }

    public static void postNotification(NotificationEnum notification) {
        postNotification(notification, (NSDictionary<?, ?>) null);
    }

    public static void postNotification(NotificationEnum notification, String object) {
        postNotification(notification, new NSString(object), null);
    }

    public static void postNotification(NotificationEnum notification, NSObject object) {
        postNotification(notification, object, null);
    }

    public static void postNotification(NotificationEnum notification, UIRemoteNotification userInfo) {
        NSNotificationCenter.getDefaultCenter().postNotification(notification.getName(), null, userInfo);
    }

    public static void postNotification(NotificationEnum notification, NSDictionary<?, ?> userInfo) {
        postNotification(notification, null, userInfo);
    }

    public static void postNotification(NotificationEnum notification, NSObject object, NSDictionary<?, ?> userInfo) {
        NSNotificationCenter.getDefaultCenter().postNotification(notification.getName(), object, userInfo);
    }

}