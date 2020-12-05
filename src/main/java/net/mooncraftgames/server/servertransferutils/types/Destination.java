package net.mooncraftgames.server.servertransferutils.types;

import net.mooncraftgames.server.servertransferutils.ServerTransferConstants;

public final class Destination {

    private String address;
    private Integer port;

    public Destination(){ this(null, null); }
    public Destination(String address) { this(address, null); }
    public Destination(String address, Integer port) {
        this.setAddress(address);
        this.setPort(port);
    }

    public void resetAddress() { this.address = ServerTransferConstants.DEFAULT_ADDRESS; }
    public void resetPort() { this.port = ServerTransferConstants.DEFAULT_PORT; }

    public void setAddress(String address) {
        if(address == null) {
            resetAddress();
        } else {
            this.address = address;
        }
    }

    public void setPort(Integer port) {
        if(port == null){
            resetPort();
        } else {
            this.port = port;
        }
    }

    public String getAddress() { return address; }
    public Integer getPort() { return port; }
}
