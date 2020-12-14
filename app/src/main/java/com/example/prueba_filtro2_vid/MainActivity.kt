package com.example.prueba_filtro2_vid

import android.app.Activity
import android.content.Intent
import android.graphics.*
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

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
        bitmap = BitmapFactory.decodeResource(resources, R.drawable.img2) //Sirve con la imagen fija
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

        val btnBrightness:Button = findViewById(R.id.Brightness)
        btnBrightness.setOnClickListener{
            //val brillo = 200
            //val contrast =
            //imagen2=bitmap.Brightness(bitmap)
            imagen2=Brightness(bitmap)
            imagen.setImageBitmap(imagen2)
        }

        val btnContrast:Button = findViewById(R.id.Contrast)
        btnContrast.setOnClickListener{
            imagen2=Contrast(bitmap)
            imagen.setImageBitmap(imagen2)
        }

        val btnGamma:Button = findViewById(R.id.Gamma)
        btnGamma.setOnClickListener{
            imagen2=Gamma(bitmap)
            imagen.setImageBitmap(imagen2)
        }

        val btnRed:Button = findViewById(R.id.Red)
        btnRed.setOnClickListener{
            imagen2=RedFilter(bitmap)
            imagen.setImageBitmap(imagen2)
        }

        val btnGreen:Button = findViewById(R.id.Green)
        btnGreen.setOnClickListener{
            imagen2=GreenFilter(bitmap)
            imagen.setImageBitmap(imagen2)
        }

        val btnBlue:Button = findViewById(R.id.Blue)
        btnBlue.setOnClickListener{
            imagen2=BlueFilter(bitmap)
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
                colorPixel=btp.getPixel(x, y)
                A=Color.alpha(colorPixel)
                R=255-Color.red(colorPixel)
                G=255-Color.green(colorPixel)
                B=255-Color.blue(colorPixel)
                bitmap2.setPixel(x, y, Color.argb(A, R, G, B))
            }
        }
        btp.recycle()
        btp=null //<------------
        return bitmap2
    }
    //-------------------------------------------------------------------------------

    fun Grises(btp: Bitmap?):Bitmap?{
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
        val bitmapRes=Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvasResult = Canvas(bitmapRes)
        val paint = Paint()
        val filtro = ColorMatrixColorFilter(colorGrisMatriz)
        paint.colorFilter=filtro
        canvasResult.drawBitmap(btp, 0f, 0f, paint)
        btp.recycle()
        btp=null
        return bitmapRes
    }
