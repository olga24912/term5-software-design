import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class Utils {
    public static void fromInputStreamToOutputStream(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int readBytes;
        while ((readBytes = in.read(buffer)) > 0) {
            out.write(buffer, 0, readBytes);
        }
    }

    public static void fromInputStreamToBytes(InputStream in, ArrayList<Byte> bytes) throws IOException {
        byte[] buffer = new byte[1024];
        int readBytes;
        while ((readBytes = in.read(buffer)) > 0) {
            for (int i = 0; i < readBytes; ++i) {
                bytes.add(buffer[i]);
            }
        }

    }
}
