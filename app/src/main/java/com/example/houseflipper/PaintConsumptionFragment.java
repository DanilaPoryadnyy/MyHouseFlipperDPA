package com.example.houseflipper;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class PaintConsumptionFragment extends Fragment {

    public PaintConsumptionFragment() {
        // Пустой конструктор
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Инфлейтим макет для этого фрагмента
        View view = inflater.inflate(R.layout.fragment_paint_consumption, container, false);
        return view;
    }
}
