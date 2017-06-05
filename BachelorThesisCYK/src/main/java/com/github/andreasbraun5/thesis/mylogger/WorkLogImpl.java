package com.github.andreasbraun5.thesis.mylogger;

import java.io.IOException;
import java.io.Writer;

/**
 * Created by AndreasBraun on 04.06.2017.
 */
final class WorkLogImpl implements WorkLog {

    private final Writer writer;
    private final int MAX_NUMBER_OF_BYTES_TO_WRITE = 1000000;
    private int writtenBytes = 0;

    WorkLogImpl(Writer writer) {
        this.writer = writer;
    }

    @Override
    public void log(String message) {
        if (writtenBytes < MAX_NUMBER_OF_BYTES_TO_WRITE) {
            try {
                this.writer.append(message).append("\n");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            writtenBytes += message.length();
        }
    }

}
