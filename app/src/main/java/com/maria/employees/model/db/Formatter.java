package com.maria.employees.model.db;

import java.sql.Timestamp;

class Formatter {

    private static final int UPPER_IND = 0;
    private static final int LOWER_IND = 1;

    private static final String NO_BIRTH_DATE_DAY = "31";
    private static final String NO_BIRTH_DATE_MONTH = "12";
    private static final String NO_BIRTH_DATE_YEAR = "9999";
    private static final String DATE_SEPARATOR = "-";
    private static final int DATE_SEPARATOR_IND = 0;
    private static final int SOURSE_DATE_SEPARATOR_IND = 2;
    private static final String TIME = " 00:00:00";
    private static final int DAY_IND_BEGIN = 0;
    private static final int DAY_IND_END = 2;
    private static final int MONTH_IND_BEGIN = 3;
    private static final int MONTH_IND_END = 5;
    private static final int YEAR_IND_BEGIN = 6;
    private static final int YEAR_IND_END = 10;

    String formatName(String value) {
        return value.substring(UPPER_IND, LOWER_IND).toUpperCase() + value.substring(LOWER_IND).toLowerCase();
    }

    Timestamp formatDate(String date) {
        if (date == null) {
            return noBirthDate();
        }

        if (date.length() == 0) {
            return noBirthDate();
        }

        if (date.charAt(SOURSE_DATE_SEPARATOR_IND) == DATE_SEPARATOR.charAt(DATE_SEPARATOR_IND)) {
            return timestampFormatOfDate(date.substring(YEAR_IND_BEGIN, YEAR_IND_END)
                    + DATE_SEPARATOR + date.substring(MONTH_IND_BEGIN, MONTH_IND_END)
                    + DATE_SEPARATOR + date.substring(DAY_IND_BEGIN, DAY_IND_END)
            );
        }

        return timestampFormatOfDate(date);
    }

    private Timestamp noBirthDate() {
        return timestampFormatOfDate(NO_BIRTH_DATE_YEAR +
                DATE_SEPARATOR + NO_BIRTH_DATE_MONTH + DATE_SEPARATOR + NO_BIRTH_DATE_DAY);
    }

    private Timestamp timestampFormatOfDate(String birthDate) {
        return Timestamp.valueOf(birthDate + TIME);
    }
}
