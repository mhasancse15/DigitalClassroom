package com.digitalclassroom15.digitalclassroom.adminPanel;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalclassroom15.digitalclassroom.R;
import com.digitalclassroom15.digitalclassroom.model.Upload;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UploadActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private Button mButtonChooseImage;
    private Button mButtonUpload;
    private EditText mEditTextFileName,sectionName,subjectName,lastDate;
    private ImageView mImageView;
    private ProgressBar mProgressBar;
    private Spinner spinner;
 private    String selectedItemText;

    private Uri mImageUri;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    private StorageTask mUploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        mButtonChooseImage = (Button) findViewById(R.id.button_choose_image);
        mButtonUpload = (Button) findViewById(R.id.button_upload);

        mEditTextFileName = (EditText) findViewById(R.id.edit_text_file_name);
        sectionName = (EditText) findViewById(R.id.sectionET);

        subjectName = (EditText) findViewById(R.id.subjectSelectTV);
        lastDate = (EditText) findViewById(R.id.lastdateTV);

        getSupportActionBar().setTitle("Upload Document");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Medium Spinner Method
        initClassSpinner();

        mImageView = (ImageView) findViewById(R.id.image_view);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);

        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        mButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(UploadActivity.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                } else {
                    uploadFile();
                }
            }
        });

    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            Picasso.with(this).load(mImageUri).into(mImageView);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        if (mImageUri != null) {
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));

            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setProgress(0);
                                }
                            }, 500);

                            Toast.makeText(UploadActivity.this, "Upload successful", Toast.LENGTH_LONG).show();
                            //  Upload upload = new Upload(mEditTextFileName.getText().toString().trim(), taskSnapshot.getDownloadUrl().toString());
                            //  Upload upload=new Upload(className.getText().toString().trim(),mEditTextFileName.getText().toString().trim(), taskSnapshot.getDownloadUrl().toString());

                            Upload upload=new Upload(

                                    selectedItemText.toString().trim(),
                                    sectionName.getText().toString().trim(),
                                    subjectName.getText().toString().trim(),
                                    mEditTextFileName.getText().toString().trim(),
                                    taskSnapshot.getDownloadUrl().toString(),
                                    lastDate.getText().toString().trim());

                            String uploadId = mDatabaseRef.push().getKey();
                            mDatabaseRef.child(uploadId).setValue(upload);

                            Intent i=new Intent(UploadActivity.this,UploadActivity.class);
                            startActivity(i);
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UploadActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);
                        }
                    });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }





    /*  Medium Spinner Action*/
    private void initClassSpinner() {
        spinner = (Spinner) findViewById(R.id.spinner);
        // Spinner Drop down elements
        ArrayList<String> languages = new ArrayList<String>();
        languages.add("Play");
        languages.add("KG");
        languages.add("Nursery");
        languages.add("one");
        languages.add("Two");
        languages.add("Three");
        languages.add("Four");
        languages.add("Five");
        languages.add("Six");
        languages.add("Seven");
        languages.add("Eight");
        languages.add("Nine");
        languages.add("Ten");
        languages.add("Eleven");
        languages.add("Twelve");


        CustomSpinnerAdapter customSpinnerAdapter=new CustomSpinnerAdapter(UploadActivity.this,languages);
        spinner.setAdapter(customSpinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedItemText= parent.getItemAtPosition(position).toString();

                //  Toast.makeText(parent.getContext(), "Android Custom Spinner Example Output..." + item, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    public class CustomSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

        private final Context activity;
        private ArrayList<String> asr;

        public CustomSpinnerAdapter(Context context,ArrayList<String> asr) {
            this.asr=asr;
            activity = context;
        }



        public int getCount()
        {
            return asr.size();
        }

        public Object getItem(int i)
        {
            return asr.get(i);
        }

        public long getItemId(int i)
        {
            return (long)i;
        }



        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            TextView txt = new TextView(UploadActivity.this);
            txt.setPadding(16, 16, 16, 16);
            txt.setTextSize(18);
            txt.setGravity(Gravity.CENTER_VERTICAL);
            txt.setText(asr.get(position));
            txt.setTextColor(Color.parseColor("#000000"));
            return  txt;
        }

        public View getView(int i, View view, ViewGroup viewgroup) {
            TextView txt = new TextView(UploadActivity.this);
            txt.setGravity(Gravity.CENTER);
            txt.setPadding(16, 16, 16, 16);
            txt.setTextSize(16);
          //  txt.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0);
            txt.setText(asr.get(i));
            txt.setTextColor(Color.parseColor("#000000"));
            return  txt;
        }

    }


    // Back Button Work
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}