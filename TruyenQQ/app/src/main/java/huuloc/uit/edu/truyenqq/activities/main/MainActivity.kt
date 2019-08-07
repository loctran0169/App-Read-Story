package huuloc.uit.edu.truyenqq.activities.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import huuloc.uit.edu.truyenqq.R
import huuloc.uit.edu.truyenqq.activities.newstory.ActivityNewStory
import huuloc.uit.edu.truyenqq.activities.newupdatestory.ActivityNewUpdateStory
import huuloc.uit.edu.truyenqq.activities.rank.ActivityRank
import huuloc.uit.edu.truyenqq.fragments.FragmentCategory
import huuloc.uit.edu.truyenqq.fragments.home.FragmentHome
import huuloc.uit.edu.truyenqq.fragments.user.FragmentUser
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var idSelect = R.id.navHome
    val viewModel: ViewModelHome by lazy {
        ViewModelProviders
            .of(this)
            .get(ViewModelHome::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ViewModelProviders
            .of(this)
            .get(ViewModelHome::class.java)

        supportFragmentManager.beginTransaction()
            .add(R.id.frmMain, FragmentHome())
            .commit()
        botNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navHome -> {
                    showFragment(FragmentHome())
                    true
                }
                R.id.navCategory -> {
                    showFragment(FragmentCategory())
                    true
                }
                R.id.navUser -> {
                    showFragment(FragmentUser())
                    true
                }
                else -> {
                    println("### nothing")
                    true
                }
            }
        }
        //FirebaseInstanceId.getInstance().instanceId
        //            .addOnCompleteListener(OnCompleteListener { task ->
        //                if (!task.isSuccessful) {
        //                    Log.w("###", "getInstanceId failed", task.exception)
        //                    return@OnCompleteListener
        //                }
        //                // Get new Instance ID token
        //                val token = task.result?.token
        //            })
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val permission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
            requestPermissions(permission, 101)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == 101) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        println("###")
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.frmMain, fragment).commit()
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .add(R.id.frmMain, fragment)
            .commit()
    }

    fun openActivityRank(view: View) {
        val intent = Intent(this,ActivityRank::class.java)
        startActivity(intent)
    }

    fun openActivityNewUpdateStory(view: View) {
        val intent = Intent(this, ActivityNewUpdateStory::class.java)
        val bundle = Bundle()
        bundle.putString("name", "Truyện mới cập nhật")
        bundle.putString("category", "")
        intent.putExtra("kind", bundle)
        startActivity(intent)
    }

    fun showNewStory(view: View) {
        startActivity(Intent(this, ActivityNewStory::class.java))
    }
}