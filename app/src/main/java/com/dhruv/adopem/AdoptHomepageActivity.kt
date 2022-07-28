package com.dhruv.adopem

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.utils.widget.ImageFilterView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhruv.adopem.adapterfiles.AdoptPetAdapter
import com.dhruv.adopem.databinding.ActivityAdoptHomepageBinding
import com.dhruv.adopem.dataclassfiles.AdoptPetDataClass
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import java.util.*
import kotlin.collections.ArrayList
import android.widget.ListAdapter as ListAdapter1


class AdoptHomepageActivity : AppCompatActivity() {
    var mFusedLocationClient: FusedLocationProviderClient? = null
    var PERMISSION_ID = 44
    var locLat: Double =0.0
    var locLong: Double =0.0
    private lateinit var tvLocation :TextView
    private lateinit var binding: ActivityAdoptHomepageBinding
    private lateinit var mypopwindow : PopupWindow
    private var mStorage:FirebaseStorage?=null
    private var mDatabaseRef:DatabaseReference?=null
    private var mDBListener: ValueEventListener?= null
    private lateinit var mpetadoptList:MutableList<AdoptPetDataClass>
    private lateinit var petadoptListAdapter: AdoptPetAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdoptHomepageBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        tvLocation = findViewById(R.id.textLocation)
        getCurrLocation()

        //PopMenu Code starts
        val background: Drawable = ContextCompat.getDrawable(this,R.drawable.menuround)!!
        val viewMenu = layoutInflater.inflate(R.layout.pop_menu_layout,null)
        mypopwindow = PopupWindow(this)
        mypopwindow.width = WindowManager.LayoutParams.WRAP_CONTENT
        mypopwindow.height = WindowManager.LayoutParams.WRAP_CONTENT
        mypopwindow.isFocusable = true
        mypopwindow.setOutsideTouchable(true);

        mypopwindow.contentView = viewMenu
        mypopwindow.setBackgroundDrawable(background)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mypopwindow.elevation = 20F;
        }
        val menubtn : ImageButton = findViewById(R.id.menuBtn)
        menubtn.setOnClickListener{
            mypopwindow.showAsDropDown(menubtn,-152,100)
        }
        //PopMenu Code ends


        val locationIconImg : ImageFilterView = findViewById(R.id.locationIcon)
        locationIconImg.setOnClickListener{

            val geocoder = Geocoder(this, Locale.getDefault())
            val addresses: List<Address> = geocoder.getFromLocation(locLat, locLong, 1)
            val cityName: String = addresses[0].locality
            val countryName: String = addresses[0].countryName
            tvLocation.text = "$cityName, $countryName"
        }

        val gotoPetShelter : TextView = viewMenu.findViewById(R.id.petshelterBtn)
        gotoPetShelter.setOnClickListener{
            val strUri = "geo:$locLat,$locLong?q=pet shelters"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(strUri))

            intent.setClassName(
                "com.google.android.apps.maps",
                "com.google.android.maps.MapsActivity"
            )

            startActivity(intent)
            mypopwindow.dismiss()
        }

        val gotoPetStores : TextView = viewMenu.findViewById(R.id.petstoreBtn)
        gotoPetStores.setOnClickListener{
            val strUri = "geo:$locLat,$locLong?q=pet stores"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(strUri))

            intent.setClassName(
                "com.google.android.apps.maps",
                "com.google.android.maps.MapsActivity"
            )

            startActivity(intent)
            mypopwindow.dismiss()
        }

        val gotoVetDoctors : TextView = viewMenu.findViewById(R.id.vetBtn)
        gotoVetDoctors.setOnClickListener{
            val strUri = "geo:$locLat,$locLong?q=veterinary doctors"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(strUri))

            intent.setClassName(
                "com.google.android.apps.maps",
                "com.google.android.maps.MapsActivity"
            )

            startActivity(intent)
            mypopwindow.dismiss()
        }

        val gotoPetCareTakers : TextView = viewMenu.findViewById(R.id.petcareBtn)
        gotoPetCareTakers.setOnClickListener{

            val intent = Intent(this, PetCareTakersAcitivty::class.java)
            startActivity(intent)
            mypopwindow.dismiss()
        }

        val gotoAdopemMeetup : TextView = viewMenu.findViewById(R.id.meetupBtn)
        gotoAdopemMeetup.setOnClickListener{

            val intent = Intent(this, AdopemMeetupActivity::class.java)
            startActivity(intent)
            mypopwindow.dismiss()
        }

        val gotoGivePet : TextView = viewMenu.findViewById(R.id.giveapet)
        gotoGivePet.setOnClickListener {
            val intent = Intent(this, GivePetActivity::class.java)
            startActivity(intent)
            mypopwindow.dismiss()
        }

        val logoutBtn : TextView = viewMenu.findViewById(R.id.logoutBtn)
        logoutBtn.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            Toast.makeText(this,"Logged Out", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()

            mypopwindow.dismiss()
        }

        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.petlistPB.visibility = View.VISIBLE
        mpetadoptList = ArrayList()
        petadoptListAdapter = AdoptPetAdapter(this, mpetadoptList)
        binding.recyclerView.adapter = petadoptListAdapter

        mStorage = FirebaseStorage.getInstance()
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("pet_main_data")
        mDBListener = mDatabaseRef!!.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                mpetadoptList.clear()
                for (petSnapshot in snapshot.children){
                    val upload  = petSnapshot.getValue(AdoptPetDataClass::class.java)
                    upload!!.key = petSnapshot.key
                    mpetadoptList.add(upload)
                }
                petadoptListAdapter.notifyDataSetChanged()
                binding.petlistPB.visibility = View.GONE
            }

            override fun onCancelled(error: DatabaseError) {
                binding.petlistPB.visibility = View.INVISIBLE
                Toast.makeText(applicationContext, "Error: "+ error.message.toString(), Toast.LENGTH_SHORT).show()
            }

        })

    }

    override fun onDestroy() {
        super.onDestroy()
        mDatabaseRef!!.removeEventListener(mDBListener!!)
    }

    private fun getCurrLocation() {
        if(checkPerm())
        {
            if(isLocationEnable()){
            //we get the lat and long
                if (ActivityCompat.checkSelfPermission(
                        this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                        requestPermission()
                    return
                }
                mFusedLocationClient?.lastLocation?.addOnCompleteListener(this){ task->
                    val location:Location?=task.result
                    if (location==null) {
                        Toast.makeText(this,"Null Location",Toast.LENGTH_SHORT).show()
                    } else{
                        //Toast.makeText(this,"Location Set",Toast.LENGTH_SHORT).show()
                        locLat = location.latitude
                        locLong = location.longitude
                       //tvLat.text = locLat.toString()
                    }
                }
            }
            else {
                //open settings
                Toast.makeText(applicationContext,"Turn on location",Toast.LENGTH_SHORT).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        }
        else {
            //request permission
            requestPermission()
        }
    }

    private fun isLocationEnable():Boolean{
        val locationManager:LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)||locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this,
            arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION),PERMISSION_ID)

    }

    private fun checkPerm(): Boolean {
        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION)==PackageManager.PERMISSION_GRANTED){
            return true
        }
        return false
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if ( requestCode==PERMISSION_ID)
        {
            if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(applicationContext,"Granted", Toast.LENGTH_SHORT).show()
                getCurrLocation()
            }
            else{
                Toast.makeText(applicationContext,"Not Granted", Toast.LENGTH_SHORT).show()
            }
        }
    }

}