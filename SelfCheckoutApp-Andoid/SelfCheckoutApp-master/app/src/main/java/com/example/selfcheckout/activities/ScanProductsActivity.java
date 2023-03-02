package com.example.selfcheckout.activities;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.selfcheckout.ApiParserFactory;
import com.example.selfcheckout.ChartItem;
import com.example.selfcheckout.CustomAdapterForItems;
import com.example.selfcheckout.IApiParser;
import com.example.selfcheckout.LocalStorageShoppingList;
import com.example.selfcheckout.PaymentConfig.CheckoutActivity;
import com.example.selfcheckout.data.ProductData;
import com.example.selfcheckout.R;
import com.example.selfcheckout.data.SupermarketData;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


public class ScanProductsActivity extends AppCompatActivity{
    private static String TAG = "ScanProductsActivity";
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    private SurfaceView surfaceView;
    private ToneGenerator toneGen1;
    public String barcodeData;
    String barcodeCopy;
    ApiParserFactory apiParserFactory = new ApiParserFactory();
    IApiParser apiParser;
    TextView name;
    TextView price;
    Button productPopUpButton;
    ListView listView;
    List<ProductData> productList = new ArrayList<>();
    int indexProductList = 0;
    private final String fileForNames = "shoppingList.txt";
    private final String fileForUser = "userAndStore.txt";
    private final String fileForPrices = "prices.txt";
    public static ArrayList<ChartItem> chartItem = new ArrayList<>();
    private static CustomAdapterForItems adapter;
    public static String[] structOfProduct;
    public static String[] initialPrice;
    public static int subtotal = 0;
    static TextView subtotalText;
    Button payButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if(!readData(fileForUser).equals(""))
        {
            String[] userAndSupermarket = readData(fileForUser).split(" ");
            if(!userAndSupermarket[0].equals(LoginActivity.token)  || !userAndSupermarket[1].subSequence(0, userAndSupermarket[1].length()-1).equals(AllSupermarketsActivity.supermarketName) || CheckoutActivity.flagForPayment)
            {
                writeDataWithoutNewLine("", fileForNames,Context.MODE_PRIVATE);
                chartItem.clear();
                subtotal = 0;
                currentListOfItems = null;
            }
        }
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_scan_products);
        listView =(ListView) findViewById(R.id.listItems);
        toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC,     100);
        surfaceView = findViewById(R.id.surfaceView);
        payButton = findViewById(R.id.payButton);
        subtotalText = findViewById(R.id.subtotal);


        detectBarcode();
        writeData(LoginActivity.token + " " + AllSupermarketsActivity.supermarketName, fileForUser,Context.MODE_PRIVATE );
        apiParser = apiParserFactory.getApiParser(AllSupermarketsActivity.dataFormat);
        for(int i = 0; i < chartItem.size();i++)
        {
            if(i == 0)
            {
                if(initialPrice != null)
                {
                    writeData(chartItem.get(i).getName() + "," + initialPrice[i] + "," + chartItem.get(i).getPriceForSelectedQuantity(), fileForNames,Context.MODE_PRIVATE);
                }else
                {
                    writeData(chartItem.get(i).getName() + "," + chartItem.get(i).getPrice() + "," + chartItem.get(i).getPriceForSelectedQuantity(), fileForNames,Context.MODE_PRIVATE);

                }
            }
            else
            {
                if(initialPrice != null)
                {
                    writeData(chartItem.get(i).getName() + "," + initialPrice[i] + "," + chartItem.get(i).getPriceForSelectedQuantity(), fileForNames,Context.MODE_APPEND);
                }else
                {
                    writeData(chartItem.get(i).getName() + "," + chartItem.get(i).getPrice() + "," + chartItem.get(i).getPriceForSelectedQuantity(), fileForNames,Context.MODE_APPEND);

                }            }
        }
        if(!readData(fileForNames).equals(""))
        {
            subtotal = 0;
            currentListOfItems = readData(fileForNames).split("\n");
            chartItem.clear();
            initialPrice = new String[currentListOfItems.length];
            for(int i = 0; i < currentListOfItems.length;i++)
            {
                structOfProduct = currentListOfItems[i].split(",");
                initialPrice[i] = structOfProduct[structOfProduct.length-2];
                subtotal += Float.parseFloat(structOfProduct[2]);
                float quantity = Float.parseFloat(structOfProduct[structOfProduct.length-1])/Float.parseFloat(structOfProduct[structOfProduct.length-2]);
                chartItem.add(new ChartItem(structOfProduct[structOfProduct.length-3], quantity,Float.parseFloat(structOfProduct[structOfProduct.length-1])));
            }
            adapter = new CustomAdapterForItems(chartItem, ScanProductsActivity.this);
            listView.setAdapter(adapter);

        }

       payButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(ScanProductsActivity.this, CheckoutActivity.class);
               startActivity(intent);
           }
       });

       setSubtotal();

    }
