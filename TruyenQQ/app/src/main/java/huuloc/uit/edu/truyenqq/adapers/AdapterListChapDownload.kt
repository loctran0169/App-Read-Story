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
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import huuloc.uit.edu.truyenqq.R
import huuloc.uit.edu.truyenqq.activities.readingdownload.ActivityReadingDownload

class AdapterListChapDownload(val context: Context, val bookId: String, var list: List<String>) :
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
        holder.chapName.text="Chương $p0"
        holder.chapName.setOnClickListener {
            val intent = Intent(context, ActivityReadingDownload::class.java)
            val bundle = Bundle()
            bundle.putString("book_id", bookId)
            bundle.putString("chap", p0)
            intent.putExtra("manga", bundle)
            context.startActivity(intent)
        }
        holder.chapDate.setOnClickListener {
            Toast.makeText(context,"Xóa",Toast.LENGTH_SHORT).show()
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var chapName = view.findViewById<TextView>(R.id.tvChapNameDownload)
        var chapDate = view.findViewById<ImageView>(R.id.btnChapDateDelete)
    }

    fun updateData(items: List<String>) {
        list = items
        notifyDataSetChanged()
    }
}