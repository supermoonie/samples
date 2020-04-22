package com.github.supermoonie;

import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author supermoonie
 * @since 2020/4/20
 */
@Data
public class User implements Serializable {

    public static final Schema<User> SCHEMA = RuntimeSchema.createFrom(User.class);

    private int id;

    private String name;

    private int age;

    private int gender;
}
