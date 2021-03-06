package com.example.flixtyle;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;


public class DiscoveryActivity extends AppCompatActivity {

    //connect to main activity
    private cards cards_data[];

    private ArrayList<String> al;
    private arrayAdapter arrayAdapter;
    private int i;

    private FirebaseAuth mAuth;

    private String currentUId;
    private DatabaseReference userDb;

    ListView listView;
    List<cards> rowItems;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.discovery);

        mAuth=FirebaseAuth.getInstance();
        checkUserSex();

        rowItems = new ArrayList<cards>();


        arrayAdapter = new arrayAdapter(this, R.layout.item, rowItems );

        SwipeFlingAdapterView flingContainer=(SwipeFlingAdapterView) findViewById(R.id.frame);

        flingContainer.setAdapter(arrayAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                rowItems.remove(0);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject
                Toast.makeText(DiscoveryActivity.this, "left",Toast.LENGTH_SHORT ).show();
                FirebaseDatabase.getInstance().getReference()
                        .child("Discovery")
                        .child(FirebaseAuth.getInstance().getUid())
                        .child("discovery")
                        .child("likes")
                        .push()
                        .setValue(true);
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                Toast.makeText(DiscoveryActivity.this, "right",Toast.LENGTH_SHORT ).show();
                FirebaseDatabase.getInstance().getReference()
                        .child("Discovery")
                        .child(FirebaseAuth.getInstance().getUid())
                        .child("discovery")
                        .child("dislikes")
                        .push()
                        .setValue(true);
            }



            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {

                // Ask for more data here
                /*
                al.add("XML ".concat(String.valueOf(i)));
                arrayAdapter.notifyDataSetChanged();
                Log.d("LIST", "notified");
                i++;
                */
            }

            @Override
            public void onScroll(float scrollProgressPercent) {


            }
        });


        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                Toast.makeText(DiscoveryActivity.this, "clicked",Toast.LENGTH_SHORT ).show();
            }
        });

    }
    private String userSex;
    private String oppositeUserSex;


    public void checkUserSex(){
        final FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference femaleDb= FirebaseDatabase.getInstance().getReference().child("Users").child("UID").child("Female");
        femaleDb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.getKey().equals(user.getUid())){
                    userSex="Female";
                    oppositeUserSex ="Male";
                    getClothing();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        DatabaseReference maleDb= FirebaseDatabase.getInstance().getReference().child("Users").child("UID").child("Male");
        maleDb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.getKey().equals(user.getUid())){
                    userSex="Male";
                    oppositeUserSex ="Female";
                    getClothing();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //get clothing depending on sex
    //important
    public void getClothing(){
        DatabaseReference clothingDb= FirebaseDatabase.getInstance().getReference().child("Users").child("UID").child(userSex);
        clothingDb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.exists()){
                    //cards item=new cards(dataSnapshot.getKey(),dataSnapshot.child("name").getValue().toString());
                    //rowItems.add(items)
                    al.add("1");
                    al.add("2");
                    al.add("3");
                    al.add("4");
                    al.add("5");
                    al.add("6");
                    al.add("7");
                    al.add("8");
                    arrayAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void logoutUser(View view){
        mAuth.signOut();
        Intent intent=new Intent(DiscoveryActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
        return;

    }


}


