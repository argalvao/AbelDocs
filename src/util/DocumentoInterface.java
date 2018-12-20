package util;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface DocumentoInterface extends Remote{
	public boolean write() throws RemoteException;
}
