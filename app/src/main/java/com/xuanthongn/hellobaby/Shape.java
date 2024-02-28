package com.xuanthongn.hellobaby;

public abstract class Shape implements IShape {
    protected String type;
    protected double[] dimensions;

    public Shape(String type, double[] dimensions) {
        this.type = type;
        this.dimensions = dimensions;
    }

    public String getType() {
        return type;
    }

    public double[] getDimensions() {
        return dimensions;
    }
}
