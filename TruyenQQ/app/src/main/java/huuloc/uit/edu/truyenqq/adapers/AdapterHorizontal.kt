package huuloc.uit.edu.truyenqq.adapers

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexboxLayoutManager
import huuloc.uit.edu.truyenqq.R
import huuloc.uit.edu.truyenqq.data.BaseItem
import huuloc.uit.edu.truyenqq.data.StoryInformation
import kotlinx.android.synthetic.main.item_story_horizontal.view.*
import kotlinx.android.synthetic.main.progressbar_loading.view.*

class AdapterHorizontal :

    RecyclerView.Adapter<BaseItem> {

    var context: Context
    var mItems: List<StoryInformation>

    constructor(context: Context, items: List<StoryInformation>) : super() {
        this.context = context
        this.mItems = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseItem {
        return when (viewType) {
            1 -> {
                val view: View = LayoutInflater.from(context).inflate(R.layout.item_story_horizontal, parent, false)
                ItemViewHolder(view)
            }
            else -> {
                val view: View = LayoutInflater.from(context).inflate(R.layout.progressbar_loading, parent, false)
                LoadingViewholder(view)
            }
        }
    }

    override fun getItemCount(): Int {
        if (mItems.isNullOrEmpty())
            return 0
        return mItems.size
    }

    override fun onBindViewHolder(holder: BaseItem, position: Int) {
        holder.bind(position)
    }

    override fun getItemViewType(position: Int): Int {
        if (mItems[position].id == "")
            return 0
        return 1
    }

    fun updateData(list: List<StoryInformation>) {
        mItems = list
        notifyDataSetChanged()
    }

    inner class ItemViewHolder(view: View) : BaseItem(view) {
        @SuppressLint("SetTextI18n")
        override fun bind(position: Int) {
            val p0 = mItems[position]
            itemView.tvStoryNameHorizontal.text = p0.name
            try {
                Glide.with(context)
                    .load("http://i.mangaqq.com/ebook/190x247/" + p0.image + "?thang=t2121")
                    .into(itemView.imgStoryItemHorizontal)
            } finally {
                Glide.with(context)
                    .load("http://i.mangaqq.com/ebook/190x247/" + p0.image + "?thang=t515")
                    .into(itemView.imgStoryItemHorizontal)
            }
            itemView.tvChapHorizontal.text = "Chương " + p0.episode
            itemView.rcvCategoryHorizontal.run {
                layoutManager = FlexboxLayoutManager(context)
                adapter = AdapterFlexBoxLayout(context, p0.category)
            }
            itemView.tvStoryTimeHorizontal.text =
                timeStampToString((System.currentTimeMillis() / 1000).toInt() - p0.modified.toInt())
        }
    }

    inner class LoadingViewholder(view: View) : BaseItem(view) {
        override fun bind(position: Int) {
            itemView.progressBar.visibility = View.VISIBLE
        }

    }
}