package com.example.asus.gopimusicplayer.Models

class SongInfoModel {
    var title:String?=null
    var author:String?=null
    var songUrl:String?=null
    var albumID:Long?=null
    constructor(title:String,author:String,songUrl:String,albumId:Long){
        this.title=title
        this.author=author
        this.songUrl=songUrl
        this.albumID=albumId
    }

}