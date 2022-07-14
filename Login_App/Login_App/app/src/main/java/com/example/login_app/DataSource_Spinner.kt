package com.example.login_app

data class DataSource_Spinner(val image: Int, val name: String)

object DataSource{
    private val myimages = intArrayOf(
        R.drawable.icon_gps_last,
        R.drawable.icon_gps_live,
        R.drawable.icon_data_usage
    )

    private val myvalues = arrayOf(
        "Elemento 1",
        "Elemento 2",
        "Agregar Elemento"
    )

    var list: ArrayList<DataSource_Spinner>? = null
        get(){
            if (field != null)
                return field

            field = ArrayList()

            for (i in myimages.indices){
                val imageId = myimages[i]
                val imageName = myvalues[i]

                val element = DataSource_Spinner(imageId, imageName)
                field!!.add(element)
            }
            return field
        }
}