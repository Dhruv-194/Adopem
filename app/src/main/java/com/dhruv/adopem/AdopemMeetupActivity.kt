package com.dhruv.adopem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dhruv.adopem.adapterfiles.AdopemMeetupAdapter
import com.dhruv.adopem.adapterfiles.PetCareTakersAdapter
import com.dhruv.adopem.dataclassfiles.AdopemMeetupDataClass
import com.dhruv.adopem.dataclassfiles.PetCareTakersDataClass

class AdopemMeetupActivity : AppCompatActivity() {

    private lateinit var newMeetupRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<AdopemMeetupDataClass>
    lateinit var MeetupLocImageId : Array<Int>
    lateinit var MeetupName: Array<String>
    lateinit var MeetupDate: Array<String>
    lateinit var MeetupTime: Array<String>
    lateinit var MeetupPlace: Array<String>
    lateinit var MeetupOverview: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adopem_meetup)

        MeetupLocImageId = arrayOf(
            R.drawable.location_img,
            R.drawable.location_img
        )
        MeetupName = arrayOf(
            "Event Name : Pet's Day-Out",
            "Event Name : Dog Run"
        )
        MeetupDate = arrayOf(
            "Date : 14/04/2022",
            "Date : 22/05/2022"
        )
        MeetupTime = arrayOf(
            "Date : 5pm - 8pm",
            "Date : 4pm - 6pm"
        )
        MeetupPlace = arrayOf(
            "Place :  Gandhi Nagar, Ahmedabad",
            "Place : Janakpuri, Old Delhi"
        )
        MeetupOverview = arrayOf(
            "Overview : Event to take your pets for a fun filled day-out event organized by Adopem Community",
            "Overview : Bring yours dogs for competing them in a fun race."
        )

        newMeetupRecyclerView = findViewById(R.id.adopemmeetuprecyclerview)
        newMeetupRecyclerView.layoutManager = LinearLayoutManager(this)
        newMeetupRecyclerView.setHasFixedSize(true)

        newArrayList= arrayListOf<AdopemMeetupDataClass>()
        getMeetupsData()

    }

    private fun getMeetupsData() {
        for( i in MeetupLocImageId.indices){
            val adopemmeetup  = AdopemMeetupDataClass(MeetupLocImageId[i], MeetupName[i], MeetupDate[i],MeetupTime[i],MeetupPlace[i], MeetupOverview[i])
            newArrayList.add(adopemmeetup)
        }

        newMeetupRecyclerView.adapter = AdopemMeetupAdapter(this, newArrayList)
    }
}