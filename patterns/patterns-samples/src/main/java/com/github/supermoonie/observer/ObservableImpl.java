package com.github.supermoonie.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * 被观察者实现
 *
 * @author supermoonie
 * @date 2020-03-20
 */
public class ObservableImpl implements Observable {

    private List<Observer> observers = new ArrayList<>();

    private String message;

    @Override
    public void register(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void remove(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObserver() {
        observers.forEach(observer -> observer.update(message));
    }

    public void setMessage(String message) {
        this.message = message;
        notifyObserver();
    }

}
