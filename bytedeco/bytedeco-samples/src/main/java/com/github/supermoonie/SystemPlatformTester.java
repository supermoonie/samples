package com.github.supermoonie;

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.systems.macosx.passwd;
import org.bytedeco.systems.macosx.timezone;

/**
 * @author supermoonie
 * @since 2020/7/10
 */
public class SystemPlatformTester {

    public static void main(String[] args) {
        timezone zone = new timezone();
        System.out.println(zone.tz_dsttime());
        System.out.println(zone.tz_minuteswest());

    }
}
