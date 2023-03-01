package com.example.nursevendor.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nursevendor.R;
import com.example.nursevendor.pojo.NurseAddtoCart;


import java.util.List;

public class RequestRecylaerViewAdapter extends RecyclerView.Adapter<RequestRecylaerViewAdapter.MyViewHolder> {


    private Context context;
    private List<NurseAddtoCart> nurseAddtoCarts;

    private ReqDetailsListener listener;

    public RequestRecylaerViewAdapter(Context context, List<NurseAddtoCart> nurseAddtoCarts) {
        this.context = context;
        this.nurseAddtoCarts = nurseAddtoCarts;
        listener = (ReqDetailsListener) context;

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView PatientName, Package, Date;

        LinearLayout getDetailsLayout;


        public MyViewHolder(View view) {
            super(view);

            getDetailsLayout = itemView.findViewById(R.id.reqDetails);

            PatientName = (TextView) view.findViewById(R.id.PatientName);
            Package = (TextView) view.findViewById(R.id.package_take);
            Date = (TextView) view.findViewById(R.id.issu_date);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.req_item, parent, false);

        return new RequestRecylaerViewAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final NurseAddtoCart nc = nurseAddtoCarts.get(position);

        holder.PatientName.setText(nc.getPatientName());
        holder.Package.setText(nc.getPackageName());
        holder.Date.setText(nc.getNurseTakenDate());

        holder.getDetailsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onReqDetails(nc);
            }
        });


    }

    @Override
    public int getItemCount() {
        return nurseAddtoCarts.size();
    }

    public interface ReqDetailsListener {
        void onReqDetails(NurseAddtoCart requestDetails);
    }

}
