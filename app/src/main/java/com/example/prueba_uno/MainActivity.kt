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
import android.widget.*
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

       /* Edge.setOnClickListener {
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
        }*/
        //Declaramos
        val datosBasicos = arrayOf("BASICOS","Negativo","E.Grises", "Brillo", "Contraste", "Gamma","Rojo","Verde", "Azul")
        val adaptador1 = ArrayAdapter(this, android.R.layout.simple_spinner_item,datosBasicos)

        val datosConvolucion = arrayOf("CONVOLUCION", "Smoothing","Gaussian Blur","Sharpen","Mean Removal","Embossing","Edge Detection")
        val adaptador2 = ArrayAdapter(this, android.R.layout.simple_spinner_item,datosConvolucion)

        val datosExtras = arrayOf("EXTRAS","Amarillo", "Violeta", "Rosa", "Cafe", "Sobell", "Prewitt", "Kirsh")
        val adaptador3 = ArrayAdapter(this, android.R.layout.simple_spinner_item,datosExtras)

        //Referenciamos
        val spinner_basicos:Spinner = findViewById(R.id.filtrosBasicos)
        adaptador1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_basicos.adapter = adaptador1

        val spinner_convolucion:Spinner = findViewById(R.id.filtrosConvolucion)
        adaptador2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_convolucion.adapter = adaptador2

        val spinner_extras:Spinner = findViewById(R.id.filtrosExtras)
        adaptador3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_extras.adapter = adaptador3

        //Evento para escuchar la seleccion
        //SPINNER BASICO
        spinner_basicos.onItemSelectedListener=object:AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(
                    applicationContext,
                    "Sin selección Básicos",
                    Toast.LENGTH_SHORT
                ).show()
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val pos = parent?.getItemAtPosition(position)
                Toast.makeText(
                    applicationContext,
                    "Seleccionado: $pos Básicos",
                    Toast.LENGTH_SHORT
                ).show()
                //Vemos la opción del filtro

                if(pos == "Negativo"){
                    bitmap = imageSelect_iv.drawable.toBitmap(imageSelect_iv.width, imageSelect_iv.height, null)
                    imagen2 = ImageControler.Invert(bitmap)
                    imageSelect_iv.setImageBitmap(imagen2)
                }else if(pos == "E.Grises"){
                    bitmap = imageSelect_iv.drawable.toBitmap(imageSelect_iv.width, imageSelect_iv.height, null)
                    imagen2 = ImageControler.Grises(bitmap)
                    imageSelect_iv.setImageBitmap(imagen2)
                }else if(pos=="Brillo"){
                    bitmap = imageSelect_iv.drawable.toBitmap(imageSelect_iv.width, imageSelect_iv.height, null)
                    imagen2 = ImageControler.Brightness(bitmap)
                    imageSelect_iv.setImageBitmap(imagen2)
                }else if (pos=="Contraste"){
                    bitmap = imageSelect_iv.drawable.toBitmap(imageSelect_iv.width, imageSelect_iv.height, null)
                    imagen2 = ImageControler.Contrast(bitmap)
                    imageSelect_iv.setImageBitmap(imagen2)
                }else if (pos=="Gamma"){
                    bitmap = imageSelect_iv.drawable.toBitmap(imageSelect_iv.width, imageSelect_iv.height, null)
                    imagen2 = ImageControler.Gamma(bitmap)
                    imageSelect_iv.setImageBitmap(imagen2)
                }else if(pos=="Rojo"){
                    bitmap = imageSelect_iv.drawable.toBitmap(imageSelect_iv.width, imageSelect_iv.height, null)
                    imagen2 = ImageControler.RedFilter(bitmap)
                    imageSelect_iv.setImageBitmap(imagen2)
                }else if(pos=="Verde"){
                    bitmap = imageSelect_iv.drawable.toBitmap(imageSelect_iv.width, imageSelect_iv.height, null)
                    imagen2 = ImageControler.GreenFilter(bitmap)
                    imageSelect_iv.setImageBitmap(imagen2)
                }else if(pos=="Azul"){
                    bitmap = imageSelect_iv.drawable.toBitmap(imageSelect_iv.width, imageSelect_iv.height, null)
                    imagen2 = ImageControler.BlueFilter(bitmap)
                    imageSelect_iv.setImageBitmap(imagen2)
                }

            }
            //Fin del item Selected
        }//Fin del spinner

        //SPINNER DE CONVOLUCION
        spinner_convolucion.onItemSelectedListener=object:AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(
                    applicationContext,
                    "Sin selección Convolución",
                    Toast.LENGTH_SHORT
                ).show()
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val pos = parent?.getItemAtPosition(position)
                Toast.makeText(
                    applicationContext,
                    "Seleccionado: $pos Convolución",
                    Toast.LENGTH_SHORT
                ).show()
                //Vemos la opción del filtro

                if(pos == "Smoothing"){
                    bitmap = imageSelect_iv.drawable.toBitmap(imageSelect_iv.width, imageSelect_iv.height, null)
                    imagen2 = ImageControler.smoothing(bitmap)
                    imageSelect_iv.setImageBitmap(imagen2)
                }else if(pos == "Gaussian Blur"){
                    bitmap = imageSelect_iv.drawable.toBitmap(imageSelect_iv.width, imageSelect_iv.height, null)
                    imagen2 = ImageControler.GaussianBlur(bitmap)
                    imageSelect_iv.setImageBitmap(imagen2)
                }else if(pos=="Sharpen"){
                    bitmap = imageSelect_iv.drawable.toBitmap(imageSelect_iv.width, imageSelect_iv.height, null)
                    imagen2 = ImageControler.sharpen(bitmap)
                    imageSelect_iv.setImageBitmap(imagen2)
                }else if (pos=="Mean Removal"){
                    bitmap = imageSelect_iv.drawable.toBitmap(imageSelect_iv.width, imageSelect_iv.height, null)
                    imagen2 = ImageControler.meanremoval(bitmap)
                    imageSelect_iv.setImageBitmap(imagen2)
                }else if (pos=="Embossing"){
                    bitmap = imageSelect_iv.drawable.toBitmap(imageSelect_iv.width, imageSelect_iv.height, null)
                    imagen2 = ImageControler.embossing(bitmap)
                    imageSelect_iv.setImageBitmap(imagen2)
                }else if(pos=="Edge Detection"){
                    bitmap = imageSelect_iv.drawable.toBitmap(imageSelect_iv.width, imageSelect_iv.height, null)
                    imagen2 = ImageControler.edgedetection(bitmap)
                    imageSelect_iv.setImageBitmap(imagen2)
                }
            }
            //Fin del item Selected
        }//Fin del spinner

        //SPINNER EXTRAS
        spinner_extras.onItemSelectedListener=object:AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(
                    applicationContext,
                    "Sin selección Extras",
                    Toast.LENGTH_SHORT
                ).show()
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val pos = parent?.getItemAtPosition(position)
                Toast.makeText(
                    applicationContext,
                    "Seleccionado: $pos Extras",
                    Toast.LENGTH_SHORT
                ).show()
                //Vemos la opción del filtro

                if(pos == "Amarillo"){
                    bitmap = imageSelect_iv.drawable.toBitmap(imageSelect_iv.width, imageSelect_iv.height, null)
                    imagen2 = ImageControler.YellowFilter(bitmap)
                    imageSelect_iv.setImageBitmap(imagen2)
                }else if(pos == "Violeta"){
                    bitmap = imageSelect_iv.drawable.toBitmap(imageSelect_iv.width, imageSelect_iv.height, null)
                    imagen2 = ImageControler.VioletFilter(bitmap)
                    imageSelect_iv.setImageBitmap(imagen2)
                }else if(pos=="Rosa"){
                    bitmap = imageSelect_iv.drawable.toBitmap(imageSelect_iv.width, imageSelect_iv.height, null)
                    imagen2 = ImageControler.NewFilter(bitmap)
                    imageSelect_iv.setImageBitmap(imagen2)
                }else if (pos=="Cafe"){
                    bitmap = imageSelect_iv.drawable.toBitmap(imageSelect_iv.width, imageSelect_iv.height, null)
                    imagen2 = ImageControler.NewFilter2(bitmap)
                    imageSelect_iv.setImageBitmap(imagen2)
                }else if (pos=="Sobell"){
                    bitmap = imageSelect_iv.drawable.toBitmap(imageSelect_iv.width, imageSelect_iv.height, null)
                    imagen2 = ImageControler.sobell(bitmap)
                    imageSelect_iv.setImageBitmap(imagen2)
                }else if(pos=="Prewitt"){
                    bitmap = imageSelect_iv.drawable.toBitmap(imageSelect_iv.width, imageSelect_iv.height, null)
                    imagen2 = ImageControler.prewitt(bitmap)
                    imageSelect_iv.setImageBitmap(imagen2)
                }else if(pos=="Kirsh"){
                    bitmap = imageSelect_iv.drawable.toBitmap(imageSelect_iv.width, imageSelect_iv.height, null)
                    imagen2 = ImageControler.kirsh(bitmap)
                    imageSelect_iv.setImageBitmap(imagen2)
                }

            }
            //Fin del item Selected
        }//Fin del spinner

        guardar.setOnClickListener{
            bitmap = imageSelect_iv.drawable.toBitmap(imageSelect_iv.width, imageSelect_iv.height, null)
            var yourTitle = "ImageToGalery"
            val yourDescription = "ImagenGaleria"
           // MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, yourTitle , yourDescription);
            MediaStore.Images.Media.insertImage(contentResolver, bitmap, yourTitle , yourDescription);
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