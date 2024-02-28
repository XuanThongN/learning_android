package com.xuanthongn.hellobaby;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class ShapeAdapter extends RecyclerView.Adapter<ShapeAdapter.ShapeViewHolder> {
    private final List<Shape> shapes;

    public ShapeAdapter(List<Shape> shapes) {
        this.shapes = shapes;
    }

    @NonNull
    @Override
    public ShapeAdapter.ShapeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shape, parent, false);
        return new ShapeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShapeAdapter.ShapeViewHolder holder, int position) {
        Shape shape = shapes.get(position);
        holder.tvShapeType.setText(shape.getType());
        holder.tvShapeDimension.setText(Arrays.toString(shape.getDimensions()));
        holder.tvShapeArea.setText("Dien tich: " + String.valueOf(shape.calculateArea()));
        holder.tvShapePerimeter.setText("Chu vi: " + String.valueOf(shape.calculatePerimeter()));
    }

    @Override
    public int getItemCount() {
        return shapes.size();
    }

    public static class ShapeViewHolder extends RecyclerView.ViewHolder {
        TextView tvShapeType, tvShapeDimension, tvShapeArea, tvShapePerimeter;

        public ShapeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvShapeType = itemView.findViewById(R.id.tvShapeType);
            tvShapeDimension = itemView.findViewById(R.id.tvShapeDimensions);
            tvShapeArea = itemView.findViewById(R.id.tvShapeArea);
            tvShapePerimeter = itemView.findViewById(R.id.tvShapePerimeter);
        }
    }
}
