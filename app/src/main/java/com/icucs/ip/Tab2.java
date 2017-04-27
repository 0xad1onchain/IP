package com.icucs.ip;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

/**
 * Created by Adi on 11/02/17.
 */
public class Tab2 extends Fragment {
    private RecyclerView recylceview;
    private DatabaseReference mDatabase;
    private Query userfEvent;
    private FirebaseRecyclerAdapter<EventRegisterData,PostviewHolder> firebaseRecyclerAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2, container, false);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        recylceview = (RecyclerView) rootView.findViewById(R.id.list);
        recylceview.setHasFixedSize(true);
        recylceview.setLayoutManager(new LinearLayoutManager(this.getContext()));
        return rootView;
    }


    @Override
    public void onStart() {
        super.onStart();
        Query registrations = mDatabase.child("Registrations");
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<EventRegisterData, PostviewHolder>(
                EventRegisterData.class,
                R.layout.registration_cards,
                PostviewHolder.class,
                registrations
        ) {


            @Override
            protected void populateViewHolder(PostviewHolder viewHolder, EventRegisterData model, int position) {
                viewHolder.setTitle(model.getJob());
                viewHolder.setdesc(model.getEventID());
                viewHolder.setelig(model.getEventName());
                viewHolder.setdate(model.getUid());
                viewHolder.setcontact(model.getJob());
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
            posttitle.setText("Job: "+elig);
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
