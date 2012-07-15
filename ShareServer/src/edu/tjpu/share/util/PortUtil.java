package edu.tjpu.share.util;

public class PortUtil {
	private static int m_port = 10000;

	private PortUtil() {
	}

	public synchronized static int getPort() {
		if (m_port <= 10060) {
			m_port = m_port+1;
		}else{
			m_port = 10000;
		}
		return m_port;
	}
}
