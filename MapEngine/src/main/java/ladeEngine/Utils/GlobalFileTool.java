package ladeEngine.Utils;

import java.io.*;
import java.util.ArrayList;

public class GlobalFileTool {
    public static void write(File index,ArrayList<String> init){
        try{
            if(!index.exists()){
                index.createNewFile();
            }
            FileWriter fw = new FileWriter(index);
            BufferedWriter bw = new BufferedWriter(fw);
            for (int i = 0; i < init.size(); i++) {
                bw.write(init.get(i));
                bw.newLine();
            }
            bw.close();
            fw.close();
        }catch(IOException e){
        }
    }

    public static ArrayList<String> read(File index){
        try{
            FileReader fr = new FileReader(index);
            BufferedReader br = new BufferedReader(fr);
            ArrayList<String> init = new ArrayList<>();
            String tmp = null;
            while((tmp = br.readLine()) != null){
                init.add(tmp);
            }
            fr.close();
            br.close();
            return init;
        }catch(IOException e){
        }
        return null;
    }
}
