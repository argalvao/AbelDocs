package util;

import server.models.Document;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface DocumentControllerInterface extends Remote {
    public String openDocument(String documentName, String user) throws RemoteException;

    public void closeDocument(String documentName) throws RemoteException;

    public boolean createDocument(String documentName, String owner) throws RemoteException;
}
