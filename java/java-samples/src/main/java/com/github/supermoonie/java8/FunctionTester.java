package com.github.supermoonie.java8;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

/**
 * 方法引用通过方法的名字来指向一个方法。
 * 方法引用使用一对冒号 ::
 *
 * @author supermoonie
 * @date 2020-03-16
 */
public class FunctionTester {

    public static void main(String[] args) {
        Car car = Car.create(Car::new);
        car.setName("bus");
        System.out.println(car);
        List<Car> cars = Arrays.asList(car);
        cars.forEach(Car::repair);
    }

}

class Car {

    private String name;

    public Car(String name) {
        this.name = name;
    }

    public Car() {

    }

    public static Car create(final Supplier<Car> supplier) {
        return supplier.get();
    }

    public void repair() {
        System.out.println("repairing " + name);
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}