package com.xuanthongn.hellobaby;

public class Triangle extends Shape {
    public Triangle(String type, double[] dimensions) {
        super(type, dimensions);
    }

    @Override
    public double calculateArea() {
        double a = dimensions[0];
        double b = dimensions[1];
        double c = dimensions[2];
        double s = (a + b + c) / 2;
        return Math.sqrt(s * (s - a) * (s - b) * (s - c));
    }

    @Override
    public double calculatePerimeter() {
        return dimensions[0] + dimensions[1] + dimensions[2];
    }
}