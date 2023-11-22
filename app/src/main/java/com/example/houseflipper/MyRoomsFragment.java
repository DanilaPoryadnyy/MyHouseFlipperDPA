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

import java.util.ArrayList;

public class MyRoomsFragment extends Fragment {
    private RoomsDBHelper dbHelper;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> roomsList;

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

        ListView roomsListView = view.findViewById(R.id.roomsRecyclerView);

        roomsList = getRooms();
        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, roomsList);
        roomsListView.setAdapter(adapter);

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

    private ArrayList<String> getRooms() {
        ArrayList<String> roomsList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("rooms", new String[]{"room_name"}, null, null, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            int roomNameColumnIndex = cursor.getColumnIndex("room_name");

            if (roomNameColumnIndex > -1) {
                cursor.moveToFirst();
                do {
                    String roomName = cursor.getString(roomNameColumnIndex);
                    roomsList.add(roomName);
                } while (cursor.moveToNext());
            }
        }

        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return roomsList;
    }
}
