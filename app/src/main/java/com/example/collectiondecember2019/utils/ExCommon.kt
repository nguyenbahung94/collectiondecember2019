package com.example.collectiondecember2019.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.format.DateFormat
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.*

inline fun <reified T : Activity> Context.openActivity(noinline extra: Intent.() -> Unit) {
    val intent = Intent(this, T::class.java)
    intent.extra()
    startActivity(intent)
}
fun sendSMS(context: Context, phone: String?) {
    if (phone.isNullOrEmpty())
        return
    val smsIntent = Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", phone, null))
    if (smsIntent.resolveActivity(context.packageManager) != null) {
        context.startActivity(smsIntent)
    }
}
fun callPhone(context: Context, phone: String?) {
    if (phone.isNullOrEmpty())
        return
    val smsIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
    if (smsIntent.resolveActivity(context.packageManager) != null) {
        context.startActivity(smsIntent)
    }
}
fun formatDoubleToString(double: Double): String {
    val df = DecimalFormat("##.##")
    df.roundingMode = RoundingMode.CEILING
    df.decimalFormatSymbols = DecimalFormatSymbols(Locale.US)
    return df.format(double)
}

fun formatDate(dateStr: String, formatSrc: String, formatDes: String): String {
    val formatter = SimpleDateFormat(formatSrc, Locale.getDefault())
    val date = formatter.parse(dateStr)
    return DateFormat.format(formatDes, date).toString()
}
fun getImageBody(file: File, bodyName: String): MultipartBody.Part {
    val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
    return MultipartBody.Part.createFormData(bodyName, file.name, requestFile)
}
