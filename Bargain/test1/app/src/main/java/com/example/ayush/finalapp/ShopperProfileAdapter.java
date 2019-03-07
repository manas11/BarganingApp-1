package com.example.ayush.finalapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShopperProfileAdapter extends RecyclerView.Adapter<ShopperProfileAdapter.ShopperProfileViewHolder> {

    private List <ShopperDetails> shopperProfileList;
//    private List <ShopperDetails> shopperProfileList;
    private List <String> s;
    //    static View v1;
//    CardFrag cardFrag;
    AlertDialog.Builder builder2;
    public TextView fname;
//    public TextView city;
    public TextView lname;
//    public TextView pincode;
//    public TextView cat1;
//    public TextView cat2;
//    public TextView cat3;
    public TextView phno;
    FragmentActivity mContext;
    int favbool;
    //   // FragmentActivity mContext;
    Bundle mBundle;

    public static ShopperDetails n;

    public ShopperProfileAdapter(List<ShopperDetails> shopperProfileList, FragmentActivity f, int favbool) {
        this.mContext= f;
        this.shopperProfileList = shopperProfileList;
        this.s= new ArrayList<String>();
        this.favbool=favbool;
    }



    public class ShopperProfileViewHolder extends RecyclerView.ViewHolder {

        public TextView first;
        ImageButton imgbutton;
        public TextView last;
        public TextView phone;



        public ShopperProfileViewHolder(View view) {
            super(view);

            first =(TextView) view.findViewById(R.id.first_name);
            last =(TextView) view.findViewById(R.id.last_name);
            phone=(TextView) view.findViewById(R.id.ph_no);
            imgbutton= (ImageButton) view.findViewById(R.id.card_click);

        }
    }

    public ShopperProfileAdapter.ShopperProfileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_list_layout, parent, false);
        return new ShopperProfileAdapter.ShopperProfileViewHolder(itemView);
    }


    //////////////////////////
    @Override
    public void onBindViewHolder(ShopperProfileAdapter.ShopperProfileViewHolder holder, final int position) {
        n = shopperProfileList.get(position);
        final ShopperDetails nego =shopperProfileList.get(position);

        holder.first.setText(n.getFname());
        holder.last.setText(n.getLname());
        holder.phone.setText(n.getPhno());


        final ShopperDetails shopperProfile=shopperProfileList.get(position);
        holder.imgbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                n = shopperProfileList.get(position);

                //////////////////////
//use alert dialog
////////////////
                builder2 = new AlertDialog.Builder (mContext);
                LayoutInflater inflater = mContext.getLayoutInflater ();
                View v1= inflater.inflate(R.layout.card_frag,null);
                builder2.setView (v1);

                fname=(TextView) v1.findViewById(R.id.neg_profile_firstname);
                lname=(TextView) v1.findViewById(R.id.neg_profile_lastname);
                phno=v1.findViewById(R.id.neg_profile_phno);
//                pincode=v1.findViewById(R.id.neg_profile_pincode);
//                cat1=v1.findViewById(R.id.neg_profile_cat1);
//                cat2=v1.findViewById(R.id.neg_profile_cat2);
//                cat3=v1.findViewById(R.id.neg_profile_cat3);
//                city=(TextView) v1.findViewById(R.id.neg_profile_city);
//                city.setText(n.getCity());
                fname.setText(n.getFname());
                lname.setText(n.getLname());
//                pincode.setText(n.getPincode());
//                cat2.setText(n.getCategory2());
//                cat1.setText(n.getCategory1());
//                cat3.setText(n.getCategory3());
                phno.setText(n.getPhno());
                builder2.setNegativeButton ("Close", new DialogInterface.OnClickListener () {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel ();

                    }
                });



                final ImageView fav = (ImageView) v1.findViewById(R.id.favbuttoncard);
                final  ImageView favdone=(ImageView)v1.findViewById(R.id.favbuttoncarddone);

                if(favbool==1){
                    fav.setVisibility(View.GONE);
                    favdone.setVisibility(View.VISIBLE);
                }

                fav.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fav.setVisibility(View.GONE);
                        favdone.setVisibility(View.VISIBLE);
                        String negokey = s.get(position);
                        Log.v("fsgfht",s.get(position));
                        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                        DatabaseReference favourite;
                        favourite=databaseReference.child("Shopper").child(firebaseUser.getUid());
                        favourite.child("Community").push().setValue(negokey);

//
                    }
                });

                favdone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        favdone.setVisibility(View.GONE);
                        fav.setVisibility(View.VISIBLE);
                        String negokey = s.get(position);
                        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                        DatabaseReference favourite;
                        favourite = databaseReference.child("Shopper").child(firebaseUser.getUid());
                        Query qremove = favourite.child("Community").orderByValue().equalTo(negokey);
                        qremove.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                                    itemSnapshot.getRef().removeValue();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }


                        });
                    }


                });



                // Add action buttons

                //Setting message manually and performing action on button click
                builder2.setCancelable (false);

                //Creating dialog box
                AlertDialog alert = builder2.create ();

                alert.show ();

                ///////////////
//                fragmentJump(n);
//                cardFrag=new CardFrag();
//                mBundle=new Bundle();
//                mBundle.putParcelable("item_selected_key",nego);

                ////////////
//fragment me jayega data

//
//                Bundle args = new Bundle();
//                args.putString("first_name",n.getFirstname());
//                args.putString("last_name",n.getLastname());
//                args.putString("phno",n.getPhone());
//                args.putString("city",n.getCity());
//                args.putString("category1",n.getCategory1());
//                args.putString("category2",n.getCategory2());
//                args.putString("category3",n.getCategory3());
//                args.putString("pincode",n.getPincode());
//                cardFrag.setArguments(args);



                //
//                fragmentTransaction.addToBackStack("searchbar");
//                fragmentTransaction.replace(R.id.content_frame, ldf).commit();
            }
        });

////////////
        n=shopperProfileList.get(position);

//
////////////
//        ValueEventListener(new ValueEventListener() {
//                //////////////
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                            if (dataSnapshot.getValue() == null) {
//
//                                                                }
//                            else {
//
//
//                                  }
//
//                        }
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });


    }//bind end

    ////////
//
//    ////////////////////////////
//    public void switchContent(int id, CardFrag fragment) {
//   //     if (mContext == null)
////            return;
//      //  if (mContext instanceof SearchDisplayFrag) {
//SearchDisplayFrag m= (SearchDisplayFrag)mContext.getSupportFragmentManager().findFragmentById(SearchDisplayFrag);
//            SearchDisplayFrag m = new SearchDisplayFrag();
//            CardFrag frag = fragment;
//            m.switchContent(id, fragment);
////        }
    //   }


//
//
//    private void fragmentJump(ShopperDetails mItemSelected) {
//        cardFrag = new CardFrag();
//        mBundle = new Bundle();
//        mBundle.putString("trem",mItemSelected.getFirstname());
////        mBundle.putParcelable("item_selected_key", (Parcelable) mItemSelected);
//        cardFrag.setArguments(mBundle);
//        switchContent(R.layout.card_frag, cardFrag);
//    }

    public void addItem(ShopperDetails eventsList, String t)
    {
        this.s.add(t);
        this.shopperProfileList.add(eventsList);
    }

    @Override
    public int getItemCount() {

        Log.i("Item-Count",Integer.toString(shopperProfileList.size()));
        return shopperProfileList.size();
    }






}