package com.youceferie.wakeuplove.FirstLaunch;

import com.youceferie.wakeuplove.Main;
import org.robovm.apple.foundation.NSDate;
import org.robovm.apple.foundation.NSObject;
import org.robovm.apple.foundation.NSRange;
import org.robovm.apple.uikit.*;
import org.robovm.objc.annotation.CustomClass;
import org.robovm.objc.annotation.IBOutlet;
import org.robovm.rt.bro.annotation.ByVal;


/**
 * Created by YouCef on 06/03/2016.
 */
@CustomClass("FirstLaunchThreeController")
public class FirstLaunchThreeController  extends UIViewController implements UIControl.OnValueChangedListener, UITextFieldDelegate {
    @IBOutlet
    private UITextField meetCity;

    @IBOutlet
    private UIDatePicker datePicker;


    @Override
    public void viewDidLoad() {
        super.viewDidLoad();
        this.meetCity.setDelegate(this);
        datePicker.setDatePickerMode(UIDatePickerMode.Date);
        configureTextField();
    }

    private void configureTextField() {
        meetCity.setAutocorrectionType(UITextAutocorrectionType.No);
        meetCity.setReturnKeyType(UIReturnKeyType.Done);
        meetCity.setClearButtonMode(UITextFieldViewMode.Never);
        meetCity.clearsOnBeginEditing();

    }

    @Override
    public void onValueChanged(UIControl control) {

    }


    @Override
    public boolean shouldBeginEditing(UITextField uiTextField) {
        return true;
    }

    @Override
    public void didBeginEditing(UITextField uiTextField) {

    }

    @Override
    public boolean shouldEndEditing(UITextField uiTextField) {
        return true;
    }

    @Override
    public void didEndEditing(UITextField uiTextField) {

         Main.setProfil(meetCity.getText(),Main.MEET_CITY);

    }



    @Override
    public void prepareForSegue(UIStoryboardSegue segue, NSObject sender) {
        if (segue.getIdentifier().equals("NextThree")) {
                Main.setProfil(datePicker.getDate().toString(), Main.MEET_DATE);
        }
    }


    @Override
    public boolean shouldPerformSegue(String identifier, NSObject sender) {
        return (meetCity.getText().length() > 1);
    }

    @Override
    public boolean shouldChangeCharacters(UITextField uiTextField, @ByVal NSRange nsRange, String s) {
        boolean result = true;

        if (uiTextField.getText().length() == 10 && s.length() > 0)
            result = false;
        return result;
    }

    @Override
    public boolean shouldClear(UITextField uiTextField) {
        return true;
    }

    @Override
    public boolean shouldReturn(UITextField uiTextField) {
        if (uiTextField.getText().length() < 2)
            return false;

        uiTextField.resignFirstResponder();
        return true;
    }

}
