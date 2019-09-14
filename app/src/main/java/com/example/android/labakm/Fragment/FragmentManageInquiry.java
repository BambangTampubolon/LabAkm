package com.example.android.labakm.Fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.android.labakm.Adapter.ProdukAdapter;
import com.example.android.labakm.Adapter.TipeBarangAdapter;
import com.example.android.labakm.Interface.ProdukInquiryInterface;
import com.example.android.labakm.Interface.TipeBarangHolderInterface;
import com.example.android.labakm.Interface.TrolleyItemInterface;
import com.example.android.labakm.R;
import com.example.android.labakm.entity.MySingleton;
import com.example.android.labakm.entity.viewmodel.BarangViewModel;
import com.example.android.labakm.entity.viewmodel.OrderDetailViewModel;
import com.example.android.labakm.entity.viewmodel.OrderViewModel;
import com.example.android.labakm.entity.viewmodel.TipeBarangViewModel;
import com.example.android.labakm.rest.BarangRest;
import com.example.android.labakm.rest.InquiryRest;
import com.example.android.labakm.rest.TipeBarangRest;
import com.example.android.labakm.rest.impl.BarangRestImpl;
import com.example.android.labakm.rest.impl.InquiryRestImpl;
import com.example.android.labakm.rest.impl.TipeBarangRestImpl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentManageInquiry extends Fragment implements ProdukInquiryInterface
        , TrolleyItemInterface, TipeBarangHolderInterface {
    private ListView listViewBarang;
    private ProdukAdapter barangAdapter;
    private Button saveButton, buttonShowTrolley;
    private BarangRest barangRest;
    private InquiryRest inquiryRest;
    private List<BarangViewModel> listBarangDB, listBarangOrder;
    private BarangViewModel itemSelected;
    private FragmentManager fragmentManager;
    private FragmentAddItemToTrolley fragmentAddItemToTrolley;
    private FragmentTrolley fragmentTrolley;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager layoutManager;
    private TipeBarangRest tipeBarangRest;
    private List<TipeBarangViewModel> listTipeBarang;
    final private String FCM_API = "https://fcm.googleapis.com/fcm/send";
    final private String serverKey = "key=" + "AAAAsDHkEeQ:APA91bH697nLvUSRrTXlC95hT1RP70OU2kJz9Kr2xPc6v_8Awcj7jfA8kynSPaBZtxEZey8jFqdjqvbQQF1DHvDh5Dxul7ne8rQJ_NhwVKc6EDlEwOih4zsfpToOo4aulyrIp2MkjNhW";
    final private String contentType = "application/json";
    String NOTIFICATION_TITLE;
    String NOTIFICATION_MESSAGE;
    String TOPIC;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage_inquiry, container, false);
        listViewBarang = view.findViewById(R.id.barang_list_view);
        saveButton = view.findViewById(R.id.save_button);
        buttonShowTrolley = view.findViewById(R.id.button_trolley);
        recyclerView = view.findViewById(R.id.recycler_tipe_barang);
        tipeBarangRest = new TipeBarangRestImpl();

        barangRest = new BarangRestImpl();
        inquiryRest = new InquiryRestImpl();
        listBarangOrder = new ArrayList<>();
        listBarangDB = barangRest.getAllBarang();
        barangAdapter = new ProdukAdapter(listBarangDB, getContext(), this);
        listViewBarang.setAdapter(barangAdapter);

        listTipeBarang = tipeBarangRest.getAllTipeBarang();
        mAdapter = new TipeBarangAdapter(listTipeBarang, getContext(), this);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

        buttonShowTrolley.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager = getFragmentManager();
                fragmentTrolley = (FragmentTrolley) fragmentManager.findFragmentByTag("fragment_trolley");
                if(null == fragmentTrolley){
                    fragmentTrolley = new FragmentTrolley();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("list_item", (Serializable) listBarangOrder);
                    fragmentTrolley.setArguments(bundle);
                }
                if(!fragmentTrolley.isVisible()){
                    fragmentTrolley.show(fragmentManager, "fragment_trolley");
                }
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int idInquiry = inquiryRest.saveInquiry(setOrderJson(listBarangOrder));
                if(idInquiry > 0){
                    TOPIC = "/cR6rkMefLO4:APA91bGWT16WZDD_aKTV6fPYxucdwsXQXjL0VXytq6rL8r2REJVVKIOZe71vcgvKElsVxh4cZOrQW-e8tJDyhIrvGf2sNJ8VqRhCcTXXTw2D8z6QoTwDUgY5Slm3DtJkLESHCGY4yN0g";
                    NOTIFICATION_TITLE = "amrunn sempakkkk";
                    NOTIFICATION_MESSAGE = "KOEEEEEEE";

                    JSONObject notification = new JSONObject();
                    JSONObject notifBody = new JSONObject();
                    try {
                        notifBody.put("title", NOTIFICATION_TITLE);
                        notifBody.put("message", NOTIFICATION_MESSAGE);

                        notification.put("to", TOPIC);
                        notification.put("data", notifBody);
                        JSONArray registrationId = new JSONArray();
                        registrationId.put("cR6rkMefLO4:APA91bGWT16WZDD_aKTV6fPYxucdwsXQXjL0VXytq6rL8r2REJVVKIOZe71vcgvKElsVxh4cZOrQW-e8tJDyhIrvGf2sNJ8VqRhCcTXXTw2D8z6QoTwDUgY5Slm3DtJkLESHCGY4yN0g");
                        notification.put("token", registrationId);
                        Log.i("cekjson", "onClick: " + notification.toString());
                    } catch (JSONException e) {
                        Log.i("CEKERROR", "onClick: ");
                    }

                    sendNotification(notification);
                }
            }
        });
        return view;
    }

    private OrderViewModel setOrderJson(List<BarangViewModel> orderViewModel){
        OrderViewModel orderVM = new OrderViewModel();
        orderVM.setId(0);
        orderVM.setCreatedby("bengbeng");
        orderVM.setCreatedbyname("bengbeng");
        orderVM.setCreatedterminal("bengbeng");
        orderVM.setId_toko(1);
        orderVM.setNama_toko("bengbeng's");
        orderVM.setStatus(0);
        orderVM.setTotal_harga(0);
        orderVM.setItems(setOrderDetailJSON(orderViewModel));
        return orderVM;
    }

    private List<OrderDetailViewModel> setOrderDetailJSON(List<BarangViewModel> barangsViewModel){
        List<OrderDetailViewModel> listOrderDetailVM = new ArrayList<>();
        for (BarangViewModel barangVM : barangsViewModel){
            OrderDetailViewModel orderDetailViewModel = new OrderDetailViewModel();
            orderDetailViewModel.setId_barang(barangVM.getId());
            orderDetailViewModel.setNama_barang(barangVM.getNama());
            orderDetailViewModel.setId(0);
            orderDetailViewModel.setId_order(0);
            orderDetailViewModel.setCreatedby("bengbeng");
            orderDetailViewModel.setCreatedbyname("bengbeng");
            orderDetailViewModel.setCreatedterminal("bengbeng");
            orderDetailViewModel.setTotal_barang(barangVM.getJumlah());
            listOrderDetailVM.add(orderDetailViewModel);
        }
        return listOrderDetailVM;
    }


    private void showIntialFragment(){
        fragmentManager = getFragmentManager();
        fragmentAddItemToTrolley = (FragmentAddItemToTrolley) fragmentManager.findFragmentByTag("add_item_totrolley");
        if(null == fragmentAddItemToTrolley){
            fragmentAddItemToTrolley = new FragmentAddItemToTrolley();
            Bundle bundle = new Bundle();
            bundle.putSerializable("item_selected", itemSelected);
            fragmentAddItemToTrolley.setArguments(bundle);
        }
        if(!fragmentAddItemToTrolley.isVisible()){
            fragmentAddItemToTrolley.show(fragmentManager, "add_item_totrolley");
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    public void addItemToTrolley(BarangViewModel barangViewModel){
        listBarangOrder.add(barangViewModel);
        this.fragmentAddItemToTrolley.dismiss();
    }

    public void cancelTrolley(){
        this.fragmentAddItemToTrolley.dismiss();
    }

    public void closeDialogTrolley(List<BarangViewModel> listItems){
        listBarangOrder = listItems;
        fragmentTrolley.dismiss();
    }

    @Override
    public void saveBarangtoProduk(BarangViewModel barangViewModel) {
        itemSelected = barangViewModel;
        showIntialFragment();
    }

    @Override
    public void searchTipeBarangById(TipeBarangViewModel tipeBarangViewModel) {
        mAdapter.notifyDataSetChanged();
        List<BarangViewModel> barangDB = barangRest.getAllBarang();
        List<BarangViewModel> listBarangByTipe = new ArrayList<>();
        for(BarangViewModel a: barangDB){
            if(tipeBarangViewModel.getId() == a.getTipe_barang_id()){
                listBarangByTipe.add(a);
            }
        }
        listBarangDB.clear();
        listBarangDB.addAll(listBarangByTipe);
        barangAdapter.notifyDataSetChanged();

    }

    @Override
    public void deleteItem(BarangViewModel barangViewModel) {
        listBarangOrder.remove(barangViewModel);
    }

    private void sendNotification(JSONObject notification){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(FCM_API, notification,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("cekresponse", "onResponse: " + response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Request error", Toast.LENGTH_LONG).show();
                        Log.i("cekerrorcall", "onErrorResponse: " + "notaworka");
                    }
                })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("Authorization", serverKey);
                params.put("Content-Type", contentType);
                return params;
            }
        };
        MySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
    }
}
