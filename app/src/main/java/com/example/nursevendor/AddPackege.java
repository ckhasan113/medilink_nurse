package com.example.nursevendor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.nursevendor.pojo.PackageDetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class AddPackege extends AppCompatActivity implements View.OnClickListener {

    Spinner PAckege;

    private TextView startTimeTV, endTimeTV;

    private FirebaseAuth auth;
    private FirebaseUser user;

    private DatabaseReference rootRef;
    private DatabaseReference userRef;


    EditText PriceET;

    private int mHour, mMinute;

    String packege,start,end,price,id,check;

    private Button addDoctorBtn;
    private LoadingDialog dialog;

    private PackageDetails packageDetails;

    String PackageArray[] ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_packege);

        check = getIntent().getStringExtra("id");
        packageDetails = (PackageDetails) getIntent().getSerializableExtra("packageDetails");

        dialog = new LoadingDialog(AddPackege.this, "Please wait...");

        startTimeTV = findViewById(R.id.start_time);
        endTimeTV = findViewById(R.id.end_time);
        PriceET = findViewById(R.id.price);
        PAckege = findViewById(R.id.isp_pac);
        addDoctorBtn = findViewById(R.id.add_new_doctor_info);
        startTimeTV.setOnClickListener(this);
        endTimeTV.setOnClickListener(this);


        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();


        rootRef = FirebaseDatabase.getInstance().getReference();
        userRef = rootRef.child("Nurse Vendor").child(user.getUid()).child("Package");

        ArrayAdapter spinnerDegreeAdapter = ArrayAdapter.createFromResource(AddPackege.this, R.array.package_array, R.layout.spinner_item_select_model2);

        spinnerDegreeAdapter.setDropDownViewResource(R.layout.spinner_item_select_model2);

        PAckege.setAdapter(spinnerDegreeAdapter);

        PAckege.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                packege = PAckege.getItemAtPosition(i).toString().trim();
                if (packege.equals("Hourly")){
                    startTimeTV.setVisibility(View.GONE);
                    endTimeTV.setVisibility(View.GONE);
                    start = "null";
                    end = "null";
                    return;
                }else if (packege.equals("Daily")){

                    startTimeTV.setVisibility(View.VISIBLE);
                    endTimeTV.setVisibility(View.VISIBLE);

                    return;
                }else if (packege.equals("Weekly")){
                    startTimeTV.setVisibility(View.GONE);
                    endTimeTV.setVisibility(View.GONE);
                    start = "null";
                    end = "null";
                    return;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if (check.equals("new")){
            addDoctorBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {



                    price = PriceET.getText().toString().trim();


                    if (packege.equals("Select")){
                        Toast.makeText(AddPackege.this, "Must Select a package", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (price.isEmpty()){
                        PriceET.setError("Price is required");
                        PriceET.requestFocus();
                        return;
                    }

                    if(start.isEmpty() || end.isEmpty()){
                        Toast.makeText(AddPackege.this, "Time Schedule missing...!", Toast.LENGTH_SHORT).show();
                    }else {
                        dialog.show();
                        id = userRef.push().getKey();
                        addPackageInfo();
                    }
                }
            });
        }
        else if (check.equals("upd")){

            id = packageDetails.getPackageId();

            addDoctorBtn.setText("Update");

            start = packageDetails.getStart();
            end = packageDetails.getEnd();

            startTimeTV.setText(start);
            endTimeTV.setText(end);
            PriceET.setText(packageDetails.getPackagePrice());

            packege = packageDetails.getPackageName();
            selectPackage(packege);
            ArrayAdapter<String> adapterfr = new ArrayAdapter<String>(this,R.layout.spinner_item_select_model2, PackageArray);
            PAckege.setAdapter(adapterfr);

            addDoctorBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    price = PriceET.getText().toString();

                    dialog.show();

                    addPackageInfo();
                }
            });


        }


    }

    private void selectPackage(String packege) {
        if (packege.equals("Hourly")){
            PackageArray = new String[]{"Hourly","Daily","Weekly"};
        }
        else if (packege.equals("Daily")){
            PackageArray = new String[]{"Daily","Weekly","Hourly"};
        }
        else if (packege.equals("Weekly")){
            PackageArray = new String[]{"Weekly","Hourly","Daily"};
        }
    }

    private void addPackageInfo() {


        PackageDetails addpack = new PackageDetails(id,packege,price,start,end);

        userRef.child(id).child("details").setValue(addpack).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    dialog.dismiss();
                    Toast.makeText(AddPackege.this, "Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddPackege.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    dialog.dismiss();
                    Toast.makeText(AddPackege.this, "Failed to add Doctor", Toast.LENGTH_SHORT).show();

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.start_time){
            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            if (hourOfDay>11) {
                                if (hourOfDay==12){
                                    startTimeTV.setText(hourOfDay + ":" + minute+" PM");
                                    start = startTimeTV.getText().toString();
                                }else {
                                    int cou = hourOfDay-12;
                                    startTimeTV.setText(cou + ":" + minute+" PM");
                                    start = startTimeTV.getText().toString();
                                }


                            }else {
                                startTimeTV.setText(hourOfDay + ":" + minute+" AM");
                                start = startTimeTV.getText().toString();
                            }

                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();

        }else if (view.getId()==R.id.end_time){
            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            if (hourOfDay>11) {
                                if (hourOfDay==12){
                                    endTimeTV.setText(hourOfDay + ":" + minute+" PM");
                                    end = endTimeTV.getText().toString();
                                }else {
                                    int cou = hourOfDay-12;
                                    endTimeTV.setText(cou + ":" + minute+" PM");
                                    end = endTimeTV.getText().toString();
                                }


                            }else {
                                endTimeTV.setText(hourOfDay + ":" + minute+" AM");
                                end = endTimeTV.getText().toString();
                            }
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();

        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddPackege.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
