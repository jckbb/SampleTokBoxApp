package com.tasktokboxapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.opentok.android.Session;
import com.opentok.android.Stream;
import com.opentok.android.Publisher;
import com.opentok.android.PublisherKit;
import com.opentok.android.Subscriber;
import com.opentok.android.OpentokError;
import android.support.annotation.NonNull;
import android.Manifest;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import android.widget.FrameLayout;

/**
 * Created by kb on 3/31/18.
 */

public class TokBoxActivity extends AppCompatActivity implements
        Session.SessionListener,
        PublisherKit.PublisherListener {

    private static final int RC_VIDEO_APP_PERM = 124;

    private static String API_KEY = "46091842";
    private static String SESSION_ID = "2_MX40NjA5MTg0Mn5-MTUyMjQ1MTk4MjQwMn40K05TYTlURlc3STZXRnBnRXd1VFFYN0h-UH4";
    private static String TOKEN = "T1==cGFydG5lcl9pZD00NjA5MTg0MiZzaWc9ODBkZjQyZTM4Y2JlY2RiY2JhYzcwZTg1OWZmNWVkNmIzNjllYjU0YzpzZXNzaW9uX2lkPTJfTVg0ME5qQTVNVGcwTW41LU1UVXlNalExTVRrNE1qUXdNbjQwSzA1VFlUbFVSbGMzU1RaWFJuQm5SWGQxVkZGWU4waC1VSDQmY3JlYXRlX3RpbWU9MTUyMjQ3MTgxMyZub25jZT0wLjQzMDgyNDQ5OTcwODk3Mjc0JnJvbGU9cHVibGlzaGVyJmV4cGlyZV90aW1lPTE1MjMwNzY2MTImaW5pdGlhbF9sYXlvdXRfY2xhc3NfbGlzdD0=";

    private Session mSession;
    private FrameLayout mPublisherViewContainer;
    private FrameLayout mSubscriberViewContainer;
    private Publisher mPublisher;
    private Subscriber mSubscriber;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        requestPermissions();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(RC_VIDEO_APP_PERM)
    private void requestPermissions() {
        String[] perms = { Manifest.permission.INTERNET, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO };

        if (EasyPermissions.hasPermissions(this, perms)) {
            // initialize view objects from your layout
            mPublisherViewContainer = (FrameLayout)findViewById(R.id.publisher_container);
            mSubscriberViewContainer = (FrameLayout)findViewById(R.id.subscriber_container);

            // initialize and connect to the session
            mSession = new Session.Builder(this, API_KEY, SESSION_ID).build();
            mSession.setSessionListener(this);
            mSession.connect(TOKEN);
        } else {
            EasyPermissions.requestPermissions(this, "This app needs access to your camera and mic to make video calls", RC_VIDEO_APP_PERM, perms);
        }
    }

    @Override
    public void onConnected(Session session) {
        mPublisher = new Publisher.Builder(this).build();
        mPublisher.setPublisherListener(this);

        mPublisherViewContainer.addView(mPublisher.getView());
        mSession.publish(mPublisher);
    }

    @Override
    public void onDisconnected(Session session) {}

    @Override
    public void onStreamReceived(Session session, Stream stream) {

        if (mSubscriber == null) {
            mSubscriber = new Subscriber.Builder(this, stream).build();
            mSession.subscribe(mSubscriber);
            mSubscriberViewContainer.addView(mSubscriber.getView());
        }
    }

    @Override
    public void onStreamDropped(Session session, Stream stream) {

        if (mSubscriber != null) {
            mSubscriber = null;
            mSubscriberViewContainer.removeAllViews();
        }
    }

    @Override
    public void onError(Session session, OpentokError opentokError) {}

    @Override
    public void onStreamCreated(PublisherKit publisherKit, Stream stream) {}

    @Override
    public void onStreamDestroyed(PublisherKit publisherKit, Stream stream) {}

    @Override
    public void onError(PublisherKit publisherKit, OpentokError opentokError) {}
}