public static void setSubtotal(){

    subtotalText.setText(("Subtotal: " + subtotal));


}
    public void detectBarcode(){
        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true)
                .build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(ScanProductsActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(surfaceView.getHolder());
                    } else {
                        ActivityCompat.requestPermissions(ScanProductsActivity.this, new
                                String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }
            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();

                if (barcodes.size() != 0) {
                    surfaceView.post(new Runnable() {
                        @Override
                        public void run() {
                            while(barcodeData == null)
                            {
                                barcodeData = barcodes.valueAt(0).displayValue;
                                barcodeCopy = barcodeData;
                                toneGen1.startTone(ToneGenerator.TONE_CDMA_PIP, 150);
                            }

                        }
                    });
                }

                if(barcodeCopy != null)
                {
                    ProductData product = apiParser.getProduct(barcodeCopy);
                    new Handler(Looper.getMainLooper()).post(new Runnable() {

                        @Override
                        public void run() {
                            popUp(product);
                            productList.add(product);
                        }
                    });

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    barcodeCopy = null;
                }
            }
        });

    }
    public String[] currentListOfItems;
    public String[] currentListOfPrices;
    public void popUp( ProductData product)
    {
        if(product == null || product.getName() == null)
    {
        View alertCustomDialog = LayoutInflater.from(ScanProductsActivity.this).inflate(R.layout.custom_dialog,null);
        name = (TextView) alertCustomDialog.findViewById(R.id.productNameText);
        name.setText("Product unavailable");
        productPopUpButton = (Button) alertCustomDialog.findViewById(R.id.productPopUpButton);
        AlertDialog.Builder alert = new AlertDialog.Builder(ScanProductsActivity.this);
        alert.setView(alertCustomDialog);
        final AlertDialog dialog = alert.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        productPopUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                barcodeData = null;
                indexProductList++;
                dialog.dismiss();
                idx++;
            }
        });
    }
        else
        {
            View alertCustomDialog = LayoutInflater.from(ScanProductsActivity.this).inflate(R.layout.custom_dialog,null);
            name = (TextView) alertCustomDialog.findViewById(R.id.productNameText);
            name.clearComposingText();
            name.setText(product.getName());
            price = (TextView) alertCustomDialog.findViewById(R.id.productPrice);
            price.setText(Integer.toString((int)product.getPrice())+ " LEI");
            writeData(name.getText().toString() + "," + ((int)product.getPrice()) + "," + ((int)product.getPrice())  ,fileForNames, Context.MODE_APPEND);
            productPopUpButton = (Button) alertCustomDialog.findViewById(R.id.productPopUpButton);
            AlertDialog.Builder alert = new AlertDialog.Builder(ScanProductsActivity.this);
            alert.setView(alertCustomDialog);

            final AlertDialog dialog = alert.create();
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
            productPopUpButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    barcodeData = null;
                    indexProductList++;
                    dialog.dismiss();
                    productsListView();
                    idx++;
                    subtotal += product.getPrice();
                    setSubtotal();
                }
            });
        }


    }

    @SuppressLint("ClickableViewAccessibility")
    public void productsListView()
    {
        currentListOfItems = readData(fileForNames).split("\n");
        String[] structOfProduct = new String[currentListOfItems.length*3];
        initialPrice = new String[currentListOfItems.length];
        for(int i = 0; i < currentListOfItems.length;i++)
        {
            structOfProduct = currentListOfItems[i].split(",");
            initialPrice[i] = structOfProduct[1];
        }
        structOfProduct = currentListOfItems[currentListOfItems.length - 1].split(",");
        currentListOfPrices = readData(fileForPrices).split("\n");

        chartItem.add(new ChartItem(structOfProduct[0], 1,Integer.parseInt(structOfProduct[1])));

        adapter = new CustomAdapterForItems(chartItem, ScanProductsActivity.this);
        listView.setAdapter(adapter);
        for(int i = 0; i < chartItem.size();i++)
        {
            if(i == 0)
            {
                if(initialPrice != null)
                {
                    writeData(chartItem.get(i).getName() + "," + initialPrice[i] + "," + chartItem.get(i).getPriceForSelectedQuantity(), fileForNames,Context.MODE_PRIVATE);
                }else
                {
                    writeData(chartItem.get(i).getName() + "," + chartItem.get(i).getPrice() + "," + chartItem.get(i).getPriceForSelectedQuantity(), fileForNames,Context.MODE_PRIVATE);

                }
            }
            else
            {
                if(initialPrice != null)
                {
                    writeData(chartItem.get(i).getName() + "," + initialPrice[i] + "," + chartItem.get(i).getPriceForSelectedQuantity(), fileForNames,Context.MODE_APPEND);
                }else
                {
                    writeData(chartItem.get(i).getName() + "," + chartItem.get(i).getPrice() + "," + chartItem.get(i).getPriceForSelectedQuantity(), fileForNames,Context.MODE_APPEND);

                }            }
        }
        if(!readData(fileForNames).equals(""))
        {

            currentListOfItems = readData(fileForNames).split("\n");
            chartItem.clear();
            initialPrice = new String[currentListOfItems.length];
            for(int i = 0; i < currentListOfItems.length;i++)
            {
                structOfProduct = currentListOfItems[i].split(",");
                initialPrice[i] = structOfProduct[structOfProduct.length-2];
                //subtotal += Float.parseFloat(structOfProduct[structOfProduct.length-2]);
                float quantity = Float.parseFloat(structOfProduct[structOfProduct.length-1])/Float.parseFloat(structOfProduct[structOfProduct.length-2]);
                chartItem.add(new ChartItem(structOfProduct[structOfProduct.length-3], quantity,Float.parseFloat(structOfProduct[structOfProduct.length-1])));
            }
            adapter = new CustomAdapterForItems(chartItem, ScanProductsActivity.this);
            listView.setAdapter(adapter);
        }

        //subtotalText.setText(String.valueOf(subtotal));

    }

    public void writeData(String text, String filename,int context)
    {
        try
        {
            FileOutputStream fos = openFileOutput(filename,context);
            text += "\n";
            fos.write(text.getBytes());
            fos.flush();
            fos.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void writeDataWithoutNewLine(String text, String filename,int context)
    {
        try
        {
            FileOutputStream fos = openFileOutput(filename,context);
            text += "";
            fos.write(text.getBytes());
            fos.flush();
            fos.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
    int idx = 0;
    public String readData(String filename)
    {
        String currentShoppingList ="";
        try
        {
            FileInputStream fin = openFileInput(filename);
            int a;
            StringBuilder temp = new StringBuilder();
            while ((a = fin.read()) != -1)
            {
                temp.append((char)a);
            }
            currentShoppingList = temp.toString();
            fin.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        Log.e("CURRENT ITEM" , currentShoppingList);
        return currentShoppingList;
    }

    //////////////////////
//    JavaCameraView javaCameraView;
//    Mat mRGB;
//    Mat gray;
//    Mat gradientX;
//    Mat gradientY;
//    BaseLoaderCallback myLoaderCallback = new BaseLoaderCallback(this) {
//        @Override
//        public void onManagerConnected(int status) {
//            switch(status){
//                case BaseLoaderCallback.SUCCESS:{
//                    javaCameraView.enableView();
//                    break;
//                }
//                default:{
//                    super.onManagerConnected(status);
//                    break;
//                }
//            }
//
//        }
//    };
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_scan_products);
//        javaCameraView = findViewById(R.id.camera);
//        ActivityCompat.requestPermissions(ScanProductsActivity.this,
//                new String[]{Manifest.permission.CAMERA},
//                1);
//        javaCameraView.setVisibility(SurfaceView.VISIBLE);
//        javaCameraView.setCameraPermissionGranted();
//        javaCameraView.setCvCameraViewListener(this);
//        if(mRGB != null){
//            detectBarcode(mRGB);
//        }
//
//    }
//
//        @Override
//        protected void onPause () {
//            super.onPause();
//            if (javaCameraView != null) {
//                javaCameraView.disableView();
//            }
//        }
//
//        @Override
//        protected void onDestroy () {
//
//            super.onDestroy();
//            if (javaCameraView != null) {
//                javaCameraView.disableView();
//            }
//        }
//        @Override
//        protected void onResume () {
//
//            super.onResume();
//            if (OpenCVLoader.initDebug()) {
//                System.out.println("OpenCV loaded successfully");
//                myLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
//            } else {
//                System.out.println("OpenCV not loaded");
//                OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION, this, myLoaderCallback);
//            }
//        }
//
//        @Override
//        public void onCameraViewStarted ( int width, int height){
//            mRGB = new Mat(height, width, CvType.CV_8UC4);
//            gray = new Mat(height, width, CvType.CV_8UC1);
//            gradientX = new Mat(height, width, CvType.CV_8UC1);
//            gradientY = new Mat(height, width, CvType.CV_8UC1);
//        }
//
//        @Override
//        public void onCameraViewStopped () {
//            mRGB.release();
//        }
//
//        @Override
//        public Mat onCameraFrame (CameraBridgeViewBase.CvCameraViewFrame inputFrame){
//            mRGB = inputFrame.rgba();
//            Mat gradient = new Mat();
//            Mat gradientAbs = new Mat();
//            Mat blurred = new Mat();
//            Mat threshold = new Mat(mRGB.height(), mRGB.width(), CvType.CV_8UC1);
//            Imgproc.cvtColor(mRGB,gray,Imgproc.COLOR_RGB2GRAY);
//            Imgproc.Sobel(gray, gradientX, CvType.CV_64F, 1, 0,-1);
//            Imgproc.Sobel(gray, gradientY, CvType.CV_64F, 0, 1,-1);
//            Core.subtract(gradientX,gradientY,gradient);
//            Core.convertScaleAbs(gradient,gradientAbs);
//            Imgproc.medianBlur(gradientAbs,blurred,9);
//            Imgproc.threshold(blurred,threshold,225,255,Imgproc.THRESH_BINARY);
//
//            Mat se = Imgproc.getStructuringElement(Imgproc.MORPH_RECT,new Size(100,7));
//            Mat closedBarcode = new Mat(mRGB.height(), mRGB.width(), CvType.CV_8UC1);
//            Imgproc.morphologyEx(threshold,closedBarcode,Imgproc.MORPH_CLOSE,se);
//
//            Imgproc.erode(closedBarcode, closedBarcode, Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(100,7)));
//            Imgproc.dilate(closedBarcode,closedBarcode,Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(100,7)));
//            final List<MatOfPoint> points = new ArrayList<>();
//            final Mat hierarchy = new Mat();
//            Imgproc.findContours(closedBarcode,points,hierarchy,Imgproc.RETR_EXTERNAL,Imgproc.CHAIN_APPROX_SIMPLE);
//
//            for (int i = 0; i < points.size(); i++) {
//                Imgproc.drawContours(mRGB,points,i,new Scalar(0,255,0));
//            }
//            return mRGB;
//        }
//        public void detectBarcode(Mat mRGB){
//
//
//
//        }
}
