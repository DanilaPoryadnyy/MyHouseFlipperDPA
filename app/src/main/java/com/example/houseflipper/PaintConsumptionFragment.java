package com.example.houseflipper;

import static java.lang.Double.parseDouble;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class PaintConsumptionFragment extends Fragment {

    private EditText paintConsumptionEditText;
    private EditText numberOfLayersEditText;
    private EditText lengthEditText;
    private EditText widthEditText;
    private EditText capacityVolumeEditText;
    private EditText costVolumeEditText;
    private TextView squareInt;
    private TextView volumeOfPaintInt;
    private TextView countOfValuesInt;
    private TextView costPerMInt;
    private TextView totalCost;
    private Button calculateButton;


    public PaintConsumptionFragment() {
        // Пустой конструктор
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Инфлейтим макет для этого фрагмента
        View view = inflater.inflate(R.layout.fragment_paint_consumption, container, false);

        paintConsumptionEditText = view.findViewById(R.id.paintConsumption);
        numberOfLayersEditText = view.findViewById(R.id.numberOfLayers);
        lengthEditText = view.findViewById(R.id.length);
        widthEditText = view.findViewById(R.id.width);
        capacityVolumeEditText = view.findViewById(R.id.capacityVolume);
        costVolumeEditText = view.findViewById(R.id.costVolume);
        numberOfLayersEditText = view.findViewById(R.id.numberOfLayers);

        squareInt = view.findViewById(R.id.squareNum);
        volumeOfPaintInt = view.findViewById(R.id.volumeOfPaintInt);
        countOfValuesInt = view.findViewById(R.id.countOfValuesInt);
        costPerMInt = view.findViewById(R.id.costPerMInt);
        totalCost = view.findViewById(R.id.totalCost);

        calculateButton = view.findViewById(R.id.calculateButtons);

        calculateButton.setOnClickListener(v -> calculatePaints());

        return view;
    }

    private void calculatePaints() {
        try {
        double lengthSurface = Double.parseDouble(lengthEditText.getText().toString()) / 100; // см в метры
        double widthSurface = Double.parseDouble(widthEditText.getText().toString()) / 100; // см в метры
        double paintConsumption = Double.parseDouble(paintConsumptionEditText.getText().toString());
        double capacityVolume = Double.parseDouble(capacityVolumeEditText.getText().toString());
        double costVolume = Double.parseDouble(costVolumeEditText.getText().toString());
        double numberOfLayers = Double.parseDouble(numberOfLayersEditText.getText().toString());

// Расчёт площади поверхности в м2
        double surfaceArea = lengthSurface * widthSurface;

// Расчёт всего объёма затрата краски на все слои
        double paintConsumptionTotal = surfaceArea * paintConsumption * numberOfLayers;

// Расчёт количества банок краски на все слои
        double countOfValues = paintConsumptionTotal / capacityVolume;

// Расчёт стоимости за краску на все слои
        double totalCostForValues = countOfValues * costVolume;

// Расчёт за 1м2 на все слои
        double costPerM = totalCostForValues / surfaceArea;


        // Обновление TextView
        squareInt.setText(String.format("%.2f", surfaceArea));
        volumeOfPaintInt.setText(String.format("%.2f", paintConsumptionTotal));
        countOfValuesInt.setText(String.format("%.2f", countOfValues));
        costPerMInt.setText(String.format("%.2f", costPerM));
        totalCost.setText(String.format("%.2f", totalCostForValues));
        } catch (NumberFormatException e) {
            // Обработка ошибки при неверном формате числа
            // Введите корректные данные
        }

    }
}
