package com.example.peaches;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class FileListAdapter extends RecyclerView.Adapter<FileListAdapter.ViewHolder> {

    private OnItemCheckListener checkListener;

    private String[] files;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private CheckBox checkBox;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            textView = (TextView) view.findViewById(R.id.text_view_filename);
            checkBox = view.findViewById(R.id.checkbox_select);

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int position = getAdapterPosition();
                    Log.d("debug", "checkbox ran: " + position);
                    checkListener.onItemChanged(textView, checkBox);
                }
            });
        }

        public TextView getTextView() { return textView; }
        public CheckBox getCheckBox() { return checkBox; }
    }

    interface OnItemCheckListener {
        void onItemChanged(TextView textView, CheckBox checkBox);
    }

    public void setOnItemCheckListener(OnItemCheckListener checkListener) {
        this.checkListener = checkListener;
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public FileListAdapter(String[] dataSet) {
        files = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getTextView().setText(files[position]);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return files.length;
    }

    public String getItemAt(int position) { return files[position]; }
}