package com.dhruv.adopem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dhruv.adopem.adapterfiles.PetCareTakersAdapter
import com.dhruv.adopem.dataclassfiles.PetCareTakersDataClass

class PetCareTakersAcitivty : AppCompatActivity() {

    private lateinit var newPctRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<PetCareTakersDataClass>
    lateinit var PctImageId : Array<Int>
    lateinit var PctName: Array<String>
    lateinit var PctAddress: Array<String>
    lateinit var PctOverview: Array<String>
    lateinit var PctPhoneNumber: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pet_care_takers_acitivty)

        PctImageId = arrayOf(
            R.drawable.profile_placeholder_img,
            R.drawable.profile_placeholder_img
        )
        PctName = arrayOf(
            "Name : Naman Jain",
            "Name : Reena Singh"
        )
        PctAddress = arrayOf(
            "Address : Gandhi Nagar, Ahmedabad",
            "Address : Janakpuri, Old Delhi"
        )
        PctOverview = arrayOf(
            "Overview : He is a good pet care taker and a hardworking person.",
            "Overview : She is a very diligent worker and can take a good care of your pet"
        )

        PctPhoneNumber = arrayOf(
           "9754570456",
            "7488726835"
        )

        newPctRecyclerView = findViewById(R.id.petcaretakerrecyclerview)
        newPctRecyclerView.layoutManager = LinearLayoutManager(this)
        newPctRecyclerView.setHasFixedSize(true)

        newArrayList= arrayListOf<PetCareTakersDataClass>()
        getPetCareTakersData()
    }

    private fun getPetCareTakersData() {
        for( i in PctImageId.indices){
            val petcaretaker  = PetCareTakersDataClass(PctImageId[i], PctName[i], PctAddress[i], PctOverview[i], PctPhoneNumber[i])
            newArrayList.add(petcaretaker)
        }

        newPctRecyclerView.adapter = PetCareTakersAdapter(this,newArrayList)
    }
}