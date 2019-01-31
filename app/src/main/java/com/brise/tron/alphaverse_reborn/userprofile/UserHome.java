package com.brise.tron.alphaverse_reborn.userprofile;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
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
import android.widget.Toast;

import com.brise.tron.alphaverse_reborn.R;
import com.brise.tron.alphaverse_reborn.homepage.BaseActivity;
import com.brise.tron.alphaverse_reborn.homepage.FragmentsHolder;
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
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.onesignal.OneSignal;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserHome extends Fragment {
    public static final String user_name = "";
    public static final String notification_id = "";
    public static final String photo_id = "";
    public static final String email_address = "";
    public static final String homeposition = "5";
    CircleImageView profphoto;
    EditText uname, email,resdence;
    Button saveprof;
    byte[] ProfileByte;
    String myimage, date1, date2, mynumber;
    DatabaseReference myid;
    FirebaseFirestore dB;
    CollectionReference users;
    private StorageReference profileImage;
    private StorageTask ProfileUploadTask;
    private Uri profilePhoto;


    public UserHome() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View brise = inflater.inflate(R.layout.fragment_user_home, container, false);
        profphoto = (CircleImageView) brise.findViewById(R.id.Pphoto);
        uname = (EditText) brise.findViewById(R.id.usersname);
        resdence = (EditText) brise.findViewById(R.id.Residence);
        email = (EditText) brise.findViewById(R.id.emailAddr);
        saveprof = (Button) brise.findViewById(R.id.saveprofile);
        dB = FirebaseFirestore.getInstance();

        mynumber = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getPhoneNumber();
        profileImage = FirebaseStorage.getInstance().getReference("PROFILE IMAGES");
        myid = FirebaseDatabase.getInstance().getReference().child("Users").child(mynumber).child("User Profile");
        users = dB.collection("Users").document(mynumber).collection("User Profile");
        findDateTime();

        saveprof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ProfileUploadTask != null && ProfileUploadTask.isInProgress()) {
                    Toast.makeText(getActivity(), "Upload in progress....", Toast.LENGTH_LONG).show();
                } else {
                    UploadProfilePhoto();
                }
            }
        });

        profphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chose = new Intent(Intent.ACTION_PICK);
                chose.setType("image/*");
                startActivityForResult(chose, 101);
            }
        });

        return brise;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == getActivity().RESULT_OK && data != null && data.getData() != null) {
            final Uri resuri = data.getData();
            if (resuri != null) {
                profilePhoto = resuri;
                profphoto.setImageURI(profilePhoto);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), profilePhoto);
                    ByteArrayOutputStream myStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, myStream);
                    ProfileByte = myStream.toByteArray();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
            }


        }
    }

    private void UploadProfilePhoto() {
        OneSignal.idsAvailable(new OneSignal.IdsAvailableHandler() {
            @Override
            public void idsAvailable(final String userId, String registrationId) {

                if (profilePhoto != null) {
                    final StorageReference ImageReference = profileImage.child(System.currentTimeMillis() + ".jpg");
                    ProfileUploadTask = ImageReference.putBytes(ProfileByte).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ImageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    String User_Name, Phone_Number,Email, Residence, SignUp_Date;

                                    SignUp_Date = date2 + " - " + date1;

                                    User_Name = uname.getText().toString().trim();
                                    Phone_Number = mynumber;
                                    Email = email.getText().toString().trim();
                                    Residence = resdence.getText().toString().trim();
                                    myimage = uri.toString();


                                    if (!TextUtils.isEmpty(User_Name) && !TextUtils.isEmpty(Phone_Number) && !TextUtils.isEmpty(Residence)) {

                                        User_Adapter user_adapter = new User_Adapter(User_Name, Phone_Number,Email,Residence, myimage, SignUp_Date,userId);
                                        users.document(User_Name).set(user_adapter);
                                        uname.setText("");
                                        resdence.setText("");
                                        email.setText("");

                                        SharedPreferences profileName = Objects.requireNonNull(getActivity()).getSharedPreferences(user_name, Context.MODE_PRIVATE);
                                        SharedPreferences.Editor nameEditor = profileName.edit();
                                        nameEditor.putString("userName",User_Name);
                                        nameEditor.apply();

                                        SharedPreferences profile_photo = Objects.requireNonNull(getActivity()).getSharedPreferences(photo_id,Context.MODE_PRIVATE);
                                        SharedPreferences.Editor photoEditor = profile_photo.edit();
                                        photoEditor.putString("userPoto",myimage);
                                        photoEditor.apply();

                                        SharedPreferences profile_notificationId = Objects.requireNonNull(getActivity()).getSharedPreferences(notification_id,Context.MODE_PRIVATE);
                                        SharedPreferences.Editor notificationEditor = profile_notificationId.edit();
                                        notificationEditor.putString("usernotifId",userId);
                                        notificationEditor.apply();

                                        SharedPreferences profile_email = Objects.requireNonNull(getActivity()).getSharedPreferences(email_address,Context.MODE_PRIVATE);
                                        SharedPreferences.Editor emailEditor = profile_email.edit();
                                        emailEditor.putString("userEmail",Email);
                                        emailEditor.apply();


                                        Toast.makeText(getActivity(), "Profile Successfully Created", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(getActivity(), "Please make sure all fields contain requested info", Toast.LENGTH_LONG).show();
                                    }

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                        }
                    });
                } else {
                    Toast.makeText(getActivity(), "Please make sure you chose a photo", Toast.LENGTH_LONG).show();
                }
            }
        });

            }

    private void findDateTime() {

        Calendar timestamp = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat timeformat = new SimpleDateFormat("HH:mm:ss");
        date1 = timeformat.format(timestamp.getTime());
        date2 = DateFormat.getDateInstance(DateFormat.FULL).format(timestamp.getTime());
    }
}
