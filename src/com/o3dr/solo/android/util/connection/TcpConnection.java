package com.o3dr.solo.android.util.connection;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;

public class TcpConnection extends AbstractIpConnection
{
  private BufferedInputStream connIn;
  private BufferedOutputStream connOut;
  private final String serverIp;
  private final int serverPort;
  private Socket socket;

  public TcpConnection(String paramString, int paramInt)
  {
    this.serverIp = paramString;
    this.serverPort = paramInt;
  }

  protected void close()
    throws IOException
  {
    if (this.socket != null)
      this.socket.close();
  }

  protected void open()
    throws IOException
  {
    InetAddress localInetAddress = InetAddress.getByName(this.serverIp);
    this.socket = new Socket();
    this.socket.setReuseAddress(true);
    this.socket.connect(new InetSocketAddress(localInetAddress, this.serverPort), 15000);
    this.connOut = new BufferedOutputStream(this.socket.getOutputStream());
    this.connIn = new BufferedInputStream(this.socket.getInputStream());
  }

  protected int read(ByteBuffer paramByteBuffer)
    throws IOException
  {
    return this.connIn.read(paramByteBuffer.array());
  }

  protected void send(AbstractIpConnection.PacketData paramPacketData)
    throws IOException
  {
    this.connOut.write(paramPacketData.data, 0, paramPacketData.dataLength);
    this.connOut.flush();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.util.connection.TcpConnection
 * JD-Core Version:    0.6.2
 */