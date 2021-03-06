package com.example.android.labakm.Fragment;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.labakm.Interface.FragmentPauseInterface;
import com.example.android.labakm.R;

public class FragmentValidasiSave extends DialogFragment{
    private FragmentPauseInterface fragmentPauseInterface;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_validasi_save, container, false);
        Bundle bundle = getArguments();
        String title = bundle.getString("title_value");
        TextView textView = view.findViewById(R.id.text_fragment_menu);
        Button yaButton = view.findViewById(R.id.ya_button);
        Button tidakButton = view.findViewById(R.id.tidak_button);
        textView.setText(title);
        yaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentPauseInterface.okButton();
            }
        });

        tidakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentPauseInterface.cancelButton();
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentPauseInterface = (FragmentPauseInterface) context;
    }
}
