package com.github.andreasbraun5.thesis.util;

import sun.util.logging.LoggingSupport;

import java.util.Date;
import java.util.logging.LogRecord;

/**
 * Created by AndreasBraun on 03.06.2017.
 */
public class MyFormatter extends java.util.logging.Formatter {

    // format string for printing the log record
    private static final String format = LoggingSupport.getSimpleFormat();
    private final Date dat = new Date();

    @Override
    public synchronized String format(LogRecord record) {
        /*
        dat.setTime(record.getMillis());
        String source;
        if (record.getSourceClassName() != null) {
            source = record.getSourceClassName();
            if (record.getSourceMethodName() != null) {
                source += " " + record.getSourceMethodName();
            }
        } else {
            source = record.getLoggerName();
        }
        String message = formatMessage(record);
        String throwable = "";
        if (record.getThrown() != null) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            pw.println();
            record.getThrown().printStackTrace(pw);
            pw.close();
            throwable = sw.toString();
        }
        return String.format(format,
                dat,
                source,
                record.getLoggerName(),
                record.getLevel().getLocalizedLevelName(),
                message,
                throwable);
      */
        return record.getMessage();
    }
}
