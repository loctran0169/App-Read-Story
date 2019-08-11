package huuloc.uit.edu.truyenqq.adapers

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import huuloc.uit.edu.truyenqq.R
import huuloc.uit.edu.truyenqq.data.Chap

class AdapterListChap(val context: Context, var list: List<Chap>) : RecyclerView.Adapter<AdapterListChap.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_chaps, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val p0 = list[position]
        holder.chapName.text = p0.name
        holder.chapDate.text = p0.created
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var chapName = view.findViewById<TextView>(R.id.tvChapName)
        var chapDate = view.findViewById<TextView>(R.id.tvChapDate)
    }
    fun updateData(items : List<Chap>){
        list= items
        notifyDataSetChanged()
    }
}