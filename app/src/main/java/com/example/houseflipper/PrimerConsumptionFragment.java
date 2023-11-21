package com.example.houseflipper;

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


public class PrimerConsumptionFragment extends Fragment {

    public PrimerConsumptionFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_primer_consumption, container, false);

        EditText primerConsumption = view.findViewById(R.id.editTextPrimerConsumption);
        EditText textLayerCount = view.findViewById(R.id.editTextLayerCount);
        EditText textLayerThickness = view.findViewById(R.id.editTextLayerThickness);
        EditText textLength = view.findViewById(R.id.editTextLength);
        EditText TextWidth = view.findViewById(R.id.editTextWidth);

        TextView squarePrimerNum = view.findViewById(R.id.squarePrimerNum);
        TextView countPrimerMNum = view.findViewById(R.id.countPrimerMNum);
        TextView countPerM = view.findViewById(R.id.countPerM);

        Button calculateButton = view.findViewById(R.id.calculateButtone);

        calculateButton.setOnClickListener(v -> {
            try {
            // Получение введенных значений
            double consumption = Double.parseDouble(primerConsumption.getText().toString());
            int layerCount = Integer.parseInt(textLayerCount.getText().toString());
            double layerThickness = Double.parseDouble(textLayerThickness.getText().toString());
            double length = Double.parseDouble(textLength.getText().toString());
            double width = Double.parseDouble(TextWidth.getText().toString());

            // Расчет площади
            double square = length * width;

            // Общий расход грунтовки
            double totalPrimerConsumption = square * consumption * layerCount * layerThickness;

            double primerPerSquareMeter = totalPrimerConsumption / square;

            // Отображение результатов
            squarePrimerNum.setText(String.format("Площадь: %.2f м²", square));
            countPrimerMNum.setText(String.format("Общий расход грунтовки: %.2f кг", totalPrimerConsumption));
            countPerM.setText(String.format("Расход грунтовки на м²: %.2f кг/м²", primerPerSquareMeter));
            } catch (NumberFormatException e) {
                // Обработка ошибки при неверном формате числа
                // Введите корректные данные
            }
        });

        return view;
    }

}
