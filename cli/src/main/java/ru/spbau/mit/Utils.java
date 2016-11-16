package ru.spbau.mit;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/** class for some helping function */
public class Utils {
    /**
     * Read from input stream to output stream
     * @param in input stream
     * @param out output stream
     * @throws IOException throws when you cann't read from inputStream or write in outputStream
     */
    public static void fromInputStreamToOutputStream(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int readBytes;
        while ((readBytes = in.read(buffer)) > 0) {
            out.write(buffer, 0, readBytes);
        }
    }

    /**
     * Read from input stream to ArrayList of bytes
     * @param in input stream
     * @param bytes ArrayList of bytes
     * @throws IOException throws when you cann't read from inputStream
     */
    public static void fromInputStreamToBytes(InputStream in, ArrayList<Byte> bytes) throws IOException {
        byte[] buffer = new byte[1024];
        int readBytes;
        while ((readBytes = in.read(buffer)) > 0) {
            for (int i = 0; i < readBytes; ++i) {
                bytes.add(buffer[i]);
            }
        }

    }

    public static InputStream fromStringToInputStream (String s) {
        return new ByteArrayInputStream(s.getBytes());
    }
}
