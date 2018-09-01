package com.example.asus.gopimusicplayer.Activity

import android.content.pm.PackageManager
import android.database.Cursor
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.SeekBar
import android.widget.Toast
import com.example.asus.gopimusicplayer.Interface.RequestCode
import com.example.asus.gopimusicplayer.Models.SongInfoModel
import com.example.asus.gopimusicplayer.R
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.song_ticket.view.*

class HomeActivity : AppCompatActivity() {
    private var listSongs = ArrayList<SongInfoModel>()
    private var adapter: MysongAdapter? = null
    private var mp: MediaPlayer? = null
    private lateinit var sbMusicProgress: SeekBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initView()
        //loadUrlOnline()
        checkPermissions()
        var myTrack = TrackSong()
        myTrack.start()
    }

    /**
     * ================================================Function used to initialize view==================================================================================================
     * */
    private fun initView() {
        sbMusicProgress = findViewById(R.id.sbMusicProgress)
    }

    /**
     * ================================================Function to check Permissions==============================================================================================
     * */
    private fun checkPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), RequestCode.REQUEST_CODE_ASK_PERMISSIONS)
                return
            }
        }
        loadMusicFromLocal()
    }

    /**
     * ================================================Function to handle  Permissions==============================================================================================
     * */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            RequestCode.REQUEST_CODE_ASK_PERMISSIONS -> if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadMusicFromLocal()
            } else {
                Toast.makeText(this, "Deniel", Toast.LENGTH_LONG)
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }


    /**
     * ================================================Function to loadMusic from local==============================================================================================
     * */

    private fun loadMusicFromLocal() {
        val allSongUri: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val selection: String = MediaStore.Audio.Media.IS_MUSIC + "!=0"
        val cursor: Cursor = contentResolver.query(allSongUri, null, selection, null, null)
        if (cursor != null) {
            if (cursor!!.moveToFirst()) {
                do {
                    val songUrl: String = cursor!!.getString(cursor!!.getColumnIndex(MediaStore.Audio.Media.DATA))
                    val songAuthor: String = cursor!!.getString(cursor!!.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                    val songName: String = cursor!!.getString(cursor!!.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME))
                    listSongs.add(SongInfoModel(songName,songAuthor,songUrl))


                } while (cursor!!.moveToNext())

            }
        }
        cursor!!.close()
        adapter = MysongAdapter(listSongs)
        lvListSong.adapter = adapter
    }

    /**
     * ================================================Inner class of Song Adapter==============================================================================================
     * */
    inner class MysongAdapter : BaseAdapter {
        var myListSong = ArrayList<SongInfoModel>()

        constructor(myListSong: ArrayList<SongInfoModel>) : super() {
            this.myListSong = myListSong
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val myView: View = layoutInflater.inflate(R.layout.song_ticket, null)
            val song: SongInfoModel = this.myListSong[position]
            myView.tvSongName.text = song.title
            myView.tvAuthorName.text = song.author
            myView.btnPlay.setOnClickListener(View.OnClickListener {
                mp = MediaPlayer()
                if (myView.btnPlay.text == "Stop") {
                    mp!!.stop()
                    myView.btnPlay.text = "Play"

                } else {
                    try {
                        mp!!.setDataSource(song.songUrl)
                        mp!!.prepare()
                        mp!!.start()
                        myView.btnPlay.text = "Stop"
                        sbMusicProgress.max = mp!!.duration
                    } catch (e: Exception) {

                    }
                }

            })
            return myView
        }

        override fun getItem(position: Int): Any {
            return this.myListSong[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return this.myListSong.size
        }

    }

    /**
     * ================================================Inner class to run song in background ==============================================================================================
     * */
    inner class TrackSong : Thread() {
        override fun run() {
            while (true) {
                try {
                    Thread.sleep(1000)
                } catch (e: Exception) {

                }
                runOnUiThread {
                    if (mp != null) {
                        sbMusicProgress.progress = mp!!.currentPosition
                    }
                }
            }

        }
    }


}
