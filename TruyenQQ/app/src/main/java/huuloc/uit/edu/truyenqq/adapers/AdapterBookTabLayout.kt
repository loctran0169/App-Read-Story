package huuloc.uit.edu.truyenqq.adapers


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import huuloc.uit.edu.truyenqq.fragments.book.FragmentBookPage

class AdapterBookTabLayout(fragment: FragmentManager) : FragmentPagerAdapter(fragment) {
    val title = listOf("Theo Dõi", "Lịch Sử", "Tải Xuống")
    override fun getItem(position: Int): Fragment {
        return FragmentBookPage(position)
    }

    override fun getCount(): Int {
        return title.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return title[position]
    }
}
