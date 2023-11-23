package com.example.houseflipper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RoomsAdapter extends RecyclerView.Adapter<RoomsAdapter.RoomViewHolder> {

    private List<Room> roomsList;

    public static class RoomViewHolder extends RecyclerView.ViewHolder {
        public TextView textId, textRoomName, textRollsCount, textTotalRollsCost,
                textTileCount, textPaintCansCount, textPaintCost, textPrimerWeight, textRoomArea;

        public RoomViewHolder(View itemView) {
            super(itemView);
            textId = itemView.findViewById(R.id.textId);
            textRoomName = itemView.findViewById(R.id.textRoomName);
            textRollsCount = itemView.findViewById(R.id.textRollsCount);
            textTotalRollsCost = itemView.findViewById(R.id.textTotalRollsCost);
            textTileCount = itemView.findViewById(R.id.textTileCount);
            textPaintCansCount = itemView.findViewById(R.id.textPaintCansCount);
            textPaintCost = itemView.findViewById(R.id.textPaintCost);
            textPrimerWeight = itemView.findViewById(R.id.textPrimerWeight);
            textRoomArea = itemView.findViewById(R.id.textRoomArea);
        }
    }

    public RoomsAdapter(List<Room> roomsList) {
        this.roomsList = roomsList;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_layout, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        Room room = roomsList.get(position);
        holder.textId.setText(String.valueOf(room.getId()));
        holder.textRoomName.setText(room.getRoomName());
        holder.textRollsCount.setText(String.valueOf(room.getRollsCount()));
        holder.textTotalRollsCost.setText(String.valueOf(room.getTotalRollsCost()));
        holder.textTileCount.setText(String.valueOf(room.getTilesCount()));
        holder.textPaintCansCount.setText(String.valueOf(room.getPaintCansCount()));
        holder.textPaintCost.setText(String.valueOf(room.getPaintCost()));
        holder.textPrimerWeight.setText(String.valueOf(room.getPrimerWeight()));
        holder.textRoomArea.setText(String.valueOf(room.getRoomArea()));
    }

    @Override
    public int getItemCount() {
        return roomsList.size();
    }
}
