package com.kushal.samplechat;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by kusha on 7/5/2017.
 */

class MsgVH extends RecyclerView.ViewHolder{

    private TextView emailTV;
    private TextView msgTV;

    public MsgVH(View itemView) {
        super(itemView);

        emailTV = (TextView) itemView.findViewById(R.id.emailTV);
        msgTV = (TextView) itemView.findViewById(R.id.msgTV);

    }

    public void bindToMessage(Message message , View.OnClickListener startClick){

        emailTV.setText(message.getEmail());
        msgTV.setText(message.getMessage());

    }
}
