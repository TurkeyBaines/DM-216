package org.dm;

import io.netty.buffer.ByteBuf;
import io.netty.util.ByteProcessor;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author Jire
 */
public final class ByteBufUtil {

    private ByteBufUtil() {
        // Private constructor to prevent instantiation
    }

    private static final byte STRING_DELIMITER_317 = (byte) 10;

    private static final ByteProcessor stringByteProcessor317 = new ByteProcessor.IndexOfProcessor(STRING_DELIMITER_317);

    public static byte[] readStringArray(ByteBuf buffer) {
        int start = buffer.readerIndex();

        int end = buffer.forEachByte(stringByteProcessor317);
        if (end == -1) {
            throw new IllegalArgumentException("Unterminated string");
        }

        int length = end - start;
        byte[] bytes = new byte[length];
        buffer.readBytes(bytes);

        // Advance the reader index past the delimiter
        buffer.readerIndex(end + 1);

        return bytes;
    }

    public static String readString(ByteBuf buffer) {
        return readString(buffer, StandardCharsets.UTF_8);
    }

    public static String readString(ByteBuf buffer, Charset charset) {
        int start = buffer.readerIndex();

        int end = buffer.forEachByte(stringByteProcessor317);
        if (end == -1) {
            throw new IllegalArgumentException("Unterminated string");
        }

        int length = end - start;
        String string = buffer.toString(start, length, charset).intern();

        // Advance the reader index past the delimiter
        buffer.readerIndex(end + 1);

        return string;
    }

}