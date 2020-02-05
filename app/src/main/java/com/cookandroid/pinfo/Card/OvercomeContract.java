package com.cookandroid.pinfo.Card;

import android.provider.BaseColumns;

public final class OvercomeContract {
    private OvercomeContract() {

    }

    public static class OvercomeEntry implements BaseColumns {
        public static final String TABLE_NAME = "overcome";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_CONTENTS = "contents";
    }
}
