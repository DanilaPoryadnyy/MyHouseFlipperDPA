package com.example.houseflipper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RoomsDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "RoomsDB";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_ROOMS = "rooms";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_ROOM_NAME = "room_name";
    private static final String COLUMN_SURFACE_AREA = "surface_area";
    private static final String COLUMN_ROLLS_COUNT = "rolls_count";
    private static final String COLUMN_ROLLS_COST = "rolls_cost";
    private static final String COLUMN_TILE_COUNT = "tile_count";
    private static final String COLUMN_PAINT_VOLUME = "paint_volume";
    private static final String COLUMN_PAINT_COST = "paint_cost";
    private static final String COLUMN_PRIMER_CONSUMPTION = "primer_consumption";

    public RoomsDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ROOMS_TABLE = "CREATE TABLE " + TABLE_ROOMS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_ROOM_NAME + " TEXT,"
                + COLUMN_SURFACE_AREA + " REAL,"
                + COLUMN_ROLLS_COUNT + " INTEGER,"
                + COLUMN_ROLLS_COST + " REAL,"
                + COLUMN_TILE_COUNT + " INTEGER,"
                + COLUMN_PAINT_VOLUME + " REAL,"
                + COLUMN_PAINT_COST + " REAL,"
                + COLUMN_PRIMER_CONSUMPTION + " REAL"
                + ")";
        db.execSQL(CREATE_ROOMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROOMS);
        onCreate(db);
    }

    public void addRoom(Room room) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ROOM_NAME, room.getRoomName());
        values.put(COLUMN_SURFACE_AREA, room.getSurfaceArea());
        values.put(COLUMN_ROLLS_COUNT, room.getRollsCount());
        values.put(COLUMN_ROLLS_COST, room.getRollsCost());
        values.put(COLUMN_TILE_COUNT, room.getTileCount());
        values.put(COLUMN_PAINT_VOLUME, room.getPaintVolume());
        values.put(COLUMN_PAINT_COST, room.getPaintCost());
        values.put(COLUMN_PRIMER_CONSUMPTION, room.getPrimerConsumption());

        db.insert(TABLE_ROOMS, null, values);
        db.close();
    }

    public Room getRoom(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ROOMS, new String[]{
                        COLUMN_ID,
                        COLUMN_ROOM_NAME,
                        COLUMN_SURFACE_AREA,
                        COLUMN_ROLLS_COUNT,
                        COLUMN_ROLLS_COST,
                        COLUMN_TILE_COUNT,
                        COLUMN_PAINT_VOLUME,
                        COLUMN_PAINT_COST,
                        COLUMN_PRIMER_CONSUMPTION},
                COLUMN_ID + "=?", new String[]{String.valueOf(id)},
                null, null, null, null);

        Room room = null;
        if (cursor != null && cursor.moveToFirst()) {
            // Проверяем индексы столбцов перед использованием
            int roomIdIndex = cursor.getColumnIndex(COLUMN_ID);
            int roomNameIndex = cursor.getColumnIndex(COLUMN_ROOM_NAME);
            int surfaceAreaIndex = cursor.getColumnIndex(COLUMN_SURFACE_AREA);
            int rollsCountIndex = cursor.getColumnIndex(COLUMN_ROLLS_COUNT);
            int rollsCostIndex = cursor.getColumnIndex(COLUMN_ROLLS_COST);
            int tileCountIndex = cursor.getColumnIndex(COLUMN_TILE_COUNT);
            int paintVolumeIndex = cursor.getColumnIndex(COLUMN_PAINT_VOLUME);
            int paintCostIndex = cursor.getColumnIndex(COLUMN_PAINT_COST);
            int primerConsumptionIndex = cursor.getColumnIndex(COLUMN_PRIMER_CONSUMPTION);

            room = new Room(
                    cursor.getString(roomNameIndex),
                    cursor.getDouble(surfaceAreaIndex),
                    cursor.getInt(rollsCountIndex),
                    cursor.getDouble(rollsCostIndex),
                    cursor.getInt(tileCountIndex),
                    cursor.getDouble(paintVolumeIndex),
                    cursor.getDouble(paintCostIndex),
                    cursor.getDouble(primerConsumptionIndex));
        }

        if (cursor != null) {
            cursor.close();
        }
        return room;
    }


    // Add other methods as needed (update, delete, get all rooms, etc.)
}
