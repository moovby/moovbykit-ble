package com.moovby.kit.bluetooth;

public interface OnReceiveMessageListener extends IErrorListener, IConnectionLostListener {


    void onNewLine(String s);
}
