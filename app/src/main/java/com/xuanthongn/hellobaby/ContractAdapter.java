package com.xuanthongn.hellobaby;

import android.view.*;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContractAdapter extends RecyclerView.Adapter<ContractAdapter.ContractViewHolder> {

    private List<Contract> contractList;

    public ContractAdapter(List<Contract> contractList) {
        this.contractList = contractList;
    }

    @NonNull
    @Override
    public ContractViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contract_item_layout, parent, false);
        return new ContractViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ContractViewHolder holder, int position) {
        Contract contract = contractList.get(position);
        holder.bind(contract);
    }

    @Override
    public int getItemCount() {
        return contractList.size();
    }

    public class ContractViewHolder extends RecyclerView.ViewHolder {

        private final TextView contractTitleTextView;
        private final TextView contractPartiesTextView;
        private final TextView contractPurposeTextView;

        public ContractViewHolder(@NonNull View itemView) {
            super(itemView);
            contractTitleTextView = itemView.findViewById(R.id.contractTitleTextView);
            contractPartiesTextView = itemView.findViewById(R.id.contractPartiesTextView);
            contractPurposeTextView = itemView.findViewById(R.id.contractPurposeTextView);
        }

        public void bind(Contract contract) {
            contractTitleTextView.setText(contract.getTitle());
            contractPartiesTextView.setText(contract.getParties());
            contractPurposeTextView.setText(contract.getPurpose());
        }
    }
}