package com.maria.employees.ui.uitools;

import android.content.Context;
import android.widget.Toast;

public class UiTools {

    public static void showMessage(String message, Context context) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
