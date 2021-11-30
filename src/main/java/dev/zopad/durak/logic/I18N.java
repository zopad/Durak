package dev.zopad.durak.logic;

import java.util.ResourceBundle;

public class I18N {
    private I18N() {
    }

    private static ResourceBundle bundle;

    public static String getMessage(String key) {
        if (bundle == null) {
            bundle = ResourceBundle.getBundle("enums");
        }
        return bundle.getString(key);
    }

    public static String getMessage(Enum<?> enumVal) {
        return getMessage(enumVal.toString());
    }
}
