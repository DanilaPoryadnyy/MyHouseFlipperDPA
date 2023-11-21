package com.example.houseflipper;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TilesFragment extends Fragment {

    private EditText lengthTileEditText;
    private EditText widthTileEditText;
    private EditText lengthSurfaceEditText;
    private EditText widthSurfaceEditText;
    private TextView textSurfaceArea;
    private TextView textTileCount;

    private Button calculateButton;

    public TilesFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tiles, container, false);

        lengthTileEditText = view.findViewById(R.id.editTextTileLength);
        widthTileEditText = view.findViewById(R.id.editTextTileWidth);
        lengthSurfaceEditText = view.findViewById(R.id.editTextSurfaceLength);
        widthSurfaceEditText = view.findViewById(R.id.editTextSurfaceWidth);
        textSurfaceArea = view.findViewById(R.id.textSurfaceArea);
        textTileCount = view.findViewById(R.id.textTileCount);
        calculateButton = view.findViewById(R.id.calculateButton);

        calculateButton.setOnClickListener(v -> calculateTiles());

        return view;
    }

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            calculateTiles();
        }
    };

    private void calculateTiles() {
        // Получение данных из EditText
        try {
        double lengthTile = parseDouble(lengthTileEditText.getText().toString()) / 100; // см в метры
        double widthTile = parseDouble(widthTileEditText.getText().toString()) / 100; // см в метры
        double lengthSurface = parseDouble(lengthSurfaceEditText.getText().toString()) / 100; // см в метры
        double widthSurface = parseDouble(widthSurfaceEditText.getText().toString()) / 100; // см в метры

        // Расчёт площади поверхности в м2
        double surfaceArea = lengthSurface * widthSurface;

        // Расчёт площади одной плитки в м2
        double tileArea = lengthTile * widthTile;

        // Расчёт количества плиток
        int tileCount = (int) Math.ceil(surfaceArea / tileArea);

        // Обновление TextView
        textSurfaceArea.setText(String.format("%.2f", surfaceArea));
        textTileCount.setText(String.valueOf(tileCount));
        } catch (NumberFormatException e) {
            // Обработка ошибки при неверном формате числа
            // Введите корректные данные
        }
    }

    private double parseDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
}
