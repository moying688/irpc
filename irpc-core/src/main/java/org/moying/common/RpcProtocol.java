package org.moying.common;

import java.io.Serializable;

import static org.moying.common.constants.RpcConstants.MAGIC_NUMBER;

public class RpcProtocol implements Serializable {

    private static final long serialVersionUID = -1L;
    private short magicNumber  = MAGIC_NUMBER;
    private int contentLength;
    private byte[] content;

    public RpcProtocol(byte[] data) {
        this.contentLength = data.length;
        this.content = new byte[contentLength];
    }

    public short getMagicNumber() {
        return magicNumber;
    }

    public void setMagicNumber(short magicNumber) {
        this.magicNumber = magicNumber;
    }

    public int getContentLength() {
        return contentLength;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
