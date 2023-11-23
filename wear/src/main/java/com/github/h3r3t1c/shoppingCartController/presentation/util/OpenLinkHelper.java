package com.github.h3r3t1c.shoppingCartController.presentation.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Toast;

import androidx.wear.activity.ConfirmationActivity;
import androidx.wear.remote.interactions.RemoteActivityHelper;

import com.github.h3r3t1c.shoppingCartController.R;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeClient;
import com.google.android.gms.wearable.Wearable;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.Executors;

public class OpenLinkHelper {

    public static void openRemoteLink(final Activity c, String s){
        RemoteActivityHelper remoteActivityHelper = new RemoteActivityHelper(c, Executors.newSingleThreadExecutor());

        NodeClient client = Wearable.getNodeClient(c);
        client.getConnectedNodes().addOnSuccessListener(nodes -> {
            if (nodes.size() > 0) {
                String nodeId = null;
                for(Node n : nodes){
                    if(nodeId == null)
                        nodeId = nodes.get(0).getId();
                    else if(n.isNearby()){
                        nodeId = n.getId();
                    }
                }
                Intent intent = new Intent(c, ConfirmationActivity.class);
                intent.putExtra(ConfirmationActivity.EXTRA_ANIMATION_TYPE, ConfirmationActivity.OPEN_ON_PHONE_ANIMATION);
                intent.putExtra(ConfirmationActivity.EXTRA_MESSAGE, c.getString(R.string.continue_on_phone));
                intent.putExtra(ConfirmationActivity.EXTRA_ANIMATION_DURATION_MILLIS,3000);
                c.startActivity(intent);

                ListenableFuture<Void> result = remoteActivityHelper.startRemoteActivity(
                        new Intent(Intent.ACTION_VIEW)
                                .addCategory(Intent.CATEGORY_BROWSABLE)
                                .setData(
                                        Uri.parse(s)
                                )
                        , nodeId);
                result.addListener(() -> {}, Executors.newSingleThreadExecutor());
            } else {
                showPhoneError(c);
            }
        }).addOnFailureListener(failure -> {
            showPhoneError(c);
        });
    }
    private static void showPhoneError(final Activity c){
        c.getWindow().getDecorView().post(()->{
            Toast.makeText(c,"Not Connect To Phone", Toast.LENGTH_LONG).show();
        });
    }
}
