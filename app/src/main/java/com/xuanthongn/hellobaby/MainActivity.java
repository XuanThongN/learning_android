package com.xuanthongn.hellobaby;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String fileName = "input.txt";
    Button btnWrite, btnRead;
    TextView tvMaxArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tvMaxArea = findViewById(R.id.tvMaxArea);

        String fileName = "input.txt";
        String fileContent = readFileContent(fileName);
        List<Shape> shapes = parseShapes(fileContent);
        // Sort shapes by perimeter
        shapes = sortShapesByPerimeter(shapes);
        ShapeAdapter shapeAdapter = new ShapeAdapter(shapes);
        recyclerView.setAdapter(shapeAdapter);
        Shape largestShape = findShapeWithLargestArea(shapes);
        tvMaxArea.setText(largestShape.getType());
    }

    public void readFile(View view) {
        try {
            FileInputStream fileInputStream = openFileInput(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();
            String lines;
            while ((lines = bufferedReader.readLine()) != null) {
                stringBuffer.append(lines);
                stringBuffer.append("\n");
            }
            bufferedReader.close();
            inputStreamReader.close();
            Log.d("File Content", stringBuffer.toString());
            Toast.makeText(this, stringBuffer.toString(), Toast.LENGTH_LONG).show();

        } catch (Exception ex) {
            Log.e("Error : ", ex.toString());
            Toast.makeText(this, ex.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void writeFile(View view) {
        try {
            FileOutputStream fileOutputStream = openFileOutput(fileName, MODE_PRIVATE);
            OutputStreamWriter outputStreamwriter = new OutputStreamWriter(fileOutputStream);
            outputStreamwriter.write("#rect1 5 6\n" +
                    "#triangle1 6 8 9\n" +
                    "#circle1 5\n" +
                    "#rect2 6 8\n" +
                    "#circle3 9\n");
            outputStreamwriter.close();
            fileOutputStream.close();
            Toast.makeText(this, "Write file successfully", Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Log.e("Error : ", ex.toString());
            Toast.makeText(this, "Write file failed", Toast.LENGTH_LONG).show();
        }
    }


    private String readFileContent(String fileName) {
        StringBuilder content = new StringBuilder();
        try {
            FileInputStream fileInputStream = openFileInput(fileName);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String lines;
            while ((lines = bufferedReader.readLine()) != null) {
                content.append(lines);
                content.append("\n");
            }
            bufferedReader.close();
            inputStreamReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    private List<Shape> parseShapes(String fileContent) {
        List<Shape> shapes = new ArrayList<>();
        String[] lines = fileContent.split("\n");
        for (String line : lines) {
            String[] parts = line.split(" ");
            if (parts.length >= 2) {
                String shapeType = parts[0];
                double[] dimensions = new double[parts.length - 1];
                for (int i = 1; i < parts.length; i++) {
                    dimensions[i - 1] = Double.parseDouble(parts[i]);
                }
                if (shapeType.contains("rect")) {
                    shapes.add(new Rectangle(shapeType, dimensions));
                } else if (shapeType.contains("triangle")) {
                    shapes.add(new Triangle(shapeType, dimensions));
                } else if (shapeType.contains("circle")) {
                    shapes.add(new Circle(shapeType, dimensions));
                }
            }
        }
        return shapes;
    }

    private List<Shape> sortShapesByPerimeter(List<Shape> input) {
        // Sort shapes by perimeter
        List<Shape> shapes = input;
        Collections.sort(shapes, new Comparator<Shape>() {
            @Override
            public int compare(Shape shape1, Shape shape2) {
                double perimeter1 = shape1.calculatePerimeter();
                double perimeter2 = shape2.calculatePerimeter();
                return Double.compare(perimeter1, perimeter2);
            }
        });
        return shapes;
    }

    private Shape findShapeWithLargestArea(List<Shape> shapes) {
        Shape largestShape = null;
        double largestArea = 0.0;
        for (Shape shape : shapes) {
            double area = shape.calculateArea();
            if (area > largestArea) {
                largestArea = area;
                largestShape = shape;
            }
        }
        return largestShape;
    }
}