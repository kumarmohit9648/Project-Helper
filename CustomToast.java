import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class CustomToast {

    public static void initToast(Context context,String msg){
        Activity act = (Activity) context;
        LayoutInflater inflater = act.getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup)act. findViewById(R.id.custom_lay));

        TextView text = (TextView) layout.findViewById(R.id.toast_text);
        text.setText(msg);
        
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);

        toast.setView(layout);
        toast.show();
    }
}
