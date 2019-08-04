package huuloc.uit.edu.truyenqq.activities

import android.Manifest
import android.app.ActivityOptions
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import huuloc.uit.edu.truyenqq.R
import huuloc.uit.edu.truyenqq.fragments.home.FragmentHome
import huuloc.uit.edu.truyenqq.fragments.user.FragmentUser
import huuloc.uit.edu.truyenqq.fragments.home.ViewModelHome
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

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
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("###", "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }
                // Get new Instance ID token
                val token = task.result?.token
            })
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val permission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
            requestPermissions(permission, 101)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == 101) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                println("###xin thành công")
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
            .addToBackStack("4")
            .add(R.id.frmMain, fragment)
            .commit()
    }

    fun openActivityRank(view: View) {
        val intent = Intent(this, Activity_Rank::class.java)
        startActivity(intent)
    }

    fun openActivityNewUpdateStory(view: View) {
        val intent = Intent(this, Activity_NewUpdateStory::class.java)
        startActivity(intent)
    }



    fun showNewStory(view: View) {
        startActivity(Intent(this, Activity_NewStory::class.java))
    }
}