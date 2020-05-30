package com.hnam.week6demo

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.core.content.FileProvider.getUriForFile
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    companion object{
        private val TAG = MainActivity::class.java.simpleName
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val API_KEY = "7519cb3f829ecd53bd9b7007076dbe23"
        const val REQUEST_IMAGE_CAPTURE = 1
        const val REQUEST_TAKE_PHOTO = 2
        const val APP_FILE_PROVIDER = "com.hnam.week6demo.fileprovider"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnTakePhoto.setOnClickListener {
            startAdvancedCameraIntent()
        }
        button2.setOnClickListener {
            getDataFromApi()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            imageView.setImageBitmap(imageBitmap)
        } else if (requestCode == REQUEST_TAKE_PHOTO && resultCode == Activity.RESULT_OK){
            photoURI?.let {
                Glide.with(this)
                    .load(it)
                    .into(imageView)
            }
        }
    }

    lateinit var currentPhotoPath: String


    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        )
    }

    private fun startBasicCameraIntent(){
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    lateinit var photoURI : Uri
    private fun startAdvancedCameraIntent(){
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    photoURI = getUriForFile(
                        this,
                        APP_FILE_PROVIDER,
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                }
            }
        }
    }

    fun getDataFromApi(){
        MovieService.getInstance().getApi().getTopRateMovie().enqueue(object : Callback<VideoResponse> {
            override fun onFailure(call: Call<VideoResponse>?, t: Throwable?) {
                //todo something
            }

            override fun onResponse(
                call: Call<VideoResponse>?,
                response: Response<VideoResponse>?
            ) {
                response?.let {
                    val resp = it.body()
                }
            }

        })
    }
}
