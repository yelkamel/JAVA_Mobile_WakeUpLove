package com.youceferie.wakeuplove;

import com.youceferie.wakeuplove.FirstLaunch.FirstLaunchOneController;
import org.robovm.apple.coregraphics.CGRect;
import org.robovm.apple.foundation.*;
import org.robovm.apple.uikit.*;
import org.robovm.objc.annotation.BindSelector;
import org.robovm.objc.annotation.CustomClass;
import org.robovm.objc.annotation.IBAction;
import org.robovm.objc.annotation.IBOutlet;
import org.robovm.objc.block.VoidBlock1;
import org.robovm.rt.bro.annotation.MachineSizedSInt;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by YouCef on 21/02/2016.
 */

@CustomClass("WakeUpLoveMainController")
public class WakeUpLoveMainController extends UIViewController implements UITableViewDataSource {
 private AlarmList alarmList = new AlarmList();
    public static final boolean PRELOAD_DATA = true;

    private UISwitch sw;

    @Override
    public void viewDidLoad()
    {

        super.viewDidLoad();

        if(!NSUserDefaults.getStandardUserDefaults().getBoolean("hasPerformedFirstLaunch"))
        {
            // Set the "hasPerformedFirstLaunch" key so this block won't execute again
            FirstLaunchOneController firstLaunchOneController = (FirstLaunchOneController) getStoryboard().instantiateViewController("FirstLaunchOneController");
            System.out.print("1 ");
            getNavigationController().pushViewController(firstLaunchOneController,true);
            System.out.print("2");
            NSUserDefaults.getStandardUserDefaults().put("hasPerformedFirstLaunch",true);
            System.out.print("3");
            NSUserDefaults.getStandardUserDefaults().synchronize();
            System.out.print("4");

        }
        else
        {
            Main.loadSetting();
            Main.loadProfil();
            AskNotifRegister();
        }

        NotificationManager.addObserver(NotificationEnum.ALARM_INPUT,
                new VoidBlock1<NSNotification>() {
                    @Override
                    public void invoke(NSNotification notification) {
                        deleteAlarmByRow(alarmList.rowAlarmById(Main.getNotifID()));
                        rescheduleLocalNotification();
                       // makeCall();
                    }
                });

        alarmList.loadAlarms(proivdeAlarm(proivdeConnectionPool()).getAlarms());


    }

    public void makeCall()
    {
        NSURL url1 = new NSURL(String.format("tel://" + "07464289365"));

        if (UIApplication.getSharedApplication().openURL(url1))
            System.out.print("ERROR open tel URL ");
    }



    public void AskNotifRegister() {

        if (Main.getInstance().isRegisteredForRemoteNotifications()) {
            // Let's encode the device token to display it as a string.
            String base64DeviceToken = Main.getInstance().getDeviceToken()
                    .toBase64EncodedString(NSDataBase64EncodingOptions.None);

            System.out.print("App is already registered for remote notifications!\nDevice token: " + base64DeviceToken);
        } else {
            Main.getInstance().registerForRemoteNotifications(new NotificationRegistrationListener() {
                @Override
                public void onSuccess() {
                    // Let's encode the device token to display it as a string.
                    String base64DeviceToken = Main.getInstance().getDeviceToken()
                            .toBase64EncodedString(NSDataBase64EncodingOptions.None);

                    System.out.print(
                            "Successfully registered for remote notifications!\nDevice token: " + base64DeviceToken);
                }

                @Override
                public void onError(Throwable e) {
                    System.out.print("An error happened: " + e.getMessage());
                    e.printStackTrace();
                }

                @Override
                public void onCancel() {
                    System.out.print("Registration was cancelled!");
                }
            });
        }
    }

    //  @Provides
    //  @Singleton
    public ConnectionPool proivdeConnectionPool() {
        try {
            Class.forName("SQLite.JDBCDriver");
        } catch (ClassNotFoundException e) {
            throw new Error(e);
        }

        /*
         * The SQLite database is kept in
         * <Application_Home>/Documents/db.sqlite. This directory is backed up
         * by iTunes. See http://goo.gl/BWlCGN for Apple's docs on the iOS file
         * system.
         */
        File dbFile = new File(System.getenv("HOME"), "Documents/db.sqlite");
        dbFile.getParentFile().mkdirs();
        Foundation.log("Using db in file: " + dbFile.getAbsolutePath());

        return new SingletonConnectionPool(
                "jdbc:sqlite:" + dbFile.getAbsolutePath());
    }

    //    @Provides
    //  @Singleton
    public JdbcAlarm proivdeAlarm(ConnectionPool connectionPool) {
        JdbcAlarm test =  new JdbcAlarm(connectionPool, PRELOAD_DATA);

        return test;
    }


    @IBAction
    public void editMode() {
        NSUserDefaults.getStandardUserDefaults().put("hasPerformedFirstLaunch",false);
       if (alarmTable.isEditing())
        {
            alarmTable.setEditing(false,true);
        }
        else
        {
            alarmTable.setEditing(true,true);
        }

    }

    @IBOutlet
    private UITableView alarmTable;

