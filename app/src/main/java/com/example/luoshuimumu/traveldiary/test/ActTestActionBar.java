package com.example.luoshuimumu.traveldiary.test;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.luoshuimumu.traveldiary.R;

public class ActTestActionBar extends ActionBarActivity {

    public interface IElectricalEquipment {
        void PowerOn();

        void PowerOff();
    }

    public class Switch {
        private IElectricalEquipment equipment;

        public void setEquipment(IElectricalEquipment obj) {
            this.equipment = (IElectricalEquipment) obj;
        }

        public IElectricalEquipment getEquipment() {
            return equipment;
        }

    }


    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_test_action_bar);

        actionBar = this.getSupportActionBar();
        if (null != actionBar) {
//            actionBar.setLogo(R.drawable.ic_launcher);
//            actionBar.setDisplayUseLogoEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        } else {
            Toast.makeText(this, "actionBar==null", Toast.LENGTH_SHORT).show();
        }
        actionBar.addTab(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.isCheckable()) {
            item.setChecked(true);
        }
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, ActNotification.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            default:
                Toast.makeText(this, "toast: " + item.getTitle(), Toast.LENGTH_SHORT).show();
                break;
        }


        return true;
    }
}
