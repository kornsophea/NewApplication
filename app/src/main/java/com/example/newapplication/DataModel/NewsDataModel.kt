package com.example.newapplication.DataModel

import java.io.Serializable

class NewsDataModel(
    var id:String,
    var userid:String,
    var imageUrl:String,
    var title:String,
    var author:String,
    var introduction:String,
    var content:String,
    var date:String

):
        Serializable {
}