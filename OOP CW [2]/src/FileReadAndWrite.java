import java.io.*;
import java.util.ArrayList;

public class FileReadAndWrite {

    public void getSavedData(String fileName, ArrayList<FileReadAndWrite> arrayList){
        try {
            FileInputStream inputStream = new FileInputStream(fileName + ".ser");
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            arrayList = (ArrayList<FileReadAndWrite>)objectInputStream.readObject();
            objectInputStream.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setData(String fileName, ArrayList<FootballClub> arrayList){
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(fileName + ".ser", false);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(arrayList);
            objectOutputStream.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
