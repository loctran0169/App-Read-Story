package huuloc.uit.edu.truyenqq.adapers

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import huuloc.uit.edu.truyenqq.R
import huuloc.uit.edu.truyenqq.activities.readingdownload.ActivityReadingDownload

class AdapterListChapDownload(val context: Context, val bookId: String, var list: List<String>) :
    RecyclerView.Adapter<AdapterListChapDownload.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_chaps, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val p0 = list[position]
        holder.chapDate.isVisible = false
        holder.chapName.text="Chương $p0"
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ActivityReadingDownload::class.java)
            val bundle = Bundle()
            bundle.putString("book_id", bookId)
            bundle.putString("chap", p0)
            intent.putExtra("manga", bundle)
            context.startActivity(intent)
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var chapName = view.findViewById<TextView>(R.id.tvChapName)
        var chapDate = view.findViewById<TextView>(R.id.tvChapDate)
    }

    fun updateData(items: List<String>) {
        list = items
        notifyDataSetChanged()
    }
}