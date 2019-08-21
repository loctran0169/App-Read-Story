package huuloc.uit.edu.truyenqq.adapers

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import huuloc.uit.edu.truyenqq.R
import huuloc.uit.edu.truyenqq.data.Chap

class AdapterChapDownload(val context: Context, var list: List<Chap>, var temp: MutableList<String>) :
    RecyclerView.Adapter<AdapterChapDownload.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_chap_select, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var select = false
        holder.tvName.text = list[position].order
        holder.itemView.setOnClickListener {
            select = !select
            if (select)
                holder.tvName.background = context.resources.getDrawable(R.drawable.border_color_button_radius)
            else
                holder.tvName.background = context.resources.getDrawable(R.drawable.border_textview_category)
            if (!temp.contains(list[position].order))
                temp.add(list[position].order)
            else {
                temp.remove(list[position].order)
            }
        }
    }

    fun updateData(items: List<Chap>) {
        list = items
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName = view.findViewById<TextView>(R.id.tvChap)
    }
}