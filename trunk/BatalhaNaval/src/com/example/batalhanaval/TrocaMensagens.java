package com.example.batalhanaval;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import android.app.IntentService;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;

public class TrocaMensagens extends IntentService{
	
	private static final int SOCKET_TIMEOUT = 5000;
    public static final String ACTION_SEND_FILE = "com.example.android.wifidirect.SEND_FILE";
    public static final String EXTRAS_FILE_PATH = "file_url";	
    public static final String EXTRAS_GROUP_OWNER_ADDRESS = "go_host";
    public static final String EXTRAS_GROUP_OWNER_PORT = "go_port";
	
	public TrocaMensagens(String name) {
        super(name);
    }

    public TrocaMensagens() {
        super("TrocaMensagens");
    }
	
	@Override
    protected void onHandleIntent(Intent intent) {	
		

		Log.i(WiFiDirectActivity.TAG, "Reinoldo onHandrleIntent");
        if (intent.getAction().equals(ACTION_SEND_FILE)) {
            String fileUri = intent.getExtras().getString(EXTRAS_FILE_PATH);
            String host = intent.getExtras().getString(EXTRAS_GROUP_OWNER_ADDRESS);
            Socket socket = new Socket();
            int port = intent.getExtras().getInt(EXTRAS_GROUP_OWNER_PORT);

            
            try {
                Log.i(WiFiDirectActivity.TAG, "Reinoldo Opening client socket - ");
                socket.bind(null);
                socket.connect((new InetSocketAddress(host, port)), SOCKET_TIMEOUT);
                
                Log.i(WiFiDirectActivity.TAG, "Reinoldo Client socket - " + socket.isConnected());
                
                OutputStream outputStream = socket.getOutputStream();
                               
                outputStream.write(fileUri.getBytes());
                
                outputStream.close();                               
                
                Log.i(WiFiDirectActivity.TAG, "Reinoldo Client: Data written");
            } catch (IOException e) {
                Log.e(WiFiDirectActivity.TAG, "Reinoldo " + e.getMessage());
            } finally {
                if (socket != null) {
                    if (socket.isConnected()) {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            // Give up
                            e.printStackTrace();
                        }
                    }
                }
            }

            
        }
        Log.i(WiFiDirectActivity.TAG, "Reinoldo fim onHandrleIntent");
    }

}
