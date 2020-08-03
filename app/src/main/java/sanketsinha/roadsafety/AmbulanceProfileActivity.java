package sanketsinha.roadsafety;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class AmbulanceProfileActivity extends AppCompatActivity {
    private Button mSubmit;
    private EditText mEmail,mName,mPhone,mPhoneAlter,mAddress,mAmbulanceNumber;
    private DatabaseReference refProfle;
    private String userId;
    private ValueEventListener refProfleListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambulance_profile);
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        refProfle = FirebaseDatabase.getInstance().getReference().child("Users").child("Ambulance").child(userId);
        mEmail = (EditText) findViewById(R.id.email);
        mName = (EditText) findViewById(R.id.name);
        mPhone = (EditText) findViewById(R.id.phone);
        mPhoneAlter = (EditText) findViewById(R.id.phoneAlternative);
        mAddress = (EditText) findViewById(R.id.address);
        mAmbulanceNumber = (EditText) findViewById(R.id.ambulanceNumber);
        mSubmit = (Button) findViewById(R.id.submit);
        getProfile();
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap map = new HashMap();
                map.put("Name", mName.getText().toString());
                map.put("Email", mEmail.getText().toString());
                map.put("Phone", mPhone.getText().toString());
                map.put("Alternative Phone", mPhoneAlter.getText().toString());
                map.put("Address", mAddress.getText().toString());
                map.put("Ambulance Number", mAmbulanceNumber.getText().toString());
                refProfle.setValue(map);
            }
        });
    }
    private void getProfile(){
        refProfleListener = refProfle.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    //Toast.makeText(getApplicationContext(), "Profile Available", Toast.LENGTH_LONG).show();
                    HashMap map = new HashMap();
                    map.putAll((Map) dataSnapshot.getValue());
                    mName.setText(map.get("Name").toString());
                    mEmail.setText(map.get("Email").toString());
                    mPhone.setText(map.get("Phone").toString());
                    mPhoneAlter.setText(map.get("Alternative Phone").toString());
                    mAddress.setText((map.get("Address").toString()));
                    mAmbulanceNumber.setText(map.get("Ambulance Number").toString());
                    mSubmit.setText("Update");
                }
                else
                    Toast.makeText(getApplicationContext(), "profle unavailable", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
