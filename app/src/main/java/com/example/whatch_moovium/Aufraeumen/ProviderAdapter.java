package com.example.whatch_moovium.Aufraeumen;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatch_moovium.R;

import java.util.ArrayList;

class ProviderAdapter extends RecyclerView.Adapter<ProviderAdapter.ProviderViewholder> {
    private final ProviderRecyclerViewInterface providerRecyclerViewInterface;

    Context context;
    ArrayList<ProviderModel> providerModels;


    public ProviderAdapter(Context context, ArrayList<ProviderModel> providerModels, ProviderRecyclerViewInterface providerRecyclerViewInterface){

        this.context = context;
        this.providerModels = providerModels;
        this.providerRecyclerViewInterface = providerRecyclerViewInterface;

    }

    @NonNull
    @Override
    public ProviderAdapter.ProviderViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Aussehen der Zeilen

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.provider_settings_row, parent, false);
        return new ProviderAdapter.ProviderViewholder(view, providerRecyclerViewInterface);

    }

    @Override
    public void onBindViewHolder(@NonNull ProviderAdapter.ProviderViewholder holder, int position) {
        //Jede sichtbare Zeile bekommt Value zugewiesen

        holder.providerNames.setText(providerModels.get(position).getProviderName());

        holder.providerSwitches.setChecked(providerModels.get(position).getProviderStatus());



    }

    @Override
    public int getItemCount() {
        //Gesamtanzahl der Items
        return providerModels.size();
    }

    public static class ProviderViewholder extends RecyclerView.ViewHolder{
        //Alle Views von provider_settings_row holen und Variablen zuweisen

        Switch providerSwitches;
        TextView providerNames;

        public ProviderViewholder(@NonNull View itemView, ProviderRecyclerViewInterface providerRecyclerViewInterface) {
            super(itemView);

            providerSwitches = itemView.findViewById(R.id.provider_switch);
            providerNames = itemView.findViewById(R.id.provider_text);
            //Switch ansprechen
            providerSwitches.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (providerRecyclerViewInterface != null){
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION){
                            providerRecyclerViewInterface.onSwitchFlipped(pos, b);
                            Log.i("userdebug", "Hallo, ich bin Switch Nr " + String.valueOf(pos));
                        }
                    }
                }
            });
        }
    }
}