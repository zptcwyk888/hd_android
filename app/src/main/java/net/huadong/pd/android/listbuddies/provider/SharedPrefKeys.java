package net.huadong.pd.android.listbuddies.provider;

public enum SharedPrefKeys {
    GAP_PROGRESS("GAP_PROGRESS"),
    SPEED_PROGRESS("SPEED_PROGRESS"),
    DIV_HEIGHT_PROGRESS("DIV_HEIGHT_PROGRESS"),
    FILL_GAP_POSITION("FILL_GAP_POSITION"),
    MANUAL_SCROLL_POSITION("MANUAL_SCROLL_POSITION"),
    AUTO_SCROLL_POSITION("AUTO_SCROLL_POSITION"),
    DIVIDERS_POSITION("DIVIDERS_POSITION");

    private String text;

    private SharedPrefKeys(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
