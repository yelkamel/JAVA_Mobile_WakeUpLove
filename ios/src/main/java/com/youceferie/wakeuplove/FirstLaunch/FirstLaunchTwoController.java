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
@CustomClass("FirstLaunchTwoController")
public class FirstLaunchTwoController extends UIViewController implements UITextFieldDelegate {
    @IBOutlet
    private UITextField partenaireName;
    @IBOutlet
    private UITextField partenairePhone;


    @Override
    public void viewDidLoad() {
        super.viewDidLoad();
        this.partenaireName.setDelegate(this);
        this.partenairePhone.setDelegate(this);
        configureTextField();
    }

    private void configureTextField() {
        partenaireName.setKeyboardType(UIKeyboardType.Alphabet);
        partenaireName.setAutocorrectionType(UITextAutocorrectionType.No);
        partenaireName.setReturnKeyType(UIReturnKeyType.Done);
        partenaireName.setClearButtonMode(UITextFieldViewMode.Never);
        partenaireName.clearsOnBeginEditing();


        partenairePhone.setKeyboardType(UIKeyboardType.NumbersAndPunctuation);
        partenairePhone.setAutocorrectionType(UITextAutocorrectionType.No);
        partenairePhone.setReturnKeyType(UIReturnKeyType.Done);
        partenairePhone.setClearButtonMode(UITextFieldViewMode.Never);
        partenairePhone.clearsOnBeginEditing();


    }


    @Override
    public void prepareForSegue(UIStoryboardSegue segue, NSObject sender) {
    }


    @Override
    public boolean shouldPerformSegue(String identifier, NSObject sender) {
        return (partenaireName.getText().length()>1 && partenairePhone.getText().length() > 5);
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
            if (uiTextField.equals(partenairePhone))
         Main.setPartenaireDico(partenairePhone.getText());
        if  (uiTextField.equals(partenaireName))
            Main.setProfil(partenaireName.getText(),Main.NAME_PARTENAIRE);

    }

    @Override
    public boolean shouldChangeCharacters(UITextField uiTextField, @ByVal NSRange nsRange, String s) {
        boolean result = true;

        if (uiTextField.getText().length() == 15 && s.length() > 0)
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


