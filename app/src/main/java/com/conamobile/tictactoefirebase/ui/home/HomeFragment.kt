package com.conamobile.tictactoefirebase.ui.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import com.conamobile.tictactoefirebase.R
import com.conamobile.tictactoefirebase.core.Notifications
import com.conamobile.tictactoefirebase.core.utils.viewBinding
import com.conamobile.tictactoefirebase.databinding.FragmentHomeBinding
import com.conamobile.tictactoefirebase.ui.BaseFragment
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class HomeFragment : BaseFragment(R.layout.fragment_home) {
    private val binding by viewBinding { FragmentHomeBinding.bind(it) }
    private var mFirebaseAnalytics: FirebaseAnalytics? = null
    private var sessionID: String? = null
    var playerSymbol: String? = null
    var activePlayer = 1
    var player1 = ArrayList<Int>()
    var player2 = ArrayList<Int>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(requireContext())
        initViews()
    }

    private fun initViews() {
        incomingCalls()
        requestBtnManager()
        acceptBtnManager()
        rowClicksManager()
    }

    private fun rowClicksManager() {
        binding.apply {
            img1.click {
                childRequest(1)
            }
            img2.click {
                childRequest(2)
            }
            img3.click {
                childRequest(3)
            }
            img4.click {
                childRequest(4)
            }
            img5.click {
                childRequest(5)
            }
            img6.click {
                childRequest(6)
            }
            img7.click {
                childRequest(7)
            }
            img8.click {
                childRequest(8)
            }
            img9.click {
                childRequest(9)
            }
        }
    }

    private fun childRequest(id: Int) {
        myRef.child("PlayerOnline").child(sessionID!!).child(id.toString())
            .setValue(sharedPref.getSavedEmail())
    }

    private fun acceptBtnManager() {
        binding.acceptBtn.click {
            val user = binding.userEditText.text.toString()
            myRef.child("Users").child(splitString(user)).child("Request").push()
                .setValue(sharedPref.getSavedEmail())
            playerOnline(splitString(user) + splitString(sharedPref.getSavedEmail()!!)) //husseinjena
            playerSymbol = "O"
        }
    }

    private fun requestBtnManager() {
        binding.requestBtn.click {
            val email = binding.userEditText.text.toString()

            myRef.child("Users").child(splitString(email)).child("Request").push()
                .setValue(sharedPref.getSavedEmail())

            playerOnline(splitString(sharedPref.getSavedEmail()!!) + splitString(email))
            playerSymbol = "X"
        }
    }

    var number = 0
    private fun incomingCalls() {
        myRef.child("Users").child(splitString(sharedPref.getSavedEmail()!!)).child("Request")
            .addValueEventListener(object : ValueEventListener {

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    try {
                        val td = dataSnapshot.value as HashMap<String, Any>
                        val value: String
                        for (key in td.keys) {
                            value = td[key] as String
                            binding.userEditText.setText(value)

                            val notifyMe = Notifications()
                            notifyMe.notify(requireContext(),
                                "$value want to play tic tac toe", number)
                            number++
                            myRef.child("Users")
                                .child(splitString(sharedPref.getSavedEmail()!!))
                                .child("Request")
                                .setValue(true)
                            break
                        }
                    } catch (ex: Exception) {
                    }
                }

                override fun onCancelled(p0: DatabaseError) {
                    tst("DatabaseError")
                }
            })
    }

    private fun playerOnline(sessionID: String) {
        this.sessionID = sessionID
        myRef.child("PlayerOnline").removeValue()
        myRef.child("PlayerOnline").child(sessionID)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    try {
                        player1.clear()
                        player2.clear()
                        val td = dataSnapshot.value as HashMap<String, Any>

                        var value: String
                        for (key in td.keys) {
                            value = td[key] as String

                            activePlayer = if (value != sharedPref.getSavedEmail()) {
                                if (playerSymbol === "X") 1 else 2
                            } else {
                                if (playerSymbol === "X") 2 else 1
                            }
                            autoPlay(key.toInt())
                        }
                    } catch (ex: Exception) {
                    }
                }

                override fun onCancelled(p0: DatabaseError) {

                }

            })

    }

    private fun autoPlay(cellID: Int) {
        binding.apply {
            val buSelect: AppCompatImageView = when (cellID) {
                1 -> img1
                2 -> img2
                3 -> img3
                4 -> img4
                5 -> img5
                6 -> img6
                7 -> img7
                8 -> img8
                9 -> img9
                else -> img1
            }
            playGame(cellID, buSelect)
        }
    }

    private fun playGame(cellID: Int, buSelected: AppCompatImageView) {
        activePlayer = if (activePlayer == 1) {
            boxCrossSelector(buSelected)
            player1.add(cellID)
            2
        } else {
            boxCircleSelector(buSelected)
            player2.add(cellID)
            1
        }
        buSelected.isEnabled = false
        checkWinner()
    }

    private fun checkWinner() {
        var winer = -1
        // row 1
        if (player1.contains(1) && player1.contains(2) && player1.contains(3)) {
            winer = 1
        }
        if (player2.contains(1) && player2.contains(2) && player2.contains(3)) {
            winer = 2
        }
        // row 2
        if (player1.contains(4) && player1.contains(5) && player1.contains(6)) {
            winer = 1
        }
        if (player2.contains(4) && player2.contains(5) && player2.contains(6)) {
            winer = 2
        }
        // row 3
        if (player1.contains(7) && player1.contains(8) && player1.contains(9)) {
            winer = 1
        }
        if (player2.contains(7) && player2.contains(8) && player2.contains(9)) {
            winer = 2
        }
        // col 1
        if (player1.contains(1) && player1.contains(4) && player1.contains(7)) {
            winer = 1
        }
        if (player2.contains(1) && player2.contains(4) && player2.contains(7)) {
            winer = 2
        }
        // col 2
        if (player1.contains(2) && player1.contains(5) && player1.contains(8)) {
            winer = 1
        }
        if (player2.contains(2) && player2.contains(5) && player2.contains(8)) {
            winer = 2
        }
        // col 3
        if (player1.contains(3) && player1.contains(6) && player1.contains(9)) {
            winer = 1
        }
        if (player2.contains(3) && player2.contains(6) && player2.contains(9)) {
            winer = 2
        }
        if (winer != -1) {
            if (winer == 1) {
                tst("Player 1  win the game")
            } else {
                tst("Player 2  win the game")
            }
        }
    }

    private fun boxCircleSelector(view: AppCompatImageView) {
        view.setBackgroundResource(R.drawable.circle)
    }

    private fun boxCrossSelector(view: AppCompatImageView) {
        view.setBackgroundResource(R.drawable.cross)
    }
}