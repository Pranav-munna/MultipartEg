package com.twixttechnologies.multiparteg.Controller

import android.content.Context
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.*

/**
 * @author Pranav Ashok on 11/06/18.
 */
class Request(context: Context, rHandeler: ProcessResponceInterphase<Responce>) : AbstractRequest<Responce>(context, rHandeler) {

    fun sendImage(title: String, name: String, type: String, file: File) {

        val params = HashMap<String, RequestBody>()

        val rb_title: RequestBody
        rb_title = RequestBody.create(MediaType.parse("multipart/form-data"), title)
        params["title"] = rb_title

        val rb_name: RequestBody
        rb_name = RequestBody.create(MediaType.parse("multipart/form-data"), name)
        params["name"] = rb_name

        val rb_type: RequestBody
        rb_type = RequestBody.create(MediaType.parse("multipart/form-data"), type)
        params["type"] = rb_type


        val image = RequestBody.create(MediaType.parse("multipart/form-data"), file.getAbsoluteFile())
        val imagePart = MultipartBody.Part.createFormData("uploaded_file", file.getName(), image)

        val call = interphase.getstatus(title, name, type, imagePart)
        call.enqueue(this)
    }
}