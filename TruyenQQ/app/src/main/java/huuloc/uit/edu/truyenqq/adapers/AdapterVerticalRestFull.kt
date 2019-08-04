package huuloc.uit.edu.truyenqq.adapers

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.siyamed.shapeimageview.RoundedImageView
import huuloc.uit.edu.truyenqq.R
import huuloc.uit.edu.truyenqq.data.StoryInformation

class AdapterVerticalRestFull(var context: Context, var items: List<StoryInformation>) :
    RecyclerView.Adapter<AdapterVerticalRestFull.BaseItem>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseItem {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_story_vertical, null)
        return BaseItem(view)
    }

    override fun getItemCount(): Int {
        if (items.isNullOrEmpty()) return 0
        return items.size
    }

    override fun onBindViewHolder(holder: BaseItem, position: Int) {
        val p0 = items[position]
        holder.itemName.text = p0.name
        Glide.with(context)
            .load("http://i.mangaqq.com/ebook/190x247/" + p0.image + "?thang=t2121")
            .into(holder.itemImage)
        holder.itemTime.text = p0.modified
    }

    fun updateData(list: List<StoryInformation>) {
        items = list
        notifyDataSetChanged()
    }

    inner class BaseItem(view: View) : RecyclerView.ViewHolder(view) {
        val itemImage = view.findViewById<RoundedImageView>(R.id.imgStoryItemVertical)
        val itemName = view.findViewById<TextView>(R.id.tvStoryName)
        val itemTime = view.findViewById<TextView>(R.id.tvStoryTime)
    }
}