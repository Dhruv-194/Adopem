package com.dhruv.adopem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.dhruv.adopem.databinding.ActivityAdoptPetDetailsBinding

class AdoptPetDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdoptPetDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdoptPetDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val intss = intent
        val namePT = intss.getStringExtra("NAME")
        val agePT = intss.getStringExtra("AGE")
        val breedPT = intss.getStringExtra("BREED")
        val ownernamePT = intss.getStringExtra("OWNERNAME")
        val hometownPT = intss.getStringExtra("HOMETOWN")
        val imgPT = intss.getStringExtra("IMURI")

        binding.petdetailsName.text = namePT
        binding.petdetailsAge.text = agePT
        binding.petdetailsBreed.text = breedPT
        binding.petdetailsON.text = ownernamePT
        binding.petdetailsHometown.text = hometownPT
        binding.petdetailsImg.loadImage(imgPT)

        binding.adoptBtn.setOnClickListener {
            Toast.makeText(this, "Adopted!", Toast.LENGTH_SHORT).show()
            val handler = Handler()
            handler.postDelayed(
                {
                    startActivity(Intent(this, AdoptHomepageActivity::class.java))
                    finish()
                }, 2000
            )
        }

    }
}