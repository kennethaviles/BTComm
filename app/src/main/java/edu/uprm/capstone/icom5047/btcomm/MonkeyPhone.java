/*
 *  Copyright (c) 2011 by Twilio, Inc., all rights reserved.
 *
 *  Use of this software is subject to the terms and conditions of 
 *  the Twilio Terms of Service located at http://www.twilio.com/legal/tos
 */

package edu.uprm.capstone.icom5047.btcomm;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.twilio.client.Connection;
import com.twilio.client.Device;
import com.twilio.client.Twilio;

import java.util.HashMap;
import java.util.Map;

public class MonkeyPhone implements Twilio.InitListener
{
    private static final String TAG = "MonkeyPhone";

    private Device device;
    private Connection connection;

    public MonkeyPhone(Context context)
    {
        Twilio.initialize(context, this /* Twilio.InitListener */);
    }

    /* Twilio.InitListener method */
    @Override
    public void onInitialized()
    {
        Log.d(TAG, "Twilio SDK is ready");

        new RetrieveCapabilityToken().execute("http://maksolutions.herokuapp.com/token");
    }

    public void connect(String phoneNumber){
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("To", phoneNumber);
        connection = device.connect(parameters, null);
        if(connection == null){
            Log.w(TAG, "Failed to create new connection.");
        }
    }

    public void disconnect(){
        if(connection != null){
            connection.disconnect();
            connection = null;
        }
    }
    
    private class RetrieveCapabilityToken extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			try{ 
				String capabilityToken = HttpHelper.httpGet(params[0]);
				return capabilityToken; 
			} catch( Exception e ){
				 Log.e(TAG, "Failed to obtain capability token: " + e.getLocalizedMessage());
				 return null;
			}
			
		}
    	
		@Override
		protected void onPostExecute(String capabilityToken ){
			MonkeyPhone.this.setCapabilityToken(capabilityToken);
		}
    }

    protected void setCapabilityToken(String capabilityToken){
    	 device = Twilio.createDevice(capabilityToken, null /* DeviceListener */);
    }
    
    /* Twilio.InitListener method */
    @Override
    public void onError(Exception e)
    {
        Log.e(TAG, "Twilio SDK couldn't start: " + e.getLocalizedMessage());
    }

    @Override
    protected void finalize()
    {
        if (device != null)
            device.release();
    }
}
