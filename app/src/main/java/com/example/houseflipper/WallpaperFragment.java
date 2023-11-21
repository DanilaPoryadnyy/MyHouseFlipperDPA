package com.example.houseflipper;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class WallpaperFragment extends Fragment {

    public WallpaperFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wallpaper, container, false);

        EditText widthRoll = view.findViewById(R.id.editWidthRoll);
        EditText lengthRoll = view.findViewById(R.id.editLengthRoll);
        EditText costPerRoll = view.findViewById(R.id.editCostRoll);
        EditText totalWidthWalls = view.findViewById(R.id.editWidthWalls);
        EditText heightWalls = view.findViewById(R.id.editHeightWalls);

        Button calculateButton = view.findViewById(R.id.calculateButton);
        calculateButton.setOnClickListener(v -> {
            // Получаем значения из полей ввода
            double width = Double.parseDouble(widthRoll.getText().toString());
            double length = Double.parseDouble(lengthRoll.getText().toString());
            double cost = Double.parseDouble(costPerRoll.getText().toString());
            double totalWidth = Double.parseDouble(totalWidthWalls.getText().toString());
            double height = Double.parseDouble(heightWalls.getText().toString());

            // Выполняем расчёты
            double rollsCount = calculateRollsCount(width, length, totalWidth, height);
            double totalCost = calculateTotalCost(rollsCount, cost);

            // Обновляем TextView с результатами
            TextView rollsCountView = view.findViewById(R.id.textRollsCountValue);
            rollsCountView.setText(String.valueOf(rollsCount));

            TextView totalCostView = view.findViewById(R.id.textTotalCostValue);
            totalCostView.setText(String.format("%.2f руб", totalCost));
        });

        return view;
    }

    private double calculateRollsCount(double width, double length, double totalWidth, double height) {
        double wallArea = totalWidth * height;
        double rollArea = width * length;
        return wallArea / rollArea;
    }

    private double calculateTotalCost(double rollsCount, double costPerRoll) {
        return rollsCount * costPerRoll;
    }
}

