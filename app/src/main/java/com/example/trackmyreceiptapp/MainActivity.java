package com.example.trackmyreceiptapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.Manifest.permission;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextDetector;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private String currentPhotoPath;
    ImageView receiptImageView, receiptImageView2;
    TextView receiptDataTextView;
    Button openCameraBtn, recentPurchasesBtn, statisticsbtn;
    RecyclerView receiptRecyclerView;
    ArrayList<item> list;
    DatabaseReference reference;
    ItemAdapter itemAdapter;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        receiptImageView = findViewById(R.id.receipt_img_id);
        receiptImageView2 = findViewById(R.id.receipt_img_id2);
        receiptDataTextView = findViewById(R.id.text_id);
        openCameraBtn = findViewById(R.id.open_camera_btn);
        recentPurchasesBtn = findViewById(R.id.recent_purchases_button);
        statisticsbtn = findViewById(R.id.statistics_button);

        receiptRecyclerView.findViewById(R.id.receipt_recycler_view);
        receiptRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<item>();

        reference = FirebaseDatabase.getInstance().getReference().child("track_receipt_app");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //set code
                for(DataSnapshot dataSnapshot1: snapshot.getChildren()) {
                    item p  = dataSnapshot1.getValue(item.class);
                    list.add(p);
                }
                itemAdapter = new ItemAdapter(MainActivity.this, list);
                receiptRecyclerView.setAdapter(itemAdapter);
                itemAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // set code
                Toast.makeText(getApplicationContext(), "No data", Toast.LENGTH_SHORT).show();

            }
        });

        //Allow permissions to use camera
        if (checkCallingOrSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 101);
        }

        openCameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                receiptDataTextView.setText("");

                String fileName = "photo";
                File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);


                try {
                    File imageFile = File.createTempFile(fileName, ".jpg", storageDirectory);
                    currentPhotoPath = imageFile.getAbsolutePath();

                    //Take picture intent
                    Uri imageUri = FileProvider.getUriForFile(MainActivity.this, "com.example.trackmyreceiptapp.fileprovider", imageFile);
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    //takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    try {
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    } catch (ActivityNotFoundException e) {
                        // display error state to the user
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            //Bitmap imageBitmap2 = BitmapFactory.decodeFile(currentPhotoPath);
            receiptImageView.setImageBitmap(imageBitmap);
            //receiptImageView2.setImageBitmap(imageBitmap2);

            FirebaseVisionImage firebaseVisionImage = FirebaseVisionImage.fromBitmap(imageBitmap);
            FirebaseVisionTextDetector firebaseVisionTextDetector = FirebaseVision.getInstance().getVisionTextDetector();
            firebaseVisionTextDetector.detectInImage(firebaseVisionImage).addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                @Override
                public void onSuccess(FirebaseVisionText firebaseVisionText) {
                    displayTextFromImage(firebaseVisionText);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }


    private void displayTextFromImage(FirebaseVisionText firebaseVisionText) {
        List<FirebaseVisionText.Block> blockList = firebaseVisionText.getBlocks();
        if(blockList.size()==0) {
            Toast.makeText(this, "No text found in Image", Toast.LENGTH_SHORT).show();
        }
        else {
            for(FirebaseVisionText.Block block : firebaseVisionText.getBlocks()) {
                String text = block.getText();
                receiptDataTextView.setText(text);
            }
        }
    }


}