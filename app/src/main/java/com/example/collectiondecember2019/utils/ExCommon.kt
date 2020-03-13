package com.example.collectiondecember2019.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.os.Build
import android.text.format.DateFormat
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.Toast
import androidx.annotation.ColorRes
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.lang.ref.WeakReference
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

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

fun handleDoubleClick(view: View, context: Context) {
    RxView.clicks(view)
        .throttleFirst(1000, TimeUnit.MILLISECONDS)
        .subscribe {

        }.let {

        }

    //convert to extensions
    fun View.reactiveclick(block: () -> Unit): Disposable {
        return RxView.clicks(this)
            .throttleFirst(1000, TimeUnit.MILLISECONDS)
            .subscribe({
                block()
            }, {})
    }

    val disposable = CompositeDisposable()

    //apply
    val button = Button(context)
    button.reactiveclick {
        // do something
    }.let { disposable.add(it) }
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun View.remove() {
    visibility = View.GONE
}

fun View.hide() {
    visibility = View.INVISIBLE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun String?.checkIsNullOrEmpty(): Boolean {
    return !this.isNullOrEmpty() && !this.equals("null", ignoreCase = true)
}

val EMAIL_ADDRESS_PATTERN = Pattern
    .compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@"
                + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\."
                + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+"
    )

fun String.isvalidEmail(): Boolean {
    return EMAIL_ADDRESS_PATTERN.matcher(this).matches()
}

fun Context.hasNetwork(): Boolean? {
    var isConnected: Boolean? = false // Initial Value
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
    if (activeNetwork != null && activeNetwork.isConnected)
        isConnected = true
    return isConnected
}

val Context.screenWidth: Int
    get() = resources.displayMetrics.widthPixels

val Context.screenHeight: Int
    get() = resources.displayMetrics.heightPixels

fun Context.setSatatusBarColor(context: WeakReference<Activity>, @ColorRes colorResId: Int) {

    if (Build.VERSION.SDK_INT >= 21) {
        val window = context.get()?.window
        window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window?.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window?.statusBarColor = context?.get()!!.resources.getColor(colorResId)
    }

}

fun Context.showToast(msg: String, length: Int) {
    Toast.makeText(
        this, msg,
        Toast.LENGTH_SHORT
    ).show()
}

//delegate
/*
* var accessToken: String
    get() = sharedPreferenceHelper.getString(Constants.ACCESS_TOKEN,"")
    set(accessToken) = sharedPreferenceHelper.setString(Constants.ACCESS_TOKEN, accessToken)

* */

/*
* https://medium.com/android-dev-hacks/advanced-android-programming-with-kotlin-part-2-aae2a15258b0
* */

