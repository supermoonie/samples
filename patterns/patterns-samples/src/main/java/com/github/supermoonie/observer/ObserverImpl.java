package com.github.supermoonie.observer;

/**
 * @author supermoonie
 * @date 2020-03-20
 */
public class ObserverImpl implements Observer {

    @Override
    public void update(String message) {
        System.out.println(message);
    }
}
