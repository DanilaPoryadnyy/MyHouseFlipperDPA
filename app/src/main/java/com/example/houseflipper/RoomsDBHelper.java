package com.example.houseflipper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class RoomsDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "rooms.db";
    private static final int DATABASE_VERSION = 1;

    public RoomsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Создание таблицы для хранения информации о комнатах
        db.execSQL("CREATE TABLE rooms (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "room_name TEXT," +
                "rolls_count INTEGER," +
                "total_rolls_cost REAL," +
                "tiles_count INTEGER," +
                "paint_cans_count INTEGER," +
                "paint_cost REAL," +
                "primer_weight REAL," +
                "room_area REAL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Обновление базы данных при изменении схемы
        db.execSQL("DROP TABLE IF EXISTS rooms");
        onCreate(db);
    }
    public void clearDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS rooms");
        onCreate(db);
    }

    public List<Room> getAllRooms() {
        List<Room> roomList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                "_id",
                "room_name",
                "rolls_count",
                "total_rolls_cost",
                "tiles_count",
                "paint_cans_count",
                "paint_cost",
                "primer_weight",
                "room_area"
        };

        Cursor cursor = db.query(
                "rooms",
                projection,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor != null) {
            int idIndex = cursor.getColumnIndex("_id");
            int nameIndex = cursor.getColumnIndex("room_name");
            int rollsCountIndex = cursor.getColumnIndex("rolls_count");
            int totalRollsCostIndex = cursor.getColumnIndex("total_rolls_cost");
            int tilesCountIndex = cursor.getColumnIndex("tiles_count");
            int paintCansCountIndex = cursor.getColumnIndex("paint_cans_count");
            int paintCostIndex = cursor.getColumnIndex("paint_cost");
            int primerWeightIndex = cursor.getColumnIndex("primer_weight");
            int roomAreaIndex = cursor.getColumnIndex("room_area");

            while (cursor.moveToNext()) {
                int roomId = cursor.getInt(idIndex);
                String roomName = cursor.getString(nameIndex);
                int rollsCount = cursor.getInt(rollsCountIndex);
                int totalRollsCost = cursor.getInt(totalRollsCostIndex);
                int tilesCount = cursor.getInt(tilesCountIndex);
                int paintCansCount = cursor.getInt(paintCansCountIndex);
                float paintCost = cursor.getFloat(paintCostIndex);
                float primerWeight = cursor.getFloat(primerWeightIndex);
                float roomArea = cursor.getFloat(roomAreaIndex);

                Room room = new Room(roomId, roomName, rollsCount, totalRollsCost, tilesCount, paintCansCount,
                        paintCost, primerWeight, roomArea);
                roomList.add(room);
            }

            cursor.close();
        }

        return roomList;
    }

}
