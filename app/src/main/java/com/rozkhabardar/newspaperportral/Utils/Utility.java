package com.rozkhabardar.newspaperportral.Utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public class Utility {
    static ProgressDialog progressDialog;
    public static boolean checknetwork(Context context)
    {
        ConnectivityManager manager= (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=manager.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isConnectedOrConnecting())
            return true;
        return false;
    }
    public  static void showpop(Activity activity)
    {
        if(progressDialog!=null)
        {
            progressDialog.dismiss();
        }
        progressDialog=new ProgressDialog(activity);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }
    public static void hidepopup()
    {
        if(progressDialog!=null)
        {
            progressDialog.dismiss();
        }
        progressDialog=null;
    }
    public static void message(Context context,String msg)
    {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
    public static void animate(RecyclerView.ViewHolder holder,Boolean aBoolean)
    {
        YoYo.with(Techniques.RollIn).duration(500).playOn(holder.itemView);

    }
  /*  public static void Alertbox(Context context,String title,String Message,String Positve)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.setPositiveButton(Positve, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

            }
        });
        builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                return false;
            }
        });
        builder.create();
        builder.show();
    }*/
}
