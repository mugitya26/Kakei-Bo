package adv1b.group06.kakeibo;


/**
 * SettingManagerクラスのテストを行う
 * @author 町田
 */
class SettingManagerTest {
    public static void main(String[] args) {
        SettingManager settingManager=new SettingManager();
        settingManager.changeLanToEnglish();
        settingManager.changeLanToJapanese();
    }
}
