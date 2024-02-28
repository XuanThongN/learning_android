package com.xuanthongn.hellobaby;

public class Circle extends Shape {
    public Circle(String type, double[] dimensions) {
        super(type, dimensions);
    }

    @Override
    public double calculateArea() {
        return Math.PI * dimensions[0] * dimensions[0];
    }

    @Override
    public double calculatePerimeter() {
        return 2 * Math.PI * dimensions[0];
    }
}
