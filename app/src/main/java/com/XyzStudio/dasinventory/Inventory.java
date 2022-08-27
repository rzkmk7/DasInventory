package com.XyzStudio.dasinventory;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Inventory extends AppCompatActivity {

    FloatingActionButton fab_add_inventory;
    FloatingActionButton fab_add_scan;
    ImageButton invHome;
    ImageButton delAllInv;
    ImageButton refInv;

    ListView listView;
    AdapterInventory adapter;
    public static ArrayList<InventoryData> inventoryArrayList = new ArrayList<>();
    Context context;

    public FirebaseDatabase database;
    public DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Inventory");

        context = this;
        fab_add_inventory = findViewById(R.id.fab_add_inventory);
        invHome = findViewById(R.id.invHome);
        delAllInv =  findViewById(R.id.delAllInv);
        refInv =  findViewById(R.id.refInv);
        fab_add_scan = findViewById(R.id.fab_add_scan);

        adapter = new AdapterInventory(this, inventoryArrayList, new AdapterInventory.OnClickListener() {

            /// button click untuk item list konsepnya adapter ngasih tau tap location dengan mListener ke dalam posisi click >lalu diterima oleh inventory dengan posisi integer msg
            @Override
            public void onClick(Integer msg) {
                Log.d("asd", inventoryArrayList.get(msg).getNamaBarang());
                FragItemInv fragItemInv = new FragItemInv(
                        inventoryArrayList.get(msg).getNamaBarang(),
                        inventoryArrayList.get(msg).getJmlStok(),
                        inventoryArrayList.get(msg).getType(),
                        inventoryArrayList.get(msg).getKet(),
                        inventoryArrayList.get(msg).getDate(),
                        inventoryArrayList.get(msg).getStokAkhir(),
                        inventoryArrayList.get(msg).getKey()                        );
                fragItemInv.show(getSupportFragmentManager(), "activity_frag_item_inventory");
            }
        }, new AdapterInventory.OnClickListenerDel(){
            @Override
            public void onClick(Integer msg){
                ref.child(inventoryArrayList.get(msg).getKey()).removeValue();
                finish();
            }
        }, new AdapterInventory.OnClickListenerHistory() {
            @Override
            public void onClick(Integer msg) {
                startActivity(new Intent(Inventory.this,InvHistory.class));
                Log.d("asd", "Kontol");
            }
        }

        );
        adapter.arrayListInventory.clear();

        listView = findViewById(R.id.ListInventory);
        listView.setAdapter(adapter);


        fab_add_inventory.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                /* startActivity(new Intent(Inventory.this,FormInventory.class));*/
                FormInventory formInventory = new FormInventory();
                formInventory.show(getSupportFragmentManager(), "activity_form_inventory");
            }
        });
        fab_add_scan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                scanCode();
            }
        });
        invHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Inventory.this,Home.class));
            }
        });

       delAllInv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("kliked","s");
                AlertDialog.Builder builder = new AlertDialog.Builder(Inventory.this);
                builder.setTitle("Do this action");
                builder.setMessage("delete semua data?");
                builder.setPositiveButton("YES", (dialog, which) -> {
                    // Do do my action here
                    ref.removeValue();
                    finish();
                    overridePendingTransition(1, 1);
                    startActivity(getIntent());
                    overridePendingTransition(1, 1);
                });

                builder.setNegativeButton("NO", (dialog, which) -> {
                    // I do not need any action here you might
                    dialog.dismiss();
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });


        refInv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                searchByName("Jaa");
