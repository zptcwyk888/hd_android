package net.huadong.pd.android.listbuddies.provider;

public enum FragmentTags {
    LIST_BUDDIES("ListBuddiesFragment"),
    CUSTOMIZE("CustomizeFragment");

    private String text;

    private FragmentTags(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
