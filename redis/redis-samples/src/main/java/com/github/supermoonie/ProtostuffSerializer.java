package com.github.supermoonie;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;

/**
 * @author supermoonie
 * @since 2020/4/20
 */
public class ProtostuffSerializer {

    public <T> byte[] serialize(T source, Schema<T> schema) {
        final LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        try {
            return ProtostuffIOUtil.toByteArray(source, schema, buffer);
        } finally {
            buffer.clear();
        }
    }

    public <T> T deserializer(byte[] bytes, Schema<T> schema) {
        T result = schema.newMessage();
        ProtostuffIOUtil.mergeFrom(bytes, result, schema);
        return result;
    }
}