    @Override
    public void viewWillAppear(boolean animated) {

        if (alarmList == null)
        {
            alarmList.loadAlarms(proivdeAlarm(proivdeConnectionPool()).getAlarms());
        }

        Main.loadSetting();
        Main.loadProfil();
        AskNotifRegister();

        System.out.print("ViewWILLAppear FINISH\n");
        alarmTable.reloadData();
        super.viewWillAppear(animated);



    }


    @Override
    public void prepareForSegue(UIStoryboardSegue segue, NSObject sender)
    {
        super.prepareForSegue(segue,sender);
        alarmTable.setEditing(false,true);

    }

    @IBAction
    public void unwindToWakeUpLove(UIStoryboardSegue segue)
    {
        if (segue.getIdentifier().equals("SendAlarm")) {

            SendAlarmController sendAlarmController = (SendAlarmController) segue.getSourceViewController();
            AlarmDetail alarmdetail = (AlarmDetail) sendAlarmController.getChildViewControllers().get(0);
            /// CREEE TON ALARM TA MERE
           // alarmList.addAlarm(sendAlarmController.getDate(), alarmdetail.getTitle(), alarmdetail.getSwitch(), alarmdetail.getSongs());

            Alarm tmp = new Alarm(sendAlarmController.getDate(),alarmdetail.getTitle(),alarmdetail.getSwitch(),alarmdetail.getSongs());
            proivdeAlarm(proivdeConnectionPool()).insert(tmp);
            Main.getInstance().scheduleLocalNotificationWithDate(sendAlarmController.getNSDate(),tmp.getId());

            alarmTable.reloadData();


        }
        alarmList.loadAlarms(proivdeAlarm(proivdeConnectionPool()).getAlarms());
    }

    public void rescheduleLocalNotification() {
        UIApplication.getSharedApplication().cancelAllLocalNotifications();
        for (int i = 0; i < alarmList.getSize(); i++) {
            Main.getInstance().scheduleLocalNotificationWithDate(new NSDate(alarmList.getAlarmByRow(i).getDate()),alarmList.getAlarmByRow(i).getId());
        }
    }

    @Override
    public long getNumberOfRowsInSection(UITableView uiTableView, long l) {
        return  alarmList.getSize();
    }

    @Override
    public UITableViewCell getCellForRow(UITableView tableView, NSIndexPath indexPath) {
        int row = indexPath.getRow();
          sw = new UISwitch(new CGRect(20, 15, 22, 22));


        UITableViewCell cell = tableView.dequeueReusableCell("AlarmTableCell");
        if(cell == null) {
            cell = new UITableViewCell(UITableViewCellStyle.Default, "AlarmTableCell");
        }
        cell.setAccessoryView(sw);
        sw.addOnValueChangedListener(new UIControl.OnValueChangedListener() {
            @Override
            public void onValueChanged(UIControl control) {
               alarmList.cancelAlarm(row, !alarmList.getAlarmByRow(row).isCancel());
            }});

        cell.setShouldIndentWhileEditing(true);
        sw.setOn(!alarmList.getAlarmByRow(row).isCancel());

        cell.getTextLabel().setText(alarmList.getStrAlarmByRow(row));
        cell.getDetailTextLabel().setText(alarmList.getAlarmByRow(row).getTitle());
        return cell;

    }

    @Override
    public long getNumberOfSections(UITableView uiTableView) {
        return 1;
    }

    @Override
    public String getTitleForHeader(UITableView uiTableView, @MachineSizedSInt long l) {
        return null;
    }

    @Override
    public String getTitleForFooter(UITableView uiTableView, @MachineSizedSInt long l) {
        return null;
    }

    @Override
    public boolean canEditRow(UITableView uiTableView, NSIndexPath nsIndexPath) {

        return true;
    }

    @Override
    public boolean canMoveRow(UITableView uiTableView, NSIndexPath nsIndexPath) {
        return false;
    }

    @Override
    public List<String> getSectionIndexTitles(UITableView uiTableView) {
        return null;
    }

    @Override
    public long getSectionForSectionIndexTitle(UITableView uiTableView, String s, @MachineSizedSInt long l) {
        return 0;
    }

    @Override
    public void commitEditingStyleForRow(UITableView uiTableView, UITableViewCellEditingStyle uiTableViewCellEditingStyle, NSIndexPath nsIndexPath) {

        if (uiTableViewCellEditingStyle.equals(UITableViewCellEditingStyle.Delete))
        {
            int row = nsIndexPath.getRow();
            deleteAlarmByRow(row);
            rescheduleLocalNotification();
        }


    }

    public  void deleteAlarmByRow(int row )
    {
        proivdeAlarm(proivdeConnectionPool()).delete(alarmList.getAlarmByRow(row));

        //YouTEST
        System.out.print("DeleteAlarm row number : " + row + " with ID " + alarmList.getAlarmByRow(row).getId() + "\n");
        alarmList.deleteAlarmByRow(row);
        alarmTable.reloadData();

    }
    @Override
    public void moveRow(UITableView uiTableView, NSIndexPath nsIndexPath, NSIndexPath nsIndexPath1) {

    }

}
