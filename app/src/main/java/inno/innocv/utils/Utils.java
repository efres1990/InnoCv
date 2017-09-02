package inno.innocv.utils;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;


/**
 * @author eladiofreire on 25/8/17.
 */

public class Utils {
    private static boolean if_changes = false;


    public static void createDialog(final Context context, String message, final String tittle, int drawable) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(context);
        }
        builder.setTitle(tittle)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (tittle.contains("added") || tittle.contains("añadido")
                                || tittle.contains("updated") || tittle.contains("actualizado")
                                || tittle.contains("deleted") || tittle.contains("borrado"))
                            ((Activity) context).onBackPressed();
                    }
                })

                .setIcon(drawable)
                .show();
    }

    public static String changeDate(String date) {
        String year = date.substring(0, 4);
        String month = date.substring(5, 7);
        String day = date.substring(8, 10);
        return year + "/" + month + "/" + day;
    }

    public static String changeDate2(String date) {

        String day = date.substring(0, 2);
        String month = date.substring(3, 5);

        if (day.contains("/")) {
            day = "0" + date.substring(0, 1);
            month = date.substring(2, 4);

        }
        String year = date.substring(6, 9);
        if (month.contains("/")) {
            month = "0" + date.substring(2, 3);
            year = date.substring(5, 9);

        }
        return year + "-" + month + "-" + day;
    }
}
