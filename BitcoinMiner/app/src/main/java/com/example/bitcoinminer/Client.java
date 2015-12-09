package com.example.bitcoinminer;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;
import org.zeromq.ZMQ.PollItem;
import org.zeromq.ZMQ.Poller;

/**
 * Created by wes on 12/9/15.
 */
public class Client implements Runnable{
	String id;
	String addr;
	String port;
	public Client(String id, String addr, String port){
		this.id=id;
		this.addr=addr;
		this.port=port;
	}
    @Override
    public void run() {
        ZContext ctx =  new ZContext();
		//Socket to connect to server
		Socket client = ctx.createSocket(ZMQ.DEALER);
		client.setIdentity(id.getBytes());
		//Change to proper address/port
		client.connect("tcp://localhost:8080");

		//Socket to connect to our app
		Socket app=ctx.createSocket(ZMQ.REP);
		//Also change to proper port
		app.connect("tcp://localhost:8081");

		PollItem[] items = new PollItem[] {
			new PollItem(client, Poller.IN)
			new PollItem(app, Poller.IN)};
		Boolean stopGo=true;
		while(stopGo){
			ZMQ.poll(items, 100);
			if(items[0].isReadable()){
				ZMsg msg = ZMsg.recvMsg(client);
				//Do stuff
				msg.destroy();
			}
			else if(items[1].isReadable()){
				//If we get anything from app socket, we treat it as a command to stop
				stopGo=false;
			}
		}
    }
}
