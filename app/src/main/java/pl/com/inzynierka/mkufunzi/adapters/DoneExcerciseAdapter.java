package pl.com.inzynierka.mkufunzi.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import pl.com.inzynierka.mkufunzi.R;
import pl.com.inzynierka.mkufunzi.models.DoneExcercise;

/**
 * Class with adapter to show doneExcercise object in activity
 */
public class DoneExcerciseAdapter extends
        RecyclerView.Adapter<DoneExcerciseAdapter.ViewHolder> {

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public static TextView idView;
        public static TextView nameView;
        public static TextView pulseView;
        public static TextView howManyView;
        public static TextView timeView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            idView = (TextView) itemView.findViewById(R.id.done_excercise_id);
            nameView = (TextView) itemView.findViewById(R.id.done_excercise_name);
            pulseView = (TextView) itemView.findViewById(R.id.done_excercise_pulse);
            timeView = (TextView) itemView.findViewById(R.id.done_excercise_time);
            howManyView = (TextView) itemView.findViewById(R.id.done_excercise_how_many);
        }
    }

    // Store a member variable for the contacts
    private List<DoneExcercise> aDoneExcercises;

    // Pass in the contact array into the constructor
    public DoneExcerciseAdapter(List<DoneExcercise> doneExcercises) {
        aDoneExcercises = doneExcercises;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public DoneExcerciseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View doneExcercise = inflater.inflate(R.layout.item_done_excercise, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(doneExcercise);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(DoneExcerciseAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        DoneExcercise doneExcercise = aDoneExcercises.get(position);

        // Set item views based on the data model
        TextView idTextView = viewHolder.idView;
        TextView nameTextView = ViewHolder.nameView;
        TextView pulseTextView = ViewHolder.pulseView;
        TextView howManyTextView = ViewHolder.howManyView;
        TextView timeTextView = ViewHolder.timeView;

        idTextView.setText("Ćwiczenie nr " + Integer.toString(position+1));
        nameTextView.setText(doneExcercise.excerciseName);
        pulseTextView.setText(Integer.toString(doneExcercise.pulse));
        howManyTextView.setText(Integer.toString(doneExcercise.howMany));
        if (doneExcercise.time.equals("null")) {
            timeTextView.setText("00:00:00");
        } else {
            timeTextView.setText(doneExcercise.time);
        }
    }

    // Return the total count of items
    @Override
    public int getItemCount() {
        return aDoneExcercises.size();
    }
}

