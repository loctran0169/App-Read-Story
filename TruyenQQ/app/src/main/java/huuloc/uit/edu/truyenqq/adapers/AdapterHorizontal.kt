package huuloc.uit.edu.truyenqq.adapers

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexboxLayoutManager
import huuloc.uit.edu.truyenqq.R
import huuloc.uit.edu.truyenqq.activities.story.ActivityStory
import huuloc.uit.edu.truyenqq.data.BaseItem
import huuloc.uit.edu.truyenqq.data.StoryInformation
import kotlinx.android.synthetic.main.item_story_horizontal.view.*
import kotlinx.android.synthetic.main.progressbar_loading.view.*

class AdapterHorizontal(var context: Context, var items: List<StoryInformation>) :

    RecyclerView.Adapter<BaseItem>() {

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
        if (items.isNullOrEmpty())
            return 0
        return items.size
    }

    override fun onBindViewHolder(holder: BaseItem, position: Int) {
        holder.bind(position)
    }

    override fun getItemViewType(position: Int): Int {
        if (items[position].id == "")
            return 0
        return 1
    }

    fun updateData(list: List<StoryInformation>) {
        items = list
        notifyDataSetChanged()
    }

    inner class ItemViewHolder(view: View) : BaseItem(view) {
        @SuppressLint("SetTextI18n")
        override fun bind(position: Int) {
            val p0 = items[position]
            itemView.tvStoryNameHorizontal.text = p0.name
            try {
                Glide.with(context)
                    .load("http://i.mangaqq.com/ebook/190x247/" + p0.image + "?thang=t2121")
                    .into(itemView.imgStoryItemHorizontal)
            }
            finally {
                Glide.with(context)
                    .load("http://i.mangaqq.com/ebook/190x247/" + p0.image + "?thang=t515")
                    .into(itemView.imgStoryItemHorizontal)
            }
            var chapter = "Chương " + p0.episode
            if (!p0.chap_order.isNullOrEmpty())
                chapter += " - Đọc tới chap ${p0.chap_order}"
            itemView.tvChapHorizontal.text = chapter
            itemView.rcvCategoryHorizontal.run {
                layoutManager = FlexboxLayoutManager(context)
                adapter = AdapterFlexBoxLayout(context, p0.category!!)
            }
            itemView.tvStoryTimeHorizontal.text =
                timeStampToString((System.currentTimeMillis() / 1000).toInt() - p0.modified!!.toInt())
            itemView.setOnClickListener {
                val intent1 = Intent(context, ActivityStory::class.java)
                val bundle = Bundle()
                bundle.putString("book_id", if (p0.book_id != null) p0.book_id else p0.id)
                intent1.putExtra("kind", bundle)
                context.startActivity(intent1)
            }
            itemView.tvLikeHorizontal.text = p0.like_book
            itemView.tvViewHorizontal.text = p0.total_view
            itemView.tvSubscribeHorizontal.text = p0.total_subscribe
        }
    }

    inner class LoadingViewholder(view: View) : BaseItem(view) {
        override fun bind(position: Int) {
            itemView.progressBar.visibility = View.VISIBLE
        }
    }

    fun loadMore(pos: Int, add: Int): Boolean {
        notifyItemRangeInserted(pos, add)
        return true
    }
}