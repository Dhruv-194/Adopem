package com.dhruv.adopem.adapterfiles

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.dhruv.adopem.R
import com.dhruv.adopem.dataclassfiles.PetCareTakersDataClass
import com.google.android.material.imageview.ShapeableImageView


class PetCareTakersAdapter(var pctContext: Context, private val petcaretakerList: ArrayList<PetCareTakersDataClass>) :
    RecyclerView.Adapter<PetCareTakersAdapter.PetCareTakersViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetCareTakersViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_petcaretaker,parent,false)
        return PetCareTakersViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PetCareTakersViewHolder, position: Int) {
        val currentItem = petcaretakerList[position]
        holder.pctprofileImg.setImageResource(currentItem.profileImg)
        holder.pctName.text = currentItem.name
        holder.pctAddress.text = currentItem.address
        holder.pctOverview.text = currentItem.overview
        holder.pctPhoneCall.setOnClickListener{
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:"+currentItem.phnumber.toString())
            pctContext.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return petcaretakerList.size
    }

    class PetCareTakersViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val pctprofileImg : ShapeableImageView = itemView.findViewById(R.id.caretakerImg)
        val pctName: TextView = itemView.findViewById(R.id.petcaretakername)
        val pctAddress: TextView = itemView.findViewById(R.id.petcaretakeraddress)
        val pctOverview: TextView = itemView.findViewById(R.id.petcaretakeroverview)
        val pctPhoneCall: ImageView = itemView.findViewById(R.id.petcaretakercall)



    }

}