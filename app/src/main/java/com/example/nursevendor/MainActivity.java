package com.example.nursevendor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nursevendor.Adapter.PackageRecylaerViewAdapter;
import com.example.nursevendor.Adapter.RequestRecylaerViewAdapter;
import com.example.nursevendor.Registration.LoginActivity;
import com.example.nursevendor.pojo.NurseAddtoCart;
import com.example.nursevendor.pojo.PackageDetails;
import com.example.nursevendor.pojo.VendorDetails;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements PackageRecylaerViewAdapter.PackageDetailsListener, View.OnClickListener ,RequestRecylaerViewAdapter.ReqDetailsListener {

    private FirebaseAuth auth;

    private FirebaseUser user;

    private DatabaseReference rootRef;
    private DatabaseReference userRef;
    private DatabaseReference pacRef;
    private DatabaseReference reqRef;

    private LoadingDialog dialog;


    private ImageView profileImageIV;

    private TextView nameTV, areaTV, CountryS, historyTV,EstablishTV;

    private String hospitalID, image, name, registration, area, city, establish, house, history, phone, email, password, status,Dcountry;
    private List<PackageDetails> packageList = new ArrayList<PackageDetails>();
    private List<NurseAddtoCart> reqList = new ArrayList<NurseAddtoCart>();
    private RecyclerView packageRecycler;

    RadioButton Rpac,Rrec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dialog = new LoadingDialog(MainActivity.this,"Data Loading...");
        dialog.show();

        rootRef = FirebaseDatabase.getInstance().getReference();

        profileImageIV = findViewById(R.id.Shospital_image);
        nameTV = findViewById(R.id.Shospital_name);
        areaTV = findViewById(R.id.Shospital_area);
       historyTV = findViewById(R.id.Shospital_history);

        Rpac = findViewById(R.id.radio0);
        Rrec = findViewById(R.id.radio1);

        Rpac.setOnClickListener(this);
        Rrec.setOnClickListener(this);

        auth = FirebaseAuth.getInstance();

        packageRecycler = findViewById(R.id.package_base_recycler);

        user = auth.getCurrentUser();

        userRef = rootRef.child("Nurse Vendor").child(user.getUid());
        setValue();
        pacRef = userRef.child("Package");
        reqRef = userRef.child("Nurse Taken");
        seePackage();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("notify1", "notification", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        FirebaseMessaging.getInstance().subscribeToTopic("nurse");

    }



    private void seePackage() {

        pacRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                packageList.clear();
                //historyTV.setText(String.valueOf(dataSnapshot.getValue()));
                for (DataSnapshot hd: dataSnapshot.getChildren()){
                    PackageDetails doc = hd.child("details").getValue(PackageDetails.class);
                    packageList.add(doc);
                }

                PackageRecylaerViewAdapter recylaerViewAdapter = new PackageRecylaerViewAdapter(MainActivity.this, packageList);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                packageRecycler.setLayoutManager(mLayoutManager);
                packageRecycler.setItemAnimator(new DefaultItemAnimator());
                packageRecycler.setAdapter(recylaerViewAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setValue() {

        userRef.child("Details").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                VendorDetails h = dataSnapshot.getValue(VendorDetails.class);

                image = h.getImage();
                name = h.getName();
                area = h.getArea();
                history = h.getHistory();
                phone = h.getPhone();
                email = h.getEmail();
                password = h.getPassword();
                status = h.getStatus();

                Uri photoUri = Uri.parse(image);
                Picasso.get().load(photoUri).into(profileImageIV);

                nameTV.setText(name);
                areaTV.setText(area);
                historyTV.setText(history);

                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void logout(View view) {
        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("device_token", "");
        rootRef.child("Users").child(user.getUid()).child("Token").updateChildren(tokenMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                auth.signOut();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void addPackage(View view) {
        Intent intent = new Intent(MainActivity.this, AddPackege.class);
        intent.putExtra("id","new");
        startActivity(intent);
        finish();
    }

    @Override
    public void onPackageDetails(PackageDetails packageDetails) {

        Intent intent = new Intent(MainActivity.this, AddPackege.class);
        intent.putExtra("id", "upd");
        intent.putExtra("packageDetails", packageDetails);
        startActivity(intent);
        finish();

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.radio0){
            seePackage();
        }else if (view.getId() == R.id.radio1){
            seeRequest();
        }
    }

    private void seeRequest() {
        reqRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                reqList.clear();
                //historyTV.setText(String.valueOf(dataSnapshot.getValue()));
                for (DataSnapshot hd: dataSnapshot.getChildren()){
                    NurseAddtoCart doc = hd.child("Details").getValue(NurseAddtoCart.class);
                    reqList.add(doc);
                }

                RequestRecylaerViewAdapter recylaerViewAdapter = new RequestRecylaerViewAdapter(MainActivity.this, reqList);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                packageRecycler.setLayoutManager(mLayoutManager);
                packageRecycler.setItemAnimator(new DefaultItemAnimator());
                packageRecycler.setAdapter(recylaerViewAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onReqDetails(NurseAddtoCart requestDetails) {

        Intent intent = new Intent(MainActivity.this, RequestDetails.class);
        intent.putExtra("Details", requestDetails);
        startActivity(intent);

    }
}
