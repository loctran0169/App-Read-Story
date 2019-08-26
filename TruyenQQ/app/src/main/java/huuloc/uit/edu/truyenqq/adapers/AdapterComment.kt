package huuloc.uit.edu.truyenqq.adapers

import android.content.Context
import android.graphics.Color
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import huuloc.uit.edu.truyenqq.R
import huuloc.uit.edu.truyenqq.data.Comment

class AdapterComment(val context: Context, var list: List<Comment>) :
    RecyclerView.Adapter<AdapterComment.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val p0 = list[position]
        holder.content.text = Html.fromHtml(p0.content)
        holder.name.text = p0.name_comment
        holder.like.text = p0.total_like
        holder.comment.text = "Trả lời"
        if (p0.created_by == "0")
            holder.kind.text = "Ẩn danh"
        else {
            holder.kind.text = "thành viên"
            holder.kind.setTextColor(Color.parseColor("#209cee"))
        }
        if (!p0.avatar.isNullOrEmpty())
            Glide.with(context)
                .load("http://avatar.mangaqq.com/160x160/" + p0.avatar + "?thang=t515")
                .into(holder.avatar)
        else
            holder.avatar.setImageResource(R.drawable.ic_nouser)
    }

    fun updateDate(items: List<Comment>) {
        list = items
        notifyDataSetChanged()
    }

    fun loadMore(pos: Int, add: Int): Boolean {
        notifyItemRangeInserted(pos, add)
        return true
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val content = view.findViewById<TextView>(R.id.tvIntent)
        val name = view.findViewById<TextView>(R.id.tvNameComment)
        val like = view.findViewById<TextView>(R.id.numLike)
        val comment = view.findViewById<TextView>(R.id.numComment)
        val kind = view.findViewById<TextView>(R.id.tvChrome)
        val avatar = view.findViewById<ImageView>(R.id.imgAvatarComment)
    }
}