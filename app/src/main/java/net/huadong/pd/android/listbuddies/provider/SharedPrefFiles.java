package net.huadong.pd.android.listbuddies.provider;


public enum SharedPrefFiles {
    CUSTOMIZE_SETTINGS("CUSTOMIZE_SETTINGS");

    private String text;

    private SharedPrefFiles(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