//                finish();
//                overridePendingTransition(1, 1);
//                startActivity(getIntent());
//                overridePendingTransition(1, 1);

                exportDataIntoWorkbook(context, "test.xlsx", inventoryArrayList);
            }
        });
        getData();
    }
    public void getData(){
        inventoryArrayList.clear();
        ref.addChildEventListener(new ChildEventListener(){
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.d("tag",snapshot.getKey());
                InventoryData inventory = snapshot.getValue(InventoryData.class);
                inventory.setKey(snapshot.getKey());
                inventoryArrayList.add(inventory);
                adapter.notifyDataSetChanged(); //teeuingggg

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void scanCode()
    {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Volume up to flash on");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLaucher.launch(options);
    }

    ActivityResultLauncher<ScanOptions> barLaucher = registerForActivityResult(new ScanContract(), result->
    {
        if(result.getContents() !=null)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(Inventory.this);
            builder.setTitle("Result");
            try {
                JSONObject obj = new JSONObject(result.getContents());
//                Log.d("testa", obj.getString("id"));
                String key = obj.getString("id");
                ref.child(key).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.e("testa", "Error getting data", task.getException());
                        }
                        else {
                            String namaBarang = task.getResult().child("namaBarang").getValue().toString();
                            String jmlStok = task.getResult().child("namaBarang").getValue().toString();
                            String ket = task.getResult().child("ket").getValue().toString();
                            String type = task.getResult().child("type").getValue().toString();
                            String date = task.getResult().child("date").getValue().toString();
                            String stokAkhir = task.getResult().child("stokAkhir").getValue().toString();

                            FragItemInv fragItemInv = new FragItemInv(
                                    namaBarang,
                                    jmlStok,
                                    type,
                                    ket,
                                    date,
                                    stokAkhir,
                                    key);
                            fragItemInv.show(getSupportFragmentManager(), "activity_frag_item_inventory");


//                            Log.d("testa", String.valueOf(task.getResult().child("namaBarang").getValue()));
                        }
                    }
                });

//                String namaBarang = obj.getString("namaBarang");
//                String jmlStok = obj.getString("jmlStok");
//                String ket = obj.getString("ket");
//                String type = obj.getString("type");
//                String date = obj.getString("date");
//                String stokAkhir = obj.getString("stokAkhir");

//                InventoryData data = new InventoryData(namaBarang, jmlStok, type, ket, date, stokAkhir);
//
//                DatabaseReference database = FirebaseDatabase.getInstance().getReference();
//                database.child("Inventory").push().setValue(data).addOnSuccessListener((new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Toast.makeText(context, "Data Tersimpan", Toast.LENGTH_SHORT).show();
//                    }
//                }));

            } catch (JSONException e) {
                e.printStackTrace();
            }
//            builder.setMessage(result.getContents());
//            builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
//            {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i)
//                {
//                    dialogInterface.dismiss();
//                }
//            }).show();
        }
    });

    private void searchByName(String name) {
        // adding a value listener to database reference to perform search
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    inventoryArrayList.clear();

                    for(DataSnapshot e : snapshot.getChildren())
                    {
                        InventoryData inventory = e.getValue(InventoryData.class);
                        if(inventory != null)
                        {
//                            Log.d("testa", inventory.getNamaBarang());
//                            Log.d("testa", name);
//                            Log.d("testa", String.valueOf(inventory.getNamaBarang().equals(name)));
                            if(inventory.getNamaBarang().equals(name) || inventory.getDate().equals(name))
                            {
                                inventory.setKey(snapshot.getKey());
                                inventoryArrayList.add(inventory);
                            }
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    // region Export Excel

    private static Cell cell;
    private static Sheet sheet;

    private static String EXCEL_SHEET_NAME = "Sheet1";
    private static Workbook workbook;
    private static CellStyle headerCellStyle;

    public static boolean exportDataIntoWorkbook(Context context, String fileName,
                                                 List<InventoryData> dataList) {
        boolean isWorkbookWrittenIntoStorage;

        // Check if available and not read only
        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            Log.e("testa", "Storage not available or read only");
            return false;
        }

        // Creating a New HSSF Workbook (.xls format)
        workbook = new HSSFWorkbook();

        setHeaderCellStyle();

        // Creating a New Sheet and Setting width for each column
        sheet = workbook.createSheet("Sheet1");
        sheet.setColumnWidth(0, (15 * 400));
        sheet.setColumnWidth(1, (15 * 400));
        sheet.setColumnWidth(2, (15 * 400));
        sheet.setColumnWidth(3, (15 * 400));

        setHeaderRow();
        fillDataIntoExcel(dataList);
        isWorkbookWrittenIntoStorage = storeExcelInStorage(context, fileName);

        return isWorkbookWrittenIntoStorage;
    }

    /**
     * Checks if Storage is READ-ONLY
     *
     * @return boolean
     */
    private static boolean isExternalStorageReadOnly() {
        String externalStorageState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED_READ_ONLY.equals(externalStorageState);
    }

    /**
     * Checks if Storage is Available
     *
     * @return boolean
     */
    private static boolean isExternalStorageAvailable() {
        String externalStorageState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(externalStorageState);
    }

    /**
     * Setup header cell style
     */
    private static void setHeaderCellStyle() {
        headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFillForegroundColor(HSSFColor.AQUA.index);
        headerCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        headerCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
    }

    /**
     * Setup Header Row
     */
    private static void setHeaderRow() {
        Row headerRow = sheet.createRow(0);

        cell = headerRow.createCell(0);
        cell.setCellValue("Nama Barang");
        cell.setCellStyle(headerCellStyle);

        cell = headerRow.createCell(1);
        cell.setCellValue("Jumlah Stok");
        cell.setCellStyle(headerCellStyle);

        cell = headerRow.createCell(2);
        cell.setCellValue("Type");
        cell.setCellStyle(headerCellStyle);

        cell = headerRow.createCell(3);
        cell.setCellValue("Keterangan");
        cell.setCellStyle(headerCellStyle);

        cell = headerRow.createCell(4);
        cell.setCellValue("Date");
        cell.setCellStyle(headerCellStyle);

        cell = headerRow.createCell(5);
        cell.setCellValue("Stok Akhir");
        cell.setCellStyle(headerCellStyle);
    }

    /**
     * Fills Data into Excel Sheet
     * <p>
     * NOTE: Set row index as i+1 since 0th index belongs to header row
     *
     * @param dataList - List containing data to be filled into excel
     */
    private static void fillDataIntoExcel(List<InventoryData> dataList) {
        for (int i = 0; i < dataList.size(); i++) {
            // Create a New Row for every new entry in list
            Row rowData = sheet.createRow(i + 1);

            // Create Cells for each row
            cell = rowData.createCell(0);
            cell.setCellValue(dataList.get(i).getNamaBarang());

            cell = rowData.createCell(1);
            cell.setCellValue(dataList.get(i).getJmlStok());

            cell = rowData.createCell(2);
            cell.setCellValue(dataList.get(i).getType());

            cell = rowData.createCell(3);
            cell.setCellValue(dataList.get(i).getKet());

            cell = rowData.createCell(4);
            cell.setCellValue(dataList.get(i).getDate());

            cell = rowData.createCell(5);
            cell.setCellValue(dataList.get(i).getStokAkhir());
        }
    }

    /**
     * Store Excel Workbook in external storage
     *
     * @param context  - application context
     * @param fileName - name of workbook which will be stored in device
     * @return boolean - returns state whether workbook is written into storage or not
     */
    private static boolean storeExcelInStorage(Context context, String fileName) {
        boolean isSuccess;
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), fileName);
        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = new FileOutputStream(file);
            workbook.write(fileOutputStream);
            Log.e("testa", "Writing file" + file);
            isSuccess = true;

        } catch (IOException e) {
            Log.e("testa", "Error writing Exception: ", e);
            isSuccess = false;
        } catch (Exception e) {
            Log.e("testa", "Failed to save file due to Exception: ", e);
            isSuccess = false;
        } finally {
            try {
                if (null != fileOutputStream) {
                    fileOutputStream.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return isSuccess;
    }

    //endregion
}