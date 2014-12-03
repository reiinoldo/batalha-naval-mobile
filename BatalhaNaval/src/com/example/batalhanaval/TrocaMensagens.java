package com.example.batalhanaval;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import android.app.IntentService;
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
            String mensagem = intent.getExtras().getString(EXTRAS_FILE_PATH);
            String host = intent.getExtras().getString(EXTRAS_GROUP_OWNER_ADDRESS);
            int port = intent.getExtras().getInt(EXTRAS_GROUP_OWNER_PORT);
            
            Socket socket = new Socket();            
            
            try {
                Log.i(WiFiDirectActivity.TAG, "Reinoldo Opening client socket - ");
                socket.bind(null);
                if (!socket.isConnected())
                	socket.connect((new InetSocketAddress(host, port)), SOCKET_TIMEOUT);
                
                Log.i(WiFiDirectActivity.TAG, "Reinoldo Client socket - " + socket.isConnected());
                
                OutputStream outputStream = socket.getOutputStream();
                               
                outputStream.write(mensagem.getBytes());
                outputStream.flush();
                
                Log.i(WiFiDirectActivity.TAG, "Reinoldo flush - " + socket.isConnected());
                
                InputStream inputStream = socket.getInputStream();
                InputStreamReader reader = new InputStreamReader(inputStream);
                BufferedReader bf = new BufferedReader(reader);
                
                Log.i(WiFiDirectActivity.TAG, "Reinoldo read - " + socket.isConnected());
                String retornoServidor = bf.readLine();
                //Loop Para Receber Msg
				while (retornoServidor != null){				
					retornoServidor = bf.readLine();
					Log.i(WiFiDirectActivity.TAG, "Reinoldo +-+ " + retornoServidor );
				}
				
                Log.i(WiFiDirectActivity.TAG, "Reinoldo readline - " + socket.isConnected());
                
                //outputStream.close();                
                
                Log.i(WiFiDirectActivity.TAG, "Reinoldo Client: Data written " + retornoServidor);
            } catch (IOException e) {
                Log.e(WiFiDirectActivity.TAG, "Reinoldo " + e.getMessage());
            } finally {
//                if (socket != null) {
//                    if (socket.isConnected()) {
//                        try {
//                            socket.close();
//                        } catch (IOException e) {
//                            // Give up
//                            e.printStackTrace();
//                        }
//                    }
//                }
            }

            
        }
        Log.i(WiFiDirectActivity.TAG, "Reinoldo fim onHandrleIntent");
    }

}
