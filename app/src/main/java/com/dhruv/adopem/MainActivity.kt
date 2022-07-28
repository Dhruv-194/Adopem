package com.dhruv.adopem

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import com.dhruv.adopem.adapterfiles.ViewPagerAdapter
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var  viewPager: ViewPager

    private lateinit var viewPagerAdapter: ViewPagerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val launcherBtn = findViewById<ImageButton>(R.id.launcherBtn)
        viewPager = findViewById(R.id.viewPager)
        viewPagerAdapter = ViewPagerAdapter(this)
        viewPager.adapter = viewPagerAdapter
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            var lastPageChange : Boolean = false
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                var lastIdx : Int = viewPagerAdapter.count-1
            }

            override fun onPageSelected(position: Int) {
           //    Toast.makeText(applicationContext,""+position.toString(),Toast.LENGTH_SHORT).show()

                if (position==0){
                    launcherBtn.visibility = View.GONE
                }

                if (position==1){
                    launcherBtn.visibility = View.GONE
                }

                if(position==2){
                    launcherBtn.visibility = View.VISIBLE
                    launcherBtn.setOnClickListener{
                        startActivity(Intent(applicationContext, LoginActivity::class.java))
                        finish()
                    }



                }
            }

            override fun onPageScrollStateChanged(state: Int) {
              /*  var lastIdx : Int = viewPagerAdapter.count-1
                var curItem : Int = viewPager.currentItem
                if (curItem==lastIdx  && state ==1){
                    lastPageChange = true
                   startActivity(Intent(applicationContext, LoginActivity::class.java))
                    finish()
                }
                else{
                    lastPageChange = false
                }*/
            }
        })
    }

    override fun onStart() {
        super.onStart()
        val user = FirebaseAuth.getInstance().currentUser
        if(user!=null){
            startActivity(Intent(this,AdoptHomepageActivity::class.java))
            finish()
        }
    }

}