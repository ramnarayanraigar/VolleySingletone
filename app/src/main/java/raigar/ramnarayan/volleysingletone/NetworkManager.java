package raigar.ramnarayan.volleysingletone;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kriscent on 27/4/18.
 */

class NetworkManager {
    private static NetworkManager instance = null;
    private RequestQueue requestQueue;

    private NetworkManager(Context context) {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

     static synchronized NetworkManager getInstance(Context context) {
        if (null == instance)
            instance = new NetworkManager(context);
        return instance;
    }

    //this is so you don't need to pass context each time
     static synchronized NetworkManager getInstance() {
        if (null == instance) {
            throw new IllegalStateException(NetworkManager.class.getSimpleName() +
                    " is not initialized, call getInstance(...) first");
        }
        return instance;
    }

     void somePostRequestReturningString(final Context mContext, JSONObject param, String url, final String TAG, final SomeCustomListener<String> listener) {
        Log.v(TAG, " API URL: " + url);
        Log.v(TAG, " API Parameters: " + param.toString());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, param,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response != null)
                            listener.getResult(response.toString(), "", false);
                        else listener.getResult("null", "", false);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            //This indicates that the request has either time out or there is no connection
                            Toast.makeText(mContext, "Internet connection is too slow to handle request", Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "Support Web Service error message: " + error.getMessage());
                            listener.getResult(error.getMessage(), "Internet connection is too slow to handle request", true);
                        } else if (error instanceof AuthFailureError) {
                            Toast.makeText(mContext, "Authentication failure, please try again later.", Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "Support Web Service error message: " + error.getMessage());
                            listener.getResult(error.getMessage(), "Authentication failure, please try again later.", true);
                        } else if (error instanceof ServerError) {
                            //Indicates that the server responded with a error response
                            Toast.makeText(mContext, "Server error", Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "Support Web Service error message: " + error.getMessage());
                            listener.getResult(error.getMessage(), "Server error", true);
                        } else if (error instanceof NetworkError) {
                            //Indicates that there was network error while performing the request
                            Toast.makeText(mContext, "Network error", Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "Support Web Service error message: " + error.getMessage());
                            listener.getResult(error.getMessage(), "Network error",true);
                        } else if (error instanceof ParseError) {
                            // Indicates that the server response could not be parsed
                            Toast.makeText(mContext, "Network error", Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "Support Web Service error message: " + error.getMessage());
                            listener.getResult(error.getMessage(), "Parsing error", true);
                        } else if (error != null) {
                            Toast.makeText(mContext, "Server error, please try again later.", Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "Support Web Service error message: " + error.getMessage());
                            listener.getResult(error.getMessage(), "Server not responding error", true);
                        }
                    }
                });

        requestQueue.add(request);
    }
}