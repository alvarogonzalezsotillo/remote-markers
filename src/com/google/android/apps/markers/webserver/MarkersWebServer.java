package com.google.android.apps.markers.webserver;

import android.graphics.Bitmap;
import android.util.Log;
import com.google.android.apps.markers.Slate;
import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.ServerRunner;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class MarkersWebServer {

    private static final String TAG = "WebServer";
    public static final int PORT = 8081;

    private BitmapSource mBitmapSource;

    private class MyNanoHTTPD extends NanoHTTPD{

        public MyNanoHTTPD(int port) {
            super(port);
        }

        @Override
        public Response serve(IHTTPSession session){
            Log.e(TAG, "Image requested");

            if( bitmapSource() == null ){
                return new Response( Response.Status.INTERNAL_ERROR, "text/plain", "There is no bitmap source at the moment" );
            }
            Bitmap b = bitmapSource().currentBitmap();
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            b.compress(Bitmap.CompressFormat.PNG, 0, os);
            ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
            Log.e(TAG, "Image computed");
            return new Response( Response.Status.OK, "image/png", is );
        }
    }

    private MyNanoHTTPD mServer;

    public MarkersWebServer(BitmapSource bitmapSource) {
        setBitmapSource(bitmapSource);
    }

    public void setBitmapSource(BitmapSource bitmapSource){
        mBitmapSource = bitmapSource;
        if( mServer != null ){
            mServer.stop();
        }
        mServer = new MyNanoHTTPD(PORT);
        try {
            mServer.start();
        }
        catch (IOException e) {
            Log.e( TAG, "Couldn't open web server", e );
        }
        Log.e( TAG, "******************************* WEBSERVER STARTED **************************" );
    }

    public BitmapSource bitmapSource(){
        return mBitmapSource;
    }


}
