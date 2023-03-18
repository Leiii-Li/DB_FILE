package com.lee.myapplication

import android.animation.FloatEvaluator
import android.app.Activity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.KeyEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import java.lang.Exception
import java.net.Inet4Address
import java.net.InetAddress
import java.net.NetworkInterface
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.abs


class DailyRecommendActivity : Activity() {

    private val mCardItems: MutableList<ImageCardAdapter.CardItem?> = ArrayList(50)
    private var mCurPosition = 10
    private var mDirection = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gridView = findViewById<RecyclerView>(R.id.recommend_grid)

        // 设置居中item放大效果
        val layoutManager = GalleryLayoutManager(GalleryLayoutManager.HORIZONTAL)
        layoutManager.attach(gridView, mCurPosition)

        // Apply ItemTransformer just like ViewPager
        layoutManager.setItemTransformer(ScaleTransformer())


        initData()

        var adapter = ImageCardAdapter(mCardItems)
        gridView.adapter = adapter

        gridView.addItemDecoration(ItemDecoration())
        gridView.setOnKeyListener { _, keyCode, keyEvent ->
            if (keyEvent.action != KeyEvent.ACTION_DOWN) return@setOnKeyListener true
            when (keyCode) {
                KeyEvent.KEYCODE_DPAD_LEFT -> {
                    layoutManager.smoothScrollToPosition(gridView, RecyclerView.State(), if (mCurPosition > 0) --mCurPosition else mCurPosition)
                    mDirection = KeyEvent.KEYCODE_DPAD_LEFT
                }
                KeyEvent.KEYCODE_DPAD_RIGHT -> {
                    layoutManager.smoothScrollToPosition(gridView, RecyclerView.State(), if (mCurPosition < mCardItems.size - 1) ++mCurPosition else mCurPosition)
                    mDirection = KeyEvent.KEYCODE_DPAD_RIGHT
                }
            }
            return@setOnKeyListener true
        }

        val hostIp = getHostIp()
        Log.i(TAG, "onCreate: $hostIp")
    }

    // 获取有限网IP
    fun getHostIp(): String? {
        try {
            val en: Enumeration<NetworkInterface> = NetworkInterface
                .getNetworkInterfaces()
            while (en.hasMoreElements()) {
                val intf: NetworkInterface = en.nextElement()
                val enumIpAddr: Enumeration<InetAddress> = intf
                    .getInetAddresses()
                while (enumIpAddr.hasMoreElements()) {
                    val inetAddress: InetAddress = enumIpAddr.nextElement()
                    if (!inetAddress.isLoopbackAddress()
                        && inetAddress is Inet4Address
                    ) {
                        return inetAddress.getHostAddress()
                    }
                }
            }
        } catch (ex: Exception) {
        }
        return "0.0.0.0"
    }

    private fun initData() {
        var cardItem: ImageCardAdapter.CardItem
        val mResId = ArrayList<Int>(4)
        mResId.add(R.drawable.bg_morning)
        mResId.add(R.drawable.bg_noon)
        mResId.add(R.drawable.bg_dusk)
        mResId.add(R.drawable.bg_night)
        for (i in 0..49) {
            cardItem = ImageCardAdapter.CardItem(mResId[i % mResId.size], "item:$i")
            mCardItems.add(cardItem)
        }
    }

    companion object {
        private const val TAG = "DailyRecommendActivity"
    }

    inner class ScaleTransformer : GalleryLayoutManager.ItemTransformer {
        override fun transformItem(layoutManager: GalleryLayoutManager?, item: View, fraction: Float) {
            if (mDirection == KeyEvent.KEYCODE_DPAD_RIGHT) {
                if (item.tag == mCurPosition) {
                    val scale = FloatEvaluator().evaluate(fraction, 1.27f, 1)
                    item.pivotX = item.width / 2f
                    item.pivotY = item.height / 2f

                    item.scaleX = scale
                    item.scaleY = scale
                }
                if (item.tag == (mCurPosition + 1)) {
                    val scale = FloatEvaluator().evaluate(fraction, 1, 1.27f)
                    item.pivotX = item.width / 2f
                    item.pivotY = item.height / 2f

                    item.scaleX = scale
                    item.scaleY = scale
                }

            }
            if (mDirection == KeyEvent.KEYCODE_DPAD_LEFT) {
                if (item.tag == mCurPosition) {
                    val scale = FloatEvaluator().evaluate(fraction, 1.27f, 1)
                    item.pivotX = item.width / 2f
                    item.pivotY = item.height / 2f

                    item.scaleX = scale
                    item.scaleY = scale
                }
                if (item.tag == (mCurPosition - 1)) {
                    val scale = FloatEvaluator().evaluate(fraction, 1, 1.27f)
                    item.pivotX = item.width / 2f
                    item.pivotY = item.height / 2f

                    item.scaleX = scale
                    item.scaleY = scale
                }

            }
        }
    }
}

