package com.example.asus.gopimusicplayer.Adapter

import android.content.ContentUris
import android.content.Context
import android.graphics.Bitmap
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.RoundedBitmapDrawable
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.example.asus.gopimusicplayer.Models.SongInfoModel
import com.example.asus.gopimusicplayer.PlayerConstants
import com.example.asus.gopimusicplayer.R

class RecentlyHeardAdapter(recentlHeardList:ArrayList<SongInfoModel>,mContext:Context):RecyclerView.Adapter<RecentlyHeardAdapter.MyViewHolder>() {
    private var recentlHeardList: ArrayList<SongInfoModel> = recentlHeardList
    private var mContext: Context = mContext

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentlyHeardAdapter.MyViewHolder {
        var itemView: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.recently_added_list_item, parent, false)
        return RecentlyHeardAdapter.MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return recentlHeardList.size
    }

    override fun onBindViewHolder(holder: RecentlyHeardAdapter.MyViewHolder, position: Int) {
        val posi: Int = holder.adapterPosition
        holder.tvRecentlyHeardSongName.text=recentlHeardList[posi].title
        holder.tvRecentlyHeardArtistName.text=recentlHeardList[posi].author
        try {
            Glide.with(mContext).load(ContentUris.withAppendedId(PlayerConstants.sArtworkUri, recentlHeardList[posi].albumID!!)).asBitmap().centerCrop().placeholder(ContextCompat.getDrawable(mContext, R.drawable.music_player))
                    .error(ContextCompat.getDrawable(mContext, R.drawable.music_player))
                    .into(object : BitmapImageViewTarget(holder.ivRecentlyHeard) {
                        override fun setResource(resource: Bitmap) {
                            val circularBitmapDrawable: RoundedBitmapDrawable = RoundedBitmapDrawableFactory.create(mContext.resources, resource)
                            circularBitmapDrawable.isCircular = true
                            holder.ivRecentlyHeard.setImageDrawable(circularBitmapDrawable)
                        }
                    })
        }catch (e:Exception){
            e.printStackTrace()
        }
    }
    class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        var ivRecentlyHeard:ImageView = itemView.findViewById(R.id.ivRecentlyHeardArtist)
        var tvRecentlyHeardSongName:TextView = itemView.findViewById(R.id.tvRecentHeardSongName)
        var tvRecentlyHeardArtistName:TextView = itemView.findViewById(R.id.tvRecentAddedArtist)
    }


}