package com.youceferie.wakeuplove;

/**
 * Created by YouCef on 29/02/2016.
 */
public enum NotificationEnum {
 /*   DID_RECEIVE_REMOTE_NOTIFICATION("com.parse.Anypic.appDelegate.applicationDidReceiveRemoteNotification"),
    USER_FOLLOWING_CHANGED("com.parse.Anypic.utility.userFollowingChanged"),
    USER_LIKING_PHOTO_CALLBACK_FINISHED("com.parse.Anypic.utility.userLikedUnlikedPhotoCallbackFinished"),
    DID_FINISH_PROCESSING_PROFILE_PICTURE("com.parse.Anypic.utility.didFinishProcessingProfilePictureNotification"),
    DID_FINISH_EDITING_PHOTO("com.parse.Anypic.tabBarController.didFinishEditingPhoto"),
    DID_FINISH_IMAGE_FILE_UPLOAD("com.parse.Anypic.tabBarController.didFinishImageFileUploadNotification"),
    USER_DELETED_PHOTO("com.parse.Anypic.photoDetailsViewController.userDeletedPhoto"),
    USER_LIKES_PHOTO("com.parse.Anypic.photoDetailsViewController.userLikedUnlikedPhotoInDetailsViewNotification"),
*/
    ALARM_INPUT("com.youceferie.wakeuplove.wakeuplovemaincontroller");

    private String name;

    NotificationEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}