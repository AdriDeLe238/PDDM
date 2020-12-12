package com.example.prueba_filtro2_vid

import android.app.Activity
import android.content.Intent
import android.graphics.*
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.provider.MediaStore
import android.view.View

class MainActivity : AppCompatActivity() {

    lateinit var imagen: ImageView
    lateinit var bitmap: Bitmap
     lateinit var urlCompleta:Unit //<------------------------------------
    //var canvas: Canvas = Canvas(bitmap);

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var imagen2: Bitmap?

        imagen = findViewById(R.id.img)
        title = "ImageView to Bitmap"
        bitmap = BitmapFactory.decodeResource(resources, R.drawable.img_prueba) //Sirve con la imagen fija
        //bitmap = BitmapFactory.decodeResource(resources, R.id.img) // No sirve
       // bitmap = BitmapFactory.decodeResource(resources,urlCompleta ) //Prueba de dirección completa
        imagen.setImageBitmap(bitmap)


        val btnInvert: Button = findViewById(R.id.invertido)
        btnInvert.setOnClickListener{
            imagen2 = Invert(bitmap)
            imagen.setImageBitmap(imagen2)
        }

        val btnGray: Button = findViewById(R.id.gray)
        btnGray.setOnClickListener{
            imagen2 = Grises(bitmap)
            imagen.setImageBitmap(imagen2)
        }

}
    //--------------------------Carga de imagenes desde la galeria-------------------
    fun onclick(view: View) {
        cargarImagen()
    }
    fun cargarImagen() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/"
        startActivityForResult(Intent.createChooser(intent, "Seleccione la aplicacion"), 10)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode === Activity.RESULT_OK) {
            val path: Uri? = data?.data
            //urlCompleta =  imagen.setImageURI(path)
           // urlCompleta =path //<------------------
            imagen.setImageURI(path)
        }
    }
    //-------------------------------------------------------------------------------
    //-------------------Filtro_Invert-----------------------------------------------
    fun Invert(btp: Bitmap?):Bitmap? {
        var btp =btp
        val bitmap2 = Bitmap.createBitmap(btp!!.width, btp.height, btp.config)
        var A:Int
        var R:Int
        var G:Int
        var B:Int
        var colorPixel:Int
        val ancho = btp.width
        val alto = btp.height

        for(y in 0 until alto){
            for (x in 0 until ancho){
                colorPixel=btp.getPixel(x,y)
                A=Color.alpha(colorPixel)
                R=255-Color.red(colorPixel)
                G=255-Color.green(colorPixel)
                B=255-Color.blue(colorPixel)
                bitmap2.setPixel(x,y,Color.argb(A,R,G,B))
            }
        }
        btp.recycle()
        btp=null //<------------
        return bitmap2
        /*
        bitmap?.apply{
            //mostramos el filtro
            val ancho = bitmap.width
            val alto = bitmap.height
            val nAncho = ancho*3


            //Aqui va algo más
            var p: ByteArray = bitmap.ninePatchChunk
            var y=0
            var x=0
            while(y < alto){
                while(x<ancho){
                    p[0] = (Byte)(255-p[0])
                }
                y++
            }
         */
    }
    //-------------------------------------------------------------------------------

    fun Grises(btp:Bitmap?):Bitmap?{
        var btp=btp
        val GArray= floatArrayOf(
            0.213f, 0.715f, 0.072f, 0.0f, 0.0f,
            0.213f, 0.715f, 0.072f, 0.0f, 0.0f,
            0.213f, 0.715f, 0.072f, 0.0f, 0.0f,
            0.0f, 0.0f, 0.0f, 1.0f, 0.0f
        )
        val colorGrisMatriz = ColorMatrix(GArray)
        val width=btp!!.width
        val height=btp.height
        val bitmapRes=Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888)
        val canvasResult = Canvas(bitmapRes)
        val paint = Paint()
        val filtro = ColorMatrixColorFilter(colorGrisMatriz)
        paint.colorFilter=filtro
        canvasResult.drawBitmap(btp,0f,0f,paint)
        btp.recycle()
        btp=null
        return bitmapRes

    }
/*
    fun Invert2(btp2:ImageView?):Bitmap?{

        bitmap = BitmapFactory.decodeResource(resources, btp2)

        var btp =bitmap

        val bitmap2 = Bitmap.createBitmap(btp!!.width, btp.height, btp.config)
        var A:Int
        var R:Int
        var G:Int
        var B:Int
        var colorPixel:Int
        val ancho = btp.width
        val alto = btp.height

        for(y in 0 until alto){
            for (x in 0 until ancho){
                colorPixel=btp.getPixel(x,y)
                A=Color.alpha(colorPixel)
                R=255-Color.red(colorPixel)
                G=255-Color.green(colorPixel)
                B=255-Color.blue(colorPixel)
                bitmap2.setPixel(x,y,Color.argb(A,R,G,B))
            }
        }
        btp.recycle()
        btp=null //<------------
        return bitmap2
    }
*/
}