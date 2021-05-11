package com.example.neighbourly.ui.feedback

import android.content.Intent
import android.media.Rating
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.neighbourly.R
import android.widget.*
import com.example.neighbourly.*
import com.example.neighbourly.ui.login.LoginActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class FeedbackFragment : Fragment() {

    lateinit var handler:DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        handler = DatabaseHelper(requireContext())

        val root = inflater.inflate(R.layout.fragment_feedback, container, false)

        val rating: RatingBar = root.findViewById(R.id.ratingBar2)
        val review: TextInputEditText = root.findViewById(R.id.reviewText)
        val submit: Button = root.findViewById(R.id.Submit)

        submit.setOnClickListener{
            Log.d("Errors", "Line 38 feedback")

            if (review.text.toString().isNotEmpty()){
                handler.insertRating(rating.rating.toString(), review.text.toString(), "test")
                Toast.makeText(requireContext(), "Review Submitted", Toast.LENGTH_LONG).show()
                val intent = Intent(context, Navigation::class.java).apply {}
                startActivity(intent)
            }
            else{
                Toast.makeText(requireContext(), "Cannot leave an empty review", Toast.LENGTH_LONG).show()
            }

        }

        return root
    }
}
