package com.github.supermoonie.java9;

/**
 * java9 进程API
 *
 * @author supermoonie
 * @since 2020-03-25
 */
public class ProcessHandleTester {

    public static void main(String[] args) {
        ProcessHandle.allProcesses().forEach(ProcessHandleTester::printInfo);
        ProcessHandle current = ProcessHandle.current();
        printInfo(current);
    }

    private static void printInfo(ProcessHandle process) {
        ProcessHandle.Info info = process.info();
        System.out.println(process.pid() + "\t" + info.command().orElse("") + "\t" + info.user().orElse(""));
    }
}
