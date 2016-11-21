package com.youceferie.wakeuplove;


import org.robovm.apple.foundation.NSRange;
import org.robovm.apple.uikit.*;
import org.robovm.objc.annotation.CustomClass;
import org.robovm.objc.annotation.IBOutlet;
import org.robovm.rt.bro.annotation.ByVal;


/**
 * Created by YouCef on 24/02/2016.
 */
@CustomClass("AlarmDetail")
public class AlarmDetail extends UITableViewController  implements UITextFieldDelegate {

    @IBOutlet
    private UITextField title;
    @IBOutlet
    private UISwitch Switch;
    @IBOutlet
    private UILabel Songs;

    public String getTitle() {
        return title.getText();
    }


    public boolean getSwitch() {
        return Switch.isOn();
    }


    public String getSongs() {
        return Songs.getText();
    }


    private void configureTextField() {
        //  partenaire.setPlaceholder("Placeholder text");
        title.setAutocorrectionType(UITextAutocorrectionType.No);
        title.setReturnKeyType(UIReturnKeyType.Done);
        title.setClearButtonMode(UITextFieldViewMode.Never);
        title.clearsOnBeginEditing();
    }

    @Override
    public void viewDidLoad() {
        super.viewDidLoad();
        this.title.setDelegate(this);
        configureTextField();
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

    }

    @Override
    public boolean shouldChangeCharacters(UITextField uiTextField, @ByVal NSRange nsRange, String s) {
        boolean result = true;

        // restrict the maximum number of characters to 5
        if (uiTextField.getText().length() == 20 && s.length() > 0)
            result = false;
        return result;
    }

    @Override
    public boolean shouldClear(UITextField uiTextField) {
        return true;
    }

    @Override
    public boolean shouldReturn(UITextField uiTextField) {
        uiTextField.resignFirstResponder();
        return true;
    }
}
