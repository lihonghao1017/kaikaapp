package com.sucetech.yijiamei.kaikaapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sucetech.yijiamei.kaikaapp.R;
import com.sucetech.yijiamei.kaikaapp.bean.BukaBean;

import java.util.List;

public class CardAdapter extends BaseAdapter {
    private List<BukaBean> deviceList;
    private LayoutInflater inflater;
    private BuKaClick buKaClick;

    public CardAdapter(Context context, List<BukaBean> deviceList, BuKaClick buKaClick) {
        inflater = LayoutInflater.from(context);
        this.deviceList = deviceList;
        this.buKaClick = buKaClick;
    }

    @Override
    public int getCount() {
        return deviceList.size();
    }

    @Override
    public Object getItem(int position) {
        return deviceList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.card_list_layout, null);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.phone = (TextView) convertView.findViewById(R.id.phone);
            holder.cards = (TextView) convertView.findViewById(R.id.cards);
            holder.buka = (TextView) convertView.findViewById(R.id.bukaButton);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.name.setText(deviceList.get(position).name);
        holder.phone.setText(deviceList.get(position).cellphone);
        List<String> cards = deviceList.get(position).cards;

        if (cards != null && cards.size() > 0) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < cards.size(); i++) {
                builder.append(cards.get(i));
                if (i != cards.size() - 1) {
                    builder.append("\n");
                }
            }
            holder.cards.setText(builder.toString());

        }
        holder.buka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buKaClick != null) {
                    buKaClick.onBuKaClick(deviceList.get(position));
                }
            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView name;
        TextView phone;
        TextView cards;
        TextView buka;
    }

    public interface BuKaClick {
        public void onBuKaClick(BukaBean bukaBean);
    }
}
