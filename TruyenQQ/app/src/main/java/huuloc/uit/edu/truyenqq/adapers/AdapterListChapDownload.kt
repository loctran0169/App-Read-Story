package huuloc.uit.edu.truyenqq.adapers

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import huuloc.uit.edu.truyenqq.R
import huuloc.uit.edu.truyenqq.activities.readingdownload.ActivityReadingDownload

class AdapterListChapDownload(
    val context: Context,
    val bookId: String,
    var list: List<String>,
    var select: MutableList<String>
) :
    RecyclerView.Adapter<AdapterListChapDownload.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_chaps_download, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val p0 = list[position]
        var selectChap = false
        holder.chapName.text = "Chương $p0"
        holder.btnRemove.isSelected=false
        if(select.contains(p0)){
            selectChap=true
            holder.btnRemove.isSelected=true
        }
        holder.chapName.setOnClickListener {
            val intent = Intent(context, ActivityReadingDownload::class.java)
            val bundle = Bundle()
            bundle.putString("book_id", bookId)
            bundle.putString("chap", p0)
            intent.putExtra("manga", bundle)
            context.startActivity(intent)
        }
        holder.btnRemove.setOnClickListener {
            selectChap = !selectChap
            holder.btnRemove.isSelected = selectChap
            if(select.contains(p0))
                select.remove(p0)
            else
                select.add(p0)
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var chapName = view.findViewById<TextView>(R.id.tvChapNameDownload)
        var btnRemove = view.findViewById<ImageView>(R.id.btnChapDateDelete)
    }

    fun updateData(items: List<String>) {
        list = items
        notifyDataSetChanged()
    }
}