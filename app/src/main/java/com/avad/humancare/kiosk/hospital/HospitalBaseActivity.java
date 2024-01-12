package com.avad.humancare.kiosk.hospital;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.avad.humancare.kiosk.R;
import com.avad.humancare.kiosk.train.TrainMainActivity;

public class HospitalBaseActivity extends FragmentActivity implements View.OnClickListener {
    public interface NavigateButtonListener{
        public void OnBackNavigated();
        public void OnNextNavigated();
    }
    public NavigateButtonListener _navigateButtonListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;

        switch (view.getId()) {
            case R.id.btn_back_main :
                intent = new Intent(this, HospitalMainMenuActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
                break;

        }
    }
}

