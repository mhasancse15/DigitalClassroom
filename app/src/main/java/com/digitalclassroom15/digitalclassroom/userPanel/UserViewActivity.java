package com.digitalclassroom15.digitalclassroom.userPanel;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.digitalclassroom15.digitalclassroom.R;
import com.digitalclassroom15.digitalclassroom.adapter.UserAdapter;
import com.digitalclassroom15.digitalclassroom.model.Upload;
import com.digitalclassroom15.digitalclassroom.userSession.UserSessionManager2;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class UserViewActivity extends AppCompatActivity implements UserAdapter.OnItemClickListener {
    private RecyclerView mRecyclerView;
    private UserAdapter mAdapter;

    private ProgressBar mProgressCircle;

    private FirebaseStorage mStorage;
    private DatabaseReference mDatabaseRef;
    private ValueEventListener mDBListener;

    private List<Upload> mUploads;

    private String imageUrl;
    private DownloadManager downloadManager;

    UserSessionManager2 session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view);
        getSupportActionBar().setTitle("Home Work View");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //SESSION MANAGER
        session = new UserSessionManager2(getApplicationContext());
        if (session.checkLogin())
            finish();


        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mProgressCircle = (ProgressBar) findViewById(R.id.progress_circle);

        mUploads = new ArrayList<>();

        mAdapter = new UserAdapter(UserViewActivity.this, mUploads);

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(UserViewActivity.this);

        mStorage = FirebaseStorage.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        mDBListener = mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                mUploads.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Upload upload = postSnapshot.getValue(Upload.class);
                    upload.setMkey(postSnapshot.getKey());
                    mUploads.add(upload);
                }

                mAdapter.notifyDataSetChanged();

                mProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(UserViewActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });
    }


    /// Menu Item
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            session.logoutUser();
            finish();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    /// MAIN MENU OPTION

    @Override
    public void onItemClick(int position) {
        //Toast.makeText(this, "Normal click at position: " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onWhatEverClick(int position) {
        Upload selectedItem = mUploads.get(position);
        final String selectedKey = selectedItem.getMkey();

        StorageReference imageRef = mStorage.getReferenceFromUrl(selectedItem.getImageUrl());
        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.e("Tuts+", "uri: " + uri.toString());
                imageUrl = uri.toString();

                downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                Uri downlodLink = Uri.parse(imageUrl);
                DownloadManager.Request request = new DownloadManager.Request(downlodLink);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                Long reference = downloadManager.enqueue(request);

                // Execute DownloadImage AsyncTask

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatabaseRef.removeEventListener(mDBListener);
    }


    // Back Button Work
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}