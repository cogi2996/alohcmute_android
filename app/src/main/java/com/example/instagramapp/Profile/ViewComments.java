package com.example.instagramapp.Profile;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.example.instagramapp.R;
import com.example.instagramapp.Utils.CommentListAdapter;
import com.example.instagramapp.models.Comments;
import com.example.instagramapp.models.Photo;

public class ViewComments extends AppCompatActivity {

    private static final String TAG = "ViewComments";

    //widgets
    private ImageView mBackArrow;
    private EditText mComment;
    private ListView mListView;
    private TextView mpost;
    private ImageView profileImage;

    //vars
    private Photo mphoto;
    private ArrayList<Comments> mComments;
    private Integer commentCount;

    //firebase
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_comments);
        mBackArrow = findViewById(R.id.back_from_view_comment);
        mComment = findViewById(R.id.comment);
        mListView = findViewById(R.id.listView);
        mpost = findViewById(R.id.post_comment);
        profileImage = findViewById(R.id.user_img);
        mComments = new ArrayList<>();

        // Initialize Firebase database reference
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        try {
            // Initialize the photo with mock data
            mphoto = getPhotoFromBundle();
            if (mphoto == null) {
                mphoto = new Photo();
                mphoto.setPhoto_id("1");
                mphoto.setCaption("Mock caption");
                mphoto.setUser_id("mock_user_id");
                mphoto.setDate_Created(getTimestamp());
            }
            commentCount = getIntent().getIntExtra("commentcount", 0);
            Log.d(TAG, "getPhotoFromBundle: arguments: " + mphoto);
            getCommentList();
        } catch (NullPointerException e) {
            Log.e(TAG, "onCreateView: NullPointerException: " + e.getMessage());
        }

        mpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mComment.getText().toString().isEmpty()) {
                    Log.d(TAG, "onClick: attempting to submit new comment.");
                    addNewComment(mComment.getText().toString());
                    mComment.setText("");
                    closeKeyboard();
                } else {
                    Toast.makeText(ViewComments.this, "you can't post a blank comment", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //get the photo from the incoming bundle from profileActivity interface
    private Photo getPhotoFromBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            return bundle.getParcelable("Photo");
        } else {
            return null;
        }
    }

    private void addNewComment(String newComment) {
        Log.d(TAG, "addNewComment: adding new comment: " + newComment);

        String commentID = myRef.push().getKey(); // get a unique id for the comment

        Comments comment = new Comments();
        comment.setComment(newComment);
        comment.setDate_created(getTimestamp());
        // Use mock user ID for testing
        comment.setUser_id("mock_user_id");

        //insert into photos node
        myRef.child("Photo")
                .child(mphoto.getPhoto_id())
                .child("comments")
                .child(commentID)
                .setValue(comment);

        //insert into user_photos node
        // myRef.child("User_Photo")
        //         .child(mphoto.getUser_id())
        //         .child(mphoto.getPhoto_id())
        //         .child("comments")
        //         .child(commentID)
        //         .setValue(comment);

        // addCommentNotification(comment.getComment(), mphoto.getUser_id(), mphoto.getPhoto_id());
    }
    private void getCommentList() {
        Log.d(TAG, "getCommentList: Comments");
        if (commentCount == 0) {
            mComments.clear();
            Comments firstComment = new Comments();
            firstComment.setUser_id(mphoto.getUser_id());
            firstComment.setComment(mphoto.getCaption());
            firstComment.setDate_created(mphoto.getDate_Created());
            mComments.add(firstComment);
            mphoto.setComments(mComments);
            setupWidgets();
        }
        myRef.child("Photo")
                .child("1")
                .child("comments")
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        Log.d(TAG, "onChildAdded: child added.");

                        Comments comment = snapshot.getValue(Comments.class);
                        if (comment != null) {
                            Log.d(TAG, "xxx: comment: " + comment.getComment());
                            mComments.add(comment);
                            setupWidgets();
                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        // Handle comment updates if necessary
                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                        // Handle comment deletions if necessary
                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        // Handle comment moves if necessary
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e(TAG, "onCancelled: database error", error.toException());
                    }
                });
    }


//    private void getCommentList() {
//        Log.d(TAG, "getCommentList: Comments");
//        if (commentCount == 0) {
//            mComments.clear();
//            Comments firstComment = new Comments();
//            firstComment.setUser_id(mphoto.getUser_id());
//            firstComment.setComment(mphoto.getCaption());
//            firstComment.setDate_created(mphoto.getDate_Created());
//            mComments.add(firstComment);
//            mphoto.setComments(mComments);
//            setupWidgets();
//        }
//        myRef.child("Photo")
////                .child(mphoto.getPhoto_id())
//                .child("1")
//                .child("comments")
//                .addChildEventListener(new ChildEventListener() {
//                    @Override
//                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                        Log.d(TAG, "onChildAdded: child added.");
//
////                        Query query = myRef.child("Photo").orderByChild("photo_id").equalTo(mphoto.getPhoto_id());
//                        Query query = myRef.child("Photo").orderByChild("photo_id").equalTo("1");
//                        query.addListenerForSingleValueEvent(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot dsnapshot) {
//                                for (DataSnapshot singleSnapshot : dsnapshot.getChildren()) {
//                                    Photo photo = new Photo();
//                                    Map<String, Object> objectMap = (HashMap<String, Object>) singleSnapshot.getValue();
//                                    photo.setCaption(objectMap.get("caption").toString());
//                                    photo.setTags(objectMap.get("tags").toString());
//                                    photo.setPhoto_id(objectMap.get("photo_id").toString());
//                                    photo.setUser_id(objectMap.get("user_id").toString());
//                                    photo.setDate_Created(objectMap.get("date_Created").toString());
//                                    photo.setImage_Path(objectMap.get("image_Path").toString());
//                                    Log.d("heeee", "onChildAdded: child added.");
//
//
//                                    mComments.clear();
//                                    for (DataSnapshot dSnapshot : singleSnapshot.child("comments").getChildren()) {
//                                        Comments comments = new Comments();
//                                        comments.setUser_id(dSnapshot.getValue(Comments.class).getUser_id());
//                                        comments.setComment(dSnapshot.getValue(Comments.class).getComment());
//                                        comments.setDate_created(dSnapshot.getValue(Comments.class).getDate_created());
//                                        mComments.add(comments);
//                                    }
//                                    photo.setComments(mComments);
//                                    mphoto = photo;
//                                    setupWidgets();
//                                }
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError error) {
//
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//                    }
//
//                    @Override
//                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//
//                    }
//
//                    @Override
//                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//    }

    // Set up widgets when the user clicks to post a comment
    private void setupWidgets() {
        CommentListAdapter adapter = new CommentListAdapter(this, R.layout.layout_each_comment, mComments);
        mListView.setAdapter(adapter);
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private String getTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }
}
