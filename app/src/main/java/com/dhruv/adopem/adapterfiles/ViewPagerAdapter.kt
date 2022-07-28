package com.dhruv.adopem.adapterfiles

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.dhruv.adopem.R

class ViewPagerAdapter(val context: Context) : PagerAdapter() {
    var layoutInflater: LayoutInflater?=null

    val imgArray = arrayOf(
        R.drawable.dog1,
        R.drawable.dog2,
        R.drawable.dog3
    )

    val headArray = arrayOf(
        "Welcome to Adopem",
        "Adopt your dream pet here",
        "Let's go!"
    )

    val description = arrayOf(
                "One stop solution for all of your pet needs",
                "A network that provide strays a new home",
                "Now lets find you a pawsome friend"
    )

    override fun getCount(): Int {
    return imgArray.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return  view ==  `object` as RelativeLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater!!.inflate(R.layout.slider,container,false)

        val img = view.findViewById<ImageView>(R.id.launcherImage)
        val txtHead = view.findViewById<TextView>(R.id.launcherHeading)
        val txtdesc = view.findViewById<TextView>(R.id.launcherDesc)

        img.setImageResource(imgArray[position])
        txtHead.text = headArray[position]
        txtdesc.text = description[position]

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as RelativeLayout)
    }
}