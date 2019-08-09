package huuloc.uit.edu.truyenqq.fragments.book

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import huuloc.uit.edu.truyenqq.R
import huuloc.uit.edu.truyenqq.adapers.AdapterBookTabLayout
import kotlinx.android.synthetic.main.fragment_book.*

@Suppress("UNREACHABLE_CODE")
class FragmentBook : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_book, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, null)
        viewPagerBook.adapter = AdapterBookTabLayout(childFragmentManager)
        tabLayoutBook.tabMode = TabLayout.MODE_FIXED
        tabLayoutBook.setupWithViewPager(viewPagerBook)
    }
}