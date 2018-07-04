package com.barbarakoduzi.patrolapp.FirebaseMessaging;

import android.app.Service;
import android.util.Log;

import com.barbarakoduzi.patrolapp.Utils.CodesUtil;
import com.barbarakoduzi.patrolapp.Utils.MySharedPref;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class FirebaseToken extends FirebaseInstanceIdService {

    private static final String TAG = "FirebaseTag";

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        MySharedPref mySharedPref = new MySharedPref(this);

        mySharedPref.saveStringInSharedPref(CodesUtil.FIREBASE_TOKEN, refreshedToken);
    }

}