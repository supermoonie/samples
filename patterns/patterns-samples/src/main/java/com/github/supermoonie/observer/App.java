package com.github.supermoonie.observer;

/**
 * @author supermoonie
 * @date 2020-03-20
 */
public class App {

    public static void main(String[] args) {
        ObservableImpl observable = new ObservableImpl();
        Observer observer = new ObserverImpl();
        observable.register(observer);
        observable.setMessage("hello world!");
    }
}
