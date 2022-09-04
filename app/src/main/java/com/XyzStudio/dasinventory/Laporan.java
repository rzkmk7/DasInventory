package com.XyzStudio.dasinventory;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Laporan extends AppCompatActivity {
    FloatingActionButton fab_add_laporan;

    ListView listView;
    AdapterLaporan adapter;
    public static ArrayList<LaporanData> laporanArrayList = new ArrayList<>();
    Context context;
    ImageButton dellAllLap;
    ImageButton btnRefLap;
    ImageButton btnLapHome;
    ImageButton expLap;

    private FirebaseUser users;
    public FirebaseDatabase database;
    public DatabaseReference ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan);

        context = this;

        users = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Users");
        dellAllLap =findViewById(R.id.delAllLap);
        btnRefLap=findViewById(R.id.btnRefLap);
        btnLapHome=findViewById(R.id.btnLapHome);
        expLap=findViewById(R.id.exportLap);

        adapter = new AdapterLaporan(this, laporanArrayList, new AdapterLaporan.OnClickListener() {

            @Override
            public void onClick(Integer message) {
//                Log.d("asd", laporanArrayList.get(message).get());
                FragLap fragLap = new FragLap(
                        laporanArrayList.get(message).getTgl_laporan(),
                        laporanArrayList.get(message).getTempat_laporan(),
                        laporanArrayList.get(message).getType_mesin(),
                        laporanArrayList.get(message).getAtt(),
                        laporanArrayList.get(message).getKey()
                );

                fragLap.show(getSupportFragmentManager(), "activity_frag_lap");
            }
        }, new AdapterLaporan.OnClickListenerDel() {

            @Override
            public void onClick(Integer message) {
                Log.d("lala",Integer.valueOf(message).toString());
                Log.d("lala", ref.child(users.getUid()).child("Laporan").child(laporanArrayList.get(message).getKey()).toString());
                ref.child(users.getUid()).child("Laporan").child(laporanArrayList.get(message).getKey()).removeValue();
                //finish();
                refresh();
            }
        });
        adapter.arrayListCustomer.clear();

        listView = findViewById(R.id.ListLaporan);
        listView.setAdapter(adapter);

        fab_add_laporan = findViewById(R.id.fab_add_laporan);

        fab_add_laporan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Laporan.this,FormLaporan.class));
            }
        });
        btnLapHome.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Laporan.this,Home.class));
            }
        });

        expLap.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //export laporan4
                Date currentTime = Calendar.getInstance().getTime();
                String currentTimeString = currentTime.toString().replaceAll(":", ".");
                boolean exportSuccess = exportDataIntoWorkbook(context, "Laporan-" + currentTimeString + ".xls", laporanArrayList);
                if(exportSuccess)
                {
                    Toast.makeText(getApplicationContext(), "Export Sukses", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Export Failed", Toast.LENGTH_LONG).show();
                }
            }

        });

        dellAllLap.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("kliked","s");
                AlertDialog.Builder builder = new AlertDialog.Builder(Laporan.this);
                builder.setTitle("Do this action");
                builder.setMessage("delete semua data?");
                builder.setPositiveButton("YES", (dialog, which) -> {
                    // Do do my action here
                    ref.child(users.getUid()).child("Laporan").removeValue();
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

        btnRefLap.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
                overridePendingTransition(1, 1);
                startActivity(getIntent());
                overridePendingTransition(1, 1);
            }
        });


        getData();
    }

    public void refresh(){
        startActivity(getIntent());
    }

    public void getData(){
        laporanArrayList.clear();
        ref.child(users.getUid()).child("Laporan").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                LaporanData laporan = snapshot.getValue(LaporanData.class);
                laporan.setKey(snapshot.getKey());
                laporanArrayList.add(laporan);
                adapter.notifyDataSetChanged();
                
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


    // region Export Excel

    private static Cell cell;
    private static Sheet sheet;

    private static String EXCEL_SHEET_NAME = "Sheet1";
    private static Workbook workbook;
    private static CellStyle headerCellStyle;

    public static boolean exportDataIntoWorkbook(Context context, String fileName,
                                                 List<LaporanData> dataList) {
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
        cell.setCellValue("Tanggal Laporan");
        cell.setCellStyle(headerCellStyle);

        cell = headerRow.createCell(1);
        cell.setCellValue("Tempat Laporan");
        cell.setCellStyle(headerCellStyle);

        cell = headerRow.createCell(2);
        cell.setCellValue("Type Mesin");
        cell.setCellStyle(headerCellStyle);

        cell = headerRow.createCell(3);
        cell.setCellValue("Att");
        cell.setCellStyle(headerCellStyle);
    }

    /**
     * Fills Data into Excel Sheet
     * <p>
     * NOTE: Set row index as i+1 since 0th index belongs to header row
     *
     * @param dataList - List containing data to be filled into excel
     */
    private static void fillDataIntoExcel(List<LaporanData> dataList) {
        for (int i = 0; i < dataList.size(); i++) {
            // Create a New Row for every new entry in list
            Row rowData = sheet.createRow(i + 1);

            // Create Cells for each row
            cell = rowData.createCell(0);
            cell.setCellValue(dataList.get(i).getTgl_laporan());

            cell = rowData.createCell(1);
            cell.setCellValue(dataList.get(i).getTempat_laporan());

            cell = rowData.createCell(2);
            cell.setCellValue(dataList.get(i).getType_mesin());

            cell = rowData.createCell(3);
            cell.setCellValue(dataList.get(i).getAtt());
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
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)+"/"+fileName);
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