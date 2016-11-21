package com.youceferie.wakeuplove;

import org.robovm.apple.foundation.NSMutableDictionary;
import org.robovm.apple.foundation.NSRange;
import org.robovm.apple.foundation.NSString;
import org.robovm.apple.uikit.*;
import org.robovm.objc.annotation.CustomClass;
import org.robovm.objc.annotation.IBOutlet;
import org.robovm.rt.bro.annotation.ByVal;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by YouCef on 01/03/2016.
 */
@CustomClass("SettingTableViewController")
public class SettingTableViewController  extends UITableViewController  implements UITextFieldDelegate {

    @IBOutlet
    private UITextField partenaire;
    @IBOutlet
    private UILabel nbwakeup;

    @IBOutlet
    private UILabel nbping;

    @IBOutlet
    private UILabel lastwakeup;

    public UISwitch getTurnoff() {
        return turnoff;
    }

    private void configureTextField() {
      //  partenaire.setPlaceholder("Placeholder text");
        partenaire.setAutocorrectionType(UITextAutocorrectionType.No);
        partenaire.setReturnKeyType(UIReturnKeyType.Done);
        partenaire.setClearButtonMode(UITextFieldViewMode.Never);
        partenaire.clearsOnBeginEditing();
        partenaire.setKeyboardType(UIKeyboardType.PhonePad);
    }

    @Override
    public void viewDidLoad() {
        super.viewDidLoad();
        this.partenaire.setDelegate(this);
        configureTextField();
    }

    public void setTurnoff(boolean turnoff) {
        this.turnoff.setOn(!turnoff);
    }

    @IBOutlet
    private UISwitch turnoff;

    public UITextField getPartenaire() {
        return partenaire;
    }

    public void setPartenaire(String partenaire) {
        this.partenaire.setText(partenaire);
    }

    public UILabel getNbwakeup() {
        return nbwakeup;
    }

    public void setNbwakeup(String nbwakeup) {
        this.nbwakeup.setText(nbwakeup);
    }

    public UILabel getNbping() {
        return nbping;
    }

    public void setNbping(String nbping) {
        this.nbping.setText(nbping);
    }

    public UILabel getLastwakeup() {
        return lastwakeup;
    }

    public void setLastwakeup(String lastwakeup) {
        this.lastwakeup.setText(lastwakeup);
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
/*
        NSMutableDictionary *newDict = [[NSMutableDictionary alloc] init];
        [newDict addEntriesFromDictionary: dict];
        [newDict setObject:@"<value>" forKey:@"<Key Name>"];
  */

        Main.setPartenaireDico(partenaire.getText());

    }

    @Override
    public boolean shouldChangeCharacters(UITextField uiTextField, @ByVal NSRange nsRange, String s) {
        boolean result = true;

        // restrict the maximum number of characters to 5
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
      //  System.out.print("Return touch \n");

        //this.getView().endEditing(true);
        return true;
    }

}