package com.example.neighbourly.ui.feedback

import android.media.Rating
import android.os.Bundle
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

        handler.findRating("test")

        submit.setOnClickListener{
            println(rating.rating)
            println(review.text)

            if (review.text.toString().isNotEmpty()){
                handler.insertRating(rating.rating.toString(), review.text.toString(), "test")
            }
            else{
                println("Cannot leave an empty review!")
            }

        }

        return root
    }
}
