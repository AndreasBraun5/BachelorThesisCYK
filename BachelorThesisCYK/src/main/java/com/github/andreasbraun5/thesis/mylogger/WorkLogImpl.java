package com.github.andreasbraun5.thesis.mylogger;

import java.io.IOException;
import java.io.Writer;

/**
 * Created by AndreasBraun on 04.06.2017.
 */
final class WorkLogImpl implements WorkLog {

    private final Writer writer;

    WorkLogImpl(Writer writer) {
        this.writer = writer;
    }

    @Override
    public void log(String message) {
        try {
            this.writer.append(message).append("\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
