package com.example.batalhanaval;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Jogo {
	
	private Integer jogador = 1; //quem é o jogador do device, 1 ou 2
	private Integer placarJogador1 = 0;
	private Integer placarJogador2 = 0;
	private Integer jogadorDeAtaque = 1; //Jogador que está no ataque, 1 ou 2
	private MainActivity activity;
	private boolean ataqueLiberado = false;
	private boolean ataquePreparado = false;
	private HashMap<String, Boolean>[] posicaoNavios; //posição dos navios
	private boolean partidaPause = false;

	@SuppressWarnings("unchecked")
	public Jogo(MainActivity activity, Integer jogador){
		this.activity = activity;
		this.jogador = jogador;
		
		//hashmap com a posicao dos navios dos jogadores
		posicaoNavios = new HashMap[2];
		posicaoNavios[0] = new HashMap<String, Boolean>(); //jogador 1
		posicaoNavios[1] = new HashMap<String, Boolean>(); //jogador 2
		
		if(jogador == 1){
			activity.preparAtaque();
		}else{
			activity.prepararDefesa();
		}
	}
	
	public void mudaLadoRodada(){
		jogadorDeAtaque = jogadorDeAtaque == 1 ? 2 : 1;
		ataqueLiberado = false;
		ataquePreparado = false;
		partidaPause = false;
	}

	//rebece os navios selecionados
	public void posicionarNavios(String mensagem, Integer jogador){
		//exemplo mensagem: 1;3;9
		posicaoNavios[jogador-1] = new HashMap<String, Boolean>();
		String[] vet = mensagem.split(";");
		for(String navio : vet){
			posicaoNavios[jogador-1].put(navio, true);
			System.out.println("navio -- "+navio+";jog "+jogador);
		}
	}
	
	//verifica o que acertou e manda a activy desenhar
	public void verificarResultadoJogada(){
		ArrayList<String> acertos = new ArrayList<String>();
		Set<String> it = (posicaoNavios[0]).keySet();
		for(String id : it){
			if(posicaoNavios[1].containsKey(id)){
				acertos.add(id);
			}
		}
		
		if(jogadorDeAtaque == jogador){
			if(jogador == 1){
				placarJogador1 += acertos.size();
			}else{
				placarJogador2 += acertos.size();
			}
		}else{
			if(jogador != 1){
				placarJogador1 += acertos.size();
			}else{
				placarJogador2 += acertos.size();
			}
		}
		activity.pintarMapaJogada(acertos);
	}
	
	public HashMap<String, Boolean> getPosicaoNavios(boolean posicaoDoAdversario){
		if(posicaoDoAdversario){
			return posicaoNavios[jogador == 1 ? 1 : 0];
		}else{
			return posicaoNavios[jogador == 1 ? 0 : 1];
		}
	}
	
	
	public boolean isAtaque(){
		return jogadorDeAtaque == 1 && jogador == 1; //verifica se o próprio jogador está em modo de ataque
	}
	
	public Integer getJogador() {
		return jogador;
	}

	public void setJogador(Integer jogador) {
		this.jogador = jogador;
	}

	public Integer getPlacarJogador1() {
		return placarJogador1;
	}

	public void setPlacarJogador1(Integer placarJogador1) {
		this.placarJogador1 = placarJogador1;
	}

	public Integer getPlacarJogador2() {
		return placarJogador2;
	}

	public void setPlacarJogador2(Integer placarJogador2) {
		this.placarJogador2 = placarJogador2;
	}

	public Integer getJogadorDeAtaque() {
		return jogadorDeAtaque;
	}

	public void setJogadorDeAtaque(Integer jogadorDeAtaque) {
		this.jogadorDeAtaque = jogadorDeAtaque;
	}

	public HashMap<String, Boolean>[] getPosicaoNavios() {
		return posicaoNavios;
	}

	public void setPosicaoNavios(HashMap<String, Boolean>[] posicaoNavios) {
		this.posicaoNavios = posicaoNavios;
	}

	public boolean isAtaquePreparado() {
		return ataquePreparado;
	}

	public void setAtaquePreparado(boolean ataquePreparado) {
		this.ataquePreparado = ataquePreparado;
	}

	public boolean isAtaqueLiberado() {
		return ataqueLiberado;
	}

	public void setAtaqueLiberado(boolean ataqueLiberado) {
		this.ataqueLiberado = ataqueLiberado;
	}
	
	public boolean isPartidaPause() {
		return partidaPause;
	}

	public void setPartidaPause(boolean partidaPause) {
		this.partidaPause = partidaPause;
	}

}
