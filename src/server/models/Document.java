package server.models;

import util.DocumentInterface;

import java.io.*;
import java.rmi.RemoteException;

public class Document implements DocumentInterface {

    final String path;
    private File file;
    private BufferedWriter bufferedWriter;

    public Document(String path) throws IOException {
        this.path = path;
        this.file = new File(path);
        if (!this.file.exists()) {
            //noinspection ResultOfMethodCallIgnored
            this.file.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(this.file);
        this.bufferedWriter = new BufferedWriter(fileWriter);
    }

    @Override
    public synchronized void write(String toWrite) throws RemoteException {
        try {
            this.bufferedWriter.write(toWrite);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public synchronized String read() throws RemoteException {
        try {
            FileReader fileReader = new FileReader(this.file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            return bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
