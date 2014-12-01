package com.example.batalhanaval;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

@SuppressLint("UseSparseArrays")
public class MainActivity extends Activity {
	
	/* CAMBIO!
	 * eai dotores, fiz a parte do funcionamento do jogo sem rede, 
	 * agora só falta implementar os métodos 'enviarMensagem' e 'receptorMensagem' onde
	 * o mesmo vai enviar a string para o outro device, e o outro vai receber
	 * 
	 * na linha 121, deve ser setado quem é o jogador do device, 1 ou 2
	 * 
	 * para simular o jogo sem rede, basta descomentar as linhas 218 e 226...
	 * Sestari */

	private ImageButton ibt1;
	private ImageButton ibt2;
	private ImageButton ibt3;
	private ImageButton ibt4;
	private ImageButton ibt5;
	private ImageButton ibt6;
	private ImageButton ibt7;
	private ImageButton ibt8;
	private ImageButton ibt9;
	private HashMap<Integer, Integer> posicaoBarcos;
	private TextView lbMsg;
	private TextView lbGeral;
	private Button btExecutar;
	private Button btConectar;	
	private Button btnLimpar;
	private Jogo jogo;
	private ArrayList<ImageButton> botoesTela = new ArrayList<ImageButton>();
	
	
	//PADRAO DE MENSAGENS
	private static String AGUARDANDO_ATAQUE = "AGAT";
	private static String ATACANDO = "ATCD";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		posicaoBarcos = new HashMap<Integer, Integer>();
		
		lbMsg = (TextView) findViewById(R.id.lbMsg);
		lbMsg.setText("");
		lbGeral = (TextView) findViewById(R.id.lbGeral);
		
