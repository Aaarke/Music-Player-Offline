package com.example.asus.gopimusicplayer.Adapter

import android.app.FragmentManager
import android.app.FragmentTransaction
import android.content.ContentUris
import android.content.Context
import android.graphics.Bitmap
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.RoundedBitmapDrawable
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.support.v7.view.menu.ActionMenuItemView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.example.asus.gopimusicplayer.Fragments.FragmentSong
import com.example.asus.gopimusicplayer.Models.SongInfoModel
import com.example.asus.gopimusicplayer.PlayerConstants
import com.example.asus.gopimusicplayer.R
import de.hdodenhof.circleimageview.CircleImageView

class MysongAdapter(mySongList: ArrayList<SongInfoModel>, mContext: Context) : RecyclerView.Adapter<MysongAdapter.MyViewHolder>() {
    private var mySongList: ArrayList<SongInfoModel> = mySongList
    private var mContext: Context = mContext

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MysongAdapter.MyViewHolder {
        var itemView: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.song_ticket, parent, false)
        return MysongAdapter.MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return mySongList.size
    }

    override fun onBindViewHolder(holder: MysongAdapter.MyViewHolder, position: Int) {
        val posi: Int = holder.adapterPosition
        holder.tvSongName.text=mySongList[posi].title
        holder.tvAuthorName.text=mySongList[posi].author
        holder.itemView.setOnClickListener(object:View.OnClickListener{
            override fun onClick(view: View?) {
                openSongActivity()
            }
        })

        try {
            Glide.with(mContext).load(ContentUris.withAppendedId(PlayerConstants.sArtworkUri, mySongList[posi].albumID!!)).asBitmap().centerCrop().placeholder(ContextCompat.getDrawable(mContext, R.drawable.music_player))
                    .error(ContextCompat.getDrawable(mContext, R.drawable.music_player))
                    .into(object : BitmapImageViewTarget(holder.ivArtist) {
                        override fun setResource(resource: Bitmap) {
                            val circularBitmapDrawable: RoundedBitmapDrawable = RoundedBitmapDrawableFactory.create(mContext.resources, resource)
                            circularBitmapDrawable.isCircular = true
                            holder.ivArtist.setImageDrawable(circularBitmapDrawable)
                        }
                    })
        }catch (e:Exception){
            e.printStackTrace()
        }

    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvSongName: TextView
        var tvAuthorName: TextView
        var ivArtist:CircleImageView

        init {
            this.tvAuthorName = itemView.findViewById(R.id.tvAuthorName)
            this.tvSongName = itemView.findViewById(R.id.tvSongName)
            this.ivArtist=itemView.findViewById(R.id.ivArtist)
        }
    }


    private fun openSongActivity(){

    }

}
