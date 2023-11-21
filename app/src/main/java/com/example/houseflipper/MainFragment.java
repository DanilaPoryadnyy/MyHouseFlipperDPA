package com.example.houseflipper;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MainFragment extends Fragment {

    public MainFragment() {
        // Пустой конструктор
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        Button decorationButton = view.findViewById(R.id.decoration_button);
        decorationButton.setOnClickListener(v -> showPopupMenu(decorationButton));

        Button materialsButton = view.findViewById(R.id.materials_button);
        materialsButton.setOnClickListener(v -> showMaterialsMenu(materialsButton));

        return view;
    }

    private void showMaterialsMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(requireContext(), view);
        popupMenu.getMenuInflater().inflate(R.menu.materials_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();
            // Handle menu item clicks here
            if (itemId == R.id.paint_consumption) {
                loadPaintConsumptionFragment();
                return true;
            } else if (itemId == R.id.primer_consumption) {
                // Do something else for another material option
                return true;
            } else {
                return false;
            }
        });

        popupMenu.show();
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(requireContext(), view);
        popupMenu.getMenuInflater().inflate(R.menu.decoration_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.wallpaper) {
                loadWallpaperFragment();
                return true;
            } else if (itemId == R.id.tiles) {
                loadTilesFragment(); // Загрузка фрагмента "Плитка"
                return true;
            } else {
                return false;
            }
        });

        popupMenu.show();
    }

    private void loadPaintConsumptionFragment() {
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new PaintConsumptionFragment())
                .addToBackStack(null)
                .commit();
    }


    private void loadWallpaperFragment() {
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new WallpaperFragment())
                .addToBackStack(null) // Добавить в Back Stack, если нужно
                .commit();
    }

    private void loadTilesFragment() {
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new TilesFragment())
                .addToBackStack(null) // Добавить в Back Stack, если нужно
                .commit();
    }

}
