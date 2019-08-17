package huuloc.uit.edu.truyenqq.adapers

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import huuloc.uit.edu.truyenqq.R

class AdapterImage(val context: Context, var list: List<String>) : RecyclerView.Adapter<AdapterImage.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_image, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            val width = Resources.getSystem().displayMetrics.widthPixels
            val height = Resources.getSystem().displayMetrics.heightPixels
            Glide.with(context)
                .load(list[position])
                .override(width, height + height)
                .error(R.drawable.ic_errorload)
                .into(holder.image)
        } catch (ex: Exception) {

        }
    }

    fun updateData(items: List<String>) {
        list = items
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var image = view.findViewById<ImageView>(R.id.imgImage)
    }
}