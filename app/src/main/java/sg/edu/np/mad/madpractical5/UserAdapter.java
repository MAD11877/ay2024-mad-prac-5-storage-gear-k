package sg.edu.np.mad.madpractical5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import sg.edu.np.mad.madpractical4.R;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<User> userList;
    private Context context;
    private OnFollowButtonClickListener listener;

    public interface OnFollowButtonClickListener {
        void onFollowButtonClick(User user);
    }

    // Constructor for normal use
    public UserAdapter(List<User> userList, Context context, OnFollowButtonClickListener listener) {
        this.userList = userList;
        this.context = context;
        this.listener = listener;
    }

    // Constructor for testing or cases where context and listener are not needed
    public UserAdapter(List<User> userList) {
        this.userList = userList;
        this.context = null;
        this.listener = null;
    }

    // Constructor to handle the case where the context is null
    public UserAdapter(List<User> userList, Context context) {
        this.userList = userList;
        this.context = context;
        this.listener = null;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_activity_list, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.nameTextView.setText(user.name);
        holder.descriptionTextView.setText(user.description);

        if (listener != null) {
            holder.followButton.setText(user.followed ? "Unfollow" : "Follow");
            holder.followButton.setOnClickListener(v -> listener.onFollowButtonClick(user));
        } else {
            holder.followButton.setVisibility(View.GONE); // or keep it visible without any click action
        }
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView descriptionTextView;
        Button followButton;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            followButton = itemView.findViewById(R.id.followButton);
        }
    }
}
