package util;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashSet;

public interface DocumentControllerInterface extends Remote {
    String openDocument(String documentName, String user) throws RemoteException;

    void closeDocument(String documentName) throws RemoteException;

    boolean createDocument(String documentName, String owner) throws RemoteException;

    HashSet<String> getUserDocuments(String user) throws RemoteException;

    void registerNewUser(String username) throws RemoteException;
}
