package pl.com.inzynierka.mkufunzi.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.activeandroid.query.Select;

import java.text.SimpleDateFormat;
import java.util.List;

import pl.com.inzynierka.mkufunzi.R;
import pl.com.inzynierka.mkufunzi.models.MeasureType;
import pl.com.inzynierka.mkufunzi.models.Measurement;

/**
 * Custom adapter used to show measures in MeasurementPage
 */
public class MeasurementAdapter extends
        RecyclerView.Adapter<MeasurementAdapter.ViewHolder> {

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView valueTextView;
        public TextView dateTextView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            valueTextView = (TextView) itemView.findViewById(R.id.value_text);
            dateTextView = (TextView) itemView.findViewById(R.id.time_text);

        }
    }

    // Store a member variable for the contacts
    private List<Measurement> aMeasurements;
    private String measureUnit;

    // Pass in the contact array into the constructor
    public MeasurementAdapter(List<Measurement> measurements, String measureUnit) {
        aMeasurements = measurements;
        this.measureUnit = measureUnit;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public MeasurementAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View measureView = inflater.inflate(R.layout.item_measurement, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(measureView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(MeasurementAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Measurement measurement = aMeasurements.get(position);

        // Set item views based on the data model
        TextView valueTextView = viewHolder.valueTextView;
        if(measurement.secondValue != 0){
            valueTextView.setText(measurement.value+ "/" + measurement.secondValue + " " + measureUnit);
        }
        else {
            valueTextView.setText(measurement.value + " " + measureUnit);
        }
        TextView dateTextView = viewHolder.dateTextView;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String formattedDate = df.format(measurement.time);
        dateTextView.setText(formattedDate);
    }

    // Return the total count of items
    @Override
    public int getItemCount() {
        return aMeasurements.size();
    }
}
