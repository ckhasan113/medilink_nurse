package com.example.nursevendor;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nursevendor.pojo.NurseAddtoCart;
import com.example.nursevendor.pojo.PackageDetails;
import com.squareup.picasso.Picasso;

public class RequestDetails extends AppCompatActivity {

    TextView PackName, PAckPrice, PArchDate, PAtientName, PAtientPHonr, PAtientEmail, DoctorREf;
    ImageView Phone, Email, PrescriptionShow;

    String phone,email;

    private NurseAddtoCart requestDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_details);

        requestDetails = (NurseAddtoCart) getIntent().getSerializableExtra("Details");

        PackName = findViewById(R.id.pack_name);
        PackName.setText(requestDetails.getPackageName());
        PAckPrice = findViewById(R.id.pack_price);
        PAckPrice.setText(requestDetails.getPackagePrice());
        PArchDate = findViewById(R.id.show_date);
        PAtientName = findViewById(R.id.pat_name);
        PAtientPHonr = findViewById(R.id.pat_phone);
        PAtientEmail = findViewById(R.id.pat_email);
        DoctorREf = findViewById(R.id.doc_ref);
        Phone = findViewById(R.id.callImage);
        Email = findViewById(R.id.mailImage);
        PrescriptionShow = findViewById(R.id.prescription_view);

        PArchDate.setText(requestDetails.getNurseTakenDate());
        PAtientName.setText(requestDetails.getPatientName());
        PAtientPHonr.setText(requestDetails.getPhone());
        PAtientEmail.setText(requestDetails.getEmail());
        DoctorREf.setText(requestDetails.getNurseTakenDoctorRef());

        phone = requestDetails.getPhone();
        email = requestDetails.getEmail();

        Uri photoUri = Uri.parse(requestDetails.getNurseTakenPrectionImage());
        Picasso.get().load(photoUri).into(PrescriptionShow);

        if (phone != null) {

            Phone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {



                    String uri = "tel:" + phone.trim();
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse(uri));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    Activity#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for Activity#requestPermissions for more details.
                            return;
                        }
                    }
                    startActivity(intent);
                }
            });
        }

        if (email != null) {

            Email.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    Uri data = Uri.parse("mailto:"+email);
                    intent.setData(data);
                    startActivity(intent);
                }
            });
        }

    }
}
