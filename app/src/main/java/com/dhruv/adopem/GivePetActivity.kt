package com.dhruv.adopem

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dhruv.adopem.databinding.ActivityGivePetBinding
import com.dhruv.adopem.dataclassfiles.AdoptPetDataClass
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask


class GivePetActivity : AppCompatActivity() {
    private var mPetImageUri:Uri?=null
    private var mStorageReference: StorageReference?=null
    private var mDatabaseReference: DatabaseReference?=null
    private var mUploadTask: StorageTask<*>?=null
    private val PICK_IMAGE_REQUEST = 1
    private lateinit var binding: ActivityGivePetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGivePetBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        mStorageReference = FirebaseStorage.getInstance().getReference("pet_main_data")
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("pet_main_data")

        binding.petImageBtn.setOnClickListener {
            openChooseFile()
        }

        binding.submitButton.setOnClickListener{
            if(mUploadTask!=null && mUploadTask!!.isInProgress){
                Toast.makeText(this, "An upload is still in progress", Toast.LENGTH_SHORT).show()
            }
            else {
                uploadFile()
            }
        }
    }



    private fun openChooseFile() {
        val photoPickerIntent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        photoPickerIntent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        startActivityForResult(photoPickerIntent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
            if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data!=null && data.data!=null){
                mPetImageUri = data.data
                binding.bugImg.setImageURI(mPetImageUri)
                if(mPetImageUri == null){
                    Toast.makeText(this, "this is null", Toast.LENGTH_SHORT).show()
                }
                else if (mPetImageUri!=null)
                {  Toast.makeText(this, "this is not null", Toast.LENGTH_SHORT).show()

                }
            }
    }
    private fun getFileExtension(uri: Uri):String?{
        val cR = contentResolver
        val mime = MimeTypeMap.getSingleton()
        return mime.getExtensionFromMimeType(cR.getType(uri))
    }
    private fun uploadFile() {

        if (mPetImageUri!=null){
            val filereference = mStorageReference!!.child(
                System.currentTimeMillis().toString()+"."+getFileExtension(mPetImageUri!!))
            binding.petaddPB.visibility = View.VISIBLE
            binding.petaddPB.isIndeterminate = true
            mUploadTask = filereference.putFile(mPetImageUri!!).addOnSuccessListener { taskSnapshot->
                filereference.downloadUrl.addOnSuccessListener {
                    val handler = Handler()
                    handler.postDelayed(
                        {
                            binding.petaddPB.visibility = View.VISIBLE
                            binding.petaddPB.isIndeterminate = false
                            binding.petaddPB.progress =0
                        }, 500)
                    Toast.makeText(this, "Pet Data added successfully", Toast.LENGTH_SHORT).show()
                    val upload = AdoptPetDataClass(
                        binding.petnameInputEditText.text.toString().trim { it <= ' ' },
                        binding.petageInputEditText.text.toString().trim{ it<= ' '},
                        binding.petbreedInputEditText.text.toString().trim{ it<= ' '},
                        binding.ownerNameText.text.toString().trim{ it<= ' '},
                        binding.pethometownText.text.toString().trim{ it<= ' '},
                        mPetImageUri.toString()
                    )
                    val uploadID = mDatabaseReference!!.push().key
                    mDatabaseReference!!.child(uploadID!!).setValue(upload!!)
                    binding.petaddPB.visibility = View.INVISIBLE
                   openImageActivity()
                }

            } .addOnFailureListener{
                e->  binding.petaddPB.visibility = View.INVISIBLE
                Toast.makeText(this, e.message.toString(), Toast.LENGTH_SHORT).show()
            }
                .addOnProgressListener {
                taskSnapshot-> val nProgress = (100.0* taskSnapshot.bytesTransferred/taskSnapshot.totalByteCount)
                binding.petaddPB.progress = nProgress.toInt()
            }
        }else {
            Toast.makeText(this, "You haven't selected any images", Toast.LENGTH_SHORT).show()
        }
    }

   private fun openImageActivity() {
    val handler = Handler()
       handler.postDelayed(
           {
               startActivity(Intent(this, AdoptHomepageActivity::class.java))
               finish()
           }, 2000
       )

    }
}