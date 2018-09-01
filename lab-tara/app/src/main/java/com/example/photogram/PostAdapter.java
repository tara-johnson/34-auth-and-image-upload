package com.example.photogram;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private List<Post> mPosts;

    public PostAdapter() {
        mPosts = new ArrayList<>();
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.post_item, viewGroup, false);

        PostViewHolder vh = new PostViewHolder(view);
        return vh;
    }

    public void setPosts(List<Post> posts) {
        this.mPosts = posts;
        this.notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = mPosts.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        public View mView;

        public ImageView mImage;
        public TextView mUid;
        public TextView mDescription;

        private Post mPost;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;

            mImage = mView.findViewById(R.id.photo);
            mUid = mView.findViewById(R.id.uid);
            mDescription = mView.findViewById(R.id.description);
        }

        public void bind(Post post) {
            mPost = post;

            mUid.setText("Posted by: " + post.uid);
            mDescription.setText(post.description);

            Picasso.get()
                    .load(post.imageUrl)
                    .placeholder(R.drawable.loading)
                    .into(mImage);
        }
    }
}
