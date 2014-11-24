package com.example.batalhanaval;

import java.util.HashMap;
import java.util.Map;

import com.batalhanaval.p2p.WiFiDirectBroadcastReceiver;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("UseSparseArrays")
public class MainActivity extends Activity {

	private ImageButton ibt1;
	private ImageButton ibt2;
	private ImageButton ibt3;
	private ImageButton ibt4;
	private ImageButton ibt5;
	private ImageButton ibt6;
	private ImageButton ibt7;
	private ImageButton ibt8;
	private ImageButton ibt9;
	private RadioButton rbnv1;
	private RadioButton rbnv2;
	private RadioButton rblimpar;
	private int qtdBarcos, qtdBarcosOld;
	private int[] posicaoBarcos;
	private TextView lbMsg;
	private Button btExecutar;
	private Boolean mapaAdversario = false;
	
	private Map<ImageButton, Integer> mapAdversario;

	/* P2P */
	private WifiP2pManager mManager;
	private Channel mChannel;
	private WiFiDirectBroadcastReceiver mReceiver;
	IntentFilter mIntentFilter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		//iniciarWifiP2P();

		rbnv1 = (RadioButton) findViewById(R.id.radio0);
		rbnv2 = (RadioButton) findViewById(R.id.radio1);
		// rblimpar = (RadioButton) findViewById(R.id.rbt3);

		posicaoBarcos = new int[3];
//		posicaoBarcosAdversario = new int[9];
		
		qtdBarcos = 0;
		qtdBarcosOld = qtdBarcos;
		
		lbMsg = (TextView) findViewById(R.id.lbMsg);
		lbMsg.setText("");
		
		btExecutar = (Button) findViewById(R.id.btExecutar);
		btExecutar.setEnabled(false); 

		ibt1 = (ImageButton) findViewById(R.id.ibt1);
		ibt2 = (ImageButton) findViewById(R.id.ibt2);
		ibt3 = (ImageButton) findViewById(R.id.ibt3);
		ibt4 = (ImageButton) findViewById(R.id.ibt4);
		ibt5 = (ImageButton) findViewById(R.id.ibt5);
		ibt6 = (ImageButton) findViewById(R.id.ibt6);
		ibt7 = (ImageButton) findViewById(R.id.ibt7);
		ibt8 = (ImageButton) findViewById(R.id.ibt8);
		ibt9 = (ImageButton) findViewById(R.id.ibt9);

		ibt1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				posicionaBarco(ibt1, 1);
			}
		});

		ibt2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				posicionaBarco(ibt2, 2);
			}
		});

		ibt3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				posicionaBarco(ibt3, 3);
			}
		});

		ibt4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				posicionaBarco(ibt4, 4);
			}
		});

		ibt5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				posicionaBarco(ibt5, 5);
			}
		});

		ibt6.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				posicionaBarco(ibt6, 6);
			}
		});

		ibt7.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				posicionaBarco(ibt7, 7);
			}
		});

		ibt8.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				posicionaBarco(ibt8, 8);
			}
		});

		ibt9.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				posicionaBarco(ibt9, 9);
			}
		});
		
		btExecutar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				mapaAdversario();				
			}
		});
		
		//procurarPares();
	}

	private void iniciarWifiP2P() {
		mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
		mChannel = mManager.initialize(this, getMainLooper(), null);
		mReceiver = new WiFiDirectBroadcastReceiver(mManager, mChannel, this);

		mIntentFilter = new IntentFilter();
		mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
		mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
		mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
		mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
	}

	private void procurarPares() {
		mManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {
			@Override
			public void onSuccess() {
				System.out.println("Encontrou algum dispositivo...");
			}
			@Override
			public void onFailure(int reasonCode) {
				System.out.println("Falha ao encontrar algum dispositivo, CÃ³digo: "+reasonCode);
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		//registerReceiver(mReceiver, mIntentFilter);
	}

	@Override
	protected void onPause() {
		super.onPause();
		//unregisterReceiver(mReceiver);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void posicionaBarco(ImageButton ibt, int idButton) {

		if(!mapaAdversario){
			//if (rblimpar.checked) {
			if (false) {
				ibt.setImageResource(R.drawable.sea);
	
				int[] posicaoBarcosAux = new int[3];
	
				// Recalcular quantos barcos tem na tela
				qtdBarcos = 0;
				for (int i = 0; i < posicaoBarcos.length; i++) {
					if (posicaoBarcos[i] != idButton && posicaoBarcos[i] > 0) {
						posicaoBarcosAux[qtdBarcos] = posicaoBarcos[i];
						qtdBarcos++;
					}
				}
				posicaoBarcos = posicaoBarcosAux;
				// Toast.makeText(this, Integer.toString(qtdBarcos),
				// Toast.LENGTH_LONG).show();
	
			} else {
				if (qtdBarcos >= 3){
					if (!btExecutar.isEnabled()) 
						btExecutar.setEnabled(true);
					return;
					
				}	
	
				if (rbnv1.isChecked())
					ibt.setImageResource(R.drawable.ship1);
				else if (rbnv2.isChecked())
					ibt.setImageResource(R.drawable.ship2);
	
				// Evita contagem do mesmo botão
				if (posicaoBarcos[qtdBarcosOld] != idButton){
					posicaoBarcos[qtdBarcos] = idButton;
					qtdBarcosOld = qtdBarcos;
					qtdBarcos++;
					lbMsg.setText("Escolhido (" + qtdBarcos + "/3)");
					if (qtdBarcos == 3)
						if (!btExecutar.isEnabled()) 
							btExecutar.setEnabled(true);
				}
			}
		}else{
			if (acertouBarco(idButton)){
				ibt.setImageResource(R.drawable.explosion);
			}else{
				ibt.setBackgroundColor(Color.GRAY);
			}
		}
	}

	public boolean acertouBarco(int idBarco) {
		for (int i = 0; i < posicaoBarcos.length; i++) {
			if (posicaoBarcos[i] == idBarco)
				return true;
		}
		return false;
	}
	
	public void mapaAdversario(){
		mapaAdversario = true;
		mapAdversario = new HashMap<ImageButton, Integer>();
		
		mapAdversario.put(ibt1, R.drawable.sea);
		mapAdversario.put(ibt2, R.drawable.sea);
		mapAdversario.put(ibt3, R.drawable.sea);
		mapAdversario.put(ibt4, R.drawable.sea);
		mapAdversario.put(ibt5, R.drawable.sea);
		mapAdversario.put(ibt6, R.drawable.sea);
		mapAdversario.put(ibt7, R.drawable.sea);
		mapAdversario.put(ibt8, R.drawable.sea);
		mapAdversario.put(ibt9, R.drawable.sea);
		
		setImage(mapAdversario);
	}
	
	public void setImage(Map<ImageButton, Integer> mapa){
		int idImagem;
		for (ImageButton ibt: mapa.keySet()){			
			idImagem = mapa.get(ibt);
			ibt.setImageResource(idImagem);			
		}
		
	}

}
