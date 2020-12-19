package com.example.prueba_uno

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

class TitleControl: LinearLayout {

    private lateinit var txtNameApp: TextView
    private lateinit var LogoApp: ImageView

    constructor(ctx: Context):super(ctx){
        inicializar()
    }
    constructor(ctx: Context, attrs: AttributeSet):super(ctx, attrs){
        inicializar()
    }
    constructor(ctx: Context, attrs: AttributeSet, defStyleAttr:Int):super(ctx, attrs, defStyleAttr){
        inicializar()
    }
    fun inicializar() {
        //Utilizamos el layout spinner_control
        val li = LayoutInflater.from(context)
        li.inflate(R.layout.name, this, true)

        //Referenciamos
        txtNameApp=findViewById(R.id.tituloApp)as TextView
        LogoApp=findViewById(R.id.logoApp)as ImageView

    }

}