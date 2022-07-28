package com.dhruv.adopem.adapterfiles

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.dhruv.adopem.R
import com.dhruv.adopem.dataclassfiles.AdopemMeetupDataClass
import com.google.android.material.imageview.ShapeableImageView



class AdopemMeetupAdapter(var meetupContext: Context, private val meetupList: ArrayList<AdopemMeetupDataClass>) : RecyclerView.Adapter<AdopemMeetupAdapter.MeetupViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeetupViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_adopem_meetup,parent,false)
        return MeetupViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MeetupViewHolder, position: Int) {
        val currentItem = meetupList[position]
        holder.meetupLocationImg.setImageResource(currentItem.locationImg)
        holder.meetupName.text = currentItem.event_name
        holder.meetupDate.text = currentItem.date
        holder.meetupTime.text = currentItem.time
        holder.meetupPlace.text = currentItem.place
        holder.meetupOverview.text = currentItem.overview
        holder.meetupLocationImg.setOnClickListener{
            val url = "https://www.google.com/maps/search/?api=1&query="+currentItem.place
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            meetupContext.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
       return meetupList.size
    }

    class MeetupViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val meetupLocationImg : ShapeableImageView = itemView.findViewById(R.id.locationImg)
        val meetupName : TextView = itemView.findViewById(R.id.meetupname)
        val meetupDate : TextView = itemView.findViewById(R.id.meetupdate)
        val meetupTime : TextView = itemView.findViewById(R.id.meetuptime)
        val meetupPlace : TextView = itemView.findViewById(R.id.meetupplace)
        val meetupOverview : TextView = itemView.findViewById(R.id.meetupoverview)
    }
}