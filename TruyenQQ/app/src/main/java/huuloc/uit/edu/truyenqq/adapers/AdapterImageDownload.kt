package huuloc.uit.edu.truyenqq.adapers

import android.content.Context
import android.content.res.Resources
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import huuloc.uit.edu.truyenqq.R
import huuloc.uit.edu.truyenqq.database.ImageChap
import huuloc.uit.edu.truyenqq.database.ImageStorageManager
import java.io.IOException

class AdapterImageDownload(val context: Context, var list: List<ImageChap>?) : RecyclerView.Adapter<AdapterImageDownload.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_image, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        if (list.isNullOrEmpty()) return 0
        return list!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try{
            holder.image.scaleType = ImageView.ScaleType.FIT_XY
            holder.image.setImageBitmap(ImageStorageManager.getImageFromInternalStorage(context,list!![position].name))
        }
        catch (ex :IOException){

        }
    }

    fun updateData(items: List<ImageChap>?) {
        list = items
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var image = view.findViewById<ImageView>(R.id.imgImage)
    }
}