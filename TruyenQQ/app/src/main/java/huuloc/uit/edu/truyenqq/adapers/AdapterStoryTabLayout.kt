package huuloc.uit.edu.truyenqq.adapers

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import huuloc.uit.edu.truyenqq.fragments.book.FragmentStoryInformation

class AdapterStoryTabLayout(val id :String, fragment: FragmentManager) :
    FragmentPagerAdapter(fragment) {

    override fun getItem(position: Int): Fragment {
        if(position==0)
            return FragmentStoryInformation(id)
        return FragmentStoryInformation(id)
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        if(position==0)
            return "Thông Tin"
        return "Chaps"
    }

}