//------------------------------------------------------------------
    fun Brightness(btp: Bitmap?):Bitmap?{
        var btp = btp
        val bitmap2 = Bitmap.createBitmap(btp!!.width, btp.height, btp.config)
        var A:Int
        var R:Int
        var G:Int
        var B:Int
        var colorPixel:Int
        val ancho = btp.width
        val alto = btp.height
        var Brillo=128
        //--------------------

      //  c.drawBitmap(btp,0,0, Paint(Color.BLACK))
/*       var contrast:Double
        contrast = Math.pow((100 + value) / 100, 2.0) */
        //--------------------
        for(x in 0 until ancho){
            for (y in 0 until alto){
                colorPixel=btp.getPixel(x, y)

                A =Color.alpha(colorPixel)
                R = Color.red(colorPixel+Brillo)
                if (R<0){R=0}else if(R>255){R=255}
                G=Color.green(colorPixel+Brillo)
                if(G < 0) { G = 0} else if(G > 255) { G = 255 }
                B=Color.blue(colorPixel+Brillo)
                if(B < 0) { B = 0} else if(B > 255) { B = 255}
                bitmap2.setPixel(x, y, Color.argb(A, R, G, B))
            }
        }
        btp.recycle()
        btp=null //<------------
        return bitmap2
    }

    /*
    fun Bitmap.Brightness(
        bright = 0.0F,
        contrast = 1.0F
        ):Bitmap?{
        val bitmapJ=copy(Bitmap.Config.ARGB_8888, true)
        val paint = Paint()

        val matrix = ColorMatrix(
            floatArrayOf(
                contrast, 0f, 0f, 0f, bright,
                0f, contrast, 0f, 0f, bright,
                0f, 0f, contrast, 0f, bright,
                0f, 0f, 0f, 1f, 0f
            )
        )

        val filtro = ColorMatrixColorFilter(matrix)
        paint.colorFilter = filtro

        Canvas(bitmapJ).drawBitmap(this,0f, 0f, paint)
        return  bitmapJ

    }
*/
    fun Contrast(btp: Bitmap?):Bitmap? {
        var btp = btp
        val bitmap2 = Bitmap.createBitmap(btp!!.width, btp.height, btp.config)
        var A:Int
        var R:Int
        var G:Int
        var B:Int
        var colorPixel:Int
        val ancho = btp.width
        val alto = btp.height
        var value=50.0
        //--------------------
        val c = Canvas()
        c.setBitmap(bitmap2)
        //  c.drawBitmap(btp,0,0, Paint(Color.BLACK))

        var contrast:Double
        contrast = Math.pow((100 + value) / 100, 2.0);
        //--------------------
        for(x in 0 until ancho){
            for (y in 0 until alto){
                colorPixel=btp.getPixel(x, y)
                A =Color.alpha(colorPixel)
                R = Color.red(colorPixel)
                R = (((((R / 255.0) - 0.5) * contrast) + 0.5) * 255.0).toInt()
                if (R<0){R=0}else if(R>255){R=255}
                G=Color.green(colorPixel)
                G=(((((G / 255.0) - 0.5) * contrast) + 0.5) * 255.0).toInt()
                if(G < 0) { G = 0}
                else if(G > 255) { G = 255 }
                B=Color.blue(colorPixel)
                B=(((((B / 255.0) - 0.5) * contrast) + 0.5) * 255.0).toInt()
                if(B < 0) { B = 0}
                else if(B > 255) { B = 255}
                bitmap2.setPixel(x, y, Color.argb(A, R, G, B))
            }
        }
        btp.recycle()
        btp=null //<------------
        return bitmap2
    }

    fun Gamma(btp:Bitmap?):Bitmap?{
        var btp = btp
        val bitmap2 = Bitmap.createBitmap(btp!!.width, btp.height, btp.config)
        var A:Int
        var R:Int
        var R1:Double
        var G:Int
        var G1:Double
        var B:Int
        var B1:Double
        var colorPixel:Int
        val ancho = btp.width
        val alto = btp.height
        var ValorGamma=0.35
        var GammaCorrection:Double
        GammaCorrection=(1/ValorGamma)
        for(x in 0 until ancho){
            for (y in 0 until alto){
                colorPixel=btp.getPixel(x, y)

                A =Color.alpha(colorPixel)

                R1 = Color.red((colorPixel)/255).toDouble()
                R =(255* (Math.pow(R1,GammaCorrection))).toInt()
                //if (R<0){R=0}else if(R>255){R=255}

                G1=Color.green((colorPixel)/255).toDouble()
                G=(255* (Math.pow(G1,GammaCorrection))).toInt()
                //if(G < 0) { G = 0} else if(G > 255) { G = 255 }

                B1=Color.blue((colorPixel)/255).toDouble()
                B=(255* (Math.pow(B1,GammaCorrection))).toInt()
                //if(B < 0) { B = 0} else if(B > 255) { B = 255}

                bitmap2.setPixel(x, y, Color.argb(A, R, G, B))
            }
        }
        btp.recycle()
        btp=null
        return bitmap2
    }

    fun RedFilter(btp:Bitmap?):Bitmap{
        var btp = btp
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
                colorPixel=btp.getPixel(x, y)

                A =Color.alpha(colorPixel)

                R=Color.red(colorPixel)
                G=0//255-Color.green(colorPixel)
                B=0//255-Color.blue(colorPixel)

                bitmap2.setPixel(x, y, Color.argb(A, R, G, B))
            }
        }
        btp.recycle()
        btp=null
        return bitmap2
    }

    fun GreenFilter(btp: Bitmap?):Bitmap?{
        var btp = btp
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
                colorPixel=btp.getPixel(x, y)

                A =Color.alpha(colorPixel)

                R=0//255-Color.red(colorPixel)
                G=Color.green(colorPixel)
                B=0//255-Color.blue(colorPixel)

                bitmap2.setPixel(x, y, Color.argb(A, R, G, B))
            }
        }
        btp.recycle()
        btp=null
        return bitmap2
    }

    fun BlueFilter(btp: Bitmap?):Bitmap?{
        var btp = btp
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
                colorPixel=btp.getPixel(x, y)

                A =Color.alpha(colorPixel)

               //R=255-Color.red(colorPixel)
                R=0
                //G=255-Color.green(colorPixel)
                G=0
                B=Color.blue(colorPixel)

                bitmap2.setPixel(x, y, Color.argb(A, R, G, B))
            }
        }
        btp.recycle()
        btp=null
        return bitmap2
    }
}