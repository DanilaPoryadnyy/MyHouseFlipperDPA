package com.example.houseflipper;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class WallpaperFragment extends Fragment {
    RoomsDBHelper roomsDBHelper;
    Spinner roomSpinner;
    Button saveRoomValueButton;

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

        roomSpinner = view.findViewById(R.id.roomSpinner); // Инициализация Spinner

        loadRoomsData();

        saveRoomValueButton = view.findViewById(R.id.saveRoomValue);
        saveRoomValueButton.setOnClickListener(v -> {
            Room selectedRoom = (Room) roomSpinner.getSelectedItem();

            if (selectedRoom != null) {
                try {
                    TextView rollsCountTextView = view.findViewById(R.id.textRollsCountValue);
                    TextView totalCostTextView = view.findViewById(R.id.textTotalCostValue);

                    // Получаем значения из TextView для рассчитанных результатов
                    String rollsCountString = rollsCountTextView.getText().toString();
                    String totalCostString = totalCostTextView.getText().toString();

                    // Парсим строки в числа
                    double rollsCount = Double.parseDouble(rollsCountString);
                    double totalCost = Double.parseDouble(totalCostString);

                    // Сохраняем значения в выбранном объекте
                    selectedRoom.setRollsCount(rollsCount);
                    selectedRoom.setTotalRollsCost(totalCost);

                    // Обновляем данные в базе данных
                    updateRoomInDatabase(selectedRoom);

                    // Ваш код для обновления интерфейса или отображения обновленной информации
                    // ...

                } catch (NumberFormatException e) {
                    // Обработка ошибки при неверном формате числа
                    // Введите корректные данные
                    e.printStackTrace(); // Логируем исключение для отладки
                }
            } else {
                // Обработка ситуации, если объект не выбран в Spinner
                // Пожалуйста, выберите комнату для сохранения данных
            }
        });

        Button calculateButton = view.findViewById(R.id.calculateButton);
        calculateButton.setOnClickListener(v -> {
            try {
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
            totalCostView.setText(String.format(String.valueOf(totalCost)));
            } catch (NumberFormatException e) {
                // Обработка ошибки при неверном формате числа
                // Введите корректные данные
            }
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
        roomSpinner.setAdapter(adapter);
    }
    public List getRoomsNames(){
        List<Room> rooms = roomsDBHelper.getAllRooms();

        // Извлечение имен комнат из объектов Room
        List<String> roomNames = new ArrayList<>();
        for (Room room : rooms) {
            roomNames.add(room.getRoomName());
        }
        return roomNames;
    }
    // Метод для обновления данных в базе данных для выбранной комнаты
    private void updateRoomInDatabase(Room room) {
        SQLiteDatabase db = roomsDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("rolls_count", room.getRollsCount());
        values.put("total_rolls_cost", room.getTotalRollsCost());

        String selection = "_id=?";
        String[] selectionArgs = {String.valueOf(room.getId())};

        db.update("rooms", values, selection, selectionArgs);
        db.close();
    }

}

