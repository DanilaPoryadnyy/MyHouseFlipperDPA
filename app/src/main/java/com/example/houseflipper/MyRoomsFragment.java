package com.example.houseflipper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
    private RecyclerView roomsRecyclerView;
    private RoomsAdapter adapter;
    private List<Room> roomsList;

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

        roomsRecyclerView = view.findViewById(R.id.roomsRecyclerView);
        roomsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        roomsList = getRooms(); // Получите данные из базы данных
        adapter = new RoomsAdapter(roomsList);
        roomsRecyclerView.setAdapter(adapter);

        clearDatabaseButton.setOnClickListener(v -> {
            dbHelper.clearDatabase();
            updateRoomsList();
        });

        addDatabaseButton.setOnClickListener(v -> {
            String roomName = inputBox.getText().toString().trim();
            if (!roomName.isEmpty()) {
                addRoom(roomName);
                updateRoomsList();
                inputBox.getText().clear(); // Очищаем поле ввода после добавления комнаты
            } else {
                // Действие при пустой строке
                Toast.makeText(requireContext(), "Введите название комнаты", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void updateRoomsList() {
        roomsList.clear();
        roomsList.addAll(getRooms());
        adapter.notifyDataSetChanged();
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
        db.insert("rooms", null, values);
        db.close();
    }

    private List<Room> getRooms() {
        List<Room> roomsList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("rooms", new String[]{"_id", "room_name"}, null, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int roomIdIndex = cursor.getColumnIndex("_id");
                int roomNameIndex = cursor.getColumnIndex("room_name");

                // Check if columns exist in the cursor
                if (roomIdIndex >= 0 && roomNameIndex >= 0) {
                    int roomId = cursor.getInt(roomIdIndex);
                    String roomName = cursor.getString(roomNameIndex);
                    Room room = new Room(roomId, roomName);
                    roomsList.add(room);
                }
            }
            cursor.close();
        }

        db.close();
        return roomsList;
    }

}

