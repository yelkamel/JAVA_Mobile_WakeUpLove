package com.youceferie.wakeuplove.FirstLaunch;

import com.youceferie.wakeuplove.Main;
import org.robovm.apple.foundation.NSObject;
import org.robovm.apple.foundation.NSRange;
import org.robovm.apple.uikit.*;
import org.robovm.objc.annotation.CustomClass;
import org.robovm.objc.annotation.IBOutlet;
import org.robovm.rt.bro.annotation.ByVal;

/**
 * Created by YouCef on 06/03/2016.
 */
@CustomClass("FirstLaunchOneController")
public class FirstLaunchOneController extends UIViewController implements UITextFieldDelegate  {

    @IBOutlet
    private UISegmentedControl gender;


    @IBOutlet
    private UITextField name;



    @Override
    public void viewDidLoad() {
        super.viewDidLoad();
        this.name.setDelegate(this);
        configureTextField();

        if (Main.firstLaunch())
        {
//            prepareForSegue()
        }
    }

    private void configureTextField() {

        name.setKeyboardType(UIKeyboardType.Alphabet);
        name.setAutocorrectionType(UITextAutocorrectionType.No);
        name.setReturnKeyType(UIReturnKeyType.Done);
        name.setClearButtonMode(UITextFieldViewMode.Never);
        name.clearsOnBeginEditing();
      //  name.setKeyboardType(UIKeyboardType.PhonePad);
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

        if (uiTextField.equals(name))
        Main.setProfil(name.getText(),Main.NAME);

    }


    @Override
    public void prepareForSegue(UIStoryboardSegue segue, NSObject sender) {
        if (segue.getIdentifier().equals("NextOne")) {
            if (gender.getSelectedSegment() == 0)
            Main.setProfil("M", Main.GENDER);
            if (gender.getSelectedSegment() == 1)
                Main.setProfil("F", Main.GENDER);
        }
        if (segue.getIdentifier().equals("NextDirect")) {

        }
    }

    @Override
    public boolean shouldPerformSegue(String identifier, NSObject sender) {
        return (name.getText().length()>1);
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

        uiTextField.resignFirstResponder();
        return true;
    }




}
