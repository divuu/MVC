package com.technomedia.digipark.server.socket;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;
import org.apache.catalina.websocket.WsOutbound;

@WebServlet(name = "SocketServlet", displayName = "SocketServlet", urlPatterns = { "/socketConn" }, description = "socket connection")
public class CommonSocketServlet extends WebSocketServlet {
	private static final long serialVersionUID = 1L;

	private static ArrayList<MyMessageInbound> mmiList = new ArrayList<MyMessageInbound>();
	private final ConcurrentHashMap<Integer, MyMessageInbound> connections = new ConcurrentHashMap<Integer, MyMessageInbound>();
	private final ConcurrentHashMap<Integer, String> sessionInfo = new ConcurrentHashMap<Integer, String>();
	private final AtomicInteger connectionIds = new AtomicInteger(0); // Unique
																		// id
																		// for
																		// each
																		// connection.

	// Access socket from different classes using instance
	public static CommonSocketServlet SocketInstance = null;

	private static Logger logger = Logger.getLogger(CommonSocketServlet.class
			.getName());

	@Override
	public void init() throws ServletException {
		super.init();
		SocketInstance = this;
	}

	@Override
	protected StreamInbound createWebSocketInbound(String protocol,
			HttpServletRequest req) {
		int Id = connectionIds.incrementAndGet();
		HttpSession sess = req.getSession(false);
		String sId = (String) sess.getId();

		logger.info("Id:" + Id + ", SId:" + sId);

		sessionInfo.put(Id, sId);
		return new MyMessageInbound(Id);
	}

	public int getConIdBySesId(String sId) {
		// get connection id using sid
		Enumeration<Integer> keys = sessionInfo.keys();
		int conId = 0;
		while (keys.hasMoreElements()) {
			int key = keys.nextElement();
			if (sessionInfo.get(key) == sId) {
				conId = key;
				break;
			}
		}

		return conId;
	}

	public boolean writeToSocket(String msg, int conId) {
		// get connection id using sid
		// get connection and write
		if (conId != 0) {
			MyMessageInbound curConn = connections.get(conId);

			CharBuffer response = CharBuffer.wrap(msg);
			try {
				curConn.myOutBound.writeTextMessage(response);

				return true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	// Class for message in bound
	private class MyMessageInbound extends MessageInbound {
		WsOutbound myOutBound;
		private final int id;

		private MyMessageInbound(int Id) {
			this.id = Id;
		}

		@Override
		public void onOpen(WsOutbound outbound) {
			try {
				logger.info("Open Client.");

				this.myOutBound = outbound;

				connections.put(id, this);

				mmiList.add(this);

				outbound.writeTextMessage(CharBuffer.wrap("Connected!"));

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onClose(int status) {
			logger.info("Close Client.");
			// clear instance
			mmiList.remove(this);
			connections.remove(id);
			sessionInfo.remove(id);
		}

		@Override
		public void onTextMessage(CharBuffer cb) throws IOException {
			logger.info("Accept Message : " + cb);
			// may latter use for command like update etc

		}

		@Override
		public void onBinaryMessage(ByteBuffer bb) throws IOException {
			logger.info("Accept Message : " + bb);
		}
	}

}
