package com.dhruv.adopem.adapterfiles

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dhruv.adopem.AdoptPetDetailsActivity
import com.dhruv.adopem.R
import com.dhruv.adopem.dataclassfiles.AdoptPetDataClass
import com.dhruv.adopem.loadImage

class AdoptPetAdapter(var mContext: Context, var adoptpetList:List<AdoptPetDataClass>): RecyclerView.Adapter<AdoptPetAdapter.ListViewHolder>()
{
    inner class ListViewHolder(var v: View): RecyclerView.ViewHolder(v){
        var imgP = v.findViewById<ImageView>(R.id.itemPetImg)
        var nameP = v.findViewById<TextView>(R.id.itemPetName)
        var ageP = v.findViewById<TextView>(R.id.itemPetAge)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        var inflater = LayoutInflater.from(parent.context)
        var v =inflater.inflate(R.layout.item_petlist_main, parent, false)
        return ListViewHolder(v)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        var currentPos = adoptpetList[position]
        holder.nameP.text = currentPos.name
        holder.ageP.text = currentPos.age
        holder.imgP.loadImage(currentPos.petimageurl)
        val name = currentPos.name
        val age = currentPos.age
        val breed = currentPos.breed
        val ownername = currentPos.ownername
        val hometown = currentPos.hometown
        val imuri  = currentPos.petimageurl
        holder.v.setOnClickListener{
            val mIntent = Intent(mContext, AdoptPetDetailsActivity::class.java)
            mIntent.putExtra("NAME", name )
            mIntent.putExtra("AGE", age )
            mIntent.putExtra("BREED", breed)
            mIntent.putExtra("OWNERNAME", ownername )
            mIntent.putExtra("HOMETOWN", hometown )
            mIntent.putExtra("IMURI", imuri )
            mContext.startActivity(mIntent)
        }
    }

    override fun getItemCount(): Int {
     return adoptpetList.size
    }
}