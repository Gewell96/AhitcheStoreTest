package com.ahitche.store.AhitcheStore.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.ahitche.store.AhitcheStore.DB_Contract.DB_ville;
import com.ahitche.store.AhitcheStore.DB_Contract.DbContract;
import com.ahitche.store.AhitcheStore.R;

import java.util.ArrayList;
import java.util.List;

public class vilAdapter extends BaseAdapter{
    Context context;
    ArrayList<DB_ville> arrayList;
    private List<String> filteredData = null;

    public vilAdapter(Context context, ArrayList<DB_ville> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return this.arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=inflater.inflate(R.layout.ville_row,null);
        Button btn_avatar=(Button)convertView.findViewById(R.id.avatarvil);
        TextView nomville=(TextView)convertView.findViewById(R.id.Row_nomvil);



        DB_ville db_viie=arrayList.get(position);
        nomville.setText(arrayList.get(position).getNomville());
        int sync_status=arrayList.get(position).getSyncstatus();

///
        String str = nomville.getText().toString();

        String[] strArray = str.split(" ");
        StringBuilder builder = new StringBuilder();

//First name
        if (strArray.length > 0){
            builder.append(strArray[0], 0, 1);
        }
        btn_avatar.setText(builder.toString());


        ///

        if (sync_status== DbContract.SYNC_STATUS_OK)
        {
            // sync_status.set

        }

        return convertView;
    }




}
