package oilutt.baseproject.utils;

import android.app.Dialog;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import oilutt.baseproject.R;

public final class DialogUtils {

    public void showDialogNoDescription(Context context, Integer txtTitle, Integer txtButton, View.OnClickListener listener){
        showDialog(context, txtTitle, null, txtButton, listener);
    }

    public void showDialogNoTile(Context context, Integer txtDescription, Integer txtButton, View.OnClickListener listener){
        showDialog(context, null, txtDescription, txtButton, listener);
    }

    public void showDialog(Context context, Integer txtTitle, Integer txtDescription, Integer txtButton, View.OnClickListener listener){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_padrao);
        TextView title  = dialog.findViewById(R.id.txt_title);
        TextView description = dialog.findViewById(R.id.txt_description);
        Button okButton = dialog.findViewById(R.id.btn_ok);
        ConstraintLayout ctlLayout = dialog.findViewById(R.id.ctl_layout);

        if(txtTitle != null) {
            title.setText(txtTitle);
        } else {
            title.setVisibility(View.GONE);
        }

        if(txtDescription != null) {
            description.setText(txtDescription);
        } else {
            description.setVisibility(View.GONE);
        }

        if(txtButton != null) {
            okButton.setText(txtButton);
            if (listener != null) {
                okButton.setOnClickListener(listener);
            } else {
                okButton.setOnClickListener((v) -> dialog.dismiss());
            }
        } else {
            okButton.setVisibility(View.GONE);
        }

        ctlLayout.setOnClickListener((v) -> dialog.dismiss());
        dialog.show();
    }
}
