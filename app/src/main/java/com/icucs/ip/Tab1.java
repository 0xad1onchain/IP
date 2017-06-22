package com.icucs.ip;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class Tab1 extends Fragment {
    private RecyclerView recylceview;
    private DatabaseReference mDatabase;
    FirebaseRecyclerAdapter<Data,PostviewHolder> firebaseRecyclerAdapter;
    private Query mQuery;
    private String job;
    private FirebaseUser user;
    FirebaseDatabase database;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1, container, false);
        Firebase.setAndroidContext(getContext());

        mDatabase = database.getInstance().getReference();
        Query userfEvents = mDatabase.child("Events");
        Log.e("Before recycle", "");
        recylceview = (RecyclerView) rootView.findViewById(R.id.list);
        Log.e("After recycle", "");

        recylceview.setHasFixedSize(true);
        recylceview.setLayoutManager(new LinearLayoutManager(this.getContext()));
        auth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();



        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(getContext(), TeacherLogin.class));
                }
            }
        };
        return rootView;

    }


    @Override
    public void onStart() {
        super.onStart();


        Query userfEvents = mDatabase.child("Events");
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Data, PostviewHolder>(
                Data.class,
                R.layout.list_cards,
                PostviewHolder.class,
                userfEvents

        ) {
            @Override
            protected void populateViewHolder(PostviewHolder viewHolder, Data model, final int position) {
                viewHolder.setTitle(model.getTitle());
                viewHolder.setdesc(model.getDescription());
                viewHolder.setelig(model.getEligibility());
                viewHolder.setdate(model.getDate());
                viewHolder.setcontact(model.getContact());

                viewHolder.mview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                        builder.setTitle("Register");
                        builder.setMessage("Add your job");


                        final EditText input = new EditText(getContext());
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.MATCH_PARENT);
                        input.setLayoutParams(lp);
                        builder.setView(input);

                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {

                                EventRegisterData obj = new EventRegisterData();
                                job = input.getText().toString();
                                obj.job = job;
                                obj.eventID = firebaseRecyclerAdapter.getRef(position).getKey();
                                obj.uid = user.getUid();
                                obj.eventName = "Dummy";
                                mDatabase.child("Registrations").push().setValue(obj);
//                                , new Firebase.CompletionListener() {
//                                    @Override
//                                    public void onComplete(FirebaseError firebaseError, Firebase firebase) {
//                                        if(firebaseError != null)
//                                        {
//                                            Toast.makeText(getContext(), "Upload Failed, Check Internet Connection", Toast.LENGTH_SHORT).show();
//                                        }
//                                        else
//                                        {
//                                            Toast.makeText(getContext(), "Event Uploaded Successfully!", Toast.LENGTH_SHORT).show();
//                                        }
//
//                                    }
//                                });
                                dialog.dismiss();
                            }
                        });

                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                // Do nothing
                                dialog.dismiss();
                            }
                        });

                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                });
            }
        };
        recylceview.setAdapter(firebaseRecyclerAdapter);


    }
    public static class PostviewHolder extends RecyclerView.ViewHolder{
        View mview;

        public PostviewHolder(View itemView) {
            super(itemView);
            mview=itemView;
        }
        public void setTitle(String title){
            TextView posttitle = (TextView) mview.findViewById(R.id.tit_rec);
            posttitle.setText(title);
        }
        public void setdesc(String desc){
            TextView posttitle = (TextView) mview.findViewById(R.id.desc_rec);
            posttitle.setText(desc);
        }
        public void setelig(String elig){
            TextView posttitle = (TextView) mview.findViewById(R.id.eligibility_rec);
            posttitle.setText("Work: "+elig);
        }
        public void setdate(String date){
            TextView posttitle = (TextView) mview.findViewById(R.id.date_rec);
            posttitle.setText("Date: "+date);
        }
        public void setcontact(String contact){
            TextView posttitle = (TextView) mview.findViewById(R.id.contact_rec);
            posttitle.setText("Contact: "+contact);
        }

    }


}
