package com.youceferie.wakeuplove;

import org.robovm.apple.eventkit.EKAlarm;
import org.robovm.apple.eventkit.EKEntityType;
import org.robovm.apple.eventkit.EKEventStore;
import org.robovm.apple.foundation.*;
import org.robovm.objc.annotation.CustomClass;

import org.robovm.apple.uikit.UIControl;
import org.robovm.apple.uikit.UIDatePicker;
import org.robovm.apple.uikit.UIDatePickerMode;
import org.robovm.apple.uikit.UILabel;
import org.robovm.apple.uikit.UIViewController;
import org.robovm.apple.eventkit.EKCalendarItem;
import org.robovm.objc.annotation.IBOutlet;
import org.robovm.apple.uikit.*;

import java.util.Date;

/**
 * Created by YouCef on 22/02/2016.
 */

@CustomClass("SendAlarmController")
public class SendAlarmController extends UIViewController implements UIControl.OnValueChangedListener
{
    @IBOutlet
    private UIDatePicker datePicker;

    private EKEventStore eventStore;
    private EKCalendarItem cal;
    public UIView getAlarmDetail() {
        return alarmDetail;
    }

    @IBOutlet
    private UIView alarmDetail;

    private EKAlarm ekAlarm;

    // private NSDateFormatter dateFormatter;
    private NSDate date;

    @Override
    public String getTitle() {
        return title;
    }

    public boolean isSnooze() {
        return snooze;
    }

    public String getSongs() {
        return songs;
    }

    private  String title;
    private  boolean snooze;
    private  String songs;


    /// FONCTIONNEL

    @Override
    public void viewDidLoad()
    {
        super.viewDidLoad();
        // Create a date formatter to be used to format the "date" property of
        // "datePicker".
     /*   dateFormatter = new NSDateFormatter();

        dateFormatter.setDateStyle(NSDateFormatterStyle.Medium);
        dateFormatter.setTimeStyle(NSDateFormatterStyle.Short);
       */ configDatePicker();
    }


    private void configDatePicker() {

        datePicker.setDatePickerMode(UIDatePickerMode.Time);
        /*
        // Set min/max date for the date picker.
        // As an example we will limit the date between now and 7 days from now.
        NSDate now = NSDate.now();
        datePicker.setMinimumDate(now);

        NSCalendar currentCalendar = NSCalendar.getCurrentCalendar();

        NSDateComponents dateComponents = new NSDateComponents();
        dateComponents.setDay(7);

        NSDate sevenDaysFromNow = currentCalendar
                .newDateByAddingComponents(dateComponents, now, NSCalendarOptions.None);
        datePicker.setMaximumDate(sevenDaysFromNow);
        */

        // Display the "minutes" interval by increments of 1 minute (this is the
        // default).
     //   datePicker.setMinuteInterval(5);

        datePicker.addOnValueChangedListener(this);

        onValueChanged(null);
    }


    public Date getDate()
    {
        return date.toDate();
    }

    public NSDate getNSDate()
    {
        return date;
    }


    @Override
    public void onValueChanged(UIControl control) {
        date = datePicker.getDate();

        // dateFormatter.setDateFormat("h:mm a");

        /*
        ekAlarm = new EKAlarm(datePicker.getDate());
        cal = new EKCalendarItem();
        cal.addAlarm(ekAlarm);
        */
        //time =  dateFormatter.format(datePicker.getDate());
    }
}
