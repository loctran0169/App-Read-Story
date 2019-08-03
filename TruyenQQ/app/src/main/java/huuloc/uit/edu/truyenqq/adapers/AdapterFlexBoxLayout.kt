package huuloc.uit.edu.truyenqq.adapers

import android.content.Context
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import huuloc.uit.edu.truyenqq.R
import huuloc.uit.edu.truyenqq.data.Category

class AdapterFlexBoxLayout(val context: Context, val list: List<Category>) :
    RecyclerView.Adapter<AdapterFlexBoxLayout.Item>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterFlexBoxLayout.Item {

        val view: View = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false)
        return Item(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: AdapterFlexBoxLayout.Item, position: Int) {
        holder.itemNameCategory.text = list[position].name
    }

    inner class Item(view: View) : RecyclerView.ViewHolder(view) {
        var itemNameCategory = view.findViewById<TextView>(R.id.tvCategoryHorizontal)
    }
}