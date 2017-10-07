package application.example.com.notecard.widget;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import application.example.com.notecard.Model.Stories;
import application.example.com.notecard.R;

/**
 * Created by Dell on 04-10-2017.
 */

public class StoryRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    public static final String TAG = StoryRemoteViewsFactory.class.getSimpleName();
    private Context mContext;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference firebaseDatabase;
    private ArrayList<Stories> storiesArrayList;
    private CountDownLatch mCountDownLatch;
    private ArrayList<String> array;



    public StoryRemoteViewsFactory(Context appliationContext, Intent intent) {
        mContext = appliationContext;
        storiesArrayList = new ArrayList<>();
        array=new ArrayList<>();
    }

    @Override
    public void onCreate() {


    }

    @Override
    public void onDataSetChanged() {
        mCountDownLatch = new CountDownLatch(1);
        array.add("sss");
        array.add("ttt");


        try {
            mCountDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    /*

    private void getItems() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance().getReference().child("nodes").child(firebaseAuth.getCurrentUser().getUid());

        final String noteId = firebaseDatabase.push().getKey();
        Log.d(TAG, noteId);
        if (mCountDownLatch.getCount() == 0) {

            firebaseDatabase.child(noteId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        Stories stories = userSnapshot.getValue(Stories.class);

                        storiesArrayList.add(stories);

                    }


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(mContext, "Error", Toast.LENGTH_SHORT).show();

                }
            });
        } else {
            if (storiesArrayList != null) {

                firebaseDatabase.child(noteId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                            Stories stories = userSnapshot.getValue(Stories.class);


                            storiesArrayList.add(stories);
                            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(mContext);
                            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(
                                    new ComponentName(mContext, getClass()));
                            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list_view);


                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(mContext, "Error", Toast.LENGTH_SHORT).show();

                    }
                });
            }
            mCountDownLatch.countDown();


        }
    }
    */


    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (array != null) {
            Log.d(TAG + "items", String.valueOf(array.size()));

            return array.size();
        } else {
            return 0;
        }
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_list_item);
        String stories = array.get(position);
        remoteViews.setTextViewText(R.id.widget_title_note, stories);
        remoteViews.setTextViewText(R.id.widget_time_note, stories);
        Bundle extras = new Bundle();
        extras.putString(mContext.getString(R.string.stories), stories);
        Intent fillIntent = new Intent();
        fillIntent.putExtras(extras);
        remoteViews.setOnClickFillInIntent(R.id.widget_item_linear, fillIntent);


        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }


}
