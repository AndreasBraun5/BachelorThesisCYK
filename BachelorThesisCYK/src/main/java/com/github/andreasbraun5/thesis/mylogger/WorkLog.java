package com.github.andreasbraun5.thesis.mylogger;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * Created by AndreasBraun on 04.06.2017.
 * Factory pattern.
 */
public interface WorkLog {

    default void log(String message) {
    }

    //writing to a file
    static WorkLog createFromWriter(Writer writer) {
        if (writer != null) {
            return new WorkLogImpl(writer);
        } else {
            // Anonymous class, that does not overwrite the log method and therefore does nothing with the message.
            return new WorkLog() {};
        }
    }

}
