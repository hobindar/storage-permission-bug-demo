package ca.hobin.bugdemo

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.os.Bundle
import android.os.Environment
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class MainActivity : AppCompatActivity() {
    private val adminComponent by lazy { ComponentName(this, DeviceAdmin::class.java) }
    private val devicePolicyManager by lazy { getSystemService(DevicePolicyManager::class.java) }
    private val forceGrantPermissionsButton: View by lazy { findViewById(R.id.set_proxy_with_bypass) }
    private val writeFileButton: View by lazy { findViewById(R.id.set_proxy_without_bypass) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        forceGrantPermissionsButton.setOnClickListener(this::forceGrantPermissions)
        writeFileButton.setOnClickListener(this::writeFile)
    }

    private fun forceGrantPermissions(view: View?) {
        devicePolicyManager.setPermissionGrantState(
            adminComponent,
            "ca.hobin.bugdemo",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            DevicePolicyManager.PERMISSION_GRANT_STATE_GRANTED
        )
        devicePolicyManager.setPermissionGrantState(
            adminComponent,
            "ca.hobin.bugdemo",
            "android.permission.READ_EXTERNAL_STORAGE",
            DevicePolicyManager.PERMISSION_GRANT_STATE_GRANTED
        )
        requestPermissions(
            arrayOf(
                "android.permission.WRITE_EXTERNAL_STORAGE",
                "android.permission.READ_EXTERNAL_STORAGE"
            ),
            123
        )
    }

    private fun writeFile(view: View?) {
        val file = File(Environment.getExternalStorageDirectory(), "foo")
        file.writeText("foo")
    }
}
