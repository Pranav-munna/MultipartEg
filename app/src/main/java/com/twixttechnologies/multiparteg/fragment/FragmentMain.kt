package com.twixttechnologies.multiparteg.fragment

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import com.twixttechnologies.multiparteg.Controller.ProcessResponceInterphase
import com.twixttechnologies.multiparteg.Controller.Request
import com.twixttechnologies.multiparteg.Controller.Responce
import com.twixttechnologies.multiparteg.R
import java.io.File


class FragmentMain : Fragment() {

    lateinit var imageview: ImageView
    lateinit var btnTest: Button
    lateinit var btnTest2: Button


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val mView = inflater.inflate(R.layout.fragment_main, container, false)
        imageview = mView.findViewById(R.id.imageview)
        btnTest = mView.findViewById(R.id.btn_test)
        btnTest2 = mView.findViewById(R.id.btn_test2)

        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(activity,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    1)
            ActivityCompat.requestPermissions(activity,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    2)

        }
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(activity,
                    arrayOf(Manifest.permission.CAMERA),
                    3)

        }

        btnTest.setOnClickListener({
            try {

                val cameraIntent = Intent(
                        android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, 10)

            } catch (e: Exception) {
            }


        })
        btnTest2.setOnClickListener({

            val pickPhoto = Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(pickPhoto, 0)
        })

        return mView
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        try {
            //gallery
            if (requestCode == 0 && resultCode == Activity.RESULT_OK) {

                val selectedImage = data.getData()
                imageview.setImageURI(selectedImage)

                val finalFile = File(getRealPathFromURI(selectedImage))
                val imgDecodableString = finalFile.toString()

                Request(activity, ResponceProcessor()).sendImage("title", "name", "image", File(imgDecodableString))
            }
            //camera
            if (requestCode == 10 && resultCode == Activity.RESULT_OK) {
                val bitmap = data.extras.get("data") as Bitmap
                imageview.setImageBitmap(bitmap)

                val selectedImage = getImageUri(activity.applicationContext, bitmap)
                val finalFile = File(getRealPathFromURI(selectedImage))
                val imgDecodableString = finalFile.toString()

                Request(activity, ResponceProcessor()).sendImage("title", "name", "image", File(imgDecodableString))
            }

            super.onActivityResult(requestCode, resultCode, data)
        } catch (e: Exception) {
        }

    }

    inner class ResponceProcessor : ProcessResponceInterphase<Responce> {
        override fun procssResponce(responce: Responce) {
            Log.e("look responce", responce.message)
        }
    }

    fun getImageUri(inContext: Context, inImage: Bitmap): Uri {
        val path = MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
    }

    fun getRealPathFromURI(uri: Uri): String {
        val cursor = activity.contentResolver.query(uri, null, null, null, null)
        cursor!!.moveToFirst()
        val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
        return cursor.getString(idx)
    }

}
