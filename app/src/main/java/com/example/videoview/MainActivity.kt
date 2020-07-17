package com.example.videoview

import android.media.PlaybackParams
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.MediaController
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 设置本地视频地址
        val videoPath = "android.resource://$packageName/${R.raw.video1}"
        videoView.setVideoPath(videoPath)
        // 控制条
        //videoView.setMediaController(MediaController(this))
        videoView.setOnPreparedListener {
            // 加载条
            progressBar2.visibility = View.INVISIBLE
            // 获取视频长度
            progressBar.max = it.duration
            // 重复播放
            it.isLooping = true
            // 改变属性
            it.playbackParams = PlaybackParams().apply {
                //speed = 2f // 两倍速
                //pitch = 2f // 音高改变
            }
            // 自动播放
            it.start()
        }
        // 进度条,每半秒刷新
        lifecycleScope.launch {
            while (true){
                // 如果视频还在播放
                if (videoView.isPlaying){
                    progressBar.progress = videoView.currentPosition
                }
                // 每半秒刷新
                delay(500)
            }
        }

    }
}