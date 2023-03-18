package com.lee.myapplication

import android.Manifest
import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.databinding.DataBindingUtil
import com.lee.myapplication.databinding.ActivityMain22Binding
import com.lee.myapplication.db.Scene
import com.lee.myapplication.db.SceneDao
import com.lee.myapplication.db.SceneDatabase
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.RuntimePermissions
import java.io.*

@RuntimePermissions
class MainActivity22 : Activity(), View.OnClickListener {
    private val TAG = "MainActivity22"
    private lateinit var mBinding: ActivityMain22Binding
    private val mStringBuilder = StringBuilder()
    private lateinit var dataBaseManager: SceneDatabase
    private lateinit var sceneDao: SceneDao
    private lateinit var mSceneJson: String
    private val ID = 1001L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main22)
        mBinding.onClickListener = this
        prepare()
    }

    @NeedsPermission(value = [Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE])
    public fun prepare() {
        dataBaseManager = SceneDatabase.getInstance(applicationContext)
        sceneDao = dataBaseManager.sceneDao()
        var line: String? = null

        BufferedReader(InputStreamReader(applicationContext.assets.open("userDefaultScene.json"))).use { reader ->
            while (reader.readLine()?.also { line = it } != null) {
                line?.let {
                    mStringBuilder.append(it)
                }
            }
        }
        mSceneJson = mStringBuilder.toString()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.write_2_file_btn -> {
                writeFile()
            }
            R.id.write_2_db_btn -> {
                writeDb()
            }
            R.id.read_2_file_btn -> {
                readFile()
            }
            R.id.read_2_db_btn -> {
                readDb()
            }
        }
    }

    private fun readDb() {
        Thread {
            val startTime = System.currentTimeMillis()
            val value = sceneDao.query(ID)
            val duration = System.currentTimeMillis() - startTime
            mBinding.readDbTime = "数据库读取耗时：$duration"
            Log.i(TAG, "readDb: ")
        }.start()
    }

    private fun readFile() {
        Thread {
            val startTime = System.currentTimeMillis()
            val dirPath = File(applicationContext.filesDir.path + "/scene/");
            val sceneFile = File(dirPath, "demo1.json")
            var line: String? = null
            val jsonString = StringBuilder()
            if (sceneFile.exists()) {
                BufferedReader(InputStreamReader(FileInputStream(sceneFile))).use { reader ->
                    while (reader.readLine()?.also { line = it } != null) {
                        line?.let {
                            jsonString.append(it)
                        }
                    }
                }
            }
            mBinding.readFileTime = "读取文件耗时 : ${(System.currentTimeMillis() - startTime)}"
            Log.i(TAG, "readFile: ")
        }.start()
    }

    private fun writeDb() {
        Thread {
            val startTime = System.currentTimeMillis()
            val scene = Scene(id = ID, value = mSceneJson)
            sceneDao.insert(scene)

            mBinding.writeDbTime = "写入数据库耗时：${(System.currentTimeMillis() - startTime)}"
            Log.i(TAG, "writeDb: ")
        }.start()
    }

    private fun writeFile() {
        Thread {
            val dirPath = File(applicationContext.filesDir.path + "/scene/")
            if (!dirPath.exists()) {
                dirPath.mkdirs()
            }
            val sceneFile = File(dirPath, "demo1.json")
            if (sceneFile.exists()) {
                sceneFile.delete()
            }
            sceneFile.createNewFile()
            val startTime = System.currentTimeMillis();
            FileOutputStream(sceneFile).use {
                it.write(mStringBuilder.toString().toByteArray())
            }
            mBinding.writeFileTime = "写入文件耗时：${(System.currentTimeMillis() - startTime)}"
            Log.i(TAG, "writeFile: ")
        }.start()
    }
}