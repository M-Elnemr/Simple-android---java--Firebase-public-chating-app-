package com.example.hp.firebasemessaging;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinner;
    private EditText editText;
    TextView editTextCode;
    String phoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = findViewById(R.id.spinnerCountries);

        spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, CountryData.countryNames));
        spinner.setOnItemSelectedListener(MainActivity.this);

        editText = findViewById(R.id.editTextPhone);
        editTextCode = findViewById(R.id.editTextCode);

        findViewById(R.id.buttonContinue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//////////////////////////////// for only test...

              //  Intent intent1 = new Intent(MainActivity.this, AppActivity.class);
                //   to clean and clear every thing and start new activity and close each other things
              //  intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

              //  startActivity(intent1);
/////////////////////////////////////////////

                String code = CountryData.countryAreaCodes[spinner.getSelectedItemPosition()];

                String number = editText.getText().toString().trim();

                if (number.isEmpty() || number.length() < 10 ) {
                    editText.setError("Valid number is required");
                    editText.requestFocus();
                    return;
                }else if (number.length() == 10){
                    phoneNumber = "+" + code + number;
                }else if (number.length() == 11){
                    phoneNumber = "+2" + number;
                }else
                    phoneNumber = "+" + number;

                Intent intent = new Intent(MainActivity.this, VerifyPhoneActivity.class);
                intent.putExtra("phonenumber", phoneNumber);
                startActivity(intent);
            }
        });
    }
    // to make the user keep signing in even after closing the application
    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) { //this mean that the user is already logged in
            Intent intent = new Intent(this, AppActivity.class);
            // to clean and clear every thing and start new activity and close each other things
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String code ="+"+ CountryData.countryAreaCodes[position];

        editTextCode.setText(code);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        return;

    }
}
