package com.kerim.neptisgame01

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kerim.neptisgame01.adapters.UserAdapter
import com.kerim.neptisgame01.models.User
import com.kerim.neptisgame01.network.ApiClient
import com.kerim.neptisgame01.network.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity() {
    private lateinit var userService: UserService
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        userService = ApiClient.createService(UserService::class.java)
        userAdapter = UserAdapter { user -> onUserSelected(user) }

        val recyclerView = findViewById<RecyclerView>(R.id.user_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = userAdapter

        loadUsers()
    }

    private fun loadUsers() {
        userService.getAllUsers().enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    val users = response.body()
                    userAdapter.submitList(users)
                } else {
                    Toast.makeText(this@HomeActivity, "Failed to load users: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Toast.makeText(this@HomeActivity, "Network error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun onUserSelected(user: User) {
        // Show a dialog to confirm playing with the selected user
        AlertDialog.Builder(this)
            .setTitle("Play Guess Name")
            .setMessage("Do you want to play guess name with ${user.username}?")
            .setPositiveButton("Yay!") { _, _ ->
                // Navigate to GameActivity
                val intent = Intent(this, GameActivity::class.java)
                intent.putExtra("OPPONENT_USER", user)  // Pass the selected user to GameActivity
                startActivity(intent)
            }
            .setNegativeButton("Naah") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}
