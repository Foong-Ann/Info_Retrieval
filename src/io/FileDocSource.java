package io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 * @author loofoong
 */
public class FileDocSource extends DocSource{
    //first you need a constructor to take a file path as name is a string
    //useFileFinder to return a array of files
    
    private ArrayList<String> _files;//cannot load into memory, so add names
    public FileDocSource(String path) {
        _files = new ArrayList<String>();
        for (File f : FileFinder.GetAllFiles(path)){
            _files.add(f.getAbsolutePath());//simply store path
        }
        
    }
    
    @Override
    public int getNumDocs() {
        return _files.size();//return number of files found
    }

    @Override
    public String getDoc(int id) {
        BufferedReader cin;//use bufferedreader to read file and 
        StringBuilder sb = new StringBuilder();
        String s = null;//create a new empty string to add content
        String result = null;//create a new empty string for return
        try{
            cin = new BufferedReader(new FileReader(_files.get(id)));
            while((s = cin.readLine())!= null){
                sb.append(s + " ");//add content to one string and insert a space between consecutive lines to ensure word break
            }
            result = sb.toString();
            cin.close();//close the BufferedReader
        }catch (FileNotFoundException e){
               System.out.println("ERROR! File not found");
        }catch(IOException e){
                System.out.println("ERROR! Cannot access files");
        }
        return result;
    }
    
}