		btExecutar = (Button) findViewById(R.id.btExecutar);
		btExecutar.setEnabled(false); 
		btExecutar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				prepararJogada();				
			}
		});
		
		btnLimpar = (Button) findViewById(R.id.btnLimpar);
		btnLimpar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				limparBotoes();
			}
		});
		
		btConectar = (Button) findViewById(R.id.btConectar);		 
		btConectar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(MainActivity.this, WiFiDirectActivity.class));
				btConectar.setEnabled(false);				
			}
		});

		ibt1 = (ImageButton) findViewById(R.id.ibt1);
		ibt1.setTag(1);
		ibt2 = (ImageButton) findViewById(R.id.ibt2);
		ibt2.setTag(2);
		ibt3 = (ImageButton) findViewById(R.id.ibt3);
		ibt3.setTag(3);
		ibt4 = (ImageButton) findViewById(R.id.ibt4);
		ibt4.setTag(4);
		ibt5 = (ImageButton) findViewById(R.id.ibt5);
		ibt5.setTag(5);
		ibt6 = (ImageButton) findViewById(R.id.ibt6);
		ibt6.setTag(6);
		ibt7 = (ImageButton) findViewById(R.id.ibt7);
		ibt7.setTag(7);
		ibt8 = (ImageButton) findViewById(R.id.ibt8);
		ibt8.setTag(8);
		ibt9 = (ImageButton) findViewById(R.id.ibt9);
		ibt9.setTag(9);
		botoesTela.add(ibt1);
		botoesTela.add(ibt2);
		botoesTela.add(ibt3);
		botoesTela.add(ibt4);
		botoesTela.add(ibt5);
		botoesTela.add(ibt6);
		botoesTela.add(ibt7);
		botoesTela.add(ibt8);
		botoesTela.add(ibt9);

		for(final ImageButton btn : botoesTela){
			btn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					posicionaBarco(btn, Integer.valueOf(String.valueOf(btn.getTag())));
				}
			});
		}
		limparBotoes();
		
		jogo = new Jogo(this,1); //selecionar quem é o jogador
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
			startActivity(new Intent(MainActivity.this, WiFiDirectActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/*
	 * Metódo que envia a mensagem pra outro aparelho
	 * 
	 */
	public void enviarMensagem(String mensagem){
		
	}
	
	/*
	 * Metódo principal para comunicação entre os devices
	 * 
	 */
	public void receptorMensagem(String mensagem){
		String[] msm = mensagem.split("@#");
		jogo.posicionarNavios(msm[1], jogo.getJogador() == 1 ? 2 : 1); //seta os navios do jogador adversario
		
		if(msm[0].equals(AGUARDANDO_ATAQUE)){
			jogo.setAtaqueLiberado(true); //outro jogador ja preparou sua defesa
			if(jogo.isAtaquePreparado()){ //meu ataque está pronto
				atacar();
			}
		}else if(msm[0].equals(ATACANDO)){
			jogo.verificarResultadoJogada();
		}
		
	}
	
	public void atacar(){
		enviarMensagem(ATACANDO+"@#"+getPosicaoBarcosMensagem()); //vai atacar e enviar a posição dos barcos
		jogo.verificarResultadoJogada();
	}

	public void prepararDefesa(){
		lbGeral.setText("Prepare os seus navios para a desefa!");
		btExecutar.setText("Defender!");
		setActivatedBotoes(true);
		limparBotoes();
	}
	
	public void preparAtaque(){
		lbGeral.setText("Prepare o seu ataque...");
		btExecutar.setText("Atacar!");
	}
	
	public void prepararJogada(){
		if(jogo.isPartidaPause()){
			jogo.mudaLadoRodada();
			limparBotoes();
			if(jogo.isAtaque()){
				preparAtaque();
			}else{
				prepararDefesa();
			}
		}else{
			btExecutar.setEnabled(false);
			jogo.posicionarNavios(getPosicaoBarcosMensagem(), jogo.getJogador()); //seta a posicao dos meus navios
			
			if(jogo.isAtaque()){
				btnLimpar.setEnabled(false);
				jogo.setAtaquePreparado(true);
				
				if(jogo.isAtaqueLiberado()){
					atacar();
				}else{
					lbGeral.setText("Aguardando o outro jogador...");
					
					//PARA SIMULAR SEM REDE
					receptorMensagem(AGUARDANDO_ATAQUE+"@#1;2;3");  
				}
			}else{
				btnLimpar.setEnabled(false);
				lbGeral.setText("Aguardando ataque...");
				enviarMensagem(AGUARDANDO_ATAQUE+"@#"+getPosicaoBarcosMensagem());
				
				//PARA SIMULAR SEM REDE
				receptorMensagem(ATACANDO+"@#1;2;3"); 
			}
		}
	}
	
	private void setActivatedBotoes(boolean activated){
		for(ImageButton btn : botoesTela){
			btn.setEnabled(activated);
		}
	}
	
	private void limparBotoes(){
		for(ImageButton btn : botoesTela){
			btn.setImageResource(R.drawable.sea);
			btn.setBackgroundColor(Color.TRANSPARENT);
		}
		posicaoBarcos.clear();
		setActivatedBotoes(true);
		btnLimpar.setEnabled(true);
	}

	private void posicionaBarco(ImageButton ibt, int idButton) {
		System.out.println(idButton);
		if (!posicaoBarcos.containsKey(idButton)){
			if(jogo.isAtaque()){
				ibt.setBackgroundColor(Color.GRAY); //seleção
			}else{
				ibt.setImageResource(R.drawable.ship1); //navio
			}
			posicaoBarcos.put(idButton, 0);

			if (posicaoBarcos.size() == 3){
				setActivatedBotoes(false);
				btExecutar.setEnabled(true);
			}
		}else{
			ibt.setImageResource(R.drawable.sea); 
			posicaoBarcos.remove(idButton);
		}
	}

	public void pintarMapaJogada(ArrayList<String> acertos){
		//desenha o mapa do adversário
		Set<String> it = jogo.getPosicaoNavios(true).keySet();
		for(String id : it){
			for(ImageButton btn : botoesTela){
				if(String.valueOf(btn.getTag()).equals(id)){
					if(jogo.isAtaque()){
						btn.setImageResource(R.drawable.ship2); 
					}else{
						btn.setImageResource(R.drawable.explosion);; //onde tentou acertar
					}
				}
			}
		}
		
		//desenha os acertos
		for(String id : acertos){
			for(ImageButton btn : botoesTela){
				if(String.valueOf(btn.getTag()).equals(id)){
					btn.setImageResource(R.drawable.explosion);
				}
			}
		}
		String placar = "PLACAR   Você: ";
		if(jogo.getJogador() == 1){
			placar += jogo.getPlacarJogador1() + " Adversário: "+jogo.getPlacarJogador2();
		}else{
			placar += jogo.getPlacarJogador2() + " Adversário: "+jogo.getPlacarJogador1();
		}
		lbMsg.setText(placar);
		btExecutar.setText("Continuar");
		btExecutar.setEnabled(true);
		lbGeral.setText("");
		jogo.setPartidaPause(true);
	}
	
	public String getPosicaoBarcosMensagem(){
		String retorno = "";
		Set<Integer> ff = posicaoBarcos.keySet();
		for(Integer key : ff){
			retorno += key+";";
		}
		return retorno;
	}
}
