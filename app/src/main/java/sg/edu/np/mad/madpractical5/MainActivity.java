package sg.edu.np.mad.madpractical5;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import sg.edu.np.mad.madpractical4.R;

public class MainActivity extends AppCompatActivity {

    private DatabaseHandler dbHelper;
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        dbHelper = new DatabaseHandler(this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<User> userList = dbHelper.getUsers();
        userAdapter = new UserAdapter(userList, this, new UserAdapter.OnFollowButtonClickListener() {
            @Override
            public void onFollowButtonClick(User user) {
                user.followed = !user.followed;
                dbHelper.updateUser(user);
                userAdapter.notifyDataSetChanged();
            }
        });
        recyclerView.setAdapter(userAdapter);
    }
}