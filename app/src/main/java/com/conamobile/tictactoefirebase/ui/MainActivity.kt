package com.conamobile.tictactoefirebase.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.conamobile.tictactoefirebase.R
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        setup()
    }

    private fun setup() {
        val databaseReference: DatabaseReference = FirebaseDatabase.getInstance()
            .getReferenceFromUrl("https://tictactoe-f7be6-default-rtdb.firebaseio.com/")

        databaseReference.child("connections").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.hasChildren()) {

                    for (i in snapshot.children) {
                        val conId = i.key
                        val playersCount = i.childrenCount
                    }

                } else {
                    val connectionUniqueId = System.currentTimeMillis()

                    snapshot.child(connectionUniqueId.toString()).child("0")
                        .child("player_name").ref.setValue(
                            "what")
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }
}