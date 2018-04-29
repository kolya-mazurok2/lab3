package lab3;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JFileChooser;

public class MyArray {
    private float [][] arr;
    private Scanner Sc = new Scanner(System.in);
    
    public MyArray(int n, int m) {
         setArr(new float[n][m]);
    }

    public float[][] getArr() {
        return arr;
    }

    public void setArr(float[][] arr) {
        this.arr = arr;
    }
    
    public String convertToString(float[][] arr) {
    	String str = "";
    	for(float [] i:arr) {
            for(float j:i) {
                str = str + "," + j;
            }
        }
    	return str.substring(1);
    }
    
    public void fillArr(int n, int m) {
         for(int i=0; i<n; i++) {
             for (int j=0; j<m; j++) {
                 arr[i][j] = (float) Math.random()*10;
             }
         }
    }
    
    public void fillArr(int n, int m, Scanner sc) {
        for(int i=0; i<n; i++) {
             for (int j=0; j<m; j++) {
                 if(sc.hasNextFloat())
                    arr[i][j] = sc.nextFloat();
             }
         }
    }
    
    public void fillArr(int n, int m, String fileName) {
        FileReader fr = null;
        try {
            Scanner in = new Scanner(new File(fileName));
            String s = "";
            
            while(in.hasNext())
                s += in.nextLine();
            
            in.close();
            
            String[] sArr = s.split(",");
            int k = 0;
            
            for(int i=0; i<n; i++) {
                for (int j=0; j<m; j++) {
                    if(sArr.length-1 < k)
                        arr[i][j] = 0;
                    else
                        arr[i][j] = Float.parseFloat(sArr[k]);
                    k++;
                }
            }
        }
        catch (IOException exc) {
            exc.printStackTrace();
        }
    }
    
    public void printArr() {
         for(float [] i:arr) {
             for(float j:i) {
                 System.out.print(j+" ");
             }
             System.out.println("");
         }
    }
}
