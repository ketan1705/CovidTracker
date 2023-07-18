package android.com.example.covidtracker.adapter

import android.com.example.covidtracker.R
import android.com.example.covidtracker.model.Model
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.NumberFormat

class AdapterClass(var modelList:List<Model>): RecyclerView.Adapter<AdapterClass.ViewHolder>() {


    private var m = 1

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var txtCountry = itemView.findViewById<TextView>(R.id.txtCountry)
        var txtCases = itemView.findViewById<TextView>(R.id.txtCases)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        var view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item_layout,parent,false)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return modelList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var modelClass = modelList[position]
        holder.txtCountry.text = modelClass.country

        when(m){
            1-> holder.txtCases.text = NumberFormat.getInstance().format(modelClass.cases.toInt())
            2-> holder.txtCases.text = NumberFormat.getInstance().format(modelClass.active.toInt())
            3-> holder.txtCases.text = NumberFormat.getInstance().format(modelClass.recovered.toInt())
            else-> holder.txtCases.text = NumberFormat.getInstance().format(modelClass.deaths.toInt())

        }
        holder.itemView.setOnClickListener{

        }
    }

    fun filter (itemText: String){
        m = when(itemText){
            "cases"->1
            "active"->2
            "recoverd"->3
            else->4
        }
    }
}