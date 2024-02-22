package afluex.parent.careermitra.constants;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import afluex.parent.careermitra.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FileDownloader {
    private static final String TAG = "Download Task";
    private Context context;

    private String downloadUrl = "", downloadFileName = "";
    private ProgressDialog progressDialog;

    public FileDownloader(Context context, String downloadUrl) {
        this.context = context;
        this.downloadUrl = downloadUrl;
        downloadFileName = downloadUrl.substring(downloadUrl.lastIndexOf('/'), downloadUrl.length());//Create file name by picking download file name from URL
        downloadFileName = downloadFileName + ".pdf";
        Log.e(TAG, downloadFileName);

        //Start Downloading Task
        new DownloadingTask().execute();
    }

    private class DownloadingTask extends AsyncTask<Void, Void, Void> {

        File apkStorage = null;
        File outputFile = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage(context.getString(R.string.downloading));
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Void result) {
            try {
                if (outputFile != null) {
                    progressDialog.dismiss();
                    ContextThemeWrapper ctw = new ContextThemeWrapper(context, R.style.AppTheme);
                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctw);
                    alertDialogBuilder.setTitle(R.string.document);
                    alertDialogBuilder.setMessage(R.string.download_ticket);
                    alertDialogBuilder.setCancelable(false);
                    alertDialogBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });

                    alertDialogBuilder.setNegativeButton(R.string.open_ticket, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            File pdfFile = new File(Environment.getExternalStorageDirectory() + "/CareerMitraTraining/" + downloadFileName);  // -> filename = maven.pdf
                            Uri path = Uri.fromFile(pdfFile);
                            Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
//                            pdfIntent.setDataAndType(path, "file/*");
                            pdfIntent.setDataAndType(path, "application/pdf");
                            pdfIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            pdfIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            Intent intent = Intent.createChooser(pdfIntent, context.getString(R.string.oprm_file));
                            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                            StrictMode.setVmPolicy(builder.build());
                            try {
                                context.startActivity(intent);
                            } catch (ActivityNotFoundException e) {
                                Toast.makeText(context, R.string.no_app, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    alertDialogBuilder.show();
//                    Toast.makeText(context, "Document Downloaded Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(context, R.string.download_fail, Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Download Failed");
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(context, R.string.download_fail, Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Download Failed with Exception - " + e.getLocalizedMessage());
            }
            super.onPostExecute(result);
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                URL url = new URL(downloadUrl);//Create Download URl
                HttpURLConnection c = (HttpURLConnection) url.openConnection();//Open Url Connection
                c.setRequestMethod("GET");//Set Request Method to "GET" since we are grtting data
                c.connect();//connect the URL Connection

                //If Connection response is not OK then show Logs
                if (c.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    Log.e(TAG, "Server returned HTTP " + c.getResponseCode()
                            + " " + c.getResponseMessage());

                }
                //Get File if SD card is present
                if (new CheckForSDCard().isSDCardPresent()) {
                    apkStorage = new File(Environment.getExternalStorageDirectory() + "/" + "CareerMitraTraining");
                } else
                    Toast.makeText(context, "Oops!! There is no SD Card.", Toast.LENGTH_SHORT).show();

                //If File is not present create directory
                if (!apkStorage.exists()) {
                    apkStorage.mkdir();
                    Log.e(TAG, "Directory Created.");
                }

                outputFile = new File(apkStorage, downloadFileName);//Create Output file in Main File

                //Create New File if not present
                if (!outputFile.exists()) {
                    outputFile.createNewFile();
                    Log.e(TAG, "File Created");
                }

                FileOutputStream fos = new FileOutputStream(outputFile);//Get OutputStream for NewFile Location

                InputStream is = c.getInputStream();//Get InputStream for connection

                byte[] buffer = new byte[1024];//Set buffer type
                int len1 = 0;//init length
                while ((len1 = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len1);//Write new file
                }

                //Close all connection after doing task
                fos.close();
                is.close();

            } catch (Exception e) {

                //Read exception if something went wrong
                e.printStackTrace();
                outputFile = null;
                Log.e(TAG, "Download Error Exception " + e.getMessage());
            }

            return null;
        }
    }

    public class CheckForSDCard {
        //Check If SD Card is present or not method
        public boolean isSDCardPresent() {
            if (Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                return true;
            }
            return false;
        }
    }
}