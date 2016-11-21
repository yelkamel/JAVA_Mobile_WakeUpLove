package com.youceferie.wakeuplove;

import org.robovm.apple.uikit.*;
import org.robovm.objc.annotation.CustomClass;
import org.robovm.objc.annotation.IBOutlet;

/**
 * Created by YouCef on 01/03/2016.
 */
@CustomClass("SeetingController")
class SettingController extends UIViewController implements UIControl.OnValueChangedListener
{
/*
    public UIView getsettingTable() {
        return settingTable;
    }
*/
    @IBOutlet
    private SettingTableViewController settingTable;



/*
    public Product (NSDictionary<NSString, NSObject> data) {
        this.category = data.get(new NSString("category")).toString();
        this.title = data.get(new NSString("title")).toString();
        this.productID = Long.valueOf(data.get(new NSString("identifier")).toString());
    }
private void loadSetting()
{
    SettingTableViewController settingTable = (SettingTableViewController) this.getChildViewControllers().get(0);

    settingTable.setNbping(String.valueOf(YouSetting.getNbPing()));

    settingTable.setNbwakeup(String.valueOf(YouSetting.getNbPing()));

    settingTable.setPartenaire(String.valueOf(YouSetting.getPartenaire());

    settingTable.setLastwakeup(String.valueOf(YouSetting.getLastWakeUpLove()));
} */

    @Override
    public void viewDidLoad()
    {
        super.viewDidLoad();

        settingTable = (SettingTableViewController) this.getChildViewControllers().get(0);

        settingTable.setNbping(String.valueOf(YouSetting.nbPingLove));
        settingTable.setNbwakeup(String.valueOf(YouSetting.nbWakeUpLove));
        settingTable.setLastwakeup(YouSetting.lastWakeUpLove.toString());
        settingTable.setPartenaire(YouSetting.partenaire);
        settingTable.setTurnoff(YouSetting.turnOff);

        settingTable.getPartenaire().addOnValueChangedListener(this);

    }

    @Override
    public void onValueChanged(UIControl uiControl) {
    }
/*
    @Override
    public void onValueChanged(UIControl uiControl) {

        //date = datePicker.getDate();

    } */


}