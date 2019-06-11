/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mnm
 */
public class io {

        public List<List<String>> readfromfile(File f) {
                List<List<String>> out = new ArrayList();
                BufferedReader br = null;
                try {
                        br = new BufferedReader(new FileReader(f));
                        String sCurrentLine;
                        boolean firstline=true;
                        while ((sCurrentLine = br.readLine()) != null) {
                                if(firstline){
                                        firstline=false;continue;
                                        
                                }
//                if (sCurrentLine.length() != 0) {
                                List<String> temp = new ArrayList<>();
                                for (String s : sCurrentLine.split(",")) {
                                        temp.add(s);
                                }
                                out.add(temp);
//                }
                        }
                        try {
                                if (br != null) {
                                        br.close();
                                }
                        } catch (IOException ex) {
                                ex.printStackTrace();
                        }
//            System.out.println("File robot8.txt Readed...." + out.size());
                } catch (IOException e) {
                        e.printStackTrace();
                } finally {
                        try {
                                if (br != null) {
                                        br.close();
                                }
                        } catch (IOException ex) {
                                ex.printStackTrace();
                        }
                }
                return out;
        }
}
