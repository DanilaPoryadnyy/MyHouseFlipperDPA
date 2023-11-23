package com.example.houseflipper;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

public class TilesFragment extends Fragment {

    private EditText lengthTileEditText;
    private EditText widthTileEditText;
    private EditText lengthSurfaceEditText;
    private EditText widthSurfaceEditText;
    private TextView textSurfaceArea;
    private TextView textTileCount;

    private Button calculateButton;
    private Spinner spinner;
    RoomsDBHelper roomsDBHelper;

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



        Button saveButtonTiles = view.findViewById(R.id.saveButtonTiles);
        saveButtonTiles.setOnClickListener(v ->{
            try {
                // ... (ваш существующий код)

                // Получение выбранного объекта Room из Spinner
                Room selectedRoom = (Room) spinner.getSelectedItem();

                if (selectedRoom != null) {
                    // Установка значений в выбранный объект Room
                    String surfaceAreaString = textSurfaceArea.getText().toString();
                    String tileCountString = textTileCount.getText().toString();
                    double surfaceArea = Double.parseDouble(surfaceAreaString);
                    int tileCount = Integer.parseInt((tileCountString));
                    selectedRoom.setRoomArea(surfaceArea);
                    selectedRoom.setTilesCount(tileCount);

                    // Обновление данных в базе данных
                    updateRoomInDatabase(selectedRoom);
                } else {
                    // Обработка ситуации, если объект не выбран в Spinner
                    // Пожалуйста, выберите комнату для сохранения данных
                }
            } catch (NumberFormatException e) {
                // Обработка ошибки при неверном формате числа
                // Введите корректные данные
            }
        });


        spinner = view.findViewById(R.id.roomSpinnerTiles); // Инициализация Spinner

        loadRoomsData();

        calculateButton.setOnClickListener(v -> calculateTiles());

        return view;
    }
    private void updateRoomInDatabase(Room room) {
        SQLiteDatabase db = roomsDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("room_area", room.getRoomArea());
        values.put("tiles_count", room.getTilesCount());

        String selection = "_id=?";
        String[] selectionArgs = {String.valueOf(room.getId())};

        db.update("rooms", values, selection, selectionArgs);
        db.close();
    }
    public void loadRoomsData() {
        roomsDBHelper = new RoomsDBHelper(requireContext()); // Инициализация помощника базы данных

        List<Room> rooms = roomsDBHelper.getAllRooms();

//        // Извлечение имен комнат из объектов Room
//        List<String> roomNames = new ArrayList<>();
//        for (Room room : rooms) {
//            roomNames.add(room.getRoomName());
//        }

        // Создание ArrayAdapter и установка его в Spinner
        ArrayAdapter<Room> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, rooms);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
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
        textSurfaceArea.setText(String.format(String.valueOf(surfaceArea)));
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
