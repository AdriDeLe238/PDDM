package com.example.prueba_uno

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.net.Uri
import android.provider.MediaStore
import java.io.File

object ImageControler {
    fun SelectPhotoFromGallery(activity: Activity, code : Int){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        activity.startActivityForResult(intent, code)
    }

    fun saveImage(context: Context, id: Long, uri: Uri){

       // MediaStore.Images.Media.insertImage(getContentResolver(), yourBitmap, yourTitle , yourDescription);

        val file = File(context.filesDir, id.toString())

        val bytes = context.contentResolver.openInputStream(uri)?.readBytes()!!

        file.writeBytes(bytes)
    }

    fun getImageUri(context: Context, id:Long):Uri {
        val file = File(context.filesDir, id.toString())
        return if (file.exists()) Uri.fromFile(file)
        else Uri.parse("android.resource://com.example.prueba_uno/drawable/imagen_no_disponible")
    }

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
        for(x in 0 until ancho){
            for (y in 0 until alto){
                colorPixel=btp.getPixel(x, y)
                A =Color.alpha(colorPixel)
                R = Color.red(colorPixel)+Brillo
                if (R<0){R=0}else if(R>255){R=255}
                G=Color.green(colorPixel)+Brillo
                if(G < 0) { G = 0} else if(G > 255) { G = 255 }
                B=Color.blue(colorPixel)+Brillo
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
        var ValorGamma=7.99
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

    fun YellowFilter(btp: Bitmap?):Bitmap?{
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
                //R=0
                G=Color.green(colorPixel)
                //G=0
                B=0
                //B=Color.blue(colorPixel)
                bitmap2.setPixel(x, y, Color.argb(A, R, G, B))
            }
        }
        btp.recycle()
        btp=null
        return bitmap2
    }

    fun VioletFilter(btp: Bitmap?):Bitmap?{
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
                //R=0
                //G=Color.green(colorPixel)
                G=0
                //B=0
                B=Color.blue(colorPixel)
                bitmap2.setPixel(x, y, Color.argb(A, R, G, B))
            }
        }
        btp.recycle()
        btp=null
        return bitmap2
    }

    fun NewFilter(btp: Bitmap?):Bitmap?{
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

                //194, 31, 254
                A =Color.alpha(colorPixel)

                //R=Color.red(colorPixel)
                R=194
                G=Color.green(colorPixel)
                //G=31
                B=254
                //B=Color.blue(colorPixel)

                bitmap2.setPixel(x, y, Color.argb(A, R, G, B))
            }
        }
        btp.recycle()
        btp=null
        return bitmap2
    }

    fun NewFilter2(btp: Bitmap?):Bitmap?{
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

                // 190, 125, 61
                A =Color.alpha(colorPixel)
                //R=Color.red(colorPixel)
                R=190
                //G=Color.green(colorPixel)
                G=125
                //B=254
                B=Color.blue(colorPixel)
                bitmap2.setPixel(x, y, Color.argb(A, R, G, B))
            }
        }
        btp.recycle()
        btp=null
        return bitmap2
    }

    fun smoothing(btp: Bitmap?): Bitmap? {
        val convolucion = ConvolucionMatriz()
        val array = arrayOf(
            intArrayOf(1, 1, 1),
            intArrayOf(1, 1, 1),
            intArrayOf(1, 1, 1)
        )
        convolucion.setMatriz(array)
        convolucion.Factor = 9
        convolucion.Offset = 0
        val bitmapConvertido : Bitmap = convolucion.convolucion(btp)
        return bitmapConvertido
    }

    fun GaussianBlur(btp: Bitmap?): Bitmap? {
        val convolucion = ConvolucionMatriz()
        val array = arrayOf(
            intArrayOf(1, 2, 1),
            intArrayOf(2, 4, 2),
            intArrayOf(1, 2, 1)
        )
        convolucion.setMatriz(array)
        convolucion.Factor = 16
        convolucion.Offset = 0
        val bitmapConvertido : Bitmap = convolucion.convolucion(btp)
        return bitmapConvertido
    }

    fun sharpen(btp: Bitmap?): Bitmap? {
        val convolucion = ConvolucionMatriz()
        val array = arrayOf(
            intArrayOf(0, -2, 0),
            intArrayOf(-2, 11, -2),
            intArrayOf(0, -2, 0)
        )
        convolucion.setMatriz(array)
        convolucion.Factor = 3
        convolucion.Offset = 0
        val bitmapConvertido : Bitmap = convolucion.convolucion(btp)
        return bitmapConvertido
    }

    fun meanremoval(btp: Bitmap?): Bitmap? {
        val convolucion = ConvolucionMatriz()
        val array = arrayOf(
            intArrayOf(-1, -1, -1),
            intArrayOf(-1, 9, -1),
            intArrayOf(-1, -1, -1)
        )
        convolucion.setMatriz(array)
        convolucion.Factor = 1
        convolucion.Offset = 300
        val bitmapConvertido : Bitmap = convolucion.convolucion(btp)
        return bitmapConvertido
    }

    fun embossing(btp: Bitmap?): Bitmap? { //checar este filtro
        val convolucion = ConvolucionMatriz()
        val array = arrayOf(
            intArrayOf(-1, 0, -1),
            intArrayOf(0, 4, 0),
            intArrayOf(-1, 0, -1)
        )
        convolucion.setMatriz(array)
        convolucion.Factor = 1
        convolucion.Offset = 300
        val bitmapConvertido : Bitmap = convolucion.convolucion(btp)
        return bitmapConvertido
    }

    fun edgedetection(btp: Bitmap?): Bitmap? { //checar este filtro
        val convolucion = ConvolucionMatriz()
        val array = arrayOf(
            intArrayOf(-1, -1, -1),
            intArrayOf(-1, 8, -1),
            intArrayOf(-1, -1, -1)
        )
        convolucion.setMatriz(array)
        convolucion.Factor = 1
        convolucion.Offset = 0
        val bitmapConvertido : Bitmap = convolucion.convolucion(btp)
        return bitmapConvertido
    }

    fun sobell(btp: Bitmap?): Bitmap? { //checar este filtro
        val convolucion = ConvolucionMatriz()
        val array = arrayOf(
            intArrayOf(1, 2, 1),
            intArrayOf(0, 0, 0),
            intArrayOf(-1, -2, -1)
        )
        convolucion.setMatriz(array)
        convolucion.Factor = 1
        convolucion.Offset = 0
        val bitmapConvertido : Bitmap = convolucion.convolucion(btp)
        return bitmapConvertido
    }

    fun prewitt(btp: Bitmap?): Bitmap? { //checar este filtro
        val convolucion = ConvolucionMatriz()
        val array = arrayOf(
            intArrayOf(1, 1, 1),
            intArrayOf(0, 0, 0),
            intArrayOf(-1, -1, -1)
        )
        convolucion.setMatriz(array)
        convolucion.Factor = 1
        convolucion.Offset = 0
        val bitmapConvertido : Bitmap = convolucion.convolucion(btp)
        return bitmapConvertido
    }

    fun kirsh(btp: Bitmap?): Bitmap? { //checar este filtro
        val convolucion = ConvolucionMatriz()
        val array = arrayOf(
            intArrayOf(5, 5, 5),
            intArrayOf(-3, -3, -3),
            intArrayOf(-3, -3, -3)
        )
        convolucion.setMatriz(array)
        convolucion.Factor = 1
        convolucion.Offset = 0
        val bitmapConvertido : Bitmap = convolucion.convolucion(btp)
        return bitmapConvertido
    }
}