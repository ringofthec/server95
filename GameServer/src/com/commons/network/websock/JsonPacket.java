package com.commons.network.websock;

public abstract class JsonPacket {
	public int getProtocolId() {return -1;}
	public abstract String json();
}
