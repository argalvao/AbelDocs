package util;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface DocumentInterface extends Remote{
	public void write(String toWrite) throws RemoteException;
}
