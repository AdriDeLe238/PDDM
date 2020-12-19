package com.example.prueba_uno

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.Image
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.drawToBitmap
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    lateinit var imagen: ImageView //antes del override
    lateinit var bitmap: Bitmap
    private val SELECT_ACTIVITY = 50
    private var imageUri : Uri? = null
    lateinit var ancho : Integer
    lateinit var alto : Integer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var imagen2: Bitmap?
        imageSelect_iv.setOnClickListener{
            ImageControler.SelectPhotoFromGallery(this, SELECT_ACTIVITY)
        }

        Edge.setOnClickListener {
            bitmap = imageSelect_iv.drawable.toBitmap(imageSelect_iv.width, imageSelect_iv.height, null)
            imagen2 = ImageControler.edgedetection(bitmap)
            imageSelect_iv.setImageBitmap(imagen2)
        }

        //filtros de covolucion
        smoothing.setOnClickListener {
            bitmap = imageSelect_iv.drawable.toBitmap(imageSelect_iv.width, imageSelect_iv.height, null)
            imagen2 = ImageControler.smoothing(bitmap)
            imageSelect_iv.setImageBitmap(imagen2)
        }
        GB.setOnClickListener {
            bitmap = imageSelect_iv.drawable.toBitmap(imageSelect_iv.width, imageSelect_iv.height, null)
            imagen2 = ImageControler.GaussianBlur(bitmap)
            imageSelect_iv.setImageBitmap(imagen2)
        }

        sharpen.setOnClickListener {

            bitmap = imageSelect_iv.drawable.toBitmap(imageSelect_iv.width, imageSelect_iv.height, null)
            imagen2 = ImageControler.sharpen(bitmap)
            imageSelect_iv.setImageBitmap(imagen2)
        }

        MR.setOnClickListener {
            bitmap = imageSelect_iv.drawable.toBitmap(imageSelect_iv.width, imageSelect_iv.height, null)
            imagen2 = ImageControler.meanremoval(bitmap)
            imageSelect_iv.setImageBitmap(imagen2)
        }
        embossing.setOnClickListener{
            bitmap = imageSelect_iv.drawable.toBitmap(imageSelect_iv.width, imageSelect_iv.height, null)
            imagen2 = ImageControler.embossing(bitmap)
            imageSelect_iv.setImageBitmap(imagen2)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when{
            requestCode == SELECT_ACTIVITY && resultCode == Activity.RESULT_OK ->{
                imageUri = data!!.data //de aqui se obtiene la ruta de la imagen
               imageSelect_iv.setImageURI(imageUri)//esto se usa para mostrar la imagen en la aplicacion
            }
        }
    }


}