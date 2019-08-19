package huuloc.uit.edu.truyenqq.adapers


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import huuloc.uit.edu.truyenqq.data.MysharedPreferences
import huuloc.uit.edu.truyenqq.data.USER_ID
import huuloc.uit.edu.truyenqq.fragments.book.FragmentBookPage

class AdapterBookTabLayout(fragment: FragmentManager, val context: Context) : FragmentPagerAdapter(fragment) {
    val share = MysharedPreferences(context).getShare
    val title = listOf("Theo Dõi", "Lịch Sử", "Tải Xuống")
    override fun getItem(position: Int): Fragment {
        val fragment = FragmentBookPage()
        val bundle = Bundle()
        bundle.putInt("tag", position)
        if (share.getString(USER_ID, null).isNullOrEmpty())
            bundle.putBoolean("login", false)
        else
            bundle.putBoolean("login", true)
        fragment.arguments = bundle
        return fragment
    }

    override fun getCount(): Int {
        return title.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return title[position]
    }
}
