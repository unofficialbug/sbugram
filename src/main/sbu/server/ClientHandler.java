package sbu.client;

import sbu.common.*; 

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;
public class ClientHandler{

	private Socket userSocket;
	private ObjectOutputStream socketOut;
	private ObjectInputStream socketIn;
	public Boolean clientOnline = true;

	public ClientHandler(Socket socket){
		try{
			userSocket = socket;
			this.socketIn = new ObjectInputStream (userSocket.getInputStream() );
			this.socketOut = new ObjectOutputStream (userSocket.getOutputStream() );
		} catch(IOException e){
			e.printStackTrace();
		}
	}
	@Override
	public void run(){
		while(clientOnline){
			Map<String,Object> income = null;
			try{
				income = (Map<String,Object>) socketIn.readObject();
				Map<String,Object> answer = null;
				Command command = (Command) income.get("command");
				//API works
				socketOut.writeObject(answer);
				socketOut.flush();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		try{
			socketIn.close();
			socketOut.close();
			userSocket.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}