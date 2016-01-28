package pl.com.inzynierka.mkufunzi.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import pl.com.inzynierka.mkufunzi.R;
import pl.com.inzynierka.mkufunzi.models.AppUser;
import pl.com.inzynierka.mkufunzi.models.Message;
import pl.com.inzynierka.mkufunzi.models.Trainer;

/**
 * Created by impresyjna on 27.01.16.
 */
public class MessageAdapter extends
        RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private AppUser appUser = AppUser.getInstance();

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView sendDateOutput;
        public TextView personOutput;
        public TextView messageOutput;


        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            sendDateOutput = (TextView) itemView.findViewById(R.id.send_date_output);
            personOutput = (TextView) itemView.findViewById(R.id.person_output);
            messageOutput = (TextView) itemView.findViewById(R.id.message_output);

        }
    }

    // Store a member variable for the contacts
    private List<Message> aMessages;
    private Trainer aTrainer;

    // Pass in the contact array into the constructor
    public MessageAdapter(List<Message> messages, Trainer trainer) {
        aMessages = messages;
        aTrainer = trainer;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View messageView = inflater.inflate(R.layout.item_message, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(messageView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(MessageAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Message message = aMessages.get(position);

        // Set item views based on the data model
        TextView sendDateView = viewHolder.sendDateOutput;
        TextView personView = viewHolder.personOutput;
        TextView messageView = viewHolder.messageOutput;

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String formattedDate = df.format(message.sendTime);
        sendDateView.setText(formattedDate);
        messageView.setText(message.content);
        android.widget.RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, android.widget.RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.topMargin = 10;
        params.bottomMargin = 10;

        if (appUser.getUser().id != message.userSendId) {
            personView.setText(aTrainer.trainerName + " " + aTrainer.trainerSurname);
            viewHolder.itemView.setBackgroundColor(Color.parseColor("#03A9F4"));
            params.rightMargin = 200;

        } else {
            personView.setText(appUser.getUser().name+ " " + appUser.getUser().surname);
            viewHolder.itemView.setBackgroundColor(Color.parseColor("#FFEB3B"));
            params.leftMargin = 200;
        }
        viewHolder.itemView.setLayoutParams(params);

    }

    // Return the total count of items
    @Override
    public int getItemCount() {
        return aMessages.size();
    }
}

