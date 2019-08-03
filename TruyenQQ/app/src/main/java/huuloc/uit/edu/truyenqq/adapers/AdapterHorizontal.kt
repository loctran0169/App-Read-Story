package huuloc.uit.edu.truyenqq.adapers

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.siyamed.shapeimageview.RoundedImageView
import com.google.android.flexbox.FlexboxLayout
import com.google.android.flexbox.FlexboxLayoutManager
import huuloc.uit.edu.truyenqq.R
import huuloc.uit.edu.truyenqq.data.ListStory
import huuloc.uit.edu.truyenqq.data.StoryInformation

class AdapterHorizontal(var context: Context, var items: List<StoryInformation>) :

    RecyclerView.Adapter<AdapterHorizontal.BaseItem>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseItem {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_story_horizontal, parent, false)
        return BaseItem(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: BaseItem, position: Int) {
        val p0 = items[position]
        holder.itemName.text = p0.name
        Glide.with(context)
            .load("http://i.mangaqq.com/ebook/190x247/" + p0.image + "?thang=t2121")
            .into(holder.itemImage)
        holder.itemChap.text = "Chương " + p0.episode
        holder.itemRecycle.run {
            layoutManager = FlexboxLayoutManager(context)
            adapter = AdapterFlexBoxLayout(context, p0.category)
        }
        //holder.itemTime.text = p0.time
    }

    fun updateData(list: List<StoryInformation>) {
        items = list
        notifyDataSetChanged()
    }

    inner class BaseItem(view: View) : RecyclerView.ViewHolder(view) {
        val itemImage = view.findViewById<RoundedImageView>(R.id.imgStoryItemHorizontal)
        val itemName = view.findViewById<TextView>(R.id.tvStoryNameHorizontal)
        val itemTime = view.findViewById<TextView>(R.id.tvStoryTimeHorizontal)
        val itemChap = view.findViewById<TextView>(R.id.tvChapHorizontal)
        val itemRecycle = view.findViewById<RecyclerView>(R.id.rcvCategoryHorizontal)
    }
}