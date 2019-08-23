package huuloc.uit.edu.truyenqq.adapers

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.chrisbanes.photoview.PhotoView
import huuloc.uit.edu.truyenqq.R
import huuloc.uit.edu.truyenqq.database.ImageChap
import huuloc.uit.edu.truyenqq.database.ImageStorageManager
import java.io.IOException

class AdapterImageDownload(val context: Context, var list: List<ImageChap>?) :
    RecyclerView.Adapter<AdapterImageDownload.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_image, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        if (list.isNullOrEmpty()) return 0
        return list!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            holder.image.setImageBitmap(ImageStorageManager.getImageFromInternalStorage(context, list!![position].name))
        } catch (ex: IOException) {

        }
    }

    fun updateData(items: List<ImageChap>?) {
        list = items
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var image = view.findViewById<PhotoView>(R.id.imgImage)
    }
}
