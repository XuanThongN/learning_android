package com.xuanthongn.hellobaby;

public class Rectangle extends Shape {
    public Rectangle(String type, double[] dimensions) {
        super(type, dimensions);
    }

    @Override
    public double calculateArea() {
        return dimensions[0] * dimensions[1];
    }

    @Override
    public double calculatePerimeter() {
        return 2 * (dimensions[0] + dimensions[1]);
    }
}

