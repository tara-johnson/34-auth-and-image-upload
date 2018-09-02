package com.example.photogram;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FeedActivity extends AppCompatActivity {

    @BindView(R.id.feed)
    public RecyclerView recyclerView;
    public LinearLayoutManager linearLayoutManager;
    public PostAdapter postAdapter;

    private List<Post> mPosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        Intent data = getIntent();
        String uid = data.getStringExtra("uid");
        String email = data.getStringExtra("email");
        if (data.hasExtra("email")) {
            email = data.getStringExtra("email");
        }

        String message = "Logged in as: ";
        if (email != null) {
            message += email;
        } else {
            message += "Anonymous (" + uid + ")";
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        ButterKnife.bind(this);

        linearLayoutManager = new LinearLayoutManager(this);
        postAdapter = new PostAdapter();

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(postAdapter);

        loadPictures();
    }

    private void loadPictures() {
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        mDatabase.getReference("photos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Post> posts = new ArrayList<>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String description = child.child("description").getValue(String.class);
                    String uid = child.child("uid").getValue(String.class);
                    String imageUrl = child.child("imageUrl").getValue(String.class);

                    // Create posts
                    Post post = new Post(uid, description, imageUrl);
                    posts.add(post);
                }
                postAdapter.setPosts(posts);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(FeedActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT);
                Log.d("ERROR", databaseError.getMessage());
            }
        });
    }

    @OnClick(R.id.takePicture)
    public void takePicture() {
        Intent intent = new Intent(this, PhotoUploadActivity.class);
        startActivity(intent);
    }
}
