package mchi.csie.ntu;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.EditText;

class SocketListener implements Runnable {
	
	public static final String TAG = "WIFIDev";
	public static final int SOCKET_LISTENER_MSG = 123456789;
	private int serverPort;
	final int ET_OUTPUT = 99999;
	final Handler mainHandler;

	public SocketListener(Handler mHandler, int port) {
		// TODO Auto-generated constructor stub
		mainHandler = mHandler;
		serverPort = port;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Log.i(TAG, "+SocketListener()");
		try {
			// establish server socket
			int connIndex = 0;
			ServerSocket serverSocket = new ServerSocket(serverPort);
			Log.i(TAG, "port:" + serverSocket.getLocalPort());

			while (true) {
				Socket incoming = serverSocket.accept();
				Log.e(TAG, "Connected a client!connIndex:"
								+ connIndex
								+ " RemoteSocketAddress:"
								+ String.valueOf(incoming.getRemoteSocketAddress()));
				Thread connHandle = new Thread(new LinkHandler(mainHandler, incoming));
				connHandle.start();
				connIndex++;
				
				if(connIndex > 3) {
					serverSocket.close();
					break;
				}
			}
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Log.i(TAG, "-SocketListener()");
		
	}
}



