package com.github.supermoonie.observer;

/**
 * 被观察者
 *
 * @author supermoonie
 * @date 2020-03-20
 */
public interface Observable {

    void register(Observer observer);

    void remove(Observer observer);

    void notifyObserver();
}
