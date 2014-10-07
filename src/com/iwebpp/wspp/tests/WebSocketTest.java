package com.iwebpp.wspp.tests;

import com.iwebpp.node.NodeContext;
import com.iwebpp.node.stream.Writable.WriteCB;
import com.iwebpp.wspp.Sender;
import com.iwebpp.wspp.WebSocket;
import com.iwebpp.wspp.WebSocket.MessageEvent;
import com.iwebpp.wspp.WebSocket.OpenEvent;
import com.iwebpp.wspp.WebSocket.onmessageListener;
import com.iwebpp.wspp.WebSocket.onopenListener;

import android.util.Log;


public final class WebSocketTest {
	private static final String TAG = "WebSocketTest";
	
	private NodeContext ctx;

	private boolean testConnect() throws Exception {

		final WebSocket ws = new WebSocket(ctx, "ws://192.188.1.100:6668", null, new WebSocket.Options());

		ws.onopen(new onopenListener(){

			@Override
			public void onOpen(OpenEvent event) throws Exception {
				Log.d(TAG, "ws opened");	

				// send message
				ws.send("Hello, node-andord", new Sender.SendOptions(false, false, false), new WriteCB(){

					@Override
					public void writeDone(String error) throws Exception {
						Log.d(TAG, "send done");						
					}

				});
			}

		});

		ws.onmessage(new onmessageListener(){

			@Override
			public void onMessage(MessageEvent event) throws Exception {
				Log.d(TAG, "message: "+event.toString());				
			}

		});

		return true;
	}

	public WebSocketTest(){
		this.ctx = new NodeContext(); 
	}
	
	public void start() {		
		(new Thread(new Runnable() {
			public void run() {
				Log.d(TAG, "start test");

				try {
					testConnect();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			    
			}
		})).start();

	}
}
