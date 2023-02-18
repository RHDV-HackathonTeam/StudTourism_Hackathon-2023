package ru.denfad.studturism.activities;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.button.MaterialButtonToggleGroup;

import ru.denfad.studturism.fragments.BookFragment;
import ru.denfad.studturism.fragments.HostelFragment;
import ru.denfad.studturism.fragments.PricesFragment;
import ru.denfad.studturism.R;

public class HostelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hostel);

        int hostelId = getIntent().getIntExtra("id", 1);

        MaterialButtonToggleGroup group = findViewById(R.id.buttons_group);
        group.check(R.id.hostel);
        group.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if (isChecked) {
                    switch (checkedId) {
                        case R.id.hostel:
                            loadFragment(HostelFragment.newInstance(hostelId));
                            break;
                        case R.id.prices:
                            loadFragment(PricesFragment.newInstance(hostelId));
                            break;
                        case R.id.book:
                            loadFragment(BookFragment.newInstance(hostelId));
                            break;
                    }
                }
            }

        });

        loadFragment(HostelFragment.newInstance(hostelId));

        ImageButton back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_NONE);
        ft.replace(R.id.hotel_frame, fragment);
        ft.commit();
    }
}