package ferranti.bikerbikus.swingGraphic;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateValidatorUsingLocalDate {
    private DateTimeFormatter dateFormatter;

    public DateValidatorUsingLocalDate(DateTimeFormatter dateFormatter) {
        this.dateFormatter = dateFormatter;
    }

    public boolean isValid(String dateStr) {
        try {
            LocalDate.parse(dateStr, this.dateFormatter);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }
}