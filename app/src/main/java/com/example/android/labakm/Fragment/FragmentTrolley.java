package com.example.android.labakm.Fragment;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.android.labakm.Adapter.TrolleyAdapter;
import com.example.android.labakm.Interface.FragmentTrolleyInterface;
import com.example.android.labakm.Interface.TrolleyItemInterface;
import com.example.android.labakm.R;
import com.example.android.labakm.entity.viewmodel.BarangViewModel;

import java.util.ArrayList;
import java.util.List;

public class FragmentTrolley extends DialogFragment implements TrolleyItemInterface{
    private ListView listViewItem;
    private Button buttonClose;
    private FragmentTrolleyInterface fragmentTrolleyInterface;
    private TrolleyAdapter trolleyAdapter;
    private List<BarangViewModel> listItemTrolley;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trolley,container,false);
        listViewItem = view.findViewById(R.id.list_item_trolley);
        buttonClose = view.findViewById(R.id.button_close);
        listItemTrolley = new ArrayList<>();
        Bundle bundle = getArguments();
        if(null != bundle){
            listItemTrolley = (List<BarangViewModel>) bundle.getSerializable("list_item");
        }

        trolleyAdapter = new TrolleyAdapter(listItemTrolley, getContext(), this);
        listViewItem.setAdapter(trolleyAdapter);
        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentTrolleyInterface.closeDialogTrolley(listItemTrolley);
            }
        });


        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentTrolleyInterface = (FragmentTrolleyInterface) context;
    }

    @Override
    public void deleteItem(BarangViewModel barangViewModel) {
        listItemTrolley.remove(barangViewModel);
        trolleyAdapter.notifyDataSetChanged();
    }
}
