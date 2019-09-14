package com.example.android.labakm;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.TransitionInflater;
import android.transition.TransitionSet;
import android.view.MenuItem;

import com.example.android.labakm.Fragment.FragmentDetailInquiry;
import com.example.android.labakm.Fragment.FragmentHistoryInquiry;
import com.example.android.labakm.Fragment.FragmentManageInquiry;
import com.example.android.labakm.Fragment.FragmentTrolley;
import com.example.android.labakm.Interface.FragmentAddItemtoTrolleyInterface;
import com.example.android.labakm.Interface.FragmentTrolleyInterface;
import com.example.android.labakm.Interface.InquiryDetailInterface;
import com.example.android.labakm.Interface.InquiryInterface;
import com.example.android.labakm.entity.viewmodel.BarangViewModel;
import com.example.android.labakm.entity.viewmodel.OrderViewModel;

import java.util.List;


public class InquiryActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener,
        InquiryInterface, InquiryDetailInterface, FragmentAddItemtoTrolleyInterface, FragmentTrolleyInterface {

    BottomNavigationView bottomNavigationView;
    private FragmentManager fragmentManager;
    private FragmentDetailInquiry fragmentDetailInquiry;
    private static final long MOVE_DEFAULT_TIME = 1000;
    private static final long FADE_DEFAULT_TIME = 300;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiry);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        showFragmentDefault("add_inquiry");
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.menu_history_inquiry:
                showFragmentDefault("history_inquiry");
                break;
            case R.id.menu_add_inquiry:
                showFragmentDefault("add_inquiry");
                break;
                default:
                    break;
        }
        return false;
    }


    private void showFragmentDefault(String fragmemnt){
        Fragment previousFragment = getFragmentManager().findFragmentById(R.id.fragment_container);
        FragmentTransaction transaction;
        if(fragmemnt.equalsIgnoreCase("add_inquiry")){
            FragmentManageInquiry fragmentManageInquiry = (FragmentManageInquiry) getFragmentManager()
                    .findFragmentByTag("fragment_add_inquiry");
            if(null == fragmentManageInquiry){
                Bundle bundle = new Bundle();
                fragmentManageInquiry = new FragmentManageInquiry();
                fragmentManageInquiry.setArguments(bundle);
            }
            transaction = getFragmentManager().beginTransaction();

            if(null != previousFragment){
                Fade exitFade = new Fade();
                exitFade.setDuration(FADE_DEFAULT_TIME);
                previousFragment.setExitTransition(exitFade);

                TransitionSet enterTransitionSet = new TransitionSet();
                enterTransitionSet.addTransition(TransitionInflater.from(this).inflateTransition(android.R.transition.move));
                enterTransitionSet.setDuration(MOVE_DEFAULT_TIME);
                enterTransitionSet.setStartDelay(FADE_DEFAULT_TIME);
                fragmentManageInquiry.setSharedElementEnterTransition(enterTransitionSet);

                Fade enterFade = new Fade();
                enterFade.setStartDelay(MOVE_DEFAULT_TIME + FADE_DEFAULT_TIME);
                enterFade.setDuration(FADE_DEFAULT_TIME);
                fragmentManageInquiry.setEnterTransition(enterFade);
            }



            transaction.replace(R.id.fragment_container, fragmentManageInquiry, "add_inquiry");
            transaction.addToBackStack(null);
            transaction.commit();
        }else if(fragmemnt.equalsIgnoreCase("history_inquiry")){
            FragmentHistoryInquiry fragmentHistoryInquiry = (FragmentHistoryInquiry) getFragmentManager()
                    .findFragmentByTag("fragment_history_inquiry");
            if(null == fragmentHistoryInquiry){
                Bundle bundle = new Bundle();
                fragmentHistoryInquiry = new FragmentHistoryInquiry();
                bundle.putInt("id_toko", 1);
                fragmentHistoryInquiry.setArguments(bundle);
            }
            transaction = getFragmentManager().beginTransaction();

            if(null != previousFragment){
                Fade exitFade = new Fade();
                exitFade.setDuration(FADE_DEFAULT_TIME);
                previousFragment.setExitTransition(exitFade);

                TransitionSet enterTransitionSet = new TransitionSet();
                enterTransitionSet.addTransition(TransitionInflater.from(this).inflateTransition(android.R.transition.move));
                enterTransitionSet.setDuration(MOVE_DEFAULT_TIME);
                enterTransitionSet.setStartDelay(FADE_DEFAULT_TIME);
                fragmentHistoryInquiry.setSharedElementEnterTransition(enterTransitionSet);

                Fade enterFade = new Fade();
                enterFade.setStartDelay(MOVE_DEFAULT_TIME + FADE_DEFAULT_TIME);
                enterFade.setDuration(FADE_DEFAULT_TIME);
                fragmentHistoryInquiry.setEnterTransition(enterFade);
            }


            transaction.replace(R.id.fragment_container, fragmentHistoryInquiry, "history_inquiry");
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    @Override
    public void checkInquiry(OrderViewModel orderViewModel) {
        fragmentManager = getFragmentManager();
        fragmentDetailInquiry = (FragmentDetailInquiry) fragmentManager
                .findFragmentByTag("fragment_detail_inquiry");
        if(null == fragmentDetailInquiry){
            fragmentDetailInquiry = new FragmentDetailInquiry();
            Bundle bundle = new Bundle();
                bundle.putSerializable("order_selected", orderViewModel);
            fragmentDetailInquiry.setArguments(bundle);
        }
        if(!fragmentDetailInquiry.isVisible()){
            fragmentDetailInquiry.show(fragmentManager, "fragment_detail_inquiry");
        }
    }

    @Override
    public void closeDialog() {
        this.fragmentDetailInquiry.dismiss();
    }

    @Override
    public void addItemToCart(BarangViewModel barang) {
        FragmentManageInquiry manageInquiry = (FragmentManageInquiry) getFragmentManager().findFragmentByTag("add_inquiry");
        manageInquiry.addItemToTrolley(barang);
    }

    @Override
    public void cancel() {
        FragmentManageInquiry manageInquiry = (FragmentManageInquiry) getFragmentManager().findFragmentByTag("add_inquiry");
        manageInquiry.cancelTrolley();
    }

    @Override
    public void closeDialogTrolley(List<BarangViewModel> listItems) {
        FragmentManageInquiry manageInquiry = (FragmentManageInquiry) getFragmentManager().findFragmentByTag("add_inquiry");
        manageInquiry.closeDialogTrolley(listItems);
    }
}
