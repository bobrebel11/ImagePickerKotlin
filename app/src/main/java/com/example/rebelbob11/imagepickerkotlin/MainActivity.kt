 package com.example.rebelbob11.imagepickerkotlin

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.widget.Toast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.IOException




 class MainActivity : AppCompatActivity() {

     lateinit var photoPath:String
     val PICK_CODE = 100
     val REQUEST_TAKE_PHOTO = 200


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_select.setOnClickListener {


            openGallery();
        }

        btn_capture.setOnClickListener {

            openCamera();
        }
    }

     private fun openCamera() {



         val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
         if (cameraIntent.resolveActivity(packageManager)!=null){

             var photoFile:File? = null
             try {
                 photoFile = createImageFile()

             }
             catch (e: IOException){

             }

             if (photoFile !=null){

                 val photoUri = FileProvider.getUriForFile(
                         this,
                         "com.example.android.fileprovider",
                         photoFile
                 )

                 cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri)
                 startActivityForResult(cameraIntent,REQUEST_TAKE_PHOTO)
             }


         }
     }



     private fun createImageFile(): File? {

         val fileName = "Mypicture"
         val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
         val image = File.createTempFile(
                 fileName,
                 ".jpg",
                 storageDir
         )

         photoPath = image.absolutePath

         return image

     }

     private fun openGallery() {

         Toast.makeText(applicationContext,"Select pressed",Toast.LENGTH_SHORT).show();

         val pickIntent = Intent()
         pickIntent.type = "image/*"
         pickIntent.action = Intent.ACTION_GET_CONTENT
         startActivityForResult(Intent.createChooser(pickIntent,"Select Image"), PICK_CODE)

     }

     override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
         super.onActivityResult(requestCode, resultCode, data)

         if (PICK_CODE == 100 && resultCode == Activity.RESULT_OK && data != null && data.data != null) run {

             val uri: Uri = data.data



             Picasso.get().load(uri).resize(450, 450).centerCrop().into(profilePic)

//             val bitmap:Bitmap = MediaStore.Images.Media.getBitmap(contentResolver,uri)
//             profilePic.setImageBitmap(bitmap)
         }

         if(REQUEST_TAKE_PHOTO == 200 && resultCode == Activity.RESULT_OK){

             Toast.makeText(applicationContext,"Camera pressed",Toast.LENGTH_SHORT).show();


         }
     }
 }
