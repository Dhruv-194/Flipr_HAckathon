package com.test.fliprhack_trello;

import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.test.fliprhack_trello.CardModelClass;
import com.test.fliprhack_trello.MyAdapter;
import com.test.fliprhack_trello.Utlis;



public class DatabaseACT_CRUD {

    public void insert(final AppCompatActivity a,
                       final DatabaseReference mDatabaseRef,
                       final ProgressBar pb, final CardModelClass cardModelClass) {
        //check if they have passed us a valid scientist. If so then return false.
        if (cardModelClass == null) {
            Utlis.showInfoDialog(a,"VALIDATION FAILED","Scientist is null");
            return;
        } else {
            //otherwise try to push data to firebase database.
            Utlis.showProgressBar(pb);
            //push data to FirebaseDatabase. Table or Child called Scientist will be
            // created.
            mDatabaseRef.child("Scientists").push().setValue(cardModelClass).
                    addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Utlis.hideProgressBar(pb);

                            if(task.isSuccessful()){
                                Utlis.openActivity(a, CardModel.class);
                                Utlis.show(a,"Congrats! INSERT SUCCESSFUL");
                            }else{
                                Utlis.showInfoDialog(a,"UNSUCCESSFUL",task.getException().
                                        getMessage());
                            }
                        }

                    });
        }
    }

    public void select(final AppCompatActivity a, DatabaseReference db,
                       final ProgressBar pb,
                       final RecyclerView rv, MyAdapter adapter) {
        Utlis.showProgressBar(pb);

        db.child("Scientists").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Utlis.DataCache.clear();
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        //Now get Scientist Objects and populate our arraylist.
                        CardModelClass cardModelClass = ds.getValue(CardModelClass.class);
                        cardModelClass.setKey(ds.getKey());
                        Utlis.DataCache.add(cardModelClass);
                    }

                    adapter.notifyDataSetChanged();

                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            Utlis.hideProgressBar(pb);
                            rv.smoothScrollToPosition(Utlis.DataCache.size());
                        }
                    });
                }else {
                    Utlis.show(a,"No more item found");
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("FIREBASE CRUD", databaseError.getMessage());
                Utlis.hideProgressBar(pb);
                Utlis.showInfoDialog(a,"CANCELLED",databaseError.getMessage());
            }
        });
    }

    public void update(final AppCompatActivity a,
                       final DatabaseReference mDatabaseRef,
                       final ProgressBar pb,
                       final CardModelClass oldCard,
                       final CardModelClass newCard) {

        if(oldCard == null){
            Utlis.showInfoDialog(a,"VALIDATION FAILED","Old Scientist is null");
            return;
        }

        Utlis.showProgressBar(pb);
        mDatabaseRef.child("Scientists").child(oldCard.getKey()).setValue(
                newCard)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Utlis.hideProgressBar(pb);

                        if(task.isSuccessful()){
                            Utlis.show(a, oldCard.getName() + " Update Successful.");
                            Utlis.openActivity(a, CardModel.class);
                        }else {
                            Utlis.showInfoDialog(a,"UNSUCCESSFUL",task.getException().
                                    getMessage());
                        }
                    }
                });
    }

    public void delete(final AppCompatActivity a, final DatabaseReference mDatabaseRef,
                       final ProgressBar pb, final CardModelClass selectedCard) {
        Utlis.showProgressBar(pb);
        final String selectedScientistKey = selectedCard.getKey();
        mDatabaseRef.child("Scientists").child(selectedScientistKey).removeValue().
                addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Utlis.hideProgressBar(pb);

                        if(task.isSuccessful()){
                            Utlis.show(a, selectedCard.getName() + " Successfully Deleted.");
                            Utlis.openActivity(a, CardModel.class);
                        }else{
                            Utlis.showInfoDialog(a,"UNSUCCESSFUL",task.getException().getMessage());
                        }
                    }
                });

    }
}
//end
