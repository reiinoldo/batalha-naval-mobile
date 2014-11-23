package com.batalhanaval.p2p;

import java.util.ArrayList;
import java.util.List;

import com.example.batalhanaval.MainActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.ActionListener;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.net.wifi.p2p.WifiP2pManager.PeerListListener;

public class WiFiDirectBroadcastReceiver extends BroadcastReceiver {

    private WifiP2pManager mManager;
    private Channel mChannel;
    private MainActivity mActivity;
    
    private WifiP2pDevice dispositivo;

    public WiFiDirectBroadcastReceiver(WifiP2pManager manager, Channel channel, MainActivity activity) {
        super();
        this.mManager = manager;
        this.mChannel = channel;
        this.mActivity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        
        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                // Wifi P2P is enabled
            } else {
                // Wi-Fi P2P is not enabled
            }
        }
        

        if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
        	// Achou algum par
        	if (mManager != null) {
    	        mManager.requestPeers(mChannel, new PeerListListener() {
					@Override
					public void onPeersAvailable(WifiP2pDeviceList peers) {
						listaDispositivos(peers);
					}
				});
    	    }
        }else if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
        	// Verifique se Wi-Fi estiver ativada e notificar atividade apropriada
        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
            // Responder a nova ligação ou desconexões
        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
        	// Responda ao estado wi-fi deste dispositivo de mudança
        }
    }
    
    public void listaDispositivos(WifiP2pDeviceList peers){
    	List<WifiP2pDevice> lista  = new ArrayList<WifiP2pDevice>();
    	lista.addAll(peers.getDeviceList());
    	dispositivo = lista.get(0);
    	
    	conectar();
    }
    
    public void conectar(){
    	WifiP2pDevice device;
    	WifiP2pConfig config = new WifiP2pConfig();
    	config.deviceAddress = dispositivo.deviceAddress;
    	mManager.connect(mChannel, config, new ActionListener() {

    	    @Override
    	    public void onSuccess() {
    	        //success logic
    	    }

    	    @Override
    	    public void onFailure(int reason) {
    	        //failure logic
    	    }
    	});
    }
}