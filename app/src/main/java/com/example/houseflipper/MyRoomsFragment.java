package com.example.houseflipper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyRoomsFragment extends Fragment {
    private RoomsDBHelper dbHelper;
    private Spinner roomSpinner;
    private TextView roomNameVar;
    private TextView rollsCount;
    private TextView rollsCost;
    private TextView tilesCount;
    private TextView paintCansCount;
    private TextView paintCost;
    private TextView primerWeight;
    private TextView roomArea;

    public MyRoomsFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_rooms, container, false);

        dbHelper = new RoomsDBHelper(requireContext());

        EditText inputBox = view.findViewById(R.id.inputBox);
        Button clearDatabaseButton = view.findViewById(R.id.clearDb);
        Button addDatabaseButton = view.findViewById(R.id.addTest);

        roomSpinner = view.findViewById(R.id.roomSpinner);

        roomNameVar = view.findViewById(R.id.roomNameVar);
        rollsCount = view.findViewById(R.id.rollsCount);
        rollsCost = view.findViewById(R.id.rollsCost);
        tilesCount = view.findViewById(R.id.tilesCount);
        paintCansCount = view.findViewById(R.id.paintCansCount);
        paintCost = view.findViewById(R.id.paintCost);
        primerWeight = view.findViewById(R.id.primerWeight);
        roomArea = view.findViewById(R.id.roomArea);

        loadRoomsData();

        clearDatabaseButton.setOnClickListener(v -> {
            dbHelper.clearDatabase();
            loadRoomsData();
        });

        addDatabaseButton.setOnClickListener(v -> {
            String roomName = inputBox.getText().toString().trim();
            if (!roomName.isEmpty()) {
                addRoom(roomName);
                loadRoomsData();
                inputBox.getText().clear(); // Очищаем поле ввода после добавления комнаты
            } else {
                // Действие при пустой строке
                Toast.makeText(requireContext(), "Введите название комнаты", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
    public void loadRoomsData() {
        dbHelper = new RoomsDBHelper(requireContext()); // Инициализация помощника базы данных

        List<Room> rooms = dbHelper.getAllRooms();

        // Создание ArrayAdapter и установка его в Spinner
        ArrayAdapter<Room> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, rooms);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roomSpinner.setAdapter(adapter);

        // Обработка выбора элемента в Spinner, если нужно что-то выполнить при выборе
        roomSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Получение выбранной комнаты
                Room selectedRoom = (Room) parent.getItemAtPosition(position);
                try {
                roomNameVar.setText(String.format("Название: " + selectedRoom.getRoomName()));
                String one = String.valueOf("Количество рулонов: "+ selectedRoom.getRollsCount());
                rollsCount.setText(one);
                String two = String.valueOf("Цена за обои: "+selectedRoom.getTotalRollsCost() + " руб.");
                rollsCost.setText(two);
                tilesCount.setText("Количество плитки: " + selectedRoom.getTilesCount());
                paintCansCount.setText("Количество банок краски:" + selectedRoom.getPaintCansCount());
                String three = String.valueOf("Цена за краску: " +selectedRoom.getPaintCost() + " руб.");
                paintCost.setText(three);
                String four = String.valueOf("Требуется грунтовки: " + selectedRoom.getPrimerWeight() + " кг");
                primerWeight.setText(four);
                String five = String.valueOf("Площадь: " + selectedRoom.getRoomArea() + " м2");
                roomArea.setText(five);
                }
                catch (Exception e){}
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Обработка ситуации, когда ничего не выбрано в Spinner
            }
        });
    }


    @Override
    public void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }

    private void addRoom(String roomName) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("room_name", roomName);
        // Добавление новых полей в базу данных при создании записи о комнате
        values.put("rolls_count", 0);
        values.put("total_rolls_cost", 0.0);
        values.put("tiles_count", 0);
        values.put("paint_cans_count", 0);
        values.put("paint_cost", 0.0);
        values.put("primer_weight", 0.0);
        values.put("room_area", 0.0);
        db.insert("rooms", null, values);
        db.close();
    }

    private List<Room> getRooms() {
        List<Room> roomsList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("rooms", new String[]{"_id", "room_name", "rolls_count", "total_rolls_cost",
                "tiles_count", "paint_cans_count", "paint_cost", "primer_weight", "room_area"}, null, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int roomIdIndex = cursor.getColumnIndex("_id");
                int roomNameIndex = cursor.getColumnIndex("room_name");
                int rollsCountIndex = cursor.getColumnIndex("rolls_count");
                int totalRollsCostIndex = cursor.getColumnIndex("total_rolls_cost");
                int tilesCountIndex = cursor.getColumnIndex("tiles_count");
                int paintCansCountIndex = cursor.getColumnIndex("paint_cans_count");
                int paintCostIndex = cursor.getColumnIndex("paint_cost");
                int primerWeightIndex = cursor.getColumnIndex("primer_weight");
                int roomAreaIndex = cursor.getColumnIndex("room_area");

                // Проверяем наличие столбцов в курсоре
                if (roomIdIndex >= 0 && roomNameIndex >= 0 && rollsCountIndex >= 0 &&
                        totalRollsCostIndex >= 0 && tilesCountIndex >= 0 && paintCansCountIndex >= 0 &&
                        paintCostIndex >= 0 && primerWeightIndex >= 0 && roomAreaIndex >= 0) {
                    int roomId = cursor.getInt(roomIdIndex);
                    String roomName = cursor.getString(roomNameIndex);
                    int rollsCount = cursor.getInt(rollsCountIndex);
                    double totalRollsCost = cursor.getDouble(totalRollsCostIndex);
                    int tilesCount = cursor.getInt(tilesCountIndex);
                    int paintCansCount = cursor.getInt(paintCansCountIndex);
                    double paintCost = cursor.getDouble(paintCostIndex);
                    double primerWeight = cursor.getDouble(primerWeightIndex);
                    double roomArea = cursor.getDouble(roomAreaIndex);

                    Room room = new Room(roomId, roomName, rollsCount, totalRollsCost, tilesCount,
                            paintCansCount, paintCost, primerWeight, roomArea);
                    roomsList.add(room);
                }
            }
            cursor.close();
        }

        db.close();
        return roomsList;
    }

}

