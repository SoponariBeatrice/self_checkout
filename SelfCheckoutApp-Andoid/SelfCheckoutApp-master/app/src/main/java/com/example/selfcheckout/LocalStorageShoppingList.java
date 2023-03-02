package com.example.selfcheckout;

import android.content.Context;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class LocalStorageShoppingList {
    private final String filename = "shoppingList.txt";
//    public void writeData(String text)
//    {
//        try
//        {
//            FileOutputStream fos = openFileOutput(filename,Context.MODE_PRIVATE);
//            String data = text;
//            fos.write(data.getBytes());
//            fos.flush();
//            fos.close();
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//    public void readData(TextView listOfShoppingItems)
//    {
//
//        try
//        {
//            FileInputStream fin = new FileInputStream(filename);
//            int a;
//            StringBuilder temp = new StringBuilder();
//            while ((a = fin.read()) != -1)
//            {
//                temp.append((char)a);
//            }
//
//            StringBuilder stringBuilder = new StringBuilder();
//            stringBuilder = (StringBuilder) listOfShoppingItems.getText();
//            stringBuilder.append(temp.toString());
//            listOfShoppingItems.setText(stringBuilder);
//            fin.close();
//        }
//        catch (IOException e)
//        {
//            e.printStackTrace();
//        }
//
//    }

}
