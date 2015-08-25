package com.o3dr.services.android.lib.drone.connection;

public class ConnectionType
{
  public static final int DEFAULT_TCP_SERVER_PORT = 5763;
  public static final long DEFAULT_UDP_PING_PERIOD = 10000L;
  public static final int DEFAULT_UDP_SERVER_PORT = 14550;
  public static final int DEFAULT_USB_BAUD_RATE = 57600;
  public static final String EXTRA_BLUETOOTH_ADDRESS = "extra_bluetooth_address";
  public static final String EXTRA_TCP_SERVER_IP = "extra_tcp_server_ip";
  public static final String EXTRA_TCP_SERVER_PORT = "extra_tcp_server_port";
  public static final String EXTRA_UDP_PING_PAYLOAD = "extra_udp_ping_payload";
  public static final String EXTRA_UDP_PING_PERIOD = "extra_udp_ping_period";
  public static final String EXTRA_UDP_PING_RECEIVER_IP = "extra_udp_ping_receiver_ip";
  public static final String EXTRA_UDP_PING_RECEIVER_PORT = "extra_udp_ping_receiver_port";
  public static final String EXTRA_UDP_SERVER_PORT = "extra_udp_server_port";
  public static final String EXTRA_USB_BAUD_RATE = "extra_usb_baud_rate";
  public static final int TYPE_BLUETOOTH = 3;
  public static final int TYPE_TCP = 2;
  public static final int TYPE_UDP = 1;
  public static final int TYPE_USB;
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.services.android.lib.drone.connection.ConnectionType
 * JD-Core Version:    0.6.2
 */