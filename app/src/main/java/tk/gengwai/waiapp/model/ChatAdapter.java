package tk.gengwai.waiapp.model;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.vision.text.Text;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import tk.gengwai.waiapp.R;

/**
 * Created by danielchan303 on 1/9/2016.
 */
public class ChatAdapter extends FirebaseListAdapter<ChatMessage> {
    public ChatAdapter(Activity activity, Class<ChatMessage> modelClass, int modelLayout, DatabaseReference ref) {
        super(activity, modelClass, modelLayout, ref);
    }

    @Override
    protected void populateView(View v, ChatMessage model, int position) {
        View view = v;
        String time = ConvertTime.convertTime(((Long) model.getTimestamp()));
        ((TextView) view.findViewById(R.id.sender)).setText(model.getSender() + " " + time);
        ((TextView) view.findViewById(R.id.message)).setText(model.getMessage());

    }
}
