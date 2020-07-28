package yc.com.rthttplibrary.util;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by suns  on 2020/7/27 17:06.
 */
public class ToastUtil {


    public static void toast(Context context, String mess) {

        if (TextUtils.isEmpty(mess)) {
            mess = "连蚊子都没有还弹啥";
        }

        Toast toast = Toast.makeText(context, mess, Toast.LENGTH_SHORT);

        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
