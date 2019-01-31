package com.brise.tron.alphaverse_reborn.items;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import com.brise.tron.alphaverse_reborn.R;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class Item_Info extends Fragment {
    public static final int CAMERA_REQUEST = 404;
    public static final int GALLERY_REQUEST = 303;
    public static final String contactID = "";
    public static final String photoID = "";
    public static final String nameID = "";
    EditText Iname,Iprice,Icomment;
    TextView itemT;
    Space ligate;
    Button saveI;
    ImageView Iphoto;
    String ownerNumber,notificationId,profilePhoto,Shopname,itemname,itemprice,itemcomment,itemphoto,docxRef,DocxName,Hott,profileName;
    Uri itempic;
    byte[] byteArray, GalleryByte, UploadByte;
    private StorageReference itemImage;
    private CollectionReference itemsLocation;
    UploadTask itemUpload;


    public Item_Info() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View Winifred = inflater.inflate(R.layout.fragment_item__info, container, false);

        Iname = Winifred.findViewById(R.id.Itemname);
        Iprice = Winifred.findViewById(R.id.ItemPrice);
        Icomment = Winifred.findViewById(R.id.ItemComment);
        saveI = Winifred.findViewById(R.id.saveItem);
        Iphoto = Winifred.findViewById(R.id.Itemphoto);
        ligate = Winifred.findViewById(R.id.space);
        itemT = Winifred.findViewById(R.id.itemTitle);

        ownerNumber = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getPhoneNumber();
        itemImage = FirebaseStorage.getInstance().getReference("ITEM IMAGES");
        itemsLocation = FirebaseFirestore.getInstance().collection("Items");

        Bundle shopUpdate = getArguments();
        if (shopUpdate != null)
        {
            Shopname = shopUpdate.getString("INname");
            itemname = shopUpdate.getString("item");
            itemprice = shopUpdate.getString("price");
            itemcomment = shopUpdate.getString("comment");
            itemphoto = shopUpdate.getString("photo");
            docxRef = shopUpdate.getString("initialN");
            Hott = shopUpdate.getString("hotmark");

            if (itemphoto != null)
            {
                Iname.setText(itemname);
                Iprice.setText(itemprice);
                Icomment.setText(itemcomment);
                itemT.setText(String.format("Editing  %s", itemname));
                itemT.setTextSize(16);
                Glide.with(Objects.requireNonNull(getActivity())).load(itemphoto).into(Iphoto);
            }
        }
        SharedPreferences status = Objects.requireNonNull(getActivity()).getSharedPreferences(contactID, Context.MODE_PRIVATE);
        notificationId = status.getString("usernotifId",null);

        SharedPreferences profile = Objects.requireNonNull(getActivity()).getSharedPreferences(photoID, Context.MODE_PRIVATE);
        profilePhoto = profile.getString("userPoto",null);

        SharedPreferences name = Objects.requireNonNull(getActivity()).getSharedPreferences(nameID, Context.MODE_PRIVATE);
        profileName = name.getString("userName",null);

        saveI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ( itemUpload!= null && itemUpload.isInProgress())
                {
                    Toast.makeText(getActivity(), "Upload in progress....", Toast.LENGTH_LONG).show();
                }else {
                    UploadItemPhoto();

                }
            }
        });
        Iphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());

                dialog.setTitle("Item Photo");
                dialog.setMessage("Get Item Image");

                dialog.setPositiveButton("From Gallery", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent photem = new Intent(Intent.ACTION_PICK);
                        photem.setType("image/*");
                        startActivityForResult(photem, GALLERY_REQUEST);

                    }
                });
                dialog.setNegativeButton("From Camera", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent fromCam = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(fromCam, CAMERA_REQUEST);
                    }
                });
                final AlertDialog choose = dialog.create();
                choose.show();
            }
        });

        return Winifred;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST && resultCode == getActivity().RESULT_OK && data != null && data.getData() != null)
        {
          final Uri iphoto = data.getData();
            if (iphoto != null)
            {
                itempic = iphoto;
                Iphoto.setImageURI(itempic);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),itempic);
                    ByteArrayOutputStream myStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50,myStream);
                    GalleryByte = myStream.toByteArray();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
            }

        }
        else if (requestCode == CAMERA_REQUEST && resultCode == getActivity().RESULT_OK)
        {
            Bitmap cameraImage = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
            if (cameraImage != null){
                Iphoto.setImageBitmap(cameraImage);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                cameraImage.compress(Bitmap.CompressFormat.JPEG,50,byteArrayOutputStream);
                byteArray = byteArrayOutputStream.toByteArray();
            }


        }
    }


    private void UploadItemPhoto() {

    if (GalleryByte != null && byteArray == null ) {
        UploadByte = GalleryByte;
        goAhead();
    }
    else if (GalleryByte == null && byteArray != null) {

        UploadByte = byteArray;
        goAhead();
    } else {
            BitmapDrawable drawable = (BitmapDrawable) Iphoto.getDrawable();
            Bitmap bitmap = drawable.getBitmap();
            ByteArrayOutputStream myStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100,myStream);
            UploadByte = myStream.toByteArray();

        goAhead();
    }


    }

    private void goAhead() {


        final StorageReference fileReference = itemImage.child(System.currentTimeMillis() + ".jpg");
        itemUpload  = fileReference.putBytes(UploadByte);
        itemUpload.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getActivity(), "Item photo uploaded successfully", Toast.LENGTH_LONG).show();

                fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String Item_Name,Item_Owner,Item_Price,Item_Comment,Item_Reference,Owner_Contact, HMStatus;
                        if (itemname != null)
                        {
                            if (docxRef != null && docxRef.equals(itemname)) {
                                DocxName = itemname;
                            }else if (docxRef != null && !docxRef.equals(itemname)) {
                                DocxName = docxRef;
                            }
                        }else
                        {

                            if (docxRef == null) {
                                DocxName = Iname.getText().toString().trim();
                                docxRef = Iname.getText().toString().trim();
                            }
                        }
                        if (Hott != null)
                        {
                         HMStatus = Hott;
                        }else
                        {
                            HMStatus = "Negative";
                        }

                        Item_Name = Iname.getText().toString().trim();
                        Item_Price = Iprice.getText().toString().trim();
                        Item_Comment = Icomment.getText().toString().trim();
                        Item_Owner = Shopname;
                        Owner_Contact = ownerNumber;
                        Item_Reference = uri.toString();


                        if (!TextUtils.isEmpty(Item_Name)&&!TextUtils.isEmpty(Item_Price)&&!TextUtils.isEmpty(Item_Comment))
                        {
                                    Item_Adapter item_adapter = new Item_Adapter(Item_Name,Item_Owner,Item_Price,Item_Comment,Item_Reference,Owner_Contact,notificationId,profilePhoto,docxRef,HMStatus,profileName);
                                    itemsLocation.document(DocxName).set(item_adapter);
                                    Iname.setText("");
                                    Iprice.setText("");
                                    Icomment.setText("");
                                    Toast.makeText(getActivity(), "Item Successfully Added", Toast.LENGTH_LONG).show();

                        }
                        else {
                            Toast.makeText(getActivity(), "Please make sure all fields contain requested info", Toast.LENGTH_LONG).show();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                        Toast.makeText(getActivity(), "Uri request failed", Toast.LENGTH_LONG).show();
                    }
                });



            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {


            }
        });
    }


}
