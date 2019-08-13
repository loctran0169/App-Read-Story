package huuloc.uit.edu.truyenqq.adapers

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import huuloc.uit.edu.truyenqq.R
import java.lang.Exception

class AdapterImage(val context : Context, var list : List<String> )  : RecyclerView.Adapter<AdapterImage.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view : View= LayoutInflater.from(context).inflate(R.layout.item_image,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            Glide.with(context)
                .load(list[position])
                .into(holder.image)
        }
        catch (ex : Exception){

        }
    }
    fun updateData(items : List<String>){
        list=items
        notifyDataSetChanged()
    }
    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        var image = view.findViewById<ImageView>(R.id.imgImage)
    }
}