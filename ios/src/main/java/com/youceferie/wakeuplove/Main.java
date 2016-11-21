package com.youceferie.wakeuplove;

import org.robovm.apple.foundation.*;
import org.robovm.apple.uikit.*;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Main extends UIApplicationDelegateAdapter {

    private static final String DEVICE_TOKEN_USER_PREF = "deviceToken";
    private static final String LOCAL_NOTIFICATION_ID_KEY = "ID";
    private static final String NOTIFICATION_INVITE_ACCEPT_ID = "ACCEPT_ID";
    private static final String NOTIFICATION_INVITE_DECLINE_ID = "DECLINE_ID";
    public static final String NOTIFICATION_INVITE_CATEGORY = "INVITE_ID";
     //NSBundle.getMainBundle().getBundlePath()+ "/Settings.bundle/YouSetting.plist";
    private static int NotifID;

    // SETTING
    public static final String NB_WAKE_UP_LOVE =  "NbWakeUpLove";
    public static final String NB_PING_LOVE =  "NbPingLove";
    public static final String LAST_WAKE_UP_LOVE =  "LastWakeUpLove";
    public static final String PARTENAIRE =  "Partenaire";
    public static final String TURN_OFF =  "TurnOff";

    // PROFIL
    public static final String NAME =  "name";
    public static final String NAME_PARTENAIRE =  "namePartenaire";
    public static final String GENDER =  "gender";
    public static final String MEET_CITY =  "meetCity";
    public static final String MEET_DATE =  "meetDate";



    public static final File YOUSETTING =  new File("/Users/YouCef/RoboVMStudioProjects/WakeUpLove/ios/YouSetting.plist.xml");
    public static final File YOUPROFIL =  new File("/Users/YouCef/RoboVMStudioProjects/WakeUpLove/ios/YouProfil.plist.xml");





    private NotificationRegistrationListener notificationRegistrationListener;

    @Override
    public boolean didFinishLaunching(UIApplication application, UIApplicationLaunchOptions launchOptions) {
        return true;
    }

    /**
     * @return our stored device token or null.
     */
    public NSData getDeviceToken() {
        return NSUserDefaults.getStandardUserDefaults().getData(DEVICE_TOKEN_USER_PREF);
    }

    public static void main(String[] args) {

        try (NSAutoreleasePool pool = new NSAutoreleasePool()) {
            UIApplication.main(args, null, Main.class);
        }




    }

    public  static void loadSetting() {

        NSDictionary<NSString, NSObject> settingsDict = (NSDictionary<NSString, NSObject>) NSDictionary
                .read(YOUSETTING);

        YouSetting.nbPingLove = Integer.parseInt(settingsDict.get(NB_PING_LOVE).toString());
        YouSetting.nbWakeUpLove  = Integer.parseInt(settingsDict.get(NB_WAKE_UP_LOVE).toString());
        YouSetting.lastWakeUpLove  =  settingsDict.get(LAST_WAKE_UP_LOVE).toString();
        YouSetting.partenaire  = settingsDict.get(PARTENAIRE).toString();
        YouSetting.turnOff  = Boolean.valueOf(settingsDict.get(TURN_OFF).toString());




    }

    public static boolean firstLaunch ()
    {

        NSDictionary<NSString, NSObject> settingsDictProfil = (NSDictionary<NSString, NSObject>) NSDictionary
                .read(YOUPROFIL);
            return (settingsDictProfil !=null);
    }

    public static void loadProfil()
    {
        NSDictionary<NSString, NSObject> settingsDictProfil = (NSDictionary<NSString, NSObject>) NSDictionary
                .read(YOUPROFIL);

        YouSetting.name = settingsDictProfil.get(NAME).toString();
        YouSetting.namePartenaire  = settingsDictProfil.get(NAME_PARTENAIRE).toString();
        YouSetting.meetCity  =  settingsDictProfil.get(MEET_CITY).toString();
        YouSetting.meetDate  = settingsDictProfil.get(MEET_DATE).toString();
        YouSetting.gender  = settingsDictProfil.get(GENDER).toString();
    }

    public static void setProfil(String str, String type)
    {
        loadProfil();
        if (type.equals(Main.NAME_PARTENAIRE))
        YouSetting.namePartenaire = str;
        if (type.equals(Main.NAME))
            YouSetting.name = str;
        if (type.equals(Main.MEET_DATE))
            YouSetting.meetDate = str;
        if (type.equals(Main.MEET_CITY))
            YouSetting.meetCity = str;
        if (type.equals(Main.GENDER))
        {
            YouSetting.gender = str;
        }

        Map<String, NSString> tmp = new HashMap<>();
        tmp.put(Main.GENDER,new NSString(YouSetting.gender));
        tmp.put(Main.MEET_CITY,new NSString(YouSetting.meetCity));
        tmp.put(Main.MEET_DATE,new NSString(YouSetting.meetDate));
        tmp.put(Main.NAME,new NSString(YouSetting.name));
        tmp.put(Main.NAME_PARTENAIRE,new NSString(YouSetting.namePartenaire));

        NSDictionary mutableDictionary = NSDictionary.fromStringMap(tmp);

        if(!mutableDictionary.write(YOUPROFIL, true)) {
            System.out.println("Ecriture echouer");
        }
    }

    public static void setPartenaireDico(String str)
    {
        loadSetting();
        YouSetting.partenaire = str;

        Map<String, NSString> tmp = new HashMap<>();
        tmp.put(Main.PARTENAIRE,new NSString(str));
        tmp.put(Main.LAST_WAKE_UP_LOVE,new NSString(YouSetting.lastWakeUpLove));
        tmp.put(Main.NB_PING_LOVE,new NSString(String.valueOf(YouSetting.nbPingLove)));
        tmp.put(Main.NB_WAKE_UP_LOVE,new NSString(String.valueOf(YouSetting.nbWakeUpLove)));
        tmp.put(Main.TURN_OFF,new NSString(String.valueOf(YouSetting.turnOff)));

        NSDictionary mutableDictionary = NSDictionary.fromStringMap(tmp);

        if(!mutableDictionary.write(YOUSETTING, true)) {
            System.out.println("Ecriture echouer");
        }
    }




    public WakeUpLoveMainController getMyViewController() {
        return (WakeUpLoveMainController) getWindow().getRootViewController();
    }



    public static Date getDateFromStr(String str)
    {
        Date date = null;

        SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        //String dateInString = "Wed Oct 16 00:00:00 CEST 2015";

        try {
            date = formatter.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static Main getInstance() {
        return (Main) UIApplication.getSharedApplication().getDelegate();
    }



    public void scheduleLocalNotificationWithDate(NSDate date, int id)
    {
        UILocalNotification localNotification= new UILocalNotification();
        localNotification.setRepeatInterval(NSCalendarUnit.Day);

        if (localNotification == null)
        {
            System.out.println("LocalNotification = null");
            return;
        }

        NSDictionary<?, ?> userInfo = new NSMutableDictionary<>();
        userInfo.put(LOCAL_NOTIFICATION_ID_KEY, id);
        localNotification.setUserInfo(userInfo);
        localNotification.setAlertTitle("TestLarm");
        localNotification.setAlertBody("Sa Sonne ?");
        localNotification.setAlertAction("START APP");
        localNotification.setSoundName("flutestone.mp3");
        localNotification.setFireDate(date);
        /*
        if (category != null) {
            // This will make the notification actionable.
            localNotification.setCategory(category);
        }*/

        UIApplication.getSharedApplication().scheduleLocalNotification(localNotification);


    }


    /**
     * return true if the app is registered for remote notifications.
     */
    public boolean isRegisteredForRemoteNotifications() {
        if (Foundation.getMajorSystemVersion() >= 8) {
            return UIApplication.getSharedApplication().isRegisteredForRemoteNotifications();
        } else {
            UIRemoteNotificationType types = UIApplication.getSharedApplication().getEnabledRemoteNotificationTypes();
            return types.contains(UIRemoteNotificationType.Badge) && types.contains(UIRemoteNotificationType.Alert)
                    && types.contains(UIRemoteNotificationType.Sound);
        }
    }

    // notifcation quelquooaque
    public void scheduleLocalNotification(String id, String category, String title, String message, String action,
                                          Date fireDate) {
        UILocalNotification notification = new UILocalNotification();
        NSDictionary<?, ?> userInfo = new NSMutableDictionary<>();
        userInfo.put(LOCAL_NOTIFICATION_ID_KEY, id);
        notification.setUserInfo(userInfo);
        notification.setAlertTitle(title);
        notification.setAlertBody(message);
        notification.setAlertAction(action);
        notification.setFireDate(new NSDate(fireDate));

        if (category != null) {
            // This will make the notification actionable.
            notification.setCategory(category);
        }

        UIApplication.getSharedApplication().scheduleLocalNotification(notification);
    }

    @Override
    public void didRegisterUserNotificationSettings(UIApplication application,
                                                    UIUserNotificationSettings notificationSettings) {
        // On iOS 8+ we will get here when the user grant or decline
        // notification access.

        // Check if the user granted notification access.
        UIUserNotificationType types = notificationSettings.getTypes();
        if (types.contains(UIUserNotificationType.Alert) && types.contains(UIUserNotificationType.Badge)
                && types.contains(UIUserNotificationType.Sound)) {
            // User granted notification access. Register for remote
            // notifications.
           // UIApplication.getSharedApplication().registerUserNotificationSettings();

            UIApplication.getSharedApplication().registerForRemoteNotifications();
        } else {
            // User declined notification access.

            // Call the cancel callback.
            if (notificationRegistrationListener != null) {
                notificationRegistrationListener.onCancel();
                notificationRegistrationListener = null;
            }
        }
    }


    /**
     * Register for notifications. We want Alert, Badge and Sound notification
     * types.
     *
     * @param listener Will be called when the user accepts or denies app
     *            notifications.
     */
    public void registerForRemoteNotifications(NotificationRegistrationListener listener) {
        this.notificationRegistrationListener = listener;

        // On iOS 8+ we have to register user notification settings first.
        if (Foundation.getMajorSystemVersion() >= 8) {
            // Specify the allowed notification types.
            UIUserNotificationType types = UIUserNotificationType.with(UIUserNotificationType.Alert,
                    UIUserNotificationType.Badge, UIUserNotificationType.Sound);

            // Let's create a few sample actions and categories for our
            // notifications.
            UIUserNotificationAction acceptAction = new UIMutableUserNotificationAction();
            acceptAction.setIdentifier(NOTIFICATION_INVITE_ACCEPT_ID);
            acceptAction.setTitle("Accept");
            // Specifies whether the app must be in the foreground to perform
            // the action.
            acceptAction.setActivationMode(UIUserNotificationActivationMode.Background);

            UIUserNotificationAction declineAction = new UIMutableUserNotificationAction();
            declineAction.setIdentifier(NOTIFICATION_INVITE_DECLINE_ID);
            declineAction.setTitle("Decline");
            declineAction.setDestructive(true);
            declineAction.setActivationMode(UIUserNotificationActivationMode.Background);

            UIUserNotificationCategory inviteCategory = new UIMutableUserNotificationCategory();
            inviteCategory.setIdentifier(NOTIFICATION_INVITE_CATEGORY);
            inviteCategory.setActions(new NSArray<>(acceptAction, declineAction),
                    UIUserNotificationActionContext.Default);

            UIUserNotificationSettings notificationSettings = new UIUserNotificationSettings(
                    types, new NSSet<>(inviteCategory));

            // This will prompt the user to allow app notifications.
            UIApplication.getSharedApplication().registerUserNotificationSettings(notificationSettings);
            /*
             * **NOTE** Only the first time you call this method the user will
             * be prompted. On subsequent calls the delegate method
             * didRegisterUserNotificationSettings will be called immediately
             * with the active settings.
             */
            /*
             * **NOTE** If you are not interested in what notification types the
             * user did allow or not, you can directly register for remote
             * notifications here.
             */
        } else {
            // Specify the allowed notification types.
            UIRemoteNotificationType types = UIRemoteNotificationType.with(UIRemoteNotificationType.Badge,
                    UIRemoteNotificationType.Alert,
                    UIRemoteNotificationType.Sound);

            // This will prompt the user to allow app notifications.
            UIApplication.getSharedApplication().registerForRemoteNotificationTypes(types);
        }
    }

    /**
     * Will be called when the user accepted remote notifications and whenever
     * the device token changes.
     *
     * @param application
     * @param deviceToken will be used to identify this device from remote push
     *            notification servers.
     */
    @Override
    public void didRegisterForRemoteNotifications(UIApplication application, NSData deviceToken) {
        if (deviceToken != null) {
            /*
             * **IMPORTANT** Normally you would send the deviceToken to a push
             * notification server to be able to send push notifications to this
             * device.
             */

            // Let's store the device token in the app preferences.
            NSUserDefaults.getStandardUserDefaults().put(DEVICE_TOKEN_USER_PREF, deviceToken);
            NSUserDefaults.getStandardUserDefaults().synchronize();
        }

        // Call the success callback.
        if (notificationRegistrationListener != null) {
            notificationRegistrationListener.onSuccess();
            notificationRegistrationListener = null;
        }
    }

    /**
     * Will be called when the registration for remote notifications failed.
     *
     * @param application
     * @param error
     */
    @Override
    public void didFailToRegisterForRemoteNotifications(UIApplication application, NSError error) {
        // Call the error callback.
        if (notificationRegistrationListener != null) {
            notificationRegistrationListener.onError(new NSErrorException(error));
            notificationRegistrationListener = null;
        }
    }

    /**
     * If the app is in front this callback will be called.
     * <p>
     * If the app is in back an alert will be displayed and this callback will
     * only be called if the user taps on the alert.
     *
     * @param application
     * @param notification
     */
    @Override
    public void didReceiveLocalNotification(UIApplication application, UILocalNotification notification) {
        // Get and print our id parameter.
      /*  String id = ;
        setNotifID(id);*/
        NSNumber tmp = (NSNumber) notification.getUserInfo().get(LOCAL_NOTIFICATION_ID_KEY);
        setNotifID(tmp.intValue());

        // TODO : Phone call and notifcation receive
       NotificationManager.postNotification(NotificationEnum.ALARM_INPUT);

        System.out.print("notification ID = " + notification.getUserInfo().get(LOCAL_NOTIFICATION_ID_KEY)+"\n");
    }


    /**
     * Will be called when the app receives a remote notification.
     *
     * @param application
     * @param userInfo
     */
    @Override
    public void didReceiveRemoteNotification(UIApplication application, UIRemoteNotification userInfo) {

        System.out.print("Did receive remote notification.");
    }


    /**
     * Will be called when the user taps on an local notification action button.
     *
     * @param application
     * @param identifier
     * @param notification
     * @param completionHandler
     */
    @Override
    public void handleLocalNotificationAction(UIApplication application, String identifier,
                                              UILocalNotification notification, Runnable completionHandler) {
        // Handle the different notification actions we defined.
        if (identifier.equals(NOTIFICATION_INVITE_ACCEPT_ID)) {
            System.out.print("Handle local notification action: ACCEPT INVITE");
        } else if (identifier.equals(NOTIFICATION_INVITE_DECLINE_ID)) {
            System.out.print("Handle local notification action: DECLINE INVITE");
        }

        // Must be called when you are done handling the notification.
        completionHandler.run();
    }


    /**
     * Will be called when the user taps on an remote notification action
     * button.
     *
     * @param application
     * @param identifier
     * @param userInfo
     * @param completionHandler
     */
    @Override
    public void handleRemoteNotificationAction(UIApplication application, String identifier,
                                               UIRemoteNotification userInfo, Runnable completionHandler) {
        // Handle the different notification actions we defined.
        if (identifier.equals(NOTIFICATION_INVITE_ACCEPT_ID)) {
            System.out.print("Handle remote notification action: ACCEPT INVITE");
        } else if (identifier.equals(NOTIFICATION_INVITE_DECLINE_ID)) {
            System.out.print("Handle remote notification action: DECLINE INVITE");
        }

        // Must be called when you are done handling the notification.
        completionHandler.run();
    }

    public static int getNotifID() {
        return NotifID;
    }

    public static void setNotifID(int notifID) {
        NotifID = notifID;
    }

}
