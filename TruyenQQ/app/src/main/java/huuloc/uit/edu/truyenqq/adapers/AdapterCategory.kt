package huuloc.uit.edu.truyenqq.adapers

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import huuloc.uit.edu.truyenqq.R
import huuloc.uit.edu.truyenqq.activities.newupdatestory.ActivityNewUpdateStory
import huuloc.uit.edu.truyenqq.data.Category

class AdapterCategory(
    val context: Context,
    var list: MutableList<Category>
) : RecyclerView.Adapter<AdapterCategory.BaseItem>(), Filterable {

    private var searchList: List<Category>? = null

    init {
        this.searchList = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseItem {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false)
        return BaseItem(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: BaseItem, position: Int) {
        val p0 = list[position]
        holder.name.text = p0.name
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ActivityNewUpdateStory::class.java)
            val bundle = Bundle()
            bundle.putString("name", p0.title)
            bundle.putString("category", p0.id)
            intent.putExtra("kind", bundle)
            context.startActivity(intent)
        }
        holder.itemView.setOnLongClickListener {
            var dialog = androidx.appcompat.app.AlertDialog.Builder(context)
            val view: View = LayoutInflater.from(context).inflate(R.layout.item_category_longclick, null)
            val _info = view.findViewById<TextView>(R.id.tvCategoryInfo)
            _info.text = p0.info
            dialog.setView(view)
            val dislay = dialog.create()
            dislay.setTitle(p0.name)
            dislay.show()
            true
        }
    }

    fun updateData(items: MutableList<Category>) {
        list = items
    }

    inner class BaseItem(view: View) : RecyclerView.ViewHolder(view) {
        var name = view.findViewById<TextView>(R.id.tvCategoryName)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    searchList = list
                } else {
                    val filteredList = ArrayList<Category>()
                    for (row in list) {
                        if (row.name.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row)
                        }
                    }
                    searchList = filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = searchList
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                list = filterResults.values as ArrayList<Category>
                notifyDataSetChanged()
            }
        }
    }

}


