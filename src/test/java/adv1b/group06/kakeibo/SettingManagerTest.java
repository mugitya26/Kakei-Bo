package adv1b.group06.kakeibo;


import adv1b.group06.kakeibo.model.Setting;

import java.util.Set;

/**
 * SettingManagerクラスのテストを行う
 *
 * @author 町田
 */
class SettingManagerTest {
    public static void main(String[] args) {
        Setting setting = SettingManager.getCurrentSetting();
        System.out.printf("before:  %s,%s,%f\n", setting.language, setting.color, setting.fontSize);
        setting = SettingManager.changeLetterSize(25);
        setting = SettingManager.changeColor("Red");
        setting = SettingManager.changeLanguage("Japanese");
        System.out.printf("after:   %s,%s,%f\n", setting.language, setting.color, setting.fontSize);
    }
}
