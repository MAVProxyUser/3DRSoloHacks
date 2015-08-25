package com.o3dr.solo.android.util.connection;

import android.util.Log;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

public class UdpConnection extends AbstractIpConnection
{
  private static final String TAG = UdpConnection.class.getSimpleName();
  private InetAddress hostAddress;
  private int hostPort;
  private DatagramPacket receivePacket;
  private DatagramPacket sendPacket;
  private final int serverPort;
  private DatagramSocket socket;

  public UdpConnection(int paramInt1, int paramInt2)
  {
    super(paramInt2);
    this.serverPort = paramInt1;
  }

  public UdpConnection(String paramString, int paramInt1, int paramInt2)
    throws UnknownHostException
  {
    super(false, true);
    this.serverPort = paramInt2;
    this.hostPort = paramInt1;
    this.hostAddress = InetAddress.getByName(paramString);
  }

  protected void close()
    throws IOException
  {
    Log.d(TAG, "Closing udp connection.");
    if (this.socket != null)
      this.socket.close();
  }

  protected void open()
    throws IOException
  {
    Log.d(TAG, "Opening udp connection.");
    if (this.serverPort == -1);
    for (DatagramSocket localDatagramSocket = new DatagramSocket(); ; localDatagramSocket = new DatagramSocket(this.serverPort))
    {
      this.socket = localDatagramSocket;
      this.socket.setBroadcast(true);
      this.socket.setReuseAddress(true);
      this.socket.setSoTimeout(15000);
      return;
    }
  }

  protected int read(ByteBuffer paramByteBuffer)
    throws IOException
  {
    if (this.receivePacket == null)
      this.receivePacket = new DatagramPacket(paramByteBuffer.array(), paramByteBuffer.capacity());
    this.socket.receive(this.receivePacket);
    this.hostAddress = this.receivePacket.getAddress();
    this.hostPort = this.receivePacket.getPort();
    return this.receivePacket.getLength();
  }

  protected void send(AbstractIpConnection.PacketData paramPacketData)
    throws IOException
  {
    if (this.hostAddress != null)
    {
      if (this.sendPacket == null)
        this.sendPacket = new DatagramPacket(paramPacketData.data, paramPacketData.dataLength, this.hostAddress, this.hostPort);
      while (true)
      {
        this.socket.send(this.sendPacket);
        return;
        this.sendPacket.setData(paramPacketData.data, 0, paramPacketData.dataLength);
        this.sendPacket.setAddress(this.hostAddress);
        this.sendPacket.setPort(this.hostPort);
      }
    }
    Log.w(TAG, "Still awaiting connection from remote host.");
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.util.connection.UdpConnection
 * JD-Core Version:    0.6.2
